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
  },
  
  ExprPlus {
    @Override
    public Expr create() {
      ExprStrConst expr1 = new ExprStrConst();
      expr1.value = "Привет всем";
      
      ExprVar expr2 = new ExprVar();
      expr2.name = "Variable_X";
      
      ExprOper2 expr3 = new ExprOper2();
      expr3.expr1 = expr2;
      expr3.expr2 = expr1;
      expr3.oper = Oper2.PLUS;
      
      ExprVar expr4 = new ExprVar();
      expr4.name = "Pomidor";
      
      ExprOper2 expr5 = new ExprOper2();
      expr5.expr1 = expr3;
      expr5.expr2 = expr4;
      expr5.oper = Oper2.MINUS;
      
      return expr5;
    }
  },
  
  //
  ;
  
  public abstract Expr create();
}
