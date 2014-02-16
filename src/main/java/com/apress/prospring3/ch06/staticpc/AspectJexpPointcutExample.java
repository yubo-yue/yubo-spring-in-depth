package com.apress.prospring3.ch06.staticpc;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

public class AspectJexpPointcutExample {

	public static class AspectjexpBean {
		public void foo1() {
			System.out.println("foo1");
		}

		public void foo2() {
			System.out.println("foo2");
		}

		public void bar() {
			System.out.println("bar");
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
		AspectjexpBean target = new AspectjexpBean();
		AspectJExpressionPointcut pc = new AspectJExpressionPointcut();
		pc.setExpression("execution(* foo*(..))");
		Advisor advisor = new DefaultPointcutAdvisor(pc, new SimpleAdvice());

		ProxyFactory pf = new ProxyFactory();
		pf.setTarget(target);
		pf.addAdvisor(advisor);
		AspectjexpBean proxy = (AspectjexpBean) pf.getProxy();
		proxy.foo1();
		proxy.foo2();
		proxy.bar();
	}

}
