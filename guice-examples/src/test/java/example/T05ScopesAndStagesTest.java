package example;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.Scopes;
import com.google.inject.Stage;
import com.google.inject.name.Names;

public class T05ScopesAndStagesTest {
	private class Module1 extends AbstractModule {

		@Override
		protected void configure() {

			bind(List.class).to(ArrayList.class);
			bind(List.class).annotatedWith(Names.named("singleton")).to(ArrayList.class).in(Scopes.SINGLETON);

			Provider<List> listProvider = createEagerListProvider();
			bind(List.class).annotatedWith(Names.named("eager-singleton")).toProvider(listProvider).asEagerSingleton();

			Provider<List> nonEagerListProvider = createNonEagerListProvider();
			bind(List.class).annotatedWith(Names.named("non-eager-singleton")).toProvider(nonEagerListProvider)
					.in(Scopes.SINGLETON);
		}
	}

	private List eagerSingletonList = null;
	private List nonEagerSingletonList = null;

	@Inject
	private Provider<List> listProvider;

	@Inject
	@Named("singleton")
	private Provider<List> singletonListProvider;

	@Inject
	@Named("eager-singleton")
	private Provider<List> eagerSingletonListProvider;

	@Inject
	@Named("non-eager-singleton")
	private List nonEagerSingletonListProvider;

	@Test
	public void test() throws Exception {
		Injector injector = Guice.createInjector(new Module1());

		assertNotNull(eagerSingletonList);

		injector.injectMembers(this);

		assertSame(eagerSingletonList, eagerSingletonListProvider.get());
		assertSame(eagerSingletonList, eagerSingletonListProvider.get());

		List singletonList = singletonListProvider.get();
		assertSame(singletonList, singletonListProvider.get());
		assertSame(singletonList, singletonListProvider.get());

		List list = listProvider.get();
		assertFalse(list == listProvider.get());
		assertFalse(list == listProvider.get());

	}

	@Test
	public void testDevelopmentStage() throws Exception {
		Injector injector = Guice.createInjector(new Module1());

		assertNotNull(eagerSingletonList);
		assertNull(nonEagerSingletonList);

		injector.injectMembers(this);

		assertNotNull(nonEagerSingletonList);
	}

	@Test
	public void testProductionStage() throws Exception {
		Injector injector = Guice.createInjector(Stage.PRODUCTION, new Module1());

		assertNotNull(eagerSingletonList);
		assertNotNull(nonEagerSingletonList);

		injector.injectMembers(this);

	}

	public Provider<List> createEagerListProvider() {
		return new Provider<List>() {

			@Override
			public List get() {
				if (eagerSingletonList != null) {
					throw new RuntimeException("EagerList is already set!");
				}
				ArrayList<Object> list = Lists.newArrayList();
				eagerSingletonList = list;
				return list;
			}
		};
	}

	public Provider<List> createNonEagerListProvider() {
		return new Provider<List>() {

			@Override
			public List get() {
				if (nonEagerSingletonList != null) {
					throw new RuntimeException("NonEagerSingletonList is already set!");
				}
				ArrayList<Object> list = Lists.newArrayList();
				nonEagerSingletonList = list;
				return list;
			}
		};
	}

}
