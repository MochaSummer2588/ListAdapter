package myTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore; // Importa l'annotazione Ignore se necessario

import myAdapter.*;
import myExceptions.IllegalStateException; // Assicurati che questo sia il tuo package per IllegalStateException

/**
 * Suite di test per la classe {@link myAdapter.ListIterator}
 * che implementa {@link myAdapter.HListIterator}, specificamente su una lista vuota.
 * <p>
 * Riassunto: Verifica il corretto funzionamento dell'iteratore bidirezionale ListIterator
 * sui casi limite di una lista vuota, inclusa la corretta emissione delle eccezioni previste.
 * <p>
 * Design: Utilizza JUnit 4.13.2. Ogni test case configura uno scenario specifico
 * per l'iteratore e verifica il comportamento atteso.
 */
public class TestListIteratorEmpty
{
    private ListAdapter list;
    private HListIterator iterator; // Oggetto ListIterator da testare

    /**
     * Configura l'ambiente di test prima di ogni test case.
     * Inizializza una ListAdapter vuota e un ListIterator su di essa.
     */
    @Before
    public void setUp() {
        list = new ListAdapter(); // La lista è vuota
        iterator = list.listIterator(); // Crea un iteratore standard, cursore a 0
    }

    // ------- TEST DEI COSTRUTTORI -------

    /**
     * Test del costruttore ListIterator(ListAdapter list) su lista vuota.
     * @summary Verifica che l'iteratore sia creato correttamente su una lista vuota.
     * @description Il cursore deve essere a 0, lastReturned a -1.
     */
    @Test
    public void testConstructorEmptyList() {
        assertNotNull(iterator);
        assertFalse(iterator.hasNext());
        assertFalse(iterator.hasPrevious());
        assertEquals(0, iterator.nextIndex());
        assertEquals(-1, iterator.previousIndex());
    }

    /**
     * Test del costruttore ListIterator(ListAdapter list, int index) con index 0 su lista vuota.
     * @summary Verifica che l'iteratore sia creato correttamente con indice 0 su lista vuota.
     * @description L'indice 0 è valido per una lista vuota e non deve lanciare eccezioni.
     * @pre La lista è vuota.
     * @post L'iteratore è posizionato correttamente all'indice 0.
     */
    @Test
    public void testConstructorEmptyListAtIndex0() {
        // Questo test è stato discusso in precedenza come un fallimento del test,
        // perché il test si aspettava un'eccezione, ma l'operazione è valida.
        // Qui testiamo il comportamento corretto.
        HListIterator it = list.listIterator(0); // Non deve lanciare eccezioni
        assertNotNull(it);
        assertFalse(it.hasNext());
        assertFalse(it.hasPrevious());
        assertEquals(0, it.nextIndex());
        assertEquals(-1, it.previousIndex());
    }

    /**
     * Test del costruttore ListIterator(ListAdapter list, int index) con index negativo.
     * @summary Verifica che venga lanciata IndexOutOfBoundsException per indice negativo.
     * @pre La lista è vuota.
     * @post Nessun iteratore creato, IndexOutOfBoundsException lanciata.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testConstructorEmptyListNegativeIndex() {
        list.listIterator(-1);
    }

    /**
     * Test del costruttore ListIterator(ListAdapter list, int index) con index > size.
     * @summary Verifica che venga lanciata IndexOutOfBoundsException per indice maggiore della dimensione.
     * @pre La lista è vuota (size = 0).
     * @post Nessun iteratore creato, IndexOutOfBoundsException lanciata.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testConstructorEmptyListIndexGreaterThanSize() {
        list.listIterator(1); // list.size() è 0, quindi 1 è fuori limite
    }

    // ------- TEST DEI METODI DI NAVIGAZIONE -------

    /**
     * Test di hasNext() su una lista vuota.
     * @summary Verifica che hasNext() restituisca false.
     */
    @Test
    public void testHasNextEmptyList() {
        assertFalse(iterator.hasNext());
    }

    /**
     * Test di next() su una lista vuota.
     * @summary Verifica che next() lanci NoSuchElementException.
     */
    @Test(expected = java.util.NoSuchElementException.class)
    public void testNextEmptyList() {
        iterator.next();
    }

