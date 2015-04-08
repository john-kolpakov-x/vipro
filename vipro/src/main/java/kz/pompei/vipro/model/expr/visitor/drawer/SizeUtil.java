package kz.pompei.vipro.model.expr.visitor.drawer;

public class SizeUtil {

  public static float fontSizeByLevel(int level) {
    if (level <= 0) return 14;
    if (level == 1) return 12;
    if (level == 2) return 11;
    if (level == 3) return 10;
    if (level == 4) return 9;
    return 8;
  }
  
}
