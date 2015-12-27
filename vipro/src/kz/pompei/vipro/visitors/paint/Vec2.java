package kz.pompei.vipro.visitors.paint;

public class Vec2 {
  public final double x, y;
  
  public Vec2(double x, double y) {
    this.x = x;
    this.y = y;
  }
  
  public Vec2(Vec2 a) {
    this(a.x, a.y);
  }
}
