package myTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import myAdapter.*;

/**
 * <b>Summary:</b>
 * <p>
 * Questa classe contiene una suite esaustiva di test per la classe interna {@code myAdapter.ListAdapter.SubList}.
 * Sono presenti test per tutti i metodi principali della sottolista: size, isEmpty, get, set, add, remove, contains, containsAll, addAll, removeAll, retainAll, clear, indexOf, lastIndexOf, e per la propagazione delle modifiche tra sottolista e lista padre.
 * Vengono testati sia i casi standard che i casi limite (sottolista vuota, indici fuori limite, elementi null, propagazione delle modifiche, eccezioni).
 * <br>
 * <b>Test Case Design:</b>
 * <p>
 * La motivazione di questa suite è assicurare che la SubList si comporti come una vera vista sulla lista padre,
 * riflettendo e propagando correttamente tutte le modifiche, e che rispetti i vincoli sugli indici e la gestione degli errori.
 * Si verifica la coerenza tra sottolista e lista padre, la corretta gestione di elementi null, la robustezza contro input errati,
 * e la conformità alle specifiche delle interfacce Java Collections.
 * La divisione tra test di accesso, modifica e interazione con la lista padre permette di coprire tutti i possibili scenari d'uso e di errore.
 */
public class TestSubListAdapter 
{

    private ListAdapter parentList;
    private HList subList; // SubList [parent index 1, parent index 2, parent index 3] = ["uno", "due", "tre"]

    /**
     * Costruttore predefinito per i test di {@code TestSubListAdapter}.
     * Non esegue inizializzazioni specifiche, affidandosi al metodo {@code setup()}.
     */
    public TestSubListAdapter() 
    {
        // Nessuna logica di inizializzazione complessa qui, JUnit si occupa del setup.
    }

    /**
     * Configura l'ambiente di test prima di ogni metodo di test.
     * Inizializza una `ListAdapter` popolata e crea una `SubList` su di essa
     * per i test. La `SubList` sarà inizialmente [1, 2, 3].
     * <p>
     * Lista Padre Iniziale: ["zero", "uno", "due", "tre", "quattro", "cinque"]
     * SubList Iniziale (indici della parentList da 1 a 4 esclusi): ["uno", "due", "tre"]
     */
    @Before
    public void setUp() 
    {
        parentList = new ListAdapter();
        parentList.add("zero");    // Index 0
        parentList.add("uno");     // Index 1 (inizio subList)
        parentList.add("due");     // Index 2
        parentList.add("tre");     // Index 3 (fine subList esclusa, 4)
        parentList.add("quattro"); // Index 4
        parentList.add("cinque");  // Index 5

        // Creiamo una sublist da index 1 (inclusivo) a 4 (esclusivo) della parentList
        // Corrisponde agli elementi "uno", "due", "tre" della parentList.
        subList = parentList.subList(1, 4);
    }

    // ------- TEST DEI METODI DI ACCESSO (NON MODIFICANO LA LISTA) --------

    /**
     * Test del metodo {@link HList#size()}.
     * <p>
     * Summary: Il test verifica il metodo {@code size()} di una sottolista non vuota.
     * <p>
     * Test Case Design: Assicurarsi che la `SubList` calcoli correttamente la sua dimensione,
     * che dovrebbe corrispondere al numero di elementi tra `fromIndex` e `toIndex` (escluso `toIndex`).
     * <p>
     * Test Description: Verifica che la sottolista, creata dagli indici [1, 4) della lista padre,
     * abbia una dimensione pari a 3 (4 - 1 = 3) chiamando il metodo {@code size()}.
     * <p>
     * Preconditions: La sottolista è inizializzata per contenere i 3 elementi della lista padre,
     * ovvero ["uno", "due", "tre"].
     * <p>
     * Postconditions: La sottolista rimane invariata.
     * <p>
     * Expected Result: {@code size()} deve restituire 3.
     */
    @Test
    public void testInitialSubListSize()
    {
        assertEquals(3, subList.size()); // "uno", "due", "tre"
    }

    /**
     * Test del metodo {@link HList#isEmpty()}.
     * <p>
     * Summary: Il test verifica il metodo {@code isEmpty()} di una sottolista non vuota.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è garantire che `isEmpty()` rifletta correttamente
     * lo stato di non-vuoto della sottolista, restituendo `false` quando contiene elementi.
     * <p>
     * Test Description: Verifica che la sottolista, che contiene gli elementi ["uno", "due", "tre"],
     * restituisca `false` quando viene chiamato il metodo {@code isEmpty()}.
     * <p>
     * Preconditions: La sottolista è inizializzata con 3 elementi, ovvero ["uno", "due", "tre"].
     * <p>
     * Postconditions: La sottolista rimane invariata.
     * <p>
     * Expected Result: {@code isEmpty()} deve restituire false.
     */
    @Test
    public void testIsEmptyFalse()
    {
        assertFalse(subList.isEmpty());
    }

    /**
     * Test del metodo {@link HList#isEmpty()}.
     * <p>
     * Sumary: Verifica che una sottolista creata vuota o svuotata sia correttamente considerata vuota.
     * <p>
     * Test Case Design: La motivazione dietro a questo test e' assicurarsi che `isEmpty()` restituisca true quando 
     * la sottolista non contiene elementi.
     * <p>
     * Test Description: Viene creata una sottolista vuota (dimensione nulla) tramite la chiamata del metodo subList(1,1) 
     * e si verifica che `isEmpty()` restituisca true.
     * Successivamente si svuota la sottolista esistente e si verifica nuovamente `isEmpty()`.
     * <p>
     * Preconditions: Una sottolista esistente con 3 elementi ossia ["uno", "due", "tre"] e un altra sottolista vuota creata con subList(1,1).
     * <p>
     * Postconditions: Sia la sottolista vuota che quella esistente sono considerate vuote.
     * <p>
     * Expected Result: {@code isEmpty()} deve restituire true da entrambi .
     */
    @Test
    public void testIsEmptyTrue() 
    {
        HList emptySubList = parentList.subList(1, 1); // SubList vuota
        assertTrue(emptySubList.isEmpty());

        subList.clear(); // Svuota la subList esistente
        assertTrue(subList.isEmpty());
    }

    /**
     * Test del metodo {@link HList#get(int)}.
     * <p>
     * Summary: Verifica il recupero di un elemento all'inizio della sottolista.
     * <p>
     * Test Case Design: Il motivo dietro a questo test e' garantire che il metodo `get(int index)` recuperi l'elemento iniziale della sottolista 
     * <p>
     * Test Description: Verifica che il primo elemento della sottolista sia "uno" (indice 0).
     * <p>
     * Preconditions: La sottolista contiene ["uno", "due", "tre"].
     * <p>
     * Postconditions: La sottolista contiene ancora gli stessi elementi.
     * <p>
     * Expected Result: {@code get(0)} deve restituire "uno".
     */
    @Test
    public void testGetFirstElement() 
    {
        assertEquals("uno", subList.get(0));
    }

    /**
     * Test del metodo {@link HList#get(int)}.
     * <p>
     * Summary: Il test verifica che il metodo {@code get(int index)} lanci {@code IndexOutOfBoundsException}
     * quando si tenta di recuperare un elemento specificando un indice negativo.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare la completa conformità
     * alla specifica della Javadoc di `java.util.List.get()`, che impone il lancio di una
     * `IndexOutOfBoundsException` per qualsiasi indice che non sia compreso tra 0 (incluso) e `size()` (escluso).
     * Questo caso specifico si concentra sulla violazione del limite inferiore dell'indice.
     * <p>
     * Test Description: 1) Si tenta di accedere a un elemento della sottolista tramite il metodo `get()`.
     *                   2) Viene fornito un indice intenzionalmente negativo (`-1`).
     *                   3) Il test si aspetta che, come risultato di questa operazione, venga lanciata una {@code IndexOutOfBoundsException}.
     * <p>
     * Preconditions: La sottolista è stata inizializzata e contiene:
     * ["uno", "due", "tre"].
     * <p>
     * Postconditions: La sottolista rimane completamente invariata, poiché l'operazione di recupero
     * non è stata completata con successo a causa dell'eccezione lanciata.
     * <p>
     * Expected Result: Una {@code IndexOutOfBoundsException} deve essere lanciata quando si chiama `get(-1)`.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetNegativeIndex()
    {
        subList.get(-1);
    }

    /**
     * Test del metodo {@link HList#get(int)}.
     * <p>
     * Summary: Il test verifica che il metodo {@code get(int index)} lanci {@code IndexOutOfBoundsException}
     * quando si tenta di recuperare un elemento specificando un indice che è fuori dai limiti validi
     * della sottolista (ovvero, maggiore o uguale alla dimensione della sottolista).
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare la completa conformità
     *  di {@code get(int index)} che impone il lancio di una
     * `IndexOutOfBoundsException` per qualsiasi indice non valido. Questo caso specifico si concentra
     * sulla violazione del limite superiore dell'indice.
     * <p>
     * Test Description: 1) Si tenta di accedere a un elemento della sottolista tramite il metodo `get()`.
     *                   2) Viene fornito un indice intenzionalmente fuori limite, pari alla dimensione corrente della sottolista (`3`).
     *                   3) Il test si aspetta che, come risultato di questa operazione,
     *                      venga lanciata una {@code IndexOutOfBoundsException}.
     * <p>
     * Preconditions: La sottolista è stata inizializzata e contiene gli elementi ["uno", "due", "tre"],
     * pertanto la sua dimensione è 3.
     * <p>
     * Postconditions: La sottolista rimane completamente invariata, poiché l'operazione di recupero
     * non è stata completata con successo a causa dell'eccezione lanciata.
     * <p>
     * Expected Result: Una {@code IndexOutOfBoundsException} deve essere lanciata quando si chiama `get(3)`.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetOutOfBoundsIndex()
    {
        subList.get(3);
    }

    /**
     * Test del metodo {@link HList#indexOf(Object)}.
     * <p>
     * Summary: Il test verifica che il metodo {@code indexOf(Object o)} restituisca correttamente
     * la posizione (indice) della prima occorrenza di un elemento esistente all'interno della sottolista.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare che `indexOf()` calcoli
     * l'indice relativo corretto di un elemento che è presente nella vista della sottolista.
     * <p>
     * Test Description: 1) Si cerca l'elemento "due" all'interno della sottolista.
     *                   2) La sottolista è stata creata con elementi che la rendono una vista valida
     *                      della lista padre, mantenendo gli indici relativi.
     *                   3) Il test si aspetta che l'indice restituito sia 1, poiché "due" è il secondo elemento
     * nella sequenza della sottolista (["uno", "due", "tre"]).
     * <p>
     * Preconditions: La sottolista è stata inizializzata e contiene gli elementi ["uno", "due", "tre"].
     * <p>
     * Postconditions: La sottolista rimane invariata; l'operazione di ricerca non modifica lo stato della lista.
     * <p>
     * Expected Result: {@code indexOf("due")} deve restituire 1.
     */
    @Test
    public void testIndexOfExistingElement()
    {
        assertEquals(1, subList.indexOf("due"));
    }

    /**
     * Test del metodo {@link HList#indexOf(Object)}.
     * <p>
     * Summary: Il test verifica che il metodo {@code indexOf(Object o)} restituisca -1
     * quando l'elemento cercato non è presente all'interno della vista definita dalla sottolista,
     * anche se tale elemento potrebbe esistere nella lista padre al di fuori del range della sottolista.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è garantire che la logica di `indexOf()`
     * sia correttamente implementata per operare esclusivamente sulla porzione di lista visibile alla sottolista,
     * senza "vedere" elementi al di fuori dei suoi indici `fromIndex` e `toIndex`.
     * <p>
     * Test Description: 1) Si tenta di cercare l'elemento "quattro" all'interno della `subList`.
     *                   2) La `subList` è stata creata per contenere gli elementi ["uno", "due", "tre"].
     *                   3) L'elemento "quattro" è presente nella lista padre, ma ad un indice (es. 4) che ricade
     *                      al di fuori del range della `subList`.
     *                   4) Il test si aspetta che `indexOf("quattro")` restituisca `-1`, indicando che l'elemento
     *                  non è stato trovato *all'interno della sottolista*.
     * <p>
     * Preconditions: La sottolista è stata inizializzata e contiene gli elementi ["uno", "due", "tre"].
     * La lista padre dalla quale è derivata la sottolista contiene l'elemento "quattro" in una posizione
     * non inclusa nel range della sottolista (es. `parentList.get(4)` restituisce "quattro").
     * <p>
     * Postconditions: La sottolista e la lista padre rimangono invariate; l'operazione di ricerca
     * non modifica il loro stato.
     * <p>
     * Expected Result: {@code indexOf("quattro")} deve restituire -1.
     */
    @Test
    public void testIndexOfNonExistingElementInSubList()
    {
        assertEquals(-1, subList.indexOf("quattro"));
    }

    /**
     * Test del metodo {@link HList#indexOf(Object)}.
     * <p>
     * Summary: Il test verifica che il metodo {@code indexOf(Object o)} sia in grado di trovare
     * correttamente la posizione (indice) della prima occorrenza di un elemento `null` all'interno della sottolista.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare che l'implementazione di `indexOf()`
     * gestisca correttamente la ricerca di valori `null`.
     * <p>
     * Test Description: 1) Un elemento `null` viene inserito nella `subList` ad una posizione specifica (indice 1).
     *                      La sottolista diventa ["uno", `null`, "due", "tre"].
     *                   2) Si invoca il metodo `indexOf(null)` sulla sottolista modificata.
     *                   3) Il test si aspetta che l'indice restituito sia 1, corrispondente alla posizione dove è stato inserito il `null`.
     * <p>
     * Preconditions: La sottolista è stata inizializzata e contiene gli elementi ["uno", "due", "tre"].
     * Un elemento `null` viene aggiunto alla sottolista prima della chiamata a `indexOf()`.
     * <p>
     * Postconditions: L'indice del primo elemento `null` nella sottolista è stato trovato correttamente.
     * La sottolista contiene ora un elemento `null` alla posizione 1.
     * <p>
     * Expected Result: {@code indexOf(null)} deve restituire 1.
     */
    @Test
    public void testIndexOfNullElement()
    {
        subList.add(1, null); // subList: ["uno", null, "due", "tre"]
        assertEquals(1, subList.indexOf(null));
    }

    /**
     * Test del metodo {@link myAdapter.HList#lastIndexOf(Object)}.
     * <p>
     * Summary: Il test verifica che il metodo {@code lastIndexOf(Object o)} restituisca correttamente l'indice dell'ultima occorrenza
     * dell'elemento specificato all'interno della sottolista. Questo test si concentra sul caso in cui l'elemento è presente più volte.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è garantire che {@code lastIndexOf()} gestisca correttamente gli elementi
     * duplicati e non si fermi alla prima occorrenza, ma trovi effettivamente l'ultima posizione dell'elemento nella sottolista.
     * Questo test convalida tale comportamento.
     * <p>
     * Test Description: 1) Si modifica la `subList` aggiungendo l'elemento "uno" alla fine, in modo che contenga
     *                      due occorrenze di "uno" (es. `["uno", "due", "tre", "uno"]`).
     *                   2) Si invoca il metodo {@code lastIndexOf("uno")} sulla `subList` modificata.
     *                   3) Il test si aspetta che l'indice restituito sia {@code 3}, che corrisponde alla posizione dell'ultima
     *                      occorrenza di "uno" nella sottolista.
     * <p>
     * Preconditions: Una {@code HList} (sottolista) è inizializzata e contiene gli elementi `["uno", "due", "tre"]`.
     * L'elemento "uno" viene aggiunto alla fine della sottolista prima della chiamata a {@code lastIndexOf()},
     * rendendola `["uno", "due", "tre", "uno"]`.
     * <p>
     * Postconditions: Lo stato della `subList` rimane invariato dopo l'esecuzione del test, poiché {@code lastIndexOf()}
     * è un'operazione di query e non modifica la lista.
     * <p>
     * Expected Result: La chiamata a {@code subList.lastIndexOf("uno")} deve restituire {@code 3}.
     */
    @Test
    public void testLastIndexOfExistingElement() 
    {
        subList.add("uno"); // subList diventa ["uno", "due", "tre", "uno"]
        assertEquals(3, subList.lastIndexOf("uno"));
    }

