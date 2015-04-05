package kz.pompei.vipro;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;

import kz.pompei.vipro.model.block.BlockClass;
import kz.pompei.vipro.model.visitor.block.draw.DrawBlockVisitor;
import kz.pompei.vipro.schema.SchemaProducerDef;

public class ViproEditorPanel extends JPanel {
  
  public final BlockClass source = new BlockClass();
  
  public ViproEditorPanel() {
    addMouseMotionListener(new MouseMotionAdapter() {
      @Override
      public void mouseMoved(MouseEvent e) {
        mouseMovedTo(e.getX(), e.getY());
      }
    });
  }
  
  private final Point mouse = new Point();
  
  private void mouseMovedTo(int x, int y) {
    mouse.setLocation(x, y);
    repaint();
  }
  
  @Override
  public void paint(Graphics g) {
    super.paint(g);
    draw((Graphics2D)g);
  }
  
  private void draw(Graphics2D g) {
    SchemaProducerDef schema = new SchemaProducerDef(g);
    DrawBlockVisitor v = new DrawBlockVisitor(g, schema, mouse);
    source.visit(v);
  }
}
