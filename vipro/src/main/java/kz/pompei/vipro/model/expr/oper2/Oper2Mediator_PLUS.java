package kz.pompei.vipro.model.expr.oper2;

import kz.pompei.vipro.model.expr.Expr;
import kz.pompei.vipro.model.expr.visitor.draw.DrawVisitor;
import kz.pompei.vipro.model.expr.visitor.draw.ExprDrawer;
import kz.pompei.vipro.model.expr.visitor.draw.ExprDrawer_PLUS;

public class Oper2Mediator_PLUS implements Oper2Mediator {
  @Override
  public ExprDrawer createDrawer(Expr expr1, Expr expr2, DrawVisitor drawVisitor) {
    return new ExprDrawer_PLUS(expr1, expr2, drawVisitor);
  }
}