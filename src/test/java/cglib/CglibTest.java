package cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;
import net.sf.cglib.proxy.InvocationHandler;

import static org.junit.Assert.*;
import org.junit.Test;

public class CglibTest {

	public static class SampleClass {
		public String test(String input) {
			return "hello world";
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

}
