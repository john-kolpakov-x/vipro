package kz.pompei.vipro.model.expr;

public class ExprVar extends Expr {
  public String name;
  
  @Override
  public <Ret> Ret visit(ExprVisitor<Ret> visitor) {
    return visitor.visitVar(this);
  }
}
