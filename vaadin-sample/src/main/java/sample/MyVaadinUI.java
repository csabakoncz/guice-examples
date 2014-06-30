package sample;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("mytheme")
@SuppressWarnings("serial")
public class MyVaadinUI extends UI {

	private final Provider<HttpServletRequest> requestProvider;
	private final Provider<HttpSession> sessionProvider;
	private final Provider<Double> requestRandomProvider;
	private final Provider<Double> sessionRandomProvider;

	@Inject
	public MyVaadinUI(Provider<HttpServletRequest> requestProvider, Provider<HttpSession> sessionProvider,
			@Named("request") Provider<Double> requestRandomProvider,
			@Named("session") Provider<Double> sessionRandomProvider) {
		this.requestProvider = requestProvider;
		this.sessionProvider = sessionProvider;
		this.requestRandomProvider = requestRandomProvider;
		this.sessionRandomProvider = sessionRandomProvider;
	}

	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);

		Button button = new Button("Click Me");
		button.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				layout.addComponent(new Label("Thank you for clicking"));

				String session = "HttpSession=" + sessionProvider.get();
				layout.addComponent(new Label(session));

				String request = "HttpServletRequest=" + requestProvider.get();
				layout.addComponent(new Label(request));

				String sessionRandom = "session random =" + sessionRandomProvider.get();
				layout.addComponent(new Label(sessionRandom));

				String requestRandom = "request random =" + requestRandomProvider.get();
				layout.addComponent(new Label(requestRandom));
			}
		});
		layout.addComponent(button);
	}

}
