package kz.pompei.vipro.core.mediator.font_texture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FontTextureGenerator {
  private final BufferedImage image;

  private final Letters letters = new Letters();

  public FontTextureGenerator(int width, int height) {
    image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
  }

  public void writeTo(File outFile) throws IOException {
    ImageIO.write(image, "png", outFile);
  }

  public void accept(GeneratorAcceptor acceptor) {
    Graphics2D graphics = image.createGraphics();

    graphics.setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_ON
    );
    graphics.setRenderingHint(
        RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON
    );

    graphics.setFont(graphics.getFont().deriveFont(80f));
    graphics.setColor(Color.BLACK);

    try {

      final int lineDist = 3, charDist = 2;
      final int width = image.getWidth();
      final int height = image.getHeight();
      final FontMetrics fontMetrics = graphics.getFontMetrics();
      final int lineHeight = fontMetrics.getHeight();

      acceptor.accept(new GeneratorAppender() {
        int x = charDist, y = lineDist;

        LetterRect paintChar(char c) {
          int w = fontMetrics.charWidth(c);
          if (x + w + charDist > width) {
            y += lineHeight + lineDist;
            if (y + lineHeight + lineDist > height) throw new RuntimeException("Cannot add char: no space in image");
            x = charDist;
          }
          graphics.drawString("" + c, x, y + lineHeight);
          LetterRect ret = new LetterRect(x, y, w, lineHeight);
          x += w + charDist;
          return ret;
        }

        @Override
        public void append(CharSequence chars) {
          for (int i = 0, n = chars.length(); i < n; i++) {
            char c = chars.charAt(i);
            letters.put(c, paintChar(c));
          }
        }

        @Override
        public void appendDefault(char defaultChar) {
          letters.putDefault(paintChar(defaultChar));
        }
      });

    } finally {
      graphics.dispose();
    }
  }

  public void drawStr(Graphics2D graphics, CharSequence str, float x, float y, float height) {
    for (int i = 0, n = str.length(); i < n; i++) {
      char c = str.charAt(i);

      LetterRect rect = letters.getRect(c);
      float width = rect.widthForHeight(height);

      BufferedImage sub = image.getSubimage(rect.x, rect.y, rect.width, rect.height);
      graphics.drawImage(sub, Math.round(x), Math.round(y), Math.round(width), Math.round(height), null);
      x += width;
    }
  }
}
