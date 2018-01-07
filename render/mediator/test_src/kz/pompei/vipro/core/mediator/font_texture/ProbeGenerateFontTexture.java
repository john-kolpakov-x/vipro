package kz.pompei.vipro.core.mediator.font_texture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ProbeGenerateFontTexture {

  private static final String S1 = "abcdefghijklmnopqrstuvwxyz";
  private static final String S2 = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
  private static final String S3 = "0123456789";
  private static final String S4 = "~`!@#$%^&*()_+-={}[]:\"|;'\\<>?,./";
  private static final char DEFAULT = '?';
  private static final String S5 = " №";

  public static void main(String[] args) throws Exception {
    File outFile = new File("build/font_texture.png");

    outFile.getParentFile().mkdirs();

    FontTextureGenerator gen = new FontTextureGenerator(1024, 1024);

    gen.accept(appender -> {
      appender.append(S1);
      appender.append(S1.toUpperCase());
      appender.append(S2);
      appender.append(S2.toUpperCase());
      appender.append(S3);
      appender.append(S4);
      appender.appendDefault(DEFAULT);
      appender.append(S5);
    });


    gen.writeTo(outFile);

    File helloFile = new File("build/hello.png");
    BufferedImage image = new BufferedImage(800, 400, BufferedImage.TYPE_INT_ARGB);

    {
      Graphics2D g = image.createGraphics();
      g.setRenderingHint(
          RenderingHints.KEY_TEXT_ANTIALIASING,
          RenderingHints.VALUE_TEXT_ANTIALIAS_ON
      );
      g.setRenderingHint(
          RenderingHints.KEY_ANTIALIASING,
          RenderingHints.VALUE_ANTIALIAS_ON
      );
      g.fillRect(0, 0, image.getWidth(), image.getHeight());

      gen.drawStr(g, "ПРИВЕТ PNG T", 10, 30, 80);

      g.dispose();
    }

    ImageIO.write(image, "png", helloFile);

  }
}
