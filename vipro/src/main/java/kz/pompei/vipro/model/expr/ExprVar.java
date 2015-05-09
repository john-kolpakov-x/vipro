package kz.pompei.vipro.model.expr;

public class ExprVar extends Expr {
  public String name;
  
  public ExprVar(String id) {
    super(id);
  }
  
  @Override
  public <Ret> Ret visit(ExprVisitor<Ret> visitor) {
    return visitor.visitVar(this);
  }
}
