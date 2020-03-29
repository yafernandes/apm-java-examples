package com.dd.async;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.math3.distribution.NormalDistribution;

public class DefaultASync extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private NormalDistribution nd = new NormalDistribution(70, 15); 

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Thread.sleep((long) nd.sample());
			JobWrapper job = new JobWrapper("msg");
			Main.queue.put(job);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		resp.setContentType("text/plain");
		resp.getWriter().println("Default");
	}

}
