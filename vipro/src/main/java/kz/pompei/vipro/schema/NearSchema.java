package kz.pompei.vipro.schema;

import java.awt.Point;

public class NearSchema {
  public int x, y;
  public int r;
  
  public NearSchema(Point near, int r) {
    x = near.x;
    y = near.y;
    this.r = r;
  }
}
