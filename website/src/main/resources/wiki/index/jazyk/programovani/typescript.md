## TypeScript 3

TypeScript je staticky typovaná nadmnožina jazyka JavaScript. 
To znamená, že všechny programy napsané v JavaScriptu (od verze ES2015) jsou také validní v TypeScriptu, avšak můžete je obohatit o další syntaktické prvky.
Jak již název napovídá, hlavním přínosem jazyka TypeScript pro programátora jsou datové typy, statická typová kontrola a typová inference.
Kromě typového systému můžete také využít všechny obvyklé moderní konstrukty, jako jsou třídy, generické a množinové typy, mixiny, apod.

### Proměnné

#### Deklarace

K deklaraci proměnných slouží tři klíčová slova:

- *var* - proměnná, jejíž hodnotu lze měnit; podporovaná z důvodu zpětné kompatibility (nedoporučuje se používat)
- *let* - proměnná, jejíž hodnotu lze měnit; viditelná pouze v aktuálním bloku (*{* ... *}*)
- *const* - proměnná, jejíž hodnotu nelze měnit 

#### Základní datové typy

- *any* - jakýkoliv typ (pro zpětnou kompatibilitu s neotypovaným kódem)
- *null* - pouze prázdná hodnota *null*
- *undefined* - nedefinovaná hodnota *undefined*
- *void* - žádná hodnota (nic)
- *boolean* - logická hodnota **true** / **false**
- *number* - číselná hodnota (podporuje desetinná i celá čísla)
- *string* - řetězec

```typescript
// libovolný typ 
const something: any;

// prázdný typ
const nullValue = null;
const undefinedValue = undefined;

// číselná hodnota
let decimal: number = 6;
let hex: number = 0xf00d;
let binary: number = 0b1010;
let octal: number = 0o744;

// pole
const fruits: string[] = ['Apple', 'Orange', 'Banana'];
const animals: Array<string> = ['Cat', 'Dog', 'Rabbit']; // generický typ

// uspořádané n-tice (tuples)
const [a, b, c, d] = ["The", "Answer", "is", 42];

// množinový typ (union)
const rating = string | null
```


never  /* unreachable */
enum Color {Red, Green, Blue = 4}
let c: Color = Color.Green

## Reference

- https://legacy.gitbook.com/book/basarat/typescript
- https://devhints.io/typescript