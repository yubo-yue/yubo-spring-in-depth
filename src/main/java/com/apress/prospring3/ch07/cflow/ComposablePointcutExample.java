package com.apress.prospring3.ch07.cflow;

import java.lang.reflect.Method;

import org.springframework.aop.Advisor;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcher;

public class ComposablePointcutExample {

	public static class SampleBean {
		public String getName() {
			return "Clarence Ho";
		}

		public void setName(String name) {
		}

		public int getAge() {
			return 100;
		}
	}

	public static class SimpleBeforeAdvice implements MethodBeforeAdvice {

		public void before(Method method, Object[] args, Object target)
				throws Throwable {
			System.out.println("Before method : " + method);
		}

	}

	public static void main(String[] args) {
		SampleBean target = new SampleBean();
		ComposablePointcut pc = new ComposablePointcut(ClassFilter.TRUE,
				new GetterMethodMatcher());
		
		System.out.println("Test 1");
		SampleBean proxy = getProxy(pc, target);
		testInvoke(proxy);
		
		System.out.println("test 2");
		pc.union(new SetterMethodMatcher());
		proxy = getProxy(pc, target);
		testInvoke(proxy);
		
		System.out.println("test 3");
		pc.intersection(new GetAgeMethodMatcher());
		proxy = getProxy(pc, target);
		testInvoke(proxy);
	}

	private static SampleBean getProxy(ComposablePointcut pc, SampleBean target) {
		Advisor advisor = new DefaultPointcutAdvisor(pc,
				new SimpleBeforeAdvice());
		ProxyFactory pf = new ProxyFactory();
		pf.setTarget(target);
		pf.addAdvisor(advisor);
		return (SampleBean) pf.getProxy();
	}

	private static void testInvoke(SampleBean proxy) {
		proxy.getAge();
		proxy.getName();
		proxy.setName("hiiiii");
	}

	private static class GetterMethodMatcher extends StaticMethodMatcher {
		public boolean matches(Method method, Class<?> cls) {
			return (method.getName().startsWith("get"));
		}
	}

	private static class GetAgeMethodMatcher extends StaticMethodMatcher {
		public boolean matches(Method method, Class<?> cls) {
			return "getAge".equals(method.getName());
		}
	}

	private static class SetterMethodMatcher extends StaticMethodMatcher {
		public boolean matches(Method method, Class<?> cls) {
			return (method.getName().startsWith("set"));
		}
	}

}
