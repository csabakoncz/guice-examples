package example;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.CreationException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;

public class T09ModuleOverrideTest {
	private static class Module1 extends AbstractModule {

		@Override
		protected void configure() {
			bind(String.class).toInstance("a");
		}
	}

	private static class Module2 extends AbstractModule {

		@Override
		protected void configure() {
			bind(String.class).toInstance("b");
		}
	}

	@Test(expected = CreationException.class)
	public void testDifferentBindings() throws Exception {
		Module module1 = new Module1();
		Module module2 = new Module2();
		Guice.createInjector(module1, module2);
	}

	@Test
	public void testSameBindings() throws Exception {
		Module module1 = new Module1();
		Module module12 = new Module1();

		Guice.createInjector(module1, module12);
	}

	@Test
	public void testOverride() throws Exception {
		Module module1 = new Module1();
		Module module2 = new Module2();

		Module module3 = Modules.override(module1).with(module2);

		Injector injector = Guice.createInjector(module3);

		assertEquals("b", injector.getInstance(String.class));
	}
}