    /**
     * Test del metodo {@link HList#contains(Object)}.
     * <p>
     * Summary: Il test verifica che il metodo {@code contains(Object o)} restituisca {@code true}
     * quando l'elemento specificato è presente all'interno della vista definita dalla sottolista.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare che {@code contains()}
     * sia correttamente implementato per cercare l'elemento solo ed esclusivamente
     * all'interno del proprio range di indici (`fromIndex` e `toIndex`), delegando
     * la ricerca in modo efficace alla lista padre ma rispettando i propri confini.
     * <p>
     * Test Description: 1) Si invoca il metodo {@code contains()} sulla `subList`, passando come argomento
     *                      un elemento ("due") che è noto essere presente all'interno del range di elementi della sottolista.
     *                   2) La sottolista è stata inizializzata con gli elementi ["uno", "due", "tre"].
     *                   3) Il test si aspetta che {@code contains("due")} restituisca {@code true}, confermando
     *                      la corretta rilevazione dell'elemento presente.
     * <p>
     * Preconditions: La sottolista è stata inizializzata e contiene gli elementi ["uno", "due", "tre"].
     * <p>
     * Postconditions: La sottolista rimane invariata; l'operazione di ricerca non modifica lo stato della lista.
     * <p>
     * Expected Result: {@code contains("due")} deve restituire {@code true}.
     */
    @Test
    public void testContainsExistingElement()
    {
        assertTrue(subList.contains("due"));
    }

    /**
     * Test del metodo {@link HList#contains(Object)}.
     * <p>
     * Summary: Il test verifica che il metodo {@code contains(Object o)} restituisca {@code false}
     * quando l'elemento specificato non è presente all'interno della vista definita dalla sottolista.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare che {@code contains()}
     * non produca "falsi positivi" e che sia in grado di determinare correttamente l'assenza di un elemento,
     * sia che l'elemento non esista affatto nella lista padre, sia che esista ma fuori dal range della sottolista.
     * <p>
     * Test Description: 1) Si invoca il metodo {@code contains()} sulla `subList`, passando come argomento
     *                      un elemento ("sette") che è noto non essere presente all'interno del range di elementi della sottolista,
     *                      né nella lista padre.
     *                   2) La sottolista è stata inizializzata con gli elementi ["uno", "due", "tre"].
     *                   3) Il test si aspetta che {@code contains("sette")} restituisca {@code false}, confermando
     *                      la corretta rilevazione dell'assenza dell'elemento.
     * <p>
     * Preconditions: La sottolista è stata inizializzata e contiene gli elementi ["uno", "due", "tre"].
     * L'elemento "sette" non è presente né nella sottolista né nella lista padre.
     * <p>
     * Postconditions: La sottolista rimane invariata; l'operazione di ricerca non modifica lo stato della lista.
     * <p>
     * Expected Result: {@code contains("sette")} deve restituire {@code false}.
     */
    @Test
    public void testContainsNonExistingElement()
    {
        assertFalse(subList.contains("sette"));
    }

    /**
     * Test del metodo {@link HList#containsAll(HCollection)}.
     * <p>
     * Summary: Il test verifica che il metodo {@code containsAll(HCollection c)} restituisca {@code true}
     * quando tutti gli elementi della collezione specificata sono presenti all'interno della sottolista.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare che {@code containsAll()}
     * sia correttamente implementato per iterare sugli elementi della collezione fornita e verificare
     * la loro presenza individuale all'interno della vista della sottolista. Questo convalida che
     * la sottolista riconosca un sottoinsieme di elementi che possiede.
     * <p>
     * Test Description: 1) Viene creata una nuova collezione (`otherCollection`) e popolata con
     *                      elementi ("uno", "tre") che sono noti essere presenti nella `subList`.
     *                   2) Si invoca il metodo {@code containsAll()} sulla `subList`, passando `otherCollection` come argomento.
     *                   3) Il test si aspetta che {@code containsAll(otherCollection)} restituisca {@code true},
     *                      confermando che tutti gli elementi di `otherCollection` sono effettivamente contenuti nella `subList`.
     * <p>
     * Preconditions: La sottolista è stata inizializzata e contiene gli elementi ["uno", "due", "tre"].
     * La `otherCollection` viene creata e contiene gli elementi ["uno", "tre"].
     * <p>
     * Postconditions: La sottolista e la `otherCollection` rimangono invariate; l'operazione di query
     * non modifica il loro stato.
     * <p>
     * Expected Result: {@code containsAll} deve restituire {@code true}.
     */
    @Test
    public void testContainsAllExisting()
    {
        ListAdapter otherCollection = new ListAdapter();
        otherCollection.add("uno");
        otherCollection.add("tre");
        assertTrue(subList.containsAll(otherCollection));
    }

    /**
     * Test del metodo {@link HList#containsAll(HCollection)}.
     * <p>
     * Summary: Il test verifica che il metodo {@code containsAll(HCollection c)} restituisca {@code false}
     * quando almeno un elemento della collezione specificata non è presente all'interno della sottolista.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare che {@code containsAll()}
     * funzioni correttamente anche in presenza di elementi non contenuti nella sottolista.
     * Si verifica che l'operazione restituisca {@code false} non appena trova un singolo elemento
     * della collezione fornita che non è presente nella sottolista, in conformità con la logica
     * del "tutti gli elementi".
     * <p>
     * Test Description: 1) Viene creata una nuova collezione (`otherCollection`) e popolata con
     *                      elementi ("uno", "sette"), dove "uno" è presente nella `subList` ma "sette" non lo è.
     *                   2) Si invoca il metodo {@code containsAll()} sulla `subList`, passando `otherCollection` come argomento.
     *                   3) Il test si aspetta che {@code containsAll(otherCollection)} restituisca {@code false},
     *                      poiché "sette" non è un elemento della `subList`.
     * <p>
     * Preconditions: La sottolista è stata inizializzata e contiene gli elementi ["uno", "due", "tre"].
     * La `otherCollection` viene creata e contiene gli elementi ["uno", "sette"].
     * <p>
     * Postconditions: La sottolista e la `otherCollection` rimangono invariate; l'operazione di query
     * non modifica il loro stato.
     * <p>
     * Expected Result: {@code containsAll} deve restituire {@code false}.
     */
    @Test
    public void testContainsAllNotExisting()
    {
        ListAdapter otherCollection = new ListAdapter();
        otherCollection.add("uno");
        otherCollection.add("sette"); // Elemento non presente nella subList
        assertFalse(subList.containsAll(otherCollection));
    }

    /**
     * Test del metodo {@link HList#containsAll(HCollection)}.
     * <p>
     * Summary: Il test verifica che il metodo {@code containsAll(HCollection c)} restituisca {@code true}
     * quando la collezione specificata come argomento è vuota, indipendentemente dal contenuto della sottolista.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare che {@code containsAll()}
     * gestisca correttamente il caso limite di una collezione vuota. Per definizione,
     * una collezione (o lista) contiene "tutti" gli elementi di una collezione vuota,
     * quindi il risultato atteso è sempre {@code true}.
     * <p>
     * Test Description: 1) Viene creata una nuova collezione (`emptyCollection`) che non contiene alcun elemento.
     *                   2) Si invoca il metodo {@code containsAll()} sulla `subList` (che contiene ["uno", "due", "tre"]),
     *                      passando `emptyCollection` come argomento.
     *                   3) Il test si aspetta che {@code containsAll(emptyCollection)} restituisca {@code true}.
     * <p>
     * Preconditions: La sottolista è stata inizializzata e contiene gli elementi ["uno", "due", "tre"].
     * La `emptyCollection` viene creata e rimane vuota.
     * <p>
     * Postconditions: La sottolista e la `emptyCollection` rimangono invariate; l'operazione di query
     * non modifica il loro stato.
     * <p>
     * Expected Result: {@code containsAll(emptyCollection)} deve restituire {@code true}.
     */
    @Test
    public void testContainsAllEmptyCollection()
    {
        ListAdapter emptyCollection = new ListAdapter();
        assertTrue(subList.containsAll(emptyCollection));
    }

    /**
     * Test del metodo {@link HList#containsAll(HCollection)}.
     * <p>
     * Summary: Il test verifica che il metodo {@code containsAll(HCollection c)} lanci
     * una {@code NullPointerException} quando la collezione specificata come argomento è `null`.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare la completa conformità
     * alla specifica di containsAll(), che impone il lancio di una
     * {@code NullPointerException} se la collezione fornita è un riferimento `null`. Questo test
     * convalida il comportamento atteso per un input non valido.
     * <p>
     * Test Description: 1) Si invoca il metodo {@code containsAll()} sulla `subList`.
     *                   2) Viene passato `null` come argomento per la collezione.
     *                   3) Il test si aspetta che, come risultato di questa operazione,
     *                      venga lanciata una {@code NullPointerException}.
     * <p>
     * Preconditions: La sottolista è stata inizializzata e contiene gli elementi ["uno", "due", "tre"].
     * L'argomento passato a {@code containsAll()} è `null`.
     * <p>
     * Postconditions: La sottolista rimane invariata, poiché l'operazione non è completata con successo
     * a causa dell'eccezione lanciata.
     * <p>
     * Expected Result: Una {@code NullPointerException} deve essere lanciata quando si chiama {@code containsAll(null)}.
     */
    @Test(expected = NullPointerException.class)
    public void testContainsAllNullCollection()
    {
        subList.containsAll(null);
    }

    // ------- TEST DEI METODI DI MODIFICA (MUTATORI) --------

    /**
     * Test del metodo {@link HList#add(Object)}.
     * <p>
     * Summary: Il test verifica l'aggiunta di un elemento alla fine della sottolista,
     * assicurando che tale operazione si rifletta correttamente sia sulla dimensione della sottolista
     * che sulla struttura e il contenuto della lista padre sottostante.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare che {@code add(Object o)}
     * sulla sottolista non solo aggiunga l'elemento alla fine della vista della sottolista,
     * ma che deleghi correttamente l'inserimento alla lista padre nella posizione corrispondente,
     * spostando di conseguenza gli elementi successivi nella lista padre e aggiornando la dimensione della sottolista.
     * <p>
     * Test Description: 1) Si verifica la dimensione iniziale della `subList` (attesa: 3).
     *                   2) Viene aggiunto un nuovo elemento ("nuovo") alla `subList` tramite il metodo {@code add()}.
     *                   3) Si verifica che il metodo {@code add()} restituisca {@code true}.
     *                   4) Si verifica che la dimensione della `subList` sia aumentata correttamente (attesa: 4).
     *                   5) Si assicura che l'elemento appena aggiunto sia effettivamente presente alla fine della `subList`
     *                      (indice 3 della `subList`).
     *                   6) Si verifica che l'elemento sia stato inserito nella posizione corretta all'interno della `parentList`
     *                      (indice 4 della `parentList`, che corrisponde all'offset iniziale della `subList` più la sua nuova dimensione).
     *                   7) Si verifica che gli elementi originali della `parentList` che seguivano la `subList` siano stati
     *                      correttamente spostati in avanti (ad esempio, "quattro" si trova ora all'indice 5 della `parentList`).
     * <p>
     * Preconditions: La sottolista è inizializzata e contiene gli elementi ["uno", "due", "tre"].
     * La lista padre è inizializzata e contiene gli elementi ["zero", "uno", "due", "tre", "quattro", "cinque"].
     * <p>
     * Postconditions: La sottolista è modificata e ora contiene gli elementi ["uno", "due", "tre", "nuovo"].
     * La lista padre è modificata e ora contiene gli elementi ["zero", "uno", "due", "tre", "nuovo", "quattro", "cinque"].
     * La dimensione della sottolista è aumentata di uno.
     * <p>
     * Expected Result: {@code add("nuovo")} deve restituire {@code true}.
     * La dimensione della sottolista deve essere {@code 4}.
     * L'elemento "nuovo" deve essere all'indice {@code 3} della sottolista e all'indice {@code 4} della lista padre.
     * L'elemento "quattro" deve essere all'indice {@code 5} della lista padre.
     */
    @Test
    public void testAddElementToSubList()
    {
        assertEquals(3, subList.size());
        assertTrue(subList.add("nuovo"));
        assertEquals(4, subList.size());
        assertEquals("nuovo", subList.get(3)); // L'elemento è alla fine della sublist
        assertEquals("nuovo", parentList.get(4)); // L'elemento è nella parentList all'offset+size
        assertEquals("quattro", parentList.get(5)); // Elementi successivi spostati
    }

    /**
     * Test del metodo {@link HList#add(int, Object)}.
     * <p>
     * Summary: Il test verifica l'aggiunta di un elemento in una posizione specifica (indice) all'interno della sottolista,
     * assicurando che tale operazione sposti correttamente gli elementi esistenti e mantenga la coerenza
     * tra la sottolista e la lista padre sottostante.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è garantire che {@code add(int index, Object element)}
     * sulla sottolista funzioni come previsto per gli inserimenti in posizioni intermedie.
     * Ciò implica che l'elemento venga inserito all'indice specificato della sottolista,
     * che gli elementi successivi nella sottolista (e di conseguenza nella lista padre) vengano spostati in avanti,
     * e che la dimensione della sottolista si aggiorni correttamente.
     * <p>
     * Test Description: 1) Si verifica la dimensione iniziale della `subList` (attesa: 3).
     *                   2) Viene aggiunto un nuovo elemento ("inserito") alla `subList` tramite il metodo {@code add(1, "inserito")},
     *                      inserendolo all'indice 1 della `subList`.
     *                   3) Si verifica che la dimensione della `subList` sia aumentata correttamente (attesa: 4).
     *                   4) Si assicura che gli elementi della `subList` siano ora nell'ordine corretto: "uno" all'indice 0,
     *                      "inserito" all'indice 1, "due" all'indice 2 e "tre" all'indice 3.
     *                   5) Si verifica anche che l'inserimento si sia propagato correttamente alla `parentList`,
     *                      controllando le posizioni degli elementi corrispondenti agli indici della `subList` e quelli successivi.
     * <p>
     * Preconditions: La sottolista è inizializzata e contiene gli elementi ["uno", "due", "tre"].
     * La lista padre è inizializzata e contiene gli elementi ["zero", "uno", "due", "tre", "quattro", "cinque"].
     * <p>
     * Postconditions: La sottolista è modificata e ora contiene gli elementi ["uno", "inserito", "due", "tre"].
     * La lista padre è aggiornata di conseguenza, con l'elemento "inserito" nella posizione corretta
     * e gli elementi successivi spostati.
     * La dimensione della sottolista è aumentata di uno.
     * <p>
     * Expected Result: L'elemento "inserito" deve essere aggiunto correttamente alla posizione specificata.
     * La dimensione della sottolista deve essere aggiornata a 4.
     * Tutti gli elementi devono mantenere le loro posizioni relative corrette sia nella sottolista che nella lista padre.
     */
    @Test
    public void testAddElementAtIndexSubList()
    {
        assertEquals(3, subList.size());
        subList.add(1, "inserito"); // Inserisce in posizione 1 della sublist
        assertEquals(4, subList.size());
        assertEquals("uno", subList.get(0));
        assertEquals("inserito", subList.get(1));
        assertEquals("due", subList.get(2));
        assertEquals("tre", subList.get(3));

        // Verifica anche sulla parentList
        assertEquals("uno", parentList.get(1));     // original index 1
        assertEquals("inserito", parentList.get(2)); // original index 2
        assertEquals("due", parentList.get(3));     // original index 3
        assertEquals("tre", parentList.get(4));     // original index 4
        assertEquals("quattro", parentList.get(5)); // original index 5
    }

