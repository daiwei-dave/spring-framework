package org.aisframework.web.utils;


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
        final Object obj = clazz.getConstructor(new Class[] {}).newInstance(new Object[] {});
                    o = method.invoke(obj,value);
        return o;
    }


}
