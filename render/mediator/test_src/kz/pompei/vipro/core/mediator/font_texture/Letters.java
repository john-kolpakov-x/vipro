package kz.pompei.vipro.core.mediator.font_texture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Letters {

  private final List<LetterRect> letterRectList = new ArrayList<>();
  private final Map<Character, Integer> letterIndexes = new HashMap<>();

  public void put(char c, LetterRect letterRect) {
    if (letterIndexes.containsKey(c)) throw new IllegalArgumentException("Letter " + c + " already put");
    letterIndexes.put(c, letterRectList.size());
    letterRectList.add(letterRect);
  }

  private LetterRect letterRectDefault = null;

  public void putDefault(LetterRect letterRect) {
    if (letterRectDefault != null) throw new IllegalArgumentException("Default character already defined");
    letterRectDefault = letterRect;
  }

  public LetterRect getRect(char c) {
    Integer index = letterIndexes.get(c);
    if (index == null) return letterRectDefault;
    return letterRectList.get(index);
  }
}
