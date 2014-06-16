package example;

import javax.inject.Inject;

public class Client {
	private Service service;

	@Inject
	public Client(Service service) {
		this.service = service;
	}

	public Service getService() {
		return service;
	}
	
	public void doWork() {
		service.init();
	}

}
