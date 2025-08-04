package com.awards.controller;

import com.awards.dto.MovieFilters;
import com.awards.dto.MoviePartialUpdateDTO;
import com.awards.dto.MovieRequestDTO;
import com.awards.model.entities.Movie;
import com.awards.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@Validated
public class MovieController {

	private final MovieService movieService;

	public MovieController (MovieService movieService) {
		this.movieService = movieService;
	}

	@Operation(
			summary = "Consultar todos os filmes",
			description = "Endpoint para consultar todos os filmes",
			tags = "Filmes"
	)
	@GetMapping
	public List<Movie> getAllMoviesFiltered (MovieFilters filters) {
		return movieService.getAllMovies(filters);
	}

	@Operation(
			summary = "Criação de filme",
			description = "Endpoint para realizar a criação de um filme",
			tags = "Filmes"
	)
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Movie> createMovie (@RequestBody @Valid MovieRequestDTO request) {
		Movie movie = movieService.createMovie(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(movie);
	}

	@Operation(
			summary = "Deletar filme",
			description = "Endpoint para deleção de um filme",
			tags = "Filmes"
	)
	@DeleteMapping("/{movieId}")
	public ResponseEntity<Void> deleteMovie (@PathVariable Long movieId) {
		movieService.deleteMovie(movieId);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@Operation(
			summary = "Update filme",
			description = "Endpoint para realizar update completo, todos os campos de um filme",
			tags = "Filmes"
	)
	@PutMapping("/{movieId}")
	public ResponseEntity<Movie> updateMovie (@PathVariable Long movieId, @RequestBody MovieRequestDTO movieToUpdate) {
		return ResponseEntity.ok(movieService.updateMovie(movieId, movieToUpdate));
	}

	@Operation(
			summary = "Update Parcial filme",
			description = "Endpoint para realizar update parcial de um filme",
			tags = "Filmes"
	)
	@PatchMapping("/{movieId}")
	public ResponseEntity<Movie> updatePartialMovie (@PathVariable Long movieId, @RequestBody MoviePartialUpdateDTO movieToUpdate) {
		return ResponseEntity.ok(movieService.updatePartialMovie(movieId, movieToUpdate));
	}

}
