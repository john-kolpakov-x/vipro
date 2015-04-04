package kz.pompei.vipro.model;

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
}
