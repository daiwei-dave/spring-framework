package aop;

import org.smart4j.framework.aop.AspectProxy;
import org.smart4j.framework.aop.annotation.Aspect;
import org.smart4j.framework.aop.proxy.ProxyChain;
import org.smart4j.framework.ioc.annotation.Bean;

import java.lang.reflect.Method;

/**
 * Created by daiwei on 2017/12/25.
 */
@Bean
@Aspect(pkg = "aop", cls = "TargetObject")
public class TargetObjectAspect extends AspectProxy {

    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
        System.out.println("方法执行之前");
        method.invoke(cls,params);
    }






}
