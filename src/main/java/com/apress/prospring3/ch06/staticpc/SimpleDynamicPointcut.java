package com.apress.prospring3.ch06.staticpc;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.DynamicMethodMatcherPointcut;

public class SimpleDynamicPointcut extends DynamicMethodMatcherPointcut {

	public boolean matches(Method method, Class<?> targetClass, Object[] args) {
		System.out.println("Dynamic check for " + method.getName());
		int x = ((Integer) args[0]).intValue();

		return x != 100;
	}

	public boolean matches(Method method, Class<?> cls) {
		System.out.println("Static check for " + method.getName());
		return ("foo".equals(method.getName()));
	}

	public ClassFilter getClassFilter() {
		return new ClassFilter() {
			public boolean matches(Class<?> cls) {
				return (cls == SampleBean.class);
			}
		};
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

	public static class SampleBean {
		public void foo(int x) {
			System.out.println("Invoked foo() with: " + x);
		}

		public void bar() {
			System.out.println("Invoked bar()");
		}
	}

	public static void main(String[] args) {
		SampleBean target = new SampleBean();
		// create advisor
		Advisor advisor = new DefaultPointcutAdvisor(
				new SimpleDynamicPointcut(), new SimpleAdvice());
		// create proxy
		ProxyFactory pf = new ProxyFactory();
		pf.setTarget(target);
		pf.addAdvisor(advisor);
		SampleBean proxy = (SampleBean) pf.getProxy();
		proxy.foo(1);
		proxy.foo(10);
		proxy.foo(100);
		proxy.bar();
		proxy.bar();
		proxy.bar();
	}
}
