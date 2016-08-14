package kz.pompei.vipro.display.impl;

import kz.pompei.vipro.display.DisplayExpr;
import kz.pompei.vipro.display.impl.leaning.DisplayLeanBuilder;

import java.util.Collection;

public class DisplayOrder {
  public static DisplayExpr displayOrder(DisplayExpr... order) {
    return displayOrder(order, 0, order.length);
  }


  public static DisplayExpr displayOrder(DisplayExpr[] order, int offset, int len) {
    if (len == 0 || order == null) {
      //noinspection ImplicitArrayToString
      throw new IllegalArgumentException("len = " + len + ", order = " + order);
    }

    if (len == 1) return order[offset];
    if (len == 2) return DisplayLeanBuilder.around(order[offset]).onRight(order[offset + 1], 0, 0).build();
    return DisplayLeanBuilder.around(order[offset]).onRight(displayOrder(order, offset + 1, len - 1), 0, 0).build();
  }

  public static DisplayExpr displayOrder(Collection<DisplayExpr> collection) {
    int size = collection.size();
    if (size == 1) return collection.iterator().next();
    DisplayExpr[] order = new DisplayExpr[size];
    int index = 0;
    for (DisplayExpr displayExpr : collection) {
      order[index++] = displayExpr;
    }
    return displayOrder(order, 0, size);
  }
}
