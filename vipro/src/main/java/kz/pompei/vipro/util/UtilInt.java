package kz.pompei.vipro.util;

public class UtilInt {
  public static int max(int... values) {
    
    if (values.length == 0) throw new IllegalArgumentException("No arguments");
    int ret = values[0];
    
    for (int i = 1, C = values.length; i < C; i++) {
      int v = values[i];
      if (ret < v) ret = v;
    }
    
    return ret;
  }
  
  public static int min(int... values) {
    
    if (values.length == 0) throw new IllegalArgumentException("No arguments");
    int ret = values[0];
    
    for (int i = 1, C = values.length; i < C; i++) {
      int v = values[i];
      if (ret > v) ret = v;
    }
    
    return ret;
  }
}