    /**
     * Test del metodo {@link HList#add(int, Object)}.
     * <p>
     * Summary: Il test verifica che il metodo {@code add(int index, Object element)} lanci
     * una {@code IndexOutOfBoundsException} quando si tenta di aggiungere un elemento
     * specificando un indice negativo.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare la completa conformità di add(int, Object), che impone il lancio di una
     * {@code IndexOutOfBoundsException} per qualsiasi indice che non sia compreso tra 0 (incluso) e `size()` (incluso).
     * Questo caso specifico si concentra sulla violazione del limite inferiore dell'indice.
     * <p>
     * Test Description: 1) Si tenta di aggiungere un nuovo elemento ("test") alla `subList`
     *                      tramite il metodo {@code add(int, Object)}.
     *                   2) Viene fornito un indice intenzionalmente negativo (`-1`).
     *                   3) Il test si aspetta che, come risultato di questa operazione,
     *                      venga lanciata una {@code IndexOutOfBoundsException}.
     * <p>
     * Preconditions: La sottolista è stata inizializzata e contiene elementi (ad esempio, ["uno", "due", "tre"]).
     * <p>
     * Postconditions: La sottolista rimane invariata, poiché l'operazione di aggiunta non è stata
     * completata con successo a causa dell'eccezione lanciata.
     * <p>
     * Expected Result: Una {@code IndexOutOfBoundsException} deve essere lanciata quando si chiama {@code add(-1, "test")}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddNegativeIndex()
    {
        subList.add(-1, "test");
    }

    /**
     * Test del metodo {@link HList#add(int, Object)}.
     * <p>
     * Summary: Il test verifica che il metodo {@code add(int index, Object element)} lanci
     * una {@code IndexOutOfBoundsException} quando si tenta di aggiungere un elemento
     * specificando un indice che è al di fuori dei limiti validi (ovvero, maggiore della sua dimensione).
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare la completa conformità di add(int, Object)`, che impone il lancio di una
     * {@code IndexOutOfBoundsException} per qualsiasi indice che non sia compreso tra 0 (incluso) e `size()` (incluso).
     * Questo caso specifico si concentra sulla violazione del limite superiore dell'indice.
     * <p>
     * Test Description: 1) Si tenta di aggiungere un nuovo elemento ("test") alla `subList`
     *                      tramite il metodo {@code add(int, Object)}.
     *                   2) Viene fornito un indice intenzionalmente fuori limite, pari a {@code 4}, mentre la dimensione
     *                      della sottolista è {@code 3} (gli indici validi per l'aggiunta sono da {@code 0} a {@code 3}).
     *                   3) Il test si aspetta che, come risultato di questa operazione,
     *                      venga lanciata una {@code IndexOutOfBoundsException}.
     * <p>
     * Preconditions: La sottolista è stata inizializzata e contiene 3 elementi (ad esempio, ["uno", "due", "tre"]).
     * <p>
     * Postconditions: La sottolista rimane invariata, poiché l'operazione di aggiunta non è stata
     * completata con successo a causa dell'eccezione lanciata.
     * <p>
     * Expected Result: Una {@code IndexOutOfBoundsException} deve essere lanciata quando si chiama {@code add(4, "test")}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddOutOfBoundsIndex()
    {
        subList.add(4, "test"); // size is 3, valid indices 0, 1, 2, 3 (for add at end)
    }

    /**
     * Test del metodo {@link HList#remove(int)}.
     * <p>
     * Summary: Il test verifica la rimozione di un elemento in una posizione specifica (indice) dalla sottolista,
     * assicurando che tale operazione si rifletta correttamente sia sulla dimensione della sottolista
     * che sulla struttura e il contenuto della lista padre sottostante.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è garantire che {@code remove(int index)}
     * sulla sottolista non solo rimuova l'elemento dalla vista della sottolista,
     * ma che deleghi correttamente la rimozione alla lista padre nella posizione corrispondente,
     * spostando di conseguenza gli elementi successivi nella lista padre e aggiornando la dimensione della sottolista.
     * <p>
     * Test Description: 1) Si verifica la dimensione iniziale della `subList` (attesa: 3).
     *                   2) Viene rimosso un elemento dalla `subList` tramite il metodo {@code remove(1)},
     *                      rimuovendo l'elemento all'indice 1 della `subList` (che è "due").
     *                   3) Si verifica che il metodo {@code remove()} restituisca l'elemento corretto ("due").
     *                   4) Si verifica che la dimensione della `subList` sia diminuita correttamente (attesa: 2).
     *                   5) Si assicura che gli elementi rimanenti nella `subList` siano ora nell'ordine corretto:
     *                      "uno" all'indice 0 e "tre" all'indice 1.
     *                   6) Si verifica anche che la rimozione si sia propagata correttamente alla `parentList`,
     *                      controllando le posizioni degli elementi che erano presenti e lo spostamento di quelli successivi.
     * <p>
     * Preconditions: La sottolista è inizializzata e contiene gli elementi ["uno", "due", "tre"].
     * La lista padre è inizializzata e contiene gli elementi ["zero", "uno", "due", "tre", "quattro", "cinque"].
     * <p>
     * Postconditions: La sottolista è modificata e ora contiene gli elementi ["uno", "tre"].
     * La lista padre è aggiornata di conseguenza, con l'elemento rimosso e gli elementi successivi spostati indietro.
     * La dimensione della sottolista è diminuita di uno.
     * <p>
     * Expected Result: L'elemento "due" deve essere rimosso correttamente.
     * La dimensione della sottolista deve essere aggiornata a 2.
     * Gli elementi rimanenti devono mantenere le loro posizioni relative corrette sia nella sottolista che nella lista padre.
     */
    @Test
    public void testRemoveElementByIndex()
    {
        assertEquals(3, subList.size());
        Object removed = subList.remove(1); // Rimuove "due" dalla sublist
        assertEquals("due", removed);
        assertEquals(2, subList.size());
        assertEquals("uno", subList.get(0));
        assertEquals("tre", subList.get(1));

        // Verifica anche sulla parentList
        assertEquals("zero", parentList.get(0));
        assertEquals("uno", parentList.get(1));
        assertEquals("tre", parentList.get(2)); // "due" è stato rimosso, "tre" si è spostato
        assertEquals("quattro", parentList.get(3));
    }

    /**
     * Test del metodo {@link HList#remove(int)}.
     * <p>
     * Summary: Il test verifica che il metodo {@code remove(int index)} lanci
     * una {@code IndexOutOfBoundsException} quando si tenta di rimuovere un elemento
     * specificando un indice negativo.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare la completa conformità
     * alla specifica di remove(int), che impone il lancio di una
     * {@code IndexOutOfBoundsException} per qualsiasi indice non compreso tra 0 (incluso) e `size()` (escluso).
     * Questo caso specifico si concentra sulla violazione del limite inferiore dell'indice.
     * <p>
     * Test Description: 1) Si tenta di rimuovere un elemento dalla `subList`
     *                      tramite il metodo {@code remove(int)}.
     *                   2) Viene fornito un indice intenzionalmente negativo (`-1`).
     *                   3) Il test si aspetta che, come risultato di questa operazione,
     *                      venga lanciata una {@code IndexOutOfBoundsException}.
     * <p>
     * Preconditions: La sottolista è stata inizializzata e contiene elementi (ad esempio, ["uno", "due", "tre"]).
     * <p>
     * Postconditions: La sottolista rimane invariata, poiché l'operazione di rimozione non è stata
     * completata con successo a causa dell'eccezione lanciata.
     * <p>
     * Expected Result: Una {@code IndexOutOfBoundsException} deve essere lanciata quando si chiama {@code remove(-1)}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveNegativeIndex()
    {
        subList.remove(-1);
    }

    /**
     * Test del metodo {@link HList#remove(int)}.
     * <p>
     * Summary: Il test verifica che il metodo {@code remove(int index)} lanci
     * una {@code IndexOutOfBoundsException} quando si tenta di rimuovere un elemento
     * specificando un indice che è al di fuori dei limiti validi (ovvero, maggiore o uguale alla dimensione).
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare la completa conformità
     * alla specifica di remove(int), che impone il lancio di una
     * {@code IndexOutOfBoundsException} per qualsiasi indice non compreso tra 0 (incluso) e `size()` (escluso).
     * Questo caso specifico si concentra sulla violazione del limite superiore dell'indice.
     * <p>
     * Test Description: 1) Si tenta di rimuovere un elemento dalla `subList`
     *                      tramite il metodo {@code remove(int)}.
     *                   2) Viene fornito un indice intenzionalmente fuori limite, pari a {@code 3},
     *                      mentre la dimensione della sottolista è {@code 3} (gli indici validi sono da {@code 0} a {@code 2}).
     *                   3) Il test si aspetta che, come risultato di questa operazione,
     *                      venga lanciata una {@code IndexOutOfBoundsException}.
     * <p>
     * Preconditions: La sottolista è stata inizializzata e contiene 3 elementi (ad esempio, ["uno", "due", "tre"]).
     * <p>
     * Postconditions: La sottolista rimane invariata, poiché l'operazione di rimozione non è stata
     * completata con successo a causa dell'eccezione lanciata.
     * <p>
     * Expected Result: Una {@code IndexOutOfBoundsException} deve essere lanciata quando si chiama {@code remove(3)}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveOutOfBoundsIndex()
    {
        subList.remove(3); 
    }

    /**
     * Test del metodo {@link HList#remove(Object)}.
     * <p>
     * Summary: Il test verifica la rimozione della prima occorrenza di un elemento specifico
     * dalla sottolista, assicurando che l'operazione sia correttamente riflessa
     * sia nella dimensione e nel contenuto della sottolista che nella lista padre sottostante.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è garantire che {@code remove(Object o)}
     * sulla sottolista trovi e rimuova correttamente la prima occorrenza dell'elemento specificato
     * all'interno del proprio range. Si vuole anche assicurare che tale rimozione
     * si propaghi correttamente alla lista padre, causando lo spostamento degli elementi successivi
     * e l'aggiornamento della dimensione della sottolista.
     * <p>
     * Test Description: 1) Si verifica la dimensione iniziale della `subList` (attesa: 3).
     *                   2) Si tenta di rimuovere l'elemento "due" dalla `subList` tramite il metodo {@code remove(Object)}.
     *                   3) Si verifica che il metodo {@code remove()} restituisca {@code true}, indicando che l'elemento è stato rimosso.
     *                   4) Si verifica che la dimensione della `subList` sia diminuita correttamente (attesa: 2).
     *                   5) Si assicura che gli elementi rimanenti nella `subList` siano ora nell'ordine corretto:
     *                      "uno" all'indice 0 e "tre" all'indice 1.
     *                   6) Si verifica anche che la rimozione si sia propagata correttamente alla `parentList`,
     *                      controllando le posizioni degli elementi e lo spostamento di quelli successivi.
     * <p>
     * Preconditions: La sottolista è inizializzata e contiene gli elementi ["uno", "due", "tre"].
     * La lista padre è inizializzata e contiene gli elementi ["zero", "uno", "due", "tre", "quattro", "cinque"].
     * <p>
     * Postconditions: La sottolista è modificata e ora contiene gli elementi ["uno", "tre"].
     * La lista padre è aggiornata di conseguenza, con l'elemento rimosso e gli elementi successivi spostati indietro.
     * La dimensione della sottolista è diminuita di uno.
     * <p>
     * Expected Result: {@code remove("due")} deve restituire {@code true}.
     * La dimensione della sottolista deve essere {@code 2}.
     * Gli elementi "uno" e "tre" devono essere rispettivamente agli indici {@code 0} e {@code 1} della sottolista.
     * La lista padre deve riflettere la rimozione di "due".
     */
    @Test
    public void testRemoveElementByObject()
    {
        assertEquals(3, subList.size());
        assertTrue(subList.remove("due")); // Rimuove "due" dalla sublist
        assertEquals(2, subList.size());
        assertEquals("uno", subList.get(0));
        assertEquals("tre", subList.get(1));

        // Verifica anche sulla parentList
        assertEquals("zero", parentList.get(0));
        assertEquals("uno", parentList.get(1));
        assertEquals("tre", parentList.get(2));
        assertEquals("quattro", parentList.get(3));
    }

    /**
     * Test del metodo {@link HList#remove(Object)}.
     * <p>
     * Summary: Il test verifica che il metodo {@code remove(Object o)} non modifichi
     * la sottolista (e di conseguenza la lista padre) e restituisca {@code false}
     * quando si tenta di rimuovere un elemento che non è presente nella sottolista.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare che {@code remove(Object o)}
     * gestisca correttamente il caso in cui l'elemento specificato non sia presente
     * all'interno del range della sottolista. Si vuole garantire che, in tale scenario,
     * il metodo non alteri lo stato della lista e restituisca il valore booleano appropriato (`false`).
     * <p>
     * Test Description: 1) Si verifica la dimensione iniziale della `subList` (attesa: 3).
     *                   2) Si tenta di rimuovere un elemento ("non_esiste") che non è presente nella `subList`
     *                      tramite il metodo {@code remove(Object)}.
     *                   3) Si verifica che il metodo {@code remove()} restituisca {@code false}, indicando che l'elemento
     *                      non è stato trovato e, di conseguenza, non è stato rimosso.
     *                   4) Si verifica che la dimensione della `subList` sia rimasta invariata (attesa: 3),
     *                      confermando che nessuna modifica è stata apportata alla lista.
     * <p>
     * Preconditions: La sottolista è inizializzata e contiene gli elementi ["uno", "due", "tre"].
     * L'elemento "non_esiste" non è presente nella sottolista né nella lista padre.
     * <p>
     * Postconditions: La sottolista rimane invariata; la sua dimensione e i suoi contenuti non sono stati modificati.
     * <p>
     * Expected Result: {@code remove("non_esiste")} deve restituire {@code false}.
     * La dimensione della sottolista non deve cambiare.
     */
    @Test
    public void testRemoveNonExistingElementByObject()
    {
        assertEquals(3, subList.size());
        assertFalse(subList.remove("non_esiste"));
        assertEquals(3, subList.size()); // Dimensione invariata
    }

    /**
     * Test del metodo {@link HList#remove(Object)}.
     * <p>
     * Summary: Il test verifica la rimozione di un elemento {@code null} dalla sottolista,
     * assicurando che l'operazione sia correttamente gestita e si rifletta sulla dimensione e sul contenuto.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è garantire che il metodo {@code remove(Object o)}
     * sia in grado di trovare e rimuovere correttamente un elemento {@code null} dalla sottolista.
     * Questo assicura la corretta gestione dei valori {@code null} all'interno delle operazioni della lista.
     * <p>
     * Test Description: 1) Un elemento {@code null} viene inserito nella `subList` ad una posizione specifica (indice 1),
     *                      trasformando la sottolista in `["uno", null, "due", "tre"]`.
     *                   2) Si verifica la dimensione della `subList` dopo l'aggiunta (attesa: 4).
     *                   3) Si tenta di rimuovere l'elemento {@code null} dalla `subList` tramite il metodo {@code remove(Object)}.
     *                   4) Si verifica che il metodo {@code remove()} restituisca {@code true}, indicando che l'elemento è stato rimosso.
     *                   5) Si verifica che la dimensione della `subList` sia diminuita correttamente (attesa: 3).
     *                   6) Si assicura che gli elementi rimanenti nella `subList` siano ora nell'ordine corretto:
     *                      "uno" all'indice 0, "due" all'indice 1 e "tre" all'indice 2.
     * <p>
     * Preconditions: La sottolista è stata inizializzata e contiene gli elementi `["uno", "due", "tre"]`.
     * Un elemento {@code null} viene aggiunto alla sottolista all'indice 1 prima della chiamata a {@code remove()}.
     * <p>
     * Postconditions: La sottolista è modificata e l'elemento {@code null} è stato rimosso.
     * La sua dimensione è diminuita di uno.
     * <p>
     * Expected Result: {@code remove(null)} deve restituire {@code true} e l'elemento {@code null} deve essere rimosso,
     * lasciando la sottolista come `["uno", "due", "tre"]`.
     */
    @Test
    public void testRemoveNullElementByObject()
    {
        subList.add(1, null); // subList: ["uno", null, "due", "tre"]
        assertEquals(4, subList.size());
        assertTrue(subList.remove(null));
        assertEquals(3, subList.size());
        assertEquals("uno", subList.get(0));
        assertEquals("due", subList.get(1));
        assertEquals("tre", subList.get(2));
    }

    /**
     * Test del metodo {@link HList#set(int, Object)}.
     * <p>
     * Summary: Il test verifica la modifica di un elemento esistente in una posizione specifica (indice)
     * all'interno della sottolista, assicurando che l'aggiornamento si rifletta correttamente
     * sia nella sottolista che nella lista padre sottostante.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è garantire che il metodo {@code set(int index, Object element)}
     * sulla sottolista aggiorni l'elemento all'indice specificato. Si vuole anche assicurare
     * che questa modifica venga propagata correttamente alla lista padre nella posizione corrispondente,
     * in modo che entrambe le viste (sottolista e lista padre) mantengano la coerenza.
     * <p>
     * Test Description: 1) Si verifica il valore iniziale dell'elemento all'indice 1 della `subList` (atteso: "due").
     *                   2) Si invoca il metodo {@code set()} sulla `subList`, modificando l'elemento all'indice 1
     *                      con il nuovo valore "modificato".
     *                   3) Si verifica che il metodo {@code set()} restituisca l'elemento precedentemente presente ("due").
     *                   4) Si assicura che l'elemento all'indice 1 della `subList` sia stato correttamente aggiornato a "modificato".
     *                   5) Infine, si verifica che la modifica sia stata correttamente applicata anche nella `parentList`,
     *                      controllando il valore all'indice corrispondente (offset della `subList` + indice nella `subList`).
     * <p>
     * Preconditions: La sottolista è stata inizializzata e contiene gli elementi ["uno", "due", "tre"].
     * La lista padre è stata inizializzata e contiene, ad esempio, ["zero", "uno", "due", "tre", "quattro", "cinque"],
     * dove gli elementi della sottolista sono un sottoinsieme contiguo.
     * <p>
     * Postconditions: La sottolista è modificata e ora contiene gli elementi ["uno", "modificato", "tre"].
     * La lista padre è aggiornata di conseguenza, con l'elemento all'indice corrispondente modificato.
     * La dimensione della sottolista rimane invariata.
     * <p>
     * Expected Result: L'elemento all'indice 1 della sottolista deve essere "modificato",
     * e il metodo {@code set()} deve restituire il vecchio elemento ("due").
     * La modifica deve essere visibile anche nella lista padre.
     */
    @Test
    public void testSetElement()
    {
        assertEquals("due", subList.get(1));
        Object old = subList.set(1, "modificato");
        assertEquals("due", old); // Restituisce il vecchio elemento
        assertEquals("modificato", subList.get(1));

        // Verifica che la modifica si rifletta nella parentList
        assertEquals("modificato", parentList.get(2)); // Index 2 nella parentList è index 1 nella subList (1+1=2)
    }

