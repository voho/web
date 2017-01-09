## Backus-Naurova forma

Backus-Naurova forma zápisu slouží k přehlednému zápisu pravidel [formální gramatiky](wiki/formalni-gramatika). Její výhoda spočívá především ve využití syntaktických zkratek.

Každé pravidlo v Backus-Naurově formě je ve tvaru *A ::= B*, kde *A* je neterminál uzavřený ve špičatých závorkách a *B* popisuje, na co lze tento neterminál přepsat. Může to být další neterminál (opět uzavřený ve špičatých závorkách), terminál (uzavřený v uvozovkách), nebo nějaká posloupnost terminálů a neterminálů o libovolné délce. Pokud je takových možností přepisu více, lze je oddělit znakem *|*. Právě tato možnost nejvíce pomáhá zkracovat zápis gramatik.

Backus-Naurova forma popisuje sebe samu zhruba takto:

```bnf
<rules>      ::= <rule> | <rule> <rules>
<rule>       ::= "<" <rule-name> ">" "::=" <expression> <EOL>
<expression> ::= <terms> | <terms> "|" <expression>
<terms>      ::= <term> | <term> <terms>
<term>       ::= <literal> | "<" <rule-name> ">"
<literal>    ::= '"' <text> '"' | "'" <text> "'"
```

Takto ji přibližuje jeden z jejích tvůrců:

> Sequences of characters enclosed in the brackets "<>" represent metalinguistic variables whose values are sequences of symbols. The marks "::=" and "|" (the latter with the meaning of "or") are metalingustic connectives. Any mark in a formula, which is not a variable or a connective, denotes itself. Juxtaposition of marks and/or variables in a formula signifies juxtaposition of the sequence denoted. *Peter Naur*

### Příklady

#### Binární čísla

Takto vypadá zápis gramatiky generující binární čísla v BNF:

```bnf
<binary> ::= <digit> | <binary> <digit>
<digit>  ::= "0" | "1"
```

A takto rozepsáno do pravidel:

1. **BINARY** &rarr; **DIGIT**
1. **BINARY** &rarr; **BINARY** **DIGIT**
1. **DIGIT** &rarr; *0*
1. **DIGIT** &rarr; *1*
