package kz.pompei.vipro.core.mediator;

import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class RenderCoreTest {
  @Test
  public void create() throws Exception {

    try (RenderCore renderCore = new RenderCore()) {
      assertThat(renderCore.__readReference__()).isNotEqualTo(0);
    }

  }
}