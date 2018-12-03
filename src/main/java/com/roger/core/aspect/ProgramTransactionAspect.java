package com.roger.core.aspect;

import com.roger.core.utils.TransactionUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//一定要把切面交给Spring管理
//在测试声明式事务的时候，需要从Spring的容器中收回编程式事务的切面
//@Component
@Aspect
public class ProgramTransactionAspect {

    @Autowired(required = false)
    private TransactionUtil transactionUtil;

    @Pointcut("execution(* com.roger.biz.service.impl..*.*(..))")
    public void addTransaction(){

    }

    //异常通知：给添加事务的方法回滚事务，当方法抛出异常时
    @AfterThrowing("addTransaction()")
    public void rollbackTransaction(){
        //获取当前事务，然后回滚
        transactionUtil.rollback();
    }

    //环绕通知：给需要添加事务的方法，手动开启事务和提交事务
    @Around("addTransaction()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable{
        transactionUtil.begin();
        joinPoint.proceed();
        transactionUtil.commit();
    }
}
