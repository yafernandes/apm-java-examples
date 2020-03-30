package com.dd.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import com.dd.Toolbox;

public class Producer implements Runnable {

	private String bootstrapServer;
	private String topic;

	public Producer(String bootstrapServer, String topic) {
		this.bootstrapServer = bootstrapServer;
		this.topic = topic;
	}

	@Override
	public void run() {
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		props.put(ProducerConfig.CLIENT_ID_CONFIG, "datadog");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		while (true) {
			try (org.apache.kafka.clients.producer.Producer<String, String> producer = new KafkaProducer<String, String>(
					props)) {
				ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, "Regular payload");
				producer.send(record);
			}
			Toolbox.sleep(5000);
		}
	}

}
