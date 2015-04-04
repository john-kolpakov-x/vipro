package kz.pompei.vipro.model;

import java.util.ArrayList;
import java.util.List;

public class BlockClass extends Block {
  
  public final List<MethodDefinition> methodList = new ArrayList<>();
  
  @Override
  public <Ret> Ret visit(BlockVisitor<Ret> visitor) {
    return visitor.visitBlockClass(this);
  }
  
}
