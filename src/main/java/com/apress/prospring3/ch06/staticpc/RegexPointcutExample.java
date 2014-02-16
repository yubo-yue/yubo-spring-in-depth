package com.apress.prospring3.ch06.staticpc;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;

public class RegexPointcutExample {

	public static class RegexBean {
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
		RegexBean target = new RegexBean();

		JdkRegexpMethodPointcut pc = new JdkRegexpMethodPointcut();
		pc.setPattern(".*foo.*");

		Advisor advisor = new DefaultPointcutAdvisor(pc, new SimpleAdvice());

		ProxyFactory pf = new ProxyFactory();
		pf.setTarget(target);
		pf.addAdvisor(advisor);

		RegexBean proxy = (RegexBean) pf.getProxy();

		proxy.bar();
		proxy.foo1();
		proxy.foo2();
	}

}
