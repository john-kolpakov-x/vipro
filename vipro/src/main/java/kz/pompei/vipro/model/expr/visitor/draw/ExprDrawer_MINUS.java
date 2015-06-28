package kz.pompei.vipro.model.expr.visitor.draw;

import kz.pompei.vipro.model.expr.Expr;

public class ExprDrawer_MINUS extends ExprDrawerStrHorizontal {
  
  public ExprDrawer_MINUS(Expr expr1, Expr expr2, DrawVisitor drawVisitor) {
    super("-", expr1, expr2, drawVisitor);
  }
  
}
