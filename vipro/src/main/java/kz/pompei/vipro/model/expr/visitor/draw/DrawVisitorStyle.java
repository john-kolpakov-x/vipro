package kz.pompei.vipro.model.expr.visitor.draw;

import java.awt.Graphics2D;

public interface DrawVisitorStyle {
  float sizeOnLevel(int level);
  
  void applyVarStyle(Graphics2D g);
  
  void applyHoverBorderStyle(Graphics2D g);
  
  void applyHoverStyle(Graphics2D g);
  
  void applyStrConstStyle(Graphics2D g);
  
  void applyOperStyle(Graphics2D g);
}
