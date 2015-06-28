package kz.pompei.vipro.model.expr.oper2;

import kz.pompei.vipro.model.expr.Expr;
import kz.pompei.vipro.model.expr.visitor.draw.DrawVisitor;
import kz.pompei.vipro.model.expr.visitor.draw.ExprDrawer;
import kz.pompei.vipro.model.expr.visitor.draw.oper2.ExprDrawer_DIV;

public class Oper2Mediator_DIV implements Oper2Mediator {
  @Override
  public ExprDrawer createDrawer(Expr expr1, Expr expr2, DrawVisitor drawVisitor) {
    return new ExprDrawer_DIV(expr1, expr2, drawVisitor);
  }
}
