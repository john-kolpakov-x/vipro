package kz.pompei.vipro.model;

import java.util.ArrayList;
import java.util.List;

public class Method extends Block {
  public int x, y;
  public String resultType;
  public final List<MethodArg> argList = new ArrayList<>();

  public BlockPoint down;
}
