package entities.node;

import org.w3c.dom.ls.LSOutput;

public class Node {
    public String value;
    public int position;
    public static int pos = 0;
    public Node left, right;

    public Node(String item) {
        value = item;
        left = right = null;
        position = pos;
        pos ++;
    }


}


