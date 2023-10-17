package com.example.DataExtractorApp.response;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.example.DataExtractorApp.dto.ResponseBody;

public class ResponseGenerator {
	public ResponseGenerator() {
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> ResponseBody<T> generateResponse(T response, HttpStatus status, Date startCallDate) {
		return new ResponseBody(response, startCallDate, new Date(), status);
	}
}

