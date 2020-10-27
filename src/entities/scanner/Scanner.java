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
    private final ArrayList<String> operators = new ArrayList<>(Arrays.asList("+", "-", "*", "/" , "<" , ">" , "=", "==", "<=", ">="));
    private final ArrayList<String> separators = new ArrayList<>(Arrays.asList("[", "]", "\\}", "\\{", " ", ";", ")", "("));

    public ArrayList<Pair<String, Integer>> getPif() {
        return pif;
    }

    private final ArrayList<String> reservedWords = new ArrayList<>(Arrays.asList("if", "else", "then", "for", "execute", "const", "array", "read", "print", "number", "string"));

    public Scanner(String program, SymbolTable symbolTable) {
        this.program = program;
        this.symbolTable = symbolTable;
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
                String[] tokens = line.split("[\\[||\\(||\\)||\\;||\\s||\\{||\\}||\\]]+");
                for(int i = 0; i < tokens.length; i++)
                {
                    //System.out.println(token);
                    String token = tokens[i];
                    if (token.equals(""))
                        continue;
                    if (operators.contains(token) || reservedWords.contains(token))
                    {
                        pif.add(new Pair<>(token, -1));
                    }
                    else {
                        if (token.matches("^[a-z][a-zA-Z0-9]*") || token.matches("[+-]?[1-9][0-9]*") || token.matches("0") ||
                                token.matches("\"[0-9A-Za-z_]+\"")) {
                            Node n = symbolTable.search(symbolTable.root,token);
                            if(n != null)
                            {
                                int pos = n.position;
                                pif.add(new Pair<>(token, pos));
                            }
                            else {
                                symbolTable.insert(token);
                                int pos = symbolTable.search(symbolTable.root,token).position;
                                pif.add(new Pair<>(token, pos));
                            }
                        }
                        else if(token.contains("\""))
                        {
                            StringBuilder stringBuilder = new StringBuilder(token + " ");
                            int index = i + 1;
                            String nextToken = tokens[index];
                            while(!nextToken.contains("\"")) {
                                stringBuilder.append(nextToken);
                                stringBuilder.append(" ");
                                index++;
                                nextToken = tokens[index];
                            }
                            stringBuilder.append(tokens[index]);
                            i = index;
                            Node n = symbolTable.search(symbolTable.root,stringBuilder.toString());
                            if(n != null)
                            {
                                int pos = n.position;
                                pif.add(new Pair<>(stringBuilder.toString(), pos));
                            }
                            else {
                                symbolTable.insert(stringBuilder.toString());
                                int pos = symbolTable.search(symbolTable.root,stringBuilder.toString()).position;
                                pif.add(new Pair<>(stringBuilder.toString(), pos));
                            }
                        }
                        else
                        {
                            System.out.println(line);
                            System.out.println(token);
                            System.out.println("Line " + indexLine + ": Lexical error");
                        }
                    }
                }
                //we parse again the line looking only for separators
                int index = 0;
                while (index < copyLine.length())
                {
                    String token = String.valueOf(copyLine.charAt(index));
                    if (separators.contains(token))
                        pif.add(new Pair<>(token, -1));
                    index ++;
                }
                line = bufferedReader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
