/**ConcatInt.java
 * @author Tony Kraus
 * @version 5-10-2017
 */

import java.util.*;
import java.io.*;

public class ConcatInt {
	public static void ConcatInt (Scanner sc) {
		try {
			PrintWriter outFile = new PrintWriter(new FileWriter(new File("output.txt")));
			String[] line;
			while (sc.hasNext()) {
				line = sc.nextLine().split("\\D");
				List<String> ints = Arrays.asList(line);
				ints.sort(intCompare);
				StringBuilder strbld = new StringBuilder();
				for (int i = 0; i < ints.size(); i++) {
					strbld.append(ints.get(i));
				}
				outFile.print(strbld.toString() + " ");
				Collections.reverse(ints);
				strbld = new StringBuilder();
				for (int i = 0; i < ints.size(); i++) {
					strbld.append(ints.get(i));
				}
				outFile.println(strbld.toString());
			}
			outFile.close();
		}catch (IOException e) {
			System.out.println(e.getMessage());
		}catch (InputMismatchException e) {
			System.out.println(e.getMessage());
		}
	}

	public static Comparator<String> intCompare = new Comparator<String>() {
		public int compare(String num1, String num2) {
			int numlength;
			int size;
			if (num1.length() < num2.length()){
				numlength = -1;
				size = num1.length();
			}else if (num2.length() < num1.length()) {
				numlength = 1;
				size = num2.length();
			}else {
				numlength = 0;
				size = num1.length();
			}
			for (int x = 0; x < size; x++) {
				int first1 = Character.getNumericValue(num1.charAt(x));
				int first2 = Character.getNumericValue(num2.charAt(x));
				if (first1 < first2) {
					return -1;
				}else if (first2 < first1) {
					return 1;
				}else if (first1 == first2 && numlength == 0 && x == size-1) {
					return 0;
				}else if (first1 == first2 && x == size-1) {
					String last;
					if (numlength == -1) {
						last = num1.substring(x+1);
					}else {
						last = num2.substring(x+1);
					}
					for (int a = 0; a < last.length(); a++) {
						int i = Character.getNumericValue(last.charAt(a));
						if (i != 0) {
							return numlength;
						}
					}
				}
			}
			return (numlength > 0) ? -1 : 1;
		}
	};

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println( "Missing file name on command line" );
		} else if (args.length > 1) {
			System.out.println( "Unexpected command line args" );
		} else try {
			ConcatInt(new Scanner(new File(args[0])));
		}catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
}
