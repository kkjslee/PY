package com.inforstack.openstack.server.socket.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.codehaus.jackson.map.ObjectMapper;

import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.NumberUtil;
import com.inforstack.openstack.utils.OpenstackUtil;
import com.inforstack.openstack.utils.StringUtil;
import com.inforstack.openstack.virt.domain.usage.DomainUsage;
import com.inforstack.openstack.virt.domain.usage.DomainUsageService;

public class CeilometerSocketHandler extends SocketHanlder {
	
	private static final Logger log = new Logger(CeilometerSocketHandler.class);
	private static final DomainUsageService DOMAIN_USAGE_SERVICE = 
			(DomainUsageService)OpenstackUtil.getBean("domainUsageService");
	
	protected CeilometerSocketHandler(){
	}

	@Override
	protected void doHandle(Socket s) {
		BufferedReader reader = null;
		PrintWriter writer = null;
		try {
			reader = new BufferedReader(new InputStreamReader(s.getInputStream(), "utf-8"));
			writer = new PrintWriter(s.getOutputStream(), true);
			
			writer.println(OpenstackUtil.buildSuccessJsonString("Welcome to ceilometer server!"));
			
			int time = NumberUtil.getInteger(OpenstackUtil
					.getConfig(Constants.CONFIG_SOCKET_CEILOMETER_TIMEOUT), 60);
			while(!s.isClosed()){
				s.setSoTimeout(1000 * time);
				String str = reader.readLine();
				
				if(StringUtil.isNullOrEmpty(str) || str.matches("\\s")){
					break;
				}
				
				DomainUsage domainUsage = parse(str);
				if(domainUsage != null){
					if(!DOMAIN_USAGE_SERVICE.exists(domainUsage)){
						DOMAIN_USAGE_SERVICE.add(domainUsage);
					}
					writer.println(OpenstackUtil.buildSuccessJsonString("Uploaded successfully!"));
				}else{
					break;
				}
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}finally{
			OpenstackUtil.close(reader);
			OpenstackUtil.close(writer);
		}
	}

	private DomainUsage parse(String str) {
		try {
			return new ObjectMapper().readValue(str, DomainUsage.class);
		} catch (Exception e) {
			log.warn("Generate domain usage from json string failed : " + e.getMessage());
		}
		
		return null;
	}
}
