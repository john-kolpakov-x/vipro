package kz.pompei.vipro.tmp;

import org.testng.annotations.Test;

import java.util.Comparator;

public class AbstractClassATest {
  @Test
  public void name() throws Exception {
    Comparator<Integer> a = Integer::compare;

    System.out.println(a);
  }
}
