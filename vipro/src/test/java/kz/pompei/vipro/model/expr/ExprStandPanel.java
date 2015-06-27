package kz.pompei.vipro.model.expr;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;

import kz.pompei.vipro.model.expr.visitor.draw.DrawVisitor;
import kz.pompei.vipro.model.expr.visitor.draw.DrawVisitorStyleDefault;
import kz.pompei.vipro.model.expr.visitor.draw.ExprDrawer;

public class ExprStandPanel extends JPanel {
  
  private Expr expr;
  
  private Point mouse = null;
  
  public void setExpr(Expr expr) {
    this.expr = expr;
    repaint();
  }
  
  public ExprStandPanel() {
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          redraw();
        }
      }
    });
    
    addMouseMotionListener(new MouseMotionAdapter() {
      @Override
      public void mouseMoved(MouseEvent e) {
        mouse = new Point(e.getX(), e.getY());
        redraw();
      }
    });
  }
  
  private void redraw() {
    repaint();
  }
  
  @Override
  public void paint(Graphics g) {
    super.paint(g);
    paintForm((Graphics2D)g);
  }
  
  private void paintForm(Graphics2D g) {
    if (expr == null) return;
    
    g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    
    DrawVisitorStyleDefault dvc = new DrawVisitorStyleDefault();
    dvc.offset = -3;
    ExprDrawer drawer = expr.visit(new DrawVisitor(g, 0, dvc));
    drawer.drawAt(100, 100 + drawer.ascent(), mouse);
  }
}
