package com.datadog.kafka;

public class Main {

	private static final String TOPIC = "datadog_demo";

	public static void main(String[] args) throws Exception {
		demo(System.getenv("CONNECT_BOOTSTRAP_SERVERS"));
	}

	public static void demo(String bootstrapServer) {
		new Thread(new Consumer(bootstrapServer, TOPIC)).start();
		new Thread(new Producer(bootstrapServer, TOPIC)).start();
	}
}
