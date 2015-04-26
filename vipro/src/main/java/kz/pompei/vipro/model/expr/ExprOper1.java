package kz.pompei.vipro.model.expr;

public class ExprOper1 extends Expr {
  public final Oper1 oper;
  public final Expr target;
  
  public ExprOper1(Oper1 oper, Expr target) {
    this.oper = oper;
    this.target = target;
  }
  
  @Override
  public <Ret> Ret visit(ExprVisitor<Ret> visitor) {
    return visitor.visitOper1(this);
  }
}