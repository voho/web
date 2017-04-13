## Blockchain

Zatím neexistuje žádná formální definice toho, co přesně pojem **blockchain** znamená. Pod tímto označením se tedy skrývají různé technologie a [systémové architektury](wiki/architektura), u kterých lze vypozorovat tyto společné rysy:

- je to distribuovaná databáze nějakých transakcí (např. finančních toků nebo informací o převodu vlastnictví z jednoho subjektu na druhý)
- jednotlivé transakce v této databázi jsou uloženy v tzv. **blocích**, které po sobě následují v přesně daném a neměnném pořadí, přičemž každý blok se odkazuje na blok předchozí
- obsahuje kryptografický mechanismus prakticky zabraňující změně existujících transakcí nebo vložení neověřených bloků
- používá kryptografii a digitální podpisy pro autentizaci a autorizaci čtecích a zápisových operací
- není určen jednoznačný vlastník dat, jedná se tedy často o sdílená data

### Potenciální využití

Sdílená ekonomika
: Decentralizovaný peer-to-peer blockchain lze využít k bezpečnému převádění vlastnických práv k různým předmětům bez potřeby centrální autority: dopravní prostředky, nemovitosti, finanční produkty, obrazy, hudba, filmy, patenty, licence. Ve světě, kde neexistuje důvěra k velkým společnostem, se takové ekonomice může překvapivě dobře dařit.

Dohled
: Technologie a protokoly založené na blockchainu mohou být využity ke zvýšení transparentnosti veřejného hlasování v demokratických systémech. Každý volič bude mít jistotu, že byl jeho hlas započítán do celkového výsledku, aniž by musel důvěřovat nějaké autoritě.

Ověřování autenticity produktů
: Historie určitého produktu může být v čase zaznamenávána a ověřována koncovými zákazníky. Velmi důležité pro produkty typu fair-trade a různé sběratelské předměty vysoké hodnoty.

Ukládání souborů
: Soubory uložené v peer-to-peer blockchainu nemusí mít centrálního vlastníka a úložiště, které by mohlo ohrozit napadení hackerem či výpadek proudu.

### Základní pojmy

#### Otisk (hash)

Pod pojmem otisk rozumíme výstup [hashovací funkce](wiki/zobrazeni), která převádí řetězec libovolné délky na řetězec fixní délky, tzv. **otisk** (fingerprint). 
Dobrá hashovací funkce by měla mít následující vlastnosti:
 
- z otisku je **velmi obtížné** získat vstupní data
- změnou vstupních dat se otisk mění **velmi těžko** předvídatelným způsobem

Pokud zde používáme výrazy jako **velmi těžko**, mluvíme o současné technologii - teoreticky prolomení možné je, ale čas potřebný k takové operaci přesahuje jakoukoliv rozumnou mez.

#### Transakce

Transakcí může být cokoliv - jedna zpráva, událost, finanční operace, záznam o převodu vlastnictví určitého majetku, a podobně.
V případě potřeby lze transakce zkládat zašifrované či podepsané a připojit i veřejný klíč k jejímu ověření, případně dešifrování.

Ke každé transakci se vypočítá její otisk, který by měl zahrnovat všechny její parametry.
 
Posléze se vypočítá otisk všech těchto otisků, který se stane součástí bloku. K výpočtu otisku otisků se používá tzv. [Merkle Tree](https://en.wikipedia.org/wiki/Merkle_tree).

```dot:digraph
rankdir=LR;
Transaction1 -> T1_hash -> T12_hash -> T1234_hash
Transaction2 -> T2_hash -> T12_hash -> T1234_hash
Transaction3 -> T3_hash -> T34_hash -> T1234_hash
Transaction4 -> T4_hash -> T34_hash -> T1234-hash
T1234_hash -> Block
```

#### Blok

Základní datová jednotka, se kterou blockchain pracuje, je jeden blok.
Každý blok obsahuje libovolný počet transakcí a několik dalších informací, z nichž nejdůležitější je **odkaz na předcházející blok**.
Pokud tedy začneme čist řetězec bloků, pomocí odkazů na předchozí bloky se postupně dostaneme až k prvnímu bloku a tím pádem získáme celou historii: všechny bloky a s nimi i všechny transakce.

Tento odkaz je uložen jako **otisk předchozího bloku**, nikoliv tedy jako nějaký identifikátor nebo časové razítko - pokud bychom používali identifikátory, bylo by snadné původní blok odstranit a na jeho místo vložit blok jiný.
Protože je otisk bloku vypočítán na základě celého svého obsahu, včetně případného odkazu na předchozí blok, je otisk bloku určitým odrazem celé historie daného blockchainu
Jakmile bychom tedy libovolný blok nebo transakci v historii změnili, změnil by se tím i jeho otisk a následující bloky by na něj již neodkazovaly správně.

Jako blockchain - tedy **řetězec bloků** - se označuje jednosměrně zřetězená posloupnost těchto bloků, ve které má každý blok ukazatel na předchozí blok.

![blockchain](blockchain.png)

#### Nonce

Obecně je se jako **nonce** v kryptografii označuje (zpravidla náhodné) číslo, které lze použít pouze jednou. V blockchainu se nonce do bloků a tedy i jejich otisků přidává pro zvýšení odolnosti vůči útokům - jeho přítomnost zvyšuje obtížnost podvržení daného bloku. I kdyby útočník vytvořil celý blok včetně transakcí takový, aby jeho otisk odpovídal otisku původního bloku, stále by musel najít takovou hodnotu nonce, aby se hash podvrženého bloku nezměnil. V praxi samozřejmě neexistuje záruka toho, že nějaké číslo bude použito pouze jednou, ale pokud je jeho rozsah dostatečně velký, není to velký problém. K nonce se také často přidává časové razítko (timestamp).

### Operace

#### Ověřování řetězce

!TODO!

#### Přidání platné transakce

!TODO!

#### Útok: vložení neplatné transakce

!TODO!

### Implementace

- [Ethereum](https://www.ethereum.org/)
- [Hyperledger](https://www.hyperledger.org/)

### Reference

- https://distributedlab.com/whitepaper/DLT&exchangeModel.pdf
- http://searchcio.techtarget.com/feature/Step-by-step-guide-to-a-blockchain-implementation
- https://bitsonblocks.net/2016/02/29/a-gentle-introduction-to-immutability-of-blockchains/
- https://bitsonblocks.net/2015/09/09/a-gentle-introduction-to-blockchain-technology/
- http://www.xorbin.com/tools/sha256-hash-calculator
- http://www.abclinuxu.cz/images/clanky/hrach/bitcoin-blockchain.png
