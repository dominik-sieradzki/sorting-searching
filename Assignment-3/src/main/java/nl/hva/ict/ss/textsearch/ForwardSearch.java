/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.hva.ict.ss.textsearch;

/**
 * @author www.geeksforgeeks.org
 * @author Dominik
 */
public class ForwardSearch {
    static int NO_OF_CHARS = 256;
    private static long timeTaken;
    private static int comps;
    private static int shift;
    //A utility function to get maximum of two integers 
     static int max (int a, int b) { return (a > b)? a: b; } 
  
     //The preprocessing function for Boyer Moore's 
     //bad character heuristic 
     static void badCharHeuristic( char []str, int size,int badchar[]) 
     { 
      int i; 
  
      // Initialize all occurrences as -1 
      for (i = 0; i < NO_OF_CHARS; i++) 
           badchar[i] = -1; 
  
      // Fill the actual value of last occurrence  
      // of a character 
      for (i = 0; i < size; i++) 
           badchar[(int) str[i]] = i; 
     } 
  
     /* A pattern searching function that uses Bad 
     Character Heuristic of Boyer Moore Algorithm */
     static int search(String text,  String pattern) 
     { 
      long begin = System.nanoTime();
      char txt[]= text.toCharArray();
      char pat[]= pattern.toCharArray();
      int m = pat.length; 
      int n = txt.length; 
      int c = 0;
      int rt = -1;
      int badchar[] = new int[NO_OF_CHARS]; 
  
      /* Fill the bad character array by calling  
         the preprocessing function badCharHeuristic()  
         for given pattern */
      badCharHeuristic(pat, m, badchar); 
  
      int s = 0;  // s is shift of the pattern with  
                  // respect to text 
      while(s <= (n - m)) 
      { 
          int j = m-1;
          /* Keep reducing index j of pattern while  
             characters of pattern and text are  
             matching at this shift s */
          while(j >= 0 && pat[j] == txt[s+j]) {
            j--;
            c++;//everytime in this while loop, a different char in txt is compared.
          }

  
          /* If the pattern is present at current 
             shift, then index j will become -1 after 
             the above loop */
          if (j < 0) 
          { 
              
              rt = s;
              /* Shift the pattern so that the next  
                 character in text aligns with the last  
                 occurrence of it in pattern. 
                 The condition s+m < n is necessary for  
                 the case when pattern occurs at the end  
                 of text */
              s += (s+m < n)? m-badchar[txt[s+m]] : 1; 
  
          } 
  
          else{
              /* Shift the pattern so that the bad character 
                 in text aligns with the last occurrence of 
                 it in pattern. The max function is used to 
                 make sure that we get a positive shift.  
                 We may get a negative shift if the last  
                 occurrence  of bad character in pattern 
                 is on the right side of the current  
                 character. */
              s += max(1, j - badchar[txt[s+j]]);    
          }

          c++;//notes that comparison is made
      }
      long end = System.nanoTime();
      timeTaken = (end-begin);
      comps = c;
      shift = rt;
      return rt;
     }
     
    public static long getTimeTaken() {
        return timeTaken;
    }

    public static int getComps() {
        return comps;
    }

    public static int getShift() {
        return shift;
    }
}