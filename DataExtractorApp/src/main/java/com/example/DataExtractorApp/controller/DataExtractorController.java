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
import org.springframework.web.bind.annotation.RestController;

import com.example.DataExtractorApp.dto.ProvinceIta;
import com.example.DataExtractorApp.dto.ResponseBody;
import com.example.DataExtractorApp.repository.ProvinceItaRepository;
import com.example.DataExtractorApp.response.ResponseGenerator;
import com.example.DataExtractorApp.service.DataExtractorService;

import lombok.extern.slf4j.Slf4j;

@RestController
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
			List<ProvinceIta> allProvinces = provinceItaRepository.findAll();
			Map<String, String> provinceMap = new HashMap<>();

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
			provinceItaRepository.deleteAll();

			body = ResponseGenerator.generateResponse("Tutte le province sono state eliminate con successo.",
					HttpStatus.OK, startCallDate);
		} catch (Exception e) {
			log.error("Error while deleting provinces: " + e.getMessage());
			body = ResponseGenerator.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, startCallDate);
		}

		return ResponseEntity.ok(body);
	}

}