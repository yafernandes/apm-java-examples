package com.datadog.filters;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class Main {

	public static void main(String[] args) throws Exception {
		demo(8080);
	}

	public static void demo(int port) throws Exception {
		Server server = new Server(port);

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
                
		context.addServlet(DDServlet.class, "/filters/*");
		context.addFilter(DDFilter.class, "/filters/*", EnumSet.of(DispatcherType.REQUEST));
		
		server.start();
		server.join();
	}
}
