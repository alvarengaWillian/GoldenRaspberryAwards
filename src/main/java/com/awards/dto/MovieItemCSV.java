package com.awards.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class MovieItemCSV {

	@JsonProperty("year")
	private String year;

	@JsonProperty("title")
	private String title;

	@JsonProperty("studios")
	private String studios;

	@JsonProperty("producers")
	private String producers;

	@JsonProperty("winner")
	private Boolean winner;
}
