package kz.pompei.vipro.schema;

import kz.pompei.vipro.model.MethodDefinition;

public interface SchemaProducer {
  MethodDefinitionSchema forMethodDefinition(MethodDefinition md);
}
