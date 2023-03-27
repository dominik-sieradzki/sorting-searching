package nl.hva.ict.ss.compression;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ExtendedHuffmanComressionTest extends HuffmanCompressionTest {
    
    public void compress(String payload){
        long length = payload.length();
        long timeBefore = System.nanoTime();
        compressor = new HuffmanCompression(payload);
        long timeTaken = System.nanoTime() - timeBefore;
        System.out.println("Smaller compression. Time taken: "+timeTaken+" nanoseconds. "
                + "Length of payload: "+length+" Time per character: "
                +(timeTaken/length)+" Compression Ratio: "
                +compressor.getCompressionRatio());
    }
    public String setupFile(String name) throws FileNotFoundException{
        File file = new File(name);
        FileInputStream fis = new FileInputStream(file);
        Scanner sc = new Scanner(fis);
        sc.useDelimiter("\\Z"); // EOF marker. Ensures that the entire file is wrapped in one sc.next() call.
        return sc.next();
    }
    
    // Put your tests here...
    @Test
    public void checkBiggerCompression() {
        String payload = "hanabira ga kusuri to warau\n" + // Excerpt from Hana Furumai ( https://robotandlyrics.wordpress.com/2016/12/12/hana-furumai/ )
"me wo soraseba kareta hana no mukuro\n" +
"hanabira ga fuwari to ochiru\n" +
"yureta kokoro no suki umeru you ni\n" +
"hanabira ga kusuri to warau\n" +
"yume utsutsu no naka de mogaku watashi\n" +
"hanabira ga kururi to soyogu\n" +
"uragaeshi no kono shigusa";
        
       compress(payload);
       assertEquals(0.4935,compressor.getCompressionRatio(), 0.0001);
    }
    @Test
    public void checkSmallerCompression() {
        compress("dracarys");
        assertEquals(0.3125,compressor.getCompressionRatio(), 0.0001);
    }
    
    @Test
    public void checkLargeCompression() throws FileNotFoundException {
        compress(setupFile("tywin biography.txt"));
        assertEquals(0.57704296,compressor.getCompressionRatio(), 0.0001);
    }
    
    @Test
    public void checkHumongousCompressions() throws FileNotFoundException {
        System.out.println("276kb");
        compress(setupFile("image276.txt"));
        System.out.println("500kb");
        compress(setupFile("image500.txt"));
        System.out.println("800kb");
        compress(setupFile("image800.txt"));
        assertEquals(0.751176,compressor.getCompressionRatio(), 0.001);
    }
}
