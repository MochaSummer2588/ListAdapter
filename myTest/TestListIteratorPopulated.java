package myTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import myAdapter.*;
import myExceptions.IllegalStateException;

/**
 * Test di integrazione e unitari per la classe {@link myAdapter.ListIterator} su una lista popolata.
 * <p>
 * Questa classe verifica il comportamento di un {@code ListIterator} ottenuto da una {@link myAdapter.ListAdapter}
 * non vuota, testando sia i costruttori che tutti i metodi di navigazione e modifica previsti
 * dall'interfaccia {@code ListIterator}.
 * <p>
 * <b>Obiettivi dei test:</b>
 * <ul>
 *   <li>Verificare la corretta inizializzazione dell'iteratore in diverse posizioni della lista.</li>
 *   <li>Assicurare la corretta gestione dei limiti sugli indici passati al costruttore.</li>
 *   <li>Testare i metodi di navigazione ({@code hasNext()}, {@code next()}, {@code hasPrevious()}, {@code previous()}, {@code nextIndex()}, {@code previousIndex()}) in tutti i casi rilevanti.</li>
 *   <li>Verificare il comportamento dei metodi di modifica ({@code remove()}, {@code set()}, {@code add()}) sia in condizioni normali che in presenza di errori (eccezioni attese).</li>
 *   <li>Assicurare la coerenza dello stato interno dell'iteratore dopo sequenze di operazioni miste.</li>
 *   <li>Verificare la corretta gestione di elementi {@code null} nella lista.</li>
 * </ul>
 * <p>
 * <b>Strategia di test:</b>
 * <ul>
 *   <li>Per ogni metodo pubblico dell'iteratore, sono previsti test sia per il caso base che per i casi limite e di errore.</li>
 *   <li>Le precondizioni e postcondizioni di ciascun test sono documentate nei rispettivi metodi.</li>
 *   <li>Vengono verificati sia gli effetti sulla lista sottostante che lo stato interno dell'iteratore (cursore, indici, lastReturned).</li>
 *   <li>Le eccezioni previste dal contratto sono testate esplicitamente.</li>
 * </ul>
 * <p>
 * <b>Precondizioni generali:</b>
 * <ul>
 *   <li>Salvo dove diversamente specificato, ogni test parte da una lista popolata con tre elementi: "zero", "uno", "due".</li>
 *   <li>Un nuovo {@code ListIterator} viene creato prima di ogni test tramite il metodo {@code setUp()}.</li>
 * </ul>
 * <p>
 * <b>Postcondizioni generali:</b>
 * <ul>
 *   <li>La lista e l'iteratore si trovano nello stato atteso dopo ogni test, oppure viene lanciata l'eccezione prevista.</li>
 * </ul>
 * <p>
 * <b>Nota:</b> Questa classe di test si focalizza su una lista non vuota; per i casi di lista vuota si rimanda a test specifici.
 */
public class TestListIteratorPopulated 
{

    private ListAdapter list;
    private HListIterator iterator; // Oggetto ListIterator da testare

    /**
     * Configura l'ambiente di test prima di ogni test case.
     * Inizializza una ListAdapter popolata e un ListIterator.
     */
    @Before
    public void setUp() {
        list = new ListAdapter();
        list.add("zero"); // Indice 0
        list.add("uno");  // Indice 1
        list.add("due");  // Indice 2
        // Dimensione lista: 3
        iterator = list.listIterator(); // Cursore a 0, lastReturned a -1
    }

    // --- TEST COSTRUTTORI ---

    /**
     * Test del costruttore {@link myAdapter.ListIterator#ListIterator(myAdapter.ListAdapter)}.
     * <p>
     * Summary: Il test verifica che il costruttore {@code ListIterator(ListAdapter list)} inizializzi l'iteratore correttamente all'inizio della lista.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è garantire che quando viene creato un iteratore tramite costruttore di default,
     * l'iteratore sia posizionato all'inizio della lista, con il cursore a 0 e lastReturned a -1.
     * <p>
     * Tes t Description: 1) Verifica che l'iteratore abbia un elemento dopo di lui.
     *                    2) Verifica che l'iteratore non abbia un elemento prima di lui.
     *                    3) Verifica che {@code nextIndex()} restituisca 0. 
     *                    4) Verifica {@code previousIndex()} restituisca -1.
     * <p>
     * Preconditions: Lista contenente 3 elementi: "zero", "uno", "due" e un iteratore posizionato prima dell'elemento "zero".
     * <p>
     * Postconditions: La lista rimane invariata e l'iteratore è posizionato all'inizio della lista.
     * <p>
     * Expected Result: {@code nextIndex()} deve essere 0, {@code previousIndex()} deve essere -1,
     * {@code hasNext()} deve essere true, {@code hasPrevious()} deve essere false.
     */
    @Test
    public void testConstructorDefaultPosition() 
    {
        assertTrue(iterator.hasNext());
        assertFalse(iterator.hasPrevious());
        assertEquals(0, iterator.nextIndex());
        assertEquals(-1, iterator.previousIndex());
    }

