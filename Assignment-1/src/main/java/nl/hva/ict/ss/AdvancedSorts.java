package nl.hva.ict.ss;

import java.util.LinkedList;

public class AdvancedSorts {

    /**
     * Implement quicksort using LinkedList only! Usage of anything but LinkedList will result in failing the assignment!
     * Source: Sedgewick-Wayne Algorithms
     * @param unsorted
     * @param <E>
     * @author: Dominik Sieradzki 500777733, Robert Sedgewick, Kevin Wayne
     */

    //quicksort for lists
    public static <E extends Comparable<E>> void quickSort(LinkedList<E> unsorted){
        quickSort(unsorted, 0, unsorted.size()-1);
    }
    public static <E extends Comparable<E>> void quickSort(LinkedList<E> unsorted, int lo, int hi) {
        if (hi <= lo) return;
        int j = partition(unsorted, lo, hi);
        quickSort(unsorted, lo, j-1);
        quickSort(unsorted, j+1, hi);
    }
    //partition method for list quicksort. This is where the sorting happens.
    static <E extends Comparable<E>> int partition(LinkedList<E> unsorted, int lo, int hi) 
    { 
        E pivot = unsorted.get(hi);  
        int i = (lo-1); // index of smaller element 
        for (int j=lo; j<hi; j++) 
        { 
            // If current element is smaller than or 
            // equal to pivot 
            if (unsorted.get(j).compareTo(pivot) <= 0) 
            { 
                i++; 
  
                // swap arr[i] and arr[j] 
                E temp = unsorted.get(i); 
                unsorted.set(i,unsorted.get(j));
                unsorted.set(j,temp); 
            } 
        } 
  
        // swap arr[i+1] and arr[high] (or pivot) 
        E temp = unsorted.get(i+1); 
        unsorted.set(i+1, unsorted.get(hi));
        unsorted.set(hi, temp);
  
        return i+1; 
    } 

    /**
     * Implement quicksort using arrays only! Usage of anything but arrays will result in failing the assignment!
     * Source: Sedgewick-Wayne Algorithms
     * @param unsorted
     * @param <E>
     * @author: Dominik Sieradzki 500777733, Robert Sedgewick, Kevin Wayne
     */

    //quicksort for arrays, now with args for lo and hi
    public static <E extends Comparable<E>> void quickSort(E[] unsorted) {
        quickSort(unsorted, 0, unsorted.length-1);
    }
    
    public static <E extends Comparable<E>> void quickSort(E[] unsorted, int lo, int hi) {
        if (hi <= lo) return;
        int j = partition(unsorted, lo, hi);
        quickSort(unsorted, lo, j-1);
        quickSort(unsorted, j+1, hi);
    }

    static int partition(Comparable arr[], int low, int high) 
    { 
        Comparable pivot = arr[high];  
        int i = (low-1); // index of smaller element 
        for (int j=low; j<high; j++) 
        { 
            // If current element is smaller than or 
            // equal to pivot 
            if (arr[j].compareTo(pivot) <= 0) 
            { 
                i++; 
  
                // swap arr[i] and arr[j] 
                Comparable temp = arr[i]; 
                arr[i] = arr[j]; 
                arr[j] = temp; 
            } 
        } 
  
        // swap arr[i+1] and arr[high] (or pivot) 
        Comparable temp = arr[i+1]; 
        arr[i+1] = arr[high]; 
        arr[high] = temp; 
  
        return i+1; 
    } 

}
