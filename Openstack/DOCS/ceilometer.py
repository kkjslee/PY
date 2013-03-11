#!/usr/bin/python
"This module will communicate with libvirt to gather resource usage"

try:
    import sys
    import time
    import threading
    import datetime
    import socket
    import re
    import libvirt
    import binascii
    from Crypto.PublicKey import RSA
    from Crypto.Cipher import PKCS1_v1_5
    from nova.virt.libvirt.driver import cfg, etree
    from nova.openstack.common import timeutils
    from nova.openstack.common import log as logging
    from nova.openstack.common import jsonutils as json
    from nova.db.sqlalchemy.models import BASE
    from nova.db.sqlalchemy.models import Column, Integer, String, Boolean, DateTime
    from nova.db.sqlalchemy.models import ForeignKey, DateTime
    from nova.db.sqlalchemy.models import relationship, backref
    from nova.db.sqlalchemy.models import get_session
    #from sqlalchemy.ext.declarative import declarative_base
    #from sqlalchemy import create_engine
    #from sqlalchemy.orm import sessionmaker
except ImportError, e:
    print "Import error in %s : %s" % (__name__, e)
    import sys
    sys.exit()

#logger = logging.getLogger(__name__)
logging.logging.basicConfig()
logger = logging.logging.getLogger(__name__)

CONF = cfg.CONF
CONF.register_opts([
    cfg.IntOpt('ceilometer_gather_interval',
               default=2,
               help="ceilometer interval"),

    cfg.IntOpt('ceilometer_upload_interval',
               default=2,
               help="ceilometer upload interval"),

    cfg.StrOpt('ceilometer_server_ip',
               default="192.168.1.176",
               help="ceilometer server ip"),

    cfg.IntOpt('ceilometer_server_port',
               default=11533,
               help="ceilometer server port")

])

#engine=create_engine('mysql://root:inforstack@192.168.1.145:3306/nova',echo=False)
#get_session=sessionmaker(bind=engine)

class DomainInfo(BASE):
    __tablename__="domain_info"
    id = Column(Integer, primary_key=True)
    
    instance_id = Column(Integer, nullable=False)
    instance_uuid = Column(String, nullable=False)
    name = Column(String(255), nullable=False)
    state = Column(Integer, nullable=False)
    max_memory = Column(Integer, nullable=False, default=0)
    memory_used = Column(Integer, nullable=False, default=0)
    cpu_number = Column(Integer, nullable=False, default=0)
    cpu_time = Column(Integer, nullable=False, default=0)
    deleted = Column(Boolean, nullable=False, default=False)
    log_time = Column(DateTime, nullable=False, default=timeutils.utcnow)

class DiskInfo(BASE):
     __tablename__="disk_info"
     id = Column(Integer, primary_key=True)
     
     name = Column(String(255), nullable=False)
     capacity_bytes = Column(Integer, nullable=False, default=0)
     allocation_bytes = Column(Integer, nullable=False, default=0)
     physical_bytes = Column(Integer, nullable=False, default=0)
     read_number = Column(Integer, nullable=False, default=0)
     read_bytes = Column(Integer, nullable=False, default=0)
     write_number = Column(Integer, nullable=False, default=0)
     write_bytes = Column(Integer, nullable=False, default=0)
     
     domain_info_id= Column(Integer, ForeignKey('domain_info.id'), nullable=False)
     domainInfo= relationship("DomainInfo",
                                cascade="all",
                                backref=backref('disk_infos'),
                                foreign_keys=domain_info_id,
                                primaryjoin='DiskInfo.domain_info_id == DomainInfo.id')

class InterfaceInfo(BASE):
     __tablename__="interface_info"
     id = Column(Integer, primary_key=True)
     
     name= Column(String(255), nullable=False)
     incoming_bytes = Column(Integer, nullable=False, default=0)
     incoming_packages = Column(Integer, nullable=False, default=0)
     incoming_errors = Column(Integer, nullable=False, default=0)
     incoming_drop = Column(Integer, nullable=False, default=0)
     outgoing_bytes = Column(Integer, nullable=False, default=0)
     outgoing_packages = Column(Integer, nullable=False, default=0)
     outgoing_errors = Column(Integer, nullable=False, default=0)
     outgoing_drop = Column(Integer, nullable=False, default=0)
          
     domain_info_id = Column(Integer, ForeignKey('domain_info.id'), nullable=False)
     domainInfo = relationship("DomainInfo",
                                cascade="all",
                                backref=backref('interface_infos'),
                                foreign_keys=domain_info_id,
                                primaryjoin='InterfaceInfo.domain_info_id == DomainInfo.id')

