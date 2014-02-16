package com.apress.prospring3.ch06;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.util.StopWatch;

public class ProfilingInterceptor implements MethodInterceptor {

	public Object invoke(MethodInvocation invocation) throws Throwable {
		StopWatch watcher = new StopWatch();
		watcher.start();
		Object retObj = invocation.proceed();
		watcher.stop();
		System.out.println(watcher.prettyPrint());
		return retObj;
	}

	public static void main(String[] args) {
		WorkBean target = new WorkBean();
		ProxyFactory pf = new ProxyFactory();
		
		pf.setTarget(target);
		pf.addAdvice(new ProfilingInterceptor());
		
		WorkBean proxy = (WorkBean) pf.getProxy();
		proxy.doSomeWork(10);
	}

	static class WorkBean {
		public void doSomeWork(int noOfTimes) {
			for (int x = 0; x < noOfTimes; x++) {
				work();
			}
		}

		private void work() {
			System.out.print(".");
		}
	}

}
