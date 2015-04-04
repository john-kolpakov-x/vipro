package kz.pompei.vipro.model;

public interface BlockVisitor<Ret> {
  
  Ret visitMethodDefinition(MethodDefinition methodDefinition);
  
  Ret visitBlockClass(BlockClass blockClass);
  
}
