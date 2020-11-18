package entities.grammar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Grammar {
    private final String filename;
    private String startSymbol;
    private final ArrayList<String> N = new ArrayList<>();
    private final ArrayList<String> terminal = new ArrayList<>();
    private final ArrayList<production> productions = new ArrayList<>();
    public Grammar(String filename) {
        this.filename = filename;
        read();
    }
    private void read() {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(filename));
            String line = bufferedReader.readLine();
            int indexLine = 0;
            while (line != null) {
                String[] tokens = line.split(";");
                if(indexLine == 0)
                {
                    this.N.addAll(Arrays.asList(tokens));
                }
                else if (indexLine == 1) {
                    this.terminal.addAll(Arrays.asList(tokens));
                }
                else if (indexLine == 2)
                    this.startSymbol = tokens[0];
                else if (indexLine > 2) {
                    String first = tokens[0];
                    ArrayList<String> second = new ArrayList<>(Arrays.asList(tokens).subList(1, tokens.length));
                    production p = new production(first, second);
                    productions.add(p);
                }
                indexLine++;
                line = bufferedReader.readLine();
            }

        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public String getStartSymbol() {
        return startSymbol;
    }

    public ArrayList<String> getN() {
        return N;
    }

    public ArrayList<String> getTerminal() {
        return terminal;
    }

    public ArrayList<production> getProductions() {
        return productions;
    }
}
