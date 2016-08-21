package kz.pompei.vipro.tmp;

public abstract class AbstractClassA {

  public abstract int abstractMethod(int x, int y);

  public String callAbstract(String X, String Y) {
    return "" + abstractMethod(Integer.parseInt(X), Integer.parseInt(Y));
  }
}
