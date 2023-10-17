package com.example.DataExtractorApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComuneApiDataFetcher {

	private String codistat_comune_dom;
	private String comune_dom;
	private String provincia_dom;
	private String tot_solo_dose_1;
	private String tot_dose_2_unica;
	private String tot_dose_addizionale_booster;
	private String tot_dose_richimm_rich2_;

}