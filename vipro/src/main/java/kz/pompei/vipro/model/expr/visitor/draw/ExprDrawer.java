package kz.pompei.vipro.model.expr.visitor.draw;

import java.awt.Point;

public interface ExprDrawer {
  int ascent();
  
  int descent();
  
  int width();
  
  void drawAt(int x, int y, Point mouse);
}
