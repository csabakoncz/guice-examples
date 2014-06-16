package example;

import static org.junit.Assert.*;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.matcher.Matchers;

public class T07AOPTest {
	private static class Module1 extends AbstractModule{

		@Override
		protected void configure() {
			//constructor binding
			try {
				bind(File.class).toConstructor(File.class.getConstructor(String.class));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			bind(String.class).toInstance("new.txt");
		}
	}
	
	private static class AOPModule extends AbstractModule{

		@Override
		protected void configure() {
			Method delete=null;
			try {
				delete = File.class.getMethod("delete");
			} catch (Exception e) {
				e.printStackTrace();
			}

			bindInterceptor(Matchers.only(File.class), Matchers.only(delete),new MethodInterceptor() {
				
				public Object invoke(MethodInvocation invocation) throws Throwable {
					System.err.println("delete prevented");
					throw new IllegalAccessException("not enough rights");
				}
			});
		}
		
	}
	

	@Test(expected=IllegalAccessException.class)
	public void test() throws Exception {
		File file = Guice.createInjector(new Module1(),new AOPModule()).getInstance(File.class);
		file.delete();
		
	}
}
