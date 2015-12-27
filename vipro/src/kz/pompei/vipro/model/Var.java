package kz.pompei.vipro.model;

import kz.pompei.vipro.visitors.Visitor;

public class Var implements Expr {
  public final String name;
  
  public Var(String name) {
    this.name = name;
  }
  
  @Override
  public <T> T visit(Visitor<T> vis) {
    return vis.visitVar(this);
  }
}
