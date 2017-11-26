package kz.pompei.vipro.display;

public interface DisplayExpr {

  void setPort(DisplayPort port);

  void reset();

  void displayTo(int x, int y);

  Size size();
}
