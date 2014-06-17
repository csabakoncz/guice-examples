package example;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertSame;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;

public class T08DefaultBindingsTest {
	private static class A {
		private final Injector injector;
		private final Stage stage;
		private final Logger logger;

		@Inject
		private A(Injector injector, Stage stage, Logger logger) {
			this.injector = injector;
			this.stage = stage;
			this.logger = logger;
		}

		@Override
		public String toString() {
			return "A [injector=" + injector + ", stage=" + stage + ", logger=" + logger + "]";
		}
	}

	@Test
	public void testJIT() throws Exception {

		Injector injector = Guice.createInjector();
		A a = injector.getInstance(A.class);
		System.out.println(a);

		assertSame(injector, a.injector);
		assertEquals(Stage.DEVELOPMENT, a.stage);
		assertNotNull(a.logger);
	}

}
