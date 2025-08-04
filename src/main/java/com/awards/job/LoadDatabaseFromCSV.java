package com.awards.job;

import com.awards.dto.MovieItemCSV;
import com.awards.mapper.MovieMapper;
import com.awards.model.entities.Movie;
import com.awards.repository.MovieRepository;
import com.awards.util.FileCSV;
import com.awards.util.FileUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Component
public class LoadDatabaseFromCSV {

	public static final String CSV_DELIMITER = ",";
	private final MovieRepository repository;

	public LoadDatabaseFromCSV (MovieRepository repository) {
		this.repository = repository;
	}

	@PostConstruct
	public void init () throws NoSuchFileException {

		String CSV_PATH = "src/main/resources/static/movies.csv";

		if (Boolean.FALSE.equals(FileUtil.fileExists(CSV_PATH))) {
			throw new NoSuchFileException("O arquivo movies.csv não existe dentro dos resources");
		}


		FileCSV csv = new FileCSV();
		csv.readFile(Path.of(CSV_PATH), ";");

		List<MovieItemCSV> records = csv.getRecords(MovieItemCSV.class);

		saveAllRecordsInDatabase(records);
	}


	public void saveAllRecordsInDatabase(List<MovieItemCSV> items) {
		List<Movie> movies = getMovieEntityFromMovieCSV(items);

		repository.saveAllAndFlush(movies);
	}

	public List<Movie> getMovieEntityFromMovieCSV (List<MovieItemCSV> items) {
		return items
				.stream()
				.map(this.normalizeProducerDatabase())
				.flatMap(Collection::stream)
				.map(MovieMapper.INSTANCE::CSVtoEntity)
				.toList();
	}

	public Function<MovieItemCSV, List<MovieItemCSV>> normalizeProducerDatabase () {
		return movie ->
				Arrays.stream(
							movie.getProducers().split(",\\s+and\\s+|\\s+and\\s+|,\\s+")
						)
						.map(String::trim)
						.map(producer -> MovieMapper.INSTANCE.CSVtoEntityWithProducer(movie, producer))
						.toList();
	}



}
