package example;

import static org.junit.Assert.*;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.BindingAnnotation;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

public class T03ModulesTest {
	@Target({ElementType.PARAMETER,ElementType.FIELD,ElementType.METHOD})
	@Retention(RetentionPolicy.RUNTIME)
	@BindingAnnotation
	 static @interface MyRandom{
		
	}

	private static class Module1 extends AbstractModule{

		@Override
		protected void configure() {
			//linked binding:
			bind(List.class).to(ArrayList.class);
			
			//constructor binding
			try {
				bind(File.class).toConstructor(File.class.getConstructor(String.class));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			bind(String.class).toInstance("new.txt");
			
			bind(Double.class).annotatedWith(MyRandom.class).toProvider(RandomNumberProvider.class);
			
			bind(String.class).annotatedWith(Names.named("Today")).toProvider(new Provider<String>() {
				public String get() {
					   return Calendar.getInstance().getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG,Locale.ENGLISH);
				}
			});
			
		}
		
	}
	
	private static class RandomNumberProvider implements Provider<Double>{
		public Double get() {
			return Math.random();
		}
	}
	
	@Test
	public void testRetrievalFromInjector() throws Exception {
		
		Injector injector = Guice.createInjector(new Module1());
		
		assertTrue(injector.getInstance(List.class) instanceof ArrayList);
		
		assertEquals("new.txt",injector.getInstance(File.class).getName());
		
		Key<Double> randomKey = Key.get(Double.class,MyRandom.class);
		Double random1 = injector.getInstance(randomKey);
		System.out.println("random  ="+random1);
		
		Provider<Double> randomProvider = injector.getProvider(randomKey);
		for (int i=0; i<5; i++) {
			System.out.println("random "+i+"="+randomProvider.get());
		}
		
		String today = injector.getInstance(Key.get(String.class,Names.named("Today")));
		System.out.println("Today is "+today);
	}
	
	private static class Injectable{
		@Inject
		private List list;

		@Inject
		private File file;
		
		@Inject
		@MyRandom
		private Double random;
		
		@Inject
		@MyRandom
		private Provider<Double> randomProvider;
		
		@Inject
		@Named("Today")
		private String today;

		@Override
		public String toString() {
			return "Injectable [list=" + list + ", file=" + file + ", random="
					+ random + ", randomProvider=" + randomProvider
					+ ", today=" + today + "]";
		}
	}
	
	@Test
	public void testFieldInjection() throws Exception {
		Injectable injectable = new Injectable();
		Guice.createInjector(new Module1()).injectMembers(injectable);
		System.out.println(injectable);
	}

	@Test
	public void testJIT() throws Exception {
		Injectable injectable = Guice.createInjector(new Module1()).getInstance(Injectable.class);
		System.out.println(injectable);
	}

}
