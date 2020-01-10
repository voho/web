## Model-View-Controller

Model-View-Controller (MVC) je jeden z nejpoužívanějších a nejobecnějších architektonických vzorů. Jeho základní myšlenky jsou velmi jednoduché, ale na druhou stranu není příliš konkrétní a tak jej lze snadno použít nesprávným způsobem. Existuje mnoho variant tohoto návrhového vzoru, které se zpravidla liší jen v některém dílčím paradigmatu.

Návrhový vzor MVC rozděluje program do tří hlavních částí:

- **Model** – data a související operace (tzv. business logika)
- **View** – prezentace dat (např. uživatelské prostředí) obsahuje přímý odkaz na model, aby mohl jeho data prezentovat vnějšímu světu
- **Controller** – řadič, který řídí tok událostí v programu (tzv. aplikační logika) obsahuje přímý odkaz na model, aby mohl modifikovat jeho data

Rozdělení do těchto tří částí přitom nemusí být pouze na jedné úrovni – i jednotlivé podprogramy mohou mít své mikroarchitektury založeny na MVC.

![Model-View-Controller](mvc1.png)

Je-li potřeba, aby program komunikoval s okolním světem (například pracoval s databází), je nutné program rozšířit o součást, která tuto komunikaci zajistí a poskytne tuto funkcionalitu i ostatním součástem systému. Tato součást nesmí být součástí modelu, aby jejich funkce zůstaly jasně vymezeny, proto se do vzoru přidává jako samostatná vrstva.

Tato nová vrstva se nazývá servisní vrstva. Ta může (ale neměla by) mít přímou referenci na model. Výhodou servisní vrstvy je možnost jejího snadného nahrazení zástupným „mock“ objektem, což se s úspěchem využívá při jednotkovém testování. Vzniklý model se někdy označuje jako Model-View-Controller-Service (MVCS).

![Model-View-Controller-Service](mvc2.png)

```uml:seq
User -> View : perform action via UI
View -> Controller : invoke action
Controller -> Model : change
Model --> Controller
Model --> View : notifies about change
View -> Model : request current state
Model --> View 
View --> User : update UI
```