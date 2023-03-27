package nl.hva.ict.ss.compression;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HuffmanCompressionTest {
    HuffmanCompression compressor;

    @Before
    public void setup() {
        compressor = new HuffmanCompression(getClass().getResourceAsStream("/edu/princeton/cs/algs4/Huffman.java"));
    }

    @Test
    public void checkWeightSimple() {
        compressor = new HuffmanCompression("aba");

        Node compressionTree = compressor.getCompressionTree();

        assertEquals(3, compressionTree.getWeight());

        Node left = compressionTree.getLeft();
        Node right = compressionTree.getRight();
        assertEquals(1, left.getWeight());
        assertEquals(2, right.getWeight());
    }

    @Test
    public void checkUniqueCharacter() {
        // Handle Linux/Mac and Windows end-of-line characters, 86 and 87 are both ok.
        int numberOfChars = compressor.getCodes().length;
        assertTrue("You appear to have some very strange end-of-line configuration on your machine!", numberOfChars == 86 || numberOfChars == 87);
    }

    @Test
    public void checkSimpleCompressionRatio() {
        compressor = new HuffmanCompression("aba");
        assertEquals(0.125,compressor.getCompressionRatio(), 0.0001);
    }

}