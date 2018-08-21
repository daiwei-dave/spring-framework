
package org.aisframework.web.servlet;


import org.aisframework.web.classcollection.ClassCollection;

import org.aisframework.web.param.HandlerMapping;
import org.aisframework.web.structure.MethodPro;
import org.aisframework.web.utils.Config;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.Map;



public class AisDispatcherServlet extends HttpServlet {

	private final String basePackage="org.aisframework.web.test";

	private Map<String,MethodPro> methodProMap =null;
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		String annoClassConfig = Config.getAnnoClassConfig("base-package");
		//防止未从配置问题找到basePackage
		if (StringUtils.isBlank(annoClassConfig)){
			annoClassConfig=basePackage;
		}

		ClassCollection.scanClassSetByPackage(annoClassConfig);//初始化配置下的 @Controller类
		methodProMap = ClassCollection.getMethodMap();//拿到封装的每个类方法的属性
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//获取路径
		String pathInfo=req.getServletPath();
		System.out.println(pathInfo.replaceAll("/", ""));
		System.out.println(pathInfo.replaceAll("/", "").split("\\.")[0]);
		String key = pathInfo.replaceAll("/", "").split("\\.")[0];
		if (methodProMap.containsKey(key)) {
			HandlerMapping.HandlerMapping(req,resp,methodProMap,key);//转发到映射器进行映射处理
			return ;
		}

		//用户请求url没有映射404处理
			resp.sendError(404);
	}
}

