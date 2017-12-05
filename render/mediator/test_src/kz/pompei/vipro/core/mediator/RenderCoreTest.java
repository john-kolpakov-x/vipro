package kz.pompei.vipro.core.mediator;

import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class RenderCoreTest {
  @Test
  public void create() throws Exception {

    try (RenderCore renderCore = new RenderCore()) {
      assertThat(renderCore.__readReference__()).isNotEqualTo(0);

      Size size = renderCore.getSize();
      assertThat(size).isNotNull();
      assertThat(size.width).isEqualTo(800);
      assertThat(size.height).isEqualTo(600);
    }

  }
}