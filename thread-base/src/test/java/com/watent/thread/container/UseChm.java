package com.watent.thread.container;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * CurrentHashMap putIfAbsent
 *
 */
public class UseChm {

    HashMap<String, String> hashMap = new HashMap<>();
    ConcurrentHashMap<String, String> chm = new ConcurrentHashMap<>();

    public String putIfAbsent(String key, String value) {
        int a;
        synchronized (hashMap) {
            if (hashMap.get(key) == null) {
                return hashMap.put(key, value);
            } else {
                return hashMap.get(key);
            }
        }
    }

    public String useChm(String key, String value) {
        return chm.putIfAbsent(key, value);
    }
}