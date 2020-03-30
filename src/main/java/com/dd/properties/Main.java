package com.dd.properties;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class Main {

	public static void main(String[] args) throws Exception {
		Server server = new Server(8081);
		ServletHandler handler = new ServletHandler();
		server.setHandler(handler);
		handler.addServletWithMapping(DDServlet.class, "/dd");
		server.start();
		server.join();
	}
}
