package kz.pompei.vipro.model.expr.visitor.draw;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;

import kz.pompei.vipro.model.expr.ExprStrConst;

class ExprDrawerStrConst implements ExprDrawer {
  
  private final Font font;
  private final String value;
  private final int width;
  private final int ascent;
  private final int descent;
  private final Graphics2D g;
  private final DrawVisitorStyle style;
  
  public ExprDrawerStrConst(ExprStrConst exprStrConst, DrawVisitor drawVisitor) {
    g = drawVisitor.g;
    
    style = drawVisitor.style;
    
    float size = drawVisitor.style.sizeOnLevel(drawVisitor.level);
    
    font = g.getFont().deriveFont(Font.PLAIN, size);
    value = exprStrConst.value;
    
    FontMetrics fontMetrics = g.getFontMetrics(font);
    width = fontMetrics.stringWidth(value);
    ascent = fontMetrics.getAscent();
    descent = fontMetrics.getDescent();
  }
  
  @Override
  public int ascent() {
    return ascent;
  }
  
  @Override
  public int descent() {
    return descent;
  }
  
  @Override
  public int width() {
    return width;
  }
  
  @Override
  public void drawAt(int x, int y, Point mouse) {
    
    if (mouse != null) {
      int H = ascent + descent;
      int rx = x - 1, ry = y - H - 1, rw = width + 2, rh = H + 2;
      if (isIn(mouse, rx, ry, rw, rh)) {
        
        style.applyHoverStyle(g);
        g.fillRect(rx + 1, ry + 1, rw - 1, rh - 1);
        
        style.applyHoverBorderStyle(g);
        g.drawRect(rx, ry, rw, rh);
      }
    }
    
    g.setFont(font);
    style.applyStrConstStyle(g);
    g.drawString(value, x, y);
  }
  
  private static boolean isIn(Point p, int x, int y, int width, int height) {
    if (p == null) return false;
    
    if (p.x < x) return false;
    if (p.y < y) return false;
    
    if (p.x > x + width) return false;
    if (p.y > y + height) return false;
    
    return true;
  }
  
}
