package com.apress.prospring3.ch07.aspectjannotation;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyAdvice {
	
	@Pointcut("execution(* com.apress.prospring3.ch07..foo*(int)) && args(intValue)")
	public void fooExecution(int intValue) {
	}
//
//	@Pointcut("bean(myDependency)")
//	public void inMyDependency() {
//	}
//	
	
}
