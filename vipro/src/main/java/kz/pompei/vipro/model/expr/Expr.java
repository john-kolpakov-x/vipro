package kz.pompei.vipro.model.expr;

import java.util.UUID;

public abstract class Expr {
  public String id;
  
  public int cursorIndex = -1;
  
  public static final String rndId() {
    return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
  }
  
  public abstract <Ret> Ret visit(ExprVisitor<Ret> visitor);
}
