package kz.pompei.vipro.model.expr;

public enum ExprExample {
  ONE {
    @Override
    public Expr create() {
      ExprVar ret = new ExprVar(null);
      ret.name = "one";
      return ret;
    }
  },
  
  TWO {
    @Override
    public Expr create() {
      ExprVar ret = new ExprVar(null);
      ret.name = "two";
      return ret;
    }
  };
  
  public abstract Expr create();
}
