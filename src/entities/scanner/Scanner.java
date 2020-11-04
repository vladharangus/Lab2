package entities.scanner;

import entities.node.Node;
import entities.symbolTable.SymbolTable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

public class Scanner {
    private final String program;
    private final SymbolTable symbolTable;
    private final ArrayList<Pair<String, Integer>> pif = new ArrayList<Pair<String, Integer>>();

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    private final ArrayList<String> operators = new ArrayList<>(Arrays.asList("+", "-", "*", "/" , "<" , ">" , "=", "==", "<=", ">=", "%"));
    private final ArrayList<String> separators = new ArrayList<>(Arrays.asList("[", "]", "}", "{", ";", " ", ")", "("));

    public ArrayList<Pair<String, Integer>> getPif() {
        return pif;
    }

    private final ArrayList<String> reservedWords = new ArrayList<>(Arrays.asList("if", "else", "then", "for", "execute", "const", "array", "read", "print", "number", "string"));

    public Scanner(String program, SymbolTable symbolTable) {
        this.program = program;
        this.symbolTable = symbolTable;
    }

    public void genPIF(String token, String Code) {
        Node n = symbolTable.search(symbolTable.root,token);
        if(n != null)
        {
            int pos = n.position;
            pif.add(new Pair<>(Code, pos));
        }
        else {
            symbolTable.insert(token);
            int pos = symbolTable.search(symbolTable.root,token).position;
            pif.add(new Pair<>(Code, pos));
        }
    }

    public void scanProgram() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(program));
            String line = bufferedReader.readLine();
            int indexLine = 0;
            while (line != null) {
                indexLine++;
                String copyLine = line;// we make a copy of the current line
                //line = line.replaceAll("\\s+","");
                //we split the line by separators in order to get identifiers, constants, reserved words or operators
                String[] tokens = line.split(" ");
                for(int i = 0; i < tokens.length; i++)
                {
                    //System.out.println(token);
                    String token = tokens[i];
                    if (operators.contains(token) || reservedWords.contains(token) || separators.contains(token))
                    {
                        if (!token.equals(" "))
                            pif.add(new Pair<>(token, -1));
                    }
                    else {
                        if (token.matches("^[a-z][a-zA-Z0-9]*") || token.matches("[+-]?[1-9][0-9]*") || token.matches("0") ||
                                token.matches("\"[0-9A-Za-z_]+\"")) {

                            if (token.matches("^[a-z][a-zA-Z0-9]*"))
                                genPIF(token,"Identifier");
                            if(token.matches("[+-]?[1-9][0-9]*") || token.matches("0"))
                                genPIF(token, "Number_const");
                            if (token.matches("\"[0-9A-Za-z_]+\""))
                                genPIF(token, "String_const");
                        }
                        else if(token.contains("\""))
                        {
                            StringBuilder stringBuilder = new StringBuilder(token + " ");
                            int index = i + 1;
                            if(index == tokens.length)
                                System.out.println("Line " + indexLine + ": Lexical error for token " + token);
                            else {

                                String nextToken = tokens[index];
                                while (index < tokens.length && !nextToken.contains("\"")) {
                                    stringBuilder.append(nextToken);
                                    stringBuilder.append(" ");
                                    index++;
                                    nextToken = tokens[index];
                                }
                                stringBuilder.append(tokens[index]);
                                i = index;
                                if (index == tokens.length)
                                    System.out.println("Line " + indexLine + ": Lexical error for token " + token);
                                else genPIF(stringBuilder.toString(),"String_const");
                            }
                        }
                        else
                        {
                            System.out.println("Line " + indexLine + ": Lexical error for token " + token);
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
