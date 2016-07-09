package kz.pompei.vipro.display;

import java.awt.Graphics2D;

public interface DisplayPort {
  Graphics2D graphics();

  float getFontSize(int level);
}
