package kz.pompei.vipro.model.expr;

public class ExprStrConst extends Expr {
  
  public String value;
  
  public ExprStrConst(String id) {
    super(id);
  }
  
  @Override
  public <Ret> Ret visit(ExprVisitor<Ret> visitor) {
    return visitor.visitStrConst(this);
  }
}
