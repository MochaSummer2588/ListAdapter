//Alberto Bortoletto 2101761

package myTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore; // Importa l'annotazione Ignore se necessario

import myAdapter.*;
import myExceptions.IllegalStateException; // Assicurati che questo sia il tuo package per IllegalStateException

import java.util.NoSuchElementException; // Per i casi in cui next() o previous() sono chiamati su un iteratore esaurito

/**
 * <b>Summary:</b>
 * <p>
 * Questa classe contiene una suite di test per {@link myAdapter.ListIterator} focalizzata sul comportamento su una lista vuota.
 * Sono presenti test per tutti i costruttori e i metodi principali dell'iteratore: navigazione (hasNext, next, hasPrevious, previous, nextIndex, previousIndex),
 * modifica (add, remove, set), gestione di elementi null, e verifica delle eccezioni.
 * Ogni metodo è testato per il comportamento atteso su una lista vuota e per la corretta gestione di input non validi.
 * <br>
 * <b>Test Case Design:</b>
 * <p>
 * La motivazione di questa suite è assicurare che l'iteratore gestisca correttamente tutti i casi limite e le operazioni su una lista vuota,
 * senza lanciare eccezioni inattese o restituire risultati errati. Si verifica che le operazioni di aggiunta inizializzino correttamente la lista,
 * che le ricerche e rimozioni su elementi inesistenti siano sicure, e che le eccezioni siano lanciate dove previsto.
 * La separazione dai test su liste popolate permette di isolare i comportamenti di bordo e prevenire regressioni.
 * Versione JUnit: **JUnit 4.13.2**
 * Versione Hamcrest: **Hamcrest 1.3**
 */
public class TestListIteratorEmpty
{
    private ListAdapter list;
    private HListIterator iterator; // Oggetto ListIterator da testare

    /**
     * Costruttore predefinito per i test di {@code TestListIteratorEmpty}.
     * Non esegue inizializzazioni specifiche, affidandosi al metodo {@code setup()}.
     */
    public TestListIteratorEmpty() 
    {
        // Nessuna logica di inizializzazione complessa qui, JUnit si occupa del setup.
    }

    /**
     * Configura l'ambiente di test prima di ogni test case.
     * Inizializza una ListAdapter vuota e un ListIterator.
     */
    @Before
    public void setUp()
    {
        list = new ListAdapter();
        iterator = list.listIterator(); // Cursore a 0, lastReturned a -1
    }

    // --- TEST COSTRUTTORI ---

    /**
     * Test del costruttore {@link myAdapter.ListIterator#ListIterator(myAdapter.ListAdapter)} su una lista vuota.
     * <p>
     * Summary: Il test verifica che il costruttore {@code ListIterator(ListAdapter list)} inizializzi l'iteratore correttamente all'inizio di una lista vuota.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è garantire che, anche su una lista vuota,
     * l'iteratore sia posizionato correttamente all'inizio (cursore a 0, lastReturned a -1).
     * <p>
     * Test Description: 1) Verifica che l'iteratore non abbia elementi successivi.
     *                   2) Verifica che l'iteratore non abbia elementi precedenti.
     *                   3) Verifica che {@code nextIndex()} restituisca 0.
     * 4) Verifica {@code previousIndex()} restituisca -1.
     * <p>
     * Preconditions: Lista vuota.
     * <p>
     * Postconditions: La lista rimane vuota e l'iteratore è posizionato all'inizio della lista.
     * <p>
     * Expected Result: {@code hasNext()} deve essere false, {@code hasPrevious()} deve essere false,
     * {@code nextIndex()} deve essere 0, {@code previousIndex()} deve essere -1.
     */
    @Test
    public void testConstructorDefaultPositionEmptyList()
    {
        assertFalse(iterator.hasNext());
        assertFalse(iterator.hasPrevious());
        assertEquals(0, iterator.nextIndex());
        assertEquals(-1, iterator.previousIndex());
    }

