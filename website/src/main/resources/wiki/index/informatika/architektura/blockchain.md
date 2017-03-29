## Blockchain

Pod názvem "blockchain" se skrývají různé technologie a systémové architektury, protože se jedná o poměrně nový a tedy neustálený pojem. 
Dají se vypozorovat tyto společné rysy:

- distribuovaná databáze nějakých transakcí (např. finančních toků)
- kryptografický mechanismus prakticky zabraňující změně existujících transakcí
- používá kryptografii a digitální podpisy pro autentizaci a autorizaci čtecích a zápisových operací

### Základní pojmy

#### Otisk (hash)

Pod pojmem otisk rozumíme výstup [hashovací funkce](wiki/zobrazeni), která převádí řetězec libovolné délky na řetězec fixní délky, tzv. **otisk** (fingerprint). 
Dobrá hashovací funkce by měla mít následující vlastnosti:
 
- z otisku je **velmi obtížné** získat vstupní data
- změnou vstupních dat se otisk mění **velmi těžko** způsobem

Pokud zde používáme výrazy jako **velmi těžko**, mluvíme o současné technologii, se kterou by se podobné operace realizovaly až miliony let.

#### Transakce

Transakcí může být cokoliv - jedna zpráva, událost, finanční operace, záznam o převodu vlastnictví určitého majetku, a podobně.

#### Blok

Základní datová jednotka, se kterou blockchain pracuje, je jeden blok.
Každý blok obsahuje libovolný počet transakcí a několik dalších informací, z nichž nejdůležitější je **odkaz na předcházející blok**.
Pokud tedy začneme čist řetězec bloků, pomocí odkazů na předchozí bloky se postupně dostaneme až k prvnímu bloku a tím pádem získáme celou historii: všechny bloky a s nimi i všechny transakce.

Tento odkaz je uložen jako **otisk předchozího bloku**, nikoliv tedy jako nějaký identifikátor nebo časové razítko - pokud bychom používali identifikátory, bylo by snadné původní blok odstranit a na jeho místo vložit blok jiný.
Protože je otisk bloku vypočítán na základě celého svého obsahu, včetně případného odkazu na předchozí blok, je otisk určitým odrazem celé historie.
Jakmile bychom tedy libovolný blok nebo transakci v historii změnili, změnil by se tím i jeho otisk a následující bloky by na něj již neodkazovaly správně.

### Neměnnost

!TODO!

#### Ověřování otisků

Celou historii lze ověřit tak, že se začne u prvního bloku a všechny otisky se znovu ověří.

### Reference

- https://bitsonblocks.net/2016/02/29/a-gentle-introduction-to-immutability-of-blockchains/
- https://bitsonblocks.net/2015/09/09/a-gentle-introduction-to-blockchain-technology/
- http://www.xorbin.com/tools/sha256-hash-calculator