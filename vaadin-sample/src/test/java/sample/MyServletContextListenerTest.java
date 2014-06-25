package sample;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Injector;

public class MyServletContextListenerTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		Injector injector = new MyServletContextListener().getInjector();
		assertNotNull(injector.getInstance(MyVaadinUI.class));
	}

}
