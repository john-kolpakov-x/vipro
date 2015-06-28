package kz.pompei.vipro.model.expr;

public enum ExprExample {
  ONE {
    @Override
    public Expr create() {
      ExprVar ret = new ExprVar();
      ret.name = "One";
      return ret;
    }
  },
  
  ExprVar {
    @Override
    public Expr create() {
      ExprVar ret = new ExprVar();
      ret.name = "Variable_A";
      return ret;
    }
  },
  
  ExprStrConst {
    @Override
    public Expr create() {
      ExprStrConst ret = new ExprStrConst();
      ret.value = "Привет всем";
      return ret;
    }
  };
  
  public abstract Expr create();
}
