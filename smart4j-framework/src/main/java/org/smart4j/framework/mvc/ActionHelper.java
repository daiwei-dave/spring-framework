package org.smart4j.framework.mvc;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.smart4j.framework.core.ClassHelper;
import org.smart4j.framework.mvc.annotation.Action;
import org.smart4j.framework.mvc.annotation.Request;
import org.smart4j.framework.util.ArrayUtil;
import org.smart4j.framework.util.CollectionUtil;
import org.smart4j.framework.util.StringUtil;

public class ActionHelper {

    private static final Map<RequestBean, ActionBean> actionMap = new LinkedHashMap<RequestBean, ActionBean>();

    static {
        // 获取所有 Action 类
        List<Class<?>> actionClassList = ClassHelper.getClassListByAnnotation(Action.class);
        if (CollectionUtil.isNotEmpty(actionClassList)) {
            // 定义两个 Action Map
            Map<RequestBean, ActionBean> commonActionMap = new HashMap<RequestBean, ActionBean>(); // 存放普通 Action Map
            Map<RequestBean, ActionBean> regexpActionMap = new HashMap<RequestBean, ActionBean>(); // 存放带有正则表达式的 Action Map
            // 遍历 Action 类
            for (Class<?> actionClass : actionClassList) {
                // 获取并遍历该 Action 类中所有的方法
                Method[] actionMethods = actionClass.getDeclaredMethods();
                if (ArrayUtil.isNotEmpty(actionMethods)) {
                    for (Method actionMethod : actionMethods) {
                        // 处理 Action 方法
                        handleActionMethod(actionClass, actionMethod, commonActionMap, regexpActionMap);
                    }
                }
            }
            // 初始化最终的 Action Map（将 Common 放在 Regexp 前面）
            actionMap.putAll(commonActionMap);
            actionMap.putAll(regexpActionMap);
        }
    }

    private static void handleActionMethod(Class<?> actionClass, Method actionMethod, Map<RequestBean, ActionBean> commonActionMap, Map<RequestBean, ActionBean> regexpActionMap) {
        // 判断当前 Action 方法是否带有 Request 注解
        if (actionMethod.isAnnotationPresent(Request.class)) {
            // 获取 Requet 注解中的 URL 字符串
            String[] urlArray = StringUtil.splitString(actionMethod.getAnnotation(Request.class).value(), ":");
            if (ArrayUtil.isNotEmpty(urlArray)) {
                // 获取请求方法与请求路径
                String requestMethod = urlArray[0];
                String requestPath = urlArray[1];
                // 将 RequestBean 与 ActionBean 放入 Action Map 中
                putActionMap(requestMethod, requestPath, actionClass, actionMethod, commonActionMap, regexpActionMap);
            }
        } else if (actionMethod.isAnnotationPresent(Request.Get.class)) {
            String requestPath = actionMethod.getAnnotation(Request.Get.class).value();
            putActionMap("GET", requestPath, actionClass, actionMethod, commonActionMap, regexpActionMap);
        } else if (actionMethod.isAnnotationPresent(Request.Post.class)) {
            String requestPath = actionMethod.getAnnotation(Request.Post.class).value();
            putActionMap("POST", requestPath, actionClass, actionMethod, commonActionMap, regexpActionMap);
        } else if (actionMethod.isAnnotationPresent(Request.Put.class)) {
            String requestPath = actionMethod.getAnnotation(Request.Put.class).value();
            putActionMap("PUT", requestPath, actionClass, actionMethod, commonActionMap, regexpActionMap);
        } else if (actionMethod.isAnnotationPresent(Request.Delete.class)) {
            String requestPath = actionMethod.getAnnotation(Request.Delete.class).value();
            putActionMap("DELETE", requestPath, actionClass, actionMethod, commonActionMap, regexpActionMap);
        }
    }

    private static void putActionMap(String requestMethod, String requestPath, Class<?> actionClass, Method actionMethod, Map<RequestBean, ActionBean> commonActionMap, Map<RequestBean, ActionBean> regexpActionMap) {
        // 判断 Request Path 中是否带有占位符
        if (requestPath.matches(".+\\{\\w+\\}.*")) {
            // 将请求路径中的占位符 {\w+} 转换为正则表达式 (\\w+)
            requestPath = StringUtil.replaceAll(requestPath, "\\{\\w+\\}", "(\\\\w+)");
            // 将 RequestBean 与 ActionBean 放入 Regexp Action Map 中
            regexpActionMap.put(new RequestBean(requestMethod, requestPath), new ActionBean(actionClass, actionMethod));
        } else {
            // 将 RequestBean 与 ActionBean 放入 Common Action Map 中
            commonActionMap.put(new RequestBean(requestMethod, requestPath), new ActionBean(actionClass, actionMethod));
        }
    }

    public static Map<RequestBean, ActionBean> getActionMap() {
        return actionMap;
    }

    public static class RequestBean {

        private String requestMethod;
        private String requestPath;

        public RequestBean(String requestMethod, String requestPath) {
            this.requestMethod = requestMethod;
            this.requestPath = requestPath;
        }

        public String getRequestMethod() {
            return requestMethod;
        }

        public String getRequestPath() {
            return requestPath;
        }
    }

    public static class ActionBean {

        private Class<?> actionClass;
        private Method actionMethod;

        public ActionBean(Class<?> actionClass, Method actionMethod) {
            this.actionClass = actionClass;
            this.actionMethod = actionMethod;
        }

        public Class<?> getActionClass() {
            return actionClass;
        }

        public Method getActionMethod() {
            return actionMethod;
        }
    }
}