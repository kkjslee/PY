package com.inforstack.openstack.server.socket.handler;

import java.net.Socket;

import com.inforstack.openstack.utils.OpenstackUtil;

public abstract class SocketHanlder {
	
	protected SocketHanlder(){
		
	}

	public void handle(final Socket s){
		if(s == null || !s.isConnected()) return;
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try{
					doHandle(s);
				}finally{
					OpenstackUtil.close(s);
				}
			}
		}).start();
	}
	
	protected abstract void doHandle(Socket s);
	
	public static final SocketHanlder getHandler(String s){
		if("ceilometer".equals(s)){
			return new CeilometerSocketHandler();
		}
		
		return null;
	}
}
