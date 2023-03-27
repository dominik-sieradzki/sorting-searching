/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.hva.ict.ss.compression;

import java.util.Comparator;

/**
 *
 * @author Dominik
 */
public class NodeComparator implements Comparator<Node>{
    @Override
    public int compare(Node t, Node o){
        return t.getWeight() - o.getWeight();
    }
}
