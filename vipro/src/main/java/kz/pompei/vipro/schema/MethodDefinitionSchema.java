package kz.pompei.vipro.schema;

import kz.pompei.vipro.model.Place;

public class MethodDefinitionSchema {
  
  public int x, y, width, height;
  
  public int nameX, nameY;
  public float nameFontSize;
  
  public Place getPlace() {
    return new Place(x, y, width, height);
  }
  
}
