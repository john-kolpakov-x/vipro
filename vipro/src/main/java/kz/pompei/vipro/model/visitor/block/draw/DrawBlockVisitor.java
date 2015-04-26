package kz.pompei.vipro.model.visitor.block.draw;

import static kz.pompei.vipro.util.StrUtil.fnn;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import kz.pompei.vipro.model.block.BlockClass;
import kz.pompei.vipro.model.block.BlockExpr;
import kz.pompei.vipro.model.block.BlockMethodDefinition;
import kz.pompei.vipro.model.block.BlockVisitor;
import kz.pompei.vipro.model.block.LocationableBlock;
import kz.pompei.vipro.model.expr.visitor.drawer.ExprDrawerCreatorVisitor;
import kz.pompei.vipro.schema.MethodDefinitionSchema;
import kz.pompei.vipro.schema.NearSchema;
import kz.pompei.vipro.schema.SchemaProducer;

public class DrawBlockVisitor implements BlockVisitor<Void> {
  
  private final Graphics2D g;
  private final SchemaProducer schemaProducer;
  private final Point mouse;
  
  private final ExprDrawerCreatorVisitor drawExprVisitor;
  
  public DrawBlockVisitor(Graphics2D g, SchemaProducer schemaProducer, Point mouse) {
    this.g = g;
    this.schemaProducer = schemaProducer;
    this.mouse = mouse;
    
    drawExprVisitor = new ExprDrawerCreatorVisitor(g);
  }
  
  @Override
  public Void visitBlockClass(BlockClass blockClass) {
    for (LocationableBlock md : blockClass.content) {
      md.visit(this);
    }
    return null;
  }
  
  @Override
  public Void visitMethodDefinition(BlockMethodDefinition md) {
    MethodDefinitionSchema mds = schemaProducer.forMethodDefinition(md, mouse);
    
    g.setColor(Color.YELLOW);
    g.fillRect(mds.x, mds.y, mds.width, mds.height);
    g.setColor(Color.BLACK);
    g.drawRect(mds.x, mds.y, mds.width, mds.height);
    
    g.setColor(Color.BLACK);
    g.setFont(g.getFont().deriveFont(mds.nameFontSize));
    g.drawString(fnn(md.name), mds.nameX, mds.nameY);
    
    drawNear(g, mds.near);
    
    return null;
  }
  
  private static void drawNear(Graphics2D g, NearSchema near) {
    if (near == null) return;
    
    int x = near.x, y = near.y, r = near.r;
    
    g.setColor(Color.WHITE);
    g.fillArc(x - r, y - r, 2 * r, 2 * r, 0, 360);
    g.setColor(Color.BLACK);
    g.drawArc(x - r, y - r, 2 * r, 2 * r, 0, 360);
  }
  
  @Override
  public Void visitExprBlock(BlockExpr exprBlock) {
    exprBlock.expr.visit(drawExprVisitor);
    return null;
  }
}
