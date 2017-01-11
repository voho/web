## Hashování (otisk)

Hashovací kryptografické funkce mají za úkol ze vstupních dat v **rozumném čase** vytvořit výstup fixní délky. Tomuto výsledku se říká *otisk* či *miniatura* (anglicky *hash*, *digest*, *checksum* nebo *fingerprint*) a má následující vlastnosti:

- z otisku je "nemožné" (vzhledem k současné technologii a stáří vesmíru, proto ty uvozovky) zpětně zrekonstruovat původní data 
- je "nemožné" najít dvě různé zprávy se stejným otiskem
- otisk je velmi citlivý na změnu vstupních dat

Seznam některých hashovacích algoritmů dostupných ve standardní knihovně jazyka Java:

- [MD2](http://tools.ietf.org/html/rfc1319), [MD5](http://tools.ietf.org/html/rfc1321), [SHA-1](http://tools.ietf.org/html/rfc3174), [SHA-256](http://csrc.nist.gov/publications/fips/fips180-3/fips180-3_final.pdf), [SHA-384](http://csrc.nist.gov/publications/fips/fips180-3/fips180-3_final.pdf), [SHA-512](http://csrc.nist.gov/publications/fips/fips180-3/fips180-3_final.pdf)

### Tvorba otisků

```java
private static byte[] toMd5(final byte[] data) {
  return toDigest(data, "MD5");
}

private static byte[] toSha512(final byte[] data) {
  return toDigest(data, "SHA-512");
}

private static byte[] toSha1(final byte[] data) {
  return toDigest(data, "SHA-1");
}

// ...

private static byte[] toDigest(final byte[] data, final String algorithm) {
  try {
    return MessageDigest.getInstance(algorithm).digest(data);
  }
  catch (Exception x) {
    throw new RuntimeException("Error while calculating digest.", x);
  }
}
```

Pro převod binárních dat (pole bajtů) na řetězec lze využít kódování [Base64](wiki/base64) nebo následující jednoduchou metodu pro převod čísla do hexadecimálním zápisu:

```java
String hexString = new BigInteger(1, data).toString(16);
```

### Tipy a triky

- Při tvorbě hashe se doporučuje vstupní data "smíchat" s vhodně zvolenou konstantou (tzv. sůl), aby byly ztíženy slovníkové útoky.
- Hashe lze samozřejmě kombinovat a vzájemně do sebe vnořovat (hash hashe).
- Od používání algoritmu MDA5 se ustupuje.

### Příklady

Příklady jsou uvedeny v hexadecimálním zápisu.

- *Hello world!* (md5) = 86fb269d190d2c85f6e0468ceca42a20
- *Hello world?* (md5) = 48604754b9fed84b3feeb84c5dc138c0
- *Hello world!* (sha1) = d3486ae9136e7856bc42212385ea797094475802
- *Domů přijdu dnes večer v 18:21.* (sha1) = 857528cc54c6f8348957eaaaf5a080e9d271b251

Zde je vidět, jak změna jednoho jediného bajtu (čísla 18 na 19) způsobí zásadní změnu hashe:

> Domů přijdu dnes večer v 18:21. (sha512):

```plain
640d8b0f19b27076c9d36a77fa2d0957323df6f81c7c3e75725ce333e67fc947
393cb74836e5520987119b635ad1d60fba5e94a1aacfe029fb9da6d8ae78c70a
```

> Domů přijdu dnes večer v 19:21. (sha512):

```plain
a2eb963189e0ebb12c72b28c4b658cd603c9dc54de437899878497d75b27e378
948b834e5f16da1bf7d505a9ac15698155c07d3925092a15a3855ccf55dab3a7
```

### Reference

- http://tools.ietf.org/html/rfc1319
- http://tools.ietf.org/html/rfc1321
- http://tools.ietf.org/html/rfc3174
- http://csrc.nist.gov/publications/fips/fips180-3/fips180-3_final.pdf
- http://stackoverflow.com/questions/521101/using-sha1-and-rsa-with-java-security-signature-vs-messagedigest-and-cipher
- http://stackoverflow.com/questions/4895523/java-string-to-sha1
- http://www.anyexample.com/programming/java/java_simple_class_to_compute_sha_1_hash.xml