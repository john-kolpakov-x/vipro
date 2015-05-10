package kz.pompei.vipro.model.expr.visitor.draw;

public class DrawVisitorContextDefault implements DrawVisitorContext {
  
  public int offset = 0;
  
  @Override
  public float sizeOnLevel(int level) {
    
    level += offset;
    
    if (level == 0) return 14;
    if (level == 1) return 13;
    if (level == 2) return 12;
    if (level == 3) return 11;
    if (level > 3) return 10;
    
    if (level == -1) return 15;
    if (level == -2) return 16;
    if (level == -3) return 17;
    if (level == -4) return 18;
    if (level == -5) return 19;
    if (level == -6) return 20;
    if (level == -7) return 21;
    
    return 22;
  }
}
