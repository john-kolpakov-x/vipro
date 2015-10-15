package kz.pompei.vipro.model;

import kz.pompei.vipro.visitors.Visitor;

public interface Expr {
  public <T> T visit(Visitor<T> vis);
}
