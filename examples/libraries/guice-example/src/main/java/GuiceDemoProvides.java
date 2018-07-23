import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import java.sql.SQLException;

public class GuiceDemoProvides {
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
            // NOP
        }

        @Provides
        @Named("MyServiceEndpointURL")
        String getMyServiceEndpointURL() {
            return "http://myservice.myapp.com:8000/";
        }

        @Provides
        @Named("MyServiceTimeoutSeconds")
        int getMyServiceTimeoutSeconds() {
            return 30;
        }
    }

    private static class DaoModule extends AbstractModule {
        @Override
        protected void configure() {
            // NOP
        }

        @Provides
        @Singleton
        MyDao getMyDao(
                @Named("MyServiceEndpointURL") String endpointURL,
                @Named("MyServiceTimeoutSeconds") int timeoutSeconds
        ) {
            return new MyDaoImpl(endpointURL, timeoutSeconds);
        }
    }

    private static class ServiceModule extends AbstractModule {
        @Override
        protected void configure() {
            // NOP
        }

        @Provides
        @Singleton
        MyService getMyService(MyDao myDao) {
            return new MyServiceImpl(myDao);
        }
    }

    // DAO

    private interface MyDao {
        void test();
    }

    private static class MyDaoImpl implements GuiceDemoProvides.MyDao {
        private final String endpointURL;
        private final int timeoutSeconds;

        public MyDaoImpl(String endpointURL, int timeoutSeconds
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

    private static class MyServiceImpl implements GuiceDemoProvides.MyService {
        private final MyDao dao;

        public MyServiceImpl(MyDao dao) {
            this.dao = dao;
        }

        @Override
        public void test() {
            dao.test();
        }
    }
}
