package com.awards.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class AwardResponseDTO {
	private String producer;

	private Integer previousWin;
	private Integer followingWin;

	@JsonProperty("interval")
	private Integer intervalYears;
}
