package com.dd.async;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class Main {

	public static BlockingQueue<JobWrapper> queue;

	public static void main(String[] args) throws Exception {

		queue = new ArrayBlockingQueue<JobWrapper>(10, true);
		
		Thread producer = new Thread(new Producer(queue));
		Thread consumer = new Thread(new Consumer(queue));
		
		consumer.start();
		producer.start();

		Server server = new Server(8081);
		ServletHandler handler = new ServletHandler();
		server.setHandler(handler);
		handler.addServletWithMapping(DefaultASync.class, "/default");
		handler.addServletWithMapping(InstrumentedASync.class, "/instrumented");
		server.start();
		server.join();
	}

}
