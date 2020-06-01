package com.datadog.crossthread;

import datadog.trace.api.DDSpanTypes;
import datadog.trace.api.DDTags;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;

public class Main {

	public static void main(String[] args) throws Exception {
		final Tracer tracer = GlobalTracer.get();
		final Span span = tracer.buildSpan("ServicehandlerSpan").start();
		span.setTag(DDTags.RESOURCE_NAME, "HelloWorld");
		span.setTag(DDTags.SPAN_TYPE, DDSpanTypes.HTTP_SERVER);
		
		tracer.activateSpan(span);
		Thread.sleep(30);

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try (final Scope scope = tracer.activateSpan(span)) {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					span.finish();
				}
			}
		};
		
		Thread t = new Thread(runnable);
		t.start();
		t.join();
		
		Thread.sleep(2000);
	}

}
