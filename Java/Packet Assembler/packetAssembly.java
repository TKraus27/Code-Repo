/**packetAssembly.java - Assemble packets based in order given 4 different arguments including packet data
 * @author Tony Kraus
 * @version 9-26-17
 * 
 * challenge from r/dailyprogrammer subreddit (https://www.reddit.com/r/dailyprogrammer/comments/72ivih/20170926_challenge_333_easy_packet_assembler/)
 */

import java.util.*;
import java.io.*;

public class packetAssembly {
  public static void main(String[] args) {
    try {
      Scanner in = new Scanner(System.in);
      String str = in.nextLine();
      String[] line;
      Double mID, pID;
      Integer pSize;
      String message;
      Map<Double, String> out = new TreeMap<>();
      Map<Double, Integer> mStatus = new TreeMap<>();
      while (!str.equals("")) {
        line = str.split("\\s+");
        mID = Double.parseDouble(line[0]);
        pID = Double.parseDouble(line[1]);
        pSize = Integer.parseInt(line[2]);
        message = String.join(" ", (Arrays.copyOfRange(line, 3, line.length)));
        Integer status = mStatus.get(mID);
        if (status != null) {
          mStatus.put(mID, status-1);
        }else {
          mStatus.put(mID, pSize-1);
        }
        if (pID != 0) {
          out.put(mID + (pID/100), message);
        }else {
          out.put(mID, message);
        }
        str = in.nextLine();
      }
      in.close();
      PrintWriter outFile = new PrintWriter(new FileWriter(new File("output.txt")));
      for (Map.Entry<Double, String> entry : out.entrySet()) {
        double mplusp = Math.floor(entry.getKey());
        String strout = entry.getValue();
        Integer mstat = mStatus.get(mplusp);
        if (mstat != null) {
          if (mstat == 0) {
            strout = String.format("Message %d:\n", (long)mplusp) + strout;
          }else if (mstat == 1) {
            strout = String.format("Message %d (missing a packet):\n", (long)mplusp) + strout;
          }else {
            strout = String.format("Message %d (missing %d packets):\n", (long)mplusp, mstat) + strout;
          }
          mStatus.remove(mplusp);
        }
        outFile.println(strout);
      }
      outFile.close();
    }catch (IOException e) {
      System.out.println(e.getMessage());
    }catch (InputMismatchException e) {
      System.out.println(e.getMessage());
    }catch (NumberFormatException e) {
      System.out.println("Packets must be entered in the following line format:\nmessage ID | packet index | # of packets/message | message string");
    }
  }
}