    /**
     * Test del costruttore {@link myAdapter.ListIterator#ListIterator(myAdapter.ListAdapter, int)} con indice 0 su lista vuota.
     * <p>
     * Summary: Verifica che il costruttore {@code ListIterator(ListAdapter list, int index)} inizializzi l'iteratore correttamente
     * all'indice 0 di una lista vuota.
     * <p>
     * Test Case Design: La motivazione è verificare che l'indice 0 sia un punto valido per l'iteratore anche su lista vuota,
     * rappresentando la posizione "prima del primo elemento" (che non esiste).
     * <p>
     * Test Description: 1) Si crea un iteratore all'indice 0 su una lista vuota.
     *                   2) Si verifica che non abbia elementi successivi o precedenti.
     *                   3) Si verificano gli indici restituiti da {@code nextIndex()} e {@code previousIndex()}.
     * <p>
     * Preconditions: Lista vuota.
     * <p>
     * Postconditions: La lista rimane vuota. L'iteratore è posizionato all'indice 0.
     * <p>
     * Expected Result: {@code nextIndex()} deve essere 0, {@code previousIndex()} deve essere -1,
     * {@code hasNext()} deve essere false, {@code hasPrevious()} deve essere false.
     */
    @Test
    public void testConstructorSpecificPositionZeroEmptyList()
    {
        iterator = list.listIterator(0); // Cursore a 0, lastReturned a -1
        assertEquals(0, iterator.nextIndex());
        assertEquals(-1, iterator.previousIndex());
        assertFalse(iterator.hasNext());
        assertFalse(iterator.hasPrevious());
    }

