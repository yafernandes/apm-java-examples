package com.datadog.interceptor;

import java.util.Collection;
import java.util.Map.Entry;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import datadog.trace.api.GlobalTracer;
import datadog.trace.api.Tracer;
import datadog.trace.api.interceptor.MutableSpan;
import datadog.trace.api.interceptor.TraceInterceptor;

public class Main {

	public static void main(String[] args) throws Exception {
		demo(8080);
	}

	public static void demo(int port) throws Exception {
		Server server = new Server(port);

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		server.setHandler(context);

		context.addServlet(DDServlet.class, "/handlers/*");

		Tracer tracer = GlobalTracer.get();

		tracer.addTraceInterceptor(new TraceInterceptor() {

			@Override
			public int priority() {
				System.out.println("Main.demo(...).new TraceInterceptor() {...}.priority()");
				return 0;
			}

			@Override
			public Collection<? extends MutableSpan> onTraceComplete(Collection<? extends MutableSpan> trace) {
				System.out.println("Main.demo(...).new TraceInterceptor() {...}.onTraceComplete()");
				for (MutableSpan span : trace) {
					System.out.println(span.getOperationName());
					for (Entry<String, Object> tag : span.getTags().entrySet()) {
						System.out.println("\t" + tag.getKey() + ": " + tag.getValue());	
					}
				}
				return trace;
			}
		});

		server.start();
		server.join();
	}
}
