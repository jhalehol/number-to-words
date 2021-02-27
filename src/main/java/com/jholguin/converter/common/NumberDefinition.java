package com.jholguin.converter.common;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class NumberDefinition {

    public static final int HUNDRED_UNIT_GROUP = 1;
    public static final int THOUSAND_UNIT_GROUP = 2;
    public static final int MILLION_UNIT_GROUP = 3;
    public static final int BILLION_UNIT_GROUP = 4;

    public static final Map<Integer, String> BASIC_NUMBERS = new HashMap<Integer, String>() {{
        put(0, "zero");
        put(1, "one");
        put(2, "two");
        put(3, "three");
        put(4, "four");
        put(5, "five");
        put(6, "six");
        put(7, "seven");
        put(8, "eight");
        put(9, "nine");
        put(10, "ten");
        put(11, "eleven");
        put(12, "twelve");
        put(13, "thirteen");
        put(14, "fourteen");
        put(15, "fifteen");
        put(16, "sixteen");
        put(17, "seventeen");
        put(18, "eighteen");
        put(19, "nineteen");
    }};

    public static final Map<Integer, String> TENS_NUMBERS = new HashMap<Integer, String>() {{
        put(2, "twenty");
        put(3, "thirty");
        put(4, "forty");
        put(5, "fifty");
        put(6, "sixty");
        put(7, "seventy");
        put(8, "eighty");
        put(10, "ninety");
    }};

    public static final Map<Integer, String> UNITS_DEFINITION = new HashMap<Integer, String>() {{
        put(HUNDRED_UNIT_GROUP, "hundred");
        put(THOUSAND_UNIT_GROUP, "thousand");
        put(MILLION_UNIT_GROUP, "million");
        put(BILLION_UNIT_GROUP, "billion");
    }};
}
