package org.dw.annotation;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.ConvertUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;
/**
 * Created by daiwei on 2017/11/9.
 */
public class ClassPathXMLApplicationContext {
    private List<BeanDefinition> beanDefines = new ArrayList<BeanDefinition>();
    private Map<String, Object> sigletons = new HashMap<String, Object>();

    public ClassPathXMLApplicationContext(String filename) {
        this.readXML(filename);
        this.instanceBeans();
        this.annotationInject();
        this.injectObject();
    }
    /**
     * 通过注解实现注入依赖对象
     */
    private void annotationInject() {
        for (String beanName : sigletons.keySet()) { // 循环所有的Bean对象
            Object bean = sigletons.get(beanName);
            if (bean != null) {
                try {
                    // 查找属性的setter上是否有注解
                    PropertyDescriptor[] ps = Introspector.getBeanInfo(bean.getClass()).getPropertyDescriptors();
                    for (PropertyDescriptor properdesc : ps) { // 循环所有属性
                        Method setter = properdesc.getWriteMethod();// 获取属性的setter方法
                        if (setter != null && setter.isAnnotationPresent(MyResource.class)) { // 判断MyResource注解是否存在
                            MyResource resource = setter.getAnnotation(MyResource.class);
                            Object injectBean = null;
                            if (resource.name() != null && !"".equals(resource.name())) {
                                injectBean = sigletons.get(resource.name()); // 通过MyResource注解的name属性获取Bean
                            } else {
                                injectBean = sigletons.get(properdesc.getName());
                                if (injectBean == null) { // 没有指定name属性, 根据属性名称进行寻找
                                    for (String key : sigletons.keySet()) {
                                        // 根据属性类型进行寻找
                                        if (properdesc.getPropertyType().isAssignableFrom(sigletons.get(key).getClass())) {
                                            injectBean = sigletons.get(key);
                                            break;
                                        }
                                    }
                                }
                            }
                            setter.setAccessible(true);
                            setter.invoke(bean, injectBean);// 把引用对象注入到属性
                        }
                    }

                    // 查找字段上是否有注解
                    Field[] fields = bean.getClass().getDeclaredFields(); // 取得声明的所有字段
                    for (Field field : fields) {
                        if (field.isAnnotationPresent(MyResource.class)) { // 判断字段上是否存在MyResource注解
                            MyResource resource = field.getAnnotation(MyResource.class);
                            Object value = null;
                            if (resource.name() != null && !"".equals(resource.name())) { // 判断是否指定name属性
                                value = sigletons.get(resource.name());
                            } else {
                                value = sigletons.get(field.getName()); // 没有指定name属性,那么根据字段名称寻找xml中是否有对应的id
                                if (value == null) {
                                    for (String key : sigletons.keySet()) {
                                        // xml中没有指定bean的id，则查看class的value是否是字段的类型超接口关系等
                                        if (field.getType().isAssignableFrom(sigletons.get(key).getClass())) {
                                            value = sigletons.get(key);
                                            break;
                                        }
                                    }
                                }
                            }
                            field.setAccessible(true);// 允许访问private字段
                            field.set(bean, value);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 为bean对象的属性注入值
     */
    private void injectObject() {
        for (BeanDefinition beanDefinition : beanDefines) {
            Object bean = sigletons.get(beanDefinition.getId());
            if (bean != null) {
                try {
                    PropertyDescriptor[] ps = Introspector.getBeanInfo(bean.getClass()).getPropertyDescriptors();
                    for (PropertyDefinition propertyDefinition : beanDefinition.getPropertys()) {
                        for (PropertyDescriptor properdesc : ps) {
                            if (propertyDefinition.getName().equals(properdesc.getName())) {
                                Method setter = properdesc.getWriteMethod(); // 获取属性的setter方法
                                if (setter != null) {
                                    Object injectBean = null;
                                    if (propertyDefinition.getRef() != null && !"".equals(propertyDefinition.getRef().trim())) {
                                        injectBean = sigletons.get(propertyDefinition.getRef());
                                    } else {
                                        injectBean = ConvertUtils.convert(propertyDefinition.getValue(), properdesc.getPropertyType());
                                    }
                                    setter.setAccessible(true); // private method
                                    setter.invoke(bean, injectBean); // 把引用对象注入到属性
                                }
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 完成bean的实例化
     */
    private void instanceBeans() {
        for (BeanDefinition beanDefinition : beanDefines) {
            try {
                if (beanDefinition.getClassName() != null && !"".equals(beanDefinition.getClassName().trim())) {
                    sigletons.put(beanDefinition.getId(), Class.forName(beanDefinition.getClassName()).newInstance());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    /**
     * 读取xml配置文件
     *
     * @param filename
     */
    private void readXML(String filename) {
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            URL xmlpath = this.getClass().getClassLoader().getResource(filename);
            document = saxReader.read(xmlpath);
            Map<String, String> nsMap = new HashMap<String, String>();
            nsMap.put("ns", "http://www.springframework.org/schema/beans");// 加入命名空间
            XPath xsub = document.createXPath("//ns:beans/ns:bean");// 创建beans/bean查询路径
            xsub.setNamespaceURIs(nsMap);// 设置命名空间
            List<Element> beans = xsub.selectNodes(document);// 获取文档下所有bean节点
            for (Element element : beans) {
                String id = element.attributeValue("id");// 获取id属性值
                String clazz = element.attributeValue("class"); // 获取class属性值
                BeanDefinition beanDefine = new BeanDefinition(id, clazz);
                XPath propertysub = element.createXPath("ns:property");//创建/property查询路径
                propertysub.setNamespaceURIs(nsMap);// 设置命名空间
                List<Element> propertys = propertysub.selectNodes(element);
                for (Element property : propertys) {
                    String propertyName = property.attributeValue("name");
                    String propertyRef = property.attributeValue("ref");
                    String propertyValue = property.attributeValue("value");
                    PropertyDefinition propertyDefinition = new PropertyDefinition(propertyName, propertyRef, propertyValue);
                    beanDefine.getPropertys().add(propertyDefinition);
                }
                beanDefines.add(beanDefine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取bean实例
     *
     * @param beanName
     * @return
     */
    public Object getBean(String beanName) {
        return this.sigletons.get(beanName);
    }

}
