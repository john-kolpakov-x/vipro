package kz.pompei.vipro.probes.etc;

public class TimeUtil {

  public static String readNano(long nano) {
    if (nano == 0) return "0";

    StringBuilder sb = new StringBuilder(10000);

    {
      final long BASE = 1_000_000_000L;
      long x = nano % BASE;
      nano = nano / BASE;
      if (x > 0) sb.append(x / 1_000_000.0).append(" мс");
    }

    appendFromSeconds(sb, nano);

    return sb.toString();
  }

  public static String readMilli(long milli) {
    if (milli == 0) return "0";

    StringBuilder sb = new StringBuilder(1_024);

    {
      final long BASE = 1_000L;
      long x = milli % BASE;
      milli = milli / BASE;
      if (x > 0) sb.append(x).append(" мс");
    }

    appendFromSeconds(sb, milli);

    return sb.toString().trim();
  }

  private static long appendUnit(StringBuilder sb, long base, String unit, long units) {
    if (units == 0) return 0;
    long x = units % base;
    if (x > 0) sb.insert(0, x + " " + unit + " ");
    return units / base;
  }

  private static void appendFromSeconds(StringBuilder sb, long seconds) {
    long tmp = seconds;
    tmp = appendUnit(sb, 60, "сек", tmp);
    tmp = appendUnit(sb, 60, "мин", tmp);
    tmp = appendUnit(sb, 24, "час", tmp);
    tmp = appendUnit(sb, 365, "дн", tmp);

    if (tmp > 0) {
      long last = tmp % 10;
      if (last == 0 || last >= 4) sb.insert(0, tmp + " лет ");
      else if (last == 1) sb.insert(0, tmp + " год ");
      else if (last == 2 || last == 3) sb.insert(0, tmp + " года ");
    }
  }


  public static String toLenZero(int len, String s) {
    return toLenPrefix(len, "0", s);
  }

  public static String toLenPrefix(int len, String prefix, String s) {
    if (s == null) s = "";
    if (prefix == null || prefix.length() == 0) return s;

    while (true) {
      if (s.length() >= len) return s;
      s = prefix + s;
    }
  }

  public static void main(String[] args) {
    long L[] = {

      1_825_276_123L, 12_276_123L,

      10_276_123L, 198_786_123_987L,

      111_222_333_444_555_666L,

      4_111_222_333_444_555_666L,

    };

    for (long l : L) {
      System.out.println(toLenPrefix(30, " ", "" + l) + " --> " + readNano(l));
    }
  }
}
