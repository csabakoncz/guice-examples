package example;

import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.CreationException;
import com.google.inject.Guice;

public class T11BindToNullTest {
	static class Module1 extends AbstractModule {

		@Override
		protected void configure() {
			bind(String.class).toInstance(null);
		}
	}

	@Test(expected = CreationException.class)
	public void test() throws Exception {
		Guice.createInjector(new Module1());
	}
}
