package com.apress.prospring3.ch07.aspectjannotation;

import org.springframework.beans.factory.annotation.Autowired;

public class MyBean {
	private MyDependency myDependency;

	public void execute() {
		myDependency.foo(100);
		myDependency.foo(101);
		myDependency.bar();
	}

	@Autowired
	public void setDep(MyDependency myDependency) {
		this.myDependency = myDependency;
	}
}