class UploaderSocket(object):

    splitRe = re.compile("\r?\n")

    def __init__(self):
        self.socket = None
        self.result = ""

    def buildSocket(self):
        if self.socket:
            self.closeSocket()

        try:
            self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            self.socket.connect((CONF.ceilometer_server_ip, CONF.ceilometer_server_port))
            rsa_pub_key = ""
            while True:
                line = self.readLine()
                if len(line) == 0:
                    break
                rsa_pub_key = rsa_pub_key + line + "\n"

            key = RSA.importKey(rsa_pub_key)
            cipher = PKCS1_v1_5.new(key)
            data = cipher.encrypt("ceilometer|"+timeutils.isotime())
            self.sendData(binascii.hexlify(data))
        except Exception, e: 
            logger.error('%s: %s' % (e.__class__.__name__, e))
            self.closeSocket()

        return self.socket

    def readLine(self):
        ret = None
        
        if self.result:
            splits = UploaderSocket.splitRe.split(self.result, 1)
            if len(splits) == 2:
                self.result = splits[1]
                ret = splits[0]

        if not ret : 
            s = self.socket
            if s:
                while True:
                    c = s.recv(1024)
                    splits = UploaderSocket.splitRe.split(c, 1)
                    if len(splits) == 1:
                        if len(splits[0]) == 0:
                            ret = self.result
                            self.result = ""
                            break
                        self.result = self.result + splits[0]
                    else:
                        ret = self.result + splits[0]
                        self.result = splits[1]
                        break

        return ret

    def end(self):
        self.sendData("")

    def sendData(self, data):
        if data == None:
            return False

        try:
            s = self.socket
            if s:
                s.sendall(data + "\n")
                resp = self.readLine()
                if len(resp) > 0 :
                    result = json.loads(resp)
                    if type(result) == type({}) and result.get("status") == 1:
                        return True
        except Exception, e:
            logger.error('%s: %s' % (e.__class__.__name__, e))

        self.closeSocket()
        return False
                
    def closeSocket(self):
        s = self.socket
        self.socket = None
        if s:
            try:
                s.shutdown(1)  
                s.close()
            except socket.error, e:
                logger.error('%s: %s' % (e.__class__.__name__, e))

