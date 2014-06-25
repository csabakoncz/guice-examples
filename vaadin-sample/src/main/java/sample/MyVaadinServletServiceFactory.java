package sample;

import com.vaadin.server.DeploymentConfiguration;
import com.vaadin.server.VaadinServlet;

public interface MyVaadinServletServiceFactory {
	MyVaadinServletService create(VaadinServlet vaadinServlet, DeploymentConfiguration deploymentConfiguration);
}
