/**L33Tspeak.java - Takes a text file with normal characters and translates it to L33T speak
 * @author Tony Kraus
 * @version 5-1-2017
 */

import java.util.*;
import java.io.*;

public class L33Tspeak {
	public static void Translate(Scanner sc) {
		try {
			PrintWriter outFile = new PrintWriter(new FileWriter(new File("output.txt")));
			String str;
			sc.useDelimiter("");
			while (sc.hasNext()) {
				str = sc.next();
				outFile.print(Leet.Leetspeak(str));
			}
			outFile.close();
		}catch (IOException e) {
			System.out.println(e.getMessage());
		}catch (InputMismatchException e) {
			System.out.println(e.getMessage());
		}
	}
	public enum Leet {
		A("a", "4"),
		B("b", "6"),
		E("e", "3"),
		I("i", "1"),
		L("l", "1"),
		M("m", "(V)"),
		N("n", "(\\)"),
		O("o", "0"),
		S("s", "5"),
		T("t", "7"),
		V("v", "\\/"),
		W("w", "'//");

		private final String letter;
		private final String leeter;

		Leet(String letter, String leeter) {
			this.letter = letter;
			this.leeter = leeter;
		}

		public static String Leetspeak(String str) {
			for (Leet x : Leet.values()) {
				if (str.equalsIgnoreCase(x.letter)) {
					return x.leeter;
				}
			}
			return str;
		}
	}
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println( "Missing file name on command line" );
		} else if (args.length > 1) {
			System.out.println( "Unexpected command line args" );
		} else try {
			Translate(new Scanner(new File(args[0])));
		}catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
}
