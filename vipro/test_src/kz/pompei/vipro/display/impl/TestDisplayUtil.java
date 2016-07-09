package kz.pompei.vipro.display.impl;

import kz.pompei.vipro.display.DisplayExpr;
import kz.pompei.vipro.display.DisplayPortImpl;
import kz.pompei.vipro.display.Size;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TestDisplayUtil {
  public static void displayToFile(DisplayExpr displayExpr, DisplayPortImpl port, String fileName) throws IOException {

    {
      BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
      port.setGraphics(image.createGraphics());
      displayExpr.size();
      port.graphics().close();
    }

    {
      Size size = displayExpr.size();
      BufferedImage image = new BufferedImage(size.width + 20, size.height() + 20, BufferedImage.TYPE_INT_ARGB);

      Graphics2D g = image.createGraphics();
      g.setColor(Color.white);
      g.fillRect(0, 0, image.getWidth(), image.getHeight());
      port.setGraphics(g);

      displayExpr.displayTo(10, 10 + size.top);

      port.graphics().close();

      new File("build").mkdirs();

      ImageIO.write(image, "png", new File("build/" + fileName + ".png"));
    }
  }
}
