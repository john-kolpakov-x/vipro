package kz.pompei.vipro.display.impl.leaning;

import kz.pompei.vipro.display.DisplayExpr;
import kz.pompei.vipro.display.DisplayPortImpl;
import kz.pompei.vipro.display.Size;
import kz.pompei.vipro.display.impl.DisplayRect;
import org.testng.annotations.Test;

import java.awt.Color;

import static kz.pompei.vipro.display.impl.TestDisplayUtil.displayToFile;

public class DisplayLeanBuilderTest {

  private final DisplayExpr base = new DisplayRect(
    new Size(50, 50, 100),
    new Color(255, 0, 0),
    new Color(181, 200, 46)
  );

  private final DisplayExpr small = new DisplayRect(
    new Size(25, 25, 50),
    new Color(200, 5, 3),
    new Color(36, 255, 17)
  );

  private final DisplayExpr small2 = new DisplayRect(
    new Size(25, 25, 70),
    new Color(200, 5, 3),
    new Color(36, 255, 17)
  );

  private final DisplayExpr wide = new DisplayRect(
    new Size(25, 25, 150),
    new Color(200, 5, 3),
    new Color(36, 255, 17)
  );
  private final DisplayExpr wide2 = new DisplayRect(
    new Size(20, 20, 170),
    new Color(200, 5, 3),
    new Color(31, 120, 255)
  );

  @Test
  public void leftRight1() throws Exception {
    DisplayExpr display = DisplayLeanBuilder.around(base)
      .onLeft(small, 0.9f, 0.04f)
      .onLeft(small, -0.9f, 0.04f)
      .onRight(small, 0.9f, 0.04f)
      .onRight(small, -0.9f, 0.04f)
      .build();

    DisplayPortImpl port = new DisplayPortImpl();
    port.levelOffset = 0;
    display.setPort(port);

    displayToFile(display, port, getClass().getSimpleName() + "_" + "leftRight1");
  }

  @Test
  public void leftRight2() throws Exception {
    DisplayExpr display = DisplayLeanBuilder.around(base)
      .onLeft(small, 0.9f, 0.04f)
      .onLeft(small, -0.9f, 0.08f)
      .onRight(small, 0.9f, 0.1f)
      .onRight(small, -0.9f, 0.04f)
      .build();

    DisplayPortImpl port = new DisplayPortImpl();
    port.levelOffset = 0;
    display.setPort(port);

    displayToFile(display, port, getClass().getSimpleName() + "_" + "leftRight2");
  }

  @Test
  public void leftRight3() throws Exception {
    DisplayExpr display = DisplayLeanBuilder.around(base)
      .onLeft(small, 0.9f, 0.041f)
      .onLeft(small2, -0.9f, 0.081f)
      .onRight(small, 0.9f, 0.1f)
      .onRight(small2, -0.9f, 0.04f)
      .build();

    DisplayPortImpl port = new DisplayPortImpl();
    port.levelOffset = 0;
    display.setPort(port);

    displayToFile(display, port, getClass().getSimpleName() + "_" + "leftRight3");
  }

  @Test
  public void leftRight4() throws Exception {
    DisplayExpr display = DisplayLeanBuilder.around(base)
      .onLeft(small, 0.9f, 0.042f)
      .onLeft(small, -0.9f, 0.082f)
      .onRight(small2, 0.9f, 0.1f)
      .onRight(small2, -0.9f, 0.04f)
      .build();

    DisplayPortImpl port = new DisplayPortImpl();
    port.levelOffset = 0;
    display.setPort(port);

    displayToFile(display, port, getClass().getSimpleName() + "_" + "leftRight4");
  }

  @Test
  public void onRight_minus_09() throws Exception {
    DisplayExpr display = DisplayLeanBuilder.around(base)
      .onRight(small, -0.9f, 0.04f)
      .build();

    DisplayPortImpl port = new DisplayPortImpl();
    port.levelOffset = 0;
    display.setPort(port);

    displayToFile(display, port, getClass().getSimpleName() + "_" + "onRight_minus_09");
  }

  @Test
  public void onRight_plus_09() throws Exception {

    DisplayExpr display = DisplayLeanBuilder.around(base)
      .onRight(small, +0.9f, 0.04f)
      .build();

    DisplayPortImpl port = new DisplayPortImpl();
    port.levelOffset = 0;
    display.setPort(port);

    displayToFile(display, port, getClass().getSimpleName() + "_" + "onRight_plus_09");
  }

  @Test
  public void onRight_small_zero() throws Exception {
    DisplayExpr display = DisplayLeanBuilder.around(base)
      .onRight(small, 0, 0.04f)
      .build();

    DisplayPortImpl port = new DisplayPortImpl();
    port.levelOffset = 0;
    display.setPort(port);

    displayToFile(display, port, getClass().getSimpleName() + "_" + "small_zero");
  }

