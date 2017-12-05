package kz.pompei.vipro.core.mediator;

public class RenderCore implements AutoCloseable {
  private long reference;

  static {
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
