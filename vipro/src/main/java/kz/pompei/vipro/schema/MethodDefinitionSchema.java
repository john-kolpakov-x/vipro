package kz.pompei.vipro.schema;

import kz.pompei.vipro.util.Place;

public class MethodDefinitionSchema {
  
  public int x, y, width, height;
  
  public int nameX, nameY;
  public float nameFontSize;
  
  public NearSchema near;
  
  public Place getPlace() {
    return new Place(x, y, width, height);
  }
  
}
