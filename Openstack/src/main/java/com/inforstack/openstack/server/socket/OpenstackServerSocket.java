package com.inforstack.openstack.server.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inforstack.openstack.configuration.ConfigurationService;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.server.socket.handler.SocketHanlder;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.NumberUtil;
import com.inforstack.openstack.utils.OpenstackUtil;

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
			while(true){
				Socket s = null;
				try {
					s = ss.accept();
					BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream(), "utf-8"));
					String type = reader.readLine();
					
					SocketHanlder handler = SocketHanlder.getHandler(type);
					if(handler == null){
						OpenstackUtil.close(s);
					}else{
						handler.handle(s);
					}
				} catch (IOException e) {
					log.error(e.getMessage(), e);
					OpenstackUtil.close(s);
				}
			}
		}

	}
}
