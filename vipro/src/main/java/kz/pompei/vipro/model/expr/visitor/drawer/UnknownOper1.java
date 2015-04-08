package kz.pompei.vipro.model.expr.visitor.drawer;

import kz.pompei.vipro.model.expr.Oper1;

public class UnknownOper1 extends RuntimeException {
  
  public UnknownOper1(Oper1 oper) {
    super("" + oper);
  }
}
