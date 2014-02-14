package com.apress.prospring3.ch06;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class MessageDecorator implements MethodInterceptor{

	public Object invoke(MethodInvocation invocation) throws Throwable {
		System.out.println("Hello ");
		Object retVal = invocation.proceed();
		System.out.println("! ");
		return retVal;
	}

}