class UploaderThread(threading.Thread):

    isRunning = False

    def __init__(self):
        threading.Thread.__init__(self)
        self._dataCache = {}

    def run(self):
        uploader = UploaderSocket()
        if not uploader.buildSocket():
            UploaderThread.isRunning = False
            return
            
        session = None
        try:
            session = get_session()
            rows = self._unsendedRows(session)
            
            for row in rows:
                data = self._findById(row[0], session)
                if not data:
                    continue
                l_data = self._last(data.instance_uuid, session)
                increment = self._calcIncrement(data, l_data)
                increment["logTime"] = data.log_time
                result = uploader.sendData(json.dumps(increment))
                if result :
                    data.deleted = True
                    session.commit()
                    self._dataCache[data.instance_uuid] = data
            uploader.end()
        except Exception, e:
            logger.error('%s: %s' % (e.__class__.__name__, e))
        finally:
            UploaderThread.isRunning = False
            if session:
                session.close()
            if uploader:
                uploader.closeSocket()

    def _unsendedRows(self, session):
        return session.execute(
                    session.query(DomainInfo.id).\
                    filter_by(deleted=False).\
                    order_by(DomainInfo.log_time.asc())
                )

    def _findById(self, domainInfoId, session):
        return session.query(DomainInfo).\
                    filter_by(id=domainInfoId, deleted=False).\
                    first()
    
    def _last(self, instance_uuid, session):
        """
            Get the latest record form database
        """
        last = self._dataCache.get(instance_uuid)
        if not last:
            last = session.query(DomainInfo).\
                    filter_by(instance_uuid=instance_uuid, deleted=True).\
                    order_by(DomainInfo.log_time.desc()).\
                    first()
        return last
        
    def _calcIncrement(self, data, l_data):
        increment = self._buildIncrement(data)
        
        if l_data and l_data.instance_id == data.instance_id:
            increment['cpuTime'] = data.cpu_time - l_data.cpu_time

            for diskInfo in increment['diskUsages']:
                name = diskInfo['name']
                l_diskInfo = None
                for info in l_data.disk_infos:
                    if info.name == name:
                        l_diskInfo = info
                        break
                if l_diskInfo:
                    diskInfo['readNumber'] = diskInfo['readNumber'] - l_diskInfo.read_number
                    diskInfo['readBytes'] = diskInfo['readBytes'] - l_diskInfo.read_bytes
                    diskInfo['writeNumber'] = diskInfo['writeNumber'] - l_diskInfo.write_number
                    diskInfo['writeBytes'] = diskInfo['writeBytes'] - l_diskInfo.write_bytes

            for interfaceInfo in increment['interfaceUsages']:
                name = interfaceInfo['name']
                l_interfaceInfo = None
                for info in l_data.interface_infos:
                    if info.name == name:
                        l_interfaceInfo = info
                        break
                if l_interfaceInfo:
                    interfaceInfo['incomingBytes'] = interfaceInfo['incomingBytes'] - l_interfaceInfo.incoming_bytes
                    interfaceInfo['incomingPackages'] = interfaceInfo['incomingPackages'] - l_interfaceInfo.incoming_packages
                    interfaceInfo['incomingErrors'] = interfaceInfo['incomingErrors'] - l_interfaceInfo.incoming_errors
                    interfaceInfo['incomingDrop'] = interfaceInfo['incomingDrop'] - l_interfaceInfo.incoming_drop
                    interfaceInfo['outgoingBytes'] = interfaceInfo['outgoingBytes'] - l_interfaceInfo.outgoing_bytes
                    interfaceInfo['outgoingPackages'] = interfaceInfo['outgoingPackages'] - l_interfaceInfo.outgoing_packages
                    interfaceInfo['outgoingErrors'] = interfaceInfo['outgoingErrors'] - l_interfaceInfo.outgoing_errors
                    interfaceInfo['outgoingDrop'] = interfaceInfo['outgoingDrop'] - l_interfaceInfo.outgoing_drop

        return increment

    def _buildIncrement(self, data):
        increment = {}
        increment['instanceId'] = data.instance_id
        increment['instanceUuid'] = data.instance_uuid
        increment['name'] = data.name
        increment['state'] = data.state
        increment['maxMemory'] = data.max_memory
        increment['memoryUsed'] = data.memory_used
        increment['cpuNumber'] = data.cpu_number
        increment['cpuTime'] = data.cpu_time

        increment['diskUsages'] = []
        for diskInfo in data.disk_infos:
            info = {}
            info['name'] = diskInfo.name
            info['capacityBytes'] = diskInfo.capacity_bytes
            info['allocationBytes'] = diskInfo.allocation_bytes
            info['physicalBytes'] = diskInfo.physical_bytes
            info['readNumber'] = diskInfo.read_number
            info['readBytes'] = diskInfo.read_bytes
            info['writeNumber'] = diskInfo.write_number
            info['writeBytes'] = diskInfo.write_bytes
            increment['diskUsages'].append(info)

        increment['interfaceUsages'] = []
        for interfaceInfo in data.interface_infos:
            info = {}
            info['name'] = interfaceInfo.name
            info['incomingBytes'] = interfaceInfo.incoming_bytes
            info['incomingPackages'] = interfaceInfo.incoming_packages
            info['incomingErrors'] = interfaceInfo.incoming_errors
            info['incomingDrop'] = interfaceInfo.incoming_drop
            info['outgoingBytes'] = interfaceInfo.outgoing_bytes
            info['outgoingPackages'] = interfaceInfo.outgoing_packages
            info['outgoingErrors'] = interfaceInfo.outgoing_errors
            info['outgoingDrop'] = interfaceInfo.outgoing_drop
            increment['interfaceUsages'].append(info)

        return increment
    
    def _findInfoByName(self, infos, name):
        if not infos or not name: 
            return None
        
        ret = None
        for info in infos:
            if info.name == name:
                ret = info
                break
        return ret

    def _calcDiskIncrement(self, data, l_data):
        if not data:
            return
        
        if not l_data:
            data.read_number = data.read_number_total - l_data.read_number_total
            data.read_bytes = data.read_bytes_total - l_data.read_bytes_total
            data.write_number = data.write_number_total - l_data.write_number_total
            data.write_bytes = data.write_bytes_total - l_data.write_bytes_total
        else:
            data.read_number = data.read_number_total
            data.read_bytes = data.read_bytes_total
            data.write_number = data.write_number_total
            data.write_bytes = data.write_bytes_total

    def _calcInterfaceIncrement(self, data, l_data):
        if not data:
            return
        if not l_data:
            data.incoming_bytes = data.incoming_bytes_total-l_data.incoming_bytes_total
            data.incoming_packages = data.incoming_packages_total-l_data.incoming_packages_total
            data.incoming_errors = data.incoming_errors_total-l_data.incoming_errors_total
            data.incoming_drop = data.incoming_drop_total-l_data.incoming_drop_total
            data.outgoing_bytes = data.outgoing_bytes_total-l_data.outgoing_bytes_total
            data.outgoing_packages = data.outgoing_packages_total - l_data.outgoing_packages_total
            data.outgoing_errors = data.outgoing_errors_total - l_data.outgoing_errors_total
            data.outgoing_drop = data.outgoing_drop_total - l_data.outgoing_drop_total
        else:
            data.incoming_bytes = data.incoming_bytes_total
            data.incoming_packages = data.incoming_packages_total
            data.incoming_errors = data.incoming_errors_total
            data.incoming_drop = data.incoming_drop_total
            data.outgoing_bytes = data.outgoing_bytes_total
            data.outgoing_packages = data.outgoing_packages_total
            data.outgoing_errors = data.outgoing_errors_total
            data.outgoing_drop = data.outgoing_drop_total

