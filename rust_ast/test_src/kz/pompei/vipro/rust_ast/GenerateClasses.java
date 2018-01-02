package kz.pompei.vipro.rust_ast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GenerateClasses {

  static class Class {
    String name;
    int i;

    Class(String name) {this.name = name;}

    Class(String name, int i) {
      this.name = name;
      this.i = i;
    }

    Class() {this.name = "Empty";}

    final List<Class> another = new ArrayList<>();

    void hello() {
      //if (helloCalled) return;
      //helloCalled = true;
      System.out.println("Hello from " + name);
      for (int i = 0; i < another.size(); i++) {
        another.get(i).helloIndex(i);
      }
    }

    void helloIndex(int index) {
      System.out.println("  Hello" + (index + 1) + " from " + name);
      if (index < another.size()) another.get(index).helloIndex(index);
    }

    boolean helloCalled;

    public void clean() {
      helloCalled = false;
    }

    public void printHeaderTo(String dir, int helloIndexCount) {
      File file = new File(dir + "/" + name + ".h");
      file.getParentFile().mkdirs();
      try (PrintStream pr = new PrintStream(file, "UTF-8")) {

        pr.println("#ifndef   LEARN_EIGEN_" + name.toUpperCase() + "_H");
        pr.println("#define   LEARN_EIGEN_" + name.toUpperCase() + "_H");
        pr.println();
        for (Class c : another) {
          pr.println("#include \"" + c.name + ".h\"");
        }
        pr.println();
        another.stream()
            .map(c -> c.name)
            .distinct()
            .sorted()
            .forEachOrdered(aName -> pr.println("class " + aName + ";"));

        pr.println();
        pr.println("class " + name + " {");
        pr.println("private:");
        for (int i = 0; i < another.size(); i++) {
          pr.println("  " + another.get(i).name + "* c" + (i + 1) + ";");
        }

        pr.println("public:");
        pr.println("  " + name + "(int number);");
        pr.println("  ~" + name + "();");
        pr.println();
        pr.println("  void hello();");
        pr.println("};");
        pr.println();
        pr.println("#endif  //LEARN_EIGEN_" + name.toUpperCase() + "_H");

      } catch (FileNotFoundException | UnsupportedEncodingException e) {
        throw new RuntimeException(e);
      }
    }

    public void printCppTo(String dir, int helloIndexCount) {
      File file = new File(dir + "/" + name + ".cpp");
      file.getParentFile().mkdirs();
      try (PrintStream pr = new PrintStream(file, "UTF-8")) {
        pr.println("#include <iostream>");
        pr.println("#include \"" + name + ".h\"");
        pr.println();
        pr.println("using namespace std;");
        pr.println();
        pr.println(name + "::" + name + "(int number) {");

        if (another.size() > 0) {

          for (int i = 0; i < another.size(); i++) {
            int I = i + 1;
            pr.println("  c" + I + " = nullptr;");
          }
          pr.println("  switch(number) {");
          pr.println("    case 0:");
          for (int i = 0; i < another.size(); i++) {
            int I = i + 1;
            String aName = another.get(i).name;
            pr.println("      c" + I + " = new " + aName + "(" + I + ");");
          }
          pr.println("      return;");
          for (int i = 0; i < another.size(); i++) {
            int I = i + 1;
            String aName = another.get(i).name;
            pr.println("    case " + I + ":");
            pr.println("      c" + I + " = new " + aName + "(" + I + ");");
            pr.println("      return;");
          }
          pr.println("    default:");
          pr.println("      return;");
          pr.println("  }");
        }
        pr.println("}");
        pr.println();
        pr.println(name + "::~" + name + "() {");
        for (int i = 0; i < another.size(); i++) {
          int I = i + 1;
          pr.println("  delete c" + I + ";");
        }
        pr.println("}");
        pr.println();
        pr.println("void " + name + "::hello() {");
        pr.println("  cout << \"Hello from " + name + "\" << endl;");
        for (int i = 1; i <= another.size(); i++) {
          pr.println("  if (c" + i + ") c" + i + "->hello();");
        }
        pr.println("}");

      } catch (FileNotFoundException | UnsupportedEncodingException e) {
        throw new RuntimeException(e);
      }
    }
  }

  static class Generator {
    final Class start = new Class("Start");
    final List<Class> exists = new ArrayList<>();
    int limit = 10000, calls = 3, deep = 3;

    int helloIndexCount;

    final List<Class> all = new ArrayList<>();

    public void generate() {
      int max = ("" + limit).length();
      StringBuilder sb = new StringBuilder(max);
      for (int i = 0; i < limit; i++) {
        sb.setLength(0);
        sb.append(i + 1);
        while (sb.length() < max) sb.insert(0, "0");
        exists.add(new Class("Class" + sb, i));
      }


      all.add(start);
      Class finish = new Class("ClassFinish");
      all.add(finish);

      for (Class c : exists) {
        for (int i = 0; i < calls; i++) {
          c.another.add(finish);
        }
      }

      for (int i = 0; i < calls; i++) {
        Collections.shuffle(exists);
        start.another.add(exists.get(0));
        for (int j = 1; j < deep; j++) {
          exists.get(j - 1).another.set(i, exists.get(j));
        }
      }

      exists.sort(Comparator.comparing(c -> c.i));
      all.addAll(exists);

      helloIndexCount = 0;
      for (Class c : all) {
        if (helloIndexCount < c.another.size()) helloIndexCount = c.another.size();
      }
    }

    public void print(String dirTo) {
      String classDir = dirTo + "/test_link2";
      all.forEach(a -> a.printHeaderTo(classDir, helloIndexCount));
      all.forEach(a -> a.printCppTo(classDir, helloIndexCount));
      printCMake(dirTo, "test_link2");
    }

    private void printCMake(String dirTo, String name) {
      File file = new File(dirTo + "/" + name + ".cmake");
      file.getParentFile().mkdirs();
      try (PrintStream pr = new PrintStream(file, "UTF-8")) {
        pr.println("add_executable(" + name + " " + name + ".cpp");

        for (Class c : all) {
          pr.println("  " + name + "/" + c.name + ".cpp " + name + "/" + c.name + ".h");
        }

        pr.println("  )");
      } catch (FileNotFoundException | UnsupportedEncodingException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public static void main(String[] args) {
    Generator generator = new Generator();
    generator.generate();
    generator.exists.forEach(Class::clean);
    generator.start.hello();
    generator.print("/home/pompei/CLionProjects/learn_eigen");
  }
}
