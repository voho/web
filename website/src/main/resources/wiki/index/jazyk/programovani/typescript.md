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

### Funkce

```typescript
// klasický zápis
function sum(a: number, b: number): number {
  return a + b;
}

// funkcionální zápis
const sum = (a: number, b: number): number => {
  return a + b;
}

// funknční výraz
const sum = function (a: number, b: number): number {
  return a + b;
}
```

### Řídící konstrukce

```typescript
if (a < 5) { /* ... */ } else { /* ... */ }
switch (a) { case 1: /* todo */ break; default: /* todo */ break; }
while (a < 5) { /* ... */ }
do { /* ... */ } while (a < 5)
```

### Typy a rozhraní

```typescript
// TODO
```

#### Průnik typů

```typescript
interface Bird {
    fly();
    layEggs();
}

interface Fish {
    swim();
    layEggs();
}

function getSmallPet(): Fish | Bird {
    // ...
}

let pet = getSmallPet();
pet.layEggs(); // okay
pet.swim();    // errors
```

#### Sjednocení typů

!TODO!

#### Typ funkce

```typescript
interface SearchFunc {
    (source: string, subString: string): boolean;
}
```

## Reference

- https://legacy.gitbook.com/book/basarat/typescript
- https://devhints.io/typescript
