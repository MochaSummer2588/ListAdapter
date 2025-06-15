package myTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import myAdapter.*;
import myExceptions.IllegalStateException;

/**
 * <b>Summary:</b>
 * <p>
 * Questa classe contiene una suite completa di test per {@link myAdapter.ListIterator} su una lista popolata.
 * Sono presenti test per tutti i costruttori e i metodi dell'iteratore: navigazione (hasNext, next, hasPrevious, previous, nextIndex, previousIndex),
 * modifica (add, remove, set), gestione di elementi null, gestione di indici e stato interno, e verifica delle eccezioni.
 * Ogni metodo è testato sia in condizioni normali che in presenza di errori o casi limite.
 * <br>
 * <b>Test Case Design:</b>
 * <p>
 * La motivazione di questa suite è assicurare che l'iteratore si comporti correttamente su una lista non vuota,
 * rispettando le specifiche delle interfacce Java Collections per la navigazione bidirezionale, la modifica sicura della lista,
 * la gestione dello stato interno (cursore, lastReturned), e la robustezza contro sequenze di operazioni miste e input errati.
 * La separazione tra lista popolata e vuota permette di coprire sia i casi d'uso tipici che quelli di bordo.
 */
public class TestListIteratorPopulated 
{

    /**
     * Costruttore predefinito per i test di {@code TestListIteratorPopulated}.
     * Non esegue inizializzazioni specifiche, affidandosi al metodo {@code setup()}.
     */
    public TestListIteratorPopulated() 
    {
        // Nessuna logica di inizializzazione complessa qui, JUnit si occupa del setup.
    }

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
     * Test Description: 1) L'iteratore viene spostato alla posizione successiva dall'inizio della lista e l'ogegtto ritornato viene verificato se e' effettivamente quello
     *                   2) Vengono verificati gli indici : precedente e successivo
     *                   3) Itero nuovamente fino ad arrivare a fine lista
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
     *                   2) Viene creato un array con dimensione maggiore rispetto a quella della lista in questo modo non si verifica {@code IndexOutOfBoundsException}. 
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
        assertTrue("L'elemento successivo non esiste",list.contains(list.get(iterator.previousIndex()))); // Verifica che l'elemento successivo esista
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
        iterator.previous();
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
            assertEquals("Non sono uguali i due elementi",currentElementIndex, iterator.previousIndex());
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

        assertEquals("Dimensione errata!", sizeOriginale - 1, list.size());          // Verifica dimensione
        assertFalse("La lista contiene ancora l'elemento",list.contains(removedObject));                         // Verifica che "zero" sia rimosso
        assertEquals("Non sosno uguali",newFirstObject, list.get(0));                // Verifica che "uno" sia il nuovo primo elemento

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
     * Test del metodo {@link myAdapter.ListIterator#remove()} per svuotare la lista.
     * <p>
     * Summary: Il test verifica che il metodo {@code remove()} possa essere usato ripetutamente in combinazione con {@code next()}
     * per rimuovere tutti gli elementi da una lista, fino a renderla completamente vuota.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare che l'operazione di rimozione sia consistente e che
     * l'iteratore gestisca correttamente lo stato della lista e i propri indici durante un'iterazione completa di rimozione.
     * Si verifica che ogni elemento venga rimosso e che la dimensione della lista si riduca progressivamente fino a zero.
     * <p>
     * Test Description: 1) Si registra la dimensione iniziale della lista.
     *                   2) Si usa un ciclo `while` che continua finché ci sono elementi successivi da attraversare (`hasNext()`).
     *                   3) All'interno del ciclo, si chiama {@code next()} per ottenere un elemento e far avanzare il cursore, impostando {@code lastReturned}.
     *                   4) Immediatamente dopo, si chiama {@code remove()} per eliminare l'elemento appena restituito.
     *                   5) Dopo il ciclo, si verifica che la lista sia completamente vuota e che la sua dimensione sia 0.
     *                   6) Si verificano anche gli indici finali dell'iteratore per assicurare che siano nello stato corretto per una lista vuota.
     * <p>
     * Preconditions: La lista è popolata con un numero noto di elementi: ["zero", "uno", "due"]. L'iteratore è all'inizio della lista.
     * <p>
     * Postconditions: Tutti gli elementi della lista vengono rimossi. La lista risulta vuota. Il cursore dell'iteratore si trova all'inizio della lista (che ora è vuota), 
     * e {@code lastReturned} è resettato a -1.
     * <p>
     * Expected Result: La dimensione finale della lista deve essere 0. La lista non deve contenere alcun elemento.
     * L'iteratore deve indicare che non ci sono elementi né successivi né precedenti ({@code hasNext()} e {@code hasPrevious()} false),
     * e gli indici {@code nextIndex()} e {@code previousIndex()} devono essere rispettivamente 0 e -1.
     */
    @Test
    public void testRemoveUntilListIsEmpty()
    {
        int originalSize = list.size();

        int removedCount = 0;
        while (iterator.hasNext())
        {
            iterator.next();    // Sposta il cursore e imposta lastReturned (es. "zero", lastReturned = 0)
            iterator.remove();  // Rimuove l'elemento. lastReturned = -1. Cursore si sposta indietro se necessario.
            removedCount++;     // Contatore degli elementi rimossi
        }

        // Verifica finale
        assertEquals(0, list.size()); // La lista dovrebbe essere completamente vuota
        assertTrue(list.isEmpty());   // Verifica anche con isEmpty()

        // Verifica che siano stati rimossi tutti gli elementi originali
        assertEquals(originalSize, removedCount);

        // Verifica lo stato finale dell'iteratore
        assertFalse(iterator.hasNext());
        assertFalse(iterator.hasPrevious());
        assertEquals(0, iterator.nextIndex());
        assertEquals(-1, iterator.previousIndex());
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#set(Object)}.
     * <p>
     * Summary: Il test verifica che il metodo {@code set()} sostituisca correttamente l'ultimo elemento restituito da una precedente chiamata a {@code next()},
     * aggiornando la lista senza modificarne la dimensione.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare che {@code set()} modifichi l'elemento corretto all'interno della lista
     * (quello identificato dall'ultima operazione di navigazione) e che la lista sottostante venga aggiornata di conseguenza,
     * mantenendo inalterati gli altri elementi e la dimensione complessiva.
     * <p>
     * Test Description: 1) Si registra la dimensione iniziale della lista.
     *                   2) L'iteratore avanza chiamando {@code next()} una volta, per ottenere l'elemento all'indice 0 ("zero") e impostare {@code lastReturned}.
     *                   3) Si chiama {@code set()} con un nuovo oggetto ("nuovoElemento") per sostituire l'elemento appena restituito.
     *                   4) Si verifica che la dimensione della lista sia rimasta invariata.
     *                   5) Si verifica che l'elemento all'indice 0 sia ora il "nuovoElemento".
     *                   6) Si verifica che gli altri elementi della lista ("uno", "due") siano rimasti inalterati alle loro posizioni originali.
     *                   7) Si verifica che gli indici dell'iteratore non siano stati influenzati dall'operazione di {@code set()} (il cursore non si sposta).
     * <p>
     * Preconditions: La lista è popolata in questo modo: ["zero", "uno", "due"]. L'iteratore è all'inizio della lista.
     * <p>
     * Postconditions: L'elemento precedentemente restituito da {@code next()} (o {@code previous()}) viene sostituito dal nuovo elemento specificato.
     * La dimensione della lista rimane invariata. Gli altri elementi non vengono modificati né spostati. Lo stato del cursore dell'iteratore non cambia,
     * e {@code lastReturned} rimane l'indice dell'elemento che è stato appena modificato.
     * <p>
     * Expected Result: La dimensione finale della lista deve essere 3. L'elemento all'indice 0 deve essere "nuovoElemento".
     * L'elemento all'indice 1 deve essere "uno" e l'elemento all'indice 2 deve essere "due".
     * Il {@code nextIndex()} dell'iteratore deve rimanere 1 e il {@code previousIndex()} deve rimanere 0.
     */
    @Test
    public void testSetAfterNext()
    {
        int sizeOriginale = list.size(); // Salva la dimensione iniziale (3)

        iterator.next();                 // Restituisce "zero". lastReturned = 0, cursor = 1.
        
        Object elementAtIndex1BeforeSet = list.get(1);      // Cattura "uno"
        Object elementAtIndex2BeforeSet = list.get(2);      // Cattura "due"

        int cursorAfterNext = iterator.nextIndex();             // Cursore dopo next() = 1
        int lastReturnedIndex = iterator.previousIndex();       // lastReturned = 0

        Object o = "nuovoElemento";

        iterator.set(o);                          // Sostituisce list[0] con "nuovoElemento". lastReturned rimane 0.

        assertEquals(sizeOriginale, list.size());                  // La dimensione della lista non deve cambiare (3)
        assertEquals(o, list.get(0));          // L'elemento all'indice 0 deve essere stato sostituito
        assertEquals(elementAtIndex1BeforeSet, list.get(1)); // L'elemento all'indice 1 deve essere rimasto "uno"
        assertEquals(elementAtIndex2BeforeSet, list.get(2)); // L'elemento all'indice 2 deve essere rimasto "due"

        assertEquals(cursorAfterNext, iterator.nextIndex());    // nextIndex() deve essere ancora 1
        assertEquals(lastReturnedIndex, iterator.previousIndex()); // previousIndex() deve essere ancora 0
    }

   /**
     * Test del metodo {@link myAdapter.ListIterator#set(Object)}.
     * <p>
     * Summary: Il test verifica che il metodo {@code set()} sostituisca correttamente l'ultimo elemento restituito da una precedente chiamata a {@code previous()},
     * aggiornando la lista senza modificarne la dimensione.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare che {@code set()} modifichi l'elemento corretto all'interno della lista
     * (quello identificato dall'ultima operazione di navigazione, in questo caso all'indietro) e che la lista sottostante venga aggiornata di conseguenza,
     * mantenendo inalterati gli altri elementi e la dimensione complessiva.
     * <p>
     * Test Description: 1) L'iteratore avanza chiamando {@code next()} tre volte, posizionandosi alla fine della lista (dopo "due").
     *                   2) L'iteratore arretra chiamando {@code previous()} una volta, per ottenere l'elemento all'indice 2 ("due") e impostare {@code lastReturned}.
     *                   3) Si chiama {@code set()} con un nuovo oggetto ("nuovoDue") per sostituire l'elemento appena restituito.
     *                   4) Si verifica che la dimensione della lista sia rimasta invariata.
     *                   5) Si verifica che l'elemento all'indice 2 sia ora "nuovoDue".
     *                   6) Si verifica che gli altri elementi della lista ("zero", "uno") siano rimasti inalterati alle loro posizioni originali.
     *                   7) Si verifica che gli indici dell'iteratore non siano stati influenzati dall'operazione di {@code set()} (il cursore non si sposta).
     * <p>
     * Preconditions: La lista è popolata con almeno tre elementi, ad esempio: ["zero", "uno", "due"]. L'iteratore è all'inizio della lista e viene poi posizionato per il test.
     * <p>
     * Postconditions: L'elemento precedentemente restituito da {@code previous()} viene sostituito dal nuovo elemento specificato.
     * La dimensione della lista rimane invariata. Gli altri elementi non vengono modificati né spostati. Lo stato del cursore dell'iteratore non cambia,
     * e {@code lastReturned} rimane l'indice dell'elemento che è stato appena modificato.
     * <p>
     * Expected Result: La dimensione finale della lista deve essere 3. L'elemento all'indice 2 deve essere "nuovoDue".
     * L'elemento all'indice 0 deve essere "zero" e l'elemento all'indice 1 deve essere "uno".
     * Il {@code nextIndex()} dell'iteratore deve rimanere 2 e il {@code previousIndex()} deve rimanere 1.
     */
    @Test
    public void testSetAfterPrevious()
    {
        int sizeOriginale = list.size(); // Salva la dimensione iniziale (3)

        // Posiziona l'iteratore:
        iterator.next();    // Restituisce "zero". lastReturned = 0, cursor = 1.
        iterator.next();    // Restituisce "uno".  lastReturned = 1, cursor = 2.
        iterator.next();    // Restituisce "due".  lastReturned = 2, cursor = 3.

        // Ora l'iteratore è alla fine.
        // nextIndex() = 3, previousIndex() = 2

        // Cattura gli elementi prima della modifica per verificarli dopo
        Object elementAtIndex0BeforeSet = list.get(0); // "zero"
        Object elementAtIndex1BeforeSet = list.get(1); // "uno"

        int cursorAfterNavigations = iterator.nextIndex();     // Cursore dopo next() tre volte = 3
        int lastReturnedIndexBeforeSet = iterator.previousIndex(); // lastReturned sarà 2 dopo previous()

        // Chiama previous() per impostare lastReturned all'elemento da modificare
        Object returnedByPrevious = iterator.previous(); // Restituisce "due". lastReturned = 2, cursor = 2.
        Object o = "nuovoDue";

        iterator.set(o); // Sostituisce list[2] con "nuovoDue". lastReturned rimane 2.

        // Verifica postcondizioni sulla lista
        assertEquals(sizeOriginale, list.size());           // La dimensione della lista non deve cambiare (3)
        assertEquals(o, list.get(2));              // L'elemento all'indice 2 deve essere stato sostituito
        assertEquals(elementAtIndex0BeforeSet, list.get(0)); // L'elemento all'indice 0 deve essere rimasto "zero"
        assertEquals(elementAtIndex1BeforeSet, list.get(1)); // L'elemento all'indice 1 deve essere rimasto "uno"

        assertEquals(cursorAfterNavigations -1, iterator.nextIndex()); // nextIndex() deve essere 2 (era 3, ma previous lo ha spostato a 2)
        assertEquals(lastReturnedIndexBeforeSet -1, iterator.previousIndex()); // previousIndex() deve essere 1 (il previousIndex DOPO la chiamata a previous())
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#set(Object)} senza chiamare {@code next()} o {@code previous()}.
     * <p>
     * Summary: Il test verifica che il metodo {@code set()} lanci correttamente una {@code IllegalStateException} se viene chiamato prima che sia stato 
     * restituito un elemento da {@code next()} o {@code previous()}.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare che il metodo {@code set()} faccia rispettare la sua precondizione fondamentale: 
     * l'obbligo di aver prima navigato l'iteratore (con {@code next()} o {@code previous()}) per identificare un elemento da modificare. 
     * Questo previene operazioni di modifica ambigue o errate.
     * <p>
     * Test Description: 1) L'iteratore viene inizializzato, trovandosi di default all'inizio della lista e senza aver ancora restituito alcun elemento 
     *                      (il suo stato interno {@code lastReturned} è -1).
     *                   2) Si tenta immediatamente di chiamare il metodo {@code set()} con un nuovo elemento.
     * <p>
     * Preconditions: L'iteratore è stato appena inizializzato. La lista e' popolata in questo modo: ["zero", "uno", "due"]
     * <p>
     * Postconditions: Nessuna modifica allo stato della lista o dell'iteratore. Viene lanciata una {@code IllegalStateException} impedendo l'esecuzione del metodo.
     * <p>
     * Expected Result: Una {@code IllegalStateException} deve essere lanciata nel momento in cui si tenta di chiamare {@code set()} senza una precedente chiamata valida a {@code next()} o {@code previous()}, o dopo una chiamata a {@code remove()} o {@code add()}.
     */
    @Test(expected = IllegalStateException.class)
    public void testSetWithoutNextOrPrevious()
    {
        iterator.set("nuovoElemento");
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#set(Object)} dopo una chiamata a {@code remove()}.
     * <p>
     * Summary: Il test verifica che il metodo {@code set()} lanci correttamente una {@code IllegalStateException} se viene chiamato dopo una precedente invocazione di {@code remove()}.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare che il metodo {@code set()} faccia rispettare la sua precondizione che l'ultimo 
     * elemento restituito da {@code next()} o {@code previous()} sia ancora valido e presente nella lista. 
     * Poiché {@code remove()} invalida questo riferimento, {@code set()} non dovrebbe essere permesso. 
     * Questo garantisce la coerenza dello stato dell'iteratore e previene modifiche a un elemento non più identificabile.
     * <p>
     * Test Description: 1) L'iteratore avanza chiamando {@code next()} una volta, per posizionarsi su un elemento valido e impostare {@code lastReturned}.
     *                   2) Si chiama {@code remove()} per eliminare l'elemento appena restituito. Questa operazione invalida lo stato interno di {@code lastReturned} resettandolo a -1.
     *                   3) Si tenta di chiamare {@code set()} con un nuovo elemento, aspettandosi un'eccezione.
     * <p>
     * Preconditions: La lista è popolata: ["zero", "uno", "due"]. L'iteratore viene posizionato su un elemento valido.
     * <p>
     * Postconditions: La lista avrà subito la rimozione del primo elemento. La successiva chiamata a {@code set()} non modifica ulteriormente la lista, 
     * ma provoca il lancio di una {@code IllegalStateException}. Lo stato del cursore dell'iteratore e di {@code lastReturned} rimangono invariati dopo il tentativo di `set()`.
     * <p>
     * Expected Result: Una {@code IllegalStateException} deve essere lanciata nel momento in cui si tenta di chiamare {@code set()} dopo una chiamata a {@code remove()}.
     */
    @Test(expected = IllegalStateException.class)
    public void testSetAfterRemove()
    {

        iterator.next();    // Restituisce "zero". lastReturned = 0, cursor = 1.
        iterator.remove();  // Rimuove "zero". lastReturned = -1, cursor = 0.
                            // A questo punto, lastReturned è -1, indicando che non c'è un elemento valido da settare.

        iterator.set("dopoRemove"); // Questa chiamata dovrebbe lanciare IllegalStateException
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#set(Object)} dopo una chiamata a {@code add()}.
     * <p>
     * Summary: Il test verifica che il metodo {@code set()} lanci correttamente una {@code IllegalStateException} se viene chiamato dopo una precedente invocazione di {@code add()}.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare che il metodo {@code set()} faccia rispettare la sua precondizione che l'ultimo elemento restituito da 
     * {@code next()} o {@code previous()} sia valido e che l'operazione di {@code add()} resetta lo stato di {@code lastReturned} a -1, invalidando la possibilità 
     * di chiamare {@code set()} immediatamente dopo. Questo garantisce la coerenza dello stato dell'iteratore.
     * <p>
     * Test Description: 1) L'iteratore aggiunge un nuovo elemento alla lista chiamando {@code add()}. Questa operazione invalida lo stato interno di {@code lastReturned} resettandolo a -1.
     *                   2) Si tenta immediatamente di chiamare {@code set()} con un nuovo valore, aspettandosi un'eccezione.
     * <p>
     * Preconditions: L'iteratore è stato inizializzato e la lista e' popolata: ["zero", "uno", "due"].
     * <p>
     * Postconditions: La lista avrà subito l'aggiunta di un elemento. La successiva chiamata a {@code set()} non modifica ulteriormente la lista, 
     * ma provoca il lancio di una {@code IllegalStateException}. Lo stato del cursore dell'iteratore e di {@code lastReturned} rimangono invariati dopo il tentativo di `set()`.
     * <p>
     * Expected Result: Una {@code IllegalStateException} deve essere lanciata nel momento in cui si tenta di chiamare {@code set()} dopo una chiamata a {@code add()}.
     */
    @Test(expected = IllegalStateException.class)
    public void testSetAfterAdd()
    {
        // Dopo add(), lastReturned viene impostato a -1.
        iterator.add("elemento"); // Aggiunge "elemento" alla lista. lastReturned = -1.

        // A questo punto, lastReturned è -1, indicando che non c'è un elemento valido da settare.
        iterator.set("nuovoValore"); // Questa chiamata dovrebbe lanciare IllegalStateException
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#add(Object)}.
     * <p>
     * Summary: Il test verifica che il metodo {@code add()} inserisca correttamente un nuovo elemento nella lista alla posizione corrente del cursore,
     * assicurando che gli elementi esistenti vengano spostati e che lo stato dell'iteratore sia aggiornato in modo consistente.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare che {@code add()} esegua un'inserzione e non una sovrascrittura.
     * Si verifica che la dimensione della lista aumenti, che il nuovo elemento sia posizionato correttamente, che gli elementi preesistenti
     * siano stati "shiftati" (spostati) e che gli indici del cursore riflettano il nuovo stato dopo l'inserimento.
     * <p>
     * Test Description: 1) Si registra la dimensione iniziale della lista.
     *                   2) Si chiama {@code add()} per inserire un nuovo elemento ("nuovo") all'inizio della lista (poiché l'iteratore è inizialmente a indice 0).
     *                   3) Si verifica che la dimensione della lista sia aumentata di uno.
     *                   4) Si verifica che il "nuovo" elemento sia ora all'indice 0.
     *                   5) Si verifica che l'elemento che originariamente era all'indice 0 ("zero") sia ora all'indice 1, confermando lo shift.
     *                   6) Si verifica che {@code nextIndex()} sia avanzato di uno e {@code previousIndex()} sia stato aggiornato per riflettere il nuovo elemento.
     *                   7) Si verifica che {@code lastReturned} sia stato resettato a -1.
     * <p>
     * Preconditions: La lista è popolata: ["zero", "uno", "due"]. L'iteratore è all'inizio della lista (cursore a 0).
     * <p>
     * Postconditions: Un nuovo elemento viene aggiunto alla lista all'indice del cursore. La dimensione della lista aumenta di uno.
     * Gli elementi che erano all'indice del cursore o successivi vengono shiftati di una posizione a destra.
     * Il cursore dell'iteratore avanza di uno, posizionandosi dopo l'elemento appena aggiunto. {@code lastReturned} viene resettato a -1.
     * <p>
     * Expected Result: La dimensione finale della lista deve essere la dimensione originale più 1 (in questo caso 4).
     * L'elemento "nuovo" deve essere all'indice 0. L'elemento "zero" deve essere all'indice 1.
     * {@code nextIndex()} deve essere 1 e {@code previousIndex()} deve essere 0.
     */
    @Test
    public void testAdd()
    {
        int sizeOriginale = list.size(); // Salva la dimensione iniziale (3)

        // Aggiunge "nuovo" all'indice 0 (poiché il cursore è 0)
        // cursor = 1 dopo add()
        iterator.add("nuovo");

        // Verifica postcondizioni sulla lista
        assertEquals(sizeOriginale + 1, list.size());                 // La dimensione deve essere aumentata a 4
        assertEquals("nuovo", list.get(0));                     // "nuovo" deve essere all'indice 0
        assertEquals("zero", list.get(1));                      // "zero" deve essere stato shiftato all'indice 1
        assertEquals("uno", list.get(2));                       // "uno" rimane all'indice 2
        assertEquals("due", list.get(3));                       // "due" rimane all'indice 3

        // Verifica postcondizioni sull'iteratore
        assertEquals(1, iterator.nextIndex());                  // Il cursore deve essere avanzato oltre l'elemento aggiunto
        assertEquals(0, iterator.previousIndex());              // previousIndex deve essere l'indice dell'elemento appena aggiunto
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#remove()} dopo una chiamata a {@code add()}.
     * <p>
     * Summary: Il test verifica che il metodo {@code remove()} lanci correttamente una {@code IllegalStateException} se viene chiamato dopo una precedente invocazione di {@code add()}.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurare che il metodo {@code remove()} faccia rispettare la sua precondizione che un elemento sia stato
     * restituito da una precedente operazione di navigazione ({@code next()} o {@code previous()}). 
     * Poiché {@code add()} resetta lo stato di {@code lastReturned} a -1 (indicando che non c'è un elemento "ultimo restituito" da rimuovere), 
     * {@code remove()} non dovrebbe essere permesso immediatamente dopo. Questo garantisce la coerenza dello stato dell'iteratore.
     * <p>
     * Test Description: 1) L'iteratore aggiunge un nuovo elemento alla lista chiamando {@code add()}. Questa operazione invalida lo stato interno di {@code lastReturned} resettandolo a -1.
     *                   2) Si tenta immediatamente di chiamare {@code remove()}, aspettandosi un'eccezione.
     * <p>
     * Preconditions: L'iteratore è stato inizializzato e la lista e' popolata: ["zero", "uno", "due"].
     * <p>
     * Postconditions: La lista avrà subito l'aggiunta di un elemento. La successiva chiamata a {@code remove()} non modifica ulteriormente la lista, 
     * ma provoca il lancio di una {@code IllegalStateException}. Lo stato del cursore dell'iteratore e di {@code lastReturned} rimangono invariati dopo il tentativo di `remove()`.
     * <p>
     * Expected Result: Una {@code IllegalStateException} deve essere lanciata nel momento in cui si tenta di chiamare {@code remove()} dopo una chiamata a {@code add()}.
     */
    @Test(expected = IllegalStateException.class)
    public void testRemoveAfterAdd()
    {
        iterator.add("elemento"); // Aggiunge "elemento" alla lista. lastReturned = -1.

        // A questo punto, lastReturned è -1, indicando che non c'è un elemento valido da rimuovere.
        iterator.remove();
    }

    /**
     * Test di combinazioni di navigazione e modifica: next, remove, add, next.
     * <p>
     * Summary: Il test verifica una sequenza complessa di operazioni miste (navigazione in avanti e indietro, rimozione, aggiunta e modifica di elementi)
     * per assicurare la consistenza dello stato dell'iteratore e della lista sottostante attraverso una normale sequenza di operazioni.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è simulare un'interazione utente più complessa con il `ListIterator`
     * per verificarne la robustezza e la capacità di mantenere la coerenza dello stato della lista e dei propri indici
     * dopo operazioni che alterano la struttura (add, remove) e il contenuto (set). Si traccia attentamente lo stato del cursore
     * e di `lastReturned` ad ogni passo.
     * <p>
     * Test Description:
     * 1) La lista iniziale è ["zero", "uno", "due"].
     * 2) Si chiama `next()` per ottenere "zero". Si verifica lo stato del cursore.
     * 3) Si chiama `remove()` per eliminare "zero". Si verificano la dimensione e il contenuto della lista.
     * 4) Si chiama `add()` per inserire "nuovoZero". Si verificano la dimensione e il contenuto.
     * 5) Si chiama `next()` per ottenere "uno". Si verificano lo stato del cursore e l'elemento restituito.
     * 6) Si chiama `set()` per modificare "uno" in "UNO_MODIFICATO". Si verifica il contenuto.
     * 7) Si chiama `next()` per ottenere "due". Si verifica che non ci siano altri elementi.
     * 8) Si chiama `previous()` per tornare a "due".
     * 9) Si chiama `remove()` per eliminare "due". Si verificano la dimensione e il contenuto finale della lista e gli indici dell'iteratore.
     * <p>
     * Preconditions: La lista è popolata con i seguenti elementi: ["zero", "uno", "due"]. L'iteratore è all'inizio della lista.
     * <p>
     * Postconditions: La lista è stata modificata attraverso le operazioni di rimozione e aggiunta/modifica.
     * Il cursore dell'iteratore e lo stato di `lastReturned` sono coerenti con la sequenza di operazioni eseguite.
     * <p>
     * Expected Result: La lista finale deve essere ["nuovoZero", "UNO_MODIFICATO"]. La dimensione finale deve essere 2.
     * Gli indici dell'iteratore devono riflettere il posizionamento finale del cursore.
     */
    @Test
    public void testMixedOperations()
    {
        // Precondizione: list = ["zero", "uno", "due"], size = 3
        // iterator: cursor=0, lastReturned=-1

        // Step 1: next()
        assertEquals("L'elemento restituito da next() non è 'zero' allo Step 1.", "zero", iterator.next()); // next() restituisce "zero"
        // Stato: list=["zero", "uno", "due"], cursor=1, lastReturned=0
        assertEquals("nextIndex() non è 1 allo Step 1.", 1, iterator.nextIndex());
        assertEquals("previousIndex() non è 0 allo Step 1.", 0, iterator.previousIndex());

        // Step 2: remove()
        iterator.remove(); // Rimuove l'ultimo elemento restituito ("zero")
        // Stato: list=["uno", "due"], cursor=0, lastReturned=-1
        assertEquals("La dimensione della lista non è 2 dopo remove() allo Step 2.", 2, list.size());
        assertFalse("La lista contiene 'zero' dopo remove() allo Step 2.", list.contains("zero"));
        assertEquals("L'elemento all'indice 0 non è 'uno' dopo remove() allo Step 2.", "uno", list.get(0)); // "uno" è ora il primo elemento
        assertEquals("nextIndex() non è 0 dopo remove() allo Step 2.", 0, iterator.nextIndex());
        assertEquals("previousIndex() non è -1 dopo remove() allo Step 2.", -1, iterator.previousIndex()); // previousIndex è -1 quando cursor è 0

        // Step 3: add()
        iterator.add("nuovoZero"); // Aggiunge "nuovoZero" alla posizione del cursore (0)
        // Stato: list=["nuovoZero", "uno", "due"], cursor=1, lastReturned=-1
        assertEquals("La dimensione della lista non è 3 dopo add() allo Step 3.", 3, list.size());
        assertEquals("L'elemento all'indice 0 non è 'nuovoZero' dopo add() allo Step 3.", "nuovoZero", list.get(0));
        assertEquals("L'elemento all'indice 1 non è 'uno' dopo add() allo Step 3.", "uno", list.get(1)); // "uno" è stato shiftato
        assertEquals("nextIndex() non è 1 dopo add() allo Step 3.", 1, iterator.nextIndex());
        assertEquals("previousIndex() non è 0 dopo add() allo Step 3.", 0, iterator.previousIndex());

        // Step 4: next()
        assertEquals("L'elemento restituito da next() non è 'uno' allo Step 4.", "uno", iterator.next()); // next() restituisce "uno"
        // Stato: list=["nuovoZero", "uno", "due"], cursor=2, lastReturned=1
        assertEquals("nextIndex() non è 2 allo Step 4.", 2, iterator.nextIndex());
        assertEquals("previousIndex() non è 1 allo Step 4.", 1, iterator.previousIndex());

        // Step 5: set()
        iterator.set("UNO_MODIFICATO"); // Sostituisce "uno" con "UNO_MODIFICATO"
        // Stato: list=["nuovoZero", "UNO_MODIFICATO", "due"], cursor=2, lastReturned=1
        assertEquals("L'elemento all'indice 1 non è 'UNO_MODIFICATO' dopo set() allo Step 5.", "UNO_MODIFICATO", list.get(1));
        assertEquals("L'elemento all'indice 0 è stato modificato inaspettatamente dopo set() allo Step 5.", "nuovoZero", list.get(0)); // Assicurati che gli altri non siano toccati
        assertEquals("L'elemento all'indice 2 è stato modificato inaspettatamente dopo set() allo Step 5.", "due", list.get(2));       // Assicurati che gli altri non siano toccati
        assertEquals("La dimensione della lista non è 3 dopo set() allo Step 5.", 3, list.size()); // Dimensione invariata

        // Step 6: next()
        assertEquals("L'elemento restituito da next() non è 'due' allo Step 6.", "due", iterator.next()); // next() restituisce "due"
        // Stato: list=["nuovoZero", "UNO_MODIFICATO", "due"], cursor=3, lastReturned=2
        assertFalse("hasNext() è true alla fine della lista allo Step 6.", iterator.hasNext()); // Dovrebbe essere alla fine della lista
        assertEquals("nextIndex() non è 3 allo Step 6.", 3, iterator.nextIndex());
        assertEquals("previousIndex() non è 2 allo Step 6.", 2, iterator.previousIndex());

        // Step 7: previous()
        assertEquals("L'elemento restituito da previous() non è 'due' allo Step 7.", "due", iterator.previous()); // previous() restituisce "due"
        // Stato: list=["nuovoZero", "UNO_MODIFICATO", "due"], cursor=2, lastReturned=2
        assertEquals("nextIndex() non è 2 dopo previous() allo Step 7.", 2, iterator.nextIndex());
        assertEquals("previousIndex() non è 1 dopo previous() allo Step 7.", 1, iterator.previousIndex());
        assertTrue("hasNext() è false dopo previous() allo Step 7.", iterator.hasNext()); // Ora dovrebbe avere un next (l'elemento "due" se lo ripassiamo)

        // Step 8: remove()
        iterator.remove(); // Rimuove l'ultimo elemento restituito ("due")
        // Stato: list=["nuovoZero", "UNO_MODIFICATO"], cursor=2, lastReturned=-1
        assertEquals("La dimensione della lista non è 2 dopo il secondo remove() allo Step 8.", 2, list.size()); // Dimensione ora 2
        assertFalse("La lista contiene 'due' dopo il secondo remove() allo Step 8.", list.contains("due")); // "due" non deve più esserci
        assertEquals("L'elemento all'indice 0 non è 'nuovoZero' dopo il secondo remove() allo Step 8.", "nuovoZero", list.get(0));
        assertEquals("L'elemento all'indice 1 non è 'UNO_MODIFICATO' dopo il secondo remove() allo Step 8.", "UNO_MODIFICATO", list.get(1));
        assertEquals("nextIndex() non è 2 dopo il secondo remove() allo Step 8.", 2, iterator.nextIndex()); // Cursore rimane alla posizione
        assertEquals("previousIndex() non è 1 dopo il secondo remove() allo Step 8.", 1, iterator.previousIndex()); // previousIndex è 1 (cursor - 1)
        assertFalse("hasNext() è true dopo il secondo remove() allo Step 8.", iterator.hasNext()); // Non ci sono più elementi dopo il cursore (alla fine)
    }

    /**
     * Test per la corretta gestione di elementi null.
     * <p>
     * Summary: Il test verifica che il metodo {@code add(Object)} dell'iteratore possa inserire correttamente un valore {@code null} nella lista
     * e che le successive operazioni di navigazione (come {@code next()}) restituiscano correttamente tale valore {@code null}.
     * <p>
     * Test Case Design: La motivazione dietro a questo test è assicurarsi che l'iteratore (e la lista sottostante)
     * gestiscano `null` come un elemento valido, senza lanciare `NullPointerException` (a meno che non sia esplicitamente
     * specificato diversamente nel contratto dell'interfaccia/implementazione). Si verifica l'inserimento,
     * la dimensione della lista, lo shifting degli elementi esistenti e la capacità di recuperare il valore `null` inserito.
     * <p>
     * Test Description: 1) Si registra la dimensione iniziale della lista.
     *                   2) Si chiama `add(null)` per inserire un elemento `null` all'inizio della lista (poiché l'iteratore è inizialmente a indice 0).
     *                   3) Si verifica che la dimensione della lista sia aumentata di uno.
     *                   4) Si verifica che l'elemento all'indice 0 sia `null`.
     *                   5) Si verifica che l'elemento che originariamente era all'indice 0 ("zero") sia ora all'indice 1, confermando lo shift.
     *                   6) Si verifica che `nextIndex()` e `previousIndex()` siano aggiornati correttamente dopo l'aggiunta.
     *                   7) Si naviga attraverso gli elementi successivi per confermare che la lista sia in uno stato coerente.
     *                   8) L'iteratore viene resettato all'inizio della lista per verificare esplicitamente che `next()` restituisca `null` come primo elemento.
     *                   9) Si verifica che il secondo elemento restituito da `next()` sia "zero".
     * <p>
     * Preconditions: La lista è popolata da tre elementi: ["zero", "uno", "due"]. L'iteratore è all'inizio della lista (cursore a 0).
     * <p>
     * Postconditions: La lista contiene un elemento `null` all'indice 0. La sua dimensione è aumentata di uno.
     * Gli elementi originali sono stati spostati. L'iteratore può navigare correttamente attraverso la lista modificata,
     * restituendo il valore `null` quando incontra l'elemento inserito.
     * <p>
     * Expected Result: La dimensione finale della lista deve essere 4. L'elemento all'indice 0 deve essere `null`.
     * `next()` deve restituire `null` quando si trova all'inizio della lista modificata, seguito da "zero".
     * Gli indici dell'iteratore devono essere coerenti con la navigazione.
     */
    @Test
        public void testAddNullElement() 
        {
        // Precondizione: list = ["zero", "uno", "due"], size = 3, cursor = 0, lastReturned = -1
        int originalSize = list.size(); // Salva la dimensione iniziale, es. 3

        // Aggiungi null all'inizio della lista (indice 0)
        iterator.add(null);
        // Stato atteso: list = [null, "zero", "uno", "due"]

        // Verifica stato dopo l'aggiunta di null
        assertEquals("La dimensione della lista non è corretta dopo l'aggiunta di null.", originalSize + 1, list.size()); // Dimensione deve essere 4
        assertNull("L'elemento all'indice 0 non è null dopo l'aggiunta.", list.get(0));                               // L'elemento all'indice 0 deve essere null
        assertEquals("L'elemento all'indice 1 non è 'zero' dopo l'aggiunta di null.", "zero", list.get(1));             // "zero" deve essere stato shiftato all'indice 1
        assertEquals("L'elemento all'indice 2 non è 'uno' dopo l'aggiunta di null.", "uno", list.get(2));                 // "uno" rimane all'indice 2
        assertEquals("L'elemento all'indice 3 non è 'due' dopo l'aggiunta di null.", "due", list.get(3));                 // "due" rimane all'indice 3
        assertEquals("nextIndex() non è 1 dopo l'aggiunta di null.", 1, iterator.nextIndex());                 // Cursore è avanzato dopo il null
        assertEquals("previousIndex() non è 0 dopo l'aggiunta di null.", 0, iterator.previousIndex());         // previousIndex punta al null aggiunto

        // Continua a navigare per verificare la consistenza
        assertEquals("Il primo next() dopo l'aggiunta di null non restituisce 'zero'.", "zero", iterator.next()); // next() restituisce "zero"
        assertEquals("Il secondo next() dopo l'aggiunta di null non restituisce 'uno'.", "uno", iterator.next());     // next() restituisce "uno"
        assertEquals("Il terzo next() dopo l'aggiunta di null non restituisce 'due'.", "due", iterator.next());     // next() restituisce "due"
        assertFalse("hasNext() è true quando dovrebbe essere false alla fine della lista.", iterator.hasNext());         // Dovrebbe essere alla fine

        // Reset l'iteratore all'inizio per testare esplicitamente next() su null
        iterator = list.listIterator(0); // cursor = 0, lastReturned = -1 (lista è [null, "zero", "uno", "due"])

        // Verifica che next() restituisca null correttamente
        assertNull("Il primo next() dopo il reset non restituisce null.", iterator.next()); // Il primo elemento (null) viene restituito
        assertEquals("nextIndex() non è 1 dopo il primo next() sul null.", 1, iterator.nextIndex()); // Cursore è su 1
        assertEquals("previousIndex() non è 0 dopo il primo next() sul null.", 0, iterator.previousIndex()); // previousIndex punta al null

        // Verifica il successivo elemento
        assertEquals("Il secondo next() dopo il reset non restituisce 'zero'.", "zero", iterator.next()); // Il secondo elemento ("zero") viene restituito
        assertEquals("nextIndex() non è 2 dopo il secondo next().", 2, iterator.nextIndex()); // Cursore è su 2
        assertEquals("previousIndex() non è 1 dopo il secondo next().", 1, iterator.previousIndex()); // previousIndex punta a "zero"
    }

}