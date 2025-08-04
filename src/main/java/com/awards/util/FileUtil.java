package com.awards.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {

	public static Boolean fileExists (String pathString) {
		Path path = Paths.get(pathString);

		return Files.exists(path);
	}
}
