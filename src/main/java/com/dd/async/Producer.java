package com.dd.async;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import com.dd.Toolbox;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import io.opentracing.propagation.TextMapAdapter;
import io.opentracing.util.GlobalTracer;

public class Producer implements Runnable {

	private final BlockingQueue<JobWrapper> queue;

	public Producer(BlockingQueue<JobWrapper> queue) {
		this.queue = queue;
	}

	public void run() {
		for (int i = 0; i < 3; i++) {
			Tracer tracer = GlobalTracer.get();
			Span span = tracer.buildSpan("Produce job").start();
			try (Scope scope = tracer.scopeManager().activate(span)) {
				// Populates the context object with the current span context.
				Map<String, String> context = new HashMap<String, String>();
				tracer.inject(span.context(), Format.Builtin.TEXT_MAP, new TextMapAdapter(context));

				try {
					Toolbox.sleep();
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
