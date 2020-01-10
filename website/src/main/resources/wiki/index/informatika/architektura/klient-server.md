## Architektura Klient-Server

Klient-server (client-server) je architektura distribuované aplikace, která se skládá ze dvou základních komponent: **klient** a **server**.

* Server je komponenta, která poskytuje určitou službu. Službou zde rozumíme například data nebo provedení nějakého výpočtu.
* Klient je komponenta, která se k serveru připojuje, aby této služby využila.

Komunikaci zahajuje klient, který k serveru naváže stabilní spojení a odešle svůj první požadavek.
Následně může stejné spojení využít i pro požadavky následující.

```uml:class
actor Client1
actor Client2
storage Server
Client1 -up-> Server
Client2 -up-> Server
```

### Výhody

* kapacita klientů a serverů je plně nezávislá; obě komponenty lze libovolně škálovat
* díky tomu, že jeden server obsluhuje větší počet klientů, bývá tato architektura poměrně levná

### Nevýhody

* protože je klientů řádově víc než serverů, server musí umět pracovat s více klienty najednou, nějak je rozlišovat, a stíhat jim odpovídat
* bezpečnost a dobré úmysly klientů nelze nijak zajistit (mohou být napadeni např. i viry), a proto by měl server zajistit autentikaci a autorizaci klientů
* jako centrální místo může být server cílem útoků, například DDOS (distribuované přetížení serveru)

### Reference

* https://apachebooster.com/blog/what-is-client-server-architecture-and-what-are-its-types/
* http://www.cs.sjsu.edu/~pearce/oom/ood/distArch/server
* https://cio-wiki.org/wiki/Client_Server_Architecture
* https://techterms.com/definition/client-server_model
