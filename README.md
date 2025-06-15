# Progetto: Adapter per Java Collections Framework (CLDC 1.1)

## Descrizione del Progetto

Questo progetto implementa un **Adapter** per consentire l'utilizzo delle interfacce del Java 2 Collections Framework (versione 1.4.2) in un ambiente Java Micro Edition (Java ME) con la configurazione **CLDC 1.1**. In particolare, l'adapter mira a rendere utilizzabile l'interfaccia `java.util.List` e le sue interfacce correlate (`Collection`, `Iterator`, `ListIterator`) in un contesto in cui è disponibile solo la classe `java.util.Vector` di CLDC 1.1.

Il progetto si basa sul pattern Adapter, utilizzando la classe `java.util.Vector` come "adaptee" per fornire l'implementazione delle funzionalità definite dalle interfacce "target" personalizzate.

## Motivazione e Scopo

L'obiettivo principale di questo progetto è superare le limitazioni di compatibilità tra librerie Java sviluppate per J2SE 1.4.2 e ambienti più restrittivi come CLDC 1.1. In scenari reali, può essere necessario riutilizzare codice esistente che si affida al Collections Framework completo di J2SE, ma l'ambiente target non lo supporta nativamente.

Questo adapter fornisce una soluzione permettendo al codice J2SE di "parlare" con le strutture dati disponibili in CLDC 1.1 attraverso un'interfaccia familiare, senza dover riscrivere completamente la logica di business.

## Architettura e Design

Il progetto è strutturato secondo il **Pattern Adapter** e rispetta le seguenti specifiche:

1.  **Package:** Tutto il codice dell'adapter risiede nel package `myAdapter`.
2.  **Interfacce Target Personalizzate:** Per evitare collisioni con le interfacce standard di J2SE, sono state definite localmente le seguenti interfacce, che replicano fedelmente i metodi delle loro controparti J2SE 1.4.2:
    * `HCollection` (replica di `java.util.Collection`)
    * `HList` (replica di `java.util.List`, estende `HCollection`)
    * `HIterator` (replica di `java.util.Iterator`)
    * `HListIterator` (replica di `java.util.ListIterator`, estende `HIterator`)
3.  **Classe Adapter Principale:**
    * `ListAdapter`: Implementa l'interfaccia `HList` e utilizza un'istanza di `java.util.Vector` (di CLDC 1.1) internamente come adaptee. Tutti i metodi di `HList` sono implementati delegando le operazioni al `Vector` sottostante, assicurando la conformità alle specifiche di CLDC 1.1 (ad esempio, per le eccezioni, la gestione di `null`, ecc.). La classe gestisce anche l'implementazione della sottolista (`SubList`) come classe interna, che funge da vista sulla `ListAdapter` genitore.
4.  **Implementazione dell'Iteratore:**
    * `ListIterator`: Implementa `HListIterator` e `HIterator`. Anche questa classe agisce da adapter, incapsulando la logica necessaria per traversare e modificare la `ListAdapter` sottostante, sempre utilizzando le funzionalità di CLDC 1.1.
5.  **Gestione delle Eccezioni:**
    * `myExceptions.IllegalStateException`: Viene fornita un'implementazione personalizzata di `IllegalStateException`, compatibile con CLDC 1.1, per gestire i casi in cui un metodo viene invocato in uno stato inappropriato (come specificato in `HListIterator.remove()` o `set()`).

### Struttura delle directory

.
├── myAdapter/
│   ├── HCollection.java
│   ├── HList.java
│   ├── HIterator.java
│   ├── HListIterator.java
│   ├── ListAdapter.java
│   └── ListIterator.java
├── myExceptions/
│   └── IllegalStateException.java
└── myTest/
├── AllTestsSuite.java
├── TestListAdapterEmpty.java
├── TestListAdapterPopulated.java
├── TestListIteratorEmpty.java
├── TestListIteratorPopulated.java
├── TestSubListAdapter.java
└── TestRunner.java


## Come Compilare ed Eseguire

Il progetto è sviluppato in Java e può essere compilato ed eseguito utilizzando un compilatore Java (JDK).

### Prerequisiti

* Java Development Kit (JDK) 8 o superiore.
* JUnit 4 (già presente nelle dipendenze implicite dai file `TestRunner.java` e `AllTestsSuite.java`).

### Compilazione

Per compilare il progetto, bisogna posizionarsi nella directory radice del progetto (quella che contiene le cartelle `myAdapter`, `myExceptions`, `myTest`) ed eseguire il seguente comando:

```bash
javac -d . myAdapter/*.java myExceptions/*.java myTest/*.java
Questo comando compilerà tutti i file sorgente e creerà le directory dei package (myAdapter, myExceptions, myTest) nella directory corrente, posizionando lì i file .class.

Esecuzione dei Test
Per eseguire la suite completa di test JUnit, utilizzare il TestRunner fornito. Dopo aver compilato il progetto, eseguire il seguente comando dalla directory radice:

Bash

java -cp .:/path/to/junit-4.x.jar org.junit.runner.JUnitCore myTest.AllTestsSuite
Nota: Sostituire /path/to/junit-4.x.jar con il percorso effettivo del file JAR di JUnit 4 sul proprio sistema. Ad esempio, su Linux/macOS potrebbe essere qualcosa come /usr/share/java/junit-4.13.jar, su Windows C:\Users\YourUser\Downloads\junit-4.13.jar.

In alternativa, il progetto include un TestRunner.java personalizzato per una più facile esecuzione e visualizzazione dei risultati:

Bash

java -cp .:path/to/junit-4.x.jar myTest.TestRunner
Questo TestRunner stamperà un riepilogo dei test eseguiti, test falliti, test ignorati, tempo di esecuzione, e successo/insucesso.

Test
Il progetto include una suite di test esaustiva, organizzata per coprire i vari aspetti dell'adapter e delle sue interfacce. I test sono situati nel package myTest e sono suddivisi nelle seguenti classi:

TestListAdapterEmpty.java: Test per ListAdapter su una lista vuota, coprendo i casi limite e la gestione delle eccezioni.
TestListAdapterPopulated.java: Test per ListAdapter su una lista popolata, verificando l'accesso, la modifica, la ricerca, la conversione in array, la gestione di elementi null, la gestione di indici validi e non validi, l'aggiunta/rimozione multipla, l'uguaglianza e l'hashCode. Questa classe include numerosi test dettagliati per ogni metodo.
TestListIteratorEmpty.java: Test per ListIterator su una lista vuota, focalizzandosi sul comportamento dell'iteratore in assenza di elementi.
TestListIteratorPopulated.java: Test per ListIterator su una lista popolata, verificando la navigazione bidirezionale, le operazioni di modifica (add, remove, set) e la gestione dello stato.
TestSubListAdapter.java: Test dedicati alla classe interna SubList di ListAdapter, assicurando che si comporti come una vista coerente sulla lista genitore e che le modifiche si propaghino correttamente.
AllTestsSuite.java: Una suite JUnit che aggrega tutti i test sopra menzionati per un'esecuzione combinata.
TestRunner.java: Un'utility per eseguire la AllTestsSuite da riga di comando e presentare i risultati in modo leggibile.
La progettazione dei test mira a garantire la conformità alle specifiche J2SE 1.4.2 per i comportamenti di List e ListIterator, pur operando su CLDC 1.1. 

Particolare attenzione è stata data a:

Gestione degli elementi null
Comportamento degli indici fuori limite.
Propagazione delle modifiche tra lista e sottolista.
Stato interno di ListIterator (cursore, lastReturned).
Coerenza di equals() e hashCode().
Lancio delle eccezioni appropriate (IndexOutOfBoundsException, NullPointerException, IllegalStateException, UnsupportedOperationException).
Tecnologie Utilizzate
Java: Linguaggio di programmazione.
CLDC 1.1 (Java ME): Ambiente di riferimento per le funzionalità utilizzate (in particolare java.util.Vector).
JUnit 4: Framework per il testing unitario.


Accorgimenti:
- L'implementazione della lista permette il contenuto di oggetti "null"
- Nelle varie classi di test sono presenti dei costruttori di default che non sono necessari, ma sono stati messi per non avere problemi nella creazione di javadoc.
- Nei vari Test cases sono presenti diversi test methods: per scelta progettuale e' stato deciso che i test piu' semplici non presentano stampe in terminale nel momento di fallita; per test piu' complicati invece vi e' presente la scrittura di messaggi in caso di fail
- I javadoc di "ListAdapte.java" e "ListIterator.java" sono stati presi dalla documentazione qui presente: https://www2.cs.duke.edu/csed/java/jdk1.4.2/docs/api/index.html . Tuttavia alcune eccezioni non sono lanciate poich

Autore
[Alberto Bortoletto 2101761]

Licenza
[Inserire qui la licenza, ad esempio MIT, Apache 2.0, o "Proprietary" se non open source. Se non specificato, si può omettere o indicare "All rights reserved".]

JUnit version used: 4.12
Hamcrest version used: 1.3
JAR files included in the JUnit/ directory:
- junit-4.12.jar
- hamcrest-core-1.3.jar

All tests can be compiled and run from command line using these libraries.
No build tools like Maven or Gradle were used.


COMANDO PER COMPILARE I TEST:
javac -cp "JUnit\junit-4.13.2.jar;JUnit\hamcrest-core-1.3.jar" -d bin myAdapter\*.java myTest\*.java myExceptions\*.java

COMANDO PER ESEGUIRE I TEST:
java -cp "bin;JUnit\junit-4.13.2.jar;JUnit\hamcrest-core-1.3.jar" myTest.TestRunner

COMANDO PER IL JAVADOC:
javadoc -d doc -cp ".;JUnit\junit-4.13.2.jar;hamcrest-core-1.3.jar" -sourcepath . myAdapter myExceptions myTest

COMANDO PER COMPILARE SOLO IL MAIN:
javac -d bin .\myAdapter\*.java .\myExceptions\*.java

COMANDO PER ESEGUIRE SOLO IL MAIN:
java .\myAdapter\MainProva.java

Ricordati di scrivere i test case (description) 
