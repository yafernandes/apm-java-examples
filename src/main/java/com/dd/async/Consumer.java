package com.dd.async;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.math3.distribution.NormalDistribution;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import io.opentracing.propagation.TextMapAdapter;
import io.opentracing.util.GlobalTracer;

public class Consumer implements Runnable {

	private final BlockingQueue<Job> queue;
	private final NormalDistribution nd = new NormalDistribution(70, 10);

	public Consumer(BlockingQueue<Job> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Job job = queue.poll(3, TimeUnit.SECONDS);
				if (job == null)
					break;
				Tracer tracer = GlobalTracer.get();
				SpanContext parentContext = tracer.extract(Format.Builtin.TEXT_MAP,
						new TextMapAdapter(job.getContext()));

				// Sets the parent parent context as the one received with the job.
				Span span = GlobalTracer.get().buildSpan("Consume job").asChildOf(parentContext).start();
				try (Scope scope = tracer.scopeManager().activate(span)) {
					Thread.sleep((long) nd.sample());
					System.out.println(job.getPayload());
				} finally {
					span.finish();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