    /**
     * Test del costruttore {@link myAdapter.ListIterator#ListIterator(myAdapter.ListAdapter, int)}.
     * <p>
     * Summary: Verifica che il costruttore {@code ListIterator(ListAdapter list, int index)} inizializzi l'iteratore correttamente a un indice specificato.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è garantire che l'iteratore venga creato all'indice specificato e dunque che prima e dopo di "iterator" ci 
     * siano gli elementi che ci si aspetta.
     * <p>
     * Test Description: 1) Si crea un iteratore a un indice specifico (1).
     *                   2) Si verifica che {@code nextIndex()} restituisca 1, {@code previousIndex()} restituisca 0.
     *                   3) Si verifica che {@code hasNext()} restituisca true e {@code hasPrevious()} restituisca true.
     * <P>
     * Preconditions: Lista contenente 3 elementi: "zero", "uno", "due".
     * <p>
     * Postconditions: L'iteratore è posizionato all'indice specificato.
     * <p>
     * Expected Result: L'iteratore viene creato all'indice 1 della lista. Esso deve avere {@code nextIndex()} 1,
     * {@code previousIndex()} 0, {@code hasNext()} true, {@code hasPrevious()} true.
     */
    @Test
    public void testConstructorSpecificPosition() 
    {
        iterator = list.listIterator(1); // Cursore a 1, lastReturned a -1
        assertEquals(1, iterator.nextIndex());
        assertEquals(0, iterator.previousIndex());
        assertTrue(iterator.hasNext());
        assertTrue(iterator.hasPrevious());
    }

    /**
     * Test del costruttore {@link myAdapter.ListIterator#ListIterator(myAdapter.ListAdapter, int)} con indice negativo.
     * <p>
     * Summary: Verifica che il costruttore lanci {@code IndexOutOfBoundsException} se l'indice al quale si vuole creare l'iteratore è negativo.
     * <p>
     * Test Case Design: Si vuole garantire che il costruttore lanci {@code IndexOutOfBoundsException} se l'indice è negativo, rispettando la logica dell'iteratore
     * (non puo' andare a un indice negativo).
     * <p>
     * Test Description: 1) Si tenta di creare un iteratore con un indice negativo (-1) su una lista.
     * <p>
     * Preconditions: Una lista popolata con 3 elementi: ["zero", "uno", "due"].
     * <p>
     * Postconditions: Nessuna modifica alla lista. Viene lanciata un'eccezione.
     * <p>
     * Expected Result: {@code IndexOutOfBoundsException} deve essere lanciata nel momento della chiamata al metodo {@code list.listIterator(-1)}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testConstructorIndexOutOfBoundsNegative() 
    {
        list.listIterator(-1);
    }

    /**
     * Test del costruttore {@link myAdapter.ListIterator#ListIterator(myAdapter.ListAdapter, int)} con indice fuori limite superiore.
     * <p>
     * Summary: Il test verifica che il costruttore {@code IndexOutOfBoundsException} lanci {@code IndexOutOfBoundsException} se l'indice è maggiore della dimensione della lista.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è garantire che il costruttore rispetti i limiti della lista e non permetta di creare un iteratore in una posizione 
     * maggiore rispetto alla dimensione della lista.
     * <p>
     * Test Description: 1) Si tenta di creare un iteratore con un indice maggiore della dimensione della lista (ad esempio indice 10 su una lista contenente ["zero", "uno", "due"]).
     * <p>
     * Preconditions: Lista popolata con 3 elementi: ["zero", "uno", "due"].
     * <p>
     * Postconditions: Nessuna modifica alla lista. Viene lanciata un'eccezione.
     * <p>
     * Expected Result: {@code IndexOutOfBoundsException} deve essere lanciata nel momento della chiamata al metodo {@code list.listIterator(10)}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testConstructorIndexOutOfBoundsGreaterThanSize() 
    {
        list.listIterator(10);
    }

    /**
     * Test del costruttore{@link myAdapter.ListIterator#ListIterator(myAdapter.ListAdapter, int)} con indice uguale alla dimensione della lista.
     * <p>
     * Summary: Verifica che l'iteratore possa essere inizializzato correttamente alla fine della lista.
     * <p>
     * Test Case Design: Il motivo di questo test è garantire che l'iteratore possa essere creato all'indice uguale alla dimensione della lista, quindi alla fine della lista.
     * <p>
     * Test Description: 1) Si crea un iteratore con un indice uguale alla dimensione della lista (3 in questo caso).
     *                   2) Si verifica che {@code nextIndex()} restituisca la dimensione della lista (3).
     *                   3) Si verifica che {@code hasNext()} restituisca false e {@code hasPrevious()} restituisca true.
     * <p>
     * Preconditions: Lista popolata con 3 elementi: "zero", "uno", "due" e un iteratore posizionato alla fine della lista, cioe' dopo "due".
     * <p>
     * Postconditions: Nessuna modifica della lista. L'iteratore rimane alla fine della lista.
     * <p>
     * Expected Result: {@code nextIndex()} deve essere uguale alla dimensione della lista,
     * {@code hasNext()} deve essere {@code false}, {@code hasPrevious()} deve essere {@code true}.
     */
    @Test
    public void testConstructorIndexAtSize() 
    {
        iterator = list.listIterator(list.size());
        assertEquals(list.size(), iterator.nextIndex());
        assertEquals(list.size() - 1, iterator.previousIndex());
        assertFalse(iterator.hasNext());
        assertTrue(iterator.hasPrevious());
    }

