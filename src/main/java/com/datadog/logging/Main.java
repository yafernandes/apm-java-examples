package com.datadog.logging;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class Main {
	public static void main(String[] args) throws Exception {
		demo(8080);
	}

	public static void demo(int port) throws Exception {
		Server server = new Server(port);
		ServletHandler handler = new ServletHandler();
		server.setHandler(handler);
		handler.addServletWithMapping(Log4jServlet.class, "/instrumented-logs/log4j");
		handler.addServletWithMapping(Log4j2Servlet.class, "/instrumented-logs/log4j2");
		handler.addServletWithMapping(Slf4jServlet.class, "/instrumented-logs/slf4j");
		server.start();
		server.join();
	}
}
