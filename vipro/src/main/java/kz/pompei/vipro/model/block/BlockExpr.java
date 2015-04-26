package kz.pompei.vipro.model.block;

import kz.pompei.vipro.model.expr.Expr;

public class BlockExpr extends LocationableBlock {
  public Expr expr;
  
  @Override
  public <Ret> Ret visit(BlockVisitor<Ret> visitor) {
    return visitor.visitExprBlock(this);
  }
}
