package example;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

public class T02TestWithMocks {
	@Test
	public void test() throws Exception {
		Service service = Mockito.mock(Service.class);
		Client client = new Client(service);
		
		client.doWork();
		
		Mockito.verify(service).init();
	}
}
