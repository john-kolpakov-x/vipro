package kz.pompei.vipro.model.expr.visitor;

import java.util.UUID;

import kz.pompei.vipro.model.expr.Expr;
import kz.pompei.vipro.model.expr.ExprOper1;
import kz.pompei.vipro.model.expr.ExprOper2;
import kz.pompei.vipro.model.expr.ExprStrConst;
import kz.pompei.vipro.model.expr.ExprVar;
import kz.pompei.vipro.model.expr.ExprVisitor;

public class CopyVisitor implements ExprVisitor<Expr> {
  
  private final boolean newId;
  
  public CopyVisitor(boolean newId) {
    this.newId = newId;
  }
  
  @Override
  public Expr visitVar(ExprVar exprVar) {
    ExprVar ret = new ExprVar(id(exprVar.id));
    ret.name = exprVar.name;
    return ret;
  }
  
  private String id(String id) {
    if (newId) return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
    return id;
  }
  
  @Override
  public Expr visitStrConst(ExprStrConst exprStrConst) {
    ExprStrConst ret = new ExprStrConst(exprStrConst.id);
    ret.value = exprStrConst.value;
    return ret;
  }
  
  @Override
  public Expr visitOper1(ExprOper1 exprOper1) {
    ExprOper1 ret = new ExprOper1(exprOper1.id);
    ret.oper = exprOper1.oper;
    ret.target = exprOper1.target.visit(this);
    return ret;
  }
  
  @Override
  public Expr visitOper2(ExprOper2 exprOper2) {
    ExprOper2 ret = new ExprOper2(exprOper2.id);
    ret.oper = exprOper2.oper;
    ret.expr1 = exprOper2.expr1.visit(this);
    ret.expr2 = exprOper2.expr2.visit(this);
    return ret;
  }
}
