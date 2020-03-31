package com.datadog.kafka;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import com.datadog.Toolbox;

public class Consumer implements Runnable {

	private String bootstrapServer;
	private String topic;

	public Consumer(String bootstrapServer, String topic) {
		this.bootstrapServer = bootstrapServer;
		this.topic = topic;
	}

	@Override
	public void run() {
		Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "demo");
		props.put(ConsumerConfig.METADATA_MAX_AGE_CONFIG, "1000");
		props.put(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG, "true");
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

		try (KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props)) {
			consumer.subscribe(Arrays.asList(topic));
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(Duration.ofDays(1));
				for (ConsumerRecord<String, String> record : records) {
					Toolbox.sleep();
				}
			}
		}
	}
}
