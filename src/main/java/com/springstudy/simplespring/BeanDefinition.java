package com.springstudy.simplespring;

/**
 * bean的注册
 */
public class BeanDefinition {
	private Class beanClass;
	private String beanClassName;
	//属性列表
	private PropertyValues propertyValues; 
	
	public Class getBeanClass() {
		return beanClass;
	}

	public String getBeanClassName() {
		return beanClassName;
	}

	/**
	 * 设置类类型
	 * @param beanClassName
	 */
	public void setBeanClassName(String beanClassName) {
		this.beanClassName = beanClassName;
		// 根据类名设置类
		try {
			this.beanClass = Class.forName(beanClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public PropertyValues getPropertyValues() {
		return propertyValues;
	}
	
	public void setPropertyValues(PropertyValues pvs) {
		this.propertyValues = pvs;
	}
}
