package com.awards.integration;


import com.awards.GoldenRaspberryApplicationTests;
import com.awards.dto.AwardResponseDTO;
import com.awards.dto.AwardsMinAndMaxResponseDTO;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
		Movie movie = insertMovie("The Movie Test", "The Producer Test", "2000", true );

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


	@Test
	void shouldReturnOneMinAndTwoMaxMovieAward() throws Exception {
		insertMovie("Swept Away", "Matthew Vaughn", "2002", true );
		insertMovie("Fantastic Four", "Matthew Vaughn", "2015", true );
		insertMovie("Test I", "Matthew Vaughn", "1980", true );
		insertMovie("Test II", "Matthew Vaughn", "2003", true );
		insertMovie("Test III", "Matthew Vaughn", "2037", true );


		ResultActions result = mockMvc.perform(get("/awards/max-and-min-winner"));

		result.andExpect(status().isOk());

		result.andExpect(jsonPath("$.min").isArray())
				.andExpect(jsonPath("$.min").isNotEmpty())
				.andExpect(jsonPath("$.min.size()").value(1))
				.andExpect(jsonPath("$.min[0].interval").value(1));



		result.andExpect(jsonPath("$.max").isArray())
				.andExpect(jsonPath("$.max").isNotEmpty())
				.andExpect(jsonPath("$.max.size()").value(2))
				.andExpect(jsonPath("$.max[0].interval").value(22))
				.andExpect(jsonPath("$.max[1].interval").value(22));
	}

	@Test
	void shouldReturnEmptyAwards() throws Exception {
		insertMovie("Test I", "Matthew Vaughn", "1980", true );
		insertMovie("Test II", "Matthew Vaughn", "2003", true );

		ResultActions result = mockMvc.perform(get("/awards/max-and-min-winner"));

		result.andExpect(status().isOk());

		result.andExpect(jsonPath("$.min").isArray())
				.andExpect(jsonPath("$.min").isEmpty());


		result.andExpect(jsonPath("$.max").isArray())
				.andExpect(jsonPath("$.max").isEmpty());
	}

	@Test
	void shouldReturnSameMovieInMinAndMaxAwards() throws Exception {
		insertMovie("Swept Away", "Matthew Vaughn", "2002", true );
		insertMovie("Fantastic Four", "Matthew Vaughn", "2015", true );

		ResultActions result = mockMvc.perform(get("/awards/max-and-min-winner"));

		result.andExpect(status().isOk());

		result.andExpect(jsonPath("$.min").isArray())
				.andExpect(jsonPath("$.min").isNotEmpty())
				.andExpect(jsonPath("$.min.size()").value(1))
				.andExpect(jsonPath("$.min[0].interval").value(13));



		result.andExpect(jsonPath("$.max").isArray())
				.andExpect(jsonPath("$.max").isNotEmpty())
				.andExpect(jsonPath("$.max.size()").value(1))
				.andExpect(jsonPath("$.max[0].interval").value(13));

		AwardsMinAndMaxResponseDTO responseDTO = objectMapper.readValue(
				result.andReturn().getResponse().getContentAsString(),
				AwardsMinAndMaxResponseDTO.class
		);

		List<AwardResponseDTO> min = responseDTO.getMin();
		List<AwardResponseDTO> max = responseDTO.getMax();

		assertEquals(min.getFirst().getProducer(), max.getFirst().getProducer());
		assertEquals(min.getFirst().getPreviousWin(), max.getFirst().getPreviousWin());
		assertEquals(min.getFirst().getFollowingWin(), max.getFirst().getFollowingWin());
	}

	@Test
	void shouldReturnFourAwardsWithIntervalWinner() throws Exception {
		insertMovie("Swept Away", "Matthew Vaughn", "2002", true );
		insertMovie("Fantastic Four", "Matthew Vaughn", "2015", true );
		insertMovie("Test I", "Matthew Vaughn", "1980", true );
		insertMovie("Test I", "Matthew Vaughn", "1981", true );
		insertMovie("Test II", "Matthew Vaughn", "2003", true );
		insertMovie("Test III", "Matthew Vaughn", "2037", true );


		mockMvc.perform(get("/awards"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$.size()").value(5))
				.andExpect(jsonPath("$[0].interval").value(1))
				.andExpect(jsonPath("$[1].interval").value(21))
				.andExpect(jsonPath("$[2].interval").value(1))
				.andExpect(jsonPath("$[3].interval").value(12))
				.andExpect(jsonPath("$[4].interval").value(22));
	}

	Movie insertMovie (String title, String producer, String year, boolean winner) {
		Movie movie = Movie
				.builder()
				.title(title)
				.producers(producer)
				.studios("The Studios Test")
				.year(year)
				.winner(winner)
				.build();


		return movieRepository.saveAndFlush(movie);
	}


}
