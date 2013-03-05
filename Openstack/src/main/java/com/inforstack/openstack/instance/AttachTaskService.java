package com.inforstack.openstack.instance;

import java.util.List;

public interface AttachTaskService {
	
	public List<AttachTask> listAll();
	
	public List<AttachTask> listNewTasks();
	
	public List<AttachTask> listProcessingTasks();
	
	public List<AttachTask> listErrorTasks();
	
	public List<AttachTask> listCompletedTasks();
	
	public void addTask(int type, String vmId, String attachmentId, String user, String pass, String tenant);
	
	public void handleTask();

}
