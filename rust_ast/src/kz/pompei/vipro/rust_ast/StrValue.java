package kz.pompei.vipro.rust_ast;

import java.util.Objects;

public class StrValue implements Value {
  public final String value;

  public StrValue(String value) {this.value = value;}

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    StrValue strValue = (StrValue) o;
    return Objects.equals(value, strValue.value);
  }

  @Override
  public int hashCode() {

    return Objects.hash(value);
  }

  @Override
  public String strValue() {
    return value;
  }
}
