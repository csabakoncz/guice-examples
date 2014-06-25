package sample;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.matcher.Matcher;
import com.google.inject.matcher.Matchers;
import com.vaadin.server.VaadinServletService;

public class MyOtherModule extends AbstractModule {

	@Override
	protected void configure() {
		Module servletServiceFactoryModule = new FactoryModuleBuilder().implement(MyVaadinServletService.class,
				MyVaadinServletService.class).build(MyVaadinServletServiceFactory.class);

		install(servletServiceFactoryModule);

		// curious to see whether interceptors work with assisted inject
		setupMethodInterception();
	}

	private void setupMethodInterception() {

		Matcher<Class> classMatcher = Matchers.subclassesOf(VaadinServletService.class);

		Matcher<Method> methodsDeclaredInVaadinServletService = interceptedMethods();

		MethodInterceptor interceptor = createInterceptor();

		bindInterceptor(classMatcher, methodsDeclaredInVaadinServletService, interceptor);
	}

	private MethodInterceptor createInterceptor() {
		return new MethodInterceptor() {

			@Override
			public Object invoke(MethodInvocation invocation) throws Throwable {
				logInvocation(invocation);
				return invocation.proceed();
			}

		};
	}

	private void logInvocation(MethodInvocation invocation) {
		String methodName = invocation.getMethod().getName();
		String target = invocation.getThis().toString();
		System.out.println("intercepted invocation of " + methodName + " on " + target);
	}

	private Matcher<Method> interceptedMethods() {
		Matcher<Method> methodsDeclaredInVaadinServletService = new AbstractMatcher<Method>() {

			@Override
			public boolean matches(Method t) {
				Class<?> declaringClass = t.getDeclaringClass();
				boolean isDeclaredInVaadinServletService = VaadinServletService.class.isAssignableFrom(declaringClass);
				return isDeclaredInVaadinServletService;
			}

		};
		return methodsDeclaredInVaadinServletService;
	}
}