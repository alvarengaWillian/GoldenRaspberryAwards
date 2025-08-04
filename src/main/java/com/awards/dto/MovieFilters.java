package com.awards.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieFilters {
	private String title;
	private String producer;
	private String studio;
	private Integer specificYear;
	private Integer greaterThanYear;
	private Integer lessThanYear;
	private Boolean onlyWinners;
	private Boolean onlyMaxAndMinConsecutiveWinner;
}
