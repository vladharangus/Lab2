package main;

import entities.node.Node;
import entities.scanner.Pair;
import entities.scanner.Scanner;
import entities.symbolTable.SymbolTable;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        SymbolTable symbolTable = new SymbolTable();
        Scanner scanner = new Scanner("p1.txt",symbolTable);
        scanner.scanProgram();
        ArrayList<Pair<String,Integer>> pif = scanner.getPif();
        for (Pair p: pif)
            System.out.println(p.getL() + " " + p.getR());
    }
}
