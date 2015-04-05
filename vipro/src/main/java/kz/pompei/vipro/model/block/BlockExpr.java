package kz.pompei.vipro.model.block;

public class BlockExpr extends LocationableBlock {
  
  @Override
  public <Ret> Ret visit(BlockVisitor<Ret> visitor) {
    return visitor.visitExprBlock(this);
  }
  
}
