package kz.pompei.vipro.model.expr;

public class ExprOper1 extends Expr {
  public Oper1 oper;
  public Expr target;
  
  public ExprOper1(String id) {
    super(id);
  }
  
  @Override
  public <Ret> Ret visit(ExprVisitor<Ret> visitor) {
    return visitor.visitOper1(this);
  }
}
