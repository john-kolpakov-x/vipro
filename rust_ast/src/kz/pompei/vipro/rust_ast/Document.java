package kz.pompei.vipro.rust_ast;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static kz.pompei.vipro.rust_ast.GroupValue.EMPTY_GROUP;

public class Document {

  private static final String ID = "__ID__";

  private static final String ENG = "abcdefghijklmnopqrstuvwxyz";
  private static final String DEG = "0123456789";
  private static final char ID_CHARS[] = (ENG + ENG.toLowerCase() + DEG).toCharArray();

  private final Random random = new Random();

  private String newId() {
    char content[] = new char[10];
    int N = ID_CHARS.length;
    for (int i = 0, n = content.length; i < n; i++) {
      content[i] = ID_CHARS[random.nextInt(N)];
    }
    return new String(content);
  }

  private final String rootId;

  public String rootId() {
    return rootId;
  }

  private final Map<String, GroupValue> groupMap = new HashMap<>();

  public Document() {
    rootId = newId();
    groupMap.put(rootId, EMPTY_GROUP.setStr(ID, rootId));
  }

  public void printTo(int offset, StringBuilder sb) {
    printValue(offset, sb, groupMap.get(rootId));
  }

  private static void printValue(int offset, StringBuilder sb, Value value) {
    if (value instanceof GroupValue) {
      printGroupValue(offset, sb, (GroupValue) value);
      return;
    }
    if (value instanceof StrValue) {
      printStrValue(offset, sb, (StrValue) value);
      return;
    }
    if (value instanceof LongValue) {
      printLongValue(offset, sb, (LongValue) value);
      return;
    }
    throw new RuntimeException("Unknown " + value);

  }

  @SuppressWarnings("unused")
  private static void printLongValue(int offset, StringBuilder sb, LongValue value) {
    sb.append("Long ").append(value.value);
  }

  private static void printOffset(int offset, StringBuilder sb) {
    for (int i = 0, n = 2 * offset; i < n; i++) sb.append(' ');
  }

  @SuppressWarnings("unused")
  private static void printStrValue(int offset, StringBuilder sb, StrValue value) {
    sb.append("Str ").append(value.value);
  }

  private static void printGroupValue(int offset, StringBuilder sb, GroupValue value) {
    sb.append("Group ").append(value.getStr(ID)).append("\n");
    for (String name : value.names()) {
      if (ID.equals(name)) continue;
      printOffset(offset, sb);
      sb.append(name).append(" = ");
      printValue(offset + 1, sb, value.get(name));
      sb.append("\n");
    }
    printOffset(offset, sb);
    sb.append("END Group");
  }

  public GroupValue insertGroup(String toId, String name) {
    GroupValue groupValue = groupMap.get(toId);
    if (groupValue == null) throw new IllegalArgumentException("No value with id = '" + toId + "'");
    if (groupValue.hasName(name)) throw new IllegalArgumentException("Group with id = '" + toId
        + "' already contains value with name = '" + name + "'");
    String id = newId();
//    GroupValue.EMPTY_GROUP.set(ID, )
    return null;
  }
}
