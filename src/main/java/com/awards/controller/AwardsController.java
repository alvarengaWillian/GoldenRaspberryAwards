package com.awards.controller;

import com.awards.dto.AwardResponseDTO;
import com.awards.dto.AwardsMinAndMaxResponseDTO;
import com.awards.service.AwardsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/awards")
public class AwardsController {

	private final AwardsService awardsService;


	public AwardsController (AwardsService awardsService) {
		this.awardsService = awardsService;
	}

	@Operation(
			summary = "Consultar todos os filmes premiados",
			description = "Endpoint para consultar todos os filmes premiados com intervalo entre premiação",
			tags = "Premiados"
	)
	@GetMapping
	public ResponseEntity<List<AwardResponseDTO>> getAllAwardsWithConsecutiveWinner () {
		List<AwardResponseDTO> movies = awardsService.getAwardsMovie();

		return ResponseEntity.ok(movies);
	}

	@Operation(
			summary = "Consultar todos os filmes premiados, somente premiação mais rapida e mais demorada",
			description = "Endpoint para consultar todos os filmes premiados, somente premiação mais rapida e mais demorada",
			tags = "Premiados"
	)
	@GetMapping("/max-and-min-winner")
	public ResponseEntity<AwardsMinAndMaxResponseDTO> getMinAndMAxAwardsWithConsecutiveWinner () {
		AwardsMinAndMaxResponseDTO movies = awardsService.getStructuredMinAndMaxMovie();

		return ResponseEntity.ok(movies);
	}
}