    /**
     * Test del metodo {@link HList#set(int, Object)}.
     * <p>
     * Summary: Il test verifica che il metodo {@code set(int index, Object element)} lanci
     * una {@code IndexOutOfBoundsException} quando si tenta di modificare un elemento
     * specificando un indice negativo.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare la completa conformità
     * alla specifica di set(), che impone il lancio di una
     * {@code IndexOutOfBoundsException} per qualsiasi indice che non sia compreso tra 0 (incluso) e `size()` (escluso).
     * Questo caso specifico si concentra sulla violazione del limite inferiore dell'indice.
     * <p>
     * Test Description: 1) Si tenta di modificare un elemento nella `subList`
     *                       tramite il metodo {@code set(int, Object)}.
     *                   2) Viene fornito un indice intenzionalmente negativo (`-1`).
     *                   3) Il test si aspetta che, come risultato di questa operazione,
     *                      venga lanciata una {@code IndexOutOfBoundsException}.
     * <p>
     * Preconditions: La sottolista è stata inizializzata e contiene elementi (ad esempio, ["uno", "due", "tre"]).
     * <p>
     * Postconditions: La sottolista rimane invariata, poiché l'operazione di modifica non è stata
     * completata con successo a causa dell'eccezione lanciata.
     * <p>
     * Expected Result: Una {@code IndexOutOfBoundsException} deve essere lanciata quando si chiama {@code set(-1, "test")}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSetNegativeIndex()
    {
        subList.set(-1, "test");
    }

    /**
     * Test del metodo {@link HList#set(int, Object)}.
     * <p>
     * Summary: Il test verifica che il metodo {@code set(int index, Object element)} lanci
     * una {@code IndexOutOfBoundsException} quando si tenta di modificare un elemento
     * specificando un indice che è al di fuori dei limiti validi (ovvero, maggiore o uguale alla dimensione).
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare la completa conformità
     * alla specifica di set(), che impone il lancio di una
     * {@code IndexOutOfBoundsException} per qualsiasi indice non compreso tra 0 (incluso) e `size()` (escluso).
     * Questo caso specifico si concentra sulla violazione del limite superiore dell'indice.
     * <p>
     * Test Description: 1) Si tenta di modificare un elemento nella `subList`
     *                      tramite il metodo {@code set(int, Object)}.
     *                   2) Viene fornito un indice intenzionalmente fuori limite, pari a {@code 3},
     *                      mentre la dimensione della sottolista è {@code 3} (gli indici validi sono da {@code 0} a {@code 2}).
     *                   3) Il test si aspetta che, come risultato di questa operazione,
     *                      venga lanciata una {@code IndexOutOfBoundsException}.
     * <p>
     * Preconditions: La sottolista è stata inizializzata e contiene 3 elementi (ad esempio, ["uno", "due", "tre"]).
     * <p>
     * Postconditions: La sottolista rimane invariata, poiché l'operazione di modifica non è stata
     * completata con successo a causa dell'eccezione lanciata.
     * <p>
     * Expected Result: Una {@code IndexOutOfBoundsException} deve essere lanciata quando si chiama {@code set(3, "test")}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSetOutOfBoundsIndex()
    {
        subList.set(3, "test"); // size is 3, valid indices 0, 1, 2
    }


    /**
     * Test del metodo {@link HList#clear()}.
     * <p>
     * Summary: Il test verifica che il metodo {@code clear()} svuoti completamente la sottolista
     * e rimuova gli elementi corrispondenti dalla lista padre sottostante.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è garantire che {@code clear()} sulla sottolista
     * rimuova tutti gli elementi contenuti nel suo range dalla lista padre. Si vuole anche assicurare
     * che la sottolista stessa diventi effettivamente vuota, con la sua dimensione impostata a zero.
     * <p>
     * Test Description: 1) Si verifica la dimensione iniziale della `subList` (attesa: 3).
     *                   2) Si invoca il metodo {@code clear()} sulla `subList`.
     *                   3) Si verifica che la dimensione della `subList` sia ora {@code 0} e che {@code isEmpty()} restituisca {@code true}.
     *                   4) Si verifica che gli elementi che facevano parte della `subList` siano stati effettivamente
     *                      rimossi dalla `parentList`. Si controlla la nuova dimensione della `parentList` (attesa: 3 da 6)
     *                      e la posizione degli elementi che non erano nel range della `subList` (es. "zero", "quattro", "cinque").
     * <p>
     * Preconditions: La sottolista è inizializzata e contiene gli elementi `["uno", "due", "tre"]`.
     * La lista padre è inizializzata e contiene gli elementi `["zero", "uno", "due", "tre", "quattro", "cinque"]`,
     * dove la sottolista è una vista degli elementi centrali.
     * <p>
     * Postconditions: La sottolista è vuota. La lista padre è modificata e contiene solo gli elementi
     * che non rientravano nel range originale della sottolista (es. `["zero", "quattro", "cinque"]`).
     * <p>
     * Expected Result: La sottolista deve essere vuota (dimensione {@code 0}).
     * La lista padre deve contenere solo gli elementi che erano esterni al range iniziale della sottolista,
     * con gli elementi successivi all'intervallo spostati per coprire il "buco" lasciato dagli elementi rimossi.
     */
    @Test
    public void testClearSubList()
    {
        assertEquals(3, subList.size());
        subList.clear();
        assertEquals(0, subList.size());
        assertTrue(subList.isEmpty());

        // Verifica che gli elementi siano stati rimossi dalla parentList
        assertEquals(3, parentList.size()); // Da 6 a 3 elementi
        assertEquals("zero", parentList.get(0));
        assertEquals("quattro", parentList.get(1));
        assertEquals("cinque", parentList.get(2));
    }

    /**
     * Test del metodo {@link HList#addAll(HCollection)}.
     * <p>
     * Summary: Il test verifica l'aggiunta di tutti gli elementi di una collezione specificata
     * alla fine della sottolista, assicurando che l'operazione mantenga l'ordine degli elementi
     * e aggiorni correttamente le dimensioni sia della sottolista che della lista padre.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è garantire che {@code addAll(HCollection c)}
     * sulla sottolista deleghi correttamente l'inserimento multiplo di elementi alla lista padre,
     * posizionandoli alla fine della vista della sottolista. Si vuole assicurare che tutti gli elementi
     * della collezione fornita vengano aggiunti, che mantengano il loro ordine relativo,
     * e che gli elementi della lista padre successivi alla sottolista vengano correttamente spostati.
     * <p>
     * Test Description: 1) Viene creata una nuova collezione (`collectionToAdd`) e popolata con
     *                      gli elementi "x" e "y".
     *                   2) Si invoca il metodo {@code addAll()} sulla `subList` (che contiene inizialmente ["uno", "due", "tre"]),
     *                      passando `collectionToAdd` come argomento.
     *                   3) Si verifica che il metodo {@code addAll()} restituisca {@code true}, indicando che la sottolista è stata modificata.
     *                   4) Si verifica che la dimensione della `subList` sia aumentata correttamente (da 3 a 5).
     *                   5) Si assicura che gli elementi "x" e "y" siano stati aggiunti correttamente alla fine della `subList`
     *                      (agli indici 3 e 4 rispettivamente).
     *                   6) Infine, si verifica che l'aggiunta si sia propagata correttamente alla `parentList`,
     *                      controllando le posizioni degli elementi aggiunti e lo spostamento degli elementi originali successivi.
     * <p>
     * Preconditions: La sottolista è inizializzata e contiene gli elementi ["uno", "due", "tre"].
     * La lista padre è inizializzata e contiene gli elementi ["zero", "uno", "due", "tre", "quattro", "cinque"].
     * La `collectionToAdd` viene creata e contiene gli elementi ["x", "y"].
     * <p>
     * Postconditions: La sottolista è modificata e ora contiene gli elementi ["uno", "due", "tre", "x", "y"].
     * La lista padre è aggiornata e ora contiene gli elementi ["zero", "uno", "due", "tre", "x", "y", "quattro", "cinque"].
     * La dimensione della sottolista è aumentata in base al numero di elementi aggiunti.
     * <p>
     * Expected Result: {@code addAll(collectionToAdd)} deve restituire {@code true}.
     * La dimensione della sottolista deve essere {@code 5}.
     * Gli elementi "x" e "y" devono essere presenti agli indici finali della sottolista e alle posizioni corrispondenti nella lista padre.
     * Gli elementi originali della lista padre successivi al range iniziale della sottolista devono essere spostati.
     */
    @Test
    public void testAddAllCollection()
    {
        ListAdapter collectionToAdd = new ListAdapter();
        collectionToAdd.add("x");
        collectionToAdd.add("y");

        assertTrue(subList.addAll(collectionToAdd));
        assertEquals(5, subList.size()); // 3 + 2 = 5
        assertEquals("x", subList.get(3));
        assertEquals("y", subList.get(4));

        // Verifica nella parentList: ["zero", "uno", "due", "tre", "x", "y", "quattro", "cinque"]
        assertEquals("x", parentList.get(4));
        assertEquals("y", parentList.get(5));
        assertEquals("quattro", parentList.get(6)); // Elementi successivi spostati
    }

    /**
     * Test del metodo {@link HList#addAll(HCollection)}.
     * <p>
     * Summary: Il test verifica che il metodo {@code addAll(HCollection c)} restituisca {@code false}
     * quando la collezione specificata come argomento è vuota, e che la sottolista non venga modificata.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare che {@code addAll()}
     * gestisca correttamente il caso in cui non ci siano elementi da aggiungere. In tale scenario,
     * la lista non dovrebbe essere modificata e il metodo dovrebbe indicare l'assenza di modifiche
     * restituendo {@code false}, come specificato dal contratto di {@code Collection.addAll()}.
     * <p>
     * Test Description: 1) Viene creata una nuova collezione (`emptyCollection`) che non contiene alcun elemento.
     *                   2) Si invoca il metodo {@code addAll()} sulla `subList` (che contiene inizialmente ["uno", "due", "tre"]),
     *                      passando `emptyCollection` come argomento.
     *                   3) Si verifica che il metodo {@code addAll()} restituisca {@code false}, indicando che la sottolista
     *                      non è stata modificata.
     *                   4) Si verifica che la dimensione della `subList` sia rimasta invariata (attesa: 3),
     *                      confermando che nessuna modifica è stata apportata alla lista.
     * <p>
     * Preconditions: La sottolista è stata inizializzata e contiene elementi ["uno", "due", "tre"].
     * La `emptyCollection` viene creata e rimane vuota.
     * <p>
     * Postconditions: La sottolista rimane invariata; la sua dimensione e i suoi contenuti non sono stati modificati.
     * <p>
     * Expected Result: {@code addAll(emptyCollection)} deve restituire {@code false}.
     * La dimensione della sottolista non deve cambiare.
     */
    @Test
    public void testAddAllEmptyCollection()
    {
        ListAdapter emptyCollection = new ListAdapter();
        assertFalse(subList.addAll(emptyCollection));
        assertEquals(3, subList.size()); // Dimensione invariata
    }

    /**
     * Test del metodo {@link HList#addAll(HCollection)}.
     * <p>
     * Summary: Il test verifica che il metodo {@code addAll(HCollection c)} lanci
     * una {@code NullPointerException} quando la collezione specificata come argomento è {@code null}.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare la completa conformità
     *  alla specifica di addAll(), che impone il lancio di una
     * {@code NullPointerException} se la collezione fornita è un riferimento {@code null}. Questo test
     * convalida il comportamento atteso per un input non valido.
     * <p>
     * Test Description: 1) Si tenta di aggiungere elementi alla `subList` tramite il metodo {@code addAll()}.
     *                   2) Viene passato {@code null} come argomento per la collezione.
     *                   3) Il test si aspetta che, come risultato di questa operazione,
     *                      venga lanciata una {@code NullPointerException}.
     * <p>
     * Preconditions: La sottolista è stata inizializzata e contiene elementi ["uno", "due", "tre"].
     * L'argomento passato a {@code addAll()} è {@code null}.
     * <p>
     * Postconditions: La sottolista rimane invariata, poiché l'operazione non è completata con successo
     * a causa dell'eccezione lanciata.
     * <p>
     * Expected Result: Una {@code NullPointerException} deve essere lanciata quando si chiama {@code addAll(null)}.
     */
    @Test(expected = NullPointerException.class)
    public void testAddAllNullCollection()
    {
        subList.addAll(null);
    }

    /**
     * Test del metodo {@link HList#addAll(int, HCollection)}.
     * <p>
     * Summary: Il test verifica l'aggiunta di tutti gli elementi di una collezione specificata
     * in una posizione (indice) specifica all'interno della sottolista, assicurando che
     * l'inserimento sposti correttamente gli elementi esistenti e aggiorni le dimensioni
     * sia della sottolista che della lista padre.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è garantire che {@code addAll(int index, HCollection c)}
     * sulla sottolista funzioni correttamente per gli inserimenti multipli in posizioni intermedie.
     * Ciò implica che gli elementi della collezione vengano inseriti all'indice specificato della sottolista,
     * che gli elementi successivi nella sottolista (e di conseguenza nella lista padre) vengano spostati in avanti,
     * e che la dimensione della sottolista si aggiorni correttamente.
     * <p>
     * Test Description: 1) Viene creata una nuova collezione (`collectionToAdd`) e popolata con
     *                      gli elementi "x" e "y".
     *                   2) Si invoca il metodo {@code addAll()} sulla `subList` (che contiene inizialmente ["uno", "due", "tre"]),
     *                      passando l'indice {@code 1} e `collectionToAdd` come argomenti. Questo inserirà "x" e "y"
     *                      all'indice 1 della sottolista.
     *                   3) Si verifica che il metodo {@code addAll()} restituisca {@code true}, indicando che la sottolista è stata modificata.
     *                   4) Si verifica che la dimensione della `subList` sia aumentata correttamente (da 3 a 5).
     *                   5) Si assicura che gli elementi della `subList` siano ora nell'ordine corretto:
     *                      "uno" all'indice 0, "x" all'indice 1, "y" all'indice 2, "due" all'indice 3 e "tre" all'indice 4.
     *                   6) Infine, si verifica che l'inserimento si sia propagato correttamente alla `parentList`,
     *                      controllando le posizioni degli elementi aggiunti e lo spostamento degli elementi originali.
     * <p>
     * Preconditions: La sottolista è inizializzata e contiene gli elementi ["uno", "due", "tre"].
     * La lista padre è inizializzata e contiene gli elementi ["zero", "uno", "due", "tre", "quattro", "cinque"].
     * La `collectionToAdd` viene creata e contiene gli elementi ["x", "y"].
     * <p>
     * Postconditions: La sottolista è modificata e ora contiene gli elementi ["uno", "x", "y", "due", "tre"].
     * La lista padre è aggiornata di conseguenza, con gli elementi aggiunti nella posizione corretta
     * e gli elementi successivi spostati. La dimensione della sottolista è aumentata in base
     * al numero di elementi aggiunti.
     * <p>
     * Expected Result: {@code addAll(1, collectionToAdd)} deve restituire {@code true}.
     * La dimensione della sottolista deve essere {@code 5}.
     * Gli elementi devono essere aggiunti nella posizione specificata, e le liste devono mantenere la coerenza.
     */
    @Test
    public void testAddAllCollectionAtIndex()
    {
        ListAdapter collectionToAdd = new ListAdapter();
        collectionToAdd.add("x");
        collectionToAdd.add("y");

        assertTrue(subList.addAll(1, collectionToAdd)); // Inserisce in posizione 1 della sublist
        assertEquals(5, subList.size()); // 3 + 2 = 5
        assertEquals("uno", subList.get(0));
        assertEquals("x", subList.get(1));
        assertEquals("y", subList.get(2));
        assertEquals("due", subList.get(3));
        assertEquals("tre", subList.get(4));

        // Verifica nella parentList: ["zero", "uno", "x", "y", "due", "tre", "quattro", "cinque"]
        assertEquals("uno", parentList.get(1));
        assertEquals("x", parentList.get(2));
        assertEquals("y", parentList.get(3));
        assertEquals("due", parentList.get(4));
        assertEquals("tre", parentList.get(5));
    }

