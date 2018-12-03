package com.roger.core.aspect;

import com.roger.core.annotaion.CustomTransactional;
import com.roger.core.utils.TransactionUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

////在测试编程式事务的时候，需要从Spring的容器中收回声明式事务的切面
@Component//一定要把切面交给Spring管理
@Aspect
public class DeclareTransactionAspect {

    @Autowired(required = false)
    private TransactionUtil transactionUtil;

    @Pointcut("execution(* com.roger.biz.service.impl..*.*(..))")
    public void addTransaction() {

    }

    //异常通知：给添加事务的方法回滚事务，当方法抛出异常时
    @AfterThrowing("addTransaction()")
    public void rollbackTransaction() {
        transactionUtil.rollback();
    }

    //环绕通知：给需要添加事务的方法，手动开启事务和提交事务
    @Around("addTransaction()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        Class<?> targetCls = joinPoint.getTarget().getClass();
        //获取即将要执行方法
        Method method = getInvokedMethod(targetCls, joinPoint);
        if (method == null) {
            joinPoint.proceed();
            return;
        }
        //判断执行方法是否有事务注解
        Annotation customTransAnno = method.getAnnotation(CustomTransactional.class);
        if (customTransAnno == null) {
            System.out.println("方法上没有事务注解，直接开始执行方法...");
            joinPoint.proceed();
            return;
        }

        transactionUtil.begin();
        joinPoint.proceed();

        transactionUtil.commit();
    }

    private Method getInvokedMethod(Class targetCls, ProceedingJoinPoint pJoinPoint) {
        List<Class<? extends Object>> clazzList = new ArrayList<>();
        Object[] args = pJoinPoint.getArgs();
        for (Object arg : args) {
            clazzList.add(arg.getClass());
        }

        Class[] argsCls = (Class[]) clazzList.toArray(new Class[0]);

        String methodName = pJoinPoint.getSignature().getName();
        Method method = null;
        try {
            method = targetCls.getMethod(methodName, argsCls);
        } catch (NoSuchMethodException e) {
            //不做任何处理,这个切面只处理事务相关逻辑
            //其他任何异常不在这个切面的考虑范围
        }
        return method;
    }
}
