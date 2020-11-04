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
        Pair[] pif = scanner.getPif();
        System.out.println("PIF:");
        for (int i = 0; i < pif.length; i++)
            if(!(pif[i] == null))
                System.out.println("Token: " + pif[i].getL() + " Position: " + i);
        SymbolTable finalST = scanner.getSymbolTable();
        System.out.println("Symbol Table:");
        finalST.inorder();

    }
}
