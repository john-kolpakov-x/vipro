package kz.pompei.vipro.nuclide;

public class Nuclide {
  public final int N, Z;
  public final String name;
  public final double massAEM;
  public double massP;

  public Nuclide(int N, int Z, String name, double massAEM) {
    this.N = N;
    this.Z = Z;
    this.name = name;
    this.massAEM = massAEM;
  }

  public String nuclideName() {
    return name + "-" + A();
  }

  @Override
  public String toString() {
    return nuclideName() + " (N=" + N + ",Z=" + Z + ")";
  }

  public int A() {
    return N + Z;
  }
}
