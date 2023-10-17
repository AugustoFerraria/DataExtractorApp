package com.example.DataExtractorApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComuneDose {

	private String codice;
	private String comune;
	private String provincia;
	private String sigla;
	private int dose1;
	private int dose2;
	private int booster;
	private int richiamo;
}