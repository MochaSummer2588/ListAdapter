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
     * Summary: Il test verifica che il metodo {@code hasPrevious()} restituisca {@code false} quando l'iteratore è posizionato all'inizio di una lista non vuota.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è garantire che il metodo {@code hasPrevious()} restituisca correttamente {@code false} quando 
     * l'iteratore non ha elementi precedenti da attraversare, ovvero è al suo punto di partenza.
     * <p>
     * Test Description: 1) Si verifica che {@code hasPrevious()} restituisca {@code false}.
     * <p>
     * Preconditions: La lista è popolata con almeno un elemento. L'iteratore è posizionato prima del primo elemento (all'indice 0).
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
     * Summary: Il test verifica che {@code previous()} restituisca gli elementi corretti e sposti il cursore indietro, iterando l'intera lista all'indietro fino all'inizio.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare che il metodo {@code previous()} recuperi correttamente tutti gli elementi in ordine inverso, 
     *                   partendo dalla fine della lista e procedendo fino all'inizio. Si verifica l'accuratezza del valore restituito e il corretto 
     *                   aggiornamento della posizione del cursore e dell'indice dell'ultimo elemento restituito.
     * <p>
     * Test Description: 1) L'iteratore avanza di tre posizioni chiamando {@code next()} tre volte, posizionandosi dopo l'ultimo elemento ("due").
     *                   2) Un loop `while` itera all'indietro finché {@code hasPrevious()} restituisce {@code false}.
     *                   3) Ad ogni iterazione, si verifica che l'elemento restituito da {@code previous()} corrisponda all'elemento atteso dalla lista, 
     *                      partendo dall'ultimo e andando a ritroso.
     * <p>
     * Preconditions: La lista è popolata con almeno tre elementi (es: ["zero", "uno", "due"]). L'iteratore è inizialmente posizionato all'inizio della lista e viene poi avanzato fino alla fine.
     * <p>
     * Postconditions: Il cursore dell'iteratore si sposta all'indietro dopo ogni chiamata a {@code previous()}, e il valore di {@code lastReturned} viene aggiornato all'indice dell'elemento restituito. Al termine del test, l'iteratore sarà posizionato all'inizio della lista. La lista rimane invariata.
     * <p>
     * Expected Result: {@code previous()} deve restituire gli elementi in ordine inverso rispetto all'inserimento originale ("due", poi "uno", poi "zero"). Il loop deve terminare quando {@code hasPrevious()} diventa {@code false}, indicando che l'iteratore ha raggiunto l'inizio della lista.
     */
    @Test
    public void testPrevious()
    {
        iterator.next(); 
        iterator.next(); 
        iterator.next(); 

        int i = list.size() - 1;
        while (iterator.hasPrevious()) 
        {
            assertEquals(list.get(i), iterator.previous());
            i--;
        }

        // Dopo il loop, l'iteratore dovrebbe essere all'inizio
        assertFalse(iterator.hasPrevious());
        assertEquals(-1, iterator.previousIndex());
        assertEquals(0, iterator.nextIndex());
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#previous()} quando non ci sono più elementi precedenti.
     * <p>
     * Summary: Il test verifica che {@code previous()} lanci {@code NoSuchElementException} quando si tenta di chiamarlo 
     *          mentre l'iteratore è posizionato all'inizio della lista (o comunque non ha elementi precedenti).
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare che il metodo segnali correttamente la mancanza di elementi precedenti, prevenendo accessi fuori dai limiti.
     * <p>
     * Test Description: 1) Si tenta di chiamare {@code previous()} immediatamente, senza che sia stato precedentemente chiamato {@code next()} o 
     *                      che l'iteratore sia stato posizionato dopo il primo elemento.
     * <p>
     * Preconditions: L'iteratore è all'inizio di una lista popolata : ["zero", "uno", "due"].
     * <p>
     * Postconditions: Nessuna modifica allo stato dell'iteratore o della lista. Viene lanciata un'eccezione.
     * <p>
     * Expected Result: {@code NoSuchElementException} deve essere lanciata nel momento della chiamata al metodo {@code previous()}.
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
     * Summary: Il test verifica che {@code nextIndex()} restituisca correttamente l'indice dell'elemento che verrebbe restituito da una successiva chiamata a {@code next()},
     * riflettendo la posizione attuale del cursore, attraversando l'intera lista con un ciclo.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare che il metodo {@code nextIndex()} rifletta accuratamente la posizione del 
     * cursore dell'iteratore in relazione all'elemento successivo, testando sia l'inizio della lista, sia l'avanzamento progressivo, sia la posizione finale. 
     * Si valuta la coerenza dell'indice restituito con la dimensione della lista e la terminazione corretta dell'iterazione.
     * <p>
     * Test Description: 1) Si verifica {@code nextIndex()} con l'iteratore appena creato (all'inizio della lista), aspettandosi 0.
     *                   2) Un ciclo `while` itera finché {@code hasNext()} è true.
     *                   3) Ad ogni iterazione, si verifica che {@code nextIndex()} restituisca l'indice corrente dell'elemento che sta per essere restituito da {@code next()}.
     *                   4) Si chiama {@code next()} per avanzare il cursore.
     *                   5) Dopo il ciclo, si verifica che l'iteratore abbia raggiunto la fine della lista, ovvero che {@code nextIndex()} corrisponda alla dimensione della 
     *                      lista e {@code hasNext()} sia false.
     * <p>
     * Preconditions: L'iteratore è inizialmente posizionato all'inizio di una lista popolata con tre elementi (["zero", "uno", "due"]).
     * <p>
     * Postconditions: Nessuna modifica allo stato della lista. Lo stato del cursore dell'iteratore si sposta in avanti dopo ogni chiamata a {@code next()}, 
     * fino a raggiungere la fine della lista.
     * <p>
     * Expected Result: Inizialmente, {@code nextIndex()} deve essere 0. Ad ogni passo del ciclo, {@code nextIndex()} deve corrispondere all'indice dell'elemento 
     * che {@code next()} sta per restituire. Alla fine del ciclo, {@code nextIndex()} deve corrispondere alla dimensione della lista e {@code hasNext()} deve essere {@code false}.
     */
    @Test
    public void testNextIndexWithWhileLoop()
    {
        assertEquals(0, iterator.nextIndex());

        int expectedIndex = 0;
        while (iterator.hasNext())
        {
            // Prima di chiamare next(), nextIndex() dovrebbe essere l'indice dell'elemento che next() restituirà
            assertEquals(expectedIndex, iterator.nextIndex());
            iterator.next();
            expectedIndex++;
        }

        // Dopo il ciclo, l'iteratore è alla fine della lista
        assertEquals(list.size(), iterator.nextIndex()); // nextIndex() dovrebbe essere la dimensione della lista
        assertFalse(iterator.hasNext()); // Non ci sono più elementi successivi
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#previousIndex()}.
     * <p>
     * Summary: Il test verifica che {@code previousIndex()} restituisca correttamente l'indice dell'elemento che verrebbe restituito da una successiva chiamata a {@code previous()},
     * riflettendo la posizione attuale del cursore, attraversando l'intera lista con un ciclo.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare che il metodo {@code previousIndex()} rifletta accuratamente la 
     * posizione del cursore dell'iteratore in relazione all'elemento precedente. Il test valuta il comportamento all'inizio della lista, 
     * durante l'avanzamento e quando l'iteratore ha raggiunto la fine.
     * <p>
     * Test Description: 1) Si verifica {@code previousIndex()} con l'iteratore appena creato (all'inizio della lista), aspettandosi -1.
     *                   2) Si usa un ciclo `while` per avanzare l'iteratore attraverso tutti gli elementi della lista.
     *                   3) Ad ogni passo del ciclo, dopo aver chiamato {@code next()}, si verifica che {@code previousIndex()} sia uguale all'indice dell'elemento appena 
     *                      "passato" (ovvero l'elemento che `previous()` restituirebbe).
     *                   4) Dopo che l'iteratore ha attraversato tutta la lista, si verifica la sua posizione finale con {@code previousIndex()} e {@code hasNext()}.
     * <p>
     * Preconditions: L'iteratore è inizialmente posizionato all'inizio di una lista popolata con almeno tre elementi (["zero", "uno", "due"]).
     * <p>
     * Postconditions: Nessuna modifica allo stato della lista. Lo stato del cursore dell'iteratore si sposta in avanti dopo ogni chiamata a {@code next()}, fino a raggiungere la fine della lista.
     * <p>
     * Expected Result: Inizialmente, {@code previousIndex()} deve essere -1. Dopo ogni chiamata a {@code next()}, {@code previousIndex()} deve essere l'indice dell'elemento 
     * appena restituito da {@code next()} (ovvero il cursore meno uno). 
     * Alla fine del ciclo, {@code previousIndex()} deve corrispondere alla dimensione della lista meno uno (l'indice dell'ultimo elemento). {@code hasNext()} deve essere {@code false}.
     */
    @Test
    public void testPreviousIndexWithWhileLoop()
    {
        assertEquals(-1, iterator.previousIndex());

        int currentElementIndex = 0;
        while (iterator.hasNext())
        {
            iterator.next();
            // Ora previousIndex() dovrebbe puntare all'elemento appena passato
            assertEquals(currentElementIndex, iterator.previousIndex());
            currentElementIndex++;
        }

        // Dopo il ciclo, l'iteratore è alla fine della lista
        assertEquals(list.size() - 1, iterator.previousIndex());
        assertFalse(iterator.hasNext());
    }

    // --- TEST METODI DI MODIFICA ---

    /**
     * Test del metodo {@link myAdapter.ListIterator#remove()}.
     * <p>
     * Summary: Il test verifica che il metodo {@code remove()} rimuova correttamente l'ultimo elemento restituito da una precedente chiamata a {@code next()},
     * assicurando che lo stato della lista e dell'iteratore sia aggiornato in modo consistente.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare che {@code remove()} elimini
     * l'elemento corretto dalla lista e che gestisca gli indici del cursore e di {@code lastReturned} in maniera appropriata.
     * Si verifica che la dimensione della lista si riduca e che gli elementi successivi si spostino per occupare lo spazio liberato.
     * <p>
     * Test Description: 1) Si registra la dimensione iniziale della lista.
     *                   2) L'iteratore avanza chiamando {@code next()} una volta, per posizionarsi sull'elemento da rimuovere ("zero"), e si cattura il riferimento all'oggetto che 
     *                      sarà il nuovo primo elemento.
     *                   3) Si chiama {@code remove()} per eliminare l'elemento appena restituito.
     *                   4) Si verifica che la dimensione della lista sia diminuita di uno rispetto alla dimensione originale.
     *                   5) Si verifica che l'elemento rimosso ("zero") non sia più presente nella lista.
     *                   6) Si verifica che l'elemento che originariamente era successivo ("uno") sia ora il primo elemento della lista.
     *                   7) Si verifica che {@code nextIndex()} sia 0 e {@code previousIndex()} sia -1, indicando che il cursore è stato 
     *                      correttamente riposizionato dopo la rimozione e che {@code lastReturned} è stato resettato.
     * <p>
     * Preconditions: La lista è popolata con i seguenti elementi: ["zero", "uno", "due"]. L'iteratore è all'inizio della lista.
     * <p>
     * Postconditions: L'elemento "zero" viene rimosso dalla lista. La dimensione della lista diminuisce di uno.
     * Gli elementi successivi vengono shiftati a sinistra. Il cursore dell'iteratore viene posizionato all'indice dell'elemento rimosso (che ora è occupato dall'elemento successivo),
     * e {@code lastReturned} viene resettato a -1.
     * <p>
     * Expected Result: La dimensione finale della lista deve essere la dimensione originale meno 1. L'elemento "zero" non deve essere più presente nella lista.
     * L'elemento "uno" deve diventare il nuovo elemento all'indice 0. {@code nextIndex()} deve essere 0 e {@code previousIndex()} deve essere -1.
     */
    @Test
    public void testRemoveAfterNext()
    {
        int sizeOriginale = list.size();

        Object removedObject = iterator.next();        // Restituisce "zero", cursor = 1, lastReturned = 0
        Object newFirstObject = list.get(iterator.nextIndex());
        iterator.remove();      

        assertEquals(sizeOriginale - 1, list.size());          // Verifica dimensione
        assertFalse(list.contains(removedObject));                         // Verifica che "zero" sia rimosso
        assertEquals(newFirstObject, list.get(0));                // Verifica che "uno" sia il nuovo primo elemento

        assertEquals(0, iterator.nextIndex());    // Cursore è tornato all'indice dove c'era "zero", ma ora c'è "uno"
        assertEquals(-1, iterator.previousIndex()); // Non c'è un elemento precedente (lastReturned è -1)
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#remove()}.
     * <p>
     * Summary: Il test verifica che il metodo {@code remove()} rimuova correttamente l'ultimo elemento restituito da una precedente chiamata a {@code previous()},
     * assicurando che lo stato della lista e dell'iteratore sia aggiornato in modo consistente.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare che {@code remove()} elimini
     * l'elemento corretto dalla lista e che gestisca gli indici del cursore e di {@code lastReturned} in maniera appropriata,
     * anche quando l'ultima operazione di navigazione è stata all'indietro. Si verifica che la dimensione della lista si riduca
     * e che gli indici del cursore riflettano correttamente la rimozione.
     * <p>
     * Test Description: 1) Si registra la dimensione iniziale della lista.
     *                   2) Si crea un nuovo iteratore posizionato alla fine della lista.
     *                   3) L'iteratore arretra chiamando {@code previous()} una volta, per posizionarsi sull'elemento da rimuovere ("due"), e 
     *                      si cattura il riferimento all'oggetto che sarà al suo posto dopo la rimozione.
     *                   4) Si chiama {@code remove()} per eliminare l'elemento appena restituito.
     *                   5) Si verifica che la dimensione della lista sia diminuita di uno rispetto alla dimensione originale.
     *                   6) Si verifica che l'elemento rimosso ("due") non sia più presente nella lista.
     *                   7) Si verifica che l'elemento che originariamente era all'indice 1 ("uno") sia ancora presente al suo indice.
     *                   8) Si verifica che {@code nextIndex()} e {@code previousIndex()} siano stati aggiornati correttamente per riflettere la rimozione e
     *                      il nuovo posizionamento del cursore.
     *                   9) Si verifica che {@code lastReturned} sia stato resettato a -1.
     * <p>
     * Preconditions: La lista è popolata con i seguenti elementi: ["zero", "uno", "due"]. L'iteratore viene inizializzato alla fine della lista.
     * <p>
     * Postconditions: L'elemento "due" viene rimosso dalla lista. La dimensione della lista diminuisce di uno.
     * Il cursore dell'iteratore mantiene la sua posizione relativa rispetto agli elementi non rimossi,
     * e {@code lastReturned} viene resettato a -1.
     * <p>
     * Expected Result: La dimensione finale della lista deve essere la dimensione originale meno 1. L'elemento "due" non deve essere più presente nella lista.
     * L'elemento "uno" deve rimanere all'indice 1. {@code nextIndex()} deve essere 2 e {@code previousIndex()} deve essere 1.
     */
    @Test
    public void testRemoveAfterPrevious()
    {
        int sizeOriginale = list.size(); // Supponiamo 3 (zero, uno, due)

        iterator = list.listIterator(list.size());
        Object removedObject = iterator.previous();
        Object unaffectedObject = list.get(1);

        iterator.remove();

        assertEquals(sizeOriginale - 1, list.size());           // Verifica dimensione: 3 - 1 = 2
        assertFalse(list.contains(removedObject));              // Verifica che "due" sia rimosso
        assertEquals(unaffectedObject, list.get(1));      // Verifica che "uno" sia ancora all'indice 1

        assertEquals(2, iterator.nextIndex());                  // Cursore è ancora a 2 (che è la nuova dimensione)
        assertEquals(1, iterator.previousIndex());              // Previous index è 1 (l'ultimo elemento prima del cursore)
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#remove()} senza chiamare {@code next()} o {@code previous()}.
     * <p>
     * Summary: Il test verifica che il metodo {@code remove()} lanci correttamente una {@code IllegalStateException} se 
     * viene chiamato prima che sia stato restituito un elemento da {@code next()} o {@code previous()}.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare che il metodo {@code remove()} faccia rispettare la sua precondizione fondamentale: 
     * l'obbligo di aver prima navigato l'iteratore (con {@code next()} o {@code previous()}) per identificare un elemento da rimuovere. 
     * Questo previene operazioni di rimozione ambigue o errate.
     * <p>
     * Test Description: 1) L'iteratore viene inizializzato, trovandosi di default all'inizio della lista e senza aver ancora restituito alcun elemento.
     *                   2) Si tenta immediatamente di chiamare il metodo {@code remove()}.
     * <p>
     * Preconditions: L'iteratore è stato appena inizializzato e quindi il suo stato interno {@code lastReturned} è -1 
     * (nessun elemento è stato ancora restituito da {@code next()} o {@code previous()}). 
     * <p>
     * Postconditions: Nessuna modifica allo stato della lista o dell'iteratore. Viene lanciata una {@code IllegalStateException} impedendo l'esecuzione del metodo.
     * <p>
     * Expected Result: Una {@code IllegalStateException} deve essere lanciata nel momento in cui si tenta di chiamare {@code remove()} 
     * senza una precedente chiamata valida a {@code next()} o {@code previous()}.
     */
    @Test(expected = IllegalStateException.class)
    public void testRemoveWithoutNextOrPrevious()
    {
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
    public void testRemoveWhileLoop() 
    {
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