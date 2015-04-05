package kz.pompei.vipro.model.block;

import java.util.UUID;

public abstract class Block {
  
  public final String uid;
  
  public Block() {
    this(UUID.randomUUID().toString());
  }
  
  public Block(String uid) {
    this.uid = uid;
  }
  
  public abstract <Ret> Ret visit(BlockVisitor<Ret> visitor);
}
