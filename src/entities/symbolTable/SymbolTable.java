package entities.symbolTable;

import entities.node.Node;

public class SymbolTable {
    public Node root;

    public SymbolTable() {
        root = null;
    }

     public void insert(String value) {
        root = insertRec(root, value);
    }


    Node insertRec(Node root, String value) {


        if (root == null) {
            root = new Node(value);
            return root;
        }

        if (value.compareTo(root.value) < 0)
            root.left = insertRec(root.left, value);
        else if (value.compareTo(root.value) > 0)
            root.right = insertRec(root.right, value);
        return root;

    }
    public void inorder()  {
        inorderRec(root);
    }

    void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.println("Value: " + root.value + ", Position: " + root.position);
            inorderRec(root.right);
        }
    }
    public Node search(Node root, String value)
    {

        if (root==null || root.value.compareTo(value) == 0)
            return root;


        if (root.value.compareTo(value) > 0)
            return search(root.left, value);


        return search(root.right, value);
    }
}
