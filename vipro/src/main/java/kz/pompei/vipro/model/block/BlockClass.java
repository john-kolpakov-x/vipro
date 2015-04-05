package kz.pompei.vipro.model.block;

import java.util.ArrayList;
import java.util.List;

public class BlockClass extends Block {
  
  public final List<LocationableBlock> content = new ArrayList<>();
  
  @Override
  public <Ret> Ret visit(BlockVisitor<Ret> visitor) {
    return visitor.visitBlockClass(this);
  }
}
