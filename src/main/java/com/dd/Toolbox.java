package com.dd;

import org.apache.commons.math3.distribution.NormalDistribution;

public class Toolbox {

	private static NormalDistribution nd = new NormalDistribution(70, 15);

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
