package sample;

import com.google.inject.servlet.ServletModule;

public class MyServletModule extends ServletModule {
	@Override
	protected void configureServlets() {
		serve("/*").with(MyVaadinServlet.class);
	}

}
