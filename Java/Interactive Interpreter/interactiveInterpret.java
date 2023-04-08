/**interactiveInterpret.java - interprets math functions and returns the appropriate value
 * @author Tony Kraus
 * @version 9-27-17
 * 
 * challenge from r/dailyprogrammer subreddit (https://www.reddit.com/r/dailyprogrammer/comments/7096nu/20170915_challenge_331_hard_interactive/)
 */

import java.util.*;
import java.io.*;
import java.util.regex.*;
import java.lang.*;
import java.text.DecimalFormat;

public class interactiveInterpret {

  public static Map<String, String> vars = new HashMap<>();
  public static List<String> ops = new LinkedList<>();

  public static void interpret(Scanner sc) {
    ops.add("(\\-*[.0-9]+)\\^(\\-*[.0-9]+)");
    ops.add("(\\-*[.0-9]+)\\*(\\-*[.0-9]+)");
    ops.add("(\\-*[.0-9]+)\\/(\\-*[.0-9]+)");
    ops.add("(\\-*[.0-9]+)\\+(\\-*[.0-9]+)");
    ops.add("(\\-*[.0-9]+)\\-(\\-*[.0-9]+)");
    try {
      PrintWriter outFile = new PrintWriter(new FileWriter(new File("output.txt")));
      String str, result;
      while (sc.hasNextLine()) {
        str = sc.nextLine();
        String nospaces = str.replaceAll("\\s+", "");
        Pattern varpat = Pattern.compile("(\\w|\\d)+(?=(=))");
        Matcher varmatch = varpat.matcher(nospaces);
        if (varmatch.find()) {
          String[] varline = nospaces.split("=");
          if (varline.length == 2) {
            result = Evaluate(varline[1]);
            vars.put(varline[0], result);
          } else {
            throw new ArithmeticException("Cannot create a variable using this syntax");
          }
        }else {
          result = Evaluate(nospaces);
        }
        outFile.println(result);
      }
      outFile.close();
    }catch (IOException e) {
      System.out.println(e.getMessage());
    }catch (InputMismatchException e) {
      System.out.println(e.getMessage());
    }
  }

  public static String Evaluate(String exp) {
    for (String var : vars.keySet()) {
      exp = exp.replace(var, vars.get(var));
    }
    Pattern pattern = Pattern.compile("(\\(+)([\\(\\^*/+\\-.0-9]*[\\^*/+\\-.0-9]+[\\^*/+\\-.0-9\\)]*)(\\)+)");
    Matcher match = pattern.matcher(exp);
    StringBuffer bufStr = new StringBuffer();
    while (match.find()) {
      match.appendReplacement(bufStr, Evaluate(match.group(2)));
    }
    match.appendTail(bufStr);
    exp = bufStr.toString();
    bufStr.setLength(0);
    double eval;
    String evalstring;
    DecimalFormat df = new DecimalFormat("0.####");
    for (int i = 0; i < ops.size(); i++) {
      pattern = Pattern.compile(ops.get(i));
      match = pattern.matcher(exp);
      while (match.find()) {
        double var1 = Double.parseDouble(match.group(1));
        double var2 = Double.parseDouble(match.group(2));
        switch (i) {
          case 0:
            eval = Math.pow(var1, var2);
            break;
          case 1:
            eval = var1 * var2;
            break;
          case 2:
            eval = var1 / var2;
            break;
          case 3:
            eval = var1 + var2;
            break;
          case 4:
            eval = var1 - var2;
            break;
          default:
            eval = 0;
            break;
        }
        evalstring = df.format(eval);
        match.appendReplacement(bufStr, evalstring);
      }
      match.appendTail(bufStr);
      exp = bufStr.toString();
      bufStr.setLength(0);
    }
    return exp;
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
