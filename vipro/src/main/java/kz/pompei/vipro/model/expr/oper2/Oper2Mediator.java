package kz.pompei.vipro.model.expr.oper2;

import kz.pompei.vipro.model.expr.Expr;
import kz.pompei.vipro.model.expr.visitor.draw.DrawVisitor;
import kz.pompei.vipro.model.expr.visitor.draw.ExprDrawer;

public interface Oper2Mediator {
  
  ExprDrawer createDrawer(Expr expr1, Expr expr2, DrawVisitor drawVisitor);
  
}