    /**
     * Test del metodo {@link HList#addAll(int, HCollection)}.
     * <p>
     * Summary: Il test verifica che il metodo {@code addAll(int index, HCollection c)} lanci
     * una {@code IndexOutOfBoundsException} quando si tenta di aggiungere una collezione di elementi
     * specificando un indice negativo.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare la completa conformità
     * alla specifica di addAll(int, Collection), che impone il lancio di una
     * {@code IndexOutOfBoundsException} per qualsiasi indice che non sia compreso tra 0 (incluso) e `size()` (incluso).
     * Questo caso specifico si concentra sulla violazione del limite inferiore dell'indice.
     * <p>
     * Test Description: 1) Viene creata una piccola collezione (`collectionToAdd`) con un elemento.
     *                   2) Si tenta di aggiungere questa collezione alla `subList` tramite il metodo {@code addAll(int, HCollection)}.
     *                   3) Viene fornito un indice intenzionalmente negativo (`-1`).
     *                   4) Il test si aspetta che, come risultato di questa operazione,
     *                      venga lanciata una {@code IndexOutOfBoundsException}.
     * <p>
     * Preconditions: La sottolista è stata inizializzata e contiene elementi ["uno", "due", "tre"].
     * Una collezione (`collectionToAdd`) è stata creata e contiene almeno un elemento.
     * <p>
     * Postconditions: La sottolista rimane invariata, poiché l'operazione di aggiunta non è stata
     * completata con successo a causa dell'eccezione lanciata.
     * <p>
     * Expected Result: Una {@code IndexOutOfBoundsException} deve essere lanciata quando si chiama {@code addAll(-1, collectionToAdd)}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAllAtIndexNegativeIndex()
    {
        ListAdapter collectionToAdd = new ListAdapter();
        collectionToAdd.add("a");
        subList.addAll(-1, collectionToAdd);
    }

    /**
     * Test del metodo {@link HList#addAll(int, HCollection)}.
     * <p>
     * Summary: Questo test verifica che il metodo {@code addAll(int index, HCollection c)} lanci
     * una {@code IndexOutOfBoundsException} quando si tenta di inserire una collezione di elementi
     * a un indice che è al di fuori dei limiti validi della sottolista (ovvero, un indice maggiore della sua dimensione).
     * <p>
     * Test Case Design: La ragione principale di questo test è assicurare la piena **conformità alla specifica di addAll(int, Collection). 
     * Questa specifica impone che venga lanciata una
     * {@code IndexOutOfBoundsException} per qualsiasi indice non compreso tra 0 (incluso) e `size()` (incluso).
     * Nello specifico, questo test si concentra sulla violazione del limite superiore dell'indice, garantendo che il metodo
     * si comporti correttamente quando l'indice fornito non è valido per l'inserimento.
     * <p>
     * Test Description:
     * 1) Viene preparata una piccola collezione, `collectionToAdd`, contenente un elemento.
     * 2) Si tenta di invocare il metodo {@code addAll()} sulla `subList`, passando l'indice {@code 4}
     * e la `collectionToAdd`. Dato che la `subList` ha una dimensione di 3 (indici validi da 0 a 2 per l'accesso,
     * e fino a 3 per l'aggiunta in coda), l'indice 4 è considerato fuori limite.
     * 3) Il test prevede che questa operazione si concluda con il lancio di una {@code IndexOutOfBoundsException}.
     * <p>
     * Preconditions: La `subList` è stata inizializzata e contiene 3 elementi (ad esempio, `["uno", "due", "tre"]`).
     * La `collectionToAdd` è stata creata e contiene almeno un elemento (ad esempio, `["a"]`).
     * <p>
     * Postconditions: La `subList` rimane invariata, poiché l'operazione di aggiunta non è andata a buon fine
     * a causa dell'eccezione lanciata.
     * <p>
     * Expected Result: Ci si aspetta il lancio di una **{@code IndexOutOfBoundsException}** quando si chiama {@code addAll(4, collectionToAdd)}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAllAtIndexOutOfBoundsIndex()
    {
        ListAdapter collectionToAdd = new ListAdapter();
        collectionToAdd.add("a");
        subList.addAll(4, collectionToAdd); // size is 3, valid indices 0, 1, 2, 3 (for add at end)
    }

    /**
     * Test del metodo {@link HList#removeAll(HCollection)}.
     * <p>
     * Summary: Questo test verifica che il metodo {@code removeAll(HCollection c)} rimuova correttamente
     * dalla sottolista **tutti gli elementi che sono contenuti anche nella collezione specificata**,
     * assicurando che le modifiche si riflettano sia nella sottolista che nella lista padre.
     * <p>
     * Test Case Design: L'obiettivo di questo test è garantire che {@code removeAll()} sulla sottolista
     * filtri e rimuova solo gli elementi che esistono simultaneamente nella sottolista e nella collezione fornita.
     * Si vuole inoltre assicurare che questa operazione influenzi la lista padre delegando correttamente la rimozione,
     * e che gli elementi rimanenti si riposizionino correttamente, aggiornando le dimensioni di entrambe le liste.
     * <p>
     * Test Description:
     * 1) Viene creata una `collectionToRemove` e popolata con gli elementi `"uno"` e `"tre"`, che sono presenti nella `subList`.
     * 2) Viene invocato il metodo {@code removeAll()} sulla `subList` (inizialmente `["uno", "due", "tre"]`),
     *    passando `collectionToRemove` come argomento.
     * 3) Il test verifica che {@code removeAll()} restituisca {@code true}, confermando che la `subList` è stata modificata.
     * 4) Si assicura che la dimensione della `subList` sia ora `1` e che l'unico elemento rimasto sia `"due"`.
     * 5) Infine, si verifica lo stato della `parentList`: ci si aspetta che gli elementi `"uno"` e `"tre"`
     *    (che corrispondevano agli elementi della `subList`) siano stati rimossi, e che gli elementi successivi
     *    si siano spostati per riempire gli spazi. Si controlla la nuova dimensione della `parentList` e l'ordine dei suoi elementi.
     * <p>
     * Preconditions: La `subList` è inizializzata e contiene gli elementi `["uno", "due", "tre"]`.
     * La `parentList` è inizializzata e contiene, ad esempio, `["zero", "uno", "due", "tre", "quattro", "cinque"]`.
     * La `collectionToRemove` è creata e contiene gli elementi `["uno", "tre"]`.
     * <p>
     * Postconditions: La `subList` è modificata e ora contiene solo l'elemento `["due"]`.
     * La `parentList` è aggiornata di conseguenza, con gli elementi corrispondenti rimossi e gli altri spostati.
     * La dimensione della `subList` è diminuita.
     * <p>
     * Expected Result: {@code removeAll(collectionToRemove)} deve restituire **{@code true}**.
     * La dimensione della `subList` deve essere **{@code 1}**, e deve contenere solo **"due"**.
     * La `parentList` deve riflettere la rimozione di "uno" e "tre", risultando in uno stato come `["zero", "due", "quattro", "cinque"]`.
     */
    @Test
    public void testRemoveAllCollection()
    {
        ListAdapter collectionToRemove = new ListAdapter();
        collectionToRemove.add("uno");
        collectionToRemove.add("tre");

        assertTrue(subList.removeAll(collectionToRemove));
        assertEquals(1, subList.size());
        assertEquals("due", subList.get(0));

        // Verifica nella parentList: ["zero", "due", "quattro", "cinque"]
        assertEquals("zero", parentList.get(0));
        assertEquals("due", parentList.get(1));
        assertEquals("quattro", parentList.get(2));
    }

    /**
     * Test del metodo {@link HList#removeAll(HCollection)}.
     * <p>
     * Summary: Questo test verifica che il metodo {@code removeAll(HCollection c)} restituisca {@code false}
     * e non modifichi la sottolista quando nessuno degli elementi nella collezione specificata
     * è presente all'interno della sottolista.
     * <p>
     * Test Case Design: La motivazione di questo test è garantire che {@code removeAll()} sulla sottolista
     * si comporti correttamente in assenza di intersezioni tra la sottolista stessa e la collezione fornita.
     * È fondamentale che il metodo non apporti modifiche e indichi questa assenza di modifiche
     * restituendo {@code false}, in linea con il contratto di {@code removeAll()}.
     * <p>
     * Test Description:
     * 1) Viene creata una `collectionToRemove` e popolata con elementi (`"sette"`, `"otto"`)
     *    che sono noti non essere presenti nella `subList`.
     * 2) Si invoca il metodo {@code removeAll()} sulla `subList` (che contiene inizialmente `["uno", "due", "tre"]`),
     *    passando `collectionToRemove` come argomento.
     * 3) Il test verifica che {@code removeAll()} restituisca {@code false}, a indicare che nessun elemento è stato rimosso
     *    e quindi la sottolista non è stata modificata.
     * 4) Si assicura che la dimensione della `subList` sia rimasta invariata (`3`), confermando che
     *    l'operazione non ha avuto alcun impatto sulla lista.
     * <p>
     * Preconditions: La `subList` è inizializzata e contiene gli elementi `["uno", "due", "tre"]`.
     * La `collectionToRemove` è creata e contiene gli elementi `["sette", "otto"]`, nessuno dei quali è presente nella `subList`.
     * <p>
     * Postconditions: La `subList` rimane invariata; la sua dimensione e i suoi contenuti non sono stati modificati.
     * <p>
     * Expected Result: {@code removeAll(collectionToRemove)} deve restituire **{@code false}**.
     * La dimensione della `subList` non deve cambiare.
     */
    @Test
    public void testRemoveAllNoMatchingElements()
    {
        ListAdapter collectionToRemove = new ListAdapter();
        collectionToRemove.add("sette");
        collectionToRemove.add("otto");
        assertFalse(subList.removeAll(collectionToRemove));
        assertEquals(3, subList.size()); // Dimensione invariata
    }

    /**
     * Test del metodo {@link HList#removeAll(HCollection)}.
     * <p>
     * Summary: Questo test verifica che il metodo {@code removeAll(HCollection c)} lanci
     * una {@code NullPointerException} quando la collezione specificata come argomento è {@code null}.
     * <p>
     * Test Case Design: La motivazione principale dietro a questo test è garantire la completa
     * conformità di removeAll(). Questa specifica
     * impone esplicitamente il lancio di una {@code NullPointerException} nel caso in cui
     * l'argomento `c` (la collezione da rimuovere) sia un riferimento {@code null}. Questo test
     * convalida il comportamento atteso del metodo di fronte a un input non valido.
     * <p>
     * Test Description:
     * 1) Si tenta di invocare il metodo {@code removeAll()} sulla `subList`.
     * 2) Viene intenzionalmente passato un riferimento {@code null} come argomento per la collezione da rimuovere.
     * 3) Il test prevede che, come risultato di questa operazione, venga lanciata una {@code NullPointerException}.
     * <p>
     * Preconditions: La `subList` è stata inizializzata e contiene elementi (ad esempio, `["uno", "due", "tre"]`).
     * L'argomento passato a {@code removeAll()} è {@code null}.
     * <p>
     * Postconditions: La `subList` rimane invariata, poiché l'operazione di rimozione non è stata
     * completata con successo a causa dell'eccezione lanciata.
     * <p>
     * Expected Result: Ci si aspetta il lancio di una **{@code NullPointerException}** quando si chiama {@code removeAll(null)}.
     */
    @Test(expected = NullPointerException.class)
    public void testRemoveAllNullCollection()
    {
        subList.removeAll(null);
    }

    /**
     * Test del metodo {@link HList#retainAll(HCollection)}.
     * <p>
     * Summary: Questo test verifica che il metodo {@code retainAll(HCollection c)} mantenga nella sottolista
     * **solo gli elementi che sono contenuti anche nella collezione specificata**, rimuovendo tutti gli altri,
     * e assicurando che le modifiche si riflettano correttamente sia nella sottolista che nella lista padre.
     * <p>
     * Test Case Design: La motivazione di questo test è garantire che {@code retainAll()} sulla sottolista
     * funzioni come un'operazione di intersezione, rimuovendo tutti gli elementi che non sono presenti
     * nella collezione fornita. È fondamentale che questa operazione si propaghi correttamente alla lista padre,
     * e che gli elementi rimanenti si riposizionino correttamente, aggiornando le dimensioni di entrambe le liste.
     * <p>
     * Test Description:
     * 1) Viene creata una `collectionToRetain` e popolata con gli elementi `"due"` e `"quattro"`.
     *    Nota: `"due"` è presente nella `subList`, mentre `"quattro"` non lo è (ma potrebbe esserlo nella `parentList`).
     * 2) Viene invocato il metodo {@code retainAll()} sulla `subList` (inizialmente `["uno", "due", "tre"]`),
     *    passando `collectionToRetain` come argomento. Ci si aspetta che solo `"due"` rimanga.
     * 3) Il test verifica che {@code retainAll()} restituisca {@code true}, confermando che la `subList` è stata modificata
     *    (poiché "uno" e "tre" sono stati rimossi).
     * 4) Si assicura che la dimensione della `subList` sia ora `1` e che l'unico elemento rimasto sia `"due"`.
     * 5) Infine, si verifica lo stato della `parentList`: ci si aspetta che gli elementi `"uno"` e `"tre"`
     *    (che facevano parte del range della `subList` e non erano nella `collectionToRetain`) siano stati rimossi.
     *    Si controlla la nuova dimensione della `parentList` e l'ordine dei suoi elementi.
     * <p>
     * Preconditions: La `subList` è inizializzata e contiene gli elementi ["uno", "due", "tre"].
     * La `parentList` è inizializzata e contiene, ad esempio, ["zero", "uno", "due", "tre", "quattro", "cinque"].
     * La `collectionToRetain` è creata e contiene gli elementi ["due", "quattro"].
     * <p>
     * Postconditions: La `subList` è modificata e ora contiene solo l'elemento `["due"]`.
     * La `parentList` è aggiornata di conseguenza, con gli elementi rimossi e gli altri spostati.
     * La dimensione della `subList` è diminuita.
     * <p>
     * Expected Result: {@code retainAll(collectionToRetain)} deve restituire **{@code true}**.
     * La dimensione della `subList` deve essere **{@code 1}**, e deve contenere solo **"due"**.
     * La `parentList` deve riflettere la rimozione di "uno" e "tre", risultando in uno stato con "zero", "due", "quattro", "cinque"
     * e con una dimensione aggiornata a 4.
     */
    @Test
    public void testRetainAllCollection()
    {
        ListAdapter collectionToRetain = new ListAdapter();
        collectionToRetain.add("due");
        collectionToRetain.add("quattro"); // "quattro" non è nella subList, ma potrebbe essere nella parent

        assertTrue(subList.retainAll(collectionToRetain));
        assertEquals(1, subList.size());
        assertEquals("due", subList.get(0));

        // Verifica nella parentList: ["zero", "due", "quattro", "cinque"]
        // 'uno' e 'tre' sono stati rimossi.
        assertEquals("zero", parentList.get(0));
        assertEquals("due", parentList.get(1));
        assertEquals("quattro", parentList.get(2));
        assertEquals(4, parentList.size());
    }

    /**
     * Test del metodo {@link HList#retainAll(HCollection)}.
     * <p>
     * Summary: Questo test verifica che il metodo {@code retainAll(HCollection c)} restituisca {@code false}
     * e non modifichi la sottolista quando tutti gli elementi attualmente presenti nella sottolista
     * sono anche contenuti nella collezione specificata, indicando che non sono necessarie modifiche.
     * <p>
     * Test Case Design: L'obiettivo principale di questo test è garantire che {@code retainAll()} sulla sottolista
     * si comporti correttamente nel caso in cui tutti i suoi elementi debbano essere mantenuti. Le specifiche di
     * {@code retainAll()} prevede che il metodo restituisca {@code false} se la collezione non subisce modifiche.
     * Questo test convalida tale comportamento, assicurando che la lista non venga inutilmente alterata
     * e che il valore di ritorno sia appropriato quando non ci sono elementi da rimuovere.
     * <p>
     * Test Description:
     * 1) Viene creata una `collectionToRetain` e popolata con gli elementi `"uno"`, `"due"`, `"tre"` (che sono tutti presenti nella `subList`)
     * e `"sette"` (un elemento aggiuntivo non presente nella `subList`).
     * 2) Viene invocato il metodo {@code retainAll()} sulla `subList` (inizialmente `["uno", "due", "tre"]`),
     * passando `collectionToRetain` come argomento. Ci si aspetta che tutti gli elementi della `subList` siano da mantenere.
     * 3) Il test verifica che {@code retainAll()} restituisca **{@code false}**, confermando che la `subList`
     * non è stata modificata poiché tutti i suoi elementi erano già presenti nella `collectionToRetain`.
     * 4) Si assicura che la dimensione della `subList` sia rimasta invariata (`3`) e che i suoi elementi
     * siano ancora nell'ordine e nel contenuto originali: `"uno"` all'indice 0, `"due"` all'indice 1 e `"tre"` all'indice 2.
     * <p>
     * Preconditions: La `subList` è inizializzata e contiene gli elementi `["uno", "due", "tre"]`.
     * La `collectionToRetain` è creata e contiene elementi che includono tutti quelli della `subList`
     * ["uno", "due", "tre", "sette"].
     * <p>
     * Postconditions: La `subList` rimane completamente invariata; la sua dimensione e i suoi contenuti non sono stati modificati.
     * La `parentList` rimane anch'essa invariata.
     * <p>
     * Expected Result: {@code retainAll(collectionToRetain)} deve restituire **{@code false}**.
     * La dimensione della `subList` deve rimanere **{@code 3}**, e i suoi elementi devono essere ancora **`["uno", "due", "tre"]`**.
     */
    @Test
    public void testRetainAllNoModification()
    {
        ListAdapter collectionToRetain = new ListAdapter();
        collectionToRetain.add("uno");
        collectionToRetain.add("due");
        collectionToRetain.add("tre");
        collectionToRetain.add("sette"); // Elemento extra non nella subList
        assertFalse(subList.retainAll(collectionToRetain));
        assertEquals(3, subList.size()); // Dimensione invariata
        assertEquals("uno", subList.get(0));
        assertEquals("due", subList.get(1));
        assertEquals("tre", subList.get(2));
    }

