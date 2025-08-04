package com.awards.mapper;

import com.awards.dto.MovieItemCSV;
import com.awards.dto.MovieRequestDTO;
import com.awards.model.entities.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MovieMapper {

	MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

	@Mapping(target = "winner", expression = "java(request.getWinner() != null ? request.getWinner() : false)")
	@Mapping(target = "id", ignore = true)
	Movie requestToEntity (MovieRequestDTO request);

	@Mapping(target = "winner", expression = "java(item.getWinner() != null ? item.getWinner() : false)")
	Movie CSVtoEntity (MovieItemCSV item);

	@Mapping(target = "winner", expression = "java(item.getWinner() != null ? item.getWinner() : false)")
	@Mapping(target = "producers", source = "producer")
	MovieItemCSV CSVtoEntityWithProducer (MovieItemCSV item, String producer);
}