    /**
     * Test del costruttore {@link myAdapter.ListIterator#ListIterator(myAdapter.ListAdapter, int)} con indice negativo su lista vuota.
     * <p>
     * Summary: Verifica che il costruttore lanci {@code IndexOutOfBoundsException} se l'indice al quale si vuole creare l'iteratore è negativo,
     * anche su una lista vuota.
     * <p>
     * Test Case Design: Si vuole garantire che il costruttore rispetti i limiti inferiori dell'indice, indipendentemente dalla dimensione della lista.
     * <p>
     * Test Description: 1) Si tenta di creare un iteratore con un indice negativo (-1) su una lista vuota.
     * <p>
     * Preconditions: Una lista vuota.
     * <p>
     * Postconditions: Nessuna modifica alla lista. Viene lanciata un'eccezione.
     * <p>
     * Expected Result: {@code IndexOutOfBoundsException} deve essere lanciata nel momento della chiamata al metodo {@code list.listIterator(-1)}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testConstructorIndexOutOfBoundsNegativeEmptyList()
    {
        list.listIterator(-1);
    }

    /**
     * Test del costruttore {@link myAdapter.ListIterator#ListIterator(myAdapter.ListAdapter, int)} con indice maggiore della dimensione su lista vuota.
     * <p>
     * Summary: Il test verifica che il costruttore lanci {@code IndexOutOfBoundsException} se l'indice è maggiore della dimensione della lista,
     * anche se la lista è vuota (quindi se l'indice è > 0).
     * <p>
     * Test Case Design: La motivazione dietro a questo test è garantire che il costruttore rispetti i limiti superiori della lista,
     * specialmente quando l'unica posizione valida è 0 per una lista vuota.
     * <p>
     * Test Description: 1) Si tenta di creare un iteratore con un indice maggiore della dimensione della lista (ad esempio indice 1 su una lista vuota).
     * <p>
     * Preconditions: Lista vuota.
     * <p>
     * Postconditions: Nessuna modifica alla lista. Viene lanciata un'eccezione.
     * <p>
     * Expected Result: {@code IndexOutOfBoundsException} deve essere lanciata nel momento della chiamata al metodo {@code list.listIterator(1)}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testConstructorIndexOutOfBoundsGreaterThanSizeEmptyList()
    {
        list.listIterator(1); // list.size() è 0, quindi 1 è fuori limite
    }

    // --- TEST METODI DI NAVIGAZIONE ---

    /**
     * Test del metodo {@link myAdapter.ListIterator#hasNext()} su lista vuota.
     * <p>
     * Summary: Verifica che {@code hasNext()} restituisca {@code false} quando la lista è vuota.
     * <p>
     * Test Case Design: La motivazione è garantire che l'iteratore riconosca correttamente l'assenza di elementi successivi in una lista vuota.
     * <p>
     * Test Description: 1) Si verifica che {@code hasNext()} restituisca {@code false}.
     * <p>
     * Preconditions: Lista vuota. L'iteratore è all'inizio.
     * <p>
     * Postconditions: Nessuna modifica allo stato dell'iteratore o della lista.
     * <p>
     * Expected Result: {@code hasNext()} deve restituire {@code false}.
     */
    @Test
    public void testHasNextEmptyList()
    {
        assertFalse(iterator.hasNext());
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#next()} su lista vuota.
     * <p>
     * Summary: Verifica che {@code next()} lanci {@code NoSuchElementException} quando la lista è vuota.
     * <p>
     * Test Case Design: La motivazione è assicurare che l'iteratore gestisca correttamente il tentativo di accedere a un elemento inesistente in una lista vuota.
     * <p>
     * Test Description: 1) Si tenta di chiamare {@code next()} su un iteratore di una lista vuota.
     * <p>
     * Preconditions: Lista vuota. L'iteratore è all'inizio.
     * <p>
     * Postconditions: Nessuna modifica alla lista. Viene lanciata un'eccezione.
     * <p>
     * Expected Result: {@code NoSuchElementException} deve essere lanciata.
     */
    @Test(expected = NoSuchElementException.class)
    public void testNextEmptyList()
    {
        iterator.next();
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#hasPrevious()} su lista vuota.
     * <p>
     * Summary: Verifica che {@code hasPrevious()} restituisca {@code false} quando la lista è vuota.
     * <p>
     * Test Case Design: La motivazione è garantire che l'iteratore riconosca correttamente l'assenza di elementi precedenti in una lista vuota.
     * <p>
     * Test Description: 1) Si verifica che {@code hasPrevious()} restituisca {@code false}.
     * <p>
     * Preconditions: Lista vuota. L'iteratore è all'inizio.
     * <p>
     * Postconditions: Nessuna modifica allo stato dell'iteratore o della lista.
     * <p>
     * Expected Result: {@code hasPrevious()} deve restituire {@code false}.
     */
    @Test
    public void testHasPreviousEmptyList()
    {
        assertFalse(iterator.hasPrevious());
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#previous()} su lista vuota.
     * <p>
     * Summary: Verifica che {@code previous()} lanci {@code NoSuchElementException} quando la lista è vuota.
     * <p>
     * Test Case Design: La motivazione è assicurare che l'iteratore gestisca correttamente il tentativo di accedere a un elemento precedente inesistente in una lista vuota.
     * <p>
     * Test Description: 1) Si tenta di chiamare {@code previous()} su un iteratore di una lista vuota.
     * <p>
     * Preconditions: Lista vuota. L'iteratore è all'inizio.
     * <p>
     * Postconditions: Nessuna modifica alla lista. Viene lanciata un'eccezione.
     * <p>
     * Expected Result: {@code NoSuchElementException} deve essere lanciata.
     */
    @Test(expected = NoSuchElementException.class)
    public void testPreviousEmptyList()
    {
        iterator.previous();
    }

    // --- TEST METODI DI INDICE ---

