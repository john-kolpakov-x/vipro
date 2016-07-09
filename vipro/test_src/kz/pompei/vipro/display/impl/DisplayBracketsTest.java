package kz.pompei.vipro.display.impl;

import kz.pompei.vipro.display.BracketsType;
import kz.pompei.vipro.display.DisplayExpr;
import kz.pompei.vipro.display.DisplayPortImpl;
import kz.pompei.vipro.display.Size;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.awt.Color;

import static kz.pompei.vipro.display.impl.TestDisplayUtil.displayToFile;

public class DisplayBracketsTest {

  @DataProvider
  public Object[][] dataProvider() {
    return new Object[][]{
      new Object[]{BracketsType.SQUARE},
      new Object[]{BracketsType.ROUND},
    };
  }

  @Test(dataProvider = "dataProvider")
  public void display(BracketsType bracketsType) throws Exception {

    DisplayExpr in = new DisplayRect(new Size(70, 50, 100),
      new Color(255, 0, 0),
      new Color(181, 200, 46)
    );

    DisplayBrackets displayBrackets = new DisplayBrackets(in, bracketsType, new Color(0, 0, 0));

    DisplayPortImpl port = new DisplayPortImpl();
    displayBrackets.setPort(port);

    displayToFile(displayBrackets, port, getClass().getSimpleName() + '_' + bracketsType);
  }
}