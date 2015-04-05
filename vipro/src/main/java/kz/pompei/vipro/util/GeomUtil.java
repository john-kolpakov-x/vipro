package kz.pompei.vipro.util;

import java.awt.Point;

public class GeomUtil {
  /**
   * Определяет расстояние от точки U до прямой, проходящей через точки A и B
   * 
   * @param U
   *          исходная точка
   * @param A
   *          одна точка прямой
   * @param B
   *          другая точка прямой
   * @return Расстояние до прямой от точки U
   */
  public static double dist(Vec2 U, Vec2 A, Vec2 B) {
    return proj(U, A, B).minus(U).len();
  }
  
  /**
   * Определает проекцию точки U на прямую, проходящую через точки A и B
   * 
   * @param U
   *          исходная точка
   * @param A
   *          одна точка прямой
   * @param B
   *          другая точка прямой
   * @return проекция U на прямую AB
   */
  public static Vec2 proj(Vec2 U, Vec2 A, Vec2 B) {
    return A.plus(B.minus(A).mul(projKoor(U, A, B)));
  }
  
  /**
   * Получает координату проекции точки U на прямой, проходящей через точки A и
   * B. Координатная ось на прямой лежит так, что точка A имеет координату 0, а
   * точка B - имеет коодринату 1.
   * 
   * @param U
   *          исходная точка
   * @param A
   *          точка на прямой с координатой 0
   * @param B
   *          точка на прямой с координатой 1
   * @return координата проекции точки U
   */
  public static double projKoor(Vec2 U, Vec2 A, Vec2 B) {
    Vec2 AU = U.minus(A);
    Vec2 AB = B.minus(A);
    
    return AU.mul(AB) / AB.sqr();
  }
  
  public static Point nearest(Point point, Point... nears) {
    double dist = 0;
    Point ret = null;
    for (Point near : nears) {
      if (near == null) continue;
      double newDist = dist(point, near);
      if (ret == null || newDist < dist) {
        ret = near;
        dist = newDist;
      }
    }
    return ret;
  }
  
  public static double dist(Point p1, Point p2) {
    double dx = p1.x - p2.x, dy = p1.y - p2.y;
    return Math.sqrt(dx * dx + dy * dy);
  }
  
}
