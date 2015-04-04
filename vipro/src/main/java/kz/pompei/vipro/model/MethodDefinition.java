package kz.pompei.vipro.model;

public class MethodDefinition extends LocationableBlock {
  public String name;
  
  @Override
  public <Ret> Ret visit(BlockVisitor<Ret> visitor) {
    return visitor.visitMethodDefinition(this);
  }
}
