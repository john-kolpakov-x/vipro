package kz.pompei.vipro.model;

public abstract class Block {
  public abstract <Ret> Ret visit(BlockVisitor<Ret> visitor);
}
