package kz.pompei.vipro.model.expr.visitor.draw;

import java.awt.Color;
import java.awt.Graphics2D;

public class DrawVisitorStyleDefault implements DrawVisitorStyle {
  
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
  
  @Override
  public void applyVarStyle(Graphics2D g) {
    g.setColor(Color.BLACK);
  }
  
  @Override
  public void applyHoverBorderStyle(Graphics2D g) {
    g.setColor(new Color(159, 201, 247));
  }
  
  @Override
  public void applyHoverStyle(Graphics2D g) {
    g.setColor(new Color(206, 222, 238));
  }
  
  @Override
  public void applyStrConstStyle(Graphics2D g) {
    g.setColor(new Color(234, 75, 36));
  }
  
  @Override
  public void applyOperStyle(Graphics2D g) {
    g.setColor(new Color(59, 89, 153));
  }
}
