package com.apress.prospring3.ch07;

import java.lang.reflect.Method;

import org.springframework.aop.Advisor;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.ControlFlowPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;

public class ControlFlowExample {

	public static class SimpleBeforeAdvice implements MethodBeforeAdvice {

		public void before(Method method, Object[] args, Object target)
				throws Throwable {
			System.out.println("Before method : " + method);
		}
		
	}
	
	public static class TestBean {
		public void foo() {
			System.out.println("foo()");
		}
	}
	
	public static void main(String[] args) {
		ControlFlowExample runner = new ControlFlowExample();
		runner.run();
	}
	
	public void run()
	{
		TestBean target = new TestBean();
		Pointcut pc = new ControlFlowPointcut(ControlFlowExample.class, "test");
		
		Advisor advisor = new DefaultPointcutAdvisor (pc, new SimpleBeforeAdvice());
		ProxyFactory pf = new ProxyFactory();
		pf.setTarget(target);
		pf.addAdvisor(advisor);
		
		TestBean proxy = (TestBean) pf.getProxy();
		
		proxy.foo();
		test(proxy);
	}
	
	private void test(TestBean bean)
	{
		bean.foo();
	}

}
