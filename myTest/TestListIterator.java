package myTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import myAdapter.*;
import myExceptions.IllegalStateException;

/**
 * Suite di test per la classe {@link myAdapter.ListIterator} che implementa {@link myAdapter.HListIterator}.
 * <p>
 * Riassunto: Verifica il corretto funzionamento dell'iteratore bidirezionale {@code ListIterator}
 * inclusi i metodi di navigazione, modifica e gestione degli indici, e la corretta
 * emissione delle eccezioni previste.
 * <p>
 * Design: Utilizza JUnit 4.13.2. Ogni test case configura uno scenario specifico
 * per l'iteratore e verifica il comportamento atteso. Particolare attenzione è data
 * ai casi limite (liste vuote, inizio/fine lista) e alle condizioni che scatenano eccezioni.
 */
public class TestListIterator 
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
     * Verifica che l'iteratore sia inizializzato correttamente all'inizio della lista.
     * <p>
     * Test Case Design: Assicurarsi che un iteratore creato senza un indice specifico
     * parta con il cursore a 0 e {@code lastReturned} a -1.
     * <p>
     * Preconditions: Una `ListAdapter` popolata.
     * <p>
     * Postconditions: L'iteratore è posizionato all'inizio della lista.
     * <p>
     * Expected Result: {@code nextIndex()} deve essere 0, {@code previousIndex()} deve essere -1,
     * {@code hasNext()} deve essere true, {@code hasPrevious()} deve essere false.
     */
    @Test
    public void testConstructorDefaultPosition() {
        assertTrue(iterator.hasNext());
        assertFalse(iterator.hasPrevious());
        assertEquals(0, iterator.nextIndex());
        assertEquals(-1, iterator.previousIndex());
    }

    /**
     * Test del costruttore {@link myAdapter.ListIterator#ListIterator(myAdapter.ListAdapter, int)}.
     * <p>
     * Verifica che l'iteratore sia inizializzato correttamente a un indice specificato.
     * <p>
     * Test Case Design: Assicurarsi che un iteratore creato con un indice valido sia
     * posizionato correttamente in termini di cursore e stato.
     * <p>
     * Preconditions: Una `ListAdapter` popolata con 3 elementi.
     * <p>
     * Postconditions: L'iteratore è posizionato all'indice specificato.
     * <p>
     * Expected Result: L'iteratore posizionato all'indice 1 deve avere {@code nextIndex()} 1,
     * {@code previousIndex()} 0, {@code hasNext()} true, {@code hasPrevious()} true.
     */
    @Test
    public void testConstructorSpecificPosition() {
        iterator = list.listIterator(1); // Cursore a 1, lastReturned a -1
        assertEquals(1, iterator.nextIndex());
        assertEquals(0, iterator.previousIndex());
        assertTrue(iterator.hasNext());
        assertTrue(iterator.hasPrevious());
        assertEquals("uno", iterator.next()); // Verifica che next() funzioni dall'indice corretto
    }

    /**
     * Test del costruttore con indice fuori limite inferiore.
     * <p>
     * Verifica che il costruttore {@code ListIterator(list, index)} lanci
     * {@code IndexOutOfBoundsException} se l'indice è negativo.
     * <p>
     * Test Case Design: Assicurarsi che l'iteratore non possa essere inizializzato con
     * un indice inferiore a 0, rispettando il contratto dell'interfaccia.
     * <p>
     * Preconditions: Una `ListAdapter` popolata.
     * <p>
     * Postconditions: Nessuna modifica alla lista. Viene lanciata un'eccezione.
     * <p>
     * Expected Result: {@code IndexOutOfBoundsException} deve essere lanciata.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testConstructorIndexOutOfBoundsNegative() {
        new ListAdapter().listIterator(-1);
    }

    /**
     * Test del costruttore con indice fuori limite superiore.
     * <p>
     * Verifica che il costruttore {@code ListIterator(list, index)} lanci
     * {@code IndexOutOfBoundsException} se l'indice è maggiore della dimensione della lista.
     * <p>
     * Test Case Design: Assicurarsi che l'iteratore non possa essere inizializzato con
     * un indice superiore alla dimensione della lista, rispettando il contratto.
     * <p>
     * Preconditions: Una `ListAdapter` popolata.
     * <p>
     * Postconditions: Nessuna modifica alla lista. Viene lanciata un'eccezione.
     * <p>
     * Expected Result: {@code IndexOutOfBoundsException} deve essere lanciata.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testConstructorIndexOutOfBoundsGreaterThanSize() {
        new ListAdapter().listIterator(10); // Lista vuota
        // o per una lista popolata: new ListAdapter().listIterator(list.size() + 1);
    }

    /**
     * Test del costruttore con indice uguale alla dimensione della lista.
     * <p>
     * Verifica che l'iteratore possa essere inizializzato correttamente alla fine della lista.
     * <p>
     * Test Case Design: Assicurarsi che l'indice pari alla dimensione della lista sia un
     * valore valido per posizionare l'iteratore dopo l'ultimo elemento.
     * <p>
     * Preconditions: Una `ListAdapter` popolata.
     * <p>
     * Postconditions: L'iteratore è posizionato alla fine della lista.
     * <p>
     * Expected Result: {@code nextIndex()} deve essere uguale alla dimensione della lista,
     * {@code hasNext()} deve essere false, {@code hasPrevious()} deve essere true.
     */
    @Test
    public void testConstructorIndexAtSize() {
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
     * Verifica che {@code hasNext()} restituisca {@code true} quando ci sono elementi successivi.
     * <p>
     * Test Case Design: Assicurarsi che il metodo indichi correttamente la presenza di un prossimo elemento.
     * <p>
     * Preconditions: L'iteratore è all'inizio di una lista non vuota.
     * <p>
     * Postconditions: Nessuna modifica allo stato dell'iteratore o della lista.
     * <p>
     * Expected Result: {@code hasNext()} deve restituire {@code true}.
     */
    @Test
    public void testHasNextTrue() {
        assertTrue(iterator.hasNext());
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#hasNext()} su una lista vuota.
     * <p>
     * Verifica che {@code hasNext()} restituisca {@code false} su una lista vuota.
     * <p>
     * Test Case Design: Assicurarsi che il metodo gestisca correttamente il caso di una lista vuota.
     * <p>
     * Preconditions: L'iteratore è stato creato su una lista vuota.
     * <p>
     * Postconditions: Nessuna modifica allo stato dell'iteratore o della lista.
     * <p>
     * Expected Result: {@code hasNext()} deve restituire {@code false}.
     */
    @Test
    public void testHasNextFalseEmptyList() {
        list = new ListAdapter();
        iterator = list.listIterator();
        assertFalse(iterator.hasNext());
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#next()}.
     * <p>
     * Verifica che {@code next()} restituisca l'elemento corretto e avanzi il cursore.
     * <p>
     * Test Case Design: Assicurarsi che il metodo recuperi l'elemento giusto e aggiorni
     * correttamente la posizione dell'iteratore e l'indice dell'ultimo elemento restituito.
     * <p>
     * Preconditions: L'iteratore è all'inizio di una lista popolata.
     * <p>
     * Postconditions: Il cursore avanza, {@code lastReturned} viene aggiornato.
     * <p>
     * Expected Result: {@code next()} deve restituire "zero", {@code nextIndex()} deve essere 1,
     * {@code previousIndex()} deve essere 0.
     */
    @Test
    public void testNext() {
        assertEquals("zero", iterator.next());
        assertEquals(1, iterator.nextIndex());
        assertEquals(0, iterator.previousIndex());
        assertEquals("uno", iterator.next());
        assertEquals(2, iterator.nextIndex());
        assertEquals(1, iterator.previousIndex());
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#next()} quando non ci sono più elementi.
     * <p>
     * Verifica che {@code next()} lanci {@code NoSuchElementException} quando la fine della lista è raggiunta.
     * <p>
     * Test Case Design: Assicurarsi che il metodo segnali correttamente la mancanza di elementi successivi.
     * <p>
     * Preconditions: L'iteratore ha iterato attraverso tutti gli elementi della lista.
     * <p>
     * Postconditions: Nessuna modifica allo stato dell'iteratore o della lista. Viene lanciata un'eccezione.
     * <p>
     * Expected Result: {@code NoSuchElementException} deve essere lanciata.
     */
    @Test(expected = java.util.NoSuchElementException.class)
    public void testNextThrowsNoSuchElementException() {
        iterator.next(); // "zero"
        iterator.next(); // "uno"
        iterator.next(); // "due"
        iterator.next(); // Dovrebbe lanciare l'eccezione
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#hasPrevious()}.
     * <p>
     * Verifica che {@code hasPrevious()} restituisca {@code true} quando ci sono elementi precedenti.
     * <p>
     * Test Case Design: Assicurarsi che il metodo indichi correttamente la presenza di un elemento precedente
     * dopo che l'iteratore si è spostato in avanti.
     * <p>
     * Preconditions: L'iteratore è stato avanzato di almeno un elemento.
     * <p>
     * Postconditions: Nessuna modifica allo stato dell'iteratore o della lista.
     * <p>
     * Expected Result: {@code hasPrevious()} deve restituire {@code true}.
     */
    @Test
    public void testHasPreviousTrue() {
        iterator.next(); // Avanza per avere un precedente
        assertTrue(iterator.hasPrevious());
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#hasPrevious()} all'inizio della lista.
     * <p>
     * Verifica che {@code hasPrevious()} restituisca {@code false} quando l'iteratore è all'inizio.
     * <p>
     * Test Case Design: Assicurarsi che il metodo gestisca correttamente il caso in cui non ci sono
     * elementi precedenti.
     * <p>
     * Preconditions: L'iteratore è all'inizio di una lista (anche vuota o popolata).
     * <p>
     * Postconditions: Nessuna modifica allo stato dell'iteratore o della lista.
     * <p>
     * Expected Result: {@code hasPrevious()} deve restituire {@code false}.
     */
    @Test
    public void testHasPreviousFalseAtStart() {
        assertFalse(iterator.hasPrevious()); // Inizialmente all'inizio
    }

    /**
     * Test del metodo {@link myAdapter.ListIterator#previous()}.
     * <p>
     * Verifica che {@code previous()} restituisca l'elemento corretto e sposti il cursore indietro.
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
    public void testPrevious() {
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
     * Verifica che {@code previous()} lanci {@code NoSuchElementException} quando l'inizio della lista è raggiunto.
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
    public void testPreviousThrowsNoSuchElementException() {
        iterator.previous(); // Dovrebbe lanciare l'eccezione, essendo all'inizio
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