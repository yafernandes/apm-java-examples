package com.dd.kafka;

public class Main {

	private static final String CONNECT_BOOTSTRAP_SERVERS = "CONNECT_BOOTSTRAP_SERVERS";
	private static final String TOPIC = "datadog_demo";

	public static void main(String[] args) {
		String bootstrapServer = System.getenv(CONNECT_BOOTSTRAP_SERVERS);

		new Thread(new Consumer(bootstrapServer, TOPIC)).start();
		new Thread(new Producer(bootstrapServer, TOPIC)).start();
	}
}
