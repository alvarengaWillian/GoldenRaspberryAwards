package com.awards.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieRequestDTO {

	private Long id;

	@NotBlank(message = "Titulo não pode ser nulo")
	private String title;

	@NotBlank(message = "Nome(s) do(s) Estudio(s) não podem ser nulo")
	private String studios;

	@NotBlank(message = "Nome(s) do(s) Produtores(s) não podem ser nulo")
	private String producers;

	@NotBlank(message = "Ano do filme não pode ser nulo")
	private String year;

	@Builder.Default
	private Boolean winner = false;

}
