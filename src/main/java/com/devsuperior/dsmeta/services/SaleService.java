package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public List<SaleSummaryDTO> getSummary(String minDate, String maxDate){
		LocalDate startDate;
		LocalDate endDate;

		if (maxDate == null) {
			endDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		} else {
			endDate = LocalDate.parse(maxDate);
		}

		if (minDate == null) {
			startDate = endDate.minusYears(1L);
		} else {
			startDate = LocalDate.parse(minDate);
		}

		if (minDate == null && maxDate == null) {
			endDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
			startDate = endDate.minusMonths(12L);
		}

		return repository.getSaleSummary(startDate, endDate);
	}
}
