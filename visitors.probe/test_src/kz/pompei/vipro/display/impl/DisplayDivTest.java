package kz.pompei.vipro.display.impl;

import kz.pompei.vipro.display.DisplayExpr;
import kz.pompei.vipro.display.DisplayPortImpl;
import kz.pompei.vipro.display.Size;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.awt.Color;

import static kz.pompei.vipro.display.impl.TestDisplayUtil.displayToFile;

public class DisplayDivTest {

  @DataProvider
  public Object[][] dataProvider() {
    return new Object[][]{
      new Object[]{0},
      new Object[]{0.05f},
    };
  }

  @Test(dataProvider = "dataProvider")
  public void display(float spaceFactor) throws Exception {

    DisplayExpr top = new DisplayRect(new Size(50, 50, 100),
      new Color(255, 0, 0),
      new Color(181, 200, 46)
    );

    DisplayExpr bottom = new DisplayRect(new Size(30, 30, 70),
      new Color(192, 0, 0),
      new Color(142, 161, 41)
    );

    DisplayDiv expr = new DisplayDiv(0, top, bottom, spaceFactor, new Color(0, 0, 0));

    DisplayPortImpl port = new DisplayPortImpl();
    port.levelOffset = 0;
    expr.setPort(port);

    displayToFile(expr, port, getClass().getSimpleName() + '_' + spaceFactor);
  }
}