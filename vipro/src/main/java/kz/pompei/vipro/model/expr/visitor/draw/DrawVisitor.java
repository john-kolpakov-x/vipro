package kz.pompei.vipro.model.expr.visitor.draw;

import java.awt.Graphics2D;

import kz.pompei.vipro.model.expr.ExprOper1;
import kz.pompei.vipro.model.expr.ExprOper2;
import kz.pompei.vipro.model.expr.ExprStrConst;
import kz.pompei.vipro.model.expr.ExprVar;
import kz.pompei.vipro.model.expr.ExprVisitor;

public class DrawVisitor implements ExprVisitor<ExprDrawer> {
  final Graphics2D g;
  int level;
  DrawVisitorStyle style;
  
  public DrawVisitor(Graphics2D g, int level, DrawVisitorStyle style) {
    this.g = g;
    this.level = level;
    this.style = style;
  }
  
  @Override
  public ExprDrawer visitVar(ExprVar exprVar) {
    return new ExprDrawerVar(exprVar, this);
  }
  
  @Override
  public ExprDrawer visitStrConst(ExprStrConst exprStrConst) {
    return new ExprDrawerStrConst(exprStrConst, this);
  }
  
  @Override
  public ExprDrawer visitOper1(ExprOper1 exprOper1) {
    return new ExprDrawerOper1(exprOper1, this);
  }
  
  @Override
  public ExprDrawer visitOper2(ExprOper2 exprOper2) {
    return new ExprDrawerOper2(exprOper2, this);
  }
}
