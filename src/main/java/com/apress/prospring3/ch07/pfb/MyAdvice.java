package com.apress.prospring3.ch07.pfb;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

public class MyAdvice implements MethodBeforeAdvice{

	public void before(Method method, Object[] args, Object target)
			throws Throwable {
		System.out.println("executing " + method.getName());
	}

}