    // --- TEST METODI DI NAVIGAZIONE ---

    /**
     * Test del metodo {@link myAdapter.ListIterator#hasNext()}.
     * <p>
     * Summary: Il test testa il metodo {@code hasNext()} per verificare se ci sono elementi successivi nella lista non vuota dalla 
     * posizione zero a quella oltre l'ultimo elemento della lista.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è garantire che il metodo {@code hasNext()} restituisca true quando ci sono elementi successivi dopo
     * l'iteratore e che l'indice successivo sia corretto, ossia un indice tale per cui la lista contiene un elemento a quell'indice.
     * <p>
     * Test Description: 1) Si verifica che {@code hasNext()} restituisca true quando l'iteratore è posizionato all'inizio della lista.
     *                   2) Si verifica tramite {@code contains()} che la lista contenga l'elemento all'indice {@code nextIndex()}, quindi che
     *                      c'e' effettivamente un elemento successivo.
     * <p>
     * Preconditions: L'iteratore è all'inizio della lista non vuota: ["zero", "uno", "due"].
     * <p>
     * Postconditions: Nessuna modifica allo stato dell'iteratore o della lista.
     * <p>
     * Expected Result: {@code hasNext()} deve restituire {@code true}.
     */
    @Test
    public void testHasNextTrue() 
    {
        assertTrue(iterator.hasNext());
        assertTrue(list.contains(list.get(iterator.nextIndex()))); // Verifica che l'elemento successivo esista
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#next()}.
     * <p>
     * Summary: Il test verifica che il metodo {@code next()} restituisca l'elemento successivo nella lista e garantisca che alla chiamata di 
     * {@code next()} l'iteratore avanzi di un elemento e quindi che {@code nextIndex()} e {@code previousIndex()} restituiscano gli indici corretti.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è garantire che il metodo {@code next()} restituisca l'elemento successivo e che l'iteratore si sposti correttamente 
     * lungo la lista, aggiornando gli indici.
     * <p>
     * Preconditions: L'iteratore è all'inizio di una lista popolata.
     * <p>
     * Postconditions: Il cursore avanza, {@code lastReturned} viene aggiornato.
     * <p>
     * Expected Result: {@code next()} deve restituire "zero", {@code nextIndex()} deve essere 1,
     * {@code previousIndex()} deve essere 0.
     */
    @Test
    public void testNext() 
    {
        assertEquals(list.get(0), iterator.next());
        assertEquals(1, iterator.nextIndex());
        assertEquals(0, iterator.previousIndex());
        assertEquals(list.get(1), iterator.next());
        assertEquals(2, iterator.nextIndex());
        assertEquals(1, iterator.previousIndex());
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#next()} oltre l'ultimo elemento della lista.
     * <p>
     * Summary: Il test verifica che il metodo {@code next()} lanci {@code NoSuchElementException} quando si tenta di chiamarlo oltre l'ultimo elemento della lista.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è valutare che l'iteratore scorra la lista fino alla fine, restituisca i valori 
     * corretti e lanci l'eccezione appropriata quando si tenta di andare oltre l'ultimo elemento.
     * <p>
     * Test Description: 1) L'iteratore si trova all'inizio della lista.
     *                   2) Viene creato un array con dimensione maggiore rispetto a quella della lista in questo modo non si verifica {@cod IndexOutOfBoundsException}. 
     *                   3) L'iteratore scorre la lista chiamando {@code next()} e confrontando l'elemento ottenuto con l'elemento nella posizione i-esima della lista tramite 
     *                      e incremento i ad ogni ciclo.
     *                   4) Si verifica che l'iteratore lanci {@code NoSuchElementException} quando si tenta di chiamare {@code next()} dopo l'ultimo elemento.                
     * <p>
     * Preconditions: Iteratore posizionato prima di "zero"; la lista su cui l'iteraore itera ha tre elementi: ["zero", "uno", "due"].
     * <p>
     * Postconditions: L'iteratore si trova dopo l'ultimo elemento della lista, ossia dopo "tre". La lista rimane invariata
     * <p>
     * Expected Result: Il listIterator scorre la lista, ritorna i valori nell'ordine con cui sono stati inseriti e con valori coerenti a quelli inseriti.
     * {@code NoSuchElementException} deve essere lanciata quando si tenta di chiamare {@code next()} dopo l'ultimo elemento.
     */
    @Test(expected = java.util.NoSuchElementException.class)
    public void testNextThrowsNoSuchElementException() 
    {
        int i = 0;
        Object[] array = new Object[list.size() + 1];    // +1 per il caso in cui si chiama next() oltre l'ultimo elemento
        list.toArray(array);                             // Converte la lista in un array per il confronto
        while(true) 
        {
            assertEquals(array[i], iterator.next());
            i++;
        }
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#hasPrevious()}.
     * <p>
     * Summary: Il test verifica che {@code hasPrevious()} restituisca {@code true} quando ci sono elementi precedenti.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è garantire che il metodo {@code hasPrevious()} restituisca true quando l'iteratore è posizionato 
     * in modo tale da avere almeno un elemento precedente. Inoltre tramite questo test si verifica che effettivamente l'elemento precedente esista nella lista.
     * <p>
     * Test Description: 1) Si chiama {@code next()} per avanzare l'iteratore di un elemento.
     *                   2) Si verifica che {@code hasPrevious()} restituisca {@code true} e che l'elemento precedente esista nella lista.
     * <p>
     * Preconditions: L'iteratore è stato avanzato di un elemento e si trova tra gli elementi agli indici 0 e 1. La lista contiene ha i seguenti elementi: ["zero", "uno", "due"].
     * <p>
     * Postconditions: Nessuna modifica allo stato dell'iteratore o della lista.
     * <p>
     * Expected Result: {@code hasPrevious()} deve restituire {@code true} e l'elemento precedente deve essere presente nella lista.
     */
    @Test
    public void testHasPreviousTrue() 
    {
        iterator.next(); // Avanza per avere un precedente
        assertTrue(iterator.hasPrevious());
        assertTrue(list.contains(list.get(iterator.previousIndex()))); // Verifica che l'elemento successivo esista
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#hasPrevious()} all'inizio della lista.
     * <p>
     * Summary: Il motivo di questo test e' verificare il funzionamento di {@code hasPrevious()} 
     * <p>
     * Test Case Design: Il motivo di questo test è garantire che il metodo {@code hasPrevious()} restituisca false quando l'iteratore è posizionato all'inizio della lista.
     * <p>
     * Test Description: 1) 
     * <p>
     * Preconditions: L'iteratore è all'inizio di una lista popolata con tre elementi: ["zero", "uno", "due"].
     * <p>
     * Postconditions: Nessuna modifica allo stato dell'iteratore o della lista.
     * <p>
     * Expected Result: {@code hasPrevious()} deve restituire {@code false}.
     */
    @Test
    public void testHasPreviousFalseAtStart() 
    {
        assertFalse(iterator.hasPrevious()); // Inizialmente all'inizio
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#previous()}.
     * <p>
     * Summary: Verifica che {@code previous()} restituisca l'elemento corretto e sposti il cursore indietro.
     * <p>
     * Test Case Design: Assicurarsi che il metodo recuperi l'elemento giusto e aggiorni
     * correttamente la posizione dell'iteratore e l'indice dell'ultimo elemento restituito
     * durante la navigazione all'indietro.
     * <p>
     * Preconditions: L'iteratore è stato avanzato di alcuni elementi.
     * <p>
     * Postconditions: Il cursore arretra, {@code lastReturned} viene aggiornato.
     * <p>
     * Expected Result: {@code previous()} deve restituire "due" (dopo aver chiamato next() 3 volte),
     * {@code nextIndex()} deve essere 2, {@code previousIndex()} deve essere 1.
     */
    @Test
    public void testPrevious() 
    {
        iterator.next(); // "zero"
        iterator.next(); // "uno"
        iterator.next(); // "due" (cursore a 3)

        assertEquals("due", iterator.previous());
        assertEquals(2, iterator.nextIndex());
        assertEquals(1, iterator.previousIndex());
        assertEquals("uno", iterator.previous());
        assertEquals(1, iterator.nextIndex());
        assertEquals(0, iterator.previousIndex());
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#previous()} quando non ci sono più elementi precedenti.
     * <p>
     * Summary: Verifica che {@code previous()} lanci {@code NoSuchElementException} quando l'inizio della lista è raggiunto.
     * <p>
     * Test Case Design: Assicurarsi che il metodo segnali correttamente la mancanza di elementi precedenti.
     * <p>
     * Preconditions: L'iteratore è all'inizio della lista.
     * <p>
     * Postconditions: Nessuna modifica allo stato dell'iteratore o della lista. Viene lanciata un'eccezione.
     * <p>
     * Expected Result: {@code NoSuchElementException} deve essere lanciata.
     */
    @Test(expected = java.util.NoSuchElementException.class)
    public void testPreviousThrowsNoSuchElementException() 
    {
        iterator.previous(); // Dovrebbe lanciare l'eccezione, essendo all'inizio
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#next()} e del metodo {@link myAdapter.ListIterator#previous()}.
     * <p>
     * Verifica che chiamate alternate di {@code next()} e {@code previous()} restituiscano lo stesso elemento.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è garantire che i metodi {@link myAdapter.ListIterator#next()} e {@link myAdapter.ListIterator#previous()}
     * non alterino la lista, ma anzi restituiscano lo stesso.
     * <p>
     * Test Description: 1) Si chiama {@code next()} per avanzare di un elemento, ossia da "zero" a "uno".
     *                   2) Inizializzo una variabile intera {@code i} a 0 e una variabile {@code indexFirstElement} a 0, che rappresenta l'indice del primo elemento della lista.
     *                   3) In un ciclo che si ripete 100 volte, si chiama {@code previous()} e poi {@code next()}.
     *                   4) Si verifica che entrambi i metodi restituiscano lo stesso elemento confrontando il risultato di {@code previous()} e {@code next()} con 
     *                      l'elemento alla posizione indexFirstElement della lista, cioe' "zero".
     * <p>
     * Preconditions: Una `ListAdapter` popolata con tre elementi: "zero", "uno", "due". L'iteratore è posizionato tra "zero" e "uno".
     * <p>
     * Postconditions: Nessuna modifica alla lista.
     * <p>
     * Expected Result: Il metodo {@code next()} deve restituire "zero" e il metodo {@code previous()} deve restituire "zero" in ogni iterazione del ciclo.
     */
    @Test
    public void testNextAndPreviousConsistency() 
    {
        //Mi sposto tra zero e uno
        iterator.next();
        int indexFirstElement = 0;
        int i = 0;      
        
        //Vado continuamente avanti e indietro per 100 volte
        while(i < 100)
        {
            assertEquals(list.get(indexFirstElement), iterator.previous());
            assertEquals(list.get(indexFirstElement), iterator.next());
            i++;
        }
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#nextIndex()}.
     * <p>
     * Verifica che {@code nextIndex()} restituisca l'indice dell'elemento che verrebbe restituito da una successiva chiamata a {@code next()}.
     * <p>
     * Test Case Design: Assicurarsi che il metodo rifletta accuratamente la posizione
     * del cursore in relazione all'elemento successivo.
     * <p>
     * Preconditions: L'iteratore è all'inizio della lista.
     * <p>
     * Postconditions: Nessuna modifica allo stato dell'iteratore o della lista.
     * <p>
     * Expected Result: Inizialmente 0, poi 1 dopo il primo {@code next()}.
     */
    @Test
    public void testNextIndex() {
        assertEquals(0, iterator.nextIndex());
        iterator.next(); // "zero"
        assertEquals(1, iterator.nextIndex());
        iterator.next(); // "uno"
        assertEquals(2, iterator.nextIndex());
        iterator.next(); // "due"
        assertEquals(3, iterator.nextIndex()); // Dopo l'ultimo elemento, nextIndex è la dimensione
        assertFalse(iterator.hasNext());
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#previousIndex()}.
     * <p>
     * Verifica che {@code previousIndex()} restituisca l'indice dell'elemento che verrebbe restituito da una successiva chiamata a {@code previous()}.
     * <p>
     * Test Case Design: Assicurarsi che il metodo rifletta accuratamente la posizione
     * del cursore in relazione all'elemento precedente.
     * <p>
     * Preconditions: L'iteratore è all'inizio della lista.
     * <p>
     * Postconditions: Nessuna modifica allo stato dell'iteratore o della lista.
     * <p>
     * Expected Result: Inizialmente -1, poi 0 dopo il primo {@code next()}.
     */
    @Test
    public void testPreviousIndex() {
        assertEquals(-1, iterator.previousIndex());
        iterator.next(); // "zero"
        assertEquals(0, iterator.previousIndex());
        iterator.next(); // "uno"
        assertEquals(1, iterator.previousIndex());
        iterator.next(); // "due"
        assertEquals(2, iterator.previousIndex()); // Dopo l'ultimo elemento, previousIndex è size - 1
        assertFalse(iterator.hasNext());
    }

    // --- TEST METODI DI MODIFICA ---

    /**
     * Test del metodo {@link myAdapter.ListIterator#remove()}.
     * <p>
     * Verifica che {@code remove()} rimuova correttamente l'ultimo elemento restituito da {@code next()}.
     * <p>
     * Test Case Design: Assicurarsi che l'elemento corretto venga rimosso e che la lista
     * sottostante venga aggiornata di conseguenza.
     * <p>
     * Preconditions: L'iteratore ha chiamato {@code next()} almeno una volta.
     * <p>
     * Postconditions: L'elemento restituito da `next()` (o `previous()`) viene rimosso,
     * la dimensione della lista diminuisce, il cursore e {@code lastReturned} vengono aggiornati.
     * <p>
     * Expected Result: L'elemento "zero" deve essere rimosso, la dimensione della lista deve essere 2.
     */
    @Test
    public void testRemoveAfterNext() {
        iterator.next(); // Restituisce "zero", lastReturned = 0
        iterator.remove();

        assertEquals(2, list.size());
        assertFalse(list.contains("zero"));
        assertEquals("uno", list.get(0)); // "uno" è il nuovo primo elemento
        assertEquals(0, iterator.nextIndex()); // Il cursore deve rimanere sulla posizione dell'elemento rimosso
        assertEquals(-1, iterator.previousIndex());
        // lastReturned deve essere -1 dopo remove
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#remove()}.
     * <p>
     * Verifica che {@code remove()} rimuova correttamente l'ultimo elemento restituito da {@code previous()}.
     * <p>
     * Test Case Design: Assicurarsi che l'elemento corretto venga rimosso e che la lista
     * sottostante venga aggiornata di conseguenza, anche dopo una navigazione all'indietro.
     * <p>
     * Preconditions: L'iteratore ha chiamato {@code previous()} almeno una volta.
     * <p>
     * Postconditions: L'elemento restituito da `previous()` viene rimosso,
     * la dimensione della lista diminuisce, il cursore e {@code lastReturned} vengono aggiornati.
     * <p>
     * Expected Result: L'elemento "due" deve essere rimosso, la dimensione della lista deve essere 2.
     */
    @Test
    public void testRemoveAfterPrevious() {
        iterator = list.listIterator(list.size()); // Cursore a 3
        iterator.previous(); // Restituisce "due", lastReturned = 2
        iterator.remove();

        assertEquals(2, list.size());
        assertFalse(list.contains("due"));
        assertEquals("uno", list.get(1)); // "uno" è ancora al suo posto
        assertEquals(2, iterator.nextIndex()); // Cursore si sposta indietro all'indice dell'elemento rimosso
        assertEquals(1, iterator.previousIndex());
        // lastReturned deve essere -1 dopo remove
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#remove()} senza chiamare {@code next()} o {@code previous()}.
     * <p>
     * Verifica che {@code remove()} lanci {@code IllegalStateException} se non è stato chiamato
     * {@code next()} o {@code previous()} prima.
     * <p>
     * Test Case Design: Assicurarsi che il metodo faccia rispettare la precondizione di aver
     * restituito un elemento prima di poterlo rimuovere.
     * <p>
     * Preconditions: L'iteratore è stato appena inizializzato, o remove() è già stato chiamato.
     * <p>
     * Postconditions: Nessuna modifica alla lista. Viene lanciata un'eccezione.
     * <p>
     * Expected Result: {@code IllegalStateException} deve essere lanciata.
     */
    @Test(expected = IllegalStateException.class)
    public void testRemoveWithoutNextOrPrevious() {
        iterator.remove();
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#remove()} chiamato due volte di seguito.
     * <p>
     * Verifica che {@code remove()} lanci {@code IllegalStateException} se chiamato due volte
     * consecutivamente senza una chiamata intermedia a {@code next()} o {@code previous()}.
     * <p>
     * Test Case Design: Assicurarsi che il metodo rispetti la regola che può essere chiamato
     * al massimo una volta per ogni chiamata a {@code next()} o {@code previous()}.
     * <p>
     * Preconditions: L'iteratore ha chiamato {@code next()} (o {@code previous()}) e poi {@code remove()}.
     * <p>
     * Postconditions: Nessuna ulteriore modifica alla lista. Viene lanciata un'eccezione.
     * <p>
     * Expected Result: {@code IllegalStateException} deve essere lanciata.
     */
    @Test(expected = IllegalStateException.class)
    public void testRemoveTwiceInARow() {
        iterator.next();
        iterator.remove();
        iterator.remove(); // Seconda chiamata, dovrebbe fallire
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#set(Object)}.
     * <p>
     * Verifica che {@code set()} sostituisca correttamente l'ultimo elemento restituito da {@code next()}.
     * <p>
     * Test Case Design: Assicurarsi che l'elemento corretto venga sostituito e che la lista
     * sottostante venga aggiornata.
     * <p>
     * Preconditions: L'iteratore ha chiamato {@code next()} almeno una volta.
     * <p>
     * Postconditions: L'elemento sostituito con il nuovo elemento. La dimensione non cambia.
     * <p>
     * Expected Result: L'elemento a indice 0 deve essere "nuovoElemento".
     */
    @Test
    public void testSetAfterNext() {
        iterator.next(); // Restituisce "zero", lastReturned = 0
        iterator.set("nuovoElemento");

        assertEquals(3, list.size());
        assertEquals("nuovoElemento", list.get(0));
        assertEquals("uno", list.get(1)); // Gli altri elementi non sono toccati
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#set(Object)}.
     * <p>
     * Verifica che {@code set()} sostituisca correttamente l'ultimo elemento restituito da {@code previous()}.
     * <p>
     * Test Case Design: Assicurarsi che l'elemento corretto venga sostituito anche dopo una
     * navigazione all'indietro.
     * <p>
     * Preconditions: L'iteratore ha chiamato {@code previous()} almeno una volta.
     * <p>
     * Postconditions: L'elemento sostituito con il nuovo elemento. La dimensione non cambia.
     * <p>
     * Expected Result: L'elemento "due" (dopo aver navigato indietro) deve essere "nuovoElemento".
     */
    @Test
    public void testSetAfterPrevious() {
        iterator.next(); // "zero"
        iterator.next(); // "uno"
        iterator.next(); // "due"
        iterator.previous(); // Restituisce "due", lastReturned = 2
        iterator.set("nuovoDue");

        assertEquals(3, list.size());
        assertEquals("nuovoDue", list.get(2));
        assertEquals("uno", list.get(1));
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#set(Object)} senza chiamare {@code next()} o {@code previous()}.
     * <p>
     * Verifica che {@code set()} lanci {@code IllegalStateException} se non è stato chiamato
     * {@code next()} o {@code previous()} prima.
     * <p>
     * Test Case Design: Assicurarsi che il metodo faccia rispettare la precondizione di aver
     * restituito un elemento prima di poterlo modificare.
     * <p>
     * Preconditions: L'iteratore è stato appena inizializzato, o remove()/add() sono già stati chiamati.
     * <p>
     * Postconditions: Nessuna modifica alla lista. Viene lanciata un'eccezione.
     * <p>
     * Expected Result: {@code IllegalStateException} deve essere lanciata.
     */
    @Test(expected = IllegalStateException.class)
    public void testSetWithoutNextOrPrevious() {
        iterator.set("nuovoElemento");
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#set(Object)} dopo una chiamata a {@code remove()}.
     * <p>
     * Verifica che {@code set()} lanci {@code IllegalStateException} dopo una chiamata a {@code remove()}.
     * <p>
     * Test Case Design: Assicurarsi che {@code set()} non possa essere chiamato dopo che
     * l'elemento di riferimento è stato rimosso.
     * <p>
     * Preconditions: L'iteratore ha chiamato {@code next()} (o {@code previous()}) e poi {@code remove()}.
     * <p>
     * Postconditions: Nessuna ulteriore modifica alla lista. Viene lanciata un'eccezione.
     * <p>
     * Expected Result: {@code IllegalStateException} deve essere lanciata.
     */
    @Test(expected = IllegalStateException.class)
    public void testSetAfterRemove() {
        iterator.next();
        iterator.remove();
        iterator.set("dopoRemove"); // Dovrebbe fallire
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#add(Object)}.
     * <p>
     * Verifica che {@code add()} inserisca correttamente un elemento nella lista alla posizione del cursore.
     * <p>
     * Test Case Design: Assicurarsi che l'elemento venga inserito correttamente senza sovrascrivere,
     * e che gli indici del cursore siano aggiornati.
     * <p>
     * Preconditions: L'iteratore è posizionato all'inizio della lista.
     * <p>
     * Postconditions: L'elemento viene aggiunto, la dimensione della lista aumenta,
     * il cursore avanza oltre l'elemento aggiunto. {@code lastReturned} viene resettato a -1.
     * <p>
     * Expected Result: La lista deve contenere "nuovo" all'indice 0, la dimensione deve essere 4,
     * {@code nextIndex()} deve essere 1, {@code previousIndex()} deve essere 0.
     */
    @Test
    public void testAdd() {
        iterator.add("nuovo"); // Aggiunge "nuovo" all'indice 0
        assertEquals(4, list.size());
        assertEquals("nuovo", list.get(0));
        assertEquals("zero", list.get(1)); // Gli elementi esistenti sono shiftati
        assertEquals(1, iterator.nextIndex()); // Cursore avanza oltre l'elemento aggiunto
        assertEquals(0, iterator.previousIndex());
        // lastReturned deve essere -1
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#add(Object)} su una lista vuota.
     * <p>
     * Verifica che {@code add()} funzioni correttamente anche su una lista inizialmente vuota.
     * <p>
     * Test Case Design: Assicurarsi che il metodo gestisca correttamente il caso di
     * inserimento nel primo elemento di una lista vuota.
     * <p>
     * Preconditions: L'iteratore è stato creato su una lista vuota.
     * <p>
     * Postconditions: La lista contiene l'elemento aggiunto.
     * <p>
     * Expected Result: La lista deve contenere "primo", la dimensione deve essere 1.
     */
    @Test
    public void testAddOnEmptyList() {
        list = new ListAdapter();
        iterator = list.listIterator();
        iterator.add("primo");

        assertEquals(1, list.size());
        assertEquals("primo", list.get(0));
        assertEquals(1, iterator.nextIndex());
        assertEquals(0, iterator.previousIndex());
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#add(Object)} e successivo {@code next()}.
     * <p>
     * Verifica che {@code next()} funzioni correttamente dopo una chiamata a {@code add()}.
     * <p>
     * Test Case Design: Assicurarsi che l'aggiunta di un elemento non alteri la corretta
     * navigazione successiva.
     * <p>
     * Preconditions: Un elemento è stato aggiunto tramite {@code add()}.
     * <p>
     * Postconditions: Il cursore avanza e l'elemento successivo viene restituito.
     * <p>
     * Expected Result: {@code next()} deve restituire l'elemento che originariamente era alla
     * posizione del cursore prima dell'aggiunta.
     */
    @Test
    public void testAddThenNext() {
        iterator.add("pre-zero"); // list: ["pre-zero", "zero", "uno", "due"]
        // Cursore è tra "pre-zero" e "zero"
        assertEquals("zero", iterator.next()); // next() deve restituire "zero"
        assertEquals(2, iterator.nextIndex());
        assertEquals(1, iterator.previousIndex());
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#add(Object)} e successiva {@code previous()}.
     * <p>
     * Verifica che {@code previous()} funzioni correttamente dopo una chiamata a {@code add()}.
     * <p>
     * Test Case Design: Assicurarsi che l'aggiunta di un elemento non alteri la corretta
     * navigazione all'indietro.
     * <p>
     * Preconditions: L'iteratore è posizionato dopo un'aggiunta e un avanzamento.
     * <p>
     * Postconditions: Il cursore arretra e l'elemento precedente viene restituito.
     * <p>
     * Expected Result: {@code previous()} deve restituire l'elemento appena aggiunto.
     */
    @Test
    public void testAddThenPrevious() {
        iterator.next(); // "zero", cursore a 1
        iterator.add("nuovo"); // list: ["zero", "nuovo", "uno", "due"], cursore a 2
        // lastReturned = -1
        
        assertEquals("nuovo", iterator.previous()); // previous() deve restituire "nuovo"
        assertEquals(1, iterator.nextIndex());
        assertEquals(0, iterator.previousIndex());
        assertEquals("zero", iterator.previous()); // E poi "zero"
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#remove()} dopo una chiamata a {@code add()}.
     * <p>
     * Verifica che {@code remove()} lanci {@code IllegalStateException} dopo una chiamata a {@code add()}.
     * <p>
     * Test Case Design: Assicurarsi che {@code remove()} non possa essere chiamato immediatamente
     * dopo un'operazione di {@code add()}.
     * <p>
     * Preconditions: Un elemento è stato aggiunto tramite {@code add()}.
     * <p>
     * Postconditions: Nessuna modifica alla lista. Viene lanciata un'eccezione.
     * <p>
     * Expected Result: {@code IllegalStateException} deve essere lanciata.
     */
    @Test(expected = IllegalStateException.class)
    public void testRemoveAfterAdd() {
        iterator.add("elemento");
        iterator.remove(); // Dovrebbe fallire
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#set(Object)} dopo una chiamata a {@code add()}.
     * <p>
     * Verifica che {@code set()} lanci {@code IllegalStateException} dopo una chiamata a {@code add()}.
     * <p>
     * Test Case Design: Assicurarsi che {@code set()} non possa essere chiamato immediatamente
     * dopo un'operazione di {@code add()}.
     * <p>
     * Preconditions: Un elemento è stato aggiunto tramite {@code add()}.
     * <p>
     * Postconditions: Nessuna modifica alla lista. Viene lanciata un'eccezione.
     * <p>
     * Expected Result: {@code IllegalStateException} deve essere lanciata.
     */
    @Test(expected = IllegalStateException.class)
    public void testSetAfterAdd() {
        iterator.add("elemento");
        iterator.set("nuovoValore"); // Dovrebbe fallire
    }

    /**
     * Test di combinazioni di navigazione e modifica: next, remove, add, next.
     * <p>
     * Verifica un flusso di operazioni miste per assicurare la consistenza dello stato dell'iteratore e della lista.
     * <p>
     * Test Case Design: Simulare un'operazione complessa per verificare la robustezza dell'iteratore.
     * <p>
     * Preconditions: Lista popolata.
     * <p>
     * Postconditions: La lista è stata modificata in base alle operazioni, il cursore è nella posizione attesa.
     * <p>
     * Expected Result: La sequenza di operazioni deve portare la lista e l'iteratore a uno stato specifico e prevedibile.
     */
    @Test
    public void testMixedOperations() {
        // list: ["zero", "uno", "due"]
        // iterator: cursor=0, lastReturned=-1

        assertEquals("zero", iterator.next()); // next: "zero", cursor=1, lastReturned=0
        assertEquals(1, iterator.nextIndex());

        iterator.remove(); // remove: list: ["uno", "due"], cursor=0, lastReturned=-1
        assertEquals(2, list.size());
        assertEquals("uno", list.get(0));

        iterator.add("nuovoZero"); // add: list: ["nuovoZero", "uno", "due"], cursor=1, lastReturned=-1
        assertEquals(3, list.size());
        assertEquals("nuovoZero", list.get(0));
        assertEquals(1, iterator.nextIndex());

        assertEquals("uno", iterator.next()); // next: "uno", cursor=2, lastReturned=1
        assertEquals(2, iterator.nextIndex());

        iterator.set("UNO_MODIFICATO"); // set: list: ["nuovoZero", "UNO_MODIFICATO", "due"], cursor=2, lastReturned=1
        assertEquals("UNO_MODIFICATO", list.get(1));

        assertEquals("due", iterator.next()); // next: "due", cursor=3, lastReturned=2
        assertFalse(iterator.hasNext());

        assertEquals("due", iterator.previous()); // previous: "due", cursor=2, lastReturned=2
        iterator.remove(); // remove: list: ["nuovoZero", "UNO_MODIFICATO"], cursor=2, lastReturned=-1
        assertEquals(2, list.size());
        assertFalse(list.contains("due"));
        assertEquals(2, iterator.nextIndex()); // Cursore rimane alla posizione
        assertEquals(1, iterator.previousIndex());
    }

    /**
     * Test per la corretta gestione di elementi null.
     * <p>
     * Verifica che {@code add(null)} funzioni correttamente e che {@code next()} restituisca null.
     * <p>
     * Test Case Design: Assicurarsi che l'iteratore gestisca `null` come un elemento valido
     * senza lanciare `NullPointerException` (a meno che non sia specificato diversamente nel contratto).
     * <p>
     * Preconditions: Una lista popolata.
     * <p>
     * Postconditions: La lista contiene un elemento null, l'iteratore restituisce null.
     * <p>
     * Expected Result: L'elemento null viene aggiunto e restituito correttamente.
     */
    @Test
    public void testAddNullElement() {
        iterator.add(null);
        assertEquals(4, list.size());
        assertNull(list.get(0));
        assertEquals("zero", list.get(1)); // Gli elementi esistenti sono shiftati
        assertEquals(1, iterator.nextIndex());

        assertEquals("zero", iterator.next());
        assertEquals("uno", iterator.next());
        assertEquals("due", iterator.next());
        
        iterator = list.listIterator(0); // Reset iteratore
        assertNull(iterator.next()); // Il primo elemento è null
        assertEquals("zero", iterator.next()); // Il secondo è "zero"
    }

}