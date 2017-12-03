package org.dw.annotation.myComponent;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Administrator on 2017/12/3.
 */
@Configuration
public class CustomizeScanTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.register(CustomizeScanTest.class);
        annotationConfigApplicationContext.refresh();
        ScanClass1 injectClass = annotationConfigApplicationContext.getBean(ScanClass1.class);
        injectClass.print();
    }

    /**
     * BeanScannerConfigurer用于嵌入到Spring的加载过程的中，
     * 这里用到了BeanFactoryPostProcessor 和 ApplicationContextAware。
     *Spring提供了一些的接口使程序可以嵌入Spring的加载过程。
     * 这个类中的继承ApplicationContextAware接口，Spring会读取ApplicationContextAware类型的的JavaBean，
     * 并调用setApplicationContext(ApplicationContext applicationContext)传入Spring的applicationContext。
     * 同样继承BeanFactoryPostProcessor接口，
     * Spring会在BeanFactory的相关处理完成后调用postProcessBeanFactory方法，进行定制的功能。
     作者：wcong
     链接：http://www.jianshu.com/p/7c2948f64b1c
     來源：简书
     著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
    @Component
    public static class BeanScannerConfigurer implements BeanFactoryPostProcessor, ApplicationContextAware {

        private ApplicationContext applicationContext;

        /**
         * 继承BeanFactoryPostProcessor接口，
         * Spring会在BeanFactory的相关处理完成后调用postProcessBeanFactory方法，进行定制的功能。
         * @param beanFactory
         * @throws BeansException
         */
        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            Scanner scanner = new Scanner((BeanDefinitionRegistry) beanFactory);
            scanner.setResourceLoader(this.applicationContext);
            scanner.scan("org.wcong.test.spring.scan");
        }

        /**
         *
         * @param applicationContext
         * @throws BeansException
         */
        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = applicationContext;
        }
    }

    /**
     * Scanner继承的ClassPathBeanDefinitionScanner是Spring内置的Bean定义的扫描器。
     */
    public final static class Scanner extends ClassPathBeanDefinitionScanner {
        /**
         *
         * @param registry
         */
        public Scanner(BeanDefinitionRegistry registry) {
            super(registry);
        }

        /**
         * Register the default filter for {@link Component @Component}.
         * includeFilter里定义了类的过滤器
         *
         */
        @Override
        public void registerDefaultFilters() {
            //newAnnotationTypeFilter(CustomizeComponent.class)表示只取被CustomizeComponent修饰的类。
            this.addExcludeFilter(new AnnotationTypeFilter(CustomizeComponent.class));
        }

        /**
         * Perform a scan within the specified base packages,
         * returning the registered bean definitions.
         * doScan里扫面了包底下的读取道德BeanDefinitionHolder，自定义GenericBeanDefinition相关功能。
         * @param basePackages
         * @return
         */
        public Set<BeanDefinitionHolder> doScan(String... basePackages) {
            Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
            for (BeanDefinitionHolder holder : beanDefinitions) {
                GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
                definition.getPropertyValues().add("innerClassName", definition.getBeanClassName());
                definition.setBeanClass(FactoryBeanTest.class);
            }
            return beanDefinitions;
        }



    }

    public static class FactoryBeanTest<T> implements InitializingBean, FactoryBean<T> {
        private String innerClassName;

        public void setInnerClassName(String innerClassName) {
            this.innerClassName = innerClassName;
        }

        /**
         * Return an instance (possibly shared or independent) of the object
         * managed by this factory.
         * @return
         * @throws Exception
         */
        @Override
        public T getObject() throws Exception {
            Class innerClass = Class.forName(innerClassName);
            if (innerClass.isInterface()) {
                return (T) InterfaceProxy.newInstance(innerClass);
            } else {

            }
            return null;
        }

        @Override
        public Class<?> getObjectType() {
            return null;
        }

        @Override
        public boolean isSingleton() {
            return false;
        }

        @Override
        public void afterPropertiesSet() throws Exception {

        }
    }

    public static class InterfaceProxy implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return null;
        }

        public static <T> T newInstance(Class<T> innerInterface) {
            ClassLoader classLoader = innerInterface.getClassLoader();
            Class[] interfaces = new Class[] { innerInterface };
            InterfaceProxy proxy = new InterfaceProxy();
            return (T) Proxy.newProxyInstance(classLoader, interfaces, proxy);
        }


    }




}
