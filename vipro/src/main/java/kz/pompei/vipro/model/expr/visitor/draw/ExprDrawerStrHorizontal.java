package kz.pompei.vipro.model.expr.visitor.draw;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;

import kz.pompei.vipro.model.expr.Expr;
import kz.pompei.vipro.util.UtilInt;

public class ExprDrawerStrHorizontal implements ExprDrawer {
  private final Graphics2D g;
  private final DrawVisitorStyle style;
  private Font font;
  
  private String oper;
  
  private int operWidth;
  private int operAscent;
  private int operDescent;
  
  private ExprDrawer expr1drawer;
  private ExprDrawer expr2drawer;
  
  public ExprDrawerStrHorizontal(String oper, Expr expr1, Expr expr2, DrawVisitor drawVisitor) {
    g = drawVisitor.g;
    style = drawVisitor.style;
    
    this.oper = ' ' + oper + ' ';
    
    float size = drawVisitor.style.sizeOnLevel(drawVisitor.level);
    font = g.getFont().deriveFont(Font.BOLD, size);
    
    FontMetrics fontMetrics = g.getFontMetrics(font);
    operWidth = fontMetrics.stringWidth(this.oper);
    operAscent = fontMetrics.getAscent();
    operDescent = fontMetrics.getDescent();
    
    expr1drawer = expr1.visit(drawVisitor);
    expr2drawer = expr2.visit(drawVisitor);
  }
  
  @Override
  public int ascent() {
    int ascent1 = expr1drawer.ascent();
    int ascent2 = expr2drawer.ascent();
    return UtilInt.max(ascent1, ascent2, operAscent);
  }
  
  @Override
  public int descent() {
    int descent1 = expr1drawer.descent();
    int descent2 = expr2drawer.descent();
    return UtilInt.max(descent1, descent2, operDescent);
  }
  
  @Override
  public int width() {
    return expr1drawer.width() + operWidth + expr2drawer.width();
  }
  
  @Override
  public void drawAt(int x, int y, Point mouse) {
    g.setFont(font);
    style.applyOperStyle(g);
    g.drawString(oper, x + expr1drawer.width(), y);
    
    expr1drawer.drawAt(x, y, mouse);
    expr2drawer.drawAt(x + expr1drawer.width() + operWidth, y, mouse);
  }
}
