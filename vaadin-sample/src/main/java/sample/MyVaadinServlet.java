package sample;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.DeploymentConfiguration;
import com.vaadin.server.ServiceException;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletService;
import com.vaadin.ui.UI;

@Singleton
// @WebServlet(value = "/*", asyncSupported = true)
// @VaadinServletConfiguration(productionMode = false, ui = MyVaadinUI.class, widgetset = "sample.AppWidgetSet")
@WebServlet(asyncSupported = true)
@VaadinServletConfiguration(productionMode = false, ui = UI.class, widgetset = "sample.AppWidgetSet")
public class MyVaadinServlet extends VaadinServlet {
	private final MyVaadinServletServiceFactory vaadinServletServiceFactory;

	@Inject
	public MyVaadinServlet(MyVaadinServletServiceFactory vaadinServletServiceFactory) {
		this.vaadinServletServiceFactory = vaadinServletServiceFactory;
	}

	@Override
	protected VaadinServletService createServletService(DeploymentConfiguration deploymentConfiguration)
			throws ServiceException {
		VaadinServletService service = vaadinServletServiceFactory.create(this, deploymentConfiguration);
		service.init();
		return service;
	}
}