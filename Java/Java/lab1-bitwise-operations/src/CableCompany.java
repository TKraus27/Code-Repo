
public class CableCompany {
    public static void main(String[] args) {
        // Test input 1
        long code1 = 7999;

        // test isEnabled
        assert isEnabled(code1, 4);
        assert !isEnabled(code1, 6);
        assert !isEnabled(code1, 7);

        // test enableChannel
        long code1_mod = enableChannel(code1, 4);
        assert code1_mod == code1;
        code1_mod = enableChannel(code1_mod, 6);
        assert isEnabled(code1_mod, 6);
        assert !isEnabled(code1_mod, 7);

        // Test input 2
        long code2 = 768;

        // test isEnabled
        assert !isEnabled(code1, 4);
        assert isEnabled(code1, 8);

        // test enableChannel
        long code2_mod = enableChannel(code2, 5);
        assert isEnabled(code2_mod, 5);
        assert code2_mod == 800;
    }

    public static boolean isEnabled(long A, int channel) {
      if (((A>>channel) & 0b1) == 1) {
        return true;
      }
      return false;
    }

    public static long enableChannel(long A, int channel) {
      return A |= (0b1<<channel);
    }
}
