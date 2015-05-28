package com.epilgrim.httpFoundation;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ParameterBag {
    private Map<String, String> bag;

    public ParameterBag() {
        bag = new LinkedHashMap<String, String>();
    }

    public ParameterBag(Map<String, String> bag) {
        this.bag = bag;
    }

    public String toString()
    {
        String result = "";
        for (String key : this.bag.keySet()) {
            result = result.concat(key + "=" + this.bag.get(key) + ";");
        }

        return result;
    }

    public void set(String key, String value)
    {
        bag.put(key, value);
    }

    public String get(String key)
    {
        return bag.get(key);
    }

    public String get(String key, String defaultValue)
    {
        if (this.has(key)) {
            return bag.get(key);
        }
        return defaultValue;
    }

    public void clear()
    {
        bag.clear();
    }

    public Boolean has(String key)
    {
        return this.bag.containsKey(key);
    }

    public  Iterator<Entry<String, String>> getIterator()
    {
        return bag.entrySet().iterator();
    }
}
