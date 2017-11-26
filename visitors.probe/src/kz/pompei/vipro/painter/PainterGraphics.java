package kz.pompei.vipro.painter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;

public class PainterGraphics implements Painter {

  private final Graphics2D g;

  public PainterGraphics(Graphics graphics) {
    g = (Graphics2D) graphics;
  }

  @Override
  public Painter create() {
    return new PainterGraphics(g.create());
  }

  @Override
  public void close() {
    g.dispose();
  }

  @Override
  public void setColor(Color color) {
    g.setColor(color);
  }

  @Override
  public void fillRect(int x, int y, int width, int height) {
    g.fillRect(x, y, width, height);
  }

  @Override
  public void drawRect(int x, int y, int width, int height) {
    g.drawRect(x, y, width, height);
  }

  @Override
  public void drawLine(int x1, int y1, int x2, int y2) {
    g.drawLine(x1, y1, x2, y2);
  }

  @Override
  public void clipRect(int x, int y, int width, int height) {
    g.clipRect(x, y, width, height);
  }

  @Override
  public void setStroke(BasicStroke basicStroke) {
    g.setStroke(basicStroke);
  }

  @Override
  public void draw(Shape shape) {
    g.draw(shape);
  }

  @Override
  public Font getFont() {
    return g.getFont();
  }

  @Override
  public void setFont(Font font) {
    g.setFont(font);
  }

  @Override
  public FontMetrics getFontMetrics() {
    return g.getFontMetrics();
  }

  @Override
  public void drawString(String text, int x, int y) {
    g.drawString(text, x, y);
  }
}
