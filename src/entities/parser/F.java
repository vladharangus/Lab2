package entities.parser;

import java.util.ArrayList;

public class F {
    private final String symbol;
    private final ArrayList<String> set = new ArrayList<>();

    public F(String symbol) {
        this.symbol = symbol;
    }

    public void add(String s) {
        set.add(s);
    }

    public String getSymbol() {
        return symbol;
    }
    public ArrayList<String> getSet() {
        return set;
    }
}
