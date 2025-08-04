package com.awards.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class AwardsMinAndMaxResponseDTO {
	List<AwardResponseDTO> min;
	List<AwardResponseDTO> max;
}
