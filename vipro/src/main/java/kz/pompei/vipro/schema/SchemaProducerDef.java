package kz.pompei.vipro.schema;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;

import kz.pompei.vipro.model.block.BlockMethodDefinition;

public class SchemaProducerDef implements SchemaProducer {
  
  private final Graphics2D g;
  
  public SchemaProducerDef(Graphics2D g) {
    this.g = g;
  }
  
  @Override
  public MethodDefinitionSchema forMethodDefinition(BlockMethodDefinition md, Point mouse) {
    MethodDefinitionSchema ret = new MethodDefinitionSchema();
    ret.x = ret.y = 0;
    Point loc = md.location;
    if (loc != null) {
      ret.x = loc.x;
      ret.y = loc.y;
    }
    
    ret.nameFontSize = 14;
    
    g.setFont(g.getFont().deriveFont(ret.nameFontSize));
    
    FontMetrics fontMetrics = g.getFontMetrics(g.getFont().deriveFont(ret.nameFontSize));
    int ascent = fontMetrics.getAscent();
    int descent = fontMetrics.getDescent();
    int strWidth = 0;
    if (md.name != null) {
      strWidth = fontMetrics.stringWidth(md.name);
    }
    
    ret.width = 4 + strWidth + 4;
    ret.height = 4 + ascent + descent + 4;
    
    ret.nameX = ret.x + 4;
    ret.nameY = ret.y + 4 + ascent;
    
    {
      Point near = ret.getPlace().near(mouse, 3);
      if (near != null) {
        ret.near = new NearSchema(near, 3);
      }
    }
    
    return ret;
  }
  
}
