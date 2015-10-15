package kz.pompei.vipro.model;

import kz.pompei.vipro.visitors.Visitor;

public class Oper2 implements Expr {
  public final Oper2Type oper;
  public final Expr a, b;
  
  public Oper2(Oper2Type oper, Expr a, Expr b) {
    this.oper = oper;
    this.a = a;
    this.b = b;
  }
  
  @Override
  public <T> T visit(Visitor<T> vis) {
    return vis.visitOper2(this);
  }
}
