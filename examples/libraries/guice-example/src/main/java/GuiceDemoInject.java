import com.google.inject.*;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

import java.sql.SQLException;

public class GuiceDemoInject {
    public static void main(String[] args) {
        // create injector
        Injector injector = Guice.createInjector(new ConfigurationModule(), new DaoModule(), new ServiceModule());
        // get the service with all dependencies injected
        MyService myService = injector.getInstance(MyService.class);
        // test the service
        myService.test();
    }

    // Injection Configuration via Modules

    private static class ConfigurationModule extends AbstractModule {
        @Override
        public void configure() {
            bindConstant().annotatedWith(Names.named("MyServiceEndpointURL")).to("http://myservice.myapp.com:8000/");
            bindConstant().annotatedWith(Names.named("MyServiceTimeoutSeconds")).to(30);
        }
    }

    private static class DaoModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(MyDao.class).to(MyDaoImpl.class);
        }
    }

    private static class ServiceModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(MyService.class).to(MyServiceImpl.class);
        }
    }

    // DAO

    private interface MyDao {
        void test();
    }

    @Singleton
    private static class MyDaoImpl implements GuiceDemoInject.MyDao {
        private final String endpointURL;
        private final int timeoutSeconds;

        @Inject
        public MyDaoImpl(
                @Named("MyServiceEndpointURL") String endpointURL,
                @Named("MyServiceTimeoutSeconds") int timeoutSeconds
        ) {
            this.endpointURL = endpointURL;
            this.timeoutSeconds = timeoutSeconds;
        }

        @Override
        public void test() {
            System.out.println(String.format("Hello world! (URL = %s, timeout = %d s)", endpointURL, timeoutSeconds));
        }
    }

    // Service

    private interface MyService {
        void test();
    }

    @Singleton
    private static class MyServiceImpl implements GuiceDemoInject.MyService {
        private final MyDao dao;

        @Inject
        public MyServiceImpl(MyDao dao) {
            this.dao = dao;
        }

        @Override
        public void test() {
            dao.test();
        }
    }
}
