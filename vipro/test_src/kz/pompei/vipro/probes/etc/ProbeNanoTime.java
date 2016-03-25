package kz.pompei.vipro.probes.etc;

import static kz.pompei.vipro.probes.etc.TimeUtil.readMilli;
import static kz.pompei.vipro.probes.etc.TimeUtil.readNano;

public class ProbeNanoTime {
  public static void main(String[] args) throws Exception {
    long nano1 = System.nanoTime();
    long milli1 = System.currentTimeMillis();

    for (int i = 0; i < 100; i++) {
      System.out.println("Hello world " + i);
      System.out.println("Привет мир " + i);
      Thread.sleep(100);
    }

    long nano2 = System.nanoTime();
    long milli2 = System.currentTimeMillis();

    System.out.println("nano = " + (nano2 - nano1) + ", milli = " + (milli2 - milli1));
    System.out.println("nano = " + readNano(nano2 - nano1) + ", milli = " + readMilli(milli2 - milli1));

  }
}
