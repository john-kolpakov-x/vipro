package kz.pompei.vipro.display;

import kz.pompei.vipro.painter.Painter;

public interface DisplayPort {
  Painter graphics();

  float getFontSize(int level);
}
