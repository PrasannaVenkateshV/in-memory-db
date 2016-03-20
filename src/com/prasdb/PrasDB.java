package com.prasdb;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;

/**
 * Created by pvaradan on 3/13/16.
 */
public class PrasDB {

    /**
     * PrasDB - The Key Value Map. considered the in-memory DB.
     */
    private final static Map<String, String> prasDBMap = new HashMap<>();

    /**
     * numEqualToMap - Map for faster computation of num of occurances of a value in the DB.
     */
    private final static Map<String, Integer> numEqualToMap  = new HashMap<>();

    /**
     * transactionStack - stack of transactions
     */
    private final static Stack<Map<String, String>> transactionStack = new Stack<>();


    /**
     * set value directly in the DB or into transaction memory based on the context
     * @param key
     * @param value
     */
    public static void set(String key, String value) {
        getMapByContext().put(key, value);
        updateNumEqualTo(value, true);
    }

    /**
     * gets the value mapped to the key, returns null if none found.
     * @param key
     * @return
     */
    public static String get(String key) {
        String value = getMapByContext().get(key);
        return value != null ? value : "NULL";
    }

    /**
     * unset a record(key, value) in the db.
     * @param key
     */
    public static void unset(String key) {
        String value = getMapByContext().get(key);
        if(value != null) {
            getMapByContext().remove(key);
            updateNumEqualTo(value, false);
        }
    }

    /**
     * getNumEqualTo - get the 'numEqualTo' value of a given string from the db.
     * @param value
     * @return
     */
    public static int getNumEqualTo(String value) {
        return numEqualToMap.get(value);
    }

    /**
     * begin the transaction
     */
    public static void beginTransaction() {
        if (transactionStack.empty()) {
            transactionStack.push(new HashMap<String, String>());
        }
        else {
            transactionStack.push(transactionStack.peek());
        }
    }

    /**
     * rollback the transaction
     */
    public static Optional<String>  rollbackTransaction() {
        if(transactionStack.empty()){
            return Optional.of("NO TRANSACTION");
        }
        transactionStack.pop();
        return Optional.empty();
    }

    /**
     * commit the transaction
     */
    public static void commitTransaction() {
        Map<String, String> transactionMap = transactionStack.pop();
        for (Map.Entry<String, String> entry : transactionMap.entrySet()) {
            set(entry.getKey(), entry.getValue());
        }
        transactionStack.clear();
    }

    /**
     * increments or decrements the numEqualTo Value of a given key.
     * @param key
     * @param isIncrement
     */
    private static void updateNumEqualTo(String key, boolean isIncrement) {
        int incrementValue = isIncrement ? 1 : -1 ;
        int numEqualTo = numEqualToMap.get(key).intValue() + incrementValue;
        if(numEqualTo <= 0) {
            numEqualToMap.remove(key);
        } else {
            numEqualToMap.put(key, numEqualTo);
        }
    }

    /**
     * identifies if the context is Transaction or atomic updates and return the right Object(Map)
     * @return
     */
    private static Map<String,String> getMapByContext() {
        // check if the transaction stack has elements, if not return the main DB Map.
        if (!transactionStack.empty()) {
            return transactionStack.peek();
        }
        return prasDBMap;
    }
}
