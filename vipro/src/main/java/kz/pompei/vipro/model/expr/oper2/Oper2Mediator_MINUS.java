package kz.pompei.vipro.model.expr.oper2;

import kz.pompei.vipro.model.expr.Expr;
import kz.pompei.vipro.model.expr.visitor.draw.DrawVisitor;
import kz.pompei.vipro.model.expr.visitor.draw.ExprDrawer;
import kz.pompei.vipro.model.expr.visitor.draw.ExprDrawer_MINUS;

public class Oper2Mediator_MINUS implements Oper2Mediator {
  @Override
  public ExprDrawer createDrawer(Expr expr1, Expr expr2, DrawVisitor drawVisitor) {
    return new ExprDrawer_MINUS(expr1, expr2, drawVisitor);
  }
}
