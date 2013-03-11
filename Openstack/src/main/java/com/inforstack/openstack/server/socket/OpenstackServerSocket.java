package com.inforstack.openstack.server.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.xml.bind.DatatypeConverter;

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
					
					writer.println(CryptoUtil.RSA_PEM_PUBLIC_KEY);
					writer.println();
					
					s.setSoTimeout(1000*60*1);
					String encrypted = reader.readLine();
					if(StringUtil.isNullOrEmpty(encrypted, false) == false){
						String plain = CryptoUtil.rsaPrivateEncrypt(Hex.decode(encrypted), CryptoUtil.RSA_DER_PRIVATE_KEY);
						
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
