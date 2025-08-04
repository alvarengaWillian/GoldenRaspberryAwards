package com.awards.repository.specification;

import com.awards.dto.MovieFilters;
import com.awards.model.entities.Movie;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import jakarta.persistence.criteria.Predicate;

public class MovieSpecification {
	public static Specification<Movie> filterMovies(MovieFilters filters) {
		return (root, query, builder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (Objects.nonNull(filters.getTitle())) {
				predicates.add(builder.like(root.get("title"), filters.getTitle()));
			}

			if (Objects.nonNull(filters.getProducer())) {
				predicates.add(builder.like(root.get("producers"), filters.getProducer()));
			}

			if (Objects.nonNull(filters.getStudio())) {
				predicates.add(builder.like(root.get("studio"), filters.getStudio()));
			}

			if (Objects.nonNull(filters.getOnlyWinners()) && filters.getOnlyWinners().equals(Boolean.TRUE)) {
				predicates.add(builder.isTrue(root.get("winner")));
			}

			if (Objects.nonNull(filters.getSpecificYear())) {
				predicates.add(builder.equal(root.get("year"), filters.getSpecificYear()));
			}

			if (Objects.nonNull(filters.getGreaterThanYear())) {
				predicates.add(builder.greaterThanOrEqualTo(root.get("year"), filters.getGreaterThanYear()));
			}

			if (Objects.nonNull(filters.getLessThanYear())) {
				predicates.add(builder.lessThanOrEqualTo(root.get("year"), filters.getLessThanYear()));
			}

			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
}
