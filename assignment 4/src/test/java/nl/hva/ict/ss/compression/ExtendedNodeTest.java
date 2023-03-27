package nl.hva.ict.ss.compression;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.util.Objects;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ExtendedNodeTest extends NodeTest {
    // Put your tests here...
    HuffmanCompression compressor;
    @Test
    public void nodeIsActuallyLeaf() {
        char[] c = "cz".toCharArray();
        Node l = new Node(2, c[0]);
        Node r = new Node(4, c[1]);
        Node n = new Node(l, r);
        boolean isLeaf = n.isLeaf();
        assertFalse(isLeaf);
    }
    @Test
    public void checkComparator() {
        char[] c = "cz".toCharArray();
        Node l = new Node(2, c[0]);
        Node r = new Node(4, c[1]);
        assertTrue(l.compareTo(r) == -2);
    }
    @Test
    public void checkBiggerCompressionInputOutputBoogaloo() throws FileNotFoundException, IOException, ClassNotFoundException {
        String payload = "dracarys";
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(("dragon.bin")))) {
            compressor = new HuffmanCompression(payload);
            Node tree = compressor.getCompressionTree();
            long r = System.nanoTime();
            tree.write(output);
            long e = System.nanoTime();
            System.out.println("Time taken to save: "+(e-r));
        }

        Node tree = null;
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(("dragon.bin")))) {
            long r = System.nanoTime();
            tree = Node.read(input);
            long e = System.nanoTime();
            System.out.println("Time taken to read: "+(e-r));
        }
        catch(OptionalDataException e){
            System.out.println(e.eof);
        }
        
        assertTrue("r".equals(tree.getLeft().getLeft().getCharacter().toString()));
    }
}
