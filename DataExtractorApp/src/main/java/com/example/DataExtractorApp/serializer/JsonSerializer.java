package com.example.DataExtractorApp.serializer;

import java.util.Map;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonSerializer<T> implements Serializer<T> {
	private final ObjectMapper objectMapper = new ObjectMapper();

	public JsonSerializer() {
		// Costruttore del serializzatore JSON.
	}

	public void configure(Map<String, ?> config, boolean isKey) {
		// Configurazione del serializzatore.
	}

	public byte[] serialize(String topic, T data) {
		if (data == null) {
			return null;
		}
		try {
			// Serializza l'oggetto in formato JSON.
			return objectMapper.writeValueAsBytes(data);
		} catch (Exception e) {
			throw new SerializationException("Error serializing JSON message", e);
		}
	}

	public void close() {
		// Chiusura del serializzatore.
	}
}