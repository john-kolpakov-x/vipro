package kz.pompei.vipro.tmp;

import org.testng.annotations.Test;

import java.util.Comparator;

public class AbstractClassATest {
  @Test
  public void name() throws Exception {
    Comparator<Integer> a = (o1, o2) -> {
      int x = o1, y = o2;
      return x == y ? 0 : (x < y ? -1 : +1);
    };

    System.out.println(a);
  }
}
