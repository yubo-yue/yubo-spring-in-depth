package com.apress.prospring3.ch06;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;

public class SimpleBeforeAdvice implements MethodBeforeAdvice {

	public static void main(String[] args) {
		MessageWriter writer = new MessageWriter();

		ProxyFactory pf = new ProxyFactory();
		pf.setTarget(writer);
		pf.addAdvice(new SimpleBeforeAdvice());

		MessageWriter proxy = (MessageWriter) pf.getProxy();
		proxy.writeMessage();
	}

	public void before(Method method, Object[] args, Object target) {
		System.out.println("Before calling target " + target + " method " + method.getName() + " args " + args);
	}
}
