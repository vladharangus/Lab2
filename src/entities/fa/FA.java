package entities.fa;

import entities.scanner.Pair;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class FA {
    private ArrayList<String> States = new ArrayList<>();
    private ArrayList<String> Alphabet = new ArrayList<>();
    private ArrayList<TransitionFunction> transitionFunctions = new ArrayList<>();
    private String initialState;
    private ArrayList<String> finalStates = new ArrayList<>();

    public FA(String filename) {
        readFA(filename);
    }

    private void readFA(String filename) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(filename));
            String line = bufferedReader.readLine();
            int indexLine = 0;
            while(line != null)
            {
                String[] tokens = line.split(" ");
                if(indexLine == 0)
                {
                    States.addAll(Arrays.asList(tokens));
                    States = (ArrayList<String>) States.stream().distinct().collect(Collectors.toList());
                }
                else if(indexLine == 1)
                {
                    Alphabet.addAll(Arrays.asList(tokens));
                    Alphabet = (ArrayList<String>) Alphabet.stream().distinct().collect(Collectors.toList());
                }
                else if(indexLine == 2)
                    initialState = tokens[0];
                else  if(indexLine == 3)
                    finalStates.addAll(Arrays.asList(tokens));
                else if (indexLine > 3)
                {
                    Pair<String, String> pair = new Pair<>(tokens[0], tokens[1]);
                    ArrayList<String> resultStates = new ArrayList<>();
                    resultStates.addAll(Arrays.asList(tokens).subList(2, tokens.length));
                    TransitionFunction ts = new TransitionFunction(pair, resultStates);
                    transitionFunctions.add(ts);
                }


                indexLine++;
                line = bufferedReader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isDeterministic()
    {
        for (TransitionFunction ts: transitionFunctions)
            if(ts.getStates().size() > 1)
                return false;
         return true;
    }

    public boolean isAccepted(String sequence) {
        if (!isDeterministic())
            return false;
        String currentState = this.initialState;
        for(int i = 0; i < sequence.length(); i++)
        {
            boolean found  = false;
            for (TransitionFunction ts: this.transitionFunctions)
            {
                if (ts.getPair().getL().equals(currentState) && ts.getPair().getR().equals(String.valueOf(sequence.charAt(i))))
                {
                    currentState = ts.getStates().get(0);
                    found = true;
                    break;
                }
            }
            if (!found)
                return false;
        }

        for (String state: this.finalStates)
            if (state.equals(currentState))
                return true;
        return false;
    }

    public ArrayList<String> getStates() {
        return States;
    }

    public void setStates(ArrayList<String> states) {
        States = states;
    }

    public ArrayList<String> getAlphabet() {
        return Alphabet;
    }

    public void setAlphabet(ArrayList<String> alphabet) {
        Alphabet = alphabet;
    }

    public ArrayList<TransitionFunction> getTransitionFunctions() {
        return transitionFunctions;
    }

    public void setTransitionFunctions(ArrayList<TransitionFunction> transitionFunctions) {
        this.transitionFunctions = transitionFunctions;
    }

    public String getInitialState() {
        return initialState;
    }

    public void setInitialState(String initialState) {
        this.initialState = initialState;
    }

    public ArrayList<String> getFinalStates() {
        return finalStates;
    }

    public void setFinalStates(ArrayList<String> finalStates) {
        this.finalStates = finalStates;
    }
}
