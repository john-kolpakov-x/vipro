package kz.pompei.vipro.model.expr.visitor.draw.oper2;

import kz.pompei.vipro.model.expr.Expr;
import kz.pompei.vipro.model.expr.visitor.draw.DrawVisitor;

public class ExprDrawer_PLUS extends ExprDrawerStrHorizontal {
  
  public ExprDrawer_PLUS(Expr expr1, Expr expr2, DrawVisitor drawVisitor) {
    super("+", expr1, expr2, drawVisitor);
  }
  
}
