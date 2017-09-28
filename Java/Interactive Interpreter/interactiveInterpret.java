/**interactiveInterpret.java - interprets math functions and returns the appropriate value
 * @author Tony Kraus
 * @version 9-27-17
 */

import java.util.*;
import java.io.*;

public class interactiveInterpret {
  public static void interpret(Scanner sc) {
    try {
			PrintWriter outFile = new PrintWriter(new FileWriter(new File("output.txt")));
			String str;
			sc.useDelimiter("");
			while (sc.hasNext()) {
				str = sc.next();
			}
			outFile.close();
		}catch (IOException e) {
			System.out.println(e.getMessage());
		}catch (InputMismatchException e) {
			System.out.println(e.getMessage());
		}
  }

  public static void main(String[] args) {
    if (args.length < 1) {
			System.out.println( "Missing file name on command line" );
		} else if (args.length > 1) {
			System.out.println( "Unexpected command line args" );
		} else try {
			interpret(new Scanner(new File(args[0])));
		}catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
  }
}
