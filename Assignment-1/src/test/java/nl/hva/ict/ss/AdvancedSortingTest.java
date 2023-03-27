package nl.hva.ict.ss;

import nl.hva.ict.ss.util.NameReader;
import org.junit.BeforeClass;
import org.junit.Test;

import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Just measures the time needed that sorting an increasing number of players.
 * The measurements stops if sorting takes more the twenty seconds or when there
 * would be sorted more then 1.048.576 of players.
 *
 * @author Nico Tromp, n.j.tromp@hva.nl
 */
public class AdvancedSortingTest {
    static final int MAX_HIGH_SCORE = 100_000;
    static final int TWENTY_SECONDS = 999999999; // originally 20_000, still far less than 20 seconds :)
    static final int UPPER_LIMIT = 1<<20; // 1.048.576
    static final Random randomizer = new SecureRandom();
    static LinkedList<Player> unsortedPlayers = new LinkedList<>();

    @BeforeClass
    public static void setup() {
        System.out.printf("Preparing players...");
        System.out.flush();
        String [] firstNames = new NameReader("/firstnames.txt").getNames();
        String [] lastNames = new NameReader("/lastnames.txt").getNames();
        for (int i = 0; i < UPPER_LIMIT; i++) {
            String firstName = firstNames[randomizer.nextInt(firstNames.length)];
            String lastName = lastNames[randomizer.nextInt(lastNames.length)];
            unsortedPlayers.addLast(new Player(firstName, lastName, randomizer.nextInt(MAX_HIGH_SCORE)));
        }
        System.out.printf("%nStart measurements.%n%n");
        System.out.flush();

        // Try to get Java read for steady measurements
        AdvancedSorts.quickSort(new LinkedList<Player>());
        AdvancedSorts.quickSort(new Player[0]);
    }

    @Test
    public void measureEfficiencyArray() {
        System.out.printf("Array implementation%n");
        long timeNeeded = 0;
        for (int numberOfPlayers = 100; numberOfPlayers < UPPER_LIMIT && timeNeeded < TWENTY_SECONDS; numberOfPlayers *= 2) {
            Player[] players = getSubList(unsortedPlayers, numberOfPlayers).toArray(new Player[numberOfPlayers]);
            System.gc();

            long start = System.nanoTime();
            AdvancedSorts.quickSort(players);
            long finish = System.nanoTime();

            // Try to keep measurements steady
            System.gc();

            timeNeeded = finish - start;

            System.out.printf("%d;%d%n", numberOfPlayers, timeNeeded);
            System.out.flush();
        }
    }

    @Test
    public void measureEfficiencyLinkedList() {
        System.out.printf("LinkedList implementation%n");
        long timeNeeded = 0;
        for (int numberOfPlayers = 100; numberOfPlayers < UPPER_LIMIT && timeNeeded < TWENTY_SECONDS; numberOfPlayers *= 2) {
            LinkedList<Player> players = getSubList(unsortedPlayers, numberOfPlayers);
            System.gc();

            long start = System.nanoTime();
            AdvancedSorts.quickSort(players);
            long finish = System.nanoTime();

            // Try to keep measurements steady
            players.clear();
            System.gc();

            timeNeeded = finish - start;

            System.out.printf("%d;%d%n", numberOfPlayers, timeNeeded);
            System.out.flush();
        }
    }

    LinkedList<Player> getSubList(LinkedList<Player> players, int numberOfPlayers) {
        LinkedList<Player> unsortedPlayers = new LinkedList<>();
        int i = 0;
        for (Player player : players) {
            unsortedPlayers.addLast(player);
            if (++i == numberOfPlayers) break;
        }
        return unsortedPlayers;
    }

}