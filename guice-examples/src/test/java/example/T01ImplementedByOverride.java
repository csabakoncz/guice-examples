package example;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class T01ImplementedByOverride {
	private static class ServiceOverride implements Service {

	}

	@Test
	public void test() throws Exception {
		Injector injector = Guice.createInjector(new AbstractModule() {

			@Override
			protected void configure() {
				bind(Service.class).to(ServiceOverride.class);
			}
		});
		
		Client client = injector.getInstance(Client.class);
		assertTrue(client.getService() instanceof ServiceOverride);
	}
	
}
