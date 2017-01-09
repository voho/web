## Mockito

[Mockito](https://code.google.com/p/mockito/) je testovací framework pro tvorbu **mocků**, tedy zástupných objektů, které se používají při tvorbě komponentových testů.

Hlavním rysem každého mocku je to, že se navenek tváří jako obyčejný objekt a přitom můžeme libovolně definovat jeho chování. Tak lze jendoduše nasimulovat mnohem více testovacích případů než bez nich a nároky na modularitu zdrojového kódu nejsou tak veliké.

Filozofie psaní integračních testů je takováto:

1. Zvolí se jednotka, která bude testována.
1. Vytvoří se mocky všech objektů, které testovaná jednotka ke své funkci potřebuje.
1. Vytvoří se všechny potřebné mocky a co nejjednodušeji se nadefinuje jejich chování tak, aby byl testovací případ naplněn.
1. Připravené mocky se zavedou do testované jednotky místo skutečných objektů.
1. Na testovací jednotce je vykonána požadovaná operace.
1. Vyhodnotí se výsledek operace a podle potřeby i jednotlivá volání mock objektů. Je možné vyhodnocovat i hodnoty parametrů, které byly z testované jednotky objekty posílány.

### Tvorba mocků

Chceme-li vytvořit mock k nějakému typu, použijeme k tomu metodu *mock*:

```java
Person personMock = Mockito.mock(Person.class);
```

Ve výchozím stavu je mock prázdný a nemá definované žádné chování. To znamená, že žádná metoda nic nedělá a pokud má návratovou hodnotu, vrací *null*. Chování mocku lze specifikovat později (viz další kapitolu).

V testech se mocky znovu vytváří zpravidla pro každý testovací případ, aby se pro každý testovací případ mohlo definovat jiné chování mocku a testovací případy se vzájemně neovlivňovaly. Protože se v tomto případě kód pro vytváření mocků neustále opakuje, lze použít následující zkrácený zápis:

```java
@RunWith(MockitoJUnitRunner.class)
public class PersonTest {
    @Mock
    private Person personMock;

    @Test
    public void testPerson() {
        // ...
    }
}
```

Výše uvedený kód odpovídá tomuto:

```java
public class PersonTest {
    @Mock
    private Person personMock;

    @Before
    public void initializeMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPerson() {
        // ...
    }
}
```

#### Definice chování

Jak již bylo řečeno, mock po vytvoření nemá definováno žádné chování a metody, které mají vracet nějakou hodnotu, vrací *null*. Potřebujeme-li, aby se mock choval určitým způsobem, můžeme jeho chování nastavit metodu po metodě velmi příjemným způsobem. 

Pokud má mockovaná metoda parametry, musí se chování mocku definovat zvlášť pro jejich různé hodnoty. Lze však použít různé zobecňující konstrukce:

- **instance typu X** (metoda *any*, *anyString*, *anyInt*, *anyMap*, atd.)
- **instance rovnající se X** (metoda *eq*, *refEq*)
- **instance shodné s X** (metoda *same*)
- **hodnoty NULL** (metoda *isNull*)
- **hodnoty různé od NULL** (metoda *isNotNull*)
- **řetězce odpovídající pravidlu** (metoda *matches*, *contains*, atd.)
- atd.

Při zavolání mockované metody jsou přijaté parametry porovnány těmito konstrukcemi a pokud vyhovují některému z pravidel, je pravidlo použito pro reakci objektu.

##### Návrat hodnoty

Tato definice způsobí, že po zavolání metody *someMethod()* bude vrácena hodnota *John*:

```java
// bez parametrů
Mockito.doReturn("John").when(myMock).someMethod();

// s parametry
Mockito.doReturn(5).when(calculatorMock).sum(eq(3), eq(2));
```

```java
// bez parametrů
Mockito.when(myMock.someMethod()).thenReturn("John");

// s parametry
Mockito.when(calculatorMock.sum(eq(3), eq(2))).thenReturn(5);
```

```java
// bez parametrů
BDDMockito.given(myMock.someMethod()).willReturn("John");

// s parametry
BDDMockito.given(calculatorMock.sum(eq(3), eq(2))).willReturn(5);
```

##### Návrat výjimky

```java
Mockito.doThrow(IllegalStateException.class).when(myMock).someMethod();
```

```java
Mockito.when(myMock.someMethod()).thenThrow(Exception.class);
```

```java
BDDMockito.given(myMock.someMethod()).willThrow(IllegalStateException.class);
```

#### Zachytávání argumentů

Každý mock může zachytávat argumenty, které mu okolí poslalo. Tak lze sledovat, jak testovaná jednotka komunikovala s okolím ve větší podrobnosti.

```java
// vytvoření zachytávače

final ArgumentCaptor<Person> personCaptor = ArgumentCaptor.forClass(Person.class);

// verifikace

verify(dao).save(personCaptor.capture());

assertEquals(john, personCaptor.getValue());
```

### Reference

- https://code.google.com/p/mockito/
- http://www.vogella.com/tutorials/Mockito/article.html#mockito
- http://fruzenshtein.com/junit-and-mockito/