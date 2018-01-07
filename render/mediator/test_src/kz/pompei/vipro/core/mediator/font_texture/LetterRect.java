package kz.pompei.vipro.core.mediator.font_texture;

public class LetterRect {
  public final int x, y, width, height;

  public LetterRect(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  public float widthForHeight(float height) {
    return (float) width / (float) this.height * height;
  }
}
