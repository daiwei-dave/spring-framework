package org.smart4j.framework.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.smart4j.framework.ds.DataSourceFactory;
import org.smart4j.framework.ds.impl.DefaultDataSourceFactory;
import org.smart4j.framework.mvc.HandlerMapping;
import org.smart4j.framework.mvc.impl.DefaultHandlerMapping;
import org.smart4j.framework.util.ObjectUtil;
import org.smart4j.framework.util.StringUtil;

/**
 * 实例工厂
 *
 * @author huangyong
 * @since 2.3
 */
public class InstanceFactory {

    private static final Map<String, Object> cache = new ConcurrentHashMap<String, Object>();

    private static final String DS_FACTORY = "smart.ds_factory";
    private static final String ACTION_HANDLER = "smart.action_handler";

    public static DataSourceFactory createDataSourceFactory() {
        return createInstance(DS_FACTORY, DefaultDataSourceFactory.class);
    }

    public static HandlerMapping createActionHandler() {
        return createInstance(ACTION_HANDLER, DefaultHandlerMapping.class);
    }

    @SuppressWarnings("unchecked")
    public static <T> T createInstance(String cacheKey, Class<T> defaultImplClass) {
        // 判断缓存中是否存在该实例
        if (cache.containsKey(cacheKey)) {
            // 若缓存中已存在，则返回缓存中的实例
            return (T) cache.get(cacheKey);
        }
        // 从配置文件中获取相应的接口实现类配置
        String implClassName = ConfigHelper.getConfigString(cacheKey);
        // 若实现类配置不存在，则使用默认实现类
        if (StringUtil.isEmpty(implClassName)) {
            implClassName = defaultImplClass.getName();
        }
        // 通过反射创建该实现类对应的实例
        T instance = ObjectUtil.newInstance(implClassName);
        // 若该实例不为空，则将该实例放入缓存
        if (instance != null) {
            cache.put(cacheKey, instance);
        }
        // 返回该实例
        return instance;
    }
}