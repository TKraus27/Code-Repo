public class BitwiseOpsExamples {
    public static void main(String[] args) {

        int x = 55; // by default Java interprets constant integers as decimal
        System.out.println(x +" = "+ Integer.toBinaryString(x));

        int y = 10;
        System.out.println(y + " = "+ Integer.toBinaryString(y));

        int z = x & y; // bitwise AND
        System.out.println(x + " & " + y + " = " + z);
        System.out.println(Integer.toBinaryString(x) + " & " + Integer.toBinaryString(y) + " = " + Integer.toBinaryString(z));

        int w = x & ~y; // negation and bitwise AND
        System.out.println("~"+y + " = " + ~y);
        System.out.println(x + " & ~" + y + " = " + w);
        System.out.println(Integer.toBinaryString(x) + " & ~" + Integer.toBinaryString(y) + " = " + Integer.toBinaryString(w));

        // You can assign a variable using a base 2 or base 16 number.
        // It doesn't change the number it represents!
        int a = 0xA; // constant represented in hex
        int b = 10;  // constant represented in decimal
        int c = 0b1010; // constant represented in binary

        System.out.println(a == b);
        System.out.println(b == c);
    }
}
