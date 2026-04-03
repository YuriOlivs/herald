package com.yuriolivs.herald.herald_auth.security.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HashUtils {
    private static final String ALGORITHM = "SHA-256";

    public static String hash(String input) throws NoSuchAlgorithmException {
        byte[] bytes = toSHA256Bytes(input);
        return toBase64(bytes);
    }

    private static byte[] toSHA256Bytes(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(ALGORITHM);
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    private static String toBase64(byte[] bytes) {
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }
}
