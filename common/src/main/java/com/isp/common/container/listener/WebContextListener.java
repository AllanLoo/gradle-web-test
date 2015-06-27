package com.isp.common.container.listener;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;



public class WebContextListener extends org.springframework.web.context.ContextLoaderListener {
	
	@Override
	public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
		if (!printKeyLoadMessage()){
			return null;
		}
		return super.initWebApplicationContext(servletContext);
	}

	private static boolean printKeyLoadMessage(){
		StringBuilder sb = new StringBuilder();
		sb.append("\r\n====================================================\r\n");
		sb.append("\r\n        欢迎使用本平台，祝您使用愉快！                  \r\n");
		sb.append("\r\n====================================================\r\n");
		return true;
	}
}
