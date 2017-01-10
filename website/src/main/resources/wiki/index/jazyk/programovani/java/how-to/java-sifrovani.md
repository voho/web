## Jednoduché šifrování

Bezpečnost dat je jednou z největších výzev v IT a nevyplácí se brát ji na lehkou váhu.
Rozhodně se vyplatí investovat do nějaké etablované knihovny třetí strany a pokud možno **nevytvářet vlastní řešení**.
Pokud přes všechna tato varování chcete implementovat jednoduchou metodu pro symetrické šifrování textu, můžete použít následující kód.
Mějte však na paměti, že se jedná spíše o obfuskaci, než o skutečné šifrování, na prolomení je šifra příliš slabá.

### Jednoduché symetrické šifrování (AES)

```java
public class Crypto {
    private static final String AES_NAME = "AES";
    private static final int ALLOWED_KEY_LENGTH = 16;
    
    private final Key key;

    public Crypto(final String rawKey) {
        if (rawKey == null || rawKey.length() != ALLOWED_KEY_LENGTH) {
            throw new IllegalStateException("Key must be specified and have length of 16.");
        }

        key = new SecretKeySpec(rawKey.getBytes(StandardCharsets.US_ASCII), AES_NAME);
    }

    public String encrypt(final String data) {
        try {
            final Cipher cipher = Cipher.getInstance(AES_NAME);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return fromByteArray(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public String decrypt(final String data) {
        try {
            final Cipher cipher = Cipher.getInstance(AES_NAME);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(toByteArray(data)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static String fromByteArray(final byte[] value) {
        return DatatypeConverter.printHexBinary(value);
    }

    private static byte[] toByteArray(final String value) {
        return DatatypeConverter.parseHexBinary(value);
    }
}
```