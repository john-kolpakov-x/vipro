package kz.pompei.vipro.model.probes;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import kz.pompei.vipro.model.block.BlockExpr;
import kz.pompei.vipro.model.expr.ExprVar;
import kz.pompei.vipro.model.visitor.block.draw.DrawBlockVisitor;
import kz.pompei.vipro.schema.SchemaProducer;
import kz.pompei.vipro.schema.SchemaProducerDef;

public class ProbeDraw {
  public static void main(String[] args) throws Exception {
    BlockExpr blockExpr = new BlockExpr();
    blockExpr.location = new Point(10, 10);
    
    ExprVar exprVar = new ExprVar("asd");
    blockExpr.expr = exprVar;
    
    BufferedImage img = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = img.createGraphics();
    
    SchemaProducer schemaProducer = new SchemaProducerDef(g);
    DrawBlockVisitor visitor = new DrawBlockVisitor(g, schemaProducer, null);
    
    blockExpr.visit(visitor);
    
    g.dispose();
    
    ImageIO.write(img, "png", new File("build/image.png"));
    
    System.out.println("OK");
  }
}
