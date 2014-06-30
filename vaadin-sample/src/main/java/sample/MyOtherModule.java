package sample;

import javax.inject.Named;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.SessionScoped;

public class MyOtherModule extends AbstractModule {

	@Override
	protected void configure() {
	}

	@Provides
	@SessionScoped
	@Named("session")
	private double provideSessionRandom() {
		return Math.random();
	}

	@Provides
	@RequestScoped
	@Named("request")
	private double provideRequestRandom() {
		return Math.random();
	}

}