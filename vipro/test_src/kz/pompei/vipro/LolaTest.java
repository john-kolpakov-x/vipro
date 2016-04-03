package kz.pompei.vipro;


import org.fest.assertions.Assertions;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class LolaTest {
  @Test
  public void asd() throws Exception {
    System.out.println("asd");
    assertThat("a").isEqualTo("a");
  }
}
