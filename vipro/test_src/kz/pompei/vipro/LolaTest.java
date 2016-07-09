package kz.pompei.vipro;


import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class LolaTest {
  @Test
  public void asd() throws Exception {
    Map<String, Integer> hashMap = new HashMap<>(10_000_000);
    Map<String, Integer> treeMap = new TreeMap<>();

    int count = 10_000_000;
    long times[] = new long[2];

    {
      Thread threads[] = new Thread[]{
        new Thread(() -> {
          long startedAt = System.nanoTime();
          fill(hashMap, count);
          times[0] = System.nanoTime() - startedAt;
        }),

        new Thread(() -> {
          long startedAt = System.nanoTime();
          fill(treeMap, count);
          times[1] = System.nanoTime() - startedAt;
        }),
      };

      for (Thread thread : threads) {
        thread.start();
      }
      for (Thread thread : threads) {
        thread.join();
      }
    }

    System.out.println("fill time hashMap is " + times[0] + " ns");
    System.out.println("fill time treeMap is " + times[1] + " ns");

    {
      Thread threads[] = new Thread[]{
        new Thread(() -> {
          long startedAt = System.nanoTime();
          inc(hashMap, count);
          times[0] = System.nanoTime() - startedAt;
        }),

        new Thread(() -> {
          long startedAt = System.nanoTime();
          inc(treeMap, count);
          times[1] = System.nanoTime() - startedAt;
        }),
      };

      for (Thread thread : threads) {
        thread.start();
      }
      for (Thread thread : threads) {
        thread.join();
      }
    }

    System.out.println("inc time hashMap is " + times[0] + " ns");
    System.out.println("inc time treeMap is " + times[1] + " ns");

  }

  private void inc(Map<String, Integer> map, int count) {
    for (int i = 0; i < count; i++) {
      String id = "A" + i;
      Integer value = map.get(id);
      map.put(id, value + 1);
    }
  }

  private void fill(Map<String, Integer> map, int count) {
    for (int i = 0; i < count; i++) {
      String id = "A" + i;
      map.put(id, i);
    }
  }
}
