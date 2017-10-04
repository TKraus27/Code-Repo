import java.util.regex.*;
import java.io.*;

class Example {
  static int x;
  int y;
  public int doSomething() {
    int z = this.y + x;
    return z;
  }
  public String getString() {
    return new String("Hello world " + y);
  }
  public static void main(String[] args) {
    Example e = new Example();
    System.out.println(e.getString());
    int a = e.doSomething();
    String b = "2.6^(2+3/2)*(2-3)";
    Pattern parenths = Pattern.compile("(\\()[\\^\\-+*/.0-9]+(\\))");
    Matcher matchp = parenths.matcher(b);
    StringBuffer bufStr = new StringBuffer();
    while (matchp.find()) {
      matchp.appendReplacement(bufStr, "10");
    }
    System.out.println(bufStr.toString());
    Pattern plus = Pattern.compile("[\\^]");
    Matcher matche = plus.matcher(bufStr.toString());
    if (matche.find()) {
      matche.appendReplacement(bufStr, "-");
    }
    matche.appendTail(bufStr);
    System.out.println(bufStr.toString());

  }
}
