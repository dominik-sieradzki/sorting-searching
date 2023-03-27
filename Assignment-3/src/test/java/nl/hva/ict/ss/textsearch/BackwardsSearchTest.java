package nl.hva.ict.ss.textsearch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BackwardsSearchTest {
    protected BackwardsSearch revSearchEngine;
    protected ForwardSearch searchEngine;
    
    //Ice Cube's "No Vaseline" lyrics. Spaces and special chars removed. This hot haystack does have a "needle" somewhere in it.
    protected String lyrics = "GotdamnImgladyallsetitoffUsedtobehardnowyourejustwetandsoftFirstyouwasdownwiththeAKAndnowIseeyouonavideowithMichelleLookinlikestraightbozosIsawitcominthatswhyIwentsoloAndkeptonstompinWhenyallmotherfuckersmovedstraightouttaComptonLivinwiththewhitesonebighouseAndnotanotherniggainsiteIstartedoffwithtoomuchcargoDroppedfourniggasnowImmakinallthedoughWhitemanjustrulinTheNiggasWithAttitudeswhoyafoolinYallniggasjustphonyIputthatonmymamaandmydeadhomeysYellaBoysonyourteamsoyourelosinAyyoDresticktoproducinCallinmeArnoldbutyouBeenadickEazyEsawyourassandwentinitquickYougotjealouswhenIgotmyowncompanyButImamanandaintnobodyhelpinmeTryintosoundlikeAmerikkkasMostYoucouldyellalldaybutyoudontcomecloseCauseyouknowImtheonethatflownYadonerun100milesbutyoustillgotonetogoWiththeLENCHMOBandyalldisgracetheCPTCauseyouregettinfuckedoutyourgreenbyawhiteboyWithnoVaselineThebiggerthecapthebiggerthepeelinWhogivesafuckaboutapunkassvillainYouregettinfuckedrealquickAndEazysdickissmellinlikeMCRensshitTriedtotellyouayearagoButWillieDtoldmetoletahoebeahoesoIcouldntstopyoufromgettingankedNowletsplaybigbanktakelittlebankTriedtodisIceCubeitwasntworthitCausethebroomstickfityourasssoperfectCutmyhairandIllcutthemballsCauseIheardyourelikegivinupthedrawersGangbangedbyyourmanagerfellaGettinmoneyoutyourasslikeamotherfuckinReadyTellerGivinupthedollarbillsNowtheygottheVillainwithapurseandhighheelsSodontbelievewhatRensayCausehesgoinoutlikeKunteKinteButIgotawhipforyaTobyUsedtobemyhomeynowyouactlikeyoudontknowmeItsacaseofdivideandconquerCauseyouletaJewbreakupmycrewHouseniggagottarunandhideYellinComptonbutyoumovedtoRiversideSodontfrontMCRencauseIrememberwhenyoudroveabe210BrokeasamothafuckinjokeLetyouonthescenetobackuptheVerseTeamItaintmyfaultoneniggagotsmartAndtheyrippinyourassholeapartBytakinyourgreenohyeahTheVillaindoesgetfuckedwithnoVaselineIneverhavedinnerwiththePresidentIneverhavedinnerwiththeneedleIneverhavedinnerwiththePresidentAndwhenIseeyourassagainIllbehesitantNowIthinkyouasnitchThrowahouseniggainaditchHalfpintbitchfuckinyourhomeboysYoulittlemaggotEazyEturnedfaggotWithyourmanagerfellaFuckinMCRenDrDreandYellaButiftheyweresmartasmeEazyEwouldbehanginfromatreeWithnoVaselinejustamatchandalittlebitofgasolineLightemupburnemupflameonTillthatJhericurlisgoneOnapermanentvacationofftheMassaplantationHeardyoubothgotthesamebankaccountDumbniggawhatyouthinkinboutGetridofthatDevilrealsimpleputabulletinhistempleCauseyoucantbetheNigga4LifecrewWithawhiteJewtellinyouwhattodoPullinwoolswithyourscamsnowIgottaplaytheSilenceoftheLambsWithamidgetwhosapunktooTryintofuckmebutIdratherfuckyouEricWrightpunkalwaysintosomethingettinfuckedatnightByMistaShitpackerbendoverforthegotdamncrackernovaseline"; 
    protected long[] times = new long[5];
    
    @Before
    public void setup() {
    }
    @Test
    public void forwardPerformanceLarge() throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(new File("image800.txt"));
        Scanner sc = new Scanner(fis);
        sc.useDelimiter("\\Z"); // EOF marker. Ensures that the entire file is wrapped in one sc.next() call.
        String payload = sc.next();
        sc = null; fis = null;
        searchEngine = new ForwardSearch();
        int index = searchEngine.search(payload,"needle");
        System.out.println("Big File Forward Search. Pattern is at shift "+ ForwardSearch.getShift() +" Time taken: " + ForwardSearch.getTimeTaken() + " nanoseconds Comparisons made: "+ ForwardSearch.getComps());
        assertEquals(388172, index);
    }
    @Test
    public void reversePerformanceLarge() throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(new File("image800.txt"));
        Scanner sc = new Scanner(fis);
        sc.useDelimiter("\\Z"); // EOF marker. Ensures that the entire file is wrapped in one sc.next() call.
        String payload = sc.next();
        sc = null; fis = null;
        revSearchEngine = new BackwardsSearch();
        int index = BackwardsSearch.search(payload,"needle");
        System.out.println("Big File Backward Search. Pattern is at shift "+ BackwardsSearch.getShift() +" Time taken: " + BackwardsSearch.getTimeTaken() + " nanoseconds. Comparisons made: "+ BackwardsSearch.getComps());
        
        assertEquals(388172, index);
    }
    @Test
    public void reversePerformanceLyrics() {
        revSearchEngine = new BackwardsSearch();
                
        times = new long[5];System.out.println(times[0]);
        System.out.println("\n Inverse search performance: SW \n");
        int index = 0;
        for(int i = 0; i < 5; i++){
            index = revSearchEngine.search(lyrics,"needle");
            times[i] = (BackwardsSearch.getTimeTaken());
            System.out.println("Pattern is at shift "+ BackwardsSearch.getShift() +" Time taken: " + BackwardsSearch.getTimeTaken() + " nanoseconds. Comparisons made: "+ BackwardsSearch.getComps());
        }        
        System.out.println("Average time taken: "+Arrays.stream(times).sum()/5+" \n");
        assertEquals(1876, index);
    }
    @Test
    public void forwardPerformanceLyrics() {
        searchEngine = new ForwardSearch();
                
        times = new long[5];System.out.println(times[0]);
        System.out.println("\n Standard search performance: SW \n");
        int index = 0;
        for(int i = 0; i < 5; i++){
            index = searchEngine.search(lyrics,"needle");
            times[i] = (ForwardSearch.getTimeTaken()); 
            System.out.println("Pattern is at shift "+ ForwardSearch.getShift() +" Time taken: " + ForwardSearch.getTimeTaken() + " nanoseconds. Comparisons made: "+ ForwardSearch.getComps());
        }        
        System.out.println("Average time taken: "+Arrays.stream(times).sum()/5+" \n");
        assertEquals(1876, index);
    }
    @Test
    public void findSingleOccurrence() {
        //int index = ForwardSearch.search("whereistheneedleinthishaystack", "needle");

        revSearchEngine = new BackwardsSearch();
        int index = revSearchEngine.search("whereistheneedleinthishaystack","needle");
        assertEquals("whereisthe".length(), index);
    }
    
    @Test
    public void cantFindOccurrence() {
        //int index = ForwardSearch.search("needle", "");
        revSearchEngine = new BackwardsSearch();
        int index = revSearchEngine.search("thereisnothinginthishaystack","needle");
        assertEquals(-1, index);
    }

    @Test
    public void findReverseOccurrence() {
        revSearchEngine = new BackwardsSearch();
        int index = revSearchEngine.search("whereistheneedleinthishaystack","needle");
        System.out.println("Inverse. Pattern is at shift "+ BackwardsSearch.getShift() +" Time taken: " + BackwardsSearch.getTimeTaken() + " nanoseconds. Comparisons made: "+ BackwardsSearch.getComps());

        assertEquals(10, index);
    }
    
    @Test
    public void findFowardTest(){
        searchEngine = new ForwardSearch();
        int index = searchEngine.search("whereistheneedleinthishaystack", "needle");
        System.out.println("Standard. Pattern is at shift "+ ForwardSearch.getShift() +" Time taken: " + ForwardSearch.getTimeTaken() + " nanoseconds. Comparisons made: "+ ForwardSearch.getComps());
        assertEquals(10, index);
    }
  }