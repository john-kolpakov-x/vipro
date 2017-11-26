package kz.pompei.vipro.display.impl.leaning;

import kz.pompei.vipro.display.DisplayExpr;
import kz.pompei.vipro.display.DisplayPort;
import kz.pompei.vipro.display.Size;

import java.util.ArrayList;
import java.util.List;

public class DisplayLeanBuilder {

  public enum HorizontalSide {
    LEFT, RIGHT
  }

  public enum VerticalSide {
    TOP, BOTTOM
  }

  public enum HorizontalAlign {
    LEFT, CENTER, RIGHT
  }

  private abstract class DisplayPart {
    protected final DisplayExpr display;
    int deltaX, deltaY;

    protected DisplayPart(DisplayExpr display) {
      this.display = display;
    }
  }

  private class VerticalPart extends DisplayPart {
    private final VerticalSide side;
    private final HorizontalAlign align;
    private final float spaceFactor;

    private VerticalPart(DisplayExpr display, VerticalSide side, HorizontalAlign align, float spaceFactor) {
      super(display);
      this.side = side;
      this.align = align;
      this.spaceFactor = spaceFactor;
    }
  }

  private class HorizontalPart extends DisplayPart {
    private final HorizontalSide side;
    private final float upFactor;
    private final float spaceFactor;

    public HorizontalPart(DisplayExpr display, HorizontalSide side, float upFactor, float spaceFactor) {
      super(display);
      this.side = side;
      this.upFactor = upFactor;
      this.spaceFactor = spaceFactor;
    }
  }

  private final DisplayExpr base;

  private final List<DisplayPart> list = new ArrayList<>();

  private DisplayLeanBuilder(DisplayExpr base) {
    this.base = base;
  }

  public static DisplayLeanBuilder around(DisplayExpr base) {
    return new DisplayLeanBuilder(base);
  }

  public DisplayLeanBuilder onRight(DisplayExpr display, float upFactor, float spaceFactor) {
    return horizontal(display, HorizontalSide.RIGHT, upFactor, spaceFactor);
  }

  public DisplayLeanBuilder onLeft(DisplayExpr display, float upFactor, float spaceFactor) {
    return horizontal(display, HorizontalSide.LEFT, upFactor, spaceFactor);
  }

  public DisplayLeanBuilder horizontal(DisplayExpr display, HorizontalSide side, float upFactor, float spaceFactor) {
    list.add(new HorizontalPart(display, side, upFactor, spaceFactor));
    return this;
  }

  public DisplayLeanBuilder vertical(DisplayExpr display, VerticalSide side, HorizontalAlign align, float spaceFactor) {
    list.add(new VerticalPart(display, side, align, spaceFactor));
    return this;
  }

  public DisplayLeanBuilder onTop(DisplayExpr display, HorizontalAlign align, float spaceFactor) {
    return vertical(display, VerticalSide.TOP, align, spaceFactor);
  }

  public DisplayLeanBuilder onTopLeft(DisplayExpr display, float spaceFactor) {
    return onTop(display, HorizontalAlign.LEFT, spaceFactor);
  }

  public DisplayLeanBuilder onTopRight(DisplayExpr display, float spaceFactor) {
    return onTop(display, HorizontalAlign.RIGHT, spaceFactor);
  }

  public DisplayLeanBuilder onTopCenter(DisplayExpr display, float spaceFactor) {
    return onTop(display, HorizontalAlign.CENTER, spaceFactor);
  }

  public DisplayLeanBuilder onBottomLeft(DisplayExpr display, float spaceFactor) {
    return onBottom(display, HorizontalAlign.LEFT, spaceFactor);
  }

  public DisplayLeanBuilder onBottomRight(DisplayExpr display, float spaceFactor) {
    return onBottom(display, HorizontalAlign.RIGHT, spaceFactor);
  }

  public DisplayLeanBuilder onBottomCenter(DisplayExpr display, float spaceFactor) {
    return onBottom(display, HorizontalAlign.CENTER, spaceFactor);
  }

  public DisplayLeanBuilder onBottom(DisplayExpr display, HorizontalAlign align, float spaceFactor) {
    return vertical(display, VerticalSide.BOTTOM, align, spaceFactor);
  }

  private boolean built = false;

