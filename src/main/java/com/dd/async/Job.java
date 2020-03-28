package com.dd.async;

import java.util.Map;

public class Job {

	private final Object payload;
	private Map<String, String> context;


	public Job(String payload) {
		this.payload = payload;
	}

	public Object getPayload() {
		return payload;
	}

	public Map<String, String> getContext() {
		return context;
	}

	public void setContext(Map<String, String> context) {
		this.context = context;
	}
}
