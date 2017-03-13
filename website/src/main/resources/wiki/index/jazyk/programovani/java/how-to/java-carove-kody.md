## Čárové a QR kódy v Javě

Pro generování a čtení čárových a QR kódů v Javě doporučuji knihovnu [ZXing](https://github.com/zxing/zxing).
Knihovna je velmi univerzální a podporuje řadu formátů. Zde se zaměříme na QR kódy.

Pro použití příkladů potřebujete následující knihovny:

* http://repo1.maven.org/maven2/com/google/zxing/core/
* http://repo1.maven.org/maven2/com/google/zxing/javase/

### Generování QR kódu

#### Generování matice QR kódu

```java
public static BitMatrix stringToQrCodeBitMatrix(final String contents, final int bitmapSize) throws WriterException {
    final Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
    // set character set to UTF-8
    hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name());
    // set margin to zero to not waste space
    hints.put(EncodeHintType.MARGIN, 0);
    // set error correction level to low
    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

    final Writer writer = new MultiFormatWriter();
    return writer.encode(contents, BarcodeFormat.QR_CODE, bitmapSize, bitmapSize, hints);
}
```

#### Vykreslení QR kódu

```java
public static RenderedImage renderBitMatrix(final BitMatrix bitMatrix) {
    final BufferedImage bufferedImage = new BufferedImage(bitMatrix.getWidth(), bitMatrix.getHeight(), IndexColorModel.BITMASK);
    final Graphics graphics = bufferedImage.getGraphics();

    for (int x = 0; x < bitMatrix.getWidth(); x++) {
        for (int y = 0; y < bitMatrix.getHeight(); y++) {
            graphics.setColor(bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
            graphics.fillRect(x, y, 1, 1);
        }
    }

    return bufferedImage;
}
```

### Čtení QR kódu

```java
public static String readTextFromQrCode(final BufferedImage qrCodeImage) throws FormatException, ChecksumException, NotFoundException {
    final Map<DecodeHintType, Object> hints = new EnumMap<DecodeHintType, Object>(DecodeHintType.class);
    hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
    hints.put(DecodeHintType.POSSIBLE_FORMATS, EnumSet.allOf(BarcodeFormat.class));
    hints.put(DecodeHintType.PURE_BARCODE, Boolean.FALSE);

    final LuminanceSource luminanceSource = new BufferedImageLuminanceSource(qrCodeImage);
    final HybridBinarizer binarizer = new HybridBinarizer(luminanceSource);
    final BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
    final Reader reader = new MultiFormatReader();
    final Result result = reader.decode(binaryBitmap, hints);
    return result.getText();
}
```

### Reference

- https://github.com/zxing
- https://zxing.org/w/decode.jspx
