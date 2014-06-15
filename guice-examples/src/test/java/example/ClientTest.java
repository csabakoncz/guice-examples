package example;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class ClientTest {

	@Test
	public void test() {
		Injector injector = Guice.createInjector();
		Client client = injector.getInstance(Client.class);
		
		assertTrue(client.getService() instanceof ServiceImpl);
	}

}
