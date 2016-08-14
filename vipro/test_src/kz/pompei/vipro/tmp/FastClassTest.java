package kz.pompei.vipro.tmp;

import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.text.DecimalFormat;

public class FastClassTest {

  static class A {
    long value = 0;
    long nanoSum = 0;

    public void add(long i) {
      value += i;
      nanoSum += System.nanoTime();
    }

    @Override
    public String toString() {
      return "A{value = " + value + "}";
    }
  }

  private static final double GIG = 1_000_000_000;
  private static final long LOOPS_COUNT = 10_000_000;

  public static String timeStr(long nano1, long nano2) {
    DecimalFormat df = new DecimalFormat("### ###.######");
    return df.format((nano2 - nano1) / GIG) + " s";
  }

  @Test
  public void fastMethod() throws Exception {

    A a1 = new A();

    long time1 = System.nanoTime();
    for (long i = 1; i <= LOOPS_COUNT; i++) {
      a1.add(i);
    }
    long time2 = System.nanoTime();

    System.out.println("a1 /direct/ = " + a1);
    System.out.println(timeStr(time1, time2));

    A a2 = new A();

    Method addMethod = A.class.getMethod("add", long.class);

    long time3 = System.nanoTime();
    for (long i = 1; i <= LOOPS_COUNT; i++) {
      addMethod.invoke(a2, i);
    }
    long time4 = System.nanoTime();

    System.out.println("a2 /reflection/ = " + a2);
    System.out.println(timeStr(time3, time4));

    FastClass fastA = FastClass.create(A.class);
    FastMethod fastAdd = fastA.getMethod(addMethod);

    A a3 = new A();

    long time5 = System.nanoTime();
    for (long i = 1; i <= LOOPS_COUNT; i++) {
      fastAdd.invoke(a3, new Object[]{i});
    }
    long time6 = System.nanoTime();

    System.out.println("a3 /fast cglib/ = " + a3);
    System.out.println(timeStr(time5, time6));
  }
}
