package kz.pompei.vipro.display.impl;

import kz.pompei.vipro.display.DisplayExpr;
import kz.pompei.vipro.display.DisplayPortImpl;
import kz.pompei.vipro.display.Size;
import org.testng.annotations.Test;

import java.awt.Color;

import static kz.pompei.vipro.display.impl.TestDisplayUtil.displayToFile;


public class DisplayRectTest {
  @Test
  public void display() throws Exception {
    DisplayExpr expr = new DisplayRect(new Size(50, 50, 100),
      new Color(255, 0, 0),
      new Color(181, 200, 46)
    );

    DisplayPortImpl port = new DisplayPortImpl();
    expr.setPort(port);

    displayToFile(expr, port, getClass().getSimpleName());
  }
}