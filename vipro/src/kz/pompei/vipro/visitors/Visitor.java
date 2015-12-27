package kz.pompei.vipro.visitors;

import kz.pompei.vipro.model.Oper2;
import kz.pompei.vipro.model.Var;

public interface Visitor<T> {
  T visitOper2(Oper2 oper2);
  
  T visitVar(Var var);
}
