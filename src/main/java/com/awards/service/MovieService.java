package com.awards.service;

import com.awards.dto.MovieFilters;
import com.awards.dto.MoviePartialUpdateDTO;
import com.awards.dto.MovieRequestDTO;
import com.awards.exception.ResourceNotFoundException;
import com.awards.mapper.MovieMapper;
import com.awards.model.entities.Movie;
import com.awards.repository.MovieRepository;
import com.awards.repository.specification.MovieSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.awards.consts.Constants.MOVIE_NOT_FOUND;

@Service
public class MovieService {

	private final MovieRepository movieRepository;

	MovieService (MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	public Movie createMovie(MovieRequestDTO request) {
		Movie movie = MovieMapper.INSTANCE.requestToEntity(request);

		return movieRepository.save(movie);
	}

	public List<Movie> getAllMovies (MovieFilters filters) {
		Specification<Movie> specification = MovieSpecification.filterMovies(filters);
		return movieRepository.findAll(specification);
	}

	public Movie updateMovie (Long movieId, MovieRequestDTO movieToUpdate) {
		if (checkMovieExistsById(movieId) ) {
			Movie movie = MovieMapper.INSTANCE.requestToEntity(movieToUpdate);
			movie.setId(movieId);
			return movieRepository.save(movie);
		}

		throw new ResourceNotFoundException(MOVIE_NOT_FOUND);
	}

	public Movie updatePartialMovie (Long movieId, MoviePartialUpdateDTO moviePartial) {
		Movie movieToUpdatePartial = getMovieById(movieId);

		moviePartial.getWinner().ifPresent(movieToUpdatePartial::setWinner);
		moviePartial.getYear().ifPresent(movieToUpdatePartial::setYear);
		moviePartial.getStudios().ifPresent(movieToUpdatePartial::setStudios);
		moviePartial.getProducers().ifPresent(movieToUpdatePartial::setProducers);
		moviePartial.getTitle().ifPresent(movieToUpdatePartial::setTitle);

		return movieRepository.save(movieToUpdatePartial);
	}

	public void deleteMovie (Long movieId) {
		getMovieById(movieId);

		movieRepository.deleteById(movieId);
	}

	public Movie getMovieById (Long movieId) {
		return movieRepository
				.findById(movieId)
				.orElseThrow(() -> new ResourceNotFoundException(MOVIE_NOT_FOUND));
	}

	public boolean checkMovieExistsById (Long movieId) {
		return movieRepository
				.findById(movieId)
				.isPresent();
	}
}
