package kz.pompei.vipro.model.expr;

import kz.pompei.vipro.model.expr.oper2.Oper2Mediator;
import kz.pompei.vipro.model.expr.oper2.Oper2Mediator_AND;
import kz.pompei.vipro.model.expr.oper2.Oper2Mediator_DIV;
import kz.pompei.vipro.model.expr.oper2.Oper2Mediator_MINUS;
import kz.pompei.vipro.model.expr.oper2.Oper2Mediator_MUL;
import kz.pompei.vipro.model.expr.oper2.Oper2Mediator_OR;
import kz.pompei.vipro.model.expr.oper2.Oper2Mediator_PLUS;

public enum Oper2 {
  PLUS(new Oper2Mediator_PLUS()), //
  MINUS(new Oper2Mediator_MINUS()), //
  DIV(new Oper2Mediator_DIV()), //
  MUL(new Oper2Mediator_MUL()), //
  AND(new Oper2Mediator_AND()), //
  OR(new Oper2Mediator_OR()), //
  ;
  
  public final Oper2Mediator mediator;
  
  Oper2(Oper2Mediator mediator) {
    this.mediator = mediator;
  }
}
