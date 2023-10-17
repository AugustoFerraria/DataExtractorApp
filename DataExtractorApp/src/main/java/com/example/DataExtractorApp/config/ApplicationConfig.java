package com.example.DataExtractorApp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

	@Value("${url.province}")
	public String urlProvince;

	@Value("${url.dosi}")
	public String urlDosi;

}