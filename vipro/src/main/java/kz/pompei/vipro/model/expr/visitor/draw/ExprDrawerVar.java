package kz.pompei.vipro.model.expr.visitor.draw;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import kz.pompei.vipro.model.expr.ExprVar;

class ExprDrawerVar implements ExprDrawer {
  
  private final Font font;
  private final String name;
  private final int width;
  private final int ascent;
  private final int descent;
  private final Graphics2D g;
  
  public ExprDrawerVar(ExprVar exprVar, DrawVisitor drawVisitor) {
    g = drawVisitor.g;
    
    float size = drawVisitor.levelFont.sizeOnLevel(drawVisitor.level);
    
    font = g.getFont().deriveFont(Font.PLAIN, size);
    name = exprVar.name;
    
    FontMetrics fontMetrics = g.getFontMetrics(font);
    width = fontMetrics.stringWidth(name);
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
  public void drawAt(int x, int y) {
    g.setFont(font);
    g.setColor(Color.BLACK);
    g.drawString(name, x, y);
  }
}
