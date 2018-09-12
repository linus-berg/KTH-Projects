/*
 * This demonstrates the FIFO queue.
*/

import java.util.*;
import edu.princeton.cs.algs4.StdIn;

class Algo {
  public static void main(String[] args) {
    FIFOQueue<Integer> test = new FIFOQueue<Integer>();
    test.Add(Integer.valueOf(100));
    test.Add(Integer.valueOf(340));
    test.Add(Integer.valueOf(210));
    for(Integer b : test) {
      System.out.println(b);
    }
  }
}

