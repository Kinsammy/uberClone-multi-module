package io.samtech.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import one.util.streamex.StreamEx;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataProcessor {

    public static String joinWihSpaceDelimiter(String... args) {
        return joins(" ", args);
    }

    private static String joins(String delimiter, String... args) {
        return StreamEx.of(args).filter(Objects::nonNull).joining(delimiter);
    }

    public static byte[] encrypt(final String data, final String key) throws GeneralSecurityException, IOException {
        return DefaultInstance.CRYPTO.encrypt(getUTF8Bytes(data), key.toCharArray());

    }

    private static byte[] getUTF8Bytes(final String input) {
        return input.getBytes(StandardCharsets.UTF_8);
    }
}
