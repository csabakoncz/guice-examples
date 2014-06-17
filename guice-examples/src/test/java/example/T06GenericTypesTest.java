package example;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;

public class T06GenericTypesTest {
	private class Module1 extends AbstractModule {

		@Override
		protected void configure() {
		}

		@Singleton
		@Provides
		private List<String> providesStringList() {
			return Lists.newArrayList("a", "b");
		}

		@Singleton
		@Provides
		private List<Integer> providesIntegerList() {
			return Lists.newArrayList(1, 2);
		}

	}

	@Inject
	private List<String> stringList;

	@Inject
	private Provider<List<Integer>> integerListProvider;

	@Test
	public void testInjection() throws Exception {

		Injector injector = Guice.createInjector(new Module1());

		injector.injectMembers(this);

		List<String> expected = Lists.newArrayList("a", "b");
		assertEquals(expected, stringList);

		List<Integer> integerList = integerListProvider.get();
		List<Integer> expectedIntList = Lists.newArrayList(1, 2);

		assertEquals(expectedIntList, integerList);
		assertSame(integerList, integerListProvider.get());
	}

	@Test
	public void testRetrievalByKey() throws Exception {

		Injector injector = Guice.createInjector(new Module1());

		Key<List<String>> listStringKey = Key.get(new TypeLiteral<List<String>>() {
		});
		List<String> expected = Lists.newArrayList("a", "b");
		assertEquals(expected, injector.getInstance(listStringKey));

		List<Integer> expectedIntList = Lists.newArrayList(1, 2);
		Key<List<Integer>> intListKey = Key.get(new TypeLiteral<List<Integer>>() {
		});
		assertEquals(expectedIntList, injector.getInstance(intListKey));
	}

}
