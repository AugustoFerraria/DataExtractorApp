package com.example.DataExtractorApp.response;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.example.DataExtractorApp.dto.ResponseBody;

public class ResponseGenerator {
	public ResponseGenerator() {
	}

	// Metodo per generare una risposta basata su un oggetto di risposta generico, uno stato HTTP e date di inizio e fine.
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> ResponseBody<T> generateResponse(T response, HttpStatus status, Date startCallDate) {
		// Crea un oggetto ResponseBody contenente il risultato, le date e lo stato HTTP specificati.
		return new ResponseBody(response, startCallDate, new Date(), status);
	}
}
