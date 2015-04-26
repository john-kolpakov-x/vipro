package kz.pompei.vipro.model.expr.visitor.drawer;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import kz.pompei.vipro.model.expr.ExprOper1;
import kz.pompei.vipro.model.expr.ExprOper2;
import kz.pompei.vipro.model.expr.ExprStrConst;
import kz.pompei.vipro.model.expr.ExprVar;
import kz.pompei.vipro.model.expr.ExprVisitor;

public class ExprDrawerCreatorVisitor implements ExprVisitor<ExprDrawer> {
  
  private int level = 0;
  private final Graphics2D g;
  
  public ExprDrawerCreatorVisitor(Graphics2D g) {
    this.g = g;
  }
  
  @Override
  public ExprDrawer visitVar(final ExprVar exprVar) {
    final float fontSize = SizeUtil.fontSizeByLevel(level);
    FontMetrics fm = g.getFontMetrics(g.getFont().deriveFont(fontSize));
    
    final int width = fm.stringWidth(exprVar.name);
    final int ascent = fm.getAscent();
    final int descent = fm.getDescent();
    
    return new ExprDrawer() {
      @Override
      public int getWidth() {
        return width;
      }
      
      @Override
      public int getDescent() {
        return descent;
      }
      
      @Override
      public int getAscent() {
        return ascent;
      }
      
      @Override
      public void draw(int x, int y) {
        g.setFont(g.getFont().deriveFont(fontSize));
        g.setColor(Color.BLACK);
        g.drawString(exprVar.name, x, y);
      }
    };
  }
  
  @Override
  public ExprDrawer visitStrConst(final ExprStrConst exprStrConst) {
    final float fontSize = SizeUtil.fontSizeByLevel(level);
    FontMetrics fm = g.getFontMetrics(g.getFont().deriveFont(fontSize));
    
    final int width = fm.stringWidth(exprStrConst.value);
    final int ascent = fm.getAscent();
    final int descent = fm.getDescent();
    
    return new ExprDrawer() {
      @Override
      public int getWidth() {
        return width;
      }
      
      @Override
      public int getDescent() {
        return descent;
      }
      
      @Override
      public int getAscent() {
        return ascent;
      }
      
      @Override
      public void draw(int x, int y) {
        g.setFont(g.getFont().deriveFont(fontSize));
        g.setColor(Color.BLUE);
        g.drawString(exprStrConst.value, x, y);
      }
    };
  }
  
  @Override
  public ExprDrawer visitOper1(ExprOper1 exprOper1) {
    ExprDrawer targetDrawer = exprOper1.target.visit(this);
    
    switch (exprOper1.oper) {
    case MINUS:
      return new PrefixOper1Drawer("-", targetDrawer, level);
      
    case NOT:
      return new NotOper1(targetDrawer, level);
      
    default:
      throw new UnknownOper1(exprOper1.oper);
    }
  }
  
  @Override
  public ExprDrawer visitOper2(ExprOper2 exprOper2) {
    ExprDrawer exprDrawer1 = exprOper2.expr1.visit(this);
    ExprDrawer exprDrawer2 = exprOper2.expr2.visit(this);
    
    switch (exprOper2.oper) {
    case MINUS:
      return new Oper2midDrawer("-", exprDrawer1, exprDrawer2, level);
      
    case PLUS:
      return new Oper2midDrawer("+", exprDrawer1, exprDrawer2, level);
      
    case DIV:
      return new DivDrawer(exprDrawer1, exprDrawer2, level);
      
    case AND:
      return new AndDrawer(exprDrawer1, exprDrawer2, level);
      
    case MUL:
      return new MulDrawer(exprDrawer1, exprDrawer2, level);
      
    case OR:
      return new OrDrawer(exprDrawer1, exprDrawer2, level);
      
    default:
      throw new UnknownOper2(exprOper2.oper);
    }
  }
}
