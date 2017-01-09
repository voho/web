## Práce s XML

### Tvorba DOM

```java
final DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
final DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
final Document document = documentBuilder.newDocument();
```

```java
final Element people = document.createElement("people");
document.appendChild(people);

final Element person1 = document.createElement("person");
person1.setAttribute("name", "John Doe");
person1.setAttribute("age", "27");
people.appendChild(person1);

final Element person2 = document.createElement("person");
person2.setAttribute("name", "Eve Evil");
person2.setAttribute("age", "44");
people.appendChild(person2);
```

### Zápis DOM do XML

Výstup do výstupního proudu:

```java
public static void xmlToOutputStream(final Document document, final OutputStream output) throws TransformerException, IOException {
    final TransformerFactory transformerFactory = TransformerFactory.newInstance();
    final Transformer transformer = transformerFactory.newTransformer();
    final Result result = new StreamResult(output);
    final Source source = new DOMSource(document);
    transformer.transform(source, result);
    output.flush();
}
```

Výstup do řetězce:

```java
public static String xmlToString(final Document document) throws TransformerException, IOException {
    final TransformerFactory transformerFactory = TransformerFactory.newInstance();
    final Transformer transformer = transformerFactory.newTransformer();

    try (final ByteArrayOutputStream output = new ByteArrayOutputStream()) {
        final Result result = new StreamResult(output);
        final Source source = new DOMSource(document);
        transformer.transform(source, result);
        return output.toString("utf-8");
    }
}
```

Formátování výstupního XML (závislé na implementaci XML API):

```java
transformer.setOutputProperty(OutputKeys.INDENT, "yes");
transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
```

### Čtení XML do DOM

Ze vstupního proudu:

```java
public static Document domFromInputStream(final InputStream input) throws ParserConfigurationException, IOException, SAXException {
    final DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
    final DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
    return documentBuilder.parse(input);
}
```

Ze souboru:

```java
public static Document domFromFile(final File file) throws ParserConfigurationException, IOException, SAXException {
    final DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
    final DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        
    try (final InputStream input = new FileInputStream(file)) {
        return documentBuilder.parse(input);
    }
}
```