    /**
     * Test del metodo {@link HList#retainAll(HCollection)}.
     * <p>
     * Summary: Questo test verifica che il metodo {@code retainAll(HCollection c)} svuoti completamente la sottolista
     * (e rimuova gli elementi corrispondenti dalla lista padre) quando nessuno degli elementi
     * della sottolista è presente nella collezione specificata.
     * <p>
     * Test Case Design: La motivazione principale di questo test è garantire che {@code retainAll()} sulla sottolista
     * si comporti correttamente quando non c'è alcuna intersezione tra i suoi elementi e quelli della collezione fornita.
     * In questo scenario, tutti gli elementi della sottolista dovrebbero essere rimossi, e il metodo dovrebbe
     * restituire {@code true} per indicare che la lista è stata modificata. Si vuole anche assicurare che
     * la rimozione si propaghi correttamente alla lista padre.
     * <p>
     * Test Description:
     * 1) Viene creata una `collectionToRetain` e popolata con elementi (`"sette"`, `"otto"`)
     *    che non sono presenti nella `subList` (inizialmente `["uno", "due", "tre"]`).
     * 2) Si invoca il metodo {@code retainAll()} sulla `subList`, passando `collectionToRetain` come argomento.
     * 3) Il test verifica che {@code retainAll()} restituisca **{@code true}**, confermando che la `subList`
     *    è stata modificata (svuotata).
     * 4) Si assicura che la `subList` sia ora **vuota** e che la sua dimensione sia **{@code 0}**.
     * 5) Infine, si verifica lo stato della `parentList`: ci si aspetta che tutti gli elementi che facevano
     *    parte del range della `subList` ("uno", "due", "tre") siano stati rimossi dalla `parentList`.
     *    Si controlla la nuova dimensione della `parentList` e l'ordine dei suoi elementi rimanenti.
     * <p>
     * Preconditions: La `subList` è inizializzata e contiene gli elementi `["uno", "due", "tre"]`.
     * La `parentList` è inizializzata e contiene `["zero", "uno", "due", "tre", "quattro", "cinque"]`.
     * La `collectionToRetain` è creata e contiene elementi (`"sette"`, `"otto"`) che non sono presenti nella `subList`.
     * <p>
     * Postconditions: La `subList` è modificata e diventa vuota.
     * La `parentList` è aggiornata di conseguenza, con gli elementi corrispondenti rimossi e gli altri spostati.
     * La dimensione della `subList` è diminuita a zero.
     * <p>
     * Expected Result: {@code retainAll(collectionToRetain)} deve restituire **{@code true}**.
     * La `subList` deve essere **vuota** (dimensione {@code 0}).
     * La `parentList` deve riflettere la rimozione di "uno", "due" e "tre", risultando in uno stato come
     * `["zero", "quattro", "cinque"]` e con una dimensione aggiornata a 3.
     */
    @Test
    public void testRetainAllClearSubList()
    {
        ListAdapter collectionToRetain = new ListAdapter();
        collectionToRetain.add("sette");
        collectionToRetain.add("otto");
        assertTrue(subList.retainAll(collectionToRetain));
        assertTrue(subList.isEmpty());
        assertEquals(0, subList.size());
        // Verifica anche sulla parentList
        assertEquals("zero", parentList.get(0));
        assertEquals("quattro", parentList.get(1));
        assertEquals("cinque", parentList.get(2));
        assertEquals(3, parentList.size()); // 3 elementi rimossi
    }

    /**
     * Test del metodo {@link HList#retainAll(HCollection)}.
     * <p>
     * Summary: Questo test verifica che il metodo {@code retainAll(HCollection c)} lanci
     * una {@code NullPointerException} quando la collezione specificata come argomento è {@code null}.
     * <p>
     * Test Case Design: La ragione principale di questo test è assicurare la piena conformità
     * di retainAll(). Questa specifica impone esplicitamente il lancio di una
     * {@code NullPointerException} nel caso in cui l'argomento `c` (la collezione da confrontare) sia un riferimento {@code null}.
     * Questo test convalida il comportamento atteso per un input non valido.
     * <p>
     * Test Description:
     * 1) Si tenta di invocare il metodo {@code retainAll()} sulla `subList`.
     * 2) Viene intenzionalmente passato un riferimento {@code null} come argomento per la collezione.
     * 3) Il test prevede che, come risultato di questa operazione, venga lanciata una {@code NullPointerException}.
     * <p>
     * Preconditions: La `subList` è stata inizializzata e contiene elementi `["uno", "due", "tre"]`.
     * L'argomento passato a {@code retainAll()} è {@code null}.
     * <p>
     * Postconditions: La `subList` rimane invariata, poiché l'operazione non è stata completata
     * a causa dell'eccezione lanciata.
     * <p>
     * Expected Result: Ci si aspetta il lancio di una **{@code NullPointerException}** quando si chiama {@code retainAll(null)}.
     */
    @Test(expected = NullPointerException.class)
    public void testRetainAllNullCollection()
    {
        subList.retainAll(null);
    }

    // ------- TEST INTERAZIONI CON LA LISTA PADRE E SOTTOLISTE DI SOTTOLISTE --------

    /**
     * Test di interazione: modifica sulla lista padre si riflette nella sottolista.
     * <p>
     * Summary: Questo test verifica che una modifica non strutturale (sostituzione di un elemento)
     * effettuata sulla lista padre si rifletta immediatamente e correttamente nella sottolista,
     * purché la modifica avvenga all'interno del range di elementi visualizzati dalla sottolista.
     * <p>
     * Test Case Design: Questo test è cruciale per validare uno dei comportamenti fondamentali delle "views" (come una sub-list).
     * Le modifiche non strutturali (ad esempio, l'aggiornamento del valore di un elemento tramite {@code set()})
     * dovrebbero propagarsi istantaneamente e bidirezionalmente tra la vista (la sottolista) e la lista di supporto (la lista padre).
     * Questo assicura la coerenza dei dati indipendentemente da quale interfaccia venga utilizzata per la modifica.
     * <p>
     * Test Description:
     * 1) Viene modificato un elemento nella `parentList` utilizzando il metodo {@code set()}.
     *    In questo caso, l'elemento all'indice `2` della `parentList` (che corrisponde all'elemento `"due"` e all'indice `1` nella `subList`)
     *    viene cambiato in `"new value"`.
     * 2) Si verifica immediatamente il valore dell'elemento corrispondente nella `subList` tramite {@code subList.get(1)}.
     * <p>
     * Preconditions: La `subList` è stata inizializzata e visualizza un sottoinsieme della `parentList`.
     * `subList` è `["uno", "due", "tre"]`, e la `parentList` è `["zero", "uno", "due", "tre", "quattro", "cinque"]`,
     * con "uno" all'indice 1 della parentList, "due" all'indice 2, e "tre" all'indice 3.
     * <p>
     * Postconditions: L'elemento all'indice 2 della `parentList` (e quindi all'indice 1 della `subList`)
     * è stato aggiornato a `"new value"`. La `subList` riflette questo cambiamento senza alcuna operazione esplicita su di essa.
     * La `parentList` sarà ora `["zero", "uno", "new value", "tre", "quattro", "cinque"]`.
     * <p>
     * Expected Result: L'invocazione di {@code subList.get(1)} deve restituire la stringa **"new value"**,
     * confermando che la modifica sulla lista padre è stata propagata con successo alla sottolista.
     */
    @Test
    public void testParentSetReflectsInSubList()
    {
        // Modifica l'elemento "due" (indice 2 nella parentList, indice 1 nella subList)
        parentList.set(2, "new value"); // parentList: ["zero", "uno", "new value", "tre", "quattro", "cinque"]
        assertEquals("new value", subList.get(1));
    }

    /**
     * Test di interazione: aggiunta sulla lista padre e "semantica indefinita" della sottolista.
     * <p>
     * Summary: Questo test verifica il comportamento di una sottolista quando la sua lista padre
     * subisce una modifica strutturale (l'aggiunta di un elemento) che **non** è stata effettuata
     * tramite le operazioni della sottolista stessa. In particolare, esamina come gli accessi
     * tramite {@code get()} si riflettano sulla nuova struttura della lista padre, pur mantenendo
     * la dimensione interna originale della sottolista.
     * <p>
     * Test Case Design: Questo test è cruciale per evidenziare la "semantica indefinita" o il comportamento
     * "non fail-fast" di implementazioni di `SubList` che non si invalidano automaticamente dopo modifiche
     * strutturali esterne alla lista padre. Senza un meccanismo "fail-fast", la `SubList` non viene invalidata,
     * ma la sua proprietà `size` interna non si adatta automaticamente a tali modifiche. Di conseguenza,
     * il suo comportamento potrebbe diventare inconsistente rispetto alle aspettative di una lista dinamica
     * (ad esempio, un elemento potrebbe essere "spinto fuori" dal suo range percepito dalla sottolista).
     * Si prevede che i metodi di accesso come {@code get()} continuino a funzionare basandosi sulla
     * struttura *modificata* della lista padre, ma i controlli sui limiti (es. `IndexOutOfBoundsException`)
     * continueranno a usare la dimensione interna non aggiornata della sottolista.
     * <p>
     * Test Description:
     * 1) Si verifica la dimensione iniziale della `subList` (attesa: 3).
     * 2) Viene aggiunto un elemento ("ADDED_BY_PARENT") alla `parentList` ad un indice che cade
     *    all'interno del range concettuale originale della `subList`.
     *    - Esempio: se `parentList` era `["zero", "uno", "due", "tre", "quattro", "cinque"]`
     *    e `subList` rappresentava `parentList[1]` a `parentList[3]` (cioè `["uno", "due", "tre"]`),
     *    l'aggiunta in `parentList.add(2, "ADDED_BY_PARENT")` modifica la `parentList` in
     *    `["zero", "uno", "ADDED_BY_PARENT", "due", "tre", "quattro", "cinque"]`.
     *    Gli elementi originali "due" e "tre" vengono spostati a indici superiori nella `parentList`.
     * 3) Si verifica che la dimensione interna della `subList` **non sia cambiata** (attesa: 3),
     *    dato che non c'è un meccanismo "fail-fast" o di aggiornamento automatico della dimensione.
     * 4) Si verifica che gli elementi acceduti tramite {@code subList.get(relative_index)} riflettano
     *    il nuovo stato della `parentList` a causa dello spostamento degli elementi.
     *    - `subList.get(0)` dovrebbe ancora restituire "uno" (corrisponde a `parentList.get(1)`).
     *    - `subList.get(1)` dovrebbe ora restituire "ADDED_BY_PARENT" (corrisponde a `parentList.get(2)`).
     *    - `subList.get(2)` dovrebbe ora restituire "due" (corrisponde a `parentList.get(3)`).
     * 5) Si tenta di accedere a un indice (`3`) che originariamente non era valido per la `subList`
     *    (poiché la sua dimensione interna è ancora 3). Nonostante la `parentList` sia cresciuta,
     *    ci si aspetta una `IndexOutOfBoundsException` perché il controllo dei limiti della `subList`
     *    si basa sulla sua dimensione interna non aggiornata. Questo conferma il comportamento "non fail-fast"
     *    e le "semanticche indefinite".
     * <p>
     * Preconditions: La `subList` è stata inizializzata come vista di un segmento contiguo della `parentList`.
     * `subList` è `["uno", "due", "tre"]` derivata da una `parentList` più ampia: ["zero", "uno", "due", "tre", "quattro", "cinque"]
     * <p>
     * Postconditions: Viene aggiunto un elemento alla `parentList` all'interno del range originale della `subList`.
     * La `subList` continua a fare riferimento alla `parentList` sottostante, ma la sua dimensione interna
     * (`size`) non viene aggiornata per riflettere la modifica strutturale della `parentList`.
     * <p>
     * Expected Result:
     * - {@code subList.size()} deve rimanere **{@code 3}**.
     * - {@code subList.get(0)} deve restituire **"uno"**.
     * - {@code subList.get(1)} deve restituire **"ADDED_BY_PARENT"**.
     * - {@code subList.get(2)} deve restituire **"due"**.
     * - L'accesso a {@code subList.get(3)} (o superiore) deve lanciare una **{@code IndexOutOfBoundsException}**,
     * poiché l'elemento originale "tre" è stato "spostato" fuori dal range percepito dalla `subList` senza che questa aggiornasse la sua `size`.
     */
    @Test
    public void testParentStructuralModificationReflectsInSubList_Add()
    {
        assertEquals(3, subList.size()); // Dimensione iniziale della sottolista

        // Aggiungi un elemento alla parentList ad un indice *all'interno* del range concettuale originale della subList.
        // parentList: ["zero", "uno", "due", "tre", "quattro", "cinque"]
        // subList (offset=1, size=3): parentList[1], parentList[2], parentList[3] => ["uno", "due", "tre"]
        parentList.add(2, "ADDED_BY_PARENT");
        // Ora parentList: ["zero", "uno", "ADDED_BY_PARENT", "due", "tre", "quattro", "cinque"]
        // Gli elementi originali agli indici 2 e 3 ("due", "tre") sono stati spostati agli indici 3 e 4.
        // L'elemento a parentList[4] ("quattro") è ora a parentList[5].

        // Il campo 'size' interno della subList rimane 3, poiché non è a conoscenza del fail-fast.
        assertEquals(3, subList.size()); // La dimensione interna della SubList non dovrebbe cambiare automaticamente.

        // Tuttavia, gli elementi accessibili tramite subList.get(indice_relativo) dovrebbero riflettere il nuovo stato della parent.
        assertEquals("uno", subList.get(0)); // parent.get(1 + 0) = parent.get(1)
        assertEquals("ADDED_BY_PARENT", subList.get(1)); // parent.get(1 + 1) = parent.get(2)
        assertEquals("due", subList.get(2)); // parent.get(1 + 2) = parent.get(3)

        // Tentare di accedere a un indice oltre la subList.size() originale lancerà IndexOutOfBoundsException
        // anche se la parentList è cresciuta, perché il controllo della dimensione interna della subList si applica ancora.
        try
        {
            subList.get(3); // Questo tenterebbe di accedere a parent.get(1+3) = parent.get(4) che è "tre"
            fail("Prevista IndexOutOfBoundsException poiché subList.size() non viene aggiornata esternamente.");
        }
        catch (IndexOutOfBoundsException e)
        {
            // Comportamento atteso
        }
        // Questo test evidenzia la "semantica indefinita" menzionata nella Javadoc.
        // La subList non riflette più accuratamente la *dimensione* o l'*esatto range* che intendeva originariamente
        // se la lista sottostante viene modificata esternamente.
    }


    /**
     * Test del metodo {@link HList#subList(int, int)} di una {@code SubList}.
     * <p>
     * Summary: Questo test verifica la corretta creazione e il funzionamento di una sottolista
     * derivata da una sottolista esistente (una "sub-subList"). Si assicura che le operazioni
     * su questa sottolista annidata si riflettano correttamente sulla lista padre originale,
     * dimostrando un calcolo cumulativo dell'offset.
     * <p>
     * Test Case Design: La motivazione principale di questo test è garantire che il meccanismo
     * di creazione di sottoliste ricorsive funzioni come previsto. Questo implica che la nuova
     * sottolista debba calcolare correttamente il suo offset e la sua dimensione rispetto alla
     * sottolista da cui è stata creata, e indirettamente, rispetto alla lista originale.
     * È fondamentale che le modifiche non strutturali (come {@code set()}) su una sub-subList
     * si propaghino correttamente fino alla lista più esterna.
     * <p>
     * Test Description:
     * 1) La `subList` iniziale è impostata a `["uno", "due", "tre"]`, che rappresenta gli elementi
     *    dalla posizione 1 alla 3 (esclusa la 4) della `parentList` (e.g., `parentList[1], parentList[2], parentList[3]`).
     * 2) Viene creata una `subSubList` chiamando `subList.subList(1, 2)`. Questo dovrebbe estrarre
     *    l'elemento all'indice relativo 1 della `subList`, che è "due".
     * 3) Si verifica che la `subSubList` abbia dimensione 1 e che il suo unico elemento sia "due".
     * 4) Viene modificato l'elemento della `subSubList` tramite {@code subSubList.set(0, "changed by sub-sub")}.
     * 5) Infine, si verifica che questa modifica sia stata correttamente propagata alla `parentList`
     *    controllando l'elemento alla sua posizione originale (indice 2).
     * <p>
     * Preconditions: La `subList` è stata inizializzata e contiene gli elementi `["uno", "due", "tre"]`.
     * La `parentList` contiene questi elementi come parte di un insieme più ampio:
     * `["zero", "uno", "due", "tre", "quattro", "cinque"]`.
     * <p>
     * Postconditions: Viene creata una `subSubList` valida. L'elemento originariamente "due"
     * nella `subList` (e `parentList`) viene modificato in "changed by sub-sub".
     * La `subSubList` contiene `["changed by sub-sub"]`.
     * La `subList` contiene `["uno", "changed by sub-sub", "tre"]`.
     * La `parentList` contiene `["zero", "uno", "changed by sub-sub", "tre", "quattro", "cinque"]`.
     * <p>
     * Expected Result: La `subSubList` deve avere dimensione **{@code 1}** e contenere **"due"**
     * subito dopo la sua creazione. Dopo la modifica tramite {@code set()}, l'elemento
     * a `parentList.get(2)` (che corrisponde a `subList.get(1)` e `subSubList.get(0)`)
     * deve essere **"changed by sub-sub"**.
     */
    @Test
    public void testSubListFromSubList()
    {
        // subList è ["uno", "due", "tre"] (indici 0, 1, 2 relativi alla subList)
        // Corrisponde a parentList[1], parentList[2], parentList[3]

        // Creiamo una sub-subList da indice 1 (inclusivo) a 2 (esclusivo), cioè solo l'elemento all'indice 1 ("due")
        HList subSubList = subList.subList(1, 2); // get element at subList index 1 -> "due"
        assertEquals(1, subSubList.size());
        assertEquals("due", subSubList.get(0));

        // Verifichiamo che la modifica nella sub-subList si rifletta nella parentList originale.
        // Modifica "due" in "changed by sub-sub". Questo è parentList[2].
        subSubList.set(0, "changed by sub-sub");
        // subSubList: ["changed by sub-sub"]
        // subList: ["uno", "changed by sub-sub", "tre"]
        // parentList: ["zero", "uno", "changed by sub-sub", "tre", "quattro", "cinque"]
        assertEquals("changed by sub-sub", parentList.get(2));
    }

    /**
     * Test del metodo {@link HList#subList(int, int)} su una {@code SubList}.
     * <p>
     * Summary: Questo test verifica che il metodo {@code subList(int fromIndex, int toIndex)}
     * lanci una {@code IndexOutOfBoundsException} quando il parametro {@code fromIndex} è negativo.
     * <p>
     * Test Case Design: La motivazione principale di questo test è assicurare la conformita' di subList(). Questa specifica impone esplicitamente che venga lanciata una
     * {@code IndexOutOfBoundsException} se {@code fromIndex} è negativo, {@code toIndex} è maggiore della dimensione
     * della lista, o se {@code fromIndex} è maggiore di {@code toIndex}. Questo test si concentra
     * sul caso specifico di un {@code fromIndex} negativo, garantendo una corretta validazione dei limiti.
     * <p>
     * Test Description:
     * 1) Si tenta di creare una sottolista dalla `subList` esistente utilizzando il metodo {@code subList(int, int)}.
     * 2) Viene fornito un {@code fromIndex} intenzionalmente negativo (pari a `-1`).
     * 3) Il test si aspetta che, come risultato di questa operazione, venga lanciata una {@code IndexOutOfBoundsException}.
     * <p>
     * Preconditions: La `subList` è stata inizializzata e contiene degli elementi ["uno", "due", "tre"].
     * <p>
     * Postconditions: Nessuna modifica viene apportata alla `subList` o alla `parentList` poiché l'operazione non
     * viene completata con successo a causa dell'eccezione.
     * <p>
     * Expected Result: Ci si aspetta il lancio di una **{@code IndexOutOfBoundsException}** quando si chiama {@code subList(-1, 1)}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSubListFromSubListNegativeFromIndex()
    {
        subList.subList(-1, 1);
    }

    /**
     * Test del metodo {@link HList#subList(int, int)} su una {@code SubList}.
     * <p>
     * Summary: Questo test verifica che il metodo {@code subList(int fromIndex, int toIndex)}
     * lanci una {@code IndexOutOfBoundsException} quando il parametro {@code toIndex} è maggiore
     * della dimensione della sottolista su cui viene invocato.
     * <p>
     * Test Case Design: Questo test è fondamentale per garantire la regolarita' di subList(). La specifica richiede esplicitamente il lancio di una
     * {@code IndexOutOfBoundsException} se {@code toIndex} è maggiore della dimensione della lista.
     * Ci assicuriamo che la validazione degli indici funzioni correttamente, in particolare per il limite superiore
     * dell'intervallo specificato per la nuova sottolista.
     * <p>
     * Test Description:
     * 1) Tentiamo di creare una nuova sottolista dalla `subList` esistente utilizzando il metodo {@code subList(int, int)}.
     * 2) Forniamo un {@code toIndex} intenzionalmente fuori limite, pari a {@code 4}.
     *    La `subList` ha una dimensione di 3, il che significa che gli indici validi per {@code toIndex} vanno da 0 a 3 (escluso).
     * 3) Il test si aspetta che, come risultato di questa operazione, venga lanciata una **{@code IndexOutOfBoundsException}**.
     * <p>
     * Preconditions: La `subList` è stata inizializzata e contiene 3 elementi ["uno", "due", "tre"].
     * <p>
     * Postconditions: Nessuna modifica viene apportata alla `subList` o alla `parentList` poiché l'operazione non
     * viene completata con successo a causa dell'eccezione.
     * <p>
     * Expected Result: Ci aspettiamo il lancio di una **{@code IndexOutOfBoundsException}** quando si chiama `subList(0, 4)`.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSubListFromSubListOutOfBoundsToIndex()
    {
        subList.subList(0, 4);
    }

    /**
     * Test del metodo {@link HList#subList(int, int)} su una {@code SubList}.
     * <p>
     * Summary: Questo test verifica che il metodo {@code subList(int fromIndex, int toIndex)}
     * lanci una {@code IndexOutOfBoundsException} quando il parametro {@code fromIndex} è maggiore
     * del parametro {@code toIndex}.
     * <p>
     * Test Case Design: Questo test è cruciale per garantire  il corretto funzionamento di subList(). La specifica richiede esplicitamente il lancio di una
     * {@code IndexOutOfBoundsException} se {@code fromIndex} è maggiore di {@code toIndex}.
     * Ci assicuriamo che la validazione degli indici funzioni correttamente per prevenire la creazione di
     * sottoliste con intervalli non validi o invertiti.
     * <p>
     * Test Description:
     * 1) Tentiamo di creare una nuova sottolista dalla `subList` esistente utilizzando il metodo {@code subList(int, int)}.
     * 2) Forniamo un {@code fromIndex} (pari a `2`) che è intenzionalmente maggiore del {@code toIndex} (pari a `1`).
     * 3) Il test si aspetta che, come risultato di questa operazione, venga lanciata una **{@code IndexOutOfBoundsException}**.
     * <p>
     * Preconditions: La `subList` è stata inizializzata e contiene elementi (ad esempio, `["uno", "due", "tre"]`).
     * <p>
     * Postconditions: Nessuna modifica viene apportata alla `subList` o alla `parentList` poiché l'operazione non
     * viene completata con successo a causa dell'eccezione.
     * <p>
     * Expected Result: Ci aspettiamo il lancio di una **{@code IndexOutOfBoundsException}** quando si chiama `subList(2, 1)`.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSubListFromSubListFromGreaterThanTo()
    {
        subList.subList(2, 1);
    }

    /**
     * Test del metodo {@link HList#toArray()}.
     * <p>
     * Summary: Questo test verifica che il metodo {@code toArray()} su una sottolista
     * restituisca correttamente un nuovo array contenente tutti gli elementi della sottolista
     * nell'ordine corretto.
     * <p>
     * Test Case Design: La motivazione principale di questo test è assicurare che la conversione
     * della sottolista in un array funzioni correttamente, producendo una "snapshot" degli elementi
     * presenti nella vista della sottolista. È fondamentale che l'array risultante contenga
     * esattamente gli elementi che la sottolista espone e che la sua dimensione sia accurata.
     * <p>
     * Test Description:
     * 1) Viene invocato il metodo {@code toArray()} sulla `subList` (che contiene `["uno", "due", "tre"]`).
     * 2) Si confronta l'array ottenuto con un array atteso (`{"uno", "due", "tre"}`) utilizzando
     * `assertArrayEquals` per verificare che gli elementi e il loro ordine corrispondano.
     * 3) Si verifica esplicitamente che la lunghezza dell'array risultante sia pari alla dimensione
     * della sottolista (attesa: 3).
     * <p>
     * Preconditions: La `subList` è stata inizializzata e contiene gli elementi `["uno", "due", "tre"]`.
     * <p>
     * Postconditions: Un nuovo array di `Object` è stato creato e restituito. Nessuna modifica
     * viene apportata alla `subList` o alla `parentList`.
     * <p>
     * Expected Result: L'array restituito da {@code toArray()} deve avere una lunghezza di **3**
     * e i suoi elementi devono essere **`{"uno", "due", "tre"}`**, nell'ordine specificato.
     */
    @Test
    public void testToArray()
    {
        Object[] expected = {"uno", "due", "tre"};
        Object[] actual = subList.toArray();
        assertArrayEquals(expected, actual);
        assertEquals(3, actual.length);
    }

    /**
     * Test del metodo {@link HList#toArray(Object[])}.
     * <p>
     * Summary: Questo test verifica che il metodo {@code toArray(Object[] a)} su una sottolista
     * riempia correttamente l'array fornito con gli elementi della sottolista quando l'array
     * in input è sufficientemente grande da contenerli tutti. Inoltre, assicura che l'elemento
     * immediatamente successivo all'ultimo elemento della sottolista nell'array fornito sia impostato a {@code null}.
     * <p>
     * Test Case Design: La motivazione principale di questo test è garantire che l'implementazione
     * del metodo {@code toArray(Object[] a)} nella sottolista rispetti la specifica del metodo. Prevede il riutilizzo dell'array fornito
     * se la sua dimensione è adeguata e l'impostazione a {@code null} dell'elemento successivo se ve ne sono.
     * Questo assicura l'efficienza (evitando la creazione di un nuovo array) e la correttezza del riempimento.
     * <p>
     * Test Description:
     * 1) Viene inizializzato un array di `String` (`arr`) con una dimensione maggiore rispetto al numero
     *    di elementi presenti nella `subList` (ad esempio, 5 elementi per una `subList` di 3 elementi).
     * 2) Si invoca il metodo {@code toArray(Object[] a)} sulla `subList`, passando `arr` come argomento.
     * 3) Si verifica, tramite {@code assertSame()}, che il metodo restituisca una referenza allo stesso array
     *    che è stato passato come input, confermando il riutilizzo.
     * 4) Si assicura che i primi elementi dell'array (`arr[0]`, `arr[1]`, `arr[2]`) corrispondano
     *    agli elementi della `subList` (`"uno"`, `"due"`, `"tre"`).
     * 5) Si verifica che l'elemento immediatamente successivo all'ultimo elemento della sottolista nell'array
     *    (`arr[3]`) sia impostato a {@code null}, come da specifica. Anche gli elementi successivi (`arr[4]`)
     *    devono rimanere {@code null} se lo erano già.
     * <p>
     * Preconditions: La `subList` è stata inizializzata e contiene gli elementi `["uno", "due", "tre"]`.
     * Viene fornito un array di tipo compatibile e di dimensione sufficiente (ad esempio, 5 per 3 elementi).
     * <p>
     * Postconditions: L'array fornito in input viene modificato: i suoi primi elementi contengono
     * gli elementi della sottolista nell'ordine corretto, e l'elemento immediatamente successivo
     * all'ultimo elemento della sottolista è impostato a {@code null} (se l'array era abbastanza grande).
     * Nessuna modifica strutturale viene apportata alla `subList` o alla `parentList`.
     * <p>
     * Expected Result: Il metodo deve restituire lo **stesso array** passato come argomento.
     * L'array deve contenere `["uno", "due", "tre", null, null]` (o `null` solo all'indice 3 se fosse un array di 4 elementi),
     * e la sua lunghezza deve rimanere quella originale fornita.
     */
    @Test
    public void testToArrayWithSufficientlyLargeArray()
    {
        String[] arr = new String[5];
        Object[] result = subList.toArray(arr);

        // Verifica che sia lo stesso array passato
        assertSame(arr, result);
        assertEquals("uno", arr[0]);
        assertEquals("due", arr[1]);
        assertEquals("tre", arr[2]);
        assertNull(arr[3]); // Elemento successivo alla fine della sublist deve essere null
        assertNull(arr[4]);
    }

    /**
     * Test del metodo {@link HList#toArray(Object[])}.
     * <p>
     * Summary: Questo test verifica che il metodo {@code toArray(Object[] a)} su una sottolista
     * crei e restituisca un **nuovo array** della dimensione corretta, contenente tutti gli elementi
     * della sottolista, quando l'array fornito in input è troppo piccolo per contenerli.
     * <p>
     * Test Case Design: La motivazione principale di questo test è garantire che l'implementazione
     * del metodo {@code toArray(Object[] a)} rispetti pienamente la specifica. Questa specifica prevede che, se l'array fornito
     * non è sufficientemente grande, debba essere creato un nuovo array dello stesso tipo (runtime type)
     * della lista e riempito con i suoi elementi. Questo assicura che l'operazione funzioni sempre,
     * indipendentemente dalla dimensione dell'array iniziale.
     * <p>
     * Test Description:
     * 1) Viene inizializzato un array di `String` (`arr`) con una dimensione intenzionalmente
     *    più piccola rispetto al numero di elementi presenti nella `subList` (ad esempio, 2 elementi per una `subList` di 3).
     * 2) Si invoca il metodo {@code toArray(Object[] a)} sulla `subList`, passando `arr` come argomento.
     * 3) Si verifica, tramite {@code assertNotSame()}, che il metodo **non** restituisca una referenza
     *    allo stesso array che è stato passato come input, confermando la creazione di un nuovo array.
     * 4) Si assicura che la lunghezza del nuovo array risultante sia esattamente pari alla dimensione
     *    della sottolista (attesa: 3).
     * 5) Si verifica che tutti gli elementi dell'array restituito (`result[0]`, `result[1]`, `result[2]`)
     *    corrispondano agli elementi della `subList` (`"uno"`, `"due"`, `"tre"`), nell'ordine corretto.
     * <p>
     * Preconditions: La `subList` è stata inizializzata e contiene gli elementi `["uno", "due", "tre"]`.
     * Viene fornito un array di tipo compatibile ma di dimensione insufficiente (ad esempio, 2 per 3 elementi).
     * <p>
     * Postconditions: Viene creato e restituito un nuovo array di `Object` (o del tipo runtime corretto).
     * L'array originale passato come input rimane invariato. Nessuna modifica strutturale viene
     * apportata alla `subList` o alla `parentList`.
     * <p>
     * Expected Result: Il metodo deve restituire un **nuovo array** (non lo stesso array passato come argomento).
     * Il nuovo array deve avere una lunghezza di **3** e il suo contenuto deve essere **`{"uno", "due", "tre"}`**.
     */
    @Test
    public void testToArrayWithTooSmallArray()
    {
        String[] arr = new String[2]; // Troppo piccolo
        Object[] result = subList.toArray(arr);

        // Verifica che sia un nuovo array
        assertNotSame(arr, result);
        assertEquals(3, result.length);
        assertEquals("uno", result[0]);
        assertEquals("due", result[1]);
        assertEquals("tre", result[2]);
    }

    /**
     * Test del metodo {@link HList#toArray(Object[])}.
     * <p>
     * Summary: Questo test verifica che il metodo {@code toArray(Object[] a)} lanci
     * una {@code NullPointerException} quando l'array fornito come argomento è {@code null}.
     * <p>
     * Test Case Design: La motivazione principale di questo test è garantire la piena conformita' del metodo. Questa specifica impone esplicitamente il lancio di una
     * {@code NullPointerException} nel caso in cui l'argomento `a` (l'array in cui memorizzare gli elementi)
     * sia un riferimento {@code null}. Questo test convalida il comportamento atteso del metodo di fronte a un input non valido.
     * <p>
     * Test Description:
     * 1) Tentiamo di convertire la `subList` in un array utilizzando il metodo {@code toArray(Object[] a)}.
     * 2) Forniamo intenzionalmente un riferimento {@code null} come argomento per l'array.
     * 3) Il test si aspetta che, come risultato di questa operazione, venga lanciata una **{@code NullPointerException}**.
     * <p>
     * Preconditions: La subList è stata inizializzata e contiene elementi ["uno", "due", "tre"].
     * L'argomento passato a {@code toArray()} è {@code null}.
     * <p>
     * Postconditions: Nessuna modifica viene apportata alla `subList` o alla `parentList` poiché l'operazione non
     * viene completata con successo a causa dell'eccezione.
     * <p>
     * Expected Result: Ci aspettiamo il lancio di una **{@code NullPointerException}** quando si chiama `toArray(null)`.
     */
    @Test(expected = NullPointerException.class)
    public void testToArrayNullArray()
    {
        subList.toArray(null);
    }


    // ------- TEST DEGLI ITERATORI (BASIC FUNCTIONALITY) --------
    // I test completi degli iteratori dovrebbero essere in una suite dedicata a ListIterator,
    // ma qui verifichiamo la loro istanziazione e funzionalità base sulla SubList.

    /**
     * Test del metodo {@link HList#iterator()}.
     * <p>
     * Summary: Questo test verifica che il metodo {@code iterator()} su una sottolista
     * restituisca un iteratore correttamente funzionante, che permetta di percorrere tutti
     * gli elementi della sottolista nell'ordine di inserimento.
     * <p>
     * Test Case Design: La motivazione principale di questo test è garantire che l'iteratore
     * fornito dalla sottolista sia una vista accurata e navigabile degli elementi che essa gestisce.
     * È fondamentale che l'iteratore esponga esattamente gli elementi nel range definito dalla
     * sottolista e che segua la logica standard degli iteratori ({@code hasNext()} e {@code next()}).
     * <p>
     * Test Description:
     * 1) Viene ottenuto un iteratore per la `subList` tramite la chiamata a {@code subList.iterator()}.
     * 2) Si utilizzano i metodi {@code hasNext()} e {@code next()} dell'iteratore per scorrere
     * attraverso tutti gli elementi previsti nella `subList` (`"uno"`, `"due"`, `"tre"`).
     * 3) Ad ogni passo, si verifica che {@code hasNext()} restituisca {@code true} prima di un elemento atteso
     * e che {@code next()} restituisca l'elemento corretto.
     * 4) Dopo aver recuperato l'ultimo elemento, si verifica che {@code hasNext()} restituisca {@code false},
     * confermando che l'iterazione è terminata e non ci sono più elementi da percorrere.
     * <p>
     * Preconditions: La `subList` è stata inizializzata e contiene gli elementi ["uno", "due", "tre"].
     * <p>
     * Postconditions: L'iteratore ha percorso tutti gli elementi della `subList` senza errori.
     * Nessuna modifica strutturale o di contenuto viene apportata alla `subList` o alla `parentList`
     * durante l'iterazione.
     * <p>
     * Expected Result: L'iteratore deve restituire gli elementi nell'ordine corretto: **"uno"**, **"due"**, e **"tre"**.
     * Le chiamate a {@code hasNext()} e {@code next()} devono comportarsi come previsto per un iteratore completo.
     */
    @Test
    public void testIterator()
    {
        HIterator it = subList.iterator();
        assertTrue(it.hasNext());
        assertEquals("uno", it.next());
        assertTrue(it.hasNext());
        assertEquals("due", it.next());
        assertTrue(it.hasNext());
        assertEquals("tre", it.next());
        assertFalse(it.hasNext());
    }

    /**
     * Test del metodo {@link HList#listIterator()}.
     * <p>
     * Summary: Questo test verifica che il metodo {@code listIterator()} su una sottolista
     * restituisca un **List Iterator** correttamente funzionante, capace di percorrere tutti
     * gli elementi della sottolista nell'ordine di inserimento.
     * <p>
     * Test Case Design: La motivazione principale di questo test è garantire che il List Iterator
     * fornito dalla sottolista sia una vista accurata e navigabile bidirezionalmente degli elementi
     * che essa gestisce. È fondamentale che il List Iterator esponga esattamente gli elementi nel range
     * definito dalla sottolista e che segua la logica standard dei List Iterator ({@code hasNext()},
     * {@code next()}, {@code hasPrevious()}, {@code previous()}, ecc.). Questo test si concentra
     * sul percorso in avanti.
     * <p>
     * Test Description:
     * 1) Viene ottenuto un List Iterator per la `subList` tramite la chiamata a {@code subList.listIterator()}.
     * 2) Si utilizzano i metodi {@code hasNext()} e {@code next()} del List Iterator per scorrere
     * attraverso tutti gli elementi previsti nella `subList` (`"uno"`, `"due"`, `"tre"`).
     * 3) Ad ogni passo, si verifica che {@code hasNext()} restituisca {@code true} prima di un elemento atteso
     * e che {@code next()} restituisca l'elemento corretto.
     * 4) Dopo aver recuperato l'ultimo elemento, si verifica che {@code hasNext()} restituisca {@code false},
     * confermando che l'iterazione in avanti è terminata e non ci sono più elementi da percorrere.
     * <p>
     * Preconditions: La `subList` è stata inizializzata e contiene gli elementi `["uno", "due", "tre"]`.
     * <p>
     * Postconditions: Il List Iterator ha percorso tutti gli elementi della `subList` senza errori.
     * Nessuna modifica strutturale o di contenuto viene apportata alla `subList` o alla `parentList`
     * durante l'iterazione.
     * <p>
     * Expected Result: Il List Iterator deve restituire gli elementi nell'ordine corretto: **"uno"**, **"due"**, e **"tre"**.
     * Le chiamate a {@code hasNext()} e {@code next()} devono comportarsi come previsto per un List Iterator.
     */
    @Test
    public void testListIterator()
    {
        HListIterator it = subList.listIterator();
        assertTrue(it.hasNext());
        assertEquals("uno", it.next());
        assertTrue(it.hasNext());
        assertEquals("due", it.next());
        assertTrue(it.hasNext());
        assertEquals("tre", it.next());
        assertFalse(it.hasNext());
    }

    /**
     * Test del metodo {@link HList#listIterator(int)}.
     * <p>
     * Summary: Questo test verifica che il metodo {@code listIterator(int index)} su una sottolista
     * restituisca un **List Iterator** correttamente inizializzato che comincia la sua traversata
     * (o si posiziona) all'indice specificato, permettendo di accedere correttamente all'elemento successivo.
     * <p>
     * Test Case Design: La motivazione principale di questo test è garantire che il List Iterator possa essere
     * posizionato correttamente a un indice arbitrario specificato al momento della sua creazione.
     * In questo caso specifico, verifichiamo il posizionamento all'inizio della sottolista (indice 0),
     * assicurandoci che il primo elemento restituito da {@code next()} sia quello atteso per tale posizione.
     * <p>
     * Test Description:
     * 1) Viene ottenuto un List Iterator per la `subList` tramite la chiamata a {@code subList.listIterator(0)},
     *    chiedendo che l'iteratore si posizioni all'inizio della sottolista.
     * 2) Si verifica che {@code hasNext()} restituisca {@code true}, indicando che ci sono elementi da percorrere.
     * 3) Si invoca {@code next()} e si assicura che restituisca `"uno"`, che è il primo elemento della `subList`.
     * <p>
     * Preconditions: La `subList` è stata inizializzata e contiene gli elementi `["uno", "due", "tre"]`.
     * <p>
     * Postconditions: Il List Iterator è stato creato e posizionato all'indice 0.
     * Nessuna modifica strutturale o di contenuto viene apportata alla `subList` o alla `parentList`.
     * <p>
     * Expected Result: La chiamata a {@code it.hasNext()} deve restituire **{@code true}**, e {@code it.next()}
     * deve restituire **"uno"**.
     */
    @Test
    public void testListIteratorFromIndexStart()
    {
        HListIterator it = subList.listIterator(0);
        assertTrue(it.hasNext());
        assertEquals("uno", it.next());
}

    /**
     * Test del metodo {@link HList#listIterator(int)}.
     * <p>
     * Summary: Questo test verifica che il metodo {@code listIterator(int index)} su una sottolista
     * restituisca un **List Iterator** correttamente inizializzato e posizionato a un indice intermedio specificato,
     * consentendo di accedere agli elementi successivi a partire da quel punto.
     * <p>
     * Test Case Design: La motivazione principale di questo test è garantire che il List Iterator sia in grado di
     * essere posizionato accuratamente a un indice arbitrario all'interno della sottolista al momento della sua creazione.
     * In questo scenario, ci concentriamo su un posizionamento intermedio (indice 1), verificando che il primo
     * elemento restituito da {@code next()} sia quello atteso e che la successiva iterazione proceda correttamente.
     * <p>
     * Test Description:
     * 1) Viene ottenuto un List Iterator per la `subList` tramite la chiamata a {@code subList.listIterator(1)},
     *    richiedendo che l'iteratore si posizioni all'indice 1 della sottolista (cioè prima dell'elemento "due").
     * 2) Si verifica che {@code hasNext()} restituisca {@code true}, indicando che ci sono elementi da percorrere.
     * 3) Si invoca {@code next()} e si assicura che restituisca **"due"**, che è l'elemento atteso all'indice 1 della `subList`.
     * 4) Si procede con l'iterazione, verificando che il successivo {@code hasNext()} sia {@code true}
     *    e che {@code next()} restituisca **"tre"**.
     * 5) Infine, si verifica che {@code hasNext()} restituisca {@code false}, confermando che l'iterazione è terminata.
     * <p>
     * Preconditions: La `subList` è stata inizializzata e contiene gli elementi `["uno", "due", "tre"]`.
     * <p>
     * Postconditions: Il List Iterator è stato creato e posizionato all'indice 1.
     * Nessuna modifica strutturale o di contenuto viene apportata alla `subList` o alla `parentList` durante l'iterazione.
     * <p>
     * Expected Result: Le chiamate a {@code hasNext()} e {@code next()} devono comportarsi come previsto
     * partendo dall'indice 1, restituendo prima **"due"** e poi **"tre"**, e poi indicando la fine degli elementi.
     */
    @Test
    public void testListIteratorFromIndexMid()
    {
        HListIterator it = subList.listIterator(1);
        assertTrue(it.hasNext());
        assertEquals("due", it.next());
        assertTrue(it.hasNext());
        assertEquals("tre", it.next());
        assertFalse(it.hasNext());
    }

    /**
     * Test del metodo {@link HList#listIterator(int)}.
     * <p>
     * Summary: Questo test verifica che il metodo {@code listIterator(int index)} su una sottolista
     * restituisca un **List Iterator** correttamente inizializzato e posizionato alla fine della lista.
     * Tale posizionamento è tipico per operazioni di aggiunta o per iniziare una traversata a ritroso.
     * <p>
     * Test Case Design: La motivazione principale di questo test è garantire che il List Iterator possa essere
     * posizionato correttamente all'indice equivalente alla dimensione della lista. Questo punto di posizionamento
     * è standard nella libreria Collections di Java e indica che l'iteratore si trova "dopo" l'ultimo elemento,
     * pronto per operazioni come {@code add()} o {@code previous()}.
     * <p>
     * Test Description:
     * 1) Viene ottenuto un List Iterator per la `subList` tramite la chiamata a {@code subList.listIterator(subList.size())},
     * richiedendo che l'iteratore si posizioni alla fine della sottolista.
     * 2) Si verifica che {@code hasNext()} restituisca **{@code false}**, confermando che, essendo alla fine,
     * non ci sono più elementi da percorrere in avanti.
     * 3) Si assicura che {@code nextIndex()} restituisca un valore pari alla dimensione della `subList`,
     * che è l'indice atteso quando l'iteratore è posizionato alla fine.
     * <p>
     * Preconditions: La `subList` è stata inizializzata e contiene gli elementi `["uno", "due", "tre"]`.
     * <p>
     * Postconditions: Il List Iterator è stato creato e posizionato correttamente alla fine della `subList`.
     * Nessuna modifica strutturale o di contenuto viene apportata alla `subList` o alla `parentList`.
     * <p>
     * Expected Result: La chiamata a {@code it.hasNext()} deve restituire **{@code false}**.
     * La chiamata a {@code it.nextIndex()} deve restituire un valore pari alla **dimensione corrente della `subList`**.
     */
    @Test
    public void testListIteratorFromIndexEnd()
    {
        HListIterator it = subList.listIterator(subList.size());
        assertFalse(it.hasNext());
        assertEquals(subList.size(), it.nextIndex());
    }

    /**
     * Test del metodo {@link HList#listIterator(int)}.
     * <p>
     * Summary: Questo test verifica che il metodo {@code listIterator(int index)} lanci
     * una **{@code IndexOutOfBoundsException}** quando l'indice specificato per l'inizializzazione
     * del List Iterator è negativo.
     * <p>
     * Test Case Design: Questo test è fondamentale per garantire la correttezza del metodo. La specifica impone esplicitamente il lancio di una
     * {@code IndexOutOfBoundsException} se l'indice non è compreso tra 0 (incluso) e {@code size()} (incluso).
     * Questo caso specifico si concentra sulla violazione del limite inferiore dell'indice.
     * <p>
     * Test Description:
     * 1) Tentiamo di ottenere un List Iterator per la `subList` utilizzando il metodo {@code listIterator(int)}.
     * 2) Forniamo un indice intenzionalmente negativo (pari a `-1`).
     * 3) Il test si aspetta che, come risultato di questa operazione, venga lanciata una **{@code IndexOutOfBoundsException}**.
     * <p>
     * Preconditions: La `subList` è stata inizializzata e contiene elementi (ad esempio, `["uno", "due", "tre"]`).
     * <p>
     * Postconditions: Nessuna modifica viene apportata alla `subList` o alla `parentList` poiché l'operazione non
     * viene completata con successo a causa dell'eccezione.
     * <p>
     * Expected Result: Ci aspettiamo il lancio di una **{@code IndexOutOfBoundsException}** quando si chiama {@code listIterator(-1)}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testListIteratorNegativeIndex()
    {
        subList.listIterator(-1);
    }

    /**
     * Test del metodo {@link HList#listIterator(int)}.
     * <p>
     * Summary: Questo test verifica che il metodo {@code listIterator(int index)} lanci
     * una **{@code IndexOutOfBoundsException}** quando l'indice specificato per l'inizializzazione
     * del List Iterator è maggiore della dimensione della sottolista.
     * <p>
     * Test Case Design: Questo test è cruciale per garantire la specifica: essa impone esplicitamente il lancio di una
     * {@code IndexOutOfBoundsException} se l'indice non è compreso tra 0 (incluso) e {@code size()} (incluso).
     * Questo caso specifico si concentra sulla violazione del limite superiore dell'indice, assicurando
     * che non sia possibile creare un iteratore posizionato al di fuori dei confini validi della lista.
     * <p>
     * Test Description:
     * 1) Tentiamo di ottenere un List Iterator per la `subList` utilizzando il metodo {@code listIterator(int)}.
     * 2) Forniamo un indice intenzionalmente fuori limite, calcolato come `subList.size() + 1`.
     * Ad esempio, se la `subList` ha dimensione 3, l'indice sarà 4.
     * 3) Il test si aspetta che, come risultato di questa operazione, venga lanciata una **{@code IndexOutOfBoundsException}**.
     * <p>
     * Preconditions: La `subList` è stata inizializzata e contiene 3 elementi (ad esempio, `["uno", "due", "tre"]`).
     * <p>
     * Postconditions: Nessuna modifica viene apportata alla `subList` o alla `parentList`, poiché l'operazione non
     * viene completata con successo a causa dell'eccezione.
     * <p>
     * Expected Result: Ci aspettiamo il lancio di una **{@code IndexOutOfBoundsException}** quando si chiama {@code listIterator(subList.size() + 1)}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testListIteratorOutOfBoundsIndex()
    {
        subList.listIterator(subList.size() + 1); // size è 3, indici validi fino a 3 (esclusivo per next(), inclusivo per add)
    }
}