  @Test
  public void onTopLeft_small() throws Exception {
    DisplayExpr display = DisplayLeanBuilder.around(base)
      .onTopLeft(small, 0.04f)
      .build();

    DisplayPortImpl port = new DisplayPortImpl();
    port.levelOffset = 0;
    display.setPort(port);

    displayToFile(display, port, getClass().getSimpleName() + "_" + "onTopLeft_small");
  }

  @Test
  public void onTopLeft_wide1() throws Exception {
    DisplayExpr display = DisplayLeanBuilder.around(base)
      .onTopLeft(wide, 0.041f)
      .onTopLeft(wide2, 0.041f)
      .build();

    DisplayPortImpl port = new DisplayPortImpl();
    port.levelOffset = 0;
    display.setPort(port);

    displayToFile(display, port, getClass().getSimpleName() + "_" + "onTopLeft_wide1");
  }

  @Test
  public void onTopLeft_wide2() throws Exception {
    DisplayExpr display = DisplayLeanBuilder.around(base)
      .onTopLeft(wide2, 0.04f)
      .onTopLeft(wide, 0.04f)
      .build();

    DisplayPortImpl port = new DisplayPortImpl();
    port.levelOffset = 0;
    display.setPort(port);

    displayToFile(display, port, getClass().getSimpleName() + "_" + "onTopLeft_wide2");
  }

  @Test
  public void onTopRight_wide() throws Exception {
    DisplayExpr display = DisplayLeanBuilder.around(base)
      .onTopRight(wide, 0.041f)
      .onTopRight(wide2, 0.041f)
      .build();

    DisplayPortImpl port = new DisplayPortImpl();
    port.levelOffset = 0;
    display.setPort(port);

    displayToFile(display, port, getClass().getSimpleName() + "_" + "onTopRight_wide");
  }

  @Test
  public void onTopCenter_wide() throws Exception {
    DisplayExpr display = DisplayLeanBuilder.around(base)
      .onTopCenter(wide2, 0.041f)
      .onTopCenter(wide, 0.041f)
      .build();

    DisplayPortImpl port = new DisplayPortImpl();
    port.levelOffset = 0;
    display.setPort(port);

    displayToFile(display, port, getClass().getSimpleName() + "_" + "onTopCenter_wide");
  }

  @Test
  public void onBottomLeft_small() throws Exception {
    DisplayExpr display = DisplayLeanBuilder.around(base)
      .onBottomLeft(small, 0.04f)
      .build();

    DisplayPortImpl port = new DisplayPortImpl();
    port.levelOffset = 0;
    display.setPort(port);

    displayToFile(display, port, getClass().getSimpleName() + "_" + "onBottomLeft_small");
  }

  @Test
  public void onBottomLeft_wide1() throws Exception {
    DisplayExpr display = DisplayLeanBuilder.around(base)
      .onBottomLeft(wide, 0.041f)
      .onBottomLeft(wide2, 0.041f)
      .build();

    DisplayPortImpl port = new DisplayPortImpl();
    port.levelOffset = 0;
    display.setPort(port);

    displayToFile(display, port, getClass().getSimpleName() + "_" + "onBottomLeft_wide1");
  }

  @Test
  public void onBottomLeft_wide2() throws Exception {
    DisplayExpr display = DisplayLeanBuilder.around(base)
      .onBottomLeft(wide2, 0.04f)
      .onBottomLeft(wide, 0.04f)
      .build();

    DisplayPortImpl port = new DisplayPortImpl();
    port.levelOffset = 0;
    display.setPort(port);

    displayToFile(display, port, getClass().getSimpleName() + "_" + "onBottomLeft_wide2");
  }

  @Test
  public void onBottomRight_wide() throws Exception {
    DisplayExpr display = DisplayLeanBuilder.around(base)
      .onBottomRight(wide, 0.041f)
      .onBottomRight(wide2, 0.041f)
      .build();

    DisplayPortImpl port = new DisplayPortImpl();
    port.levelOffset = 0;
    display.setPort(port);

    displayToFile(display, port, getClass().getSimpleName() + "_" + "onBottomRight_wide");
  }

  @Test
  public void onBottomCenter_wide() throws Exception {
    DisplayExpr display = DisplayLeanBuilder.around(base)
      .onBottomCenter(wide2, 0.041f)
      .onBottomCenter(wide, 0.041f)
      .build();

    DisplayPortImpl port = new DisplayPortImpl();
    port.levelOffset = 0;
    display.setPort(port);

    displayToFile(display, port, getClass().getSimpleName() + "_" + "onBottomCenter_wide");
  }
}
