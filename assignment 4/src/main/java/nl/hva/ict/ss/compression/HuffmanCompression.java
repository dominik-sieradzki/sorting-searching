package nl.hva.ict.ss.compression;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class HuffmanCompression {
    private final String text;
    private static List<String> textInCode; // It will be populated with character frequencies in processText();
    private Node root;
    private static List<String> codes;
    
    public HuffmanCompression(String text) {
        this.text = text;
        treeSetup();
    }

    public HuffmanCompression(InputStream input) {
        Scanner sc = new Scanner(input);
        sc.useDelimiter("\\Z"); // EOF marker
        text = sc.next();
        treeSetup();
    }
    
    
    private HashMap<Character, Integer> frequencyTable(){
        //Original code author: https://stackoverflow.com/a/6712708 . Implementation: Dominik Sieradzki, 500777733
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            Integer val = map.get(c);
            if (val != null) {
                map.put(c, val + 1);
            }
            else {
               map.put(c, 1);
           }
        }
    return map;
    }
    
    private PriorityQueue<Node> PQsetup(HashMap<Character, Integer> payload){
        NodeComparator cmp = new NodeComparator();
        PriorityQueue<Node> pq = new PriorityQueue(payload.size(), cmp);
        for(HashMap.Entry<Character, Integer> entry: payload.entrySet()){
            Node n = new Node(entry.getValue(), entry.getKey());
            pq.add(n);
        }
        return pq;
    }
    
    private PriorityQueue<Node> treeSetup(){
        PriorityQueue<Node> pq = PQsetup(frequencyTable());
        while(pq.size() > 1){
            Node n1 = pq.peek(); pq.poll();
            Node n2 = pq.peek(); pq.poll();
            Node interval = new Node(n1, n2);
            pq.add(interval);
            root = interval;
        }
        return pq;
    }

    /**
     * Returns the compression ratio assuming that every characters in the text uses 8 bits.
     * @return the compression ratio.
     */
    public double getCompressionRatio() {
        double textValue = text.length()*8;//*8, because each char is 8 bits
        double codeValue = getTextInCode(root).length();
        return codeValue/textValue;
    }
    /**
     * Returns the texting text
     * @param n
     * @return the text in Huffman code
     */
    private String getTextInCode(Node n){
        textInCode = new ArrayList();
        String payload = "";
        char[] txt = text.toCharArray();
        for (char c : txt){
            charSniff(n, c, "");
        } 
        payload = String.join("", textInCode);
        textInCode.clear();
        return payload;
    }
    /**
     * populates static textInCode array via recursive node tree traversal. It assumes that 
     * each character is unique in the tree.
     * @param n - the node
     * @param c - the searched character
     * @param i - the code for character
     */
    private static void charSniff(Node n, char c, String i){
        if (n.isLeaf()) { 
            if(n.getCharacter() == c) textInCode.add(i);
            return; 
        } 
        charSniff(n.getLeft(), c, i + "0"); 
        charSniff(n.getRight(), c, i + "1"); 
    }
    

    /**
     * Returns the root of the compression tree.
     * @return the root of the compression tree.
     */
    Node getCompressionTree() {
        return root;
    }
    
    /**
     * Recursive method to be used by getCodes() and populace "codes" element
     * @param root - a node currently being examined
     * @param s - The string that represents the traversal from root to current node.
     */
    //Source: https://www.geeksforgeeks.org/huffman-coding-greedy-algo-3/
    public static void printCode(Node root, String s) 
    { 
        // codes is an array list. It will later be used to populate getCodes string[].
        if (root.isLeaf()) { 
            codes.add("'"+root.getCharacter()+"'"+" -> "+ ""+s);
            return; 
        } 
        printCode(root.getLeft(), s + "0"); 
        printCode(root.getRight(), s + "1"); 
    } 
    /**
     * Returns a list with the character and the code that is used to encode it.
     * The format per entry is: "'char' -> code"
     * For "aba" this would result in: ["'b' -> 0", "'a' -> 1"]
     * And for "cacbcac" this would result in: ["'b' -> 00", "'a' -> 01", "'c' -> 1"]
     * @return the Huffman codes
     */
    String[] getCodes() {
        codes = new ArrayList<>();
        printCode(root, "");
        String[] result = new String[codes.size()];
        for(int i = 0; i < codes.size(); i++){
            result[i] = codes.get(i);
        }
        codes.clear();
        return result;
    }
}
