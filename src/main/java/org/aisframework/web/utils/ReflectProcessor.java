package org.aisframework.web.utils;


import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


/**
 * 反射处理
 * @Author  daiwei
 * @date 2017/12/24
 *
 */
public class ReflectProcessor {

    /**
     * 方法处理
     * <P>
     *     用来绑定方法上的参数
     * </P>
     * @param method
     * @param clazz
     * @param methodname
     * @param value
     * @return
     * @throws Exception
     */
    public static Object parseMethod(final Method method,final Class<?> clazz, String methodname, Object[] value) throws Exception {
        if(value == null){
            value = new Object[]{};
        }
        Object o =null;
        Constructor<?> clazzConstructor = clazz.getConstructor(new Class[]{});
        Object obj = clazzConstructor.newInstance(new Object[]{});
        System.out.println(obj.toString());
        //执行对象里面的方法。即跳转到方法里面执行
        o = method.invoke(obj,value);
        return o;
    }


}
