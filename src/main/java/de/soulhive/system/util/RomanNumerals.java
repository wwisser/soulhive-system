package de.soulhive.system.util;

import lombok.experimental.UtilityClass;

import java.util.TreeMap;

@UtilityClass
public class RomanNumerals {

    private final static TreeMap<Integer, String> INT_ROMAN_NUMERAL_MAP = new TreeMap<>();

    static {
        INT_ROMAN_NUMERAL_MAP.put(1000, "M");
        INT_ROMAN_NUMERAL_MAP.put(900, "CM");
        INT_ROMAN_NUMERAL_MAP.put(500, "D");
        INT_ROMAN_NUMERAL_MAP.put(400, "CD");
        INT_ROMAN_NUMERAL_MAP.put(100, "C");
        INT_ROMAN_NUMERAL_MAP.put(90, "XC");
        INT_ROMAN_NUMERAL_MAP.put(50, "L");
        INT_ROMAN_NUMERAL_MAP.put(40, "XL");
        INT_ROMAN_NUMERAL_MAP.put(10, "X");
        INT_ROMAN_NUMERAL_MAP.put(9, "IX");
        INT_ROMAN_NUMERAL_MAP.put(5, "V");
        INT_ROMAN_NUMERAL_MAP.put(4, "IV");
        INT_ROMAN_NUMERAL_MAP.put(1, "I");
    }

    public static String toRoman(int number) {
        int floorKey = INT_ROMAN_NUMERAL_MAP.floorKey(number);
        if (number == floorKey) {
            return INT_ROMAN_NUMERAL_MAP.get(number);
        }
        return INT_ROMAN_NUMERAL_MAP.get(floorKey) + toRoman(number - floorKey);
    }

}
