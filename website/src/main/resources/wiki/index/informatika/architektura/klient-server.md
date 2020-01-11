## Architektura Klient-Server

Klient-server (client-server) je architektura distribuované aplikace, která se skládá ze dvou základních komponent: **klient** a **server**.

* Server je komponenta, která poskytuje určitou službu. Službou zde rozumíme například data nebo provedení nějakého výpočtu. K jednomu serveru může být připojen libovolný počet klientů.
* Klient je komponenta, která se k serveru připojuje, aby této služby využila.

Komunikaci zahajuje klient, který k serveru naváže stabilní spojení a odešle svůj první požadavek.
Následně může stejné spojení využít i pro požadavky následující.
Server pro každého nového klienta vytvoří nový vnitřní stav, který se označuje jako **relace** (session).
Tento stav k němu přiřadí až do doby, než se klient odpojí.
Relace primárně slouží k omezení síťové komunikace - klient se může například přihlásit jen na začátku relace a pak už se na existující přihlášení pouze odvolávat.

```uml:class
actor Client1
actor Client2
actor Client3
storage Server
Client1 -up-> Server: session 1
Client2 -up-> Server: session 2
Client3 -up-> Server: session 3
```

### Výhody

* kapacita klientů a serverů je plně nezávislá
* kód klienta i serveru lze nezávisle aktualizovat (omezeno kompatibilitou rozhraní)

### Nevýhody

* protože je klientů řádově víc než serverů, server musí umět pracovat s mnoha klienty najednou a udržovat relaci pro každého z nich
* bezpečnost a dobré úmysly klientů nelze nijak zajistit (mohou být třeba napadeni viry), a proto musí server zajistit autentikaci a autorizaci klientů
* jako centrální místo může být server cílem útoků, například DDOS (distribuované přetížení serveru)

### Reference

* https://apachebooster.com/blog/what-is-client-server-architecture-and-what-are-its-types/
* http://www.cs.sjsu.edu/~pearce/oom/ood/distArch/server
* https://cio-wiki.org/wiki/Client_Server_Architecture
* https://techterms.com/definition/client-server_model