    /**
     * Test del metodo {@link myAdapter.ListIterator#nextIndex()} su lista vuota.
     * <p>
     * Summary: Il test verifica che {@code nextIndex()} restituisca 0 in lista è vuota.
     * <p>
     * Test Case Design: Si vuole garantire che l'indice del prossimo elemento sia 0, indicando che non ci sono elementi successivi.
     * <p>
     * Test Description: 1) Si verifica che {@code nextIndex()} restituisca 0.
     * <p>
     * Preconditions: Lista vuota. L'iteratore è all'inizio.
     * <p>
     * Postconditions: Nessuna modifica allo stato dell'iteratore o della lista.
     * <p>
     * Expected Result: {@code nextIndex()} deve essere 0.
     */
    @Test
    public void testNextIndexEmptyList()
    {
        assertEquals(0, iterator.nextIndex());
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#previousIndex()} su lista vuota.
     * <p>
     * Summary: Il test verifica che {@code previousIndex()} restituisca -1 quando la lista è vuota.
     * <p>
     * Test Case Design: Si vuole garantire che l'indice dell'elemento precedente sia -1, indicando che non ci sono elementi precedenti.
     * <p>
     * Test Description: 1) Si verifica che {@code previousIndex()} restituisca -1.
     * <p>
     * Preconditions: Lista vuota. L'iteratore è all'inizio.
     * <p>
     * Postconditions: Nessuna modifica allo stato dell'iteratore o della lista.
     * <p>
     * Expected Result: {@code previousIndex()} deve essere -1.
     */
    @Test
    public void testPreviousIndexEmptyList()
    {
        assertEquals(-1, iterator.previousIndex());
    }

    // --- TEST METODI DI MODIFICA ---

    /**
     * Test del metodo {@link myAdapter.ListIterator#remove()} su lista vuota.
     * <p>
     * Summary: Verifica che {@code remove()} lanci {@code IllegalStateException} su una lista vuota,
     * poiché non c'è stato alcun elemento restituito da {@code next()} o {@code previous()}.
     * <p>
     * Test Case Design: La motivazione è garantire che {@code remove()} non possa essere chiamato quando non c'è un elemento "lastReturned" da rimuovere.
     * <p>
     * Test Description: 1) Si tenta di chiamare {@code remove()} immediatamente.
     * <p>
     * Preconditions: Lista vuota. L'iteratore è all'inizio.
     * <p>
     * Postconditions: La lista e l'iteratore rimangono invariati. Viene lanciata un'eccezione.
     * <p>
     * Expected Result: {@code IllegalStateException} deve essere lanciata.
     */
    @Test(expected = IllegalStateException.class)
    public void testRemoveEmptyList()
    {
        iterator.remove();
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#set(Object)} su lista vuota.
     * <p>
     * Summary: Verifica che {@code set(Object o)} lanci {@code IllegalStateException} su una lista vuota,
     * poiché non c'è alcun elemento da sostituire (nessun "lastReturned").
     * <p>
     * Test Case Design: La motivazione è garantire che {@code set()} non possa essere chiamato quando non c'è un elemento "lastReturned" da modificare.
     * <p>
     * Test Description: 1) Si crea un iteratore su una lista vuota.
     *                   2) Si tenta di chiamare {@code set()} immediatamente.
     * <p>
     * Preconditions: Lista vuota. L'iteratore è all'inizio.
     * <p>
     * Postconditions: La lista e l'iteratore rimangono invariati. Viene lanciata un'eccezione.
     * <p>
     * Expected Result: {@code IllegalStateException} deve essere lanciata.
     */
    @Test(expected = IllegalStateException.class)
    public void testSetEmptyList()
    {
        iterator.set("elemento");
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#add(Object)} su lista vuota.
     * <p>
     * Summary: Verifica che {@code add(Object o)} inserisca correttamente un elemento in una lista vuota.
     * <p>
     * Test Case Design: Garantire che {@code add()} funzioni come previsto anche quando la lista è inizialmente vuota.
     * <p>
     * Test Description: 1) Si aggiunge un elemento alla lista vuota.
     *                   2) Si verifica che la dimensione della lista sia 1.
     *                   3) Si verifica che l'elemento aggiunto sia presente e al corretto indice.
     *                   4) Si verifica che il cursore sia avanzato e {@code lastReturned} resettato.
     * <p>
     * Preconditions: Lista vuota. L'iteratore è all'inizio.
     * <p>
     * Postconditions: La lista contiene l'elemento aggiunto. L'iteratore è posizionato dopo l'elemento aggiunto.
     * <p>
     * Expected Result: La lista contiene l'elemento, la dimensione è 1, {@code nextIndex()} è 1, {@code previousIndex()} è 0.
     */
    @Test
    public void testAddEmptyList()
    {
        iterator.add("primo elemento"); // list.add(0, "primo elemento"). list: ["primo elemento"]. Cursore a 1. lastReturned = -1.
        assertEquals(1, list.size());
        assertEquals("primo elemento", list.get(0));
        assertEquals(1, iterator.nextIndex());
        assertEquals(0, iterator.previousIndex());
        assertTrue(iterator.hasPrevious());
        assertFalse(iterator.hasNext()); // Il cursore è alla fine della lista (dopo il "primo elemento")
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#add(Object)} di un elemento null su lista vuota.
     * <p>
     * Summary: Verifica che {@code add(null)} funzioni correttamente su una lista vuota.
     * <p>
     * Test Case Design: Assicurarsi che l'iteratore possa aggiungere e gestire {@code null} come elemento valido anche in una lista vuota.
     * <p>
     * Test Description: 1) Si aggiunge un elemento {@code null} alla lista vuota.
     *                   2) Si verifica che la dimensione sia 1 e che l'elemento sia {@code null}.
     *                   3) Si naviga l'iteratore per recuperare il {@code null} e verificare che sia corretto.
     * <p>
     * Preconditions: Lista vuota. L'iteratore è all'inizio.
     * <p>
     * Postconditions: La lista contiene un singolo elemento {@code null}. L'iteratore è posizionato dopo l'elemento aggiunto.
     * <p>
     * Expected Result: L'elemento {@code null} viene aggiunto e restituito correttamente.
     */
    @Test
    public void testAddNullElementEmptyList()
    {
        iterator.add(null); // list.add(0, null). list: [null]. Cursore a 1. lastReturned = -1.
        assertEquals(1, list.size());
        assertNull(list.get(0));
        assertEquals(1, iterator.nextIndex());
        assertEquals(0, iterator.previousIndex());
        assertTrue(iterator.hasPrevious());
        assertFalse(iterator.hasNext());

        assertEquals(null, iterator.previous());
        assertEquals(-1, iterator.previousIndex());
        assertEquals(0, iterator.nextIndex());
    }

    /**
     * Test per la sequenza add-remove su lista inizialmente vuota.
     * <p>
     * Summary: Verifica che {@code add()} e {@code remove()} funzionino correttamente in sequenza su una lista inizialmente vuota.
     * <p>
     * Test Case Design: Questo test valuta la gestione degli stati dell'iteratore dopo una modifica di aggiunta e poi rimozione.
     * <p>
     * Test Description: 1) Aggiungi un elemento alla lista vuota.
     *                   2) Chiama {@code next()} per spostare l'iteratore {@code lastReturned}.
     *                   3) Chiama {@code remove()}.
     *                   4) Verifica che la lista sia di nuovo vuota e che l'iteratore sia nello stato iniziale.
     * <p>
     * Preconditions: Lista vuota.
     * <p>
     * Postconditions: La lista è nuovamente vuota. L'iteratore è resettato allo stato iniziale.
     * <p>
     * Expected Result: La lista è vuota dopo la sequenza, e l'iteratore riflette questo stato.
     */
    @Test
    public void testAddThenRemoveEmptyList()
    {
        iterator.add("temp"); // Aggiunge "temp". list: ["temp"]. Cursore a 1. lastReturned = -1.
        assertEquals(1, list.size());

        assertEquals("temp", iterator.previous()); 
        
        iterator.remove();
        assertEquals(0, list.size());
        assertFalse(list.contains("temp"));
        
        // Lo stato dell'iteratore dovrebbe essere come se fosse stato appena creato su una lista vuota
        assertFalse(iterator.hasNext());
        assertFalse(iterator.hasPrevious());
        assertEquals(0, iterator.nextIndex());
        assertEquals(-1, iterator.previousIndex());
    }
}