  public DisplayExpr build() {
    if (built) throw new RuntimeException("TYou can build only once");
    built = true;
    return new DisplayExpr() {

      private Size size = null;
      int baseDeltaX, baseDeltaY;

      @Override
      public void setPort(DisplayPort port) {
        base.setPort(port);
        for (DisplayPart part : list) {
          part.display.setPort(port);
        }
      }

      @Override
      public void reset() {
        size = null;
        base.reset();
        for (DisplayPart part : list) {
          part.display.reset();
        }
      }

      @Override
      public void displayTo(int x, int y) {
        size();
        base.displayTo(x + baseDeltaX, y + baseDeltaY);
        for (DisplayPart part : list) {
          part.display.displayTo(x + part.deltaX, y + part.deltaY);
        }
      }

      @Override
      public Size size() {
        if (size != null) return size;

        Size baseSize = base.size();
        float baseHeight = baseSize.height();
        baseDeltaX = baseDeltaY = 0;

        int leftDist = 0, topDist = 0, rightDist = 0, bottomDist = 0;
        int topOffset = 0, bottomOffset = 0;

        for (DisplayPart part : list) {
          Size partSize = part.display.size();

          if (part instanceof HorizontalPart) {
            HorizontalPart p = (HorizontalPart) part;

            float yUnit;
            if (p.upFactor > 0) {
              yUnit = baseSize.top + partSize.bottom;
            } else {
              yUnit = baseSize.bottom + partSize.top;
            }

            p.deltaY = Math.round(yUnit * p.upFactor);

            {
              int localTopDist = partSize.top + p.deltaY - baseSize.top;
              if (topDist < localTopDist) topDist = localTopDist;
            }
            {
              int localBottomDist = partSize.bottom - p.deltaY - baseSize.bottom;
              if (bottomDist < localBottomDist) bottomDist = localBottomDist;
            }

            int space = Math.round(baseHeight * p.spaceFactor);
            int width = space + partSize.width;

            switch (p.side) {
              case LEFT: {
                p.deltaX = -width;
                if (leftDist < width) leftDist = width;
                break;
              }
              case RIGHT: {
                p.deltaX = baseSize.width + space;
                if (rightDist < width) rightDist = width;
                break;
              }
              default:
                throw new RuntimeException("Unknown side = " + p.side);
            }

            continue;
          }

          if (part instanceof VerticalPart) {
            VerticalPart p = (VerticalPart) part;

            Size partSide = p.display.size();

            int space = Math.round(baseHeight * p.spaceFactor);

            int height = space + partSide.height();

            switch (p.side) {
              case TOP: {
                p.deltaY = baseSize.top + space + partSize.bottom + topOffset;
                topOffset += height;
                break;
              }
              case BOTTOM: {
                p.deltaY = -(baseSize.bottom + space + partSize.top + bottomOffset);
                bottomOffset += height;
                break;
              }
              default:
                throw new RuntimeException("Unknown value of side = " + p.side);
            }

            switch (p.align) {

              case LEFT: {
                p.deltaX = 0;
                int deltaWidth = partSide.width - baseSize.width;
                if (rightDist < deltaWidth) rightDist = deltaWidth;
                break;
              }

              case CENTER: {
                p.deltaX = (baseSize.width - partSize.width) / 2;
                if (p.deltaX < 0) {
                  int localLeftDist = -p.deltaX;
                  int localRightDist = partSize.width - baseSize.width - localLeftDist;
                  if (leftDist < localLeftDist) leftDist = localLeftDist;
                  if (rightDist < localRightDist) rightDist = localRightDist;
                }
                break;
              }

              case RIGHT: {
                p.deltaX = baseSize.width - partSide.width;
                if (leftDist < -p.deltaX) leftDist = -p.deltaX;
                break;
              }

              default:
                throw new RuntimeException("Unknown value of align = " + p.align);
            }
            continue;
          }

          throw new RuntimeException("Unknown part class " + part.getClass());
        }

        {
          baseDeltaX += leftDist;
          for (DisplayPart part : list) {
            part.deltaX += leftDist;
            part.deltaY = -part.deltaY;
          }

          if (topDist < topOffset) topDist = topOffset;
          if (bottomDist < bottomOffset) bottomDist = bottomOffset;

          int width = leftDist + baseSize.width + rightDist;
          int top = baseSize.top + topDist;
          int bottom = baseSize.bottom + bottomDist;

          return size = new Size(top, bottom, width);
        }
      }
    };
  }
}
