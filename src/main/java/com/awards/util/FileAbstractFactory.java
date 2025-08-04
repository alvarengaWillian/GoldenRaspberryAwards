package com.awards.util;

import java.nio.file.Path;
import java.util.List;

abstract class FileAbstractFactory {

	abstract void readFile (Path filePath);
	abstract void readFile (Path filePath, String fileDelimiter);
	abstract <T> List<T> getRecords (Class<T> type);
}
