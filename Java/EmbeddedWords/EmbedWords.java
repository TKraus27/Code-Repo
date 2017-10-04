/**EmbedWords.java
 * @author Tony Kraus
 * @version 5-10-17
 */

import java.util.*;
import java.io.*;

public class EmbedWords {

  public static void EmbedWords (Scanner sc) {
    String line;
    LinkedList<String> lets = new LinkedList<>();
    while (sc.hasNext()) {
      line = sc.nextLine();
      LinkedList<String> word = new LinkedList<>(Arrays.asList(line.split("")));
      lets = addList(lets, word);
    }
    for (int x = 0; x < lets.size(); x++) {
      System.out.print(lets.get(x));
    }
    System.out.println();
  }

  public static LinkedList<String> addList (LinkedList<String> lets, LinkedList<String> word) {
    if (lets.isEmpty()) {
      lets.addAll(word);
    }else {
      int index;
      int last = 0;
      for (int x = 0; x < word.size(); x++) {
        index = lets.indexOf(word.get(x));
        if (index == -1) {
          lets.add(last, word.get(x));
          last = x+1;
        }else {
          int temp1;
          int temp2;
          for (int a = 0; a < x; a++) {

          }
        }
      }
    }
    return lets;
  }


  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println( "Missing file name on command line" );
    } else if (args.length > 1) {
      System.out.println( "Unexpected command line args" );
    } else try {
      EmbedWords(new Scanner(new File(args[0])));
    }catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    }
  }
}
