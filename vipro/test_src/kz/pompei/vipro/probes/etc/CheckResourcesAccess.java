package kz.pompei.vipro.probes.etc;

import java.io.InputStream;

public class CheckResourcesAccess {
  public static void main(String[] args) {
    new CheckResourcesAccess().run();
  }

  private void run() {
    InputStream inputStream = getClass().getResourceAsStream("CheckResourcesAccess.txt");
    System.out.println(inputStream);
  }
}
