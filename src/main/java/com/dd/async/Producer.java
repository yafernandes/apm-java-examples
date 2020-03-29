package com.dd.async;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.math3.distribution.NormalDistribution;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import io.opentracing.propagation.TextMapAdapter;
import io.opentracing.util.GlobalTracer;

public class Producer implements Runnable {

	private final BlockingQueue<JobWrapper> queue;
	private final NormalDistribution nd = new NormalDistribution(70, 10);

	public Producer(BlockingQueue<JobWrapper> queue) {
		this.queue = queue;
	}

	public void run() {
		for (int i = 0; i < 10; i++) {
			Tracer tracer = GlobalTracer.get();
			// Using ignoreActiveSpan just b/c of the loop situation. Otherwise
			Span span = tracer.buildSpan("Produce job").start();
			try (Scope scope = tracer.scopeManager().activate(span)) {
				// Populates the context object with the current span context.
				Map<String, String> context = new HashMap<String, String>();
				tracer.inject(span.context(), Format.Builtin.TEXT_MAP, new TextMapAdapter(context));

				try {
					Thread.sleep((long) nd.sample());
					JobWrapper job = new JobWrapper("msg");
					job.setHeaders(context);
					queue.put(job);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} finally {
				span.finish();
			}
		}
	}

}
