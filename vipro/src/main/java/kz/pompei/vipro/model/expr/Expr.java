package kz.pompei.vipro.model.expr;

import java.util.UUID;

public abstract class Expr {
  public final String id;
  
  public int cursorIndex = -1;
  
  public Expr(String id) {
    this.id = id == null ? rndId() : id;
  }
  
  public static final String rndId() {
    return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
  }
  
  public abstract <Ret> Ret visit(ExprVisitor<Ret> visitor);
}
