package sample;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.DeploymentConfiguration;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Singleton
// @WebServlet(value = "/*", asyncSupported = true)
// @VaadinServletConfiguration(productionMode = false, ui = MyVaadinUI.class, widgetset = "sample.AppWidgetSet")
@WebServlet(asyncSupported = true)
@VaadinServletConfiguration(productionMode = false, ui = UI.class, widgetset = "sample.AppWidgetSet")
public class MyVaadinServlet extends VaadinServlet {

	private final MyUiProvider myUiProvider;

	@Inject
	public MyVaadinServlet(MyUiProvider myUiProvider) {
		this.myUiProvider = myUiProvider;
	}

	@Override
	protected VaadinServletService createServletService(DeploymentConfiguration deploymentConfiguration)
			throws ServiceException {
		VaadinServletService servletService = super.createServletService(deploymentConfiguration);
		attachSessionInitListener(servletService);
		return servletService;
	}

	private void attachSessionInitListener(VaadinServletService servletService) {
		servletService.addSessionInitListener(new SessionInitListener() {

			@Override
			public void sessionInit(SessionInitEvent event) throws ServiceException {
				VaadinSession session = event.getSession();
				session.addUIProvider(myUiProvider);
			}
		});

	}
}