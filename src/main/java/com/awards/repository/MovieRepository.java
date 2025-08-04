package com.awards.repository;

import com.awards.dto.AwardResponseDTO;
import com.awards.model.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {

	@Query(nativeQuery = true,
			value =
			"""
				 (
					SELECT *
					FROM (
						SELECT
							previousMovie.producers as producer,
							CAST(previousMovie.movie_year AS INT) AS previousWin,
							CAST(followingMovie.movie_year AS INT) AS followingWin,
							ABS(CAST(followingMovie.movie_year AS INT) - CAST(previousMovie.movie_year AS INT)) AS interval_years
						FROM movie previousMovie
						JOIN movie followingMovie
						  ON previousMovie.producers = followingMovie.producers
						 AND previousMovie.winner = TRUE
						 AND followingMovie.winner = TRUE
						 AND CAST(previousMovie.movie_year AS INT) < CAST(followingMovie.movie_year AS INT)
					) AS intervals
				)
			"""
	)
	List<AwardResponseDTO> findMaxAndMinConsecutiveWinner();


	@Query(nativeQuery = true, value = """
		 (
        SELECT *
        FROM (
            SELECT\s
                previousMovie.producers as producer,
                CAST(previousMovie.movie_year AS INT) AS previousWin,
                CAST(followingMovie.movie_year AS INT) AS followingWin,
                ABS(CAST(followingMovie.movie_year AS INT) - CAST(previousMovie.movie_year AS INT)) AS interval_years
            FROM movie previousMovie
            JOIN movie followingMovie
              ON previousMovie.producers = followingMovie.producers
             AND previousMovie.winner = TRUE
             AND followingMovie.winner = TRUE
             AND CAST(previousMovie.movie_year AS INT) < CAST(followingMovie.movie_year AS INT)
        ) AS intervals
        WHERE interval_years = (
            SELECT MIN(ABS(CAST(followingMovie.movie_year AS INT) - CAST(previousMovie.movie_year AS INT)))
            FROM movie previousMovie
            JOIN movie followingMovie
              ON previousMovie.producers = followingMovie.producers
             AND previousMovie.winner = TRUE
             AND followingMovie.winner = TRUE
             AND CAST(previousMovie.movie_year AS INT) < CAST(followingMovie.movie_year AS INT)
        )
    )
	""")
	List<AwardResponseDTO> findMinConsecutiveWinner();


	@Query(nativeQuery = true, value = """
		 (
        SELECT *
        FROM (
            SELECT\s
                previousMovie.producers as producer,
                CAST(previousMovie.movie_year AS INT) AS previousWin,
                CAST(followingMovie.movie_year AS INT) AS followingWin,
                ABS(CAST(followingMovie.movie_year AS INT) - CAST(previousMovie.movie_year AS INT)) AS interval_years
            FROM movie previousMovie
            JOIN movie followingMovie
              ON previousMovie.producers = followingMovie.producers
             AND previousMovie.winner = TRUE
             AND followingMovie.winner = TRUE
             AND CAST(previousMovie.movie_year AS INT) < CAST(followingMovie.movie_year AS INT)
        ) AS intervals
        WHERE interval_years = (
            SELECT MAX(ABS(CAST(followingMovie.movie_year AS INT) - CAST(previousMovie.movie_year AS INT)))
            FROM movie previousMovie
            JOIN movie followingMovie
              ON previousMovie.producers = followingMovie.producers
             AND previousMovie.winner = TRUE
             AND followingMovie.winner = TRUE
             AND CAST(previousMovie.movie_year AS INT) < CAST(followingMovie.movie_year AS INT)
        )
    );  
	""")
	List<AwardResponseDTO> findMaxConsecutiveWinner();




}
