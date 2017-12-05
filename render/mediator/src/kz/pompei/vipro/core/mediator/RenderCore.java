package kz.pompei.vipro.core.mediator;

import java.io.File;

public class RenderCore implements AutoCloseable {
  @SuppressWarnings("unused")
  private long reference;

  static {
    String d = "render/core/cmake-build-debug";
    String name = "libvipro_render_core.so";
    File file = new File(d + "/" + name);
    System.load(file.getAbsolutePath());
    init0();
  }

  private static native void init0();

  public RenderCore() {
    initReference();
  }

  private native void initReference();

  public native void initialize();

  private native void destroyReference();

  private native void putSize(int sizePlace[]);

  @SuppressWarnings("unused")
  private void throwMessage(String message) {
    throw new RuntimeException(message);
  }

  public Size getSize() {
    int sizePlace[] = new int[2];
    putSize(sizePlace);
    Size ret = new Size();
    ret.width = sizePlace[0];
    ret.height = sizePlace[1];
    return ret;
  }

  long __readReference__() {
    return reference;
  }

  @Override
  public void close() {
    destroyReference();
  }
}
