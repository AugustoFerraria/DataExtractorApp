package com.example.DataExtractorApp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.example.DataExtractorApp.dto.ProvinceIta;
import com.example.DataExtractorApp.repository.ProvinceItaRepository;
import com.example.DataExtractorApp.service.DataExtractorService;

@SpringBootTest
@ActiveProfiles("test")
public class DataExtractorAppApplicationTests {
	@Autowired
    private DataExtractorService dataExtractorService;

    @MockBean
    private ProvinceItaRepository provinceItaRepository;

    @Test
    void testGetAllProvinces() {
        // Create test data
        ProvinceIta province1 = new ProvinceIta("AG", "Agrigento");
        ProvinceIta province2 = new ProvinceIta("AL", "Alessandria");
        List<ProvinceIta> provinceList = List.of(province1, province2);

        // Mock the behavior of the repository
        when(provinceItaRepository.findAll()).thenReturn(provinceList);

        // Perform the actual test
        List<ProvinceIta> result = dataExtractorService.getProvinces();

        // Assertions
        assertEquals(2, result.size());
        assertEquals("AG", result.get(0).getCodice());
        assertEquals("Agrigento", result.get(0).getNome());
    }
}
