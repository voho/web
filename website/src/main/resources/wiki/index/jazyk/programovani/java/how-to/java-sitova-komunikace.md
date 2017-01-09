## Síťová komunikace

### Komunikace přes TCP

Protokol TCP (Transmission Control Protocol) se používá pro vytvoření plně duplexního spojení mezi dvěma síťovými prvky, které zajišťuje doručení všech dat ve správném pořadí. 

Síťová komunikace založená na socketech se v Javě implementuje velmi jednoduše. Následující příklad demonstruje odeslání jednoho čísla "42" přes TCP a jeho vypsání na straně serveru.

#### Server

```java
public void runServer() throws IOException {
    ServerSocket serverSocket = null;
    Socket clientSocket = null;
    DataInputStream clientStream = null;

    try {
        // vytvořit serverový socket na portu 12345
        serverSocket = new ServerSocket(12345);

        // čekat, dokud se klient nepřipojí a pak získat jeho socket
        clientSocket = serverSocket.accept();

        // získat vstupní proud socketu
        // (výstup pro klienta = vstup pro server)
        clientStream = new DataInputStream(clientSocket.getInputStream());

        // načíst a zobrazit data od klienta
        System.out.println("Received number: " + clientStream.readInt());
    } finally {
        // uvolnit prostředky

        if (clientSocket != null) {
            try {
                clientSocket.close();
            } catch (IOException ex) {
                // NOP
            }
        }

        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException ex) {
                // NOP
            }
        }
    }
}
```

#### Klient

```java
public void runClient() throws IOException {
    Socket clientSocket = null;
    DataOutputStream serverStream = null;

    try {
        // vytvořit socket a spojit ho se serverem "localhost" na portu 12345
        clientSocket = new Socket("localhost", 12345);

        // získat výstupní proud socketu
        // (výstup pro klienta = vstup pro server)
        serverStream = new DataOutputStream(clientSocket.getOutputStream());

        // odeslat data
        serverStream.writeInt(42);
    } finally {
        // uvolnit prostředky

        if (clientSocket != null) {
            try {
                clientSocket.close();
            } catch (IOException ex) {
                // NOP
            }
        }
    }
}
```

### Komunikace přes UDP

Protokol UDP (User Datagram Protocol) je minimalistický protokol pro příjem a odesílání zpráv, tzv. **datagramů**. Protože neobsahuje žádné mechanismy pro navazování a řízení spojení, není zajištěno pořadí ani doručení jednotlivých odeslaných datagramů. Tyto funkce v případě nutnosti musí zajistit až aplikace.

Následující příklad demonstruje základní práci se socketem.

#### Server

```java
// otevřít socket na portu 9876
final DatagramSocket serverSocket = new DatagramSocket(9876);

// připravit buffer o velikosti 1024 bajtů
final byte[] receiveData = new byte[1024];

while (!cancelled) {
  // připravit úložiště pro příchozí paket
  final DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
  
  // přijmout paket (blokující volání až do doby, než paket dorazí)
  serverSocket.receive(receivePacket);
  
  // data paketu jsou nyní uložena jako pole bajtů: receivePacket.getData()
}

// uzavřít socket
serverSocket.close();
```

#### Klient

```java
// paket bude odeslán na tento počítač - "localhost"
final InetAddress IPAddress = InetAddress.getByName("localhost");

// připravit socket
final DatagramSocket clientSocket = new DatagramSocket();

// připravit data k odeslání
final byte[] data = "hello".getBytes();

// připravit úložiště pro odchozí paket na port 9876
final DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, 9876);

// odeslat paket
clientSocket.send(sendPacket);

// uzavřít socket
clientSocket.close();
```

### Reference

- http://en.wikipedia.org/wiki/Internet_protocol_suite
- http://en.wikipedia.org/wiki/Transmission_Control_Protocol
- http://en.wikipedia.org/wiki/User_Datagram_Protocol
- http://tools.ietf.org/html/rfc768