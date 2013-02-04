package com.inforstack.openstack.security.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.security.resource.Resource;
import com.inforstack.openstack.security.resource.ResourceService;
import com.inforstack.openstack.utils.StringUtil;

@Service("securityMetadataSource")
public class OpenstackSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	
	private static final Logger log = new Logger(OpenstackSecurityMetadataSource.class);
	
	@Autowired
	private ResourceService resourceService;
	
	private Map<String, List<String>> resourceMap = new HashMap<String, List<String>>();
	
	public OpenstackSecurityMetadataSource(){
	}
	
	@PostConstruct
	public void loadResourceDefine(){
		log.debug("Load defined resources");
		List<Resource> resources = resourceService.listAll();
		if(resources!=null){
			for(Resource resource : resources){
				String url = resource.getUrl();
				if(!StringUtil.isNullOrEmpty(url)){
					url = url.trim();
					if(url.startsWith("/")){
						List<String> permissionLst = resourceMap.get(url);
						if(permissionLst==null){
							permissionLst = new ArrayList<String>();
							resourceMap.put(url, permissionLst);
						}
						permissionLst.add(resource.getPermission());
					}
				}
			}
		}
		log.debug(resourceMap.size() + " resource definition found");
	}
	
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		log.debug("Get ConfigAttribute");
		String url = ((FilterInvocation)object).getRequestUrl();
		int index = url.indexOf("?");
		if(index!=-1) {
			url = url.substring(0, index);
		}
		if(url.endsWith("/")){
			url = url.substring(0, url.length()-1);
		}
        List<String> permissions = resourceMap.get(url);
        
        Collection<ConfigAttribute> attrs = new ArrayList<ConfigAttribute>();
        if(permissions!=null){
        	for(String permission : permissions){
        		attrs.add(new SecurityConfig(permission));
        	}
        }
        log.debug(attrs.size()+" ConfigAttribute found");
        return attrs;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}
}
