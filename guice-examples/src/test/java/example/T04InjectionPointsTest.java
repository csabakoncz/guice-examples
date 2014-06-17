package example;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;

public class T04InjectionPointsTest {
	static class A {
		@Inject
		@Named("a")
		String injectedField;

		String constructorInjected;

		String setterInjected;

		@Inject
		public A(@Named("b") String constructorInjected) {
			this.constructorInjected = constructorInjected;
		}

		@Inject
		private void setC(@Named("c") String setterInjected) {
			this.setterInjected = setterInjected;
		}
	}
	
	private static class Module1 extends AbstractModule{
		@Override
		protected void configure() {
			bind(String.class).annotatedWith(Names.named("a")).toInstance("a");
			bind(String.class).annotatedWith(Names.named("b")).toInstance("b");
			bind(String.class).annotatedWith(Names.named("c")).toInstance("c");
		}
	}
	
	@Test
	public void test() throws Exception {
		Injector injector = Guice.createInjector(new Module1());
		A a = injector.getInstance(A.class);
		assertEquals("a",a.injectedField);
		assertEquals("b",a.constructorInjected);
		assertEquals("c",a.setterInjected);
	}

}
