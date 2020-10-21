package main;

import entities.node.Node;
import entities.symbolTable.SymbolTable;

public class Main {
    public static void main(String[] args) {
        SymbolTable symbolTable = new SymbolTable();
        symbolTable.insert("c");
        symbolTable.insert("d");
        symbolTable.insert("a");
        symbolTable.insert("b");

        symbolTable.inorder();

        Node found = symbolTable.search(symbolTable.root, "d");
        System.out.println(found.position);
    }
}
