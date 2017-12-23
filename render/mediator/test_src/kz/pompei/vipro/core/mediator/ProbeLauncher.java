package kz.pompei.vipro.core.mediator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class ProbeLauncher {
  public static void main(String[] args) throws Exception {
    new ProbeLauncher().run();
  }

  private void run() throws Exception {
    RenderCore renderCore = new RenderCore();
    loadTextureImage(renderCore);
    renderCore.initialize();
    renderCore.mainLoop();

    System.out.println("Exit from program OK");
  }

  private void loadTextureImage(RenderCore renderCore) throws Exception {
    //renderCore.putTextureImageData();
    InputStream textureInputStream = getClass().getResourceAsStream("texture.jpg");
    BufferedImage image = ImageIO.read(textureInputStream);

    int w = image.getWidth(), h = image.getHeight();

//    BufferedImage good = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
//    {
//      Graphics2D g = good.createGraphics();
////      g.drawImage(image, 0, 0, null);
//      g.setColor(new Color(0xF1, 0xE2, 0xA3));
//      g.fillRect(0, 0, w, h);
//      g.dispose();
//    }
//    ImageIO.write(good, "png", new File("build/tex.png"));

    int imageData[] = new int[w * h];
//    good.getRGB(0, 0, w, h, imageData, 0, w);
    image.getRGB(0, 0, w, h, imageData, 0, w);

    byte imageBytes[] = new byte[w * h * 4];
    copyToBytes(imageData, imageBytes);

//    try (FileOutputStream outputStream = new FileOutputStream(new File("build/tex.bytes"))) {
//      outputStream.write(imageBytes);
//    }

    renderCore.putTextureImageData(w, h, imageBytes);

  }

  @SuppressWarnings({"PointlessArithmeticExpression", "PointlessBitwiseExpression"})
  private static void copyToBytes(int[] from, byte[] to) {
    for (int i = 0, c = from.length; i < c; i++) {
      int color = from[i];
      to[4 * i + 3] = (byte) ((color & 0xFF000000) >> (3 * 8));
      to[4 * i + 0] = (byte) ((color & 0x00FF0000) >> (2 * 8));
      to[4 * i + 1] = (byte) ((color & 0x0000FF00) >> (1 * 8));
      to[4 * i + 2] = (byte) ((color & 0x000000FF) >> (0 * 8));
    }
  }
}
