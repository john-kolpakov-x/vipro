package kz.pompei.vipro.model;

public class Size {
  public int width, height;
  
  public Size() {
    this(0, 0);
  }
  
  public Size(Size a) {
    this(a.width, a.height);
  }
  
  public Size(int width, int height) {
    this.width = width;
    this.height = height;
  }
  
  public void setSize(int width, int height) {
    this.width = width;
    this.height = height;
  }
}
