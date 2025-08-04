package com.awards.interfaces;

import java.util.Arrays;
import java.util.List;

public interface TransformFileItems {

   	<T> List<T> getRecords(final Class<T[]> type);
}
