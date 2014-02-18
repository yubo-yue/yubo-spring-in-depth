package com.apress.prospring3.ch07.introduction;

import org.springframework.aop.support.DefaultIntroductionAdvisor;

public class IsModifiedAdvisor extends DefaultIntroductionAdvisor {

	public IsModifiedAdvisor() {
		super(new IsModifiedMixin());
	}

}
