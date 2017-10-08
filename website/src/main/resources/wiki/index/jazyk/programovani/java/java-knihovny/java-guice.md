## Google Guice

Guice je framework pro [vkládání závislostí](wiki/vkladani-zavislosti) (dependency injection) od firmy Google.

Základní vlastnosti:

- jednoduchost (omezený počet možností, jak dosáhnout jednoho cíle)
- možnost modulární konfigurace
- veškerá konfigurace v kódu (žádné externí [XML](wiki/xml), atd.)

### Vytvoření a konfigurace injektoru

Injektor je objekt, který se bude starat o vkládání závislostí.
K jeho vytvoření je potřeba vytvořit jeden nebo více tzv. **modulů**, což jsou speciální konfigurační třídy, ve kterých lze definovat jednotlivé závislosti a způsob jejich vytváření.

Každý modul musí implementovat rozhraní *javadoc:com.google.inject.Module*.
Kvůli přehlednosti se ale zpravidla dědí od abstraktní třídy *javadoc:com.google.inject.AbstractModule*, která umožňuje srozumitelnější zápis.

```java
// create injector
Injector injector = Guice.createInjector(new ConfigurationModule(), new DaoModule(), new ServiceModule());

// get the service with all dependencies injected
MyService myService = injector.getInstance(MyService.class);
```

### Příklad

#### Konfigurace pomocí @Inject

Tato metoda ponechává vytváření objektů v režii Guice.
Objekty v tomto případě musí mít buď veřejný konstruktor bez parametrů, nebo konstruktor s anotací *javadoc:com.google.inject.Inject*.
Objekty, které mají být vytvořené jako [singletony](wiki/singleton), musí být označeny anotací *javadoc:com.google.inject.Singleton*.
Pro upřesnění lze samozřejmě připojit i další anotace, například *javadoc:com.google.inject.name.Named*.

```java
import com.google.inject.*;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

import java.sql.SQLException;

public class GuiceDemoInject {
    public static void main(String[] args) throws SQLException {
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
```

#### Konfigurace pomocí @Provides

Tato metoda předává kontrolu nad vytvářením objektů uživateli.
U tohoto způsobu konfigurace se připravují [tovární metody](wiki/factory-method), které  každý objekt nebo závislost.
Aby Guice dokázal metodu najít, musí se k ní přidat anotace *javadoc:com.google.inject.Provides*.
Pro upřesnění lze samozřejmě připojit i další anotace, například *javadoc:com.google.inject.Singleton* nebo *javadoc:com.google.inject.name.Named*.

```java
import com.google.inject.*;
import com.google.inject.name.Named;

import java.sql.SQLException;

public class GuiceDemoProvides {
    public static void main(String[] args) throws SQLException {
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
```