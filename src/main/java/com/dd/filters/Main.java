package com.dd.filters;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class Main {

	public static void main(String[] args) throws Exception {
		Server server = new Server(8081);

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
                
		context.addServlet(DDServlet.class, "/dd/*");
		context.addFilter(DDFilter.class, "/dd/*", EnumSet.of(DispatcherType.REQUEST));
		
		server.start();
		server.join();
	}
}
