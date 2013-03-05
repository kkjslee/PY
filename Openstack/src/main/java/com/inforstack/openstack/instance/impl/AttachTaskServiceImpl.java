package com.inforstack.openstack.instance.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.cinder.CinderService;
import com.inforstack.openstack.api.cinder.VolumeAttachment;
import com.inforstack.openstack.api.keystone.Access;
import com.inforstack.openstack.api.keystone.KeystoneService;
import com.inforstack.openstack.instance.AttachTask;
import com.inforstack.openstack.instance.AttachTaskDao;
import com.inforstack.openstack.instance.AttachTaskService;
import com.inforstack.openstack.instance.Instance;
import com.inforstack.openstack.instance.InstanceDao;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.OpenstackUtil;

@Service
@Transactional
public class AttachTaskServiceImpl implements AttachTaskService {
	
	private static final Logger log = new Logger(AttachTaskServiceImpl.class);

	@Autowired
	private InstanceDao instanceDao;
	
	@Autowired
	private AttachTaskDao taskDao;
	
	@Autowired
	private KeystoneService keystoneService;
	
	@Autowired
	private CinderService cinderService;
	
	@PostConstruct
	public void initThread() {		
		Thread thread = new Thread("Attach Task Thread") {

			@Override
			public void run() {
				try {
					Thread.sleep(1000 * 60);
				} catch (InterruptedException e) {
				}
				while (true) {
					try {
						Thread.sleep(1000);
						AttachTaskService self = OpenstackUtil.getBean(AttachTaskService.class);
						if (self != null) {
							self.handleTask();
						}
					} catch (InterruptedException e) {
					}
				}
			}
			
		};
		thread.start();
	}
	
	@Override
	public List<AttachTask> listAll() {
		return this.taskDao.listAll();
	}
	
	@Override
	public List<AttachTask> listNewTasks() {
		return this.taskDao.listByObject("status", Constants.ATTACH_TASK_STATUS_NEW);
	}

	@Override
	public List<AttachTask> listProcessingTasks() {
		return this.taskDao.listByObject("status", Constants.ATTACH_TASK_STATUS_PROCESSING);
	}
	
	@Override
	public List<AttachTask> listErrorTasks() {
		return this.taskDao.listByObject("status", Constants.ATTACH_TASK_STATUS_ERROR);
	}

	@Override
	public List<AttachTask> listCompletedTasks() {
		return this.taskDao.listByObject("status", Constants.ATTACH_TASK_STATUS_COMPLETE);
	}
	
	@Override
	public void addTask(int type, String vmId, String attachmentId, String user, String pass, String tenant) {
		AttachTask task = new AttachTask();
		task.setType(type);
		task.setStatus(Constants.ATTACH_TASK_STATUS_NEW);
		task.setServerId(vmId);
		task.setAttachmentId(attachmentId);
		task.setCreateTime(new Date());
		task.setUser(user);
		task.setPass(pass);
		task.setTenant(tenant);
		this.taskDao.persist(task);
	}

	@Override
	public void handleTask() {
		List<AttachTask> tasks = this.taskDao.listByObject("status", Constants.ATTACH_TASK_STATUS_NEW);
		for (int idx = 0; idx < 10 && idx < tasks.size(); idx++) {
			Date now = new Date();
			AttachTask task = tasks.get(idx);
			int type = task.getType();
			String serverId = task.getServerId();
			String attachId = task.getAttachmentId();
			String user = task.getUser();
			String pass = task.getPass();
			String tenant = task.getTenant();
			switch (type) {
				case Constants.ATTACH_TASK_TYPE_VOLUME: {
					try {
						Access access = this.keystoneService.getAccess(user, pass, tenant, true);
						if (access != null) {
							Instance vm = this.instanceDao.findByObject("uuid", serverId);
							if (vm != null && vm.getStatus().equalsIgnoreCase("active")) {
								Instance volume = this.instanceDao.findByObject("uuid", attachId);
								if (volume != null && volume.getStatus().equalsIgnoreCase("available")) {
									task.setUpdateTime(now);
									task.setStatus(Constants.ATTACH_TASK_STATUS_PROCESSING);
									VolumeAttachment va = this.cinderService.attachVolume(access, serverId, attachId, null);
									if (va.getId() != null && !va.getId().trim().isEmpty()) {
										task.setUpdateTime(now);
										task.setStatus(Constants.ATTACH_TASK_STATUS_COMPLETE);
										log.info("Attach volume[" + attachId + "] to vm[" + serverId + "]");
									} else {
										task.setUpdateTime(now);
										task.setStatus(Constants.ATTACH_TASK_STATUS_ERROR);
										log.error("Can't attach volume[" + attachId + "] to vm[" + serverId + "]");
									}
								}
							}												
						}
					} catch (OpenstackAPIException e) {
						task.setUpdateTime(now);
						task.setStatus(Constants.ATTACH_TASK_STATUS_ERROR);
						log.error("Can't attach volume[" + attachId + "] to vm[" + serverId + "]", e);
					}
					break;
				}
				case Constants.DETACH_TASK_TYPE_VOLUME:
					try {
						Access access = this.keystoneService.getAccess(user, pass, tenant, true);
						if (access != null) {
							Instance vm = this.instanceDao.findByObject("uuid", serverId);
							if (vm != null && vm.getStatus().equalsIgnoreCase("deleted")) {
								Instance volume = this.instanceDao.findByObject("uuid", attachId);
								if (volume != null && volume.getStatus().equalsIgnoreCase("in-use")) {
									task.setUpdateTime(now);
									task.setStatus(Constants.ATTACH_TASK_STATUS_PROCESSING);
									this.cinderService.removeVolume(access, attachId);
									task.setUpdateTime(now);
									task.setStatus(Constants.ATTACH_TASK_STATUS_COMPLETE);
								}
							}												
						}
					} catch (OpenstackAPIException e) {
						task.setUpdateTime(now);
						task.setStatus(Constants.ATTACH_TASK_STATUS_ERROR);
						log.error("Can't attach volume[" + attachId + "] to vm[" + serverId + "]", e);
					}
					break;
				}				
		}
	}

}
