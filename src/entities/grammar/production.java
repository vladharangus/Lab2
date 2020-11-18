package entities.grammar;

import java.util.ArrayList;

public class production {
    private String first;
    private ArrayList<String> second;

    public production(String first, ArrayList<String> second) {
        this.first = first;
        this.second = second;
    }

    public String getFirst() {
        return first;
    }

    public ArrayList<String> getSecond() {
        return second;
    }
}
