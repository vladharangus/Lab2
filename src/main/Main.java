package main;

import entities.fa.FA;
import entities.fa.TransitionFunction;
import entities.grammar.Grammar;
import entities.grammar.production;
import entities.node.Node;
import entities.scanner.Pair;
import entities.scanner.Scanner;
import entities.symbolTable.SymbolTable;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        /*SymbolTable symbolTable = new SymbolTable();
        Scanner scanner = new Scanner("p1.txt",symbolTable);
        scanner.scanProgram();
        ArrayList<Pair<String, Integer>> pif = scanner.getPif();
        System.out.println("PIF:");
        for (Pair p: pif)
            System.out.println(p.getL() + " " + p.getR());
        SymbolTable finalST = scanner.getSymbolTable();
        System.out.println("Symbol Table:");
        finalST.inorder();*/
        Grammar gr = new Grammar("g1.txt");
        ArrayList<production> productions = gr.getProductions();
        for (production p: productions)
        {
            System.out.println(" ");
            System.out.println(p.getFirst());
            for(String s: p.getSecond())
                System.out.println(s);
        }
        System.out.println("Nonterminals");
        ArrayList<String> symbols = gr.getN();
        for(String s: symbols)
            System.out.println(s);



    }
}
