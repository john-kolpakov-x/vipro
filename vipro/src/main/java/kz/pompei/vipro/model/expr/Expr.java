package kz.pompei.vipro.model.expr;

public abstract class Expr {
  public abstract <Ret> Ret visit(ExprVisitor<Ret> visitor);
}
