package com.apress.prospring3.ch06.annotationpc;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

public class AnnotationPointcutExample {

	public static class AnnotationBean {
		
		@AdviceRequired
		public void foo(int x) {
			System.out.println("Invoked foo() with: " + x);
		}

		public void bar() {
			System.out.println("Invoked bar()");
		}
	}
	
	public static class SimpleAdvice implements MethodInterceptor {

		public Object invoke(MethodInvocation invocation) throws Throwable {
			System.out.println(" >>Invoking "
					+ invocation.getMethod().getName());
			Object retObj = invocation.proceed();
			System.out.println(" >> Done");
			return null;
		}

	}

	public static void main(String[] args) {
		AnnotationBean target = new AnnotationBean();
		AnnotationMatchingPointcut pc = AnnotationMatchingPointcut
				.forMethodAnnotation(AdviceRequired.class);
		
		Advisor advisor = new DefaultPointcutAdvisor(pc, new SimpleAdvice());

		ProxyFactory pf = new ProxyFactory();
		pf.setTarget(target);
		pf.addAdvisor(advisor);
		
		AnnotationBean proxy = (AnnotationBean) pf.getProxy();
		
		proxy.foo(10);
		proxy.bar();
	}

}
