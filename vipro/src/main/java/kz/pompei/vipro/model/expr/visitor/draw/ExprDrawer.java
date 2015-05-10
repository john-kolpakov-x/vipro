package kz.pompei.vipro.model.expr.visitor.draw;


public interface ExprDrawer {
  int ascent();
  
  int descent();
  
  int width();
  
  void drawAt(int x, int y);
}
