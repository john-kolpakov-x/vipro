package kz.pompei.vipro.probe3d;

import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class VulkanCoreTest {
  @Test
  public void creation() throws Exception {

    try (VulkanCore core = new VulkanCore()) {
      System.out.println("core.__readReference() = " + core.__readReference());
      System.out.println("core.width() = " + core.width());
      System.out.println("core.height() = " + core.height());
      assertThat(core.__readReference()).isNotEqualTo(0);
    }


  }

}