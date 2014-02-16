package com.apress.prospring3.ch06;

import static java.lang.System.out;

import java.lang.reflect.Method;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.aop.framework.ProxyFactory;

public class SimpleThrowAdvice implements ThrowsAdvice {

	public static void main(String[] args) {
		ErrorBean errorBean = new ErrorBean();
		ProxyFactory pf = new ProxyFactory();
		pf.setTarget(errorBean);
		pf.addAdvice(new SimpleThrowAdvice());
		ErrorBean proxy = (ErrorBean) pf.getProxy();
		try {
			proxy.errorProneMethod();
		} catch (Exception ignored) {
		}
		try {
			proxy.otherErrorProneMethod();
		} catch (Exception ignored) {
		}
	}

	public void afterThrowing(Exception ex) {
		out.println("***");
		out.println("Generic Exception captured");
		out.println("Caught : " + ex.getClass().getName());
		out.println("***\n");
	}

	public void afterThrowing(Method method, Object[] args, Object target,
			IllegalArgumentException ex) {
		out.println("***");
		out.println("IllegalArgumentException Capture");
		out.println("Caught: " + ex.getClass().getName());
		out.println("Method: " + method.getName());
		out.println("***\n");
	}

	public static class ErrorBean {
		public void errorProneMethod() throws Exception {
			throw new Exception("Foo");
		}

		public void otherErrorProneMethod() throws IllegalArgumentException {
			throw new IllegalArgumentException("Bar");
		}
	}
}
