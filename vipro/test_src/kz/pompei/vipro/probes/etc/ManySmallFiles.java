package kz.pompei.vipro.probes.etc;

import java.io.File;
import java.io.PrintStream;
import java.util.Random;

import static kz.pompei.vipro.probes.etc.TimeUtil.readNano;
import static kz.pompei.vipro.probes.etc.TimeUtil.toLenZero;

public class ManySmallFiles {
  public static void main(String[] args) throws Exception {

    Random rnd = new Random();
    rnd.setSeed(System.currentTimeMillis());

    final int len = 2, limit = 100;

    long makeDirTime = 0, makeFileTime = 0;

    long startedAt = System.nanoTime();

    //String home = System.getProperty("user.home");
    //String outDir = home + "/tmp/Rumba_Simulation";
    String outDir = "/home/pompei/discs/linux-data/tmp/Rumba_Simulation";

    for (int i = 0; i < 3_000; i++) {
      String a = "Culture" + toLenZero(len, "" + rnd.nextInt(limit));
      String b = "Pomidor" + toLenZero(len, "" + rnd.nextInt(limit));
      String c = "UNIQUE" + toLenZero(len, "" + rnd.nextInt(limit));

      String f = "䒆䓌䓏__" + a + "_" + b + "_" + c + "_" + toLenZero(5, "" + rnd.nextInt(100_000)) + ".txt";

      File dir = new File(outDir + '/' + a + '/' + b + '/' + c);
      long time1 = System.nanoTime();
      dir.mkdirs();
      long time2 = System.nanoTime();
      try (PrintStream out = new PrintStream(new File(dir.getAbsolutePath() + "/" + f), "UTF-8")) {
        out.println("Привет всем " + rnd.nextInt(100_000));
        out.println("QWE [" + rnd.nextInt(100_000) + "] <==> [" + rnd.nextInt(100_000) + "]");
      }
      long time3 = System.nanoTime();

      makeDirTime += time2 - time1;
      makeFileTime += time3 - time2;

      if (i % 100 == 0) {
        long all = System.nanoTime() - startedAt;
        long rest = all - makeDirTime - makeFileTime;
        System.out.println("[i: " + i + "] [makeDirTime: " + readNano(makeDirTime)
          + "] [makeFileTime: " + readNano(makeFileTime) + "] [all time: " + readNano(all)
          + "] [rest time: " + readNano(rest) + "]");
      }
    }

    long all = System.nanoTime() - startedAt;
    long rest = all - makeDirTime - makeFileTime;
    System.out.println("Complete [makeDirTime: " + readNano(makeDirTime)
      + "] [makeFileTime: " + readNano(makeFileTime) + "] [all time: " + readNano(all)
      + "] [rest time: " + readNano(rest) + "]");
  }

}
