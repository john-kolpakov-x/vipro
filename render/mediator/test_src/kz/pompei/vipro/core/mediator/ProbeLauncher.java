package kz.pompei.vipro.core.mediator;

public class ProbeLauncher {
  public static void main(String[] args) {
    new ProbeLauncher().run();
  }

  private void run() {
    RenderCore renderCore = new RenderCore();
    renderCore.initialize();
    renderCore.mainLoop();

    System.out.println("OK");
  }
}
