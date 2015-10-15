package kz.pompei.vipro.visitors.paint;

import java.awt.Graphics2D;

public interface Painter {
  void setGraphics(Graphics2D g, PaintContext paintContext);
  
  Selector select(Vec2 mouse);
  
  Selector getSelector();
  
  void paintTo(Vec2 to);
  
  Size size();
}
