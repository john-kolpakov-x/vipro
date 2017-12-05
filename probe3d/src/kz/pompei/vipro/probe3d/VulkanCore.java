package kz.pompei.vipro.probe3d;

public class VulkanCore implements AutoCloseable {

  static {
    String d = "/home/pompei/CLionProjects/vipro_render/cmake-build-debug";
    String name = "libvipro_render.so";
    System.load(d + "/" + name);

    init0();
  }

  private static native void init0();

  private long reference;

  private native void initReference();

  private native void destroyReference();

  private native int readWidth();

  private native int readHeight();

  public VulkanCore() {
    reference = 0;
    initReference();
  }

  long __readReference() {
    return reference;
  }

  @Override
  public void close() throws Exception {
    if (reference != 0) destroyReference();
  }

  public int width() {
    return readWidth();
  }

  public int height() {
    return readHeight();
  }
}
