package com.datadog.async;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.datadog.Toolbox;

public class DefaultASync extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Toolbox.sleep();
			JobWrapper job = new JobWrapper("msg");
			Main.queue.put(job);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		resp.setContentType("text/plain");
		resp.getWriter().println("Default");
	}

}
