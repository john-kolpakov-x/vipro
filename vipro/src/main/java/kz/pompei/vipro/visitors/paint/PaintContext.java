package kz.pompei.vipro.visitors.paint;

import java.awt.Point;

public interface PaintContext {
  double scaleFactor();
  
  Vec2 fromScreen(Point p);
  
  Point toScreen(Vec2 v);
}
