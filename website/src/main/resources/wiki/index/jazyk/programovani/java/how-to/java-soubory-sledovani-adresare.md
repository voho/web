## Sledování změn adresáře

Sledování změn adresáře umožňuje reagovat na změny jeho obsahu, například tehdy, pokud je nějaký soubor přidán, odebrán či modifikován. Tento přístup najde uplatnění například v aplikacích, které čekají na nějaká souborová data a ihned po jejich dostupnosti je musí načíst a zpracovat. Není vhodné však spoléhat na efektivnost přístupu, protože některé souborové systémy nemají nativní podporu pro sledování změn a proto přichází ke slovu její neefektivní emulace pomocí pravidelného dotazování (polling). 

Některé události mohou občas přicházet vícenásobně a v krajním případě může dokonce vzniknout speciální událost typu *OVERFLOW*, která značí nedefinovanou změnu nebo přeplnění fronty událostí. Proto je nanejvýš vhodné chování důkladně otestovat na cílovém souborovém a operačním systému a respektovat omezení, která daný souborový systém má.

### Listener

Zde uvedený listener specifikuje rozhraní pro třídy, které chtějí reagovat na detekovaná události. Pro každý typ události je to jedna metoda, která se synchronně zavolá hned jak je událost sledovací službou detekována. Jako parametr metody obdrží konkrétní soubor, kterého se událost týká.

```java
public interface PathEventListener {
    void onFileCreated(Path file);
    void onFileModified(Path file);
    void onFileDeleted(Path file);
}
```

### Sledovací služba

Události jsou generovány službou *WatchService* pro konkrétní vazbu mezi touto službou a libovolnou instancí typu *Watchable* (například *Path*).

Každá jednotlivá událost je reprezentována rozhraním *WatchEvent*, které je genericky typované podle kontextu, který v sobě nese. Pro události na souborovém systému je to typicky *Path*, takže většinou se zpracovávají události typu *WatchEvent<Path>*.

```java
public class PathObserver {
    private final Path pathToObserve;
    private WatchService watchService;
    private final Set<PathEventListener> listeners;

    public PathObserver(Path pathToObserve) {
        this.pathToObserve = pathToObserve;
        this.listeners = new LinkedHashSet<>();
    }

    public void start() throws IOException {
        this.watchService = this.pathToObserve.getFileSystem().newWatchService();

        Kind<?>[] filter = {
            StandardWatchEventKinds.ENTRY_CREATE,
            StandardWatchEventKinds.ENTRY_DELETE,
            StandardWatchEventKinds.ENTRY_MODIFY
        };

        this.pathToObserve.register(this.watchService, filter);

        loop();
    }

    public void stop() throws IOException {
        this.watchService.close();
    }

    private void loop() {
        boolean run = true;

        while (run) {
            final WatchKey key;

            try {
                // wait for events

                key = watchService.take();

                // events are available - process them all

                for (final WatchEvent<?> event : key.pollEvents()) {
                    if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) {
                        assert event.kind().type().equals(Path.class);
                        fireFileCreated((Path) event.context());
                    } else if (StandardWatchEventKinds.ENTRY_MODIFY.equals(event.kind())) {
                        assert event.kind().type().equals(Path.class);
                        fireFileModified((Path) event.context());
                    } else if (StandardWatchEventKinds.ENTRY_DELETE.equals(event.kind())) {
                        assert event.kind().type().equals(Path.class);
                        fireFileDeleted((Path) event.context());
                    } else {
                        // possibly an OVERFLOW event
                        // (you should check the whole directory because something strange happened)
                    }
                }

                // reset the watched object and repeat

                run = key.reset();
            } catch (final ClosedWatchServiceException e) {
                // service was closed - graceful end
                break;
            } catch (final InterruptedException e) {
                // service was interrupted - violent end
                break;
            }
        }
    }

    public void addListener(PathEventListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(PathEventListener listener) {
        this.listeners.remove(listener);
    }

    private void fireFileCreated(Path path) {
        for (PathEventListener listener : listeners) {
            listener.onFileCreated(path);
        }
    }

    private void fireFileModified(Path path) {
        for (PathEventListener listener : listeners) {
            listener.onFileModified(path);
        }
    }

    private void fireFileDeleted(Path path) {
        for (PathEventListener listener : listeners) {
            listener.onFileDeleted(path);
        }
    }
}
```

### Použití

Nejjednodušší použití je uvedeno níže. Pozor zejména na skutečnost, že se spuštěním služby zablokuje vlákno, dokud nedojde ve sledované složce k nějaké události. Uvedená aplikace také nemá žádnou možnost ukončení. Nic však nebrání provést úpravu v implementaci a pro čekání používat samostatnou vrstvu s dedikovaným vláknem a nějaké řídící flagy.

```java
final Path pathToWatch = Paths.get("c:/temp");
final PathObserver observer = new PathObserver(pathToWatch);

observer.addListener(new PathEventListener() {
	@Override
	public void onFileCreated(Path file) {
		System.out.println("Created: " + file);
	}

	@Override
	public void onFileModified(Path file) {
		System.out.println("Updated: " + file);
	}

	@Override
	public void onFileDeleted(Path file) {
		System.out.println("Deleted: " + file);
	}
});

observer.start();
```
