package kz.pompei.vipro.display.impl;

import kz.pompei.vipro.display.DisplayExpr;
import kz.pompei.vipro.display.DisplayPort;
import kz.pompei.vipro.display.Size;
import kz.pompei.vipro.painter.Painter;

import java.awt.Color;

public class DisplayRect implements DisplayExpr {

  private final Color border;
  private final Color background;
  private final Size size;
  private DisplayPort port;

  public DisplayRect(Size size, Color border, Color background) {
    this.size = size;
    this.border = border;
    this.background = background;
  }

  @Override
  public void setPort(DisplayPort port) {
    this.port = port;
  }

  @Override
  public void reset() {
  }

  @Override
  public void displayTo(int x, int y) {
    try (Painter g = port.graphics().create()) {

      if (background != null) {
        g.setColor(background);
        g.fillRect(x, y - size.top, size.width, size.height());
      }

      if (border != null) {
        g.setColor(border);
        g.drawRect(x, y - size.top, size.width, size.height());
        g.drawLine(x, y, x + size.width, y);
      }

    }
  }

  @Override
  public Size size() {
    return size;
  }
}
