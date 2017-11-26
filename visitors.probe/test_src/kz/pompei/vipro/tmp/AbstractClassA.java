package kz.pompei.vipro.tmp;

public abstract class AbstractClassA {

  public abstract int abstractMethod(int x, int y);

  /**
   * Это супер описание этого метода
   *
   * @param X это переменная икс
   * @param Y это аргумент игрек
   * @return чё-то надо вернуть
   */
  public String callAbstract(String X, String Y) {
    return "" + abstractMethod(Integer.parseInt(X), Integer.parseInt(Y));
  }
}
