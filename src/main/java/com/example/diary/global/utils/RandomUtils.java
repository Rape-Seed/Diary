package com.example.diary.global.utils;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class RandomUtils {
    private static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String lower = upper.toLowerCase(Locale.ROOT);
    private static final String digits = "0123456789";
    private static final String alphanum = upper + lower + digits;
    private static final int DEFAULT_LENGTH = 21;
    private final Random random;
    private final char[] symbols;
    private final char[] buf;

    private RandomUtils() {
        this(DEFAULT_LENGTH);
    }

    private RandomUtils(int length) {
        this(length, new SecureRandom());
    }

    private RandomUtils(int length, Random random) {
        this(length, random, alphanum);
    }

    private RandomUtils(int length, Random random, String symbols) {
        if (length < 1) {
            throw new IllegalArgumentException();
        }
        if (symbols.length() < 2) {
            throw new IllegalArgumentException();
        }
        this.random = Objects.requireNonNull(random);
        this.symbols = symbols.toCharArray();
        this.buf = new char[length];
    }

    private String nextString() {
        for (int idx = 0; idx < buf.length; ++idx) {
            buf[idx] = symbols[random.nextInt(symbols.length)];
        }
        return new String(buf);
    }

    public static String make() {
        return new RandomUtils().nextString();
    }

    public static String make(int length) {
        return new RandomUtils(length).nextString();
    }

    public static String make(int length, Random random) {
        return new RandomUtils(length, random).nextString();
    }

    public static String make(int length, Random random, String symbols) {
        return new RandomUtils(length, random, symbols).nextString();
    }

}
