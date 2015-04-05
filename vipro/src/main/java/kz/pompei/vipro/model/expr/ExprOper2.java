package kz.pompei.vipro.model.expr;

public class ExprOper2 extends Expr {
  public final Oper2 oper;
  public final Expr expr1;
  public final Expr expr2;
  
  public ExprOper2(Oper2 oper, Expr expr1, Expr expr2) {
    super();
    this.oper = oper;
    this.expr1 = expr1;
    this.expr2 = expr2;
  }
  
  @Override
  public <Ret> Ret visit(ExprVisitor<Ret> visitor) {
    return visitor.visitOper2(this);
  }
}
