package kz.pompei.vipro.model.visitor.block;

import kz.pompei.vipro.model.BlockClass;
import kz.pompei.vipro.model.BlockVisitor;
import kz.pompei.vipro.model.MethodDefinition;
import kz.pompei.vipro.model.Place;
import kz.pompei.vipro.schema.MethodDefinitionSchema;
import kz.pompei.vipro.schema.SchemaProducer;

public class GetPlaceBlockVisitor implements BlockVisitor<Place> {
  
  private final SchemaProducer schemaProducer;
  
  public GetPlaceBlockVisitor(SchemaProducer schemaProducer) {
    this.schemaProducer = schemaProducer;
  }
  
  @Override
  public Place visitBlockClass(BlockClass bc) {
    Place ret = null;
    for (MethodDefinition md : bc.methodList) {
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
  public Place visitMethodDefinition(MethodDefinition md) {
    MethodDefinitionSchema schema = schemaProducer.forMethodDefinition(md);
    return schema.getPlace();
  }
}
