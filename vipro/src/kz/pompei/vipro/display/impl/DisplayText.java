package kz.pompei.vipro.display.impl;

import kz.pompei.vipro.display.DisplayExpr;
import kz.pompei.vipro.display.DisplayPort;
import kz.pompei.vipro.display.Size;
import kz.pompei.vipro.painter.Painter;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

public class DisplayText implements DisplayExpr {
  public final int level;
  public final String text;
  public final Color color;
  public final boolean bold;
  public final boolean italic;
  private DisplayPort port;

  public DisplayText(int level, String text, Color color, boolean bold, boolean italic) {
    this.level = level;
    this.text = text;
    this.color = color;
    this.bold = bold;
    this.italic = italic;
  }

  @Override
  public void setPort(DisplayPort port) {
    this.port = port;
  }

  @Override
  public void reset() {
    size = null;
  }

  private void prepare(Painter g) {
    g.setColor(color);
    g.setFont(g.getFont()
      .deriveFont(port.getFontSize(level))
      .deriveFont((bold ? Font.BOLD : 0) | (italic ? Font.ITALIC : 0))
    );
  }

  @Override
  public void displayTo(int x, int y) {
    size();
    try (Painter g = port.graphics().create()) {
      prepare(g);
      g.drawString(text, x, y);
    }
  }

  private Size size = null;

  @Override
  public Size size() {
    if (size != null) return size;

    try (Painter g = port.graphics().create()) {
      prepare(g);

      FontMetrics fm = g.getFontMetrics();
      int top = fm.getAscent();
      int bottom = fm.getDescent();
      int width = fm.stringWidth(text);

      return size = new Size(top, bottom, width);
    }
  }
}
