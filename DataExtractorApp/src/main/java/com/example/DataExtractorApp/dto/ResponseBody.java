package com.example.DataExtractorApp.dto;

import java.util.Date;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseBody<T> {

	private T data;

	private Date startCallDate;
	private Date endCallDate;
	private HttpStatus status;

	public ResponseBody() {
	}

	public ResponseBody(T data, Date startCallDate, Date endCallDate, HttpStatus status) {
		this.data = data;
		this.startCallDate = startCallDate;
		this.endCallDate = endCallDate;
		this.status = status;
	}

	public ResponseBody(Date startCallDate, Date endCallDate, HttpStatus status) {
		this.startCallDate = startCallDate;
		this.endCallDate = endCallDate;
		this.status = status;
	}
}