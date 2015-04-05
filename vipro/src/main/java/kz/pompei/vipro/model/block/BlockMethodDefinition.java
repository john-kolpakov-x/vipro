package kz.pompei.vipro.model.block;

public class BlockMethodDefinition extends LocationableBlock {
  public String name;
  
  @Override
  public <Ret> Ret visit(BlockVisitor<Ret> visitor) {
    return visitor.visitMethodDefinition(this);
  }
}
