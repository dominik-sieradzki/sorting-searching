package nl.hva.ict.ss.compression;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class NodeTest {
    @Test
    public void canReadWhatWasWritten() throws IOException, ClassNotFoundException {

        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(("huffman-tree.bin")))) {
            Node tree = new Node(new Node(1, 'b'), new Node(2, 'a'));
            tree.write(output);
        }

        Node tree = null;
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(("huffman-tree.bin")))) {
            tree = Node.read(input);
        }
        catch(OptionalDataException e){
            System.out.println(e.eof);
        }

        assertEquals(Character.valueOf('b'), tree.getLeft().getCharacter());
        assertEquals(Character.valueOf('a'), tree.getRight().getCharacter());
    }
}