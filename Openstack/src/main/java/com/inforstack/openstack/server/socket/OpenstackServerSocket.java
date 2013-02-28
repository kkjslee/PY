package com.inforstack.openstack.server.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Security;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.xml.bind.DatatypeConverter;

import org.bouncycastle.openssl.PEMReader;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inforstack.openstack.configuration.ConfigurationService;
import com.inforstack.openstack.exception.ApplicationRuntimeException;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.server.socket.handler.SocketHanlder;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.CryptoUtil;
import com.inforstack.openstack.utils.NumberUtil;
import com.inforstack.openstack.utils.OpenstackUtil;
import com.inforstack.openstack.utils.StringUtil;

@Component
public class OpenstackServerSocket {
	
	private static final Logger log = new Logger(OpenstackServerSocket.class);
	private ServerSocket ss;
	@Autowired
	private ConfigurationService configurationService;
	
	private static final String RSA_PEM_PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----\n"
				+ "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC60sj093Jcp41M2VgGCYTaXkaR\n"
				+ "YKZNiJUBBkifUHUPS7ThSnP7WY5WaWILZxB+Vjg64v80TtAcczJ+gAC6//MK28OV\n"
				+ "Mdhe8aVHBZBqxk++WmzCBXINgcYEQ/Z/mbNA4q3oQRowWlLKEVunh1Kf03mHWIFO\n"
				+ "7prKZyBEpmUCUBB9MwIDAQAB\n"
				+ "-----END PUBLIC KEY-----";
	
	private static final String RSA_PEM_PRIVATE_KEY = "-----BEGIN RSA PRIVATE KEY-----\n"
				+ "MIICXQIBAAKBgQC60sj093Jcp41M2VgGCYTaXkaRYKZNiJUBBkifUHUPS7ThSnP7\n"
				+ "WY5WaWILZxB+Vjg64v80TtAcczJ+gAC6//MK28OVMdhe8aVHBZBqxk++WmzCBXIN\n"
				+ "gcYEQ/Z/mbNA4q3oQRowWlLKEVunh1Kf03mHWIFO7prKZyBEpmUCUBB9MwIDAQAB\n"
				+ "AoGBALPqjtErBB2q1m8t2JFn1WOe6wLSOxXr9ONZs9KJX1JtrDJSy0NCSct6DYvB\n"
				+ "o6anTduYBAHR7KWZYwVkLE/qcZlY2vpsL4UCVzYRF2yPrevQQG+5ZWlrszC5hp0F\n"
				+ "Ep3KYbDqD59zdrP949VYzeGtCDlO20XgcFbKcXqY3uLPlhlhAkEAwMYuVBhtH8hU\n"
				+ "QubyCuUc8iNMJWt2pnXBtg3JLfzUkmgkNqehwfTzZbDF4q2EQkM7ZTBqyjlkNBcS\n"
				+ "17XLuOPvqwJBAPgY9lXxIf2/7+EZ92KRkEHbotvHRPmFcUlSeoVRBh5qmTeKpwyj\n"
				+ "vIOcEkpArvr26fMN1PcUmmgvx9OozDZCwJkCQBQMiIT2hWLo0tqiakn3yirkwOaj\n"
				+ "ZpOpa5wjkujVgsY3TozgolIpx6ar2+jXYwoBNAwyHOkrTuCcBbmpjqaMDkECQQCX\n"
				+ "v/z8uE9IPFxnXVCZs9t+zO8iaxJfZSXT6WUTommRtTYeaOqgqo4mGhJ95G6jBuA2\n"
				+ "UKGQt8NyMreRWU//aHkRAkAvgoLx/+EKvIE1kcprlXkOvkeM+Z0LPrDoYr0iHs53\n"
				+ "Gl2kSLqx5g+0xnJ3q1ZpGZMu3m/D98UJ+MpuCNVRqUFG\n"
				+ "-----END RSA PRIVATE KEY-----";
	
	private static final byte[] RSA_DER_PRIVATE_KEY;
	private static final byte[] RSA_DER_PUBLIC_KEY;
	static{
		KeyPair pk;
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			pk = (KeyPair)new PEMReader(new StringReader(RSA_PEM_PRIVATE_KEY)).readObject();
			RSA_DER_PRIVATE_KEY = pk.getPrivate().getEncoded();
			RSA_DER_PUBLIC_KEY = pk.getPublic().getEncoded();
		} catch (IOException e) {
			throw new ApplicationRuntimeException(e);
		}
	}
	
	@PostConstruct
	public void startSocket(){
		try {
			ss = new ServerSocket();
			ss.bind(new InetSocketAddress(
					configurationService.getValueByName(Constants.CONFIG_SOCKET_IP), 
					NumberUtil.getInteger(configurationService.getValueByName(Constants.CONFIG_SOCKET_PORT))));
		} catch (IOException e) {
			log.fetal(e.getMessage(), e);
			return;
		}
		new Thread(new SocketThread()).start();
	}
	
	private class SocketThread implements Runnable {
		
		@Override
		public void run() {
			int tolerance = NumberUtil.getInteger(
					configurationService.getValueByName(
							Constants.CONFIG_SOCKET_TIMESTAMP_TOLERANCE),	
					60);
			while(true){
				Socket s = null;
				try {
					s = ss.accept();
					BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream(), "utf-8"));
					PrintWriter writer = new PrintWriter(s.getOutputStream(), true);
					
					writer.println(RSA_PEM_PUBLIC_KEY);
					writer.println();
					
					s.setSoTimeout(1000*60*1);
					String encrypted = reader.readLine();
					if(StringUtil.isNullOrEmpty(encrypted, false) == false){
						String plain = CryptoUtil.rsaPrivateEncrypt(Hex.decode(encrypted), RSA_DER_PRIVATE_KEY);
						
						String[] splits = plain.split("\\|");
						if(splits.length == 2 && splits[1].contains("T")){
							Calendar timestamp = DatatypeConverter.parseDateTime(splits[1]);
							long between = Math.abs((timestamp.getTimeInMillis()-new Date().getTime()));
							if(between/1000 < tolerance){
								SocketHanlder handler = SocketHanlder.getHandler(splits[0]);
								if(handler != null){
									handler.handle(s);
									continue;
								}
							}
						}
					}
					
					OpenstackUtil.close(s);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					OpenstackUtil.close(s);
				}
			}
		}

	}
}
