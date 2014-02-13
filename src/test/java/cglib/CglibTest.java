package cglib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Dispatcher;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;
import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.LazyLoader;

import org.junit.Test;

public class CglibTest {

	public static class SampleClass {
		public String test(String input) {
			return "hello world";
		}
	}

	public static class LazyObject {
		private String objectName;

		public String getObjectName() {
			return objectName;
		}

		public void setObjectName(String objectName) {
			this.objectName = objectName;
		}

	}

	public static class BossObject {
		private LazyObject lazyObject;
		private String greetings;
		
		public BossObject() {
			greetings = "i am good";
			lazyObject = (LazyObject) Enhancer.create(LazyObject.class,
					new LazyLoader() {
						//only called at the first load time and only call once.
						public Object loadObject() throws Exception {
							System.out.println("I am late");
							LazyObject o = new LazyObject();
							o.setObjectName("lazy susan");
							return o;
						}
					});
		}
		
		public LazyObject getLazyObject()
		{
			return lazyObject;
		}
		
		public String getGreetings()
		{
			return greetings;
		}
	}
	
	public static class DispatchedBean {
		private String name;
		private int value;
		
		public void setName(String name)
		{
			this.name = name;
		}
		public void setValue(int value)
		{
			this.value = value;
		}
	}
	
	public static class DispatcherBean {
		private String name;
		private int age;
		private DispatchedBean dispatchedOne;
		
		public DispatcherBean()
		{
			name = "Dispatcher";
			age = 1;
			dispatchedOne = (DispatchedBean)Enhancer.create(DispatchedBean.class, new Dispatcher() {

				public Object loadObject() throws Exception {
					System.out.println("Dispatched every time");
					DispatchedBean obj = new DispatchedBean();
					return obj;
				}});
		}
		
		public DispatchedBean getDispatchedOne()
		{
			return dispatchedOne;
		}
	}

	@Test
	public void testFixedValue() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(SampleClass.class);
		enhancer.setCallback(new FixedValue() {

			public Object loadObject() throws Exception {
				return "Hello Cglib!";
			}

		});

		SampleClass proxy = (SampleClass) enhancer.create();
		assertEquals("Hello Cglib!", proxy.test(null));
	}

	@Test
	public void testInvocationHandler() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(SampleClass.class);
		enhancer.setCallback(new InvocationHandler() {

			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				if (method.getDeclaringClass() != Object.class
						&& method.getReturnType() == String.class) {
					return "Hello Cglib!";
				} else {
					throw new RuntimeException();
				}
			}
		});
		SampleClass proxy = (SampleClass) enhancer.create();
		assertEquals("Hello Cglib!", proxy.test(null));
		try {
			proxy.toString();
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testLazyLoader()
	{
		BossObject bo = new BossObject();
		assertEquals("i am good", bo.getGreetings());
		System.out.println("Lazy Object is coming ...");
		bo.getLazyObject().getObjectName();
	}
	
	@Test
	public void testDispatcher()
	{
		DispatcherBean dispatcher = new DispatcherBean();
		dispatcher.getDispatchedOne().setName("aaa");
		dispatcher.getDispatchedOne().setValue(2);
		
		assertTrue(true);
	}

}
