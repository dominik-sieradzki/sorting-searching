package nl.hva.ict.ss.textsearch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class LanguageDetectorTest {
    private LanguageDetector detector;
    //The text I'm using as stand-in for English language, is from https://www.darcypattison.com/writing/revision/400-words-exactly/
    private final String ENGWORDS = "Here’s your 400 challenge: Writing a long sentence, in fact, a sentence that is exactly 400 words long. Here’s my offering. As a self-taught writer who has taken the long, winding road towards writing and literary efforts, I was slow to learn about writing long sentences, both how to do so and why one might want to do so, but finally was enlightened by three articulate authors and their books: Ursula LeGuin, the respected science fiction and fantasy writer, author of The Left Hand of Darkness, and the popular children’s series, Wizard of the Earthsea, encourages long sentences in her how-to book on writing, Steering the Craft, by quoting a 354-word sentence from Mark Twain’s Huckleberry Finn, which takes the long, slow route – LeGuin calls it the “marvelously supple connections of complex syntax” – to describing the details of a sunrise over the Mississippi River, including the sights, sounds, and smells of the unfolding morning; the second book which is less artistic, but perhaps more helpful to me personally was Ann Longknife, Ph.D and K.D. Sullivan’s book, The Art of Styling Sentences: 20 Patterns for Success – look for the second edition published in 2002 – which I studied with a writing friend, KN, and found to be extremely helpful in reviewing colons, semi-colons, appostives, etc, especially as KN and I posted to mutual mailing lists and encourage each other to use the patterns correctly and creatively and learned that control of language was essential to make the words mean what you want it to mean ( in fact, I found this book to be so useful, that I required it as a text when I taught Freshman Composition); and third, was Dona Hickey’s wondeful book, Developing a Written Voice, a virtual gem of a book – it’s not for the faint-hearted, because it reads like a college text book, but it’s a gem, nonetheless – which encourages the exploration of both long and short sentences, including sentence fragments, while Hickey also gives the writer a range of options for creating coherence and cohesion among the various parts of the sentence, including traditional rhetorical strategies such as schemes (unusual patterns of words) : schemes of balance, such as parallel structures, antithesis and the isocolon; schemes of unusual or inverted word order, such as anastrophe and parenthesis; schemes of omission, such as ellipsis, asyndeton or polysyndeton; schemes of repetition, such as alliteration, polyptoton, assonance, anaphora, epistrophe, epanalepsis, anadiplosis, tricolon, chiasmus, and of course, long lists – all useful tools to create long sentences and keep them understandable. Writing long is fun. Really. Try it.".replaceAll("[^a-zA-Z0-9]", "").toLowerCase();   
    @Before
    public void setup() {
        detector = new LanguageDetector(getClass().getResourceAsStream("/edu/princeton/cs/algs4/Huffman.java"));
        
    }
    private String preparePayload(String name) throws FileNotFoundException{
        File file = new File(name);
        FileInputStream fis = new FileInputStream(file);
        Scanner sc = new Scanner(fis);
        sc.useDelimiter("\\Z"); // EOF marker. Ensures that the entire file is wrapped in one sc.next() call.
        
        String payload = sc.next();
        payload = payload.replaceAll("[^a-zA-Z0-9]", "");
        return payload.toLowerCase();
    }

    // Add your tests here. They are allowed to NOT use assertXxxx... :-)
    @Test
    public void isNotEnglish() throws FileNotFoundException {
        System.out.println("Text in German. Test succeeds if detector returns false");
        assertFalse(detector.compare(detector.getTable(ENGWORDS), detector.getTable(preparePayload("dw.txt"))));
    }
    @Test
    public void isEnglish() throws FileNotFoundException{
        System.out.println("Text in English. Test succeeds if detector returns true");
        assertTrue(detector.compare(detector.getTable(ENGWORDS), detector.getTable(preparePayload("tywin biography.txt"))));
    }
    @Test
    public void isFrench() throws FileNotFoundException{
        System.out.println("Text in French. Test succeeds if detector returns false");
        assertFalse(detector.compare(detector.getTable(ENGWORDS), detector.getTable(preparePayload("france.txt"))));
    }
    @Test
    public void revealsCalls(){
        assertTrue(LanguageDetector.containsMethodCall(detector.getContent()));
    }
}