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

  private native void destroyReference();

  public native Size getSize();

  long __readReference__() {
    return reference;
  }

  @Override
  public void close() {
    destroyReference();
  }
}
