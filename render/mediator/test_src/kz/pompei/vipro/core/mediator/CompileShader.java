package kz.pompei.vipro.core.mediator;

import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class CompileShader {

  public static String vulkanSdkDir() {
    String ret = System.getenv("VULKAN_SDK_DIR");
    if (ret == null || ret.length() == 0) throw new RuntimeException("No ENV VULKAN_SDK_DIR");
    return ret;
  }

  private static final String BIN = vulkanSdkDir() + "/x86_64/bin";
  private static final String EXE = "glslangValidator";

  static class Args {
    public String inFileName = null;
    public String hppDirName = null;
    public String spvDirName = null;

    public Args(String[] args) throws Exception {
      for (int i = 0, n = args.length / 2; i < n; i++) {
        String key = args[2 * i], value = args[2 * i + 1];
        getClass().getField(key).set(this, value);
      }
    }

    File inFile() {
      File inFile = new File(inFileName);
      if (!inFile.exists()) throw new RuntimeException("No file " + inFileName);
      return inFile;
    }

    File spvFile() {
      File dir = new File(spvDirName);
      dir.mkdirs();
      return dir.toPath().resolve(inFile().getName() + ".spv").toFile();
    }

    File hppFile() {
      File dir = new File(hppDirName);
      dir.mkdirs();
      return dir.toPath().resolve(inFile().getName() + ".spv.hpp").toFile();
    }
  }

  public static void main(String[] args) throws Exception {
    Args a = new Args(args);

    List<String> cmd = new ArrayList<>();
    cmd.add(BIN + "/" + EXE);
    cmd.add("-V");
    cmd.add("-o");
    cmd.add(a.spvFile().getAbsolutePath());
    cmd.add(a.inFile().getAbsolutePath());

//    System.out.println(cmd.stream().collect(Collectors.joining(" ")));

    Process process = new ProcessBuilder()
        .command(cmd)
        .redirectError(ProcessBuilder.Redirect.INHERIT)
        .redirectOutput(ProcessBuilder.Redirect.INHERIT)
        .start();
    int exitCode = process.waitFor();
    if (exitCode != 0) throw new RuntimeException("Cannot compile " + a.inFileName + ": exit code = " + exitCode);

    {
      ByteArrayOutputStream out = new ByteArrayOutputStream((int) a.spvFile().length());
      try (FileInputStream in = new FileInputStream(a.spvFile())) {
        IOUtils.copy(in, out);
      }

      convertAndPrint(a.hppFile(), out.toByteArray());
    }

//    try (FileInputStream in = new FileInputStream(a.inFile())) {
//      try (FileOutputStream out = new FileOutputStream(
//          a.hppFile().getParentFile().toPath().resolve(a.inFile().getName()).toFile())
//      ) {
//        IOUtils.copy(in, out);
//      }
//    }
  }

  public static int byteToInt(byte b) {
    int ret = b;
    if (ret < 0) ret += 256;
    return ret;
  }

  @Test
  public void byteToInt_test() {
    for (int i = 0; i < 256; i++) {
      byte b = (byte) i;
      System.out.println("i = " + i + ", b = " + b);
      assertThat(byteToInt((byte) i)).describedAs("i = " + i).isEqualTo(i);
    }
  }

  private static void convertAndPrint(File hppFile, byte[] bytes) throws Exception {
    final int LineLen = 120;

    try (PrintStream pr = new PrintStream(hppFile, "UTF-8")) {
      String var = hppFile.getName().toUpperCase().replace('.', '_');
      pr.println("static const unsigned int  " + var + "_LEN = " + bytes.length + ";");
      pr.println("static const unsigned char " + var + "_DATA[] = {");
      StringBuilder line = new StringBuilder(LineLen);
      boolean first = true;

      for (byte b : bytes) {
        String part = " " + byteToInt(b) + ",";
        if (line.length() + part.length() > LineLen) {
          if (first) first = false;
          else pr.println();
          pr.print(line);
          line.setLength(0);
        }
        line.append(part);
      }
      if (line.length() > 0) {
        if (!first) pr.println();
        pr.print(line);
      }

      pr.println("\n};");
    }
  }
}
