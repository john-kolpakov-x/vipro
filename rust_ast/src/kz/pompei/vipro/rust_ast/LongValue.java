package kz.pompei.vipro.rust_ast;

public class LongValue implements Value {
  public final long value;

  public LongValue(long value) {this.value = value;}

  @Override
  public String strValue() {
    return "" + value;
  }
}
