package main;

import entities.fa.FA;
import entities.fa.TransitionFunction;
import entities.grammar.Grammar;
import entities.grammar.production;
import entities.node.Node;
import entities.parser.Parser;
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
        Parser parser = new Parser(gr);
        ArrayList<String> firsts = parser.getFollows("Y");
        for(String s: firsts)
            System.out.println(s);
    }
}
