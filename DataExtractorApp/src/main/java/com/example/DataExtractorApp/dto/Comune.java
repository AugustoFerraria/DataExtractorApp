package com.example.DataExtractorApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comune {

	private String codice;
	private String nome;
	private String sigla;
	private String regione;
}