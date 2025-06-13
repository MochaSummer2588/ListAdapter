package myTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import myAdapter.*;

/**
 * Suite di test per la classe interna {@code myAdapter.ListAdapter.SubList}.
 * <p>
 * Riassunto: Verifica il corretto funzionamento dei metodi della {@code SubList},
 * assicurando che agisca come una vista sulla lista padre e che le modifiche
 * siano reciprocamente riflesse. Particolare attenzione è data alla gestione
 * degli indici e alla propagazione delle operazioni alla lista sottostante.
 * <p>
 * Design: Utilizza JUnit 4.13.2. Ogni test case opera su una `SubList` creata
 * da una `ListAdapter` popolata, che viene ripristinata prima di ogni test.
 * Si testa il comportamento della `SubList` sia in condizioni normali che al bordo
 * dei suoi limiti, e le interazioni con la lista padre. Sono inclusi test per le eccezioni.
 */
public class TestSubListAdapter 
{

    private ListAdapter parentList;
    private HList subList; // SubList [parent index 1, parent index 2, parent index 3] = ["uno", "due", "tre"]

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
     * Summary: Il test testa il metodo {@code size()} della sottolista su una sottolista non vuota.
     * <p>
     * Test Case Design: Assicurarsi che la `SubList` calcoli correttamente la sua dimensione in modo tale che abbia il numero di elementi corretto
     * in base agli indici `fromIndex` e `toIndex` forniti.
     * <p>
     * Test Description: Verifica che la sottolista abbia una dimensione pari a 3 in quanto lastindex - firstindex = 4 - 1 = 3 chiamando il metodo {@code size()} sulla sottolista.
     * <p>
     * Preconditions: La sottolista è inizializzata con i 3 elementi della lista padre, ossia ["uno", "due", "tre"].
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
     * Summary: Il test testa il metodo {@code isEmpty()} della sottolista su una sottolista non vuota.
     * <p>
     * Test Case Design: La motivazione dietro a questo test e' garantire che `isEmpty()` rifletta correttamente 
     * lo stato di non-vuoto della sottolista
     * <p>
     * Test Description: 
     * <p>
     * Preconditions: La sottolista è inizializzata con 3 elementi ossia ["uno", "due", "tre"].
     * <p>
     * Postconditions: La sottolista non è vuota.
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
     * Verifica il recupero di un elemento alla fine della sottolista.
     * <p>
     * Test Case Design:  Assicurarsi che il mapping degli indici funzioni correttamente anche per l'ultimo elemento.
     * <p>
     * Preconditions:  La sottolista contiene ["uno", "due", "tre"].
     * <p>
     * Postconditions:  L'elemento all'indice 2 della sottolista è "tre".
     * <p>
     * Expected Result: {@code get(2)} deve restituire "tre".
     */
    @Test
    public void testGetLastElement() 
    {
        assertEquals("tre", subList.get(2));
    }

    /**
     * Test del metodo {@link HList#get(int)}.
     *  Verifica che `get` lanci `IndexOutOfBoundsException` per indice negativo.
     * Test Case Design:  Assicurare la conformità alla Javadoc di `List.get()`, che specifica
     * il lancio di `IndexOutOfBoundsException` per indici negativi.
     * Preconditions:  La sottolista contiene ["uno", "due", "tre"].
     * Postconditions:  Viene lanciata `IndexOutOfBoundsException`.
     * Expected Result: `IndexOutOfBoundsException` per `get(-1)`.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetNegativeIndex() 
    {
        subList.get(-1);
    }

    /**
     * Test del metodo {@link HList#get(int)}.
     *  Verifica che `get` lanci `IndexOutOfBoundsException` per indice fuori limite (maggiore o uguale alla dimensione).
     * Test Case Design:  Assicurare la conformità alla Javadoc di `List.get()`, che specifica
     * il lancio di `IndexOutOfBoundsException` per indici fuori range.
     * Preconditions:  La sottolista contiene ["uno", "due", "tre"].
     * Postconditions:  Viene lanciata `IndexOutOfBoundsException`.
     * Expected Result: `IndexOutOfBoundsException` per `get(3)`.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetOutOfBoundsIndex() 
    {
        subList.get(3);
    }

    /**
     * Test del metodo {@link HList#indexOf(Object)}.
     *  Verifica la ricerca di un elemento esistente nella sottolista.
     * Test Case Design:  Assicurarsi che `indexOf()` trovi correttamente la posizione relativa di un elemento.
     * Preconditions:  La sottolista contiene ["uno", "due", "tre"].
     * Postconditions:  L'indice di "due" è 1.
     * Expected Result: {@code indexOf("due")} deve restituire 1.
     */
    @Test
    public void testIndexOfExistingElement() 
    {
        assertEquals(1, subList.indexOf("due"));
    }

    /**
     * Test del metodo {@link HList#indexOf(Object)}.
     *  Verifica la ricerca di un elemento non esistente nella sottolista (ma esistente nella lista padre fuori range).
     * Test Case Design:  Garantire che `indexOf()` si limiti a cercare solo all'interno della vista della sottolista.
     * Preconditions:  La sottolista contiene ["uno", "due", "tre"]. La lista padre contiene "quattro" all'indice 4.
     * Postconditions:  L'indice di "quattro" nella sottolista è -1.
     * Expected Result: {@code indexOf("quattro")} deve restituire -1.
     */
    @Test
    public void testIndexOfNonExistingElementInSubList() 
    {
        assertEquals(-1, subList.indexOf("quattro"));
    }

    /**
     * Test del metodo {@link HList#indexOf(Object)}.
     *  Verifica la ricerca di un elemento non presente né nella sottolista né nella lista padre.
     * Test Case Design:  Assicurarsi che `indexOf()` restituisca -1 per elementi completamente assenti.
     * Preconditions:  La sottolista contiene ["uno", "due", "tre"].
     * Postconditions:  L'indice di "non_esiste" è -1.
     * Expected Result: {@code indexOf("non_esiste")} deve restituire -1.
     */
    @Test
    public void testIndexOfCompletelyNonExistingElement() 
    {
        assertEquals(-1, subList.indexOf("non_esiste"));
    }

    /**
     * Test del metodo {@link HList#indexOf(Object)}.
     *  Verifica la ricerca di un elemento null.
     * Test Case Design:  Assicurarsi che la ricerca di valori null sia gestita correttamente.
     * Preconditions:  La sottolista contiene ["uno", "due", "tre"]. Un elemento null viene aggiunto.
     * Postconditions:  L'indice del primo elemento null è corretto.
     * Expected Result: {@code indexOf(null)} deve restituire 1.
     */
    @Test
    public void testIndexOfNullElement() 
    {
        subList.add(1, null); // subList: ["uno", null, "due", "tre"]
        assertEquals(1, subList.indexOf(null));
    }

    /**
     * Test del metodo {@link HList#lastIndexOf(Object)}.
     *  Verifica la ricerca dell'ultima occorrenza di un elemento esistente.
     * Test Case Design:  Assicurarsi che `lastIndexOf()` trovi l'ultima posizione relativa di un elemento duplicato.
     * Preconditions:  La sottolista contiene ["uno", "due", "tre"]. Aggiunto "uno" per testare.
     * Postconditions:  L'indice dell'ultima occorrenza di "uno" è 3.
     * Expected Result: {@code lastIndexOf("uno")} deve restituire 3.
     */
    @Test
    public void testLastIndexOfExistingElement() 
    {
        subList.add("uno"); // subList diventa ["uno", "due", "tre", "uno"]
        assertEquals(3, subList.lastIndexOf("uno"));
    }

    /**
     * Test del metodo {@link HList#lastIndexOf(Object)}.
     *  Verifica la ricerca dell'ultima occorrenza di un elemento non presente.
     * Test Case Design:  Assicurarsi che `lastIndexOf()` restituisca -1 per elementi assenti.
     * Preconditions:  La sottolista contiene ["uno", "due", "tre"].
     * Postconditions:  L'indice dell'ultima occorrenza di "non_esiste" è -1.
     * Expected Result: {@code lastIndexOf("non_esiste")} deve restituire -1.
     */
    @Test
    public void testLastIndexOfNonExistingElement() 
    {
        assertEquals(-1, subList.lastIndexOf("non_esiste"));
    }

    /**
     * Test del metodo {@link HList#lastIndexOf(Object)}.
     *  Verifica la ricerca dell'ultima occorrenza di un elemento null.
     * Test Case Design:  Assicurarsi che la ricerca dell'ultima occorrenza di valori null sia gestita correttamente.
     * Preconditions:  La sottolista contiene ["uno", "due", "tre"]. Più elementi null vengono aggiunti.
     * Postconditions:  L'indice dell'ultimo elemento null è corretto.
     * Expected Result: {@code lastIndexOf(null)} deve restituire 2.
     */
    @Test
    public void testLastIndexOfNullElement() 
    {
        subList.add(0, null); // subList: [null, "uno", "due", "tre"]
        subList.add(2, null); // subList: [null, "uno", null, "due", "tre"]
        assertEquals(2, subList.lastIndexOf(null));
    }


    /**
     * Test del metodo {@link HList#contains(Object)}.
     *  Verifica che la sottolista contenga un elemento presente.
     * Test Case Design:  Assicurarsi che `contains()` deleghi correttamente la ricerca all'interno del proprio range.
     * Preconditions:  La sottolista contiene ["uno", "due", "tre"].
     * Postconditions:  `contains("due")` è true.
     * Expected Result: {@code contains("due")} deve restituire true.
     */
    @Test
    public void testContainsExistingElement() 
    {
        assertTrue(subList.contains("due"));
    }

    /**
     * Test del metodo {@link HList#contains(Object)}.
     *  Verifica che la sottolista non contenga un elemento non presente.
     * Test Case Design:  Assicurarsi che `contains()` non dia falsi positivi per elementi fuori dal suo range.
     * Preconditions:  La sottolista contiene ["uno", "due", "tre"].
     * Postconditions:  `contains("sette")` è false.
     * Expected Result: {@code contains("sette")} deve restituire false.
     */
    @Test
    public void testContainsNonExistingElement() 
    {
        assertFalse(subList.contains("sette"));
    }

    /**
     * Test del metodo {@link HList#containsAll(HCollection)}.
     *  Verifica che la sottolista contenga tutti gli elementi di una collezione data.
     * Test Case Design:  Assicurarsi che `containsAll()` verifichi correttamente la presenza di tutti gli elementi
     * della collezione specificata all'interno della sottolista.
     * Preconditions:  La sottolista contiene ["uno", "due", "tre"]. Collezione con "uno", "tre".
     * Postconditions:  `containsAll` è true.
     * Expected Result: {@code containsAll} deve restituire true.
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
     *  Verifica che la sottolista non contenga tutti gli elementi di una collezione data.
     * Test Case Design:  Assicurarsi che `containsAll()` restituisca false se anche solo un elemento
     * della collezione specificata non è presente nella sottolista.
     * Preconditions:  La sottolista contiene ["uno", "due", "tre"]. Collezione con "uno", "sette".
     * Postconditions:  `containsAll` è false.
     * Expected Result: {@code containsAll} deve restituire false.
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
     *  Verifica il comportamento di `containsAll` con una collezione vuota.
     * Test Case Design:  Assicurarsi che `containsAll` restituisca true per una collezione vuota,
     * in quanto una lista contiene "tutti" gli elementi di una collezione vuota per definizione.
     * Preconditions:  La sottolista è ["uno", "due", "tre"]. Collezione vuota.
     * Postconditions:  `containsAll` è true.
     * Expected Result: {@code containsAll(emptyCollection)} deve restituire true.
     */
    @Test
    public void testContainsAllEmptyCollection() 
    {
        ListAdapter emptyCollection = new ListAdapter();
        assertTrue(subList.containsAll(emptyCollection));
    }

    /**
     * Test del metodo {@link HList#containsAll(HCollection)}.
     *  Verifica che `containsAll` lanci `NullPointerException` se la collezione è null.
     * Test Case Design:  Assicurare la conformità alla Javadoc di `Collection.containsAll()`, che specifica
     * il lancio di `NullPointerException` se la collezione è null.
     * Preconditions:  La sottolista è ["uno", "due", "tre"]. La collezione è null.
     * Postconditions:  Viene lanciata `NullPointerException`.
     * Expected Result: `NullPointerException` per `containsAll(null)`.
     */
    @Test(expected = NullPointerException.class)
    public void testContainsAllNullCollection() 
    {
        subList.containsAll(null);
    }

    // ------- TEST DEI METODI DI MODIFICA (MUTATORI) --------

    /**
     * Test del metodo {@link HList#add(Object)}.
     *  Verifica l'aggiunta di un elemento alla fine della sottolista.
     * Test Case Design:  Assicurarsi che l'aggiunta di un elemento si rifletta correttamente
     * sia nella sottolista (aggiornando la sua dimensione) che nella lista padre
     * (inserendo l'elemento nella posizione corretta e spostando i successivi).
     * Preconditions:  La sottolista è ["uno", "due", "tre"], la lista padre è ["zero", "uno", "due", "tre", "quattro", "cinque"].
     * Postconditions:  La sottolista diventa ["uno", "due", "tre", "nuovo"]. La lista padre cambia in
     * ["zero", "uno", "due", "tre", "nuovo", "quattro", "cinque"].
     * La dimensione della sottolista aumenta.
     * Expected Result: {@code add("nuovo")} deve restituire true, dimensione corretta, elemento al posto giusto.
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
     *  Verifica l'aggiunta di un elemento in una posizione specifica della sottolista.
     * Test Case Design:  Garantire che l'inserimento in posizione intermedia sposti correttamente
     * gli elementi e mantenga la coerenza tra sottolista e lista padre.
     * Preconditions:  La sottolista è ["uno", "due", "tre"].
     * Postconditions:  La sottolista diventa ["uno", "inserito", "due", "tre"]. La lista padre si aggiorna.
     * Expected Result: Elemento inserito correttamente, dimensioni aggiornate.
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
     *  Verifica che `add(int, Object)` lanci `IndexOutOfBoundsException` per indice negativo.
     * Test Case Design:  Assicurare la conformità alla Javadoc di `List.add(int, Object)`, che specifica
     * il lancio di `IndexOutOfBoundsException` per indici negativi.
     * Preconditions:  La sottolista contiene elementi.
     * Postconditions:  Viene lanciata `IndexOutOfBoundsException`.
     * Expected Result: `IndexOutOfBoundsException` per `add(-1, "a")`.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddNegativeIndex() 
    {
        subList.add(-1, "test");
    }

    /**
     * Test del metodo {@link HList#add(int, Object)}.
     *  Verifica che `add(int, Object)` lanci `IndexOutOfBoundsException` per indice maggiore della dimensione.
     * Test Case Design:  Assicurare la conformità alla Javadoc di `List.add(int, Object)`, che specifica
     * il lancio di `IndexOutOfBoundsException` se l'indice è maggiore della dimensione.
     * Preconditions:  La sottolista contiene 3 elementi.
     * Postconditions:  Viene lanciata `IndexOutOfBoundsException`.
     * Expected Result: `IndexOutOfBoundsException` per `add(4, "a")`.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddOutOfBoundsIndex() 
    {
        subList.add(4, "test"); // size is 3, valid indices 0, 1, 2, 3 (for add at end)
    }

    /**
     * Test del metodo {@link HList#remove(int)}.
     *  Verifica la rimozione di un elemento dalla sottolista.
     * Test Case Design:  Assicurarsi che la rimozione di un elemento si rifletta correttamente
     * sia nella sottolista (aggiornando la sua dimensione) che nella lista padre
     * (rimuovendo l'elemento dalla posizione corretta e spostando i successivi).
     * Preconditions:  La sottolista è ["uno", "due", "tre"].
     * Postconditions:  La sottolista diventa ["uno", "tre"]. La lista padre si aggiorna.
     * Expected Result: Elemento rimosso correttamente, dimensioni aggiornate.
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
     *  Verifica che `remove(int)` lanci `IndexOutOfBoundsException` per indice negativo.
     * Test Case Design:  Assicurare la conformità alla Javadoc di `List.remove(int)`, che specifica
     * il lancio di `IndexOutOfBoundsException` per indici negativi.
     * Preconditions:  La sottolista contiene elementi.
     * Postconditions:  Viene lanciata `IndexOutOfBoundsException`.
     * Expected Result: `IndexOutOfBoundsException` per `remove(-1)`.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveNegativeIndex() 
    {
        subList.remove(-1);
    }

    /**
     * Test del metodo {@link HList#remove(int)}.
     *  Verifica che `remove(int)` lanci `IndexOutOfBoundsException` per indice fuori limite (maggiore o uguale alla dimensione).
     * Test Case Design:  Assicurare la conformità alla Javadoc di `List.remove(int)`, che specifica
     * il lancio di `IndexOutOfBoundsException` per indici fuori range.
     * Preconditions:  La sottolista contiene 3 elementi.
     * Postconditions:  Viene lanciata `IndexOutOfBoundsException`.
     * Expected Result: `IndexOutOfBoundsException` per `remove(3)`.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveOutOfBoundsIndex() 
    {
        subList.remove(3); // size is 3, valid indices 0, 1, 2
    }

    /**
     * Test del metodo {@link HList#remove(Object)}.
     *  Verifica la rimozione della prima occorrenza di un elemento specifico dalla sottolista.
     * Test Case Design:  Assicurarsi che `remove(Object)` trovi e rimuova correttamente l'elemento,
     * e che la rimozione si propaghi alla lista padre.
     * Preconditions:  La sottolista è ["uno", "due", "tre"].
     * Postconditions:  La sottolista diventa ["uno", "tre"]. La lista padre si aggiorna.
     * Expected Result: Rimosso correttamente, dimensioni aggiornate.
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
     *  Verifica che `remove(Object)` non modifichi la lista se l'elemento non è presente.
     * Test Case Design:  Assicurarsi che il metodo gestisca correttamente il caso di un elemento assente.
     * Preconditions:  La sottolista è ["uno", "due", "tre"].
     * Postconditions:  La sottolista rimane invariata.
     * Expected Result: `remove("non_esiste")` deve restituire false e la dimensione non deve cambiare.
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
     *  Verifica la rimozione di un elemento `null`.
     * Test Case Design:  Assicurarsi che `remove(Object)` gestisca correttamente gli elementi null.
     * Preconditions:  La sottolista contiene un elemento null.
     * Postconditions:  L'elemento null viene rimosso.
     * Expected Result: `remove(null)` deve restituire true e rimuovere l'elemento null.
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
     *  Verifica la modifica di un elemento nella sottolista.
     * Test Case Design:  Assicurarsi che `set()` aggiorni correttamente l'elemento nella posizione specificata
     * e che questa modifica si rifletta nella lista padre.
     * Preconditions:  La sottolista è ["uno", "due", "tre"].
     * Postconditions:  La sottolista diventa ["uno", "modificato", "tre"]. La lista padre si aggiorna.
     * Expected Result: Elemento modificato correttamente.
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
     *  Verifica che `set` lanci `IndexOutOfBoundsException` per indice negativo.
     * Test Case Design:  Assicurare la conformità alla Javadoc di `List.set()`, che specifica
     * il lancio di `IndexOutOfBoundsException` per indici negativi.
     * Preconditions:  La sottolista contiene elementi.
     * Postconditions:  Viene lanciata `IndexOutOfBoundsException`.
     * Expected Result: `IndexOutOfBoundsException` per `set(-1, "a")`.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSetNegativeIndex() 
    {
        subList.set(-1, "test");
    }

    /**
     * Test del metodo {@link HList#set(int, Object)}.
     *  Verifica che `set` lanci `IndexOutOfBoundsException` per indice fuori limite.
     * Test Case Design:  Assicurare la conformità alla Javadoc di `List.set()`, che specifica
     * il lancio di `IndexOutOfBoundsException` per indici fuori range.
     * Preconditions:  La sottolista contiene 3 elementi.
     * Postconditions:  Viene lanciata `IndexOutOfBoundsException`.
     * Expected Result: `IndexOutOfBoundsException` per `set(3, "a")`.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSetOutOfBoundsIndex() 
    {
        subList.set(3, "test"); // size is 3, valid indices 0, 1, 2
    }


    /**
     * Test del metodo {@link HList#clear()}.
     *  Verifica che la `clear()` svuoti la sottolista e rimuova gli elementi dalla lista padre.
     * Test Case Design:  Garantire che `clear()` rimuova tutti gli elementi nel range della sottolista dalla lista padre,
     * e che la sottolista diventi effettivamente vuota.
     * Preconditions:  La sottolista contiene ["uno", "due", "tre"]. La lista padre è ["zero", "uno", "due", "tre", "quattro", "cinque"].
     * Postconditions:  La sottolista è vuota. La lista padre è ["zero", "quattro", "cinque"].
     * Expected Result: La sottolista è vuota e la lista padre contiene solo gli elementi esterni al range iniziale della sublist.
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
     *  Verifica l'aggiunta di una collezione alla fine della sottolista.
     * Test Case Design:  Assicurarsi che `addAll(HCollection)` deleghi correttamente l'aggiunta multipla,
     * mantenendo l'ordine e aggiornando le dimensioni.
     * Preconditions:  La sottolista è ["uno", "due", "tre"]. Collezione con "x", "y".
     * Postconditions:  La sottolista diventa ["uno", "due", "tre", "x", "y"]. La lista padre si aggiorna.
     * Expected Result: Dimensione corretta, elementi aggiunti correttamente.
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
     *  Verifica che `addAll` restituisca false se la collezione è vuota.
     * Test Case Design:  Assicurarsi che non venga modificata la lista e che venga restituito false
     * se non ci sono elementi da aggiungere.
     * Preconditions:  La sottolista contiene elementi. La collezione è vuota.
     * Postconditions:  La sottolista rimane invariata.
     * Expected Result: `addAll(emptyCollection)` deve restituire false.
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
     *  Verifica che `addAll` lanci `NullPointerException` se la collezione è null.
     * Test Case Design:  Assicurare la conformità alla Javadoc di `Collection.addAll()`, che specifica
     * il lancio di `NullPointerException` se la collezione è null.
     * Preconditions:  La sottolista contiene elementi. La collezione è null.
     * Postconditions:  Viene lanciata `NullPointerException`.
     * Expected Result: `NullPointerException` per `addAll(null)`.
     */
    @Test(expected = NullPointerException.class)
    public void testAddAllNullCollection() 
    {
        subList.addAll(null);
    }

    /**
     * Test del metodo {@link HList#addAll(int, HCollection)}.
     *  Verifica l'aggiunta di una collezione in una posizione specifica della sottolista.
     * Test Case Design:  Assicurarsi che l'inserimento multiplo in posizione intermedia funzioni correttamente,
     * spostando gli elementi esistenti e aggiornando le dimensioni.
     * Preconditions:  La sottolista è ["uno", "due", "tre"]. Collezione con "x", "y".
     * Postconditions:  La sottolista diventa ["uno", "x", "y", "due", "tre"]. La lista padre si aggiorna.
     * Expected Result: Dimensione corretta, elementi aggiunti correttamente.
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
     *  Verifica che `addAll(int, HCollection)` lanci `IndexOutOfBoundsException` per indice negativo.
     * Test Case Design:  Assicurare la conformità alla Javadoc di `List.addAll(int, HCollection)`, che specifica
     * il lancio di `IndexOutOfBoundsException` per indici negativi.
     * Preconditions:  La sottolista contiene elementi.
     * Postconditions:  Viene lanciata `IndexOutOfBoundsException`.
     * Expected Result: `IndexOutOfBoundsException` per `addAll(-1, collection)`.
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
     *  Verifica che `addAll(int, HCollection)` lanci `IndexOutOfBoundsException` per indice maggiore della dimensione.
     * Test Case Design:  Assicurare la conformità alla Javadoc di `List.addAll(int, HCollection)`, che specifica
     * il lancio di `IndexOutOfBoundsException` se l'indice è maggiore della dimensione.
     * Preconditions:  La sottolista contiene 3 elementi.
     * Postconditions:  Viene lanciata `IndexOutOfBoundsException`.
     * Expected Result: `IndexOutOfBoundsException` per `addAll(4, collection)`.
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
     *  Verifica la rimozione di tutti gli elementi di una collezione dalla sottolista.
     * Test Case Design:  Assicurarsi che `removeAll()` rimuova correttamente solo gli elementi
     * presenti nella collezione specificata e che si trovino nella sottolista,
     * propagando le modifiche alla lista padre.
     * Preconditions:  La sottolista è ["uno", "due", "tre"]. Collezione con "uno", "tre".
     * Postconditions:  La sottolista diventa ["due"]. La lista padre si aggiorna.
     * Expected Result: Dimensione corretta, elementi rimossi correttamente.
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
     *  Verifica che `removeAll` non modifichi la lista se nessun elemento della collezione è presente.
     * Test Case Design:  Assicurarsi che il metodo restituisca false e non modifichi la lista se non ci sono intersezioni.
     * Preconditions:  La sottolista contiene ["uno", "due", "tre"]. Collezione con "sette", "otto".
     * Postconditions:  La sottolista rimane invariata.
     * Expected Result: `removeAll(collection)` deve restituire false.
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
     *  Verifica che `removeAll` lanci `NullPointerException` se la collezione è null.
     * Test Case Design:  Assicurare la conformità alla Javadoc di `Collection.removeAll()`, che specifica
     * il lancio di `NullPointerException` se la collezione è null.
     * Preconditions:  La sottolista contiene elementi. La collezione è null.
     * Postconditions:  Viene lanciata `NullPointerException`.
     * Expected Result: `NullPointerException` per `removeAll(null)`.
     */
    @Test(expected = NullPointerException.class)
    public void testRemoveAllNullCollection() 
    {
        subList.removeAll(null);
    }

    /**
     * Test del metodo {@link HList#retainAll(HCollection)}.
     *  Verifica il mantenimento solo degli elementi di una collezione nella sottolista.
     * Test Case Design:  Assicurarsi che `retainAll()` rimuova correttamente tutti gli elementi della sottolista
     * che NON sono presenti nella collezione specificata, propagando le modifiche alla lista padre.
     * Preconditions:  La sottolista è ["uno", "due", "tre"]. Collezione con "due", "quattro".
     * Postconditions:  La sottolista diventa ["due"]. La lista padre si aggiorna.
     * Expected Result: Dimensione corretta, elementi mantenuti correttamente.
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
     *  Verifica che `retainAll` non modifichi la lista se tutti gli elementi sono da mantenere.
     * Test Case Design:  Assicurarsi che il metodo restituisca false se non ci sono modifiche da apportare.
     * Preconditions:  La sottolista contiene ["uno", "due", "tre"]. Collezione con "uno", "due", "tre", "sette".
     * Postconditions:  La sottolista rimane invariata.
     * Expected Result: `retainAll(collection)` deve restituire false.
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
     *  Verifica che `retainAll` svuoti la sottolista se nessun elemento della collezione è presente.
     * Test Case Design:  Assicurarsi che la sottolista venga svuotata se non ci sono elementi da mantenere.
     * Preconditions:  La sottolista contiene ["uno", "due", "tre"]. Collezione con "sette", "otto".
     * Postconditions:  La sottolista è vuota.
     * Expected Result: `retainAll(collection)` deve restituire true e la sottolista deve essere vuota.
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
     *  Verifica che `retainAll` lanci `NullPointerException` se la collezione è null.
     * Test Case Design:  Assicurare la conformità alla Javadoc di `Collection.retainAll()`, che specifica
     * il lancio di `NullPointerException` se la collezione è null.
     * Preconditions:  La sottolista contiene elementi. La collezione è null.
     * Postconditions:  Viene lanciata `NullPointerException`.
     * Expected Result: `NullPointerException` per `retainAll(null)`.
     */
    @Test(expected = NullPointerException.class)
    public void testRetainAllNullCollection() 
    {
        subList.retainAll(null);
    }

    // ------- TEST INTERAZIONI CON LA LISTA PADRE E SOTTOLISTE DI SOTTOLISTE --------

    /**
     * Test di interazione: modifica sulla lista padre si riflette nella sottolista.
     *  Verifica che una modifica di un elemento nella lista padre (all'interno del range della sublist)
     * si rifletta nella sottolista.
     * Test Case Design:  Questo è un comportamento chiave delle "views": le modifiche non strutturali
     * si propagano istantaneamente tra la vista e la lista backing.
     * Preconditions:  La sottolista è ["uno", "due", "tre"].
     * Postconditions:  L'elemento all'indice 2 della lista padre ("due") viene cambiato in "new value".
     * La sottolista riflette questo cambiamento.
     * Expected Result: `subList.get(1)` deve restituire "new value".
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
     *  Verifica il comportamento della sottolista quando la lista padre subisce
     * una modifica strutturale (aggiunta) *non* tramite la sottolista.
     * Test Case Design:  Questo test è cruciale per mostrare la "semantica indefinita" menzionata nella Javadoc.
     * Senza un meccanismo "fail-fast", la `SubList` non viene invalidata,
     * ma la sua `size` interna non si adatta e il suo comportamento potrebbe essere
     * inconsistente rispetto alle aspettative di una lista dinamica.
     * Si prevede che gli `get` continuino a funzionare ma sulla struttura *modificata* della parent.
     * Preconditions:  La sottolista è ["uno", "due", "tre"].
     * Postconditions:  Viene aggiunto un elemento nella lista padre all'interno del range della sottolista.
     * La sottolista continua a funzionare, ma la sua dimensione interna non cambia.
     * Expected Result: `subList.get(1)` deve restituire l'elemento appena aggiunto dalla parentList.
     * La `subList.size()` deve rimanere la stessa.
     * L'elemento che era all'ultimo indice della subList (es. "tre") potrebbe non essere più accessibile
     * tramite il suo vecchio indice nella subList (se gli elementi sono stati spostati oltre il 'size' interno).
     */
    @Test
    public void testParentStructuralModificationReflectsInSubList_Add() 
    {
        assertEquals(3, subList.size()); // Initial size of subList

        // Add an element to the parentList at an index *within* the subList's original conceptual range.
        // parentList: ["zero", "uno", "due", "tre", "quattro", "cinque"]
        // subList (offset=1, size=3): parentList[1], parentList[2], parentList[3] => ["uno", "due", "tre"]
        parentList.add(2, "ADDED_BY_PARENT");
        // Now parentList: ["zero", "uno", "ADDED_BY_PARENT", "due", "tre", "quattro", "cinque"]
        // The original elements at index 2 and 3 ("due", "tre") are shifted to 3 and 4.
        // The element at parentList[4] ("quattro") is now at parentList[5].

        // The subList's internal 'size' field remains 3, as it's not fail-fast aware.
        assertEquals(3, subList.size()); // SubList's internal size should not change automatically.

        // However, the elements accessed via subList.get(relative_index) should reflect the parent's new state.
        assertEquals("uno", subList.get(0)); // parent.get(1 + 0) = parent.get(1)
        assertEquals("ADDED_BY_PARENT", subList.get(1)); // parent.get(1 + 1) = parent.get(2)
        assertEquals("due", subList.get(2)); // parent.get(1 + 2) = parent.get(3)

        // Attempting to access an index beyond the original subList.size() will throw IndexOutOfBoundsException
        // even if the parentList has grown, because subList's internal size check still applies.
        try 
        {
            subList.get(3); // This would try to access parent.get(1+3) = parent.get(4) which is "tre"
            fail("Expected IndexOutOfBoundsException as subList.size() is not updated externally.");
        } 
        catch (IndexOutOfBoundsException e) 
        {
            // Expected behavior
        }
        // This test highlights the "undefined semantics" mentioned in the Javadoc.
        // The subList no longer accurately reflects the *size* or the *exact range* it originally intended
        // if the backing list is modified externally.
    }


    /**
     * Test del metodo {@link HList#subList(int, int)} di una `SubList`.
     *  Verifica la creazione di una sottolista da una sottolista esistente.
     * Test Case Design:  Assicurarsi che la creazione di sottoliste ricorsive funzioni correttamente,
     * con il calcolo cumulativo dell'offset rispetto alla lista originale.
     * Preconditions:  La sottolista iniziale è ["uno", "due", "tre"].
     * Postconditions:  Viene creata una sottolista interna che è ["due"].
     * Expected Result: Dimensione corretta e contenuto corretto.
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
     * Test del metodo {@link HList#subList(int, int)} su una `SubList`.
     *  Verifica che `subList` lanci `IndexOutOfBoundsException` per `fromIndex` negativo.
     * Test Case Design:  Assicurare la conformità alla Javadoc e la corretta validazione degli indici.
     * Preconditions:  La sottolista contiene elementi.
     * Postconditions:  Viene lanciata `IndexOutOfBoundsException`.
     * Expected Result: `IndexOutOfBoundsException` per `subList(-1, 1)`.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSubListFromSubListNegativeFromIndex() 
    {
        subList.subList(-1, 1);
    }

    /**
     * Test del metodo {@link HList#subList(int, int)} su una `SubList`.
     *  Verifica che `subList` lanci `IndexOutOfBoundsException` per `toIndex` fuori limite.
     * Test Case Design:  Assicurare la conformità alla Javadoc e la corretta validazione degli indici.
     * Preconditions:  La sottolista contiene 3 elementi.
     * Postconditions:  Viene lanciata `IndexOutOfBoundsException`.
     * Expected Result: `IndexOutOfBoundsException` per `subList(0, 4)`.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSubListFromSubListOutOfBoundsToIndex() 
    {
        subList.subList(0, 4); // size is 3, valid toIndex is up to 3 (exclusive)
    }

    /**
     * Test del metodo {@link HList#subList(int, int)} su una `SubList`.
     *  Verifica che `subList` lanci `IndexOutOfBoundsException` per `fromIndex` maggiore di `toIndex`.
     * Test Case Design:  Assicurare la conformità alla Javadoc e la corretta validazione degli indici.
     * Preconditions:  La sottolista contiene elementi.
     * Postconditions:  Viene lanciata `IndexOutOfBoundsException`.
     * Expected Result: `IndexOutOfBoundsException` per `subList(2, 1)`.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSubListFromSubListFromGreaterThanTo() 
    {
        subList.subList(2, 1);
    }


    /**
     * Test del metodo {@link HList#toArray()}.
     *  Verifica che `toArray()` restituisca un array contenente tutti gli elementi della sottolista.
     * Test Case Design:  Assicurarsi che la conversione in array funzioni correttamente per la vista.
     * Preconditions:  La sottolista contiene ["uno", "due", "tre"].
     * Postconditions:  Viene restituito un array contenente ["uno", "due", "tre"].
     * Expected Result: Il contenuto dell'array deve corrispondere agli elementi della sottolista.
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
     *  Verifica che `toArray(T[] a)` riempia l'array fornito se è abbastanza grande.
     * Test Case Design:  Assicurarsi che la sovrascrittura di `toArray(T[] a)` gestisca correttamente il riutilizzo
     * dell'array passato.
     * Preconditions:  La sottolista contiene ["uno", "due", "tre"]. Viene fornito un array di dimensione sufficiente.
     * Postconditions:  L'array fornito viene riempito con gli elementi della sottolista e l'elemento successivo è null.
     * Expected Result: Il contenuto dell'array deve corrispondere agli elementi della sottolista e la lunghezza corretta.
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
     *  Verifica che `toArray(T[] a)` crei un nuovo array se quello fornito è troppo piccolo.
     * Test Case Design:  Assicurarsi che venga creato un nuovo array della dimensione corretta se l'array passato non è sufficiente.
     * Preconditions:  La sottolista contiene ["uno", "due", "tre"]. Viene fornito un array troppo piccolo.
     * Postconditions:  Viene restituito un nuovo array con gli elementi della sottolista.
     * Expected Result: Un nuovo array di dimensione 3 con il contenuto corretto.
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
     *  Verifica che `toArray(T[] a)` lanci `NullPointerException` se l'array è null.
     * Test Case Design:  Assicurare la conformità alla Javadoc di `Collection.toArray(T[] a)`, che specifica
     * il lancio di `NullPointerException` se l'array è null.
     * Preconditions:  La sottolista contiene elementi. L'array è null.
     * Postconditions:  Viene lanciata `NullPointerException`.
     * Expected Result: `NullPointerException` per `toArray(null)`.
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
     *  Verifica che {@code iterator()} restituisca un iteratore valido e funzionante.
     * Test Case Design:  Assicurarsi che l'iteratore ottenuto dalla sottolista permetta di scorrere i suoi elementi.
     * Preconditions:  La sottolista contiene ["uno", "due", "tre"].
     * Postconditions:  L'iteratore scorre correttamente gli elementi.
     * Expected Result: L'iteratore restituisce gli elementi nell'ordine corretto.
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
     *  Verifica che {@code listIterator()} restituisca un list iterator valido e funzionante.
     * Test Case Design:  Assicurarsi che il list iterator ottenuto dalla sottolista permetta di scorrere i suoi elementi.
     * Preconditions:  La sottolista contiene ["uno", "due", "tre"].
     * Postconditions:  Il list iterator scorre correttamente gli elementi.
     * Expected Result: Il list iterator restituisce gli elementi nell'ordine corretto.
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
     *  Verifica che {@code listIterator(int)} restituisca un list iterator valido
     * che inizia dalla posizione specificata.
     * Test Case Design:  Assicurarsi che il list iterator possa essere posizionato correttamente all'inizio.
     * Preconditions:  La sottolista contiene ["uno", "due", "tre"].
     * Postconditions:  Il list iterator inizia dall'indice 0.
     * Expected Result: Il primo elemento restituito da `next()` è "uno".
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
     *  Verifica che {@code listIterator(int)} restituisca un list iterator valido
     * che inizia da una posizione intermedia.
     * Test Case Design:  Assicurarsi che il list iterator possa essere posizionato correttamente.
     * Preconditions:  La sottolista contiene ["uno", "due", "tre"].
     * Postconditions:  Il list iterator inizia dall'indice 1.
     * Expected Result: Il primo elemento restituito da `next()` è "due".
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
     *  Verifica che {@code listIterator(int)} restituisca un list iterator valido
     * che inizia alla fine della lista (pronto per aggiungere).
     * Test Case Design:  Assicurarsi che un list iterator possa essere posizionato alla fine della lista,
     * consentendo l'aggiunta.
     * Preconditions:  La sottolista contiene ["uno", "due", "tre"].
     * Postconditions:  Il list iterator inizia alla fine della lista e non ha un prossimo elemento.
     * Expected Result: {@code hasNext()} restituisce false, `nextIndex()` è la dimensione.
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
     *  Verifica che `listIterator(int)` lanci `IndexOutOfBoundsException` per indice negativo.
     * Test Case Design:  Assicurare la conformità alla Javadoc di `ListIterator`, che specifica
     * il lancio di `IndexOutOfBoundsException` per indici negativi.
     * Preconditions:  La sottolista contiene elementi.
     * Postconditions:  Viene lanciata `IndexOutOfBoundsException`.
     * Expected Result: `IndexOutOfBoundsException` per `listIterator(-1)`.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testListIteratorNegativeIndex() 
    {
        subList.listIterator(-1);
    }

    /**
     * Test del metodo {@link HList#listIterator(int)}.
     *  Verifica che `listIterator(int)` lanci `IndexOutOfBoundsException` per indice maggiore della dimensione.
     * Test Case Design:  Assicurare la conformità alla Javadoc di `ListIterator`, che specifica
     * il lancio di `IndexOutOfBoundsException` se l'indice è maggiore della dimensione.
     * Preconditions:  La sottolista contiene 3 elementi.
     * Postconditions:  Viene lanciata `IndexOutOfBoundsException`.
     * Expected Result: `IndexOutOfBoundsException` per `listIterator(4)`.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testListIteratorOutOfBoundsIndex() 
    {
        subList.listIterator(subList.size() + 1); // size is 3, valid indices up to 3 (exclusive for next(), inclusive for add)
    }
}