package com.awards.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Data
@Builder
public class MoviePartialUpdateDTO {
	private Optional<String> title = Optional.empty();
	private Optional<String> studios = Optional.empty();
	private Optional<String> producers = Optional.empty();
	private Optional<String> year = Optional.empty();
	private Optional<Boolean> winner = Optional.empty();
}
