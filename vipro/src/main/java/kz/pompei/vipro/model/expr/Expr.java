package kz.pompei.vipro.model.expr;

public abstract class Expr {
  public final String id;
  
  public Expr(String id) {
    this.id = id;
  }
  
  public abstract <Ret> Ret visit(ExprVisitor<Ret> visitor);
}
