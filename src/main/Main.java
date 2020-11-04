package main;

import entities.fa.FA;
import entities.fa.TransitionFunction;
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
       /* ArrayList<Pair<String, Integer>> pif = scanner.getPif();
        System.out.println("PIF:");
        for (Pair p: pif)
            System.out.println(p.getL() + " " + p.getR());
        SymbolTable finalST = scanner.getSymbolTable();
        System.out.println("Symbol Table:");
        finalST.inorder();*/
        FA fa = new FA("FA.txt");
        java.util.Scanner scanner1 = new java.util.Scanner(System.in);
        int option;
        while(true)
        {
            System.out.println("Choose an option:\n1.View States\n2.View alphabet\n3.View initial state\n4.View final states\n5.View Transition functions" +
                    "\n6.Check if it is deterministic\n7.Exit");
            option = scanner1.nextInt();
            if(option == 1)
            {
                System.out.println("States");
                for (String s: fa.getStates())
                    System.out.println(s);
            }
            if (option == 2)
            {
                System.out.println("Alphabet");
                for (String s: fa.getAlphabet())
                    System.out.println(s);
            }
            if (option == 3)
            {
                System.out.println("Initial State");
                System.out.println(fa.getInitialState());
            }
            if (option == 4)
            {
                System.out.println("Final States");
                for (String s: fa.getFinalStates())
                    System.out.println(s);
            }
            if (option == 5)
            {
                System.out.println("Transiton Function");
                for (TransitionFunction ts: fa.getTransitionFunctions())
                {
                    System.out.println(ts.getPair().getL() + " " + ts.getPair().getR() + ":");
                    for (String s: ts.getStates())
                        System.out.println(s);
                }
            }
            if (option == 6)
                System.out.println(fa.isDeterministic());
            if(option == 7)
                break;
        }

    }
}
