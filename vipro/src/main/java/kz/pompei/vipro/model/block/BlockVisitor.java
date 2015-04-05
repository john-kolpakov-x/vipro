package kz.pompei.vipro.model.block;

public interface BlockVisitor<Ret> {
  
  Ret visitMethodDefinition(BlockMethodDefinition methodDefinition);
  
  Ret visitBlockClass(BlockClass blockClass);
  
  Ret visitExprBlock(BlockExpr exprBlock);
  
}
