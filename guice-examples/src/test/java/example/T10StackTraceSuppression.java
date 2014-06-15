package example;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import com.google.common.base.Stopwatch;
import com.google.inject.AbstractModule;
import com.google.inject.CreationException;
import com.google.inject.Guice;
import com.google.inject.name.Names;

/**
 * This test does not demonstrate anything
 */
public class T10StackTraceSuppression {
	private static final String GUICE_INCLUDE_STACK_TRACES = "guice_include_stack_traces";

	private static class Module1 extends AbstractModule {

		@Override
		protected void configure() {
			for (int i = 0; i < 200; i++) {
				String name = Integer.toString(i);
				bind(Integer.class).annotatedWith(Names.named(name))
						.toInstance(Integer.valueOf(i));
				
			}
		}
	}
	
	private static class Module2 extends AbstractModule{
		@Override
		protected void configure() {
			bind(Integer.class).annotatedWith(Names.named("0")).toInstance(42);
		}
	}

	@Rule
	public TestName testName=new TestName();

	@Before
	public void setUp() {
		doRunTest("setup");
	}
	
	@Test
	public void testRegular() throws Exception {
		doRunTest(testName.getMethodName());
	}
	@Test
	public void testWithDisabledStackTraces() throws Exception {
		try {
			System.setProperty(GUICE_INCLUDE_STACK_TRACES, "OFF");
			doRunTest(testName.getMethodName());
		}
		finally {
			System.getProperties().remove(GUICE_INCLUDE_STACK_TRACES);
		}
	}
	@Test
	public void testWithCompleteStackTraces() throws Exception {
		try {
			System.setProperty(GUICE_INCLUDE_STACK_TRACES, "COMPLETE");
			doRunTest(testName.getMethodName());
		}
		finally {
			System.getProperties().remove(GUICE_INCLUDE_STACK_TRACES);
		}
	}

private void doRunTest(String name) {
			Stopwatch stopwatch=new Stopwatch();
			Module1 module = new Module1();
			try {
				stopwatch.start();
//				Guice.createInjector(module);
				Guice.createInjector(module,new Module2());
			}
			catch(CreationException e) {
				System.out.println(e);
			}
			finally {
				stopwatch.stop();
				System.out.println(name+" took "+stopwatch);
			}

	 }
}
