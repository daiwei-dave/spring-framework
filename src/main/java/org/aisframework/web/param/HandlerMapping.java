package org.aisframework.web.param;

import org.aisframework.web.structure.MethodPro;
import org.aisframework.web.test.UserController;
import org.aisframework.web.utils.CollectionUtils;
import org.aisframework.web.utils.ReflectProcessor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 *处理器适配器,找到合适的处理器去处理
 * @Auther daiwei
 * @date 2017/12/24
 *
 */
public class HandlerMapping {
    /**
     *方法映射
     * <P>
     *     寻找合适的方法并交给相应的方法去执行
     * </P>
     * @param req
     * @param resp
     * @param methodProMap
     * @param key
     */
    public static void  HandlerMapping(HttpServletRequest req,HttpServletResponse resp, Map<String,MethodPro> methodProMap, String key) {
        try {

        List<String> paramlist = MethodResolver.getMethodNames(methodProMap.get(key).getMethod().getDeclaringClass().getName(), key);
        Map params = req.getParameterMap();
        MethodPro methodPro = methodProMap.get(key);
        Method method = methodPro.getMethod();
        List<String> classNames = CollectionUtils.classArrToStringList(method.getParameterTypes());
        Object[] invokeParamVulue = MethodResolver.paramArray(paramlist, classNames, req, resp, params);;
        Method urlmethod = methodProMap.get(key).getMethod();

            String reqtype = req.getMethod().toUpperCase();

            //http请求处理
            if(methodPro.getUrlStyle().equals("POST")){
                    if(!reqtype.equals("POST")) {

                        resp.sendError(405);
                        //resp.getWriter().print("405 not allowed");
                        return;
                    }
            }

            //String返回方法参数类型处理
            if (urlmethod.getReturnType().getName().equals("java.lang.String")) {

                String uri = ReflectProcessor.parseMethod(method,UserController.class, key, invokeParamVulue).toString();
                req.getRequestDispatcher("WEB-INF/" + uri + ".html").forward(req, resp);
                return;

            //ajax接口处理，即直接返回数据
            } else if (methodProMap.get(key).getAjax()) {
                Object o = ReflectProcessor.parseMethod(method,UserController.class, key, invokeParamVulue);
                //输出到客户端
                resp.getWriter().print(o);
                return;
            }

            ReflectProcessor.parseMethod(method,UserController.class, key, invokeParamVulue);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
