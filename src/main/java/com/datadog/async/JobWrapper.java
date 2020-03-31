package com.datadog.async;

import java.util.Map;

public class JobWrapper {

	private final Object job;
	private Map<String, String> headers;


	public JobWrapper(Object payload) {
		this.job = payload;
	}

	public Object getJob() {
		return job;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
}
