package sample;

import javax.inject.Inject;
import javax.inject.Provider;

import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UICreateEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;

public class MyUiProvider extends UIProvider {
	private final Provider<MyVaadinUI> diUIProvider;

	@Inject
	public MyUiProvider(Provider<MyVaadinUI> diUIProvider) {
		this.diUIProvider = diUIProvider;
	}

	@Override
	public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
		return MyVaadinUI.class;
	}

	@Override
	public UI createInstance(UICreateEvent event) {
		return diUIProvider.get();
	}

}
