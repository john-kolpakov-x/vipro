package kz.pompei.vipro.util;

import java.awt.Point;

public class Place {
  public Point location;
  public Size size;
  
  public int getLeft() {
    norm();
    if (size.width >= 0) return location.x;
    return location.x + size.width;
  }
  
  public int getRight() {
    norm();
    if (size.width <= 0) return location.x;
    return location.x + size.width;
  }
  
  public int getTop() {
    norm();
    if (size.height >= 0) return location.y;
    return location.y + size.height;
  }
  
  public int getBottom() {
    norm();
    if (size.height <= 0) return location.y;
    return location.y + size.height;
  }
  
  public void norm() {
    if (location == null) location = new Point();
    if (size == null) size = new Size();
  }
  
  public Place() {
    this(new Point(), new Size());
  }
  
  public Place(Point location, Size size) {
    this.location = location;
    this.size = size;
  }
  
  public Place(int x, int y, int width, int height) {
    this(new Point(x, y), new Size(width, height));
  }
  
  public void unionWithMe(Place p) {
    if (p == null) return;
    norm();
    p.norm();
    
    int left = min(getLeft(), p.getLeft());
    int right = max(getRight(), p.getRight());
    int top = min(getTop(), p.getTop());
    int bottom = max(getBottom(), p.getBottom());
    
    location.setLocation(left, top);
    size.setSize(right - left, bottom - top);
  }
  
  private static int min(int a, int b) {
    return a < b ? a : b;
  }
  
  private static int max(int a, int b) {
    return a > b ? a : b;
  }
  
  public Point near(Point point, int dist) {
    if (point == null) return null;
    
    int left = getLeft();
    int right = getRight();
    int top = getTop();
    int bottom = getBottom();
    
    Point near1 = near(left, top, right, top, point, dist);
    Point near2 = near(right, top, right, bottom, point, dist);
    Point near3 = near(left, top, left, bottom, point, dist);
    Point near4 = near(left, bottom, right, bottom, point, dist);
    
    return GeomUtil.nearest(point, near1, near2, near3, near4);
  }
  
  private static Point near(int x1, int y1, int x2, int y2, Point point, double dist) {
    Vec2 A = new Vec2(x1, y1);
    Vec2 B = new Vec2(x2, y2);
    Vec2 P = new Vec2(point);
    double t = GeomUtil.projKoor(P, A, B);
    if (t < 0) t = 0;
    if (t > 1) t = 1;
    
    Vec2 K = A.plus(B.minus(A).mul(t));
    
    double len = K.minus(P).len();
    if (len > dist) return null;
    return K.getPoint();
  }
}
