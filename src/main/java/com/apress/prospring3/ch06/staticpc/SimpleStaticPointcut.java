package com.apress.prospring3.ch06.staticpc;

import java.lang.reflect.Method;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

public class SimpleStaticPointcut extends StaticMethodMatcherPointcut {

	public static class BeanOne {
		public void foo() {
			System.out.println("foo");
		}

		public void bar() {
			System.out.println("bar");
		}
	}
	
	public static class BeanTwo {
		public void foo() {
			System.out.println("foo");
		}

		public void bar() {
			System.out.println("bar");
		}
	}
	
	public static class SimpleAdvice implements MethodInterceptor {

		public Object invoke(MethodInvocation invocation) throws Throwable {
			System.out.println(" >>Invoking " + invocation.getMethod().getName());
			Object retObj = invocation.proceed();
			System.out.println(" >> Done");
			return null;
		}
		
	}

	public boolean matches(Method method, Class<?> targetClass) {
		return ("foo".equals(method.getName()));
	}

	public ClassFilter getClassFilter() {
		return new ClassFilter() {
			public boolean matches(Class<?> clazz) {
				return (clazz == BeanOne.class);
			}
		};
	}
	
	public static void main(String[] args)
	{
		BeanOne one = new BeanOne(); 
		BeanTwo two = new BeanTwo(); 
		BeanOne proxyOne; 
		BeanTwo proxyTwo; 
		// create pointcut, advice and advisor 
		Pointcut pc = new SimpleStaticPointcut(); 
		Advice advice = new SimpleAdvice(); 
		Advisor advisor = new DefaultPointcutAdvisor(pc, advice); 
		// create BeanOne proxy 
		ProxyFactory pf = new ProxyFactory(); 
		pf.addAdvisor(advisor); 
		pf.setTarget(one); 
		proxyOne = (BeanOne)pf.getProxy(); 
		// create BeanTwo proxy 
		pf = new ProxyFactory(); 
		pf.addAdvisor(advisor); 
		pf.setTarget(two); 
		proxyTwo = (BeanTwo)pf.getProxy(); 
		proxyOne.foo(); 
		proxyTwo.foo(); 
		proxyOne.bar(); 
		proxyTwo.bar(); 
	}
}