class Ceilometer:
    """
        Class gather reources from libvirt
    """
    def __init__(self):
        self._conn = libvirt.openReadOnly(self.uri)

    @property
    def uri(self):
        if CONF.libvirt_type == 'uml':
            uri = CONF.libvirt_uri or 'uml:///system'
        elif CONF.libvirt_type == 'xen':
            uri = CONF.libvirt_uri or 'xen:///'
        elif CONF.libvirt_type == 'lxc':
            uri = CONF.libvirt_uri or 'lxc:///'
        else:
            uri = CONF.libvirt_uri or 'qemu:///system'
        return uri

    
    def doCollection(self):
        conn = self._conn

        for id in conn.listDomainsID():
            domain = self.get_domain(id)
            info = self.get_info(domain)
            info.disk_infos.extend(self.get_disk_infos(domain))
            info.interface_infos.extend(self.get_interface_infos(domain))
            self.save(info)

    def save(self, info):
        session = get_session()
        try:
            session.add(info)
            session.commit()
        except Exception, e:
            logger.error('%s: %s' % (e.__class__.__name__, e))
            session.rollback()
        finally:
            session.close()
               
    def get_domain(self, instance_id):
        return self._conn.lookupByID(instance_id)

    def get_info(self, domain):
        (state, max_mem, mem, num_cpu, cpu_time) = domain.info()
        return DomainInfo(
                instance_id = domain.ID(),
                instance_uuid = domain.UUIDString(),
                name= domain.name(),
                state= state,
                max_memory= max_mem,
                memory_used= mem,
                cpu_number= num_cpu,
                cpu_time= cpu_time)

    def get_disk_infos(self, domain):
        infos = []
        for d in self.get_disks(domain):
            (rd_req, rd_bytes, wr_req, wr_bytes, errs) = domain.blockStats(d)
            (dc, da, dp) = domain.blockInfo(d, 0)
            infos.append(
                DiskInfo(
                    name = d,
                    capacity_bytes = dc,
                    allocation_bytes = da,
                    physical_bytes = dp,
                    read_number = rd_req,
                    read_bytes  = rd_bytes,
                    write_number  = wr_req,
                    write_bytes  = wr_bytes)
            )
            
        return infos

    def get_interface_infos(self, domain):
        infos = []
        for i in self.get_interfaces(domain):
            (rx_bytes, rx_packets,rx_errs,rx_drop,tx_bytes,tx_packets,tx_errs,tx_drop) = domain.interfaceStats(i)
            infos.append(
                InterfaceInfo(
                    name= i,
                    incoming_bytes = rx_bytes,
                    incoming_packages = rx_packets,
                    incoming_errors = rx_errs,
                    incoming_drop = rx_drop,
                    outgoing_bytes = tx_bytes,
                    outgoing_packages = tx_packets,
                    outgoing_errors = tx_errs,
                    outgoing_drop = tx_drop)
            )
        return infos

    def get_disks(self, domain):
        """
        Returns a list of all block devices for this domain.
        """
        xml = domain.XMLDesc(0)
        doc = None
        
        try:
            doc = etree.fromstring(xml)
        except:
            return []

        disks = []
        for node in doc.findall('./devices/disk'):
            devdst = None
                    
            for child in node.getchildren():
                if child.tag == 'target':
                    devdst = child.get('dev')
                    if devdst == None:
                        continue
                    disks.append(devdst)

        return disks


    def get_interfaces(self, domain):
        """
        Returns a list of all network interfaces for this instance.
        """
        xml = domain.XMLDesc(0)
        doc = None

        try:
            doc = etree.fromstring(xml)
        except:
            return []
        
        interfaces = []
        for node in doc.findall('./devices/interface'):
            devdst = None

            for child in node.getchildren():
                if child.tag == 'target':
                    devdst = child.get('dev')
                    if devdst == None:
                        continue
                    interfaces.append(devdst)

        return interfaces


if __name__ == '__main__':
    ceilometer = Ceilometer()
    def main():
        interval = CONF.ceilometer_gather_interval
        while True:
            try:
                time.sleep(interval)
                t = threading.Timer(0, ceilometer.doCollection)
                t.daemon = True
                t.start()
            except Exception, e:
                logger.error('%s: %s' % (e.__class__.__name__, e))

    t = threading.Timer(0, main)
    t.daemon = True
    t.start()

    interval = CONF.ceilometer_upload_interval
    while True :
        try:
            time.sleep(interval)
            if not UploaderThread.isRunning:
                UploaderThread.isRunning = True
                ut = UploaderThread()
                ut.daemon = True
                ut.start()
        except Exception, e:
            logger.error('%s: %s' % (e.__class__.__name__, e))
