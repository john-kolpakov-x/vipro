package kz.pompei.vipro.schema;

import java.awt.Point;

import kz.pompei.vipro.model.block.BlockMethodDefinition;

public interface SchemaProducer {
  MethodDefinitionSchema forMethodDefinition(BlockMethodDefinition md, Point mouse);
}
