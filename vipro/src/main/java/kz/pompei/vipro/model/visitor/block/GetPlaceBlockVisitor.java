package kz.pompei.vipro.model.visitor.block;

import kz.pompei.vipro.model.block.BlockClass;
import kz.pompei.vipro.model.block.BlockVisitor;
import kz.pompei.vipro.model.block.BlockExpr;
import kz.pompei.vipro.model.block.LocationableBlock;
import kz.pompei.vipro.model.block.BlockMethodDefinition;
import kz.pompei.vipro.schema.SchemaProducer;
import kz.pompei.vipro.util.Place;

public class GetPlaceBlockVisitor implements BlockVisitor<Place> {
  
  private final SchemaProducer schemaProducer;
  
  public GetPlaceBlockVisitor(SchemaProducer schemaProducer) {
    this.schemaProducer = schemaProducer;
  }
  
  @Override
  public Place visitBlockClass(BlockClass bc) {
    Place ret = null;
    for (LocationableBlock md : bc.content) {
      Place p = md.visit(this);
      if (ret == null) {
        ret = p;
      } else {
        ret.unionWithMe(p);
      }
    }
    if (ret == null) return new Place();
    return ret;
  }
  
  @Override
  public Place visitMethodDefinition(BlockMethodDefinition md) {
    return schemaProducer.forMethodDefinition(md, null).getPlace();
  }
  
  @Override
  public Place visitExprBlock(BlockExpr exprBlock) {
    throw new RuntimeException();
  }
}
