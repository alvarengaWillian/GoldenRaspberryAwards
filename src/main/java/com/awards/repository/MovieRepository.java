package com.awards.repository;

import com.awards.dto.AwardResponseDTO;
import com.awards.model.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {

	@Query(nativeQuery = true, value = """
		 (
			SELECT *
			FROM (
				SELECT\s
					movie.producers as producer,
					CAST(movie.movie_year AS INT) AS previousWin,
					LEAD(CAST(movie.movie_year AS INT)) OVER ( PARTITION BY producers ORDER BY CAST(movie_year AS INT) ) AS followingWin,
					ABS(LEAD(CAST(movie.movie_year AS INT)) OVER ( PARTITION BY producers ORDER BY CAST(movie_year AS INT) ) - CAST(movie.movie_year AS INT)) AS interval_years
				FROM movie
				WHERE winner = TRUE
			) AS consecutive_wins
			WHERE followingWin IS NOT NULL
			ORDER BY previousWin
    	 );
	""")
	List<AwardResponseDTO> findMaxAndMinConsecutiveWinner();


	@Query(nativeQuery = true, value = """
		 (
			SELECT *
			FROM (
				SELECT\s
					movie.producers as producer,
					CAST(movie.movie_year AS INT) AS previousWin,
					LEAD(CAST(movie.movie_year AS INT)) OVER ( PARTITION BY producers ORDER BY CAST(movie_year AS INT) ) AS followingWin,
					ABS(LEAD(CAST(movie.movie_year AS INT)) OVER ( PARTITION BY producers ORDER BY CAST(movie_year AS INT) ) - CAST(movie.movie_year AS INT)) AS interval_years
				FROM movie
				WHERE winner = TRUE
			) AS consecutive_wins
			WHERE followingWin IS NOT NULL AND interval_years = (
				SELECT MIN(interval_years)
				FROM (
					SELECT\s
						LEAD(CAST(movie_year AS INT)) OVER (PARTITION BY producers ORDER BY CAST(movie_year AS INT)) - CAST(movie_year AS INT) AS interval_years
					FROM movie
					WHERE winner = TRUE
				) as all_intervals
			)
    	 );
	""")
	List<AwardResponseDTO> findMinConsecutiveWinner();


	@Query(nativeQuery = true, value = """
		 (
			SELECT *
			FROM (
				SELECT\s
					movie.producers as producer,
					CAST(movie.movie_year AS INT) AS previousWin,
					LEAD(CAST(movie.movie_year AS INT)) OVER ( PARTITION BY producers ORDER BY CAST(movie_year AS INT) ) AS followingWin,
					ABS(LEAD(CAST(movie.movie_year AS INT)) OVER ( PARTITION BY producers ORDER BY CAST(movie_year AS INT) ) - CAST(movie.movie_year AS INT)) AS interval_years
				FROM movie
				WHERE winner = TRUE
			) AS consecutive_wins
			WHERE followingWin IS NOT NULL AND interval_years = (
				SELECT MAX(interval_years)
				FROM (
					SELECT\s
						LEAD(CAST(movie_year AS INT)) OVER (PARTITION BY producers ORDER BY CAST(movie_year AS INT)) - CAST(movie_year AS INT) AS interval_years
					FROM movie
					WHERE winner = TRUE
				) as all_intervals
			)
    	 );
	""")
	List<AwardResponseDTO> findMaxConsecutiveWinner();




}
