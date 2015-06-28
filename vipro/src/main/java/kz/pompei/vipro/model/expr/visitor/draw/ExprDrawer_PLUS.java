package kz.pompei.vipro.model.expr.visitor.draw;

import kz.pompei.vipro.model.expr.Expr;

public class ExprDrawer_PLUS extends ExprDrawerStrHorizontal {
  
  public ExprDrawer_PLUS(Expr expr1, Expr expr2, DrawVisitor drawVisitor) {
    super("+", expr1, expr2, drawVisitor);
  }
  
}
