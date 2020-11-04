package entities.fa;

import entities.scanner.Pair;

import java.util.ArrayList;

public class TransitionFunction {
    private Pair<String, String> pair;
    private ArrayList<String> states = new ArrayList<>();

    @Override
    public String toString() {
        return "TransitionFunction{" +
                "pair=" + pair +
                ", states=" + states +
                '}';
    }

    public Pair<String, String> getPair() {
        return pair;
    }

    public void setPair(Pair<String, String> pair) {
        this.pair = pair;
    }

    public ArrayList<String> getStates() {
        return states;
    }

    public void setStates(ArrayList<String> states) {
        this.states = states;
    }


    public TransitionFunction(Pair<String, String> pair, ArrayList<String> states) {
        this.pair = pair;
        this.states = states;
    }
}
