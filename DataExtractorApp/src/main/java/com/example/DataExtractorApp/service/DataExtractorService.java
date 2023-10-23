package com.example.DataExtractorApp.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.DataExtractorApp.config.ApplicationConfig;
import com.example.DataExtractorApp.config.KafkaConfig;
import com.example.DataExtractorApp.dto.ComuneApiDataFetcher;
import com.example.DataExtractorApp.dto.ComuneDose;
import com.example.DataExtractorApp.dto.ProvinceIta;
import com.example.DataExtractorApp.repository.ProvinceItaRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class DataExtractorService {
	@Autowired
	private KafkaConfig kafkaConfig;
	@Autowired
	private KafkaTemplate<String, ComuneDose> kafkaTemplate;
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private ProvinceItaRepository provinceItaRepository;
	@Autowired
	private ApplicationConfig applicationConfig;

	public List<ProvinceIta> getProvinces() {
		// Recupera tutte le province dalla Database.
		return provinceItaRepository.findAll();
	}

	@Transactional
	public void saveProvince(ProvinceIta province) {
		// Salva una provincia nella Database.
		entityManager.persist(province);
	}

	public List<ComuneDose> processProvincesForKafka() {
		List<ProvinceIta> provinces = getProvinces();

		// Crea una mappa di province utilizzando il nome come chiave e il codice come valore.
		Map<String, String> comuneMap = provinces.stream()
				.collect(Collectors.toMap(province -> province.getNome().toUpperCase(), ProvinceIta::getCodice));

		String url = applicationConfig.urlDosi;

		RestTemplate restTemplate = new RestTemplate();
		ComuneApiDataFetcher[] comuneAPIList = restTemplate.getForObject(url, ComuneApiDataFetcher[].class);

		return transferComuneData(comuneAPIList, comuneMap);
	}

	private List<ComuneDose> transferComuneData(ComuneApiDataFetcher[] comuneAPIData, Map<String, String> comuneMap) {
		// Trasforma i dati della API in oggetti ComuneDose.
		return Arrays.stream(comuneAPIData).map(comuneApiDataFetcher -> {
			ComuneDose comuneDose = new ComuneDose();
			comuneDose.setCodice(comuneApiDataFetcher.getCodistat_comune_dom());
			comuneDose.setComune(comuneApiDataFetcher.getComune_dom());
			comuneDose.setProvincia(comuneApiDataFetcher.getProvincia_dom());
			comuneDose.setDose1(Integer.parseInt(comuneApiDataFetcher.getTot_solo_dose_1()));
			comuneDose.setDose2(Integer.parseInt(comuneApiDataFetcher.getTot_dose_2_unica()));
			comuneDose.setBooster(Integer.parseInt(comuneApiDataFetcher.getTot_dose_addizionale_booster()));
			comuneDose.setRichiamo(Integer.parseInt(comuneApiDataFetcher.getTot_dose_richimm_rich2_()));
			comuneDose.setSigla(comuneMap.get(comuneApiDataFetcher.getProvincia_dom()));
			return comuneDose;
		}).collect(Collectors.toList());
	}

	public List<ComuneDose> sendRecords() {
		List<ComuneDose> comuneDoseList = processProvincesForKafka();

		// Invia i record al topic Kafka.
		comuneDoseList.forEach(comune -> kafkaTemplate.send(kafkaConfig.topicName, comune.getCodice(), comune));

		return comuneDoseList;
	}
}