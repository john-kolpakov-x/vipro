package kz.pompei.vipro.model.expr;

public interface ExprVisitor<Ret> {
  
  Ret visitVar(ExprVar exprVar);
  
  Ret visitStrConst(ExprStrConst exprStrConst);
  
  Ret visitOper1(ExptOper1 exptOper1);
  
  Ret visitOper2(ExprOper2 exprOper2);
  
}
