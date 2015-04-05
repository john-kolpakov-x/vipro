package kz.pompei.vipro.schema;

import java.awt.Point;

import kz.pompei.vipro.model.MethodDefinition;

public interface SchemaProducer {
  MethodDefinitionSchema forMethodDefinition(MethodDefinition md, Point mouse);
}