    /**
     * Test di hasPrevious() su una lista vuota.
     * @summary Verifica che hasPrevious() restituisca false.
     */
    @Test
    public void testHasPreviousEmptyList() {
        assertFalse(iterator.hasPrevious());
    }

    /**
     * Test di previous() su una lista vuota.
     * @summary Verifica che previous() lanci NoSuchElementException.
     */
    @Test(expected = java.util.NoSuchElementException.class)
    public void testPreviousEmptyList() {
        iterator.previous();
    }

    // ------- TEST DEI METODI DI INDICE -------

    /**
     * Test di nextIndex() su una lista vuota.
     * @summary Verifica che nextIndex() restituisca 0.
     */
    @Test
    public void testNextIndexEmptyList() {
        assertEquals(0, iterator.nextIndex());
    }

    /**
     * Test di previousIndex() su una lista vuota.
     * @summary Verifica che previousIndex() restituisca -1.
     */
    @Test
    public void testPreviousIndexEmptyList() {
        assertEquals(-1, iterator.previousIndex());
    }

    // ------- TEST DEI METODI DI MODIFICA -------

    /**
     * Test di remove() su una lista vuota senza aver chiamato next()/previous().
     * @summary Verifica che remove() lanci IllegalStateException.
     */
    @Test(expected = IllegalStateException.class)
    public void testRemoveEmptyListNoNextPrevious() {
        iterator.remove();
    }

    /**
     * Test di set() su una lista vuota senza aver chiamato next()/previous().
     * @summary Verifica che set() lanci IllegalStateException.
     */
    @Test(expected = IllegalStateException.class)
    public void testSetEmptyListNoNextPrevious() {
        iterator.set("elemento");
    }

    /**
     * Test di add() su una lista vuota.
     * @summary Verifica che add() inserisca l'elemento e aggiorni gli indici.
     */
    @Test
    public void testAddEmptyList() {
        iterator.add("nuovo");
        
        assertEquals(1, list.size());
        assertEquals("nuovo", list.get(0));
        
        // Dopo add, il cursore avanza di 1 e lastReturned è resettato a -1
        assertEquals(1, iterator.nextIndex()); // Cursore ora punta alla fine
        assertEquals(0, iterator.previousIndex()); // previous() dovrebbe restituire l'elemento appena aggiunto
        
        assertTrue(iterator.hasPrevious()); // Ora può tornare indietro all'elemento appena aggiunto
        assertFalse(iterator.hasNext()); // E non ci sono elementi successivi dopo l'add
        
        // Verifica che previous() funzioni correttamente dopo l'add
        assertEquals("nuovo", iterator.previous()); // Cursore torna a 0
        assertEquals(0, iterator.nextIndex());
        assertEquals(-1, iterator.previousIndex());
        assertFalse(iterator.hasPrevious());
        assertTrue(iterator.hasNext());
    }

    /**
     * Test di add() di più elementi su una lista vuota.
     * @summary Verifica che add() inserisca elementi multipli correttamente.
     */
    @Test
    public void testAddMultipleEmptyList() {
        iterator.add("primo"); // list: ["primo"], cursor: 1
        iterator.add("secondo"); // list: ["primo", "secondo"], cursor: 2
        iterator.add("terzo"); // list: ["primo", "secondo", "terzo"], cursor: 3
        
        assertEquals(3, list.size());
        assertEquals("primo", list.get(0));
        assertEquals("secondo", list.get(1));
        assertEquals("terzo", list.get(2));
        
        assertEquals(3, iterator.nextIndex());
        assertEquals(2, iterator.previousIndex()); // previous() dovrebbe restituire "terzo"
        
        // Verifica navigazione all'indietro
        assertEquals("terzo", iterator.previous()); // cursor: 2
        assertEquals("secondo", iterator.previous()); // cursor: 1
        assertEquals("primo", iterator.previous()); // cursor: 0
        
        assertFalse(iterator.hasPrevious());
        assertEquals(0, iterator.nextIndex());
        assertEquals(-1, iterator.previousIndex());
        
        // Verifica navigazione in avanti
        assertEquals("primo", iterator.next()); // cursor: 1
        assertEquals("secondo", iterator.next()); // cursor: 2
        assertEquals("terzo", iterator.next()); // cursor: 3
        
        assertFalse(iterator.hasNext());
        assertEquals(3, iterator.nextIndex());
        assertEquals(2, iterator.previousIndex());
    }
}