package kz.pompei.vipro.model;

public class BlockPoint {
  public int x, y;
  public final Block owner;

  public Connection connection;

  public BlockPoint(Block owner) {
    this.owner = owner;
  }
}
