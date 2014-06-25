package sample;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;
import com.vaadin.server.DeploymentConfiguration;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletService;
import com.vaadin.server.VaadinSession;

public class MyVaadinServletService extends VaadinServletService {

	private final MyUiProvider myUiProvider;

	@Inject
	public MyVaadinServletService(@Assisted VaadinServlet servlet,
			@Assisted DeploymentConfiguration deploymentConfiguration, MyUiProvider myUiProvider)
			throws ServiceException {
		super(servlet, deploymentConfiguration);
		this.myUiProvider = myUiProvider;
		registerSessionInitListener();
	}

	private void registerSessionInitListener() {
		this.addSessionInitListener(new SessionInitListener() {

			@Override
			public void sessionInit(SessionInitEvent event) throws ServiceException {
				VaadinSession session = event.getSession();
				session.addUIProvider(myUiProvider);
			}
		});
	}
}
