package kz.pompei.vipro.tmp;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class CounterTest {

  interface Counter {
    void inc();

    long get();

    void set(long value);
  }

  static class CounterSimple implements Counter {
    @Override
    public void inc() {
      value++;
    }

    private long value;

    @Override
    public long get() {
      return value;
    }

    @Override
    public void set(long value) {
      this.value = value;
    }
  }

  static class CounterSyncSimple implements Counter {
    @Override
    public void inc() {
      synchronized (this) {
        value++;
      }
    }

    private volatile long value;

    @Override
    public long get() {
      return value;
    }

    @Override
    public void set(long value) {
      this.value = value;
    }
  }

  static class CounterAtomic implements Counter {
    @Override
    public void inc() {
      value.incrementAndGet();
    }

    private AtomicLong value = new AtomicLong();

    @Override
    public long get() {
      return value.get();
    }

    @Override
    public void set(long value) {
      this.value.set(value);
    }
  }

  static class TestResult {
    String name;
    long counter;
    int threadsCount;
    double timeSec;

    @Override
    public String toString() {
      return name + " - counter: " + counter + ", threads: " + threadsCount + ", timeSec: " + timeSec;
    }
  }

  private static final double GIG = 1_000_000_000;

  public static void main(String[] args) throws Exception {
    final int threadCount = 5;
    final int count = 30_000_000;

    ArrayList<TestResult> resList = new ArrayList<>(16);

    resList.add(testIt(threadCount, count, new CounterAtomic()));
    System.out.println(resList.get(resList.size() - 1));
    resList.add(testIt(threadCount, count, new CounterAtomic()));
    System.out.println(resList.get(resList.size() - 1));
    resList.add(testIt(threadCount, count, new CounterAtomic()));
    System.out.println(resList.get(resList.size() - 1));

    resList.add(testIt(threadCount, count, new CounterSyncSimple()));
    System.out.println(resList.get(resList.size() - 1));
    resList.add(testIt(threadCount, count, new CounterSyncSimple()));
    System.out.println(resList.get(resList.size() - 1));
    resList.add(testIt(threadCount, count, new CounterSyncSimple()));
    System.out.println(resList.get(resList.size() - 1));

    resList.add(testIt(threadCount, count, new CounterSimple()));
    System.out.println(resList.get(resList.size() - 1));
    resList.add(testIt(threadCount, count, new CounterSimple()));
    System.out.println(resList.get(resList.size() - 1));
    resList.add(testIt(threadCount, count, new CounterSimple()));
    System.out.println(resList.get(resList.size() - 1));


    System.out.println("* * * * * * *");
    resList.forEach(System.out::println);
  }

  private static TestResult testIt(int threadCount, int count, Counter counter) throws InterruptedException {
    Thread tt[] = new Thread[threadCount];
    for (int ti = 0; ti < threadCount; ti++) {
      tt[ti] = new Thread(() -> {
        for (//noinspection UnnecessaryLocalVariable
          int i = 0, c = count; i < c; i++) {
          counter.inc();
        }
      });
    }

    long time1 = System.nanoTime();
    for (Thread t : tt) {
      t.start();
    }
    for (Thread t : tt) {
      t.join();
    }
    long time2 = System.nanoTime();

    double timeSec = (time2 - time1) / GIG;

    {
      TestResult ret = new TestResult();
      ret.counter = counter.get();
      ret.name = counter.getClass().getSimpleName();
      ret.threadsCount = threadCount;
      ret.timeSec = timeSec;
      return ret;
    }
  }

}
