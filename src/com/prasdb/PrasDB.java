package com.prasdb;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pvaradan on 3/13/16.
 */
public class PrasDB {
    /**
     *
     */
    private final static Map<String, String> PrasDB  = new HashMap<>();

    /**
     *
     */
    private final static Map<String, Integer> valueOccuranceMap  = new HashMap<>();

    /**
     *
     * @param key
     * @param value
     */
    public static void set(String key, String value) {
        PrasDB.put(key, value);
        int numOfOccurance = 1;
        if (valueOccuranceMap.get(value) != null) {
          numOfOccurance =  valueOccuranceMap.get(value) + 1;
        }
        valueOccuranceMap.put(value, numOfOccurance);
    }

    /**
     *
     * @param key
     * @return
     */
    public static String get(String key) {
        return PrasDB.get(key);
    }

    /**
     *
     * @param key
     */
    public static void unset(String key){
        PrasDB.remove(key);
    }

    /**
     *
     * @param value
     * @return
     */
    public static int numOccurancesOfValue(String value) {
        return valueOccuranceMap.get(value);
    }


}
