package kz.pompei.vipro.display.impl;

import kz.pompei.vipro.display.BracketsType;
import kz.pompei.vipro.display.DisplayExpr;
import kz.pompei.vipro.display.DisplayPortImpl;
import org.testng.annotations.Test;

import java.awt.Color;

import static kz.pompei.vipro.display.impl.TestDisplayUtil.displayToFile;

public class ComplexTest {
  @Test
  public void name() throws Exception {
    DisplayExpr expr = getDisplayExpr();

    System.out.println(expr);

    DisplayPortImpl port = new DisplayPortImpl();
    expr.setPort(port);

    displayToFile(expr, port, getClass().getSimpleName());

  }

  private DisplayExpr getDisplayExpr() {
    Color color = new Color(0, 0, 0);

    DisplayText a = new DisplayText(0, "α", color, false, false);
    DisplayText eq = new DisplayText(0, "=", color, false, false);

    DisplayText sin = new DisplayText(0, "sin", color, false, false);
    DisplayText cos = new DisplayText(0, "cos", color, false, false);
    DisplayText tg = new DisplayText(0, "tg", color, false, false);

    DisplayBrackets b_a = new DisplayBrackets(a, BracketsType.ROUND, color);

    DisplayExpr top = DisplayLeaningVertical.displayOrder(sin, b_a);
    DisplayExpr bot = DisplayLeaningVertical.displayOrder(cos, b_a);

    DisplayDiv div = new DisplayDiv(0, top, bot, 0.04f, color);

    return DisplayLeaningVertical.displayOrder(tg, b_a, eq, div);
  }
}
