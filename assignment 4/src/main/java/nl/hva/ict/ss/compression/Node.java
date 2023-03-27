package nl.hva.ict.ss.compression;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

public class Node  implements Comparable<Node>, Serializable {
    private Node left;
    private Node right;
    private int weight;
    private Character character;

    public Node(int weight, Character c) {
        this.weight = weight;
        this.character = c;
    }

    public Node(Node left, Node right) {
        this.weight = left.weight + right.weight;
        this.left = left;
        this.right = right;
    }

    public void write(ObjectOutputStream output) throws IOException {
        if(this.isLeaf()){
            output.writeObject(this);return;
        }
        output.writeObject(null);
        write(output, this.left);
        write(output, this.right);

    }
    public void write(ObjectOutputStream output, Node n) throws IOException {
        if(n.isLeaf()){
            output.writeObject(n); return;
        }
        output.writeObject(null);
        write(output, n.left);
        write(output, n.right);
    }

    public static Node read(ObjectInputStream input) throws IOException, ClassNotFoundException {
        Object e = input.readObject();
        if(e == null){
            return new Node((Node)read(input),(Node)read(input));
        }
        else return (Node)e;//hopefully they're lined up right
    }

    @Override//compare based on frequency
    public int compareTo(Node o) {
        return this.weight - o.weight;
    }
    
    // check if the node is a leaf
    public boolean isLeaf() {
        assert ((left == null) && (right == null)) || ((left != null) && (right != null));
        return (left == null) && (right == null);
    }    

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public int getWeight() {
        return weight;
    }

    public Character getCharacter() {
        return character;
    }

}
