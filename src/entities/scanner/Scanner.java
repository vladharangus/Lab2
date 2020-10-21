package entities.scanner;

import entities.symbolTable.SymbolTable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Scanner {
    private String program;
    private SymbolTable symbolTable;
    private ArrayList<Pair<String, Integer>> pif = new ArrayList<Pair<String, Integer>>();
    private ArrayList<String> operators = new ArrayList<>();
    private ArrayList<String> separators = new ArrayList<>();
    private ArrayList<String> reservedWords = new ArrayList<>();

    public Scanner(String program, SymbolTable symbolTable, ArrayList<String> operators, ArrayList<String> separators, ArrayList<String> reservedWords) {
        this.program = program;
        this.symbolTable = symbolTable;
        this.operators = operators;
        this.separators = separators;
        this.reservedWords = reservedWords;
    }

    public void scanProgram() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(program));
            String line = bufferedReader.readLine();
            while (line != null) {
                int index = 0;
                while (index < line.length()){
                    //Detecting the token
                    String token = String.valueOf(line.charAt(index));
                    //Special case for  == => =<
                    if (token.equals("=") && index + 1 < line.length()) {
                        String next = String.valueOf(line.charAt(index + 1));
                        if (next.equals("="))
                            token = token + next;
                            index++;

                    }
                    else if (token.equals("<") || token.equals(">")){
                        String next = String.valueOf(line.charAt(index + 1));
                        if (next.equals("="))
                            token = token + next;
                        index++;
                    }


                    if (operators.contains(token) || reservedWords.contains(token) || separators.contains(token))
                    {
                        pif.add(new Pair<>(token, -1));
                        index++;
                    }

                    else {
                        if (token.matches("^[a-z]([a-zA-Z][0-0])*") ) {

                            int pos = symbolTable.search(symbolTable.root,token).position;
                            pif.add(new Pair<>(token, pos));
                        }
                        else
                        {
                            System.out.println("Lexical error");
                        }
                    }

                }


                line = bufferedReader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
