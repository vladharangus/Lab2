package main;

import entities.node.Node;
import entities.scanner.Pair;
import entities.scanner.Scanner;
import entities.symbolTable.SymbolTable;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        SymbolTable symbolTable = new SymbolTable();
        Scanner scanner = new Scanner("p1err.txt",symbolTable);
        scanner.scanProgram();
        ArrayList<Pair<String,Integer>> pif = scanner.getPif();
        System.out.println("PIF:");
        for (Pair p: pif)
            System.out.println("Token: " + p.getL() + " Position: " + p.getR());
        SymbolTable finalST = scanner.getSymbolTable();
        System.out.println("Symbol Table:");
        finalST.inorder();
    }
}
