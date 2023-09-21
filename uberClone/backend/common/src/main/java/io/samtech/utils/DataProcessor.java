package io.samtech.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import one.util.streamex.StreamEx;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataProcessor {

    public static String joinWihSpaceDelimiter(String... args) {
        return joins(" ", args);
    }

    private static String joins(String delimiter, String... args) {
        return StreamEx.of(args).filter(Objects::nonNull).joining(delimiter)
    }
}
