package example;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.CreationException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.name.Names;

public class T10ChildInjectorTest {
	static class Module1 extends AbstractModule {

		@Override
		protected void configure() {
			bind(String.class).toInstance("a");
		}
	}

	static class Module2 extends AbstractModule {

		@Override
		protected void configure() {
			bind(String.class).toInstance("b");
		}
	}

	static class Module3 extends AbstractModule {

		@Override
		protected void configure() {
			bind(String.class).annotatedWith(Names.named("b")).toInstance("b");
		}
	}

	@Test(expected = CreationException.class)
	public void testDifferentBindings() throws Exception {
		Module module1 = new Module1();
		Module module2 = new Module2();
		Injector parent = Guice.createInjector(module1);
		parent.createChildInjector(module2);
	}

	@Test(expected = CreationException.class)
	public void testSameBindings() throws Exception {
		Module module1 = new Module1();
		Module module12 = new Module1();

		Injector parent = Guice.createInjector(module1);
		parent.createChildInjector(module12);
	}

	@Test
	public void testChild() throws Exception {
		Module module1 = new Module1();
		Module module3 = new Module3();

		Injector parent = Guice.createInjector(module1);
		Injector child = parent.createChildInjector(module3);

		assertEquals("a", child.getInstance(String.class));

		Key<String> keyB = Key.get(String.class, Names.named("b"));
		assertEquals("b", child.getInstance(keyB));
	}
}
