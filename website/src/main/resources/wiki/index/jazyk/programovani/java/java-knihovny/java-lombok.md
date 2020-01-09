## Lombok

Lombok je soubor anotací pro jazyk Java který umožňuje minimalizovat neužitečný kód.
Lombok toto realizuje generováním zdrojového kódu během před-kompilace. 

Pro podporu tohoto nástroje v IDE je zpravidla potřeba stáhnout plugin, který bude za běhu výsledný kód poskytovat vývojovému prostředí.

### Nastavení

- Gradle: https://projectlombok.org/setup/gradle
- Maven: https://projectlombok.org/setup/maven

### Základní anotace

- [@NonNull](https://projectlombok.org/features/NonNull)
    - u atributu třídy: označí pole jako povinné a vrátí výjimku (`NullPointer, že jeho hodnota nikdy není `null` (kontrolu v setteru a konstruktorech)
    - u parametru metody: přidá kontrolu hodnoty parametru
- [@Getter/@Setter](https://projectlombok.org/features/GetterSetter)
    - u atributu třídy: vygeneruje getter / setter
    - u třídy: vygeneruje gettery / settery pro všechna pole třídy
- [@ToString](https://projectlombok.org/features/ToString)
    - u třídy: vygeneruje implementaci metody `toString`
- [@EqualsAndHashCode](https://projectlombok.org/features/EqualsAndHashCode)
    - vygeneruje kód pro ověření ekvivalence dvou objektů (tzn. metody `equals` a `hashCode`)
    - podrobnosti (např. pole, která se mají ověřovat) lze upřesnit pomocí parametrů anotace
- [@NoArgsConstructor](https://projectlombok.org/features/constructor)
    - u třídy: vygeneruje konstruktor bez argumentů
- [@RequiredArgsConstructor](https://projectlombok.org/features/constructor)
    - u třídy: vygeneruje konstruktor se všemi povinnými atributy (povinný atribut = ten, který je anotovaný jako `@NonNull` nebo je to primitivní typ)
- [@AllArgsConstructor](https://projectlombok.org/features/constructor)
    - u třídy: vygeneruje konstruktor se všemi atributy třídy

### Pokročilé anotace

- `@Data`
    - getter pro každé pole
    - setter pro každé modifikovatelné pole (taková, které nejsou deklarovaná jako `final`)
    - implementaci `equals`, `hashCode` a `toString`
    - konstruktor pro všechna vyžadovaná pole (taková, která jsou deklarovaná jako `final`)
- `@Value`
    - podobné jako `@Data`, ale vlastnosti objektu nebudou modifikovatelné

### Reference

- https://projectlombok.org/
