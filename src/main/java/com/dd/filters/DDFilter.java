package com.dd.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.math3.distribution.NormalDistribution;

import datadog.trace.api.interceptor.MutableSpan;
import io.opentracing.Span;
import io.opentracing.util.GlobalTracer;

public class DDFilter implements Filter {

	private NormalDistribution nd = new NormalDistribution(70, 15);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		Span span = GlobalTracer.get().activeSpan();
		if (span instanceof MutableSpan) {
			MutableSpan mSpan = (MutableSpan) span;
			if (request instanceof HttpServletRequest) {
				HttpServletRequest httpRequest = (HttpServletRequest) request;
				String operation = httpRequest.getHeader("operation");
				if (operation != null) {
					mSpan.setOperationName(operation);
				}
			}
		}
		try {
			Thread.sleep((long) nd.sample());
			chain.doFilter(request, response);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {
	}

}
