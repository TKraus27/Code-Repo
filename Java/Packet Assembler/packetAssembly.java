/**packetAssembly.java - Assemble packets based in order given 4 different arguments including packet data
 * @author Tony Kraus
 * @version 9-26-17
 */

import java.util.*;
import java.io.*;

public class packetAssembly {
  public static void main(String[] args) {
    try {
      Scanner in = new Scanner(System.in);
      String str = in.nextLine();
      String[] line;
      int mID, pID, pSize;
      String message;
      Map<Integer, String> out = new TreeMap<>();
      while (!(str.equals(""))) {
        line = str.split("\\s+");
        mID = Integer.parseInt(line[0]);
        pID = Integer.parseInt(line[1]);
        pSize = Integer.parseInt(line[2]);
        message = String.join(" ", (Arrays.copyOfRange(line, 3, line.length)));
        out.put(pID + mID, message);
        str = in.nextLine();
      }
      in.close();
      PrintWriter outFile = new PrintWriter(new FileWriter(new File("output.txt")));
      for (Map.Entry<Integer, String> x : out.entrySet()) {
        outFile.println(x.getValue());
      }
      outFile.close();
    }catch (IOException e) {
			System.out.println(e.getMessage());
		}catch (InputMismatchException e) {
			System.out.println(e.getMessage());
		}
  }
}
