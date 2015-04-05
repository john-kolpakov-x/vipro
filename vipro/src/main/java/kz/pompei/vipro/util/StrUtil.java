package kz.pompei.vipro.util;

public class StrUtil {
  public static String fnn(String... ss) {
    for (String s : ss) {
      if (s != null) return s;
    }
    return "";
  }
}
