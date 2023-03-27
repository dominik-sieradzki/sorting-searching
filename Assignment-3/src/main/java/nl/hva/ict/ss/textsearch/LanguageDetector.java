package nl.hva.ict.ss.textsearch;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LanguageDetector {
    private String content; // Once an instance is created this will hold the complete content of the file.
    private final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    //private final String REGEX = "(a)|(b)|(c)|(d)|(e)|(f)|(g)|(h)|(i)|(j)|(k)|(l)|(m)|(n)|(o)|(p)|(q)|(r)|(s)|(t)|(u)|(v)|(w)|(x)|(y)|(z)";
    private final String REGEX = "(?!\\bif\\b|\\bfor\\b|\\bwhile\\b|\\bswitch\\b|\\btry\\b|\\bcatch\\b)(\\b[\\w]+\\b)[\\s\\n\\r]*(?=\\(.*\\))";

    
    public LanguageDetector(InputStream input) {
        Scanner sc = new Scanner(input);
        sc.useDelimiter("\\Z"); // EOF marker
        content = sc.next();
    }

    // Put your own code here and integrate it with the test class

    //compares the list with the sample list. This is where you determine the language.

    /**
     *
     * @param content
     * @return
     */
    public ArrayList<Double> getTable(String content){
       ArrayList table = new ArrayList();
       char[] a = ALPHABET.toCharArray();
       int counter = 0;
       //for each character in the alphabet, a regex of that character is compiled.
       //then find() method is looped for all matches. Amount found is recorded in table array.
       for (char c: a){
           counter = 0;
           String regex = "["+c+"]";
           Pattern p = Pattern.compile(regex);
           Matcher m = p.matcher(content);
           while(m.find()){
              counter++;
           }
           if(counter > 0){
           Double payload = (double)counter/(double)content.length();
           table.add(payload);
           }
           else table.add((double) 0.0);
       }
        
        return table;
    }
    public boolean compare(ArrayList<Double> one, ArrayList<Double> other){
        System.out.println(one.size()+" ");System.out.println(+other.size());
        int checks = 0;
        int charChecks = 0;
        double standard = 0.02;
        double negativeStandard = -0.02;
        char l = 64; //
        for(int i = 0; i < one.size(); i++){
            l = (char) (l+1);
            System.out.println("letter "+l);
            System.out.println("sample:     "+one.get(i));
            System.out.println("checked_file:   "+other.get(i));
            System.out.println("sum (difference):   "+(one.get(i)-other.get(i)));
            charChecks++;
            if(one.get(i)-other.get(i) > negativeStandard && one.get(i)-other.get(i) < standard) checks++;
            
        }
        System.out.println(checks+" vs "+charChecks);
        return charChecks - checks < charChecks*0.1;
    }

    public String getContent(){
        return content;
    }
    
    /**
    * Matches strings like {@code obj.myMethod(params)} and
    * {@code if (something)} Remembers what's in front of the parentheses and
    * what's inside.
    * <p>
    * {@code (?U)} lets {@code \\w} also match non-ASCII letters.
    * Copyright (c) 2019, Matthias Braun
    */
   public static final Pattern PARENTHESES_REGEX = Pattern
           .compile("([.\\w]+)\\s*\\((.*)\\)");

   /*
    * After these Java keywords may come an opening parenthesis.
    */
   private static List<String> keyWordsBeforeParens = Arrays.asList("while", "for", "if",
           "try", "catch", "switch");
   
   //Regex made by altering a version that belongs to https://stackoverflow.com/users/3974075/allen-baker.
   //Original regex: https://stackoverflow.com/a/25478963 
   public static final Pattern RGX = Pattern.compile("(\\w)*(\\.[\\s\\n\\r]*[\\w]+)[\\s\\n\\r]*(?=\\(.*\\))*\\S(?=[(-)])+");
//(\S)*(\.[\s\n\r]*[\w]+)[\s\n\r]*(?=\(.*\))*\S(?=[(-)])+
   public static boolean containsMethodCall(final String s) {
       final Matcher matcher = RGX.matcher(s);
       int count = 0;
       while(matcher.find()){
           count++;
           System.out.println("Caller & Method name: " + matcher.group());
           
       }
       System.out.println("Total calls detected: " + count);
       /*final Matcher matcher = PARENTHESES_REGEX.matcher(s);

       while (matcher.find()) {
           final String beforeParens = matcher.group(1);
           final String insideParens = matcher.group(2);
           if (keyWordsBeforeParens.contains(beforeParens)) {
               System.out.println("Keyword: " + beforeParens);
               return containsMethodCall(insideParens);
           } else {
               System.out.println("Method name: " + beforeParens);
               //return true;
           }
       }*/
       return true;
   }
}