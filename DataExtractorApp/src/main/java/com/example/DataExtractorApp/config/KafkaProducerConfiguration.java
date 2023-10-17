package com.example.DataExtractorApp.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.example.DataExtractorApp.dto.ComuneDose;

@Configuration
public class KafkaProducerConfiguration {
	@Value("${spring.kafka.bootstrap-servers}")
	private String boostrapServers;

	public Map<String, Object> producerConfig() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, boostrapServers);
		return props;
	}

	@Bean
	public ProducerFactory<String, ComuneDose> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfig());
	}
	
	@Bean
	public KafkaTemplate<String, ComuneDose> kafkaTemplate(
			ProducerFactory<String, ComuneDose> producerFactory
			){
		return new KafkaTemplate<> (producerFactory);
	}
}