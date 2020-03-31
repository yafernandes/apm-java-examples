package com.datadog.async;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.datadog.Toolbox;

import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import io.opentracing.propagation.TextMapAdapter;
import io.opentracing.util.GlobalTracer;

public class InstrumentedASync extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Populates the context object with the current span context.
		Map<String, String> context = new HashMap<String, String>();
		Tracer tracer = GlobalTracer.get();
		tracer.inject(tracer.activeSpan().context(), Format.Builtin.TEXT_MAP, new TextMapAdapter(context));

		try {
			Toolbox.sleep();
			JobWrapper job = new JobWrapper("msg");
			job.setHeaders(context);
			Main.queue.put(job);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		resp.setContentType("text/plain");
		resp.getWriter().println("Instrumented");
	}

}
