package com.awards.util;
import com.awards.dto.MovieItemCSV;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileCSV extends FileAbstractFactory {

	List<List<String>> items;

	@Override
	void readFile (Path filePath) {	}

	@Override
	public void readFile (Path csvPath, String fileDelimiter) {
		try {
			items = Files.readAllLines(Paths.get(csvPath.toUri()))
					.stream()
					.map(line -> Arrays.asList(line.split(fileDelimiter)))
					.toList();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <T> List<T> getRecords (Class<T> type) {
		ObjectMapper mapper = new ObjectMapper();

		return items
				.stream()
				.skip(1)
				.map(item -> {
					Map<String, Object> map = new HashMap<>();
					List<String> header = items.getFirst();

					for (int i = 0; i < header.size(); i++) {
						if (header.get(i).equals("winner")) {
							map.put(header.get(i), i < item.size() ? item.get(i).equals("yes") : "");
							continue;
						}

						map.put(header.get(i), i < item.size() ? item.get(i) : "");
					}

					return mapper.convertValue(map, type);
				})
				.toList();
	}
}
