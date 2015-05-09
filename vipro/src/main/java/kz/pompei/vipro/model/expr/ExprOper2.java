package kz.pompei.vipro.model.expr;

public class ExprOper2 extends Expr {
  public Oper2 oper;
  public Expr expr1;
  public Expr expr2;
  
  public ExprOper2(String id) {
    super(id);
  }
  
  @Override
  public <Ret> Ret visit(ExprVisitor<Ret> visitor) {
    return visitor.visitOper2(this);
  }
}
