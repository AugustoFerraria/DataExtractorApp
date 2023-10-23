package com.example.DataExtractorApp.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.DataExtractorApp.dto.ProvinceIta;
import com.example.DataExtractorApp.dto.ResponseBody;
import com.example.DataExtractorApp.repository.ProvinceItaRepository;
import com.example.DataExtractorApp.response.ResponseGenerator;
import com.example.DataExtractorApp.service.DataExtractorService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/api")
@Slf4j
public class DataExtractorController {
	@Autowired
	private DataExtractorService dataExtractorService;
	@Autowired
	private ProvinceItaRepository provinceItaRepository;

	@CrossOrigin(origins = "*", allowedHeaders = "*", originPatterns = "*")
	@GetMapping(value = "/requestSendRecords")
	public ResponseEntity<?> requestSendRecords() {
		@SuppressWarnings("rawtypes")
		ResponseBody body;

		Date startCallDate = new Date();
		log.info(String.format("requestSendRecords - startCallDate %s", startCallDate));

		try {
			// Chiamata al Service per inviare i record.
			dataExtractorService.sendRecords();
			body = ResponseGenerator.generateResponse("RequestSendRecords completato correttamente.", HttpStatus.OK,
					startCallDate);
		} catch (Exception e) {
			log.error("Si è verificato un errore in requestSendRecords: " + e.getMessage());
			body = ResponseGenerator.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, startCallDate);
		}
		return ResponseEntity.ok(body);
	}

	@PostMapping("/addAllProvinces")
	public ResponseEntity<?> addAllProvinces(@RequestBody List<ProvinceIta> provinces) {
		@SuppressWarnings("rawtypes")
		ResponseBody body;

		Date startCallDate = new Date();
		log.info(String.format("addAllProvinces - startCallDate %s", startCallDate));

		try {
			for (ProvinceIta province : provinces) {
				// Chiamata al Service per salvare le province.
				dataExtractorService.saveProvince(province);
			}

			body = ResponseGenerator.generateResponse("Tutte le province sono state aggiunte correttamente.",
					HttpStatus.OK, startCallDate);
		} catch (Exception e) {
			log.error("Errore durante l'aggiunta delle province: " + e.getMessage());
			body = ResponseGenerator.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, startCallDate);
		}

		return ResponseEntity.ok(body);
	}

	@GetMapping("/getAllProvinces")
	public ResponseEntity<Map<String, String>> getAllProvinces() {
		Date startCallDate = new Date();
		log.info(String.format("getAllProvinces - startCallDate %s", startCallDate));

		try {
			// Recupero di tutte le province dalla Database.
			List<ProvinceIta> allProvinces = provinceItaRepository.findAll();
			Map<String, String> provinceMap = new HashMap<>();

			// Creazione di una mappa delle province.
			for (ProvinceIta province : allProvinces) {
				provinceMap.put(province.getCodice(), province.getNome());
			}

			return ResponseEntity.ok(provinceMap);
		} catch (Exception e) {
			log.error("Errore durante il recupero delle province: " + e.getMessage());
			return ResponseEntity.badRequest().build();
		}
	}

	@DeleteMapping("")
	public ResponseEntity<?> deleteAllProvinces() {
		@SuppressWarnings("rawtypes")
		ResponseBody body;

		Date startCallDate = new Date();
		log.info(String.format("deleteAllProvinces - startCallDate %s", startCallDate));

		try {
			// Eliminazione di tutte le province dalla Database.
			provinceItaRepository.deleteAll();

			body = ResponseGenerator.generateResponse("Tutte le province sono state eliminate con successo.",
					HttpStatus.OK, startCallDate);
		} catch (Exception e) {
			log.error("Errore durante l'eliminazione delle province: " + e.getMessage());
			body = ResponseGenerator.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, startCallDate);
		}

		return ResponseEntity.ok(body);
	}

	/*
	 Per caricare il database Postgres utilizza questo URL "http://localhost:8080/addAllProvinces" con il Header POST e il body
	 "[
  {"codice": "AG", "nome": "Agrigento"},
  {"codice": "AL", "nome": "Alessandria"},
  {"codice": "AN", "nome": "Ancona"},
  {"codice": "AO", "nome": "Aosta"},
  {"codice": "AP", "nome": "Ascoli Piceno"},
  {"codice": "AQ", "nome": "L Aquila"},
  {"codice": "AR", "nome": "Arezzo"},
  {"codice": "AT", "nome": "Asti"},
  {"codice": "AV", "nome": "Avellino"},
  {"codice": "BA", "nome": "Bari"},
  {"codice": "BG", "nome": "Bergamo"},
  {"codice": "BI", "nome": "Biella"},
  {"codice": "BL", "nome": "Belluno"},
  {"codice": "BN", "nome": "Benevento"},
  {"codice": "BO", "nome": "Bologna"},
  {"codice": "BR", "nome": "Brindisi"},
  {"codice": "BS", "nome": "Brescia"},
  {"codice": "BT", "nome": "Barletta-Andria-Trani"},
  {"codice": "BZ", "nome": "Bolzano/Bozen"},
  {"codice": "CA", "nome": "Cagliari"},
  {"codice": "CB", "nome": "Campobasso"},
  {"codice": "CE", "nome": "Caserta"},
  {"codice": "CH", "nome": "Chieti"},
  {"codice": "CL", "nome": "Caltanissetta"},
  {"codice": "CN", "nome": "Cuneo"},
  {"codice": "CO", "nome": "Como"},
  {"codice": "CR", "nome": "Cremona"},
  {"codice": "CS", "nome": "Cosenza"},
  {"codice": "CT", "nome": "Catania"},
  {"codice": "CZ", "nome": "Catanzaro"},
  {"codice": "EN", "nome": "Enna"},
  {"codice": "FC", "nome": "Forlì-Cesena"},
  {"codice": "FE", "nome": "Ferrara"},
  {"codice": "FG", "nome": "Foggia"},
  {"codice": "FI", "nome": "Firenze"},
  {"codice": "FM", "nome": "Fermo"},
  {"codice": "FR", "nome": "Frosinone"},
  {"codice": "GE", "nome": "Genova"},
  {"codice": "GO", "nome": "Gorizia"},
  {"codice": "GR", "nome": "Grosseto"},
  {"codice": "IM", "nome": "Imperia"},
  {"codice": "IS", "nome": "Isernia"},
  {"codice": "KR", "nome": "Crotone"},
  {"codice": "LC", "nome": "Lecco"},
  {"codice": "LE", "nome": "Lecce"},
  {"codice": "LI", "nome": "Livorno"},
  {"codice": "LO", "nome": "Lodi"},
  {"codice": "LT", "nome": "Latina"},
  {"codice": "LU", "nome": "Lucca"},
  {"codice": "MB", "nome": "Monza e della Brianza"},
  {"codice": "MC", "nome": "Macerata"},
  {"codice": "ME", "nome": "Messina"},
  {"codice": "MI", "nome": "Milano"},
  {"codice": "MN", "nome": "Mantova"},
  {"codice": "MO", "nome": "Modena"},
  {"codice": "MS", "nome": "Massa-Carrara"},
  {"codice": "MT", "nome": "Matera"},
  {"codice": "NA", "nome": "Napoli"},
  {"codice": "NO", "nome": "Novara"},
  {"codice": "NU", "nome": "Nuoro"},
  {"codice": "OR", "nome": "Oristano"},
  {"codice": "PA", "nome": "Palermo"},
  {"codice": "PC", "nome": "Piacenza"},
  {"codice": "PD", "nome": "Padova"},
  {"codice": "PE", "nome": "Pescara"},
  {"codice": "PG", "nome": "Perugia"},
  {"codice": "PI", "nome": "Pisa"},
  {"codice": "PN", "nome": "Pordenone"},
  {"codice": "PO", "nome": "Prato"},
  {"codice": "PR", "nome": "Parma"},
  {"codice": "PT", "nome": "Pistoia"},
  {"codice": "PU", "nome": "Pesaro e Urbino"},
  {"codice": "PV", "nome": "Pavia"},
  {"codice": "PZ", "nome": "Potenza"},
  {"codice": "RA", "nome": "Ravenna"},
  {"codice": "RC", "nome": "Reggio Calabria"},
  {"codice": "RE", "nome": "Reggio nell'Emilia"},
  {"codice": "RG", "nome": "Ragusa"},
  {"codice": "RI", "nome": "Rieti"},
  {"codice": "RM", "nome": "Roma"},
  {"codice": "RN", "nome": "Rimini"},
  {"codice": "RO", "nome": "Rovigo"},
  {"codice": "SA", "nome": "Salerno"},
  {"codice": "SI", "nome": "Siena"},
  {"codice": "SO", "nome": "Sondrio"},
  {"codice": "SP", "nome": "La Spezia"},
  {"codice": "SR", "nome": "Siracusa"},
  {"codice": "SS", "nome": "Sassari"},
  {"codice": "SU", "nome": "Sud Sardegna"},
  {"codice": "SV", "nome": "Savona"},
  {"codice": "TA", "nome": "Taranto"},
  {"codice": "TE", "nome": "Teramo"},
  {"codice": "TN", "nome": "Trento"},
  {"codice": "TO", "nome": "Torino"},
  {"codice": "TP", "nome": "Trapani"},
  {"codice": "TR", "nome": "Terni"},
  {"codice": "TS", "nome": "Trieste"},
  {"codice": "TV", "nome": "Treviso"},
  {"codice": "UD", "nome": "Udine"},
  {"codice": "VA", "nome": "Varese"},
  {"codice": "VB", "nome": "Verbano-Cusio-Ossola"},
  {"codice": "VC", "nome": "Vercelli"},
  {"codice": "VE", "nome": "Venezia"},
  {"codice": "VI", "nome": "Vicenza"},
  {"codice": "VR", "nome": "Verona"},
  {"codice": "VT", "nome": "Viterbo"},
  {"codice": "VV", "nome": "Vibo Valentia"}
]"
	*/
}