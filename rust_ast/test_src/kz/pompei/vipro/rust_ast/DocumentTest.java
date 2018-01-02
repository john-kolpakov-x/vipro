package kz.pompei.vipro.rust_ast;

import org.testng.annotations.Test;

public class DocumentTest {
  @Test
  public void testName() throws Exception {
    Document document = new Document();


    StringBuilder sb = new StringBuilder();
    document.printTo(0, sb);

    System.out.println(sb);
  }
}