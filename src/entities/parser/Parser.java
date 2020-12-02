package entities.parser;

import entities.grammar.Grammar;
import entities.grammar.production;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Parser {
    private Grammar grammar;
    private ArrayList<F> firsts = new ArrayList<>();
    private ArrayList<F> follows = new ArrayList<>();

    public ArrayList<String> getFirsts(String X) {
        for(F f: firsts)
            if(f.getSymbol().equals(X))
                return (ArrayList<String>) f.getSet().stream().distinct().collect(Collectors.toList());;
         return null;
    }
    public ArrayList<String> getFollows(String X) {
        for(F f: follows)
            if(f.getSymbol().equals(X))
                return (ArrayList<String>) f.getSet().stream().distinct().collect(Collectors.toList());;
        return null;
    }
     public void add(String s, String tba){
         for (F f : firsts)
             if (f.getSymbol().equals(s)) {
                 f.add(tba);
                    break;
             }
     }
    public void addF(String s, String tba){
        for (F f : follows)
            if (f.getSymbol().equals(s)) {
                f.add(tba);
                break;
            }
    }

    public Parser(Grammar grammar) {
        this.grammar = grammar;
        initFirst();
        initFollow();
    }

    private void initFollow() {
        String S = grammar.getStartSymbol();
        F f = new F(S);
        f.add("$");
        follows.add(f);
        for (String n: grammar.getN())
            if(!n.equals(S))
            {
                F f1 = new F(n);
                follows.add(f1);
            }
        for(String n: grammar.getN())
            getFollow(n);

    }

    private ArrayList<String> getAll(production p) {
        ArrayList<String> result = new ArrayList<>();
        for(String s: p.getSecond()){
            String[] tokens = s.split(" ");
            result.addAll(Arrays.asList(tokens));
        }
        return result;
    }

    private void getFollow(String n) {
        ArrayList<production> productions = grammar.productionContaining(n);
        for(production p: productions)
        {
            ArrayList<String> items = getAll(p);
                if(items.contains(n)) {
                    int index = items.indexOf(n);
                    index++;
                    if (index == items.size()) {
                        getFollow(p.getFirst());
                        for (String s: getFF(p.getFirst()).getSet())
                            addF(n,s);
                    }
                    else {
                        ArrayList<ArrayList<String>> temp = new ArrayList<>();
                        for( int k = index; k < items.size(); k++) {
                            String next = items.get(k);
                            ArrayList<String> copy = new ArrayList<>(getF(next).getSet());
                            temp.add(copy);
                        }
                        ArrayList<String> result = concat(temp);
                        if (!result.contains("#"))
                            for(String s: result)
                                addF(n, s);
                        else {
                            result.remove("#");
                            for(String s: result)
                                addF(n, s);
                            getFollow(p.getFirst());
                            for (String s: getFF(p.getFirst()).getSet())
                                addF(n,s);
                        }
                    }
                }
        }

    }

    void initFirst() {
        ArrayList<String> terminals = this.grammar.getTerminal();
        ArrayList<String> N = this.grammar.getN();

        for(String t: terminals) {
            F f = new F(t);
            f.add(t);
            firsts.add(f);
        }
        for(String n: N) {
            F f = new F(n);
            firsts.add(f);
        }
        for (String n: N)
            getFirst(n);
    }


    private void getFirst(String n) {
        ArrayList<ArrayList<String>> temp = new ArrayList<>();
        production p = grammar.findProduction(n);
        for (String pr: p.getSecond()) {
            String[] tokens = pr.split(" ");
            int index = 0;
            for (String t: tokens) {
                index++;
                if(grammar.getTerminal().contains(t)){
                    if(index <= 1)
                        add(n,t);
                    ArrayList<String> elem = new ArrayList<>();
                    elem.add(t);
                    temp.add(elem);
                }
                else {
                    getFirst(t);
                    ArrayList<String> setT = getF(t).getSet();
                    temp.add(setT);
                }

            }
        }
        ArrayList<String> result = concat(temp);
        for (String s: result)
            add(n,s);

    }

    private ArrayList<String> concat(ArrayList<ArrayList<String>> temp) {
        if (temp.size() == 1)
            return temp.get(0);
        ArrayList<String> result = new ArrayList<>();
        boolean check = true;

        for (int i = 0; i < temp.size() - 1; i++)
            for(int j = 0; j < temp.size(); j++)
            {
                if (!temp.get(i).contains("#") || !temp.get(j).contains("#"))
                    check = false;
                for (String s1: temp.get(i))
                    for(String s2: temp.get(j))
                        if(s1.equals("#") && !s2.equals("#"))
                            result.add(s2);
                        else if (!s1.equals("#"))
                            result.add(s1);
            }
        if(check)
            result.add("#");
        return result;
    }

    public F getF(String s) {
        for(F f: firsts)
            if(f.getSymbol().equals(s))
                return f;
        return null;
    }

    public F getFF(String s) {
        for(F f: follows)
            if(f.getSymbol().equals(s))
                return f;
        return null;
    }


}
