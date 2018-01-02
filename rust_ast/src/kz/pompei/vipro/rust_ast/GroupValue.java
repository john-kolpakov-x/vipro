package kz.pompei.vipro.rust_ast;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GroupValue implements Value {
  private final Map<String, Value> values;

  private GroupValue(Map<String, Value> values) {this.values = values;}

  public static final GroupValue EMPTY_GROUP = new GroupValue(new HashMap<>());

  public GroupValue set(String name, Value value) {
    Map<String, Value> values = new HashMap<>(this.values);
    values.put(name, value);
    return new GroupValue(values);
  }

  public GroupValue setStr(String name, String value) {
    if (value == null) return delete(name);
    return set(name, new StrValue(value));
  }

  public GroupValue setLong(String name, Long value) {
    if (value == null) return delete(name);
    return set(name, new LongValue(value));
  }

  public GroupValue delete(String name) {
    if (!values.containsKey(name)) return this;
    Map<String, Value> values = new HashMap<>(this.values);
    values.remove(name);
    if (values.size() == 0) return EMPTY_GROUP;
    return new GroupValue(values);
  }

  public Stream<Map.Entry<String, Value>> stream() {
    return values.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey));
  }

  public List<String> names() {
    return values.keySet().stream().sorted().collect(Collectors.toList());
  }

  public Value get(String name) {
    return values.get(name);
  }

  public String getStr(String name) {
    Value value = values.get(name);
    return value == null ? null : value.strValue();
  }

  @Override
  public String strValue() {
    throw new UnsupportedOperationException();
  }

  public boolean hasName(String name) {
    return values.containsKey(name);
  }
}
