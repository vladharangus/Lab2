package entities.parser;

import entities.grammar.Grammar;
import entities.grammar.production;
import entities.scanner.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class Parser {

    private Grammar grammar;

    private ArrayList<F> firsts = new ArrayList<>();
    private ArrayList<F> follows = new ArrayList<>();
    private Stack<String> alpha = new Stack<>();
    private Stack<String> beta = new Stack<>();
    private Stack<String> pi = new Stack<>();
    private ParseTable parseTable = new ParseTable();
    private final ArrayList<Pair<String,String>> numberedProductions = new ArrayList<>();


    public Stack<String> getAlpha() {
        return alpha;
    }

    public Stack<String> getBeta() {
        return beta;
    }

    public Stack<String> getPi() {
        return pi;
    }

    public ParseTable getParseTable() {
        return parseTable;
    }

    public Parser(Grammar grammar) {
        this.grammar = grammar;
        initFirst();
        initFollow();
        createParseTable();
    }

    /**.....................FIRST....................................*/

    public ArrayList<String> getFirsts(String X) {
        for(F f: firsts)
            if(f.getSymbol().equals(X))
                return (ArrayList<String>) f.getSet().stream().distinct().collect(Collectors.toList());;
        return null;
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
        for (String n: N){
            getF(n).setSet(getFirst(n));
        }

    }


    public ArrayList<String> getFirst(String n) {
        ArrayList<String> temp = new ArrayList<>();
        production p = grammar.findProduction(n);
        ArrayList<String> terminals = grammar.getTerminal();
        for (String pr: p.getSecond()) {
            String[] tokens = pr.split(" ");
            String t = tokens[0];
            if(t.equals("#"))
                temp.add("#");
            else if (terminals.contains(t))
                temp.add(t);
            else
                temp.addAll(getFirst(t));

        }

        return temp;
    }


    public F getF(String s) {
        for(F f: firsts)
            if(f.getSymbol().equals(s))
                return f;
        return null;
    }

    /**................FOLLOW...............*/

    private void initFollow() {

        String S = grammar.getStartSymbol();
        F fS = new F(S);
        fS.add("$");
        follows.add(fS);

        for(String n: grammar.getN())
            if(!n.equals(S))
            {
                F f = new F(n);
                follows.add(f);
            }

        for (String n: grammar.getN())
            getFollow(n, new ArrayList<>());
    }

    public void getFollow(String n, ArrayList<String> visited) {
        ArrayList<String> terminals = grammar.getTerminal();
        ArrayList<production> productions = grammar.productionContaining(n);
        for(production p: productions)
        {
            String startS = p.getFirst();
            for (String s: p.getSecond()) {
                String[] tokenstemp = s.split(" ");
                ArrayList<String> tokens = new ArrayList<>(Arrays.asList(tokenstemp));
                if (tokens.contains(n)) {
                    if(visited.contains(n))
                        continue;
                    int index = tokens.indexOf(n);
                    index++;
                    if(index == tokens.size()) {
                        visited.add(n);
                        getFollow(startS, visited);
                        for (String t: getFF(startS).getSet())
                            addF(n,t);
                    }
                    else {
                        ArrayList<ArrayList<String>> temp = new ArrayList<>();
                        for (int k = index; k < tokens.size(); k++) {
                            String next = tokens.get(k);
                            ArrayList<String> firstSet = getFirsts(next);
                            temp.add(firstSet);
                        }
                        ArrayList<String> result = concat(temp);
                        if (!result.contains("#"))
                            for (String r: result)
                                addF(n, r);
                        else {
                            result.remove("#");
                            for (String r: result)
                                addF(n, r);
                            visited.add(n);
                            getFollow(startS, visited);
                            for (String t: getFollows(startS))
                                addF(n,t);
                        }
                    }
                }

            }
        }


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

    public ArrayList<String> getFollows(String X) {
        for(F f: follows)
            if(f.getSymbol().equals(X))
                return (ArrayList<String>) f.getSet().stream().distinct().collect(Collectors.toList());;
        return null;
    }

    public void addF(String s, String tba){
        for (F f : follows)
            if (f.getSymbol().equals(s)) {
                f.add(tba);
                break;
            }
    }

    public F getFF(String s) {
        for(F f: follows)
            if(f.getSymbol().equals(s))
                return f;
        return null;
    }

    /**.......................Parsing Table..........................*/

    public void numberingProductions(){
        for(production production: grammar.getProductions()) {
            for (String second : production.getSecond()) {
                numberedProductions.add(new Pair(production.getFirst(), second));
            }
        }
    }

    public void createParseTable(){
        numberingProductions();
        ArrayList<String> columns = new ArrayList<>(grammar.getTerminal());
        columns.add("$");
        parseTable.put(new Pair<>("$", "$"), new Pair<>(new ArrayList<String>(Collections.singleton("accept")), -1));
        for(String t: grammar.getTerminal()){
            parseTable.put(new Pair<>(t, t), new Pair<>(new ArrayList<String>(Collections.singleton("pop")), -1));
        }
        for(Pair<String, String> p:numberedProductions) {
            String row = p.getL();
            String[] rhs = p.getR().split(" ");
            ArrayList<String> tokens = new ArrayList<>(Arrays.asList(rhs));
            Pair<ArrayList<String>, Integer> parseTableValue = new Pair<>(tokens, numberedProductions.indexOf(p)+1);
            for(String column: columns){
                Pair<String, String> key = new Pair<>(row, column);
                if(tokens.get(0).equals(column) && !column.equals("#"))
                    parseTable.put(key, parseTableValue);
                else if(grammar.getN().contains(tokens.get(0)) && getFirsts(tokens.get(0)).contains(column)){
                    if(!parseTable.containsKey(key)){
                        parseTable.put(key, parseTableValue);
                    }
                }
                else{
                    if (tokens.get(0).equals("#")) {
                        for (String b : getFollows(row))
                            if(!parseTable.containsKey(new Pair<>(row, b)))
                                parseTable.put(new Pair<>(row, b), parseTableValue);

                    }
                    else {
                        ArrayList<String> firsts = new ArrayList<>();
                        for (String s : tokens) {
                            if (grammar.getN().contains(s))
                                firsts.addAll(getFirsts(s));
                        }
                        if (firsts.contains("#")) {
                            for (String r : getFirsts(row)) {
                                if (r.equals("#")) {
                                    r = "$";
                                }
                                key = new Pair<>(row, r);
                                if (!parseTable.containsKey(key)) {
                                    parseTable.put(key, parseTableValue);
                                }
                            }
                        }
                    }
                }
            }

        }
    }
    /**..............................Parsing..............................*/

    private void initStacks(ArrayList<String> w) {
        alpha.clear();
        alpha.push("$");
        pushChars(w, alpha);

        beta.clear();
        beta.push("$");
        beta.push(grammar.getStartSymbol());

        pi.clear();
        pi.push("#");
    }

    private void pushChars(ArrayList<String> w, Stack<String> alpha) {
        for(int i = w.size() - 1; i >= 0; i--)
            alpha.push(w.get(i));
    }

    public boolean parse(ArrayList<String> w) {
        initStacks(w);
        boolean go = true;
        boolean result = true;

        while(go) {
            String headb = beta.peek();
            String heada = alpha.peek();

            System.out.println("alpha");
            System.out.println(alpha);

            System.out.println("beta");
            System.out.println(beta);

            System.out.println("pi");
            System.out.println(pi);

            if(headb.equals("$") && heada.equals("$"))
                return result;

            Pair<String, String> heads = new Pair<>(headb, heada);
            Pair<ArrayList<String>, Integer> parseT = parseTable.get(heads);

            if (parseT == null) {
                heads = new Pair<>(headb, "#");
                parseT = parseTable.get(heads);
                if (parseT != null) {
                    beta.pop();
                    continue;
                }
            }

            if(parseT == null) {
                go = false;
                result = false;
            }
            else {
                ArrayList<String> production = parseT.getL();
                Integer prodPos = parseT.getR();

                if(prodPos == -1 && production.get(0).equals("accept"))
                    go = false;
                else if (prodPos == -1 && production.get(0).equals("pop")) {
                    beta.pop();
                    alpha.pop();
                }
                else {
                    beta.pop();
                    if(!production.get(0).equals("#"))
                        pushChars(production,beta);
                    pi.push(prodPos.toString());
                }
            }
        }
        return result;
    }

}