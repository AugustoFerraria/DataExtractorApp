package com.example.DataExtractorApp.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaTopicConfig {

	@Autowired
	private KafkaConfig systemConfig;

	@Bean
	public KafkaAdmin admin() {
		// Configurazione dell'amministratore Kafka.
		Map<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, systemConfig.bootstrapServers);
		return new KafkaAdmin(configs);
	}

	@Bean
	public NewTopic covidDosesTopic() {
		// Definizione di un nuovo topic Kafka per le dosi di COVID-19.
		return TopicBuilder.name(systemConfig.topicName).compact().build();
	}
}
