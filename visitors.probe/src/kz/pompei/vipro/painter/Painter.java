package kz.pompei.vipro.painter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Shape;

public interface Painter extends AutoCloseable {
  Painter create();

  @Override
  void close();

  void setColor(Color color);

  void fillRect(int x, int y, int width, int height);

  void drawRect(int x, int y, int width, int height);

  void drawLine(int x1, int y1, int x2, int y2);

  void clipRect(int x, int y, int width, int height);

  void setStroke(BasicStroke basicStroke);

  void draw(Shape shape);

  Font getFont();

  void setFont(Font font);

  FontMetrics getFontMetrics();

  void drawString(String text, int x, int y);
}
