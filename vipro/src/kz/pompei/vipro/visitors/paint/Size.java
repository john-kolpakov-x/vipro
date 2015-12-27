package kz.pompei.vipro.visitors.paint;

public class Size {
  public final double heightTop, heightBottom, width;
  
  public Size(double heightTop, double heightBottom, double width) {
    this.heightTop = heightTop;
    this.heightBottom = heightBottom;
    this.width = width;
  }
  
  public double height() {
    return heightTop + heightBottom;
  }
  
  public int intHeightTop() {
    return toInt(heightTop);
  }
  
  public int intHeightBottom() {
    return toInt(heightBottom);
  }
  
  public int intHeight() {
    return toInt(height());
  }
  
  public int intWidth() {
    return toInt(width);
  }
  
  private static int toInt(double a) {
    int sign = 1;
    if (a < 0) {
      a = -a;
      sign = -sign;
    }
    return sign * ((int)(a + 0.5));
  }
}
