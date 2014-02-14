package com.apress.prospring3.ch06;

import org.springframework.aop.framework.ProxyFactory;

public class HelloWorldAOPExample {

	public static void main(String[] args) {
		MessageWriter target = new MessageWriter();
		
		ProxyFactory pf = new ProxyFactory();
		
		pf.addAdvice(new MessageDecorator());
		pf.setTarget(target);
		
		MessageWriter proxy = (MessageWriter) pf.getProxy();
		target.writeMessage();
		proxy.writeMessage();
	}

}
