package entities.parser;
import entities.scanner.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ParseTable {
    private Map<Pair<String, String>, Pair<ArrayList<String>, Integer>> table = new LinkedHashMap<>();


    public void put(Pair<String, String> key, Pair<ArrayList<String>, Integer> value){
        table.put(key, value);
    }

    public Pair<ArrayList<String>, Integer> get(Pair<String, String> key){
        for (Map.Entry<Pair<String, String>, Pair<ArrayList<String>, Integer>> entry : table.entrySet()) {
            if (entry.getValue() != null) {
                Pair<String, String> currentKey = entry.getKey();
                Pair<ArrayList<String>, Integer> currentValue = entry.getValue();

                if (currentKey.getL().equals(key.getL()) && currentKey.getR().equals(key.getR())) {
                    return currentValue;
                }
            }
        }

        return null;
    }

    public boolean containsKey(Pair<String, String> key) {
        boolean result = false;
        for (Pair<String, String> currentKey : table.keySet()) {
            if (currentKey.getL().equals(key.getL()) && currentKey.getR().equals(key.getR())) {
                result = true;
            }
        }

        return result;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<Pair<String, String>, Pair<ArrayList<String>, Integer>> entry : table.entrySet()) {
            if (entry.getValue() != null) {
                Pair<String, String> key = entry.getKey();
                Pair<ArrayList<String>, Integer> value = entry.getValue();

                sb.append("M[").append(key.getL()).append(",").append(key.getR()).append("] = [")
                        .append(value.getL()).append(",").append(value.getR()).append("]\n");
            }
        }

        return sb.toString();
    }

}
