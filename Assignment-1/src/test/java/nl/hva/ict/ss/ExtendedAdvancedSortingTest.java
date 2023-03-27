package nl.hva.ict.ss;

import java.util.LinkedList;
import static nl.hva.ict.ss.AdvancedSortingTest.UPPER_LIMIT;
import nl.hva.ict.ss.util.NameReader;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Put your tests that show you implemented the code correctly in this class.
 * Any test placed at a different class will be ignored!
 * Failing to add tests here will result in failing the assignment!
 */
public class ExtendedAdvancedSortingTest extends AdvancedSortingTest {

    @Test
    public void theNameComparatorWorks() {
        LinkedList<Player> NamePlayers = new LinkedList<>();
        NamePlayers.add(new Player("Pim", "Jan", 1));
        NamePlayers.add(new Player("Pik", "Jan", 1));
        Player[] players = getSubList(NamePlayers, NamePlayers.size()).toArray(new Player[NamePlayers.size()]);
        AdvancedSorts.quickSort(players);
        assertTrue("Pim".equals(players[players.length-1].getFirstName()));
        
    }
    @Test
    public void theDuplicateEntriesAreSorted(){
        LinkedList<Player> NamePlayers = new LinkedList<>();
        NamePlayers.add(new Player("Pim", "Jan", 1));
        NamePlayers.add(new Player("Pim", "Jan", 1));
        Player[] players = getSubList(NamePlayers, NamePlayers.size()).toArray(new Player[NamePlayers.size()]);
        AdvancedSorts.quickSort(players);

        assertTrue("Pim".equals(players[0].getFirstName()) && "Pim".equals(players[1].getFirstName()));
        
    }
    @Test
    public void thereIsActualSortingDoneInArray(){
        Player[] players = new Player[3];
        players[2] = new Player("Pik", "Jan", 1);
        players[1] = new Player("ultron", "thanos", 1000);
        players[0] = new Player("Super", "deBoer", 1000000000);
        AdvancedSorts.quickSort(players);
        assertTrue(players[2].getHighScore() > players[0].getHighScore());
        
    }
    @Test
    public void thereIsActualSortingDoneInList(){
        LinkedList<Player> players = new LinkedList<>();
        players.add(new Player("Pik", "Jan", 1000000));
        players.add(new Player("ultron", "thanos", 10));
        players.add(new Player("Super", "deBoer", 100));
        AdvancedSorts.quickSort(players);
        assertTrue(players.getLast().getHighScore() > players.getFirst().getHighScore());
    }
}
