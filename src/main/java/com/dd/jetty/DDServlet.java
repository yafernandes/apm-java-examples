package com.dd.jetty;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.math3.distribution.NormalDistribution;

import datadog.trace.api.DDSpanTypes;
import datadog.trace.api.DDTags;
import datadog.trace.api.interceptor.MutableSpan;
import io.opentracing.Span;
import io.opentracing.util.GlobalTracer;

public class DDServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private NormalDistribution nd = new NormalDistribution(70, 15); 

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, Object> prop = new HashMap<String, Object>();
		Span span = GlobalTracer.get().activeSpan();
		if (span instanceof MutableSpan) {
			MutableSpan mSpan = (MutableSpan) span;
			prop.putAll(mSpan.getTags());
			
			prop.put("operation", mSpan.getOperationName());
			mSpan.setOperationName("Custom operation");

			prop.put(DDTags.RESOURCE_NAME, mSpan.getResourceName());
			mSpan.setResourceName("Custom resource");

			prop.put(DDTags.SERVICE_NAME, mSpan.getServiceName());
			mSpan.setServiceName("Custom service");

			prop.put(DDTags.SPAN_TYPE, mSpan.getSpanType());
			mSpan.setSpanType(DDSpanTypes.MONGO);
		}
		
		try {
			Thread.sleep((long) nd.sample());
		} catch (InterruptedException e) {
			throw new ServletException(e);
		}
		resp.setContentType("text/plain");
		resp.getWriter().println("===  ORIGINAL VALUES ===");
		for (Entry<String, Object> entry : prop.entrySet()) {
			resp.getWriter().println(entry.getKey() + ": " + entry.getValue());
		}
	}

}
