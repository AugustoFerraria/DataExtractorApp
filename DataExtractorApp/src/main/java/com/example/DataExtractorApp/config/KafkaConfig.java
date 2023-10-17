package com.example.DataExtractorApp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

	@Value("${producerApplicationID}")
	public String producerApplicationID;

	@Value("${bootstrapServers}")
	public String bootstrapServers;

	@Value("${topicName}")
	public String topicName;
}