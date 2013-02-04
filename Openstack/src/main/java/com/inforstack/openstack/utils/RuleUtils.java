package com.inforstack.openstack.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;

import org.drools.KnowledgeBase;
import org.drools.agent.KnowledgeAgent;
import org.drools.agent.KnowledgeAgentFactory;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;

public class RuleUtils {
	
	private static HashMap<String, KnowledgeAgent> knowledgeBaseCache = new HashMap<String, KnowledgeAgent>();
	
	public static KnowledgeBase readKnowledgeBase(String name, String type, String location) {
		KnowledgeBase kbase = null;
		if (!knowledgeBaseCache.containsKey(name)) {
			String changesetString = createChangeSetString(type, location);
			if (!changesetString.isEmpty()) {
				Resource changeset = ResourceFactory.newByteArrayResource(changesetString.getBytes());
				if (changeset != null) {
					KnowledgeAgent kAgent = KnowledgeAgentFactory.newKnowledgeAgent(name);
					kAgent.applyChangeSet(changeset);
					knowledgeBaseCache.put(name, kAgent);
				}
			}
		}
		if (knowledgeBaseCache.containsKey(name)) {
			kbase = knowledgeBaseCache.get(name).getKnowledgeBase();
		}
		return kbase;
	}
	
	private static String createChangeSetString(String type, String location) {
		StringBuilder str = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<change-set xmlns='http://drools.org/drools-5.0/change-set ' " +
                " xmlns:xs='http://www.w3.org/2001/XMLSchema-instance' " +
                " xs:schemaLocation='http://drools.org/drools-5.0/change-set drools-change-set-5.0.xsd' >");
		str.append("<add>");
		if (type.equalsIgnoreCase("file")) {
			String[] files = location.split(",");
			for (String file : files) {
				appendResource(str, "file:" + getFileURI(file), "PKG");
			}
		} else if (type.equalsIgnoreCase("url")) {
			String[] urls = location.split(",");
			for (String url : urls) {
				appendResource(str, url, "PKG");
			}
		}
		str.append("</add>");
        str.append("</change-set>");
		return str.toString();
	}
	
	private static void appendResource(StringBuilder builder, String source, String type) {
        builder.append("<resource source='");
        builder.append(source);
        builder.append("' type='");
        builder.append(type);
        builder.append("' basicAuthentication='enabled' username='admin' password='admin' />");
    }
	
    private static String getFileURI(String file){
    	try {
			return new File(file).toURI().toURL().getFile();
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException(file);
		}
    }


}
