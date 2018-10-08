package com.demo.index;

import javax.servlet.http.HttpSession;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class UserInterceptor implements Interceptor{

	public void intercept(Invocation inv) {
		// TODO Auto-generated method stub
		Controller controller = inv.getController();
		 
		System.out.println("Before method invoking");
		System.out.println("URL:"+controller.getRequest().getLocalAddr());
		
		HttpSession session = inv.getController().getSession();  
		 if(session == null){  
			 		inv.getController().redirect("pages-login.html");  
	        }  
	        else{  
	            String nickname = (String) session.getAttribute("loginUser");  
	            if(nickname != null) {  
	                //System.out.println("hello");  
	        		inv.invoke();//对目标方法的调用,在这一行代码的前后插入切面代码可以很方便地实现AOP
	        		controller.setAttr("ContextPath", controller.getRequest().getContextPath() );
	        		System.out.println("After method invoking");
	        		System.out.println("ContextPath:"+controller.getRequest().getRequestURI());
	            }  
	            else {  
	            	inv.getController().redirect("pages-login.html");  
	            }  
	        }  
	}

}
