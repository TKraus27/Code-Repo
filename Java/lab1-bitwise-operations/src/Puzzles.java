public class Puzzles {
    // "Expression" means that you should write simple code
    // that doesn't involve looping. Use bitwise operators like
    // AND (&), OR(|), NOT(~), XOR(^), shift left logical (<<),
    // shift right logical (>>>), shift right arithmetic (>>).

    // You should try to solve each of these problems on paper before
    // writing the code.

    /* #1 Write an expression that results in the least significant
     byte of an integer x being preserved, but all other bits set to
     1. For example: 0x98234493 becomes 0xFFFFFF93.
     */
    public static int Q1(int x) {
      System.out.println(Integer.toHexString(x & 0xFF));
      System.out.println(Integer.toHexString(~0xFF));
      return (x & 0xFF) | ~0xFF;
    }

    /* #2 Write an expression that results in the least significant
    byte of an integer x staying the same, but all other bits
    complemented. For example: 0x98234493 becomes 0x67dcbb93.
    */
    public static int Q2(int x) {
      System.out.println(Integer.toHexString(x & 0xFF));
      System.out.println(Integer.toHexString(~x & ~0xFF));
      return (x & 0xFF)|(~x & ~0xFF);
    }

    /* #3 Write an expression that results in the least significant
        byte of an integer x being set to 0, but all other bits unchanged.
        For example: 0x98234493 becomes 0x98234400.
        */
    public static int Q3(int x) {
      return ((x>>8)<<8);
    }

    /* #6 Write an expression that is equivalent to x == y.
    It should evaluate to 1 if and only if x and y are equal.
     */
    public static int Q6(int x, int y) {
      String w = Integer.toHexString(x);
      String v = Integer.toHexString(y);
      if(w.equals(v)) {
        return 1;
      }
      return 0;
    }

    /* #7 Write an expression that reverses the bytes of an
    integer x. That is the least significant byte should be
    swapped with the most significant byte, but the bits within
    each byte should remain in their original order.
    For example, 0xABCD1234 should be turned into 0x3412CDAB

    HINTS: Try the problem without using hints. If you get really stuck you
    can spoil one hint at a time using the converter at
    http://www.geocachingtoolbox.com/index.php?lang=en&page=asciiConversion
    Copy/paste the sequence of numbers into "Text:" and make sure to choose "from: decimal (base 10)" "to: Text (ASCII)"

    Hint1: 84 114 121 32 116 111 32 99 111 109 112 117 116 101 32 101 97 99 104 32 111 102 32 116 104 101 32 52 32 110 101 119 32 98 121 116 101 115 32 115 101 112 97 114 97 116 101 108 121 32 97 110 100 32 116 104 101 110 32 99 111 109 98 105 110 101 32 116 104 101 109 32 105 110 116 111 32 97 32 115 105 110 103 108 101 32 105 110 116 46
    Hint2: 84 111 32 99 111 109 112 117 116 101 32 116 104 101 32 112 97 114 116 105 97 108 32 97 110 115 119 101 114 32 119 104 101 114 101 32 116 104 101 32 109 111 115 116 32 115 105 103 110 105 102 105 99 97 110 116 32 98 121 116 101 32 105 115 32 99 111 114 114 101 99 116 32 97 110 100 32 116 104 101 32 114 101 115 116 32 111 102 32 116 104 101 32 98 105 116 115 32 97 114 101 32 48 44 32 121 111 117 32 119 111 117 108 100 32 115 104 105 102 116 32 108 101 102 116 32 116 104 101 32 108 101 97 115 116 32 115 105 103 110 105 102 105 99 97 110 116 32 98 121 116 101 32 111 102 32 120 32 98 121 32 104 111 119 32 109 97 110 121 32 98 105 116 115 63
    Hint3: 70 111 114 32 65 66 67 68 49 50 51 52 44 32 121 111 117 114 32 102 111 117 114 32 112 97 114 116 105 97 108 32 97 110 115 119 101 114 115 32 109 97 121 32 98 101 32 51 52 48 48 48 48 48 48 44 32 48 48 49 50 48 48 48 48 48 48 44 32 48 48 48 48 67 68 48 48 44 32 97 110 100 32 48 48 48 48 48 48 65 66 46 32 72 111 119 32 100 111 32 121 111 117 32 99 111 109 98 105 110 101 32 116 104 101 115 101 32 102 111 117 114 32 105 110 116 115 32 105 110 116 111 32 116 104 101 32 102 105 110 97 108 32 97 110 115 119 101 114 63
    */
    public static int Q7(int x) {
      int val1 = x>>24;
      int val2 = ((x<<8)>>24)<<8;
      int val3 = ((x<<16)>>24)<<16;
      int val4 = x<<24;
      System.out.println(Integer.toHexString(val1));
      System.out.println(Integer.toHexString(val2));
      System.out.println(Integer.toHexString(val3));
      System.out.println(Integer.toHexString(val4));
      return val1|val2|val3|val4;
    }

    public static void main(String[] args) {
        // A few test cases are given here for the coding problems.
        // You should add additional test cases to provide yourself more evidence of correctness.

        assert Q1(0x98234493) == 0xFFFFFF93;
        assert Q2(0x98234493) == 0x67dcbb93;
        assert Q3(0x98234493) == 0x98234400;


        /* #4 Do your expressions work if the size of the "int" data type
              is 16? 12? 64? If not, why? (answer as comment)
              The first one would only work for the last 8 hex digits but #2 and #3 would work for any size. The first one is being compared to an 8 digit hex so any more digits than that is not going to be compared. The other two are not dependent on the size of the int.
         */


        /* #5 What is the value in hexadecimal of ~0? Assume that
            the literal 0 is an integer (as opposed to a char or short, etc.).
            (answer as comment)
            It would be a hex value of 0xFFFFFFFF
            */

        assert Q6(0xFFFF0000, 0xFFFF0000) == 1;
        assert Q6(0xABCDEF00, 0xABCD1111) == 0;


        assert Q7(0xABCD1234) == 0x3412CDAB;

        /* #8 Reflect on your problem solving approach for the puzzles.
           What strategies did you use to decide on an initial implementation?
           What strategies did you use to revise or fix your initial implementation?
           (answer as a comment)
           First I tried to use loops and operate on the hex string. I then moved on to using bitwise calculations and tried finding numbers that would get me the value that I needed using the right bitwise operator. The first one is hard to get working if the value is larger than 8 hex digits but the others were easier to implement.
           */
    }


}
