package com.dd.async;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		BlockingQueue<Job> queue = new ArrayBlockingQueue<Job>(10, true);
		
		Thread producer = new Thread(new Producer(queue));
		Thread consumer = new Thread(new Consumer(queue));
		
		consumer.start();
		producer.start();
	}

}
