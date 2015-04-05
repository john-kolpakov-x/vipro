package kz.pompei.vipro.model.expr;

public class ExprStrConst extends Expr {
  
  public final String value;
  
  public ExprStrConst(String value) {
    this.value = value;
  }
  
  @Override
  public <Ret> Ret visit(ExprVisitor<Ret> visitor) {
    return visitor.visitStrConst(this);
  }
}
