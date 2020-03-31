package com.datadog;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Toolbox {

	private static NormalDistribution nd = new NormalDistribution(70, 15);
	
	public static final Logger logger = LoggerFactory.getLogger("com.datadog.demo");

	public static void sleep(long sleep) {
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void sleep() {
		sleep((long) nd.sample());
	}

}
