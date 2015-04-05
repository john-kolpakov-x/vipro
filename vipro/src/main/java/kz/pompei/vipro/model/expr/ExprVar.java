package kz.pompei.vipro.model.expr;

public class ExprVar extends Expr {
  public final String name;
  
  public ExprVar(String name) {
    this.name = name;
  }
  
  @Override
  public <Ret> Ret visit(ExprVisitor<Ret> visitor) {
    return visitor.visitVar(this);
  }
}
