package entities.parser;

import entities.grammar.Grammar;
import entities.grammar.production;

import java.util.ArrayList;

public class Parser {
    private Grammar grammar;

    public Parser(Grammar grammar) {
        this.grammar = grammar;
    }

    public ArrayList<String> first(String X) {
        ArrayList<F> fs = new ArrayList<>();
        ArrayList<String> terminals = grammar.getTerminal();
        ArrayList<String> N = grammar.getN();
        ArrayList<production> productions = grammar.getProductions();

        for(String A : N) {
            ArrayList<String> set = new ArrayList<>();
            for(production p: productions)
                if (p.getFirst().equals(A))
                    for (String s: p.getSecond())
                    {
                        String[] tokens = s.split(" ");
                        if (terminals.contains(tokens[0]) || (tokens.length == 1 && tokens[0].equals("#")))
                            set.add(tokens[0]);
                    }
            F f = new F(0,A,set);
            fs.add(f);
        }
        int i = 0;
        boolean stop = false;
        do {
            i++;
            for (String A: N) {
                if (isComputed(i, fs)) {
                    
                }
            }

        }while(!stop);
    }

    boolean isComputed(int i, ArrayList<F> fs) {
        int nr = 0;
        for (String n: grammar.getN())
            for (F f: fs)
                if (f.getSymbol().equals(n) && f.getK() == i)
                    nr++;
         return (nr == grammar.getN().size());
    }
}
