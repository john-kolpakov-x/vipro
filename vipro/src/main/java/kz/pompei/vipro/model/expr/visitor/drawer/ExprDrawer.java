package kz.pompei.vipro.model.expr.visitor.drawer;


public interface ExprDrawer {
  int getWidth();
  
  int getAscent();
  
  int getDescent();
  
  void draw(int x, int y);
}
