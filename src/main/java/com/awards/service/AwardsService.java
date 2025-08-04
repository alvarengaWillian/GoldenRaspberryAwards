package com.awards.service;

import com.awards.dto.AwardResponseDTO;
import com.awards.dto.AwardsMinAndMaxResponseDTO;
import com.awards.dto.MovieFilters;
import com.awards.model.entities.Movie;
import com.awards.repository.MovieRepository;
import com.awards.repository.specification.MovieSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AwardsService {

	private final MovieRepository movieRepository;

	public AwardsService (MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	public List<AwardResponseDTO> getAwardsMovie () {
		return movieRepository.findMaxAndMinConsecutiveWinner();
	}

	public AwardsMinAndMaxResponseDTO getStructuredMinAndMaxMovie () {
		List<AwardResponseDTO> min = movieRepository.findMinConsecutiveWinner();
		List<AwardResponseDTO> max = movieRepository.findMaxConsecutiveWinner();

		return AwardsMinAndMaxResponseDTO
				.builder()
				.min(min)
				.max(max)
				.build();
	}
}
