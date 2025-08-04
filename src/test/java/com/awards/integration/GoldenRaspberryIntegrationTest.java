package com.awards.integration;


import com.awards.GoldenRaspberryApplicationTests;
import com.awards.dto.MoviePartialUpdateDTO;
import com.awards.dto.MovieRequestDTO;
import com.awards.model.entities.Movie;
import com.awards.repository.MovieRepository;
import com.awards.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GoldenRaspberryIntegrationTest extends GoldenRaspberryApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private MovieService movieService;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setup() {
		movieRepository.deleteAll();
	}

	@Test
	void shouldCreateMovie() throws Exception {
		MovieRequestDTO request = MovieRequestDTO
				.builder()
						.title("The Matrix")
						.winner(true)
						.producers("Neo")
						.studios("Warner Bros")
						.year("2001")
						.build();

		mockMvc.perform(post("/movies")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.title").value("The Matrix"))
				.andExpect(jsonPath("$.winner").value(true));
	}

	@Test
	void shouldGetAllMovies() throws Exception {
		Movie movie = Movie.builder()
				.title("Inception")
				.studios("Warner Bros")
				.producers("Emma Thomas")
				.year("2010")
				.winner(true)
				.build();

		movieRepository.save(movie);

		mockMvc.perform(get("/movies"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].title").value("Inception"));
	}

	@Test
	void shouldUpdateMovie() throws Exception {
		Movie movie = movieRepository.save(Movie.builder()
				.title("Old Title")
				.studios("Test Studio")
				.producers("Test Producer")
				.year("2000")
				.winner(false)
				.build());

		MovieRequestDTO updateRequest = MovieRequestDTO
				.builder()
				.title("Updated Title")
				.studios("Updated Title")
				.producers("Updated Producer")
				.year("2001")
				.winner(true)
				.build();

		mockMvc.perform(put("/movies/" + movie.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updateRequest)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value("Updated Title"))
				.andExpect(jsonPath("$.year").value("2001"));
	}

	@Test
	void shouldDeleteMovie() throws Exception {
		Movie movie = movieRepository.save(Movie.builder()
				.title("To Be Deleted")
				.studios("Test Studio")
				.producers("Test Producer")
				.year("2000")
				.winner(false)
				.build());

		mockMvc.perform(delete("/movies/" + movie.getId()))
				.andExpect(status().isNoContent());

		assertTrue(movieRepository.findById(movie.getId()).isEmpty());
	}

	@Test
	void shouldReturnBadRequestWhenDeleteMovieNotExists() throws Exception {
		Long movieId = 99999L;
		mockMvc.perform(delete("/movies/" + movieId))
				.andExpect(status().isBadRequest());

		assertTrue(movieRepository.findById(movieId).isEmpty());
	}

	@Test
	void shouldThrowExceptionWhenDeleteMovie() throws Exception {
		long movieId = 99999L;
		mockMvc.perform(delete("/movies/" + movieId))
				.andExpect(status().isBadRequest());

		assertTrue(movieRepository.findById(movieId).isEmpty());
	}

	@Test
	void shouldReturnBadRequestWhenUpdateMovieNotExists() throws Exception {
		long movieId = 99999L;

		MovieRequestDTO updateRequest = MovieRequestDTO
				.builder()
				.title("Updated Title")
				.studios("Updated Title")
				.producers("Updated Producer")
				.year("2001")
				.winner(true)
				.build();

		mockMvc.perform(put("/movies/" + movieId)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updateRequest))
				)
				.andExpect(status().isBadRequest());
	}

	@Test
	void shouldReturnMovieWithPartialUpdate() throws Exception {
		Movie movie = insertMovie();

		MoviePartialUpdateDTO updateRequestTitle = MoviePartialUpdateDTO
				.builder()
				.title(Optional.of("Title Updated"))
				.build();

		MoviePartialUpdateDTO updateRequestProducer = MoviePartialUpdateDTO
				.builder()
				.producers(Optional.of("Producers Updated"))
				.build();

		mockMvc.perform(patch("/movies/" + movie.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updateRequestTitle))
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value("Title Updated"))
				.andExpect(jsonPath("$.producers").value("The Producer Test"));


		mockMvc.perform(patch("/movies/" + movie.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updateRequestProducer))
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value("Title Updated"))
				.andExpect(jsonPath("$.producers").value("Producers Updated"));

	}



	Movie insertMovie () {
		Movie movie = Movie
				.builder()
				.title("The Movie Test")
				.producers("The Producer Test")
				.studios("The Studios Test")
				.year("2000")
				.winner(true)
				.build();


		return movieRepository.save(movie);
	}


}
