package com.datadog;

import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

public class APMExamples implements Callable<Integer>{
	
	@Option(names = { "-d", "--demo" }, required = true, description = "Demo name")
	public String demo;

	@Option(names = { "-s", "--boostrap" }, description = "Bootstrap servers", defaultValue = "localhost:9092")
	public String bootstrapServers;

	@Option(names = { "-p", "--port" }, description = "Jetty port", defaultValue = "8080")
	public int port;

	@Option(names = { "-h", "--help" }, usageHelp = true, description = "Display a help message")
	private boolean helpRequested = false;

	@Command(name = "APMExamples", description = "Multiple Java APM examples")
	public static void main(String[] args) throws Exception {
		int exitCode = new CommandLine(new APMExamples()).execute(args);
		System.exit(exitCode);
	}

	@Override
	public Integer call() throws Exception {
		switch (demo.toLowerCase()) {
		case "async":
			com.datadog.async.Main.demo(port);
			break;
		case "filters":
			com.datadog.filters.Main.demo(port);
			break;
		case "kafka":
			com.datadog.kafka.Main.demo(bootstrapServers);
			break;
		case "logging":
			com.datadog.logging.Main.demo(port);
			break;
		case "tags":
			com.datadog.tags.Main.demo(port);
			break;
		default:
			System.err.println("Check https://github.com/yafernandes/apm-java-examples for possible demo values.");
			break;
		}
		return 0;
	}
}
