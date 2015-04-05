package kz.pompei.vipro.util;

import java.awt.Point;

public class Vec2 {
  public final double x, y;
  
  public Vec2(double x, double y) {
    this.x = x;
    this.y = y;
  }
  
  public Vec2() {
    this(0, 0);
  }
  
  public Vec2(Vec2 a) {
    this(a == null ? 0 : a.x, a == null ? 0 : a.y);
  }
  
  public Vec2(Point point) {
    this(point.x, point.y);
  }
  
  public double sqr() {
    return x * x + y * y;
  }
  
  public double len() {
    return Math.sqrt(sqr());
  }
  
  public Vec2 minus(Vec2 a) {
    return new Vec2(x - a.x, y - a.y);
  }
  
  public Vec2 plus(Vec2 a) {
    return new Vec2(x + a.x, y + a.y);
  }
  
  public Vec2 mul(double a) {
    return new Vec2(x * a, y * a);
  }
  
  public Vec2 div(double a) {
    return new Vec2(x / a, y / a);
  }
  
  public double mul(Vec2 a) {
    return x * a.x + y * a.y;
  }
  
  public Vec2 norm() {
    return div(len());
  }
  
  public Point getPoint() {
    return new Point((int)Math.floor(x + 0.5), (int)Math.floor(y + 0.5));
  }
}
