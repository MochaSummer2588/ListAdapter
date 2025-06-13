package myTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import myAdapter.*;

/**
 * Suite di test per la classe {@link myAdapter.ListAdapter}.
 * <p>
 * Riassunto: Verifica il corretto funzionamento dei metodi della classe {@code ListAdapter} che implementa l'interfaccia {@link myAdapter.HList}.
 * <p>
 * Design: Utilizza JUnit 4.13.2<br />
 *         La suite include test per metodi di accesso, modifica e interrogazione di una {@link myAdapter.HList}<br />
 *         attraverso la classe {@link myAdapter.ListAdapter}, con un'istanza ripopolata prima di ogni test case.<br />
 *         Si suppone che la classe {@link myAdapter.ListIterator} funzioni correttamente e sarà testata nella sua propria suite.
 */
public class TestListAdapterEmpty
{
    private ListAdapter list;

    /**
     * Configura l'ambiente di test popolando la lista che verrà manipolata.
     */
    @Before
    public void setUp() 
    {
        // Crea una lista vuota
        list = new ListAdapter();
    }

    //------- TEST DEL METODO add(Object) ----------

    /**
     * Test del metodo {@link HList#add(Object)}.
     *
     * @summary Verifica che un singolo elemento venga aggiunto correttamente alla lista.
     *
     * @description 1) Aggiunge un elemento alla lista vuota.<br />
     *              2) Verifica che sia stato inserito correttamente.<br />
     *              3) Verifica che la dimensione sia aumentata di 1.<br />
     *              4) Verifica che l'elemento sia quello atteso.<br />
     *
     * @pre La lista è vuota.
     * @post La lista contiene un solo elemento.
     * @expected {@code size()} restituisce 1 e {@code get(0)} è l'elemento aggiunto.
     */
    @Test
    public void testAddOneElement() 
    {
        int sizeOriginale = list.size();
        // Aggiunge un singolo elemento alla lista vuota
        boolean result = list.add("X");

        // Verifica che l'elemento sia stato aggiunto correttamente
        assertTrue(result);

        assertEquals(sizeOriginale + 1, list.size());
        assertEquals("X", list.get(0));
    }

    /**
     * Test del metodo {@link HList#add(Object)}.
     *
     * @summary Verifica che più elementi possano essere aggiunti alla lista.
     *
     * @description 1) Aggiunge tre elementi.<br />
     *              2) Verifica che siano stati inseriti correttamente.<br />
     *              3) Verifica che la dimensione sia aumentata di 3.<br />
     *              4) Verifica l'ordine degli elementi.<br />
     *
     * @pre La lista è vuota.
     * @post La lista contiene tre elementi.
     * @expected {@code size()} restituisce 3 e gli elementi sono nell'ordine corretto.
     */
    @Test
    public void testAddMultipleElements() 
    {
        int sizeOriginale = list.size();

        // Aggiunge tre elementi alla lista
        list.add("A");
        list.add("B");
        list.add("C");
        assertEquals(sizeOriginale + 3, list.size());
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("C", list.get(2));
    }

    /**
     * Test del metodo {@link HList#add(Object)}.
     *
     * @summary Verifica che si possa aggiungere un elemento null alla lista.
     *
     * @description 1) Aggiunge un elemento null.<br /> 
     *              2) Verifica che sia stato inserito correttamente.<br />
     *
     * @pre La lista è vuota.
     * @post La lista contiene un elemento null.
     * @expected {@code size()} restituisce 1 e {@code get(0)} è null.
     */
    @Test
    public void testAddNullElement() 
    {
        boolean result = list.add(null);
        assertTrue(result);
        assertEquals(1, list.size());
        assertNull(list.get(0));
    }

    /**
     * Test del metodo {@link HList#add(Object)}.
     *
     * @summary Verifica che lo stesso elemento possa essere aggiunto più volte alla lista.
     *
     * @description 1) Aggiunge lo stesso elemento due volte.<br />
     *              2) Verifica che entrambi gli elementi siano presenti.<br />
     *              3) Verifica che gli elementi siano lo stesso elemento.<br />
     *
     * @pre La lista è vuota.
     * @post La lista contiene due elementi identici.
     * @expected {@code size()} restituisce 2 e gli elementi sono lo stesso elemento.
     */
    @Test 
    public void testAddSameElement() 
    {
        // Aggiunge lo stesso elemento due volte
        Object o = "A";
        list.add(o);
        list.add(o);
        assertSame(list.get(0), list.get(1));
    }

    /**
     * Test del metodo {@link HList#add(Object)}.
     *
     * @summary Verifica che lo stesso elemento possa essere aggiunto più volte alla lista.
     *
     * @description 1) Aggiunge lo stesso elemento due volte.<br />
     *              2) Verifica che entrambi gli elementi siano presenti.<br />
     *              3) Verifica che la dimensione sia aumentata di 2.<br />
     *
     * @pre La lista è vuota.
     * @post La lista contiene due elementi identici.
     * @expected {@code size()} restituisce 2 e gli elementi sono identici.
     */
    public void testAddDuplicateElement() 
    {
        list.add("A");
        list.add("A");
        assertEquals("A", list.get(0));
        assertEquals("A", list.get(1));
    }

    /**
     * Test del metodo {@link HList#add(Object)}.
     *
     * @summary Verifica che si possano aggiungere elementi di tipo Object alla lista, ma di istanze diverse.
     *
     * @description 1) Aggiunge due elementi di tipo Object ma di istanze diverse.<br />
     *              2) Verifica che siano stati inseriti correttamente.<br />
     *              3) Verifica che la dimensione sia aumentata di 2.<br />
     *
     * @pre La lista è vuota.
     * @post La lista contiene due elementi di tipo Object.
     * @expected {@code size()} restituisce 2 e gli elementi sono di tipo Object ma di istanze diverse.
     */
    @Test
    public void testAddObjectElements() 
    {
        // Aggiunge due elementi di tipo Object ma di istanze diverse
        list.add("A");      // String
        list.add(42);       // Integer

        // Verifica che gli elementi siano di istanze diverse
        assertTrue(list.get(0) instanceof String);
        assertTrue(list.get(1) instanceof Integer);
    }

    //------- TEST DEL METODO add(index, Object) ----------

    /**
     * Test del metodo {@link HList#add(int, Object)}.
     * @summary Verifica l'inserimento di un elemento in una posizione non valida.
     * @description 1) Prova ad aggiungere un elemento in una posizione negativa.<br />
     *              2) Prova ad aggiungere un elemento in una posizione oltre la dimensione della lista.
     * 
     * @pre La lista è vuota.
     * @post La lista non cambia.
     * @expected {@code add(int, Object)} deve lanciare IndexOutOfBoundsException per posizioni non valide.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddNegativeIndex() 
    {
        // Prova ad aggiungere un elemento in una posizione negativa
        list.add(-1, "A");
    }

    /**
     * Test del metodo {@link HList#add(int, Object)}.
     * @summary Verifica l'inserimento di un elemento in una posizione valida.
     * @description 1) Aggiunge un elemento in una posizione non valida (0).<br />
     * 
     * @pre La lista è vuota.
     * @post La lista non cambia.
     * @expected {@code add(int, Object)} deve lanciare IndexOutOfBoundsException per posizioni non valide.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddGreaterIndex() 
    {
        // Aggiunge un elemento in una posizione valida (0)
        list.add(5, "A");
    }

    //------- TEST DEL METODO size() ----------

    /**
     * Test del metodo {@link myAdapter.ListAdapter#size() size}.
     * 
     * @summary Verifica che il metodo {@code size()} restituisca correttamente la dimensione della lista.
     * 
     * @design Verifica che {@code size()} si appoggi correttamente al metodo size del Vector sottostante.
     * 
     * @description 1. Controlla che la dimensione della lista corrisponda al valore atteso dopo il popolamento.<br />
     *              2. Rimuove un elemento e verifica che la dimensione diminuisca di 1.<br />
     *              3. Svuota la lista e verifica che la dimensione diventi 0.
     * 
     * @precondition La lista è stata popolata con 4 elementi.
     * 
     * @postcondition La lista è stata svuotata.
     * 
     * @result {@code size()} deve restituire il numero di elementi nella lista.
     */
    @Test
    public void test_Lista_Size() {
        // Verifica la dimensione iniziale
        assertEquals(4, listaTest.size());
        
        // Rimuove un elemento e verifica la dimensione
        listaTest.remove(0);
        assertEquals(3, listaTest.size());
        
        // Svuota la lista e verifica la dimensione
        listaTest.clear();
        assertEquals(0, listaTest.size());
    }

    //------- TEST DEL METODO isEmpty() ----------

    /**
     * Test del metodo {@link myAdapter.ListAdapter#isEmpty() isEmpty}.
     * 
     * @summary Verifica che il metodo {@code isEmpty()} restituisca true quando la lista è vuota, false altrimenti.
     * 
     * @design Verifica che {@code isEmpty()} si appoggi correttamente al metodo isEmpty del Vector sottostante.
     * 
     * @description 1. Controlla che la lista non sia vuota all'inizio.<br />
     *              2. Svuota la lista e verifica che sia vuota.<br />
     *              3. Verifica che una lista appena creata sia vuota.
     * 
     * @precondition La lista è stata popolata con 4 elementi.
     * 
     * @postcondition La lista è stata svuotata.
     * 
     * @result {@code isEmpty()} deve restituire true se la lista è vuota, false altrimenti.
     */
    @Test
    public void test_Lista_IsEmpty() {
        // Verifica che la lista non sia vuota
        assertFalse(listaTest.isEmpty());
        
        // Svuota la lista e verifica che sia vuota
        listaTest.clear();
        assertTrue(listaTest.isEmpty());
        
        // Verifica che una lista nuova sia vuota
        assertTrue(list.isEmpty());
    }

    //------- TEST DEL METODO contains() ----------

    /**
     * Test del metodo {@link myAdapter.ListAdapter#contains(Object) contains}.
     * 
     * @summary Verifica che il metodo {@code contains()} restituisca true se l'elemento è presente, false altrimenti.
     * 
     * @design Verifica che {@code contains()} si appoggi correttamente al metodo contains del Vector sottostante.
     * 
     * @description 1. Verifica che un elemento presente sia trovato.<br />
     *              2. Verifica che un elemento non presente non sia trovato.<br />
     *              3. Verifica che null sia gestito correttamente.
     * 
     * @precondition La lista è stata popolata con 4 elementi.
     * 
     * @postcondition La lista rimane invariata.
     * 
     * @result {@code contains()} deve restituire true se l'elemento è presente, false altrimenti.
     */
    @Test
    public void test_Lista_Contains() {
        // Verifica elemento presente
        assertTrue(listaTest.contains("due"));
        
        // Verifica elemento non presente
        assertFalse(listaTest.contains("cinque"));
        
        // Verifica con null
        listaTest.add(null);
        assertTrue(listaTest.contains(null));
    }

    //------- TEST DEL METODO toArray() ----------

    /**
     * Test del metodo {@link myAdapter.ListAdapter#toArray() toArray}.
     * 
     * @summary Verifica che il metodo {@code toArray()} restituisca un array contenente tutti gli elementi della lista.
     * 
     * @design Verifica che l'array restituito contenga tutti gli elementi nell'ordine corretto.
     * 
     * @description 1. Verifica che l'array restituito abbia la stessa dimensione della lista.<br />
     *              2. Verifica che gli elementi nell'array corrispondano a quelli nella lista.<br />
     *              3. Verifica con una lista vuota.
     * 
     * @precondition La lista è stata popolata con 4 elementi.
     * 
     * @postcondition La lista rimane invariata.
     * 
     * @result {@code toArray()} deve restituire un array contenente tutti gli elementi della lista nell'ordine corretto.
     */
    @Test
    public void test_Lista_ToArray() {
        // Verifica con lista popolata
        Object[] array = listaTest.toArray();
        assertEquals(listaTest.size(), array.length);
        assertEquals("uno", array[0]);
        assertEquals("quattro", array[3]);
        
        // Verifica con lista vuota
        array = list.toArray();
        assertEquals(0, array.length);
    }

    //------- TEST DEL METODO remove(Object) ----------

    /**
     * Test del metodo {@link myAdapter.ListAdapter#remove(Object) remove}.
     * 
     * @summary Verifica che il metodo {@code remove()} rimuova correttamente un elemento dalla lista.
     * 
     * @design Verifica che l'elemento venga rimosso e che la dimensione diminuisca di 1.
     * 
     * @description 1. Rimuove un elemento presente e verifica che non sia più nella lista.<br />
     *              2. Verifica che la dimensione sia diminuita di 1.<br />
     *              3. Prova a rimuovere un elemento non presente.
     * 
     * @precondition La lista è stata popolata con 4 elementi.
     * 
     * @postcondition La lista ha un elemento in meno.
     * 
     * @result {@code remove()} deve rimuovere l'elemento se presente e restituire true, altrimenti false.
     */
    @Test
    public void test_Lista_Remove_Object() {
        int sizeOriginale = listaTest.size();
        
        // Rimuove un elemento presente
        assertTrue(listaTest.remove("due"));
        assertEquals(sizeOriginale - 1, listaTest.size());
        assertFalse(listaTest.contains("due"));
        
        // Prova a rimuovere un elemento non presente
        assertFalse(listaTest.remove("dieci"));
    }

    //------- TEST DEL METODO addAll() ----------

    /**
     * Test del metodo {@link myAdapter.ListAdapter#addAll(HCollection) addAll}.
     * 
     * @summary Verifica che il metodo {@code addAll()} aggiunga correttamente tutti gli elementi di una collezione.
     * 
     * @design Verifica che tutti gli elementi vengano aggiunti in fondo e che la dimensione aumenti di conseguenza.
     * 
     * @description 1. Crea una collezione con elementi da aggiungere.<br />
     *              2. Verifica che gli elementi siano stati aggiunti.<br />
     *              3. Verifica che la dimensione sia aumentata correttamente.
     * 
     * @precondition La lista è stata popolata con 4 elementi.
     * 
     * @postcondition La lista ha elementi aggiuntivi.
     * 
     * @result {@code addAll()} deve aggiungere tutti gli elementi della collezione e restituire true se la lista è cambiata.
     */
    @Test
    public void test_Lista_AddAll() {
        ListAdapter daAggiungere = new ListAdapter();
        daAggiungere.add("cinque");
        daAggiungere.add("sei");
        
        int sizeOriginale = listaTest.size();
        
        // Aggiunge gli elementi e verifica
        assertTrue(listaTest.addAll(daAggiungere));
        assertEquals(sizeOriginale + daAggiungere.size(), listaTest.size());
        assertTrue(listaTest.containsAll(daAggiungere));
    }

    //------- TEST DEL METODO clear() ----------

    /**
     * Test del metodo {@link myAdapter.ListAdapter#clear() clear}.
     * 
     * @summary Verifica che il metodo {@code clear()} svuoti correttamente la lista.
     * 
     * @design Verifica che la lista sia vuota dopo la chiamata a clear().
     * 
     * @description 1. Svuota la lista e verifica che sia vuota.<br />
     *              2. Verifica che la dimensione sia 0.<br />
     *              3. Verifica che non contenga più gli elementi originali.
     * 
     * @precondition La lista è stata popolata con 4 elementi.
     * 
     * @postcondition La lista è vuota.
     * 
     * @result {@code clear()} deve svuotare la lista.
     */
    @Test
    public void test_Lista_Clear() {
        // Svuota la lista e verifica
        listaTest.clear();
        assertTrue(listaTest.isEmpty());
        assertEquals(0, listaTest.size());
        assertFalse(listaTest.contains("uno"));
    }

    //------- TEST DEL METODO get() ----------

    /**
     * Test del metodo {@link myAdapter.ListAdapter#get(int) get}.
     * 
     * @summary Verifica che il metodo {@code get()} restituisca correttamente l'elemento all'indice specificato.
     * 
     * @design Verifica che l'elemento corretto venga restituito e che vengano lanciate eccezioni per indici non validi.
     * 
     * @description 1. Verifica che gli elementi siano restituiti correttamente.<br />
     *              2. Verifica che venga lanciata IndexOutOfBoundsException per indici non validi.
     * 
     * @precondition La lista è stata popolata con 4 elementi.
     * 
     * @postcondition La lista rimane invariata.
     * 
     * @result {@code get()} deve restituire l'elemento all'indice specificato.
     */
    @Test
    public void test_Lista_Get() {
        // Verifica elementi in posizioni valide
        assertEquals("uno", listaTest.get(0));
        assertEquals("quattro", listaTest.get(3));
        
        // Verifica eccezioni per indici non validi
        try {
            listaTest.get(-1);
            fail("Dovrebbe lanciare IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {}
        
        try {
            listaTest.get(listaTest.size());
            fail("Dovrebbe lanciare IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {}
    }

    //------- TEST DEL METODO set() ----------

    /**
     * Test del metodo {@link myAdapter.ListAdapter#set(int, Object) set}.
     * 
     * @summary Verifica che il metodo {@code set()} sostituisca correttamente l'elemento all'indice specificato.
     * 
     * @design Verifica che l'elemento venga sostituito e che venga restituito il vecchio elemento.
     * 
     * @description 1. Sostituisce un elemento e verifica il cambiamento.<br />
     *              2. Verifica che venga restituito il vecchio elemento.<br />
     *              3. Verifica eccezioni per indici non validi.
     * 
     * @precondition La lista è stata popolata con 4 elementi.
     * 
     * @postcondition La lista ha un elemento modificato.
     * 
     * @result {@code set()} deve sostituire l'elemento all'indice specificato e restituire il vecchio elemento.
     */
    @Test
    public void test_Lista_Set() {
        // Sostituisce un elemento e verifica
        Object vecchioElemento = listaTest.set(1, "due-modificato");
        assertEquals("due", vecchioElemento);
        assertEquals("due-modificato", listaTest.get(1));
        
        // Verifica eccezioni per indici non validi
        try {
            listaTest.set(-1, "errore");
            fail("Dovrebbe lanciare IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {}
    }

    //------- TEST DEL METODO add(int, Object) ----------

    /**
     * Test del metodo {@link myAdapter.ListAdapter#add(int, Object) add}.
     * 
     * @summary Verifica che il metodo {@code add()} inserisca correttamente un elemento alla posizione specificata.
     * 
     * @design Verifica che l'elemento venga inserito nella posizione corretta e che gli altri elementi vengano spostati.
     * 
     * @description 1. Inserisce un elemento in una posizione valida e verifica.<br />
     *              2. Verifica che la dimensione sia aumentata di 1.<br />
     *              3. Verifica eccezioni per indici non validi.
     * 
     * @precondition La lista è stata popolata con 4 elementi.
     * 
     * @postcondition La lista ha un elemento in più.
     * 
     * @result {@code add()} deve inserire l'elemento alla posizione specificata.
     */
    @Test
    public void test_Lista_Add_Index() {
        int sizeOriginale = listaTest.size();
        
        // Inserisce un elemento e verifica
        listaTest.add(1, "uno-e-mezzo");
        assertEquals(sizeOriginale + 1, listaTest.size());
        assertEquals("uno-e-mezzo", listaTest.get(1));
        assertEquals("due", listaTest.get(2));
        
        // Verifica eccezioni per indici non validi
        try {
            listaTest.add(-1, "errore");
            fail("Dovrebbe lanciare IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {}
    }

    //------- TEST DEL METODO remove(int) ----------

    /**
     * Test del metodo {@link myAdapter.ListAdapter#remove(int) remove}.
     * 
     * @summary Verifica che il metodo {@code remove()} rimuova correttamente l'elemento alla posizione specificata.
     * 
     * @design Verifica che l'elemento venga rimosso, che la dimensione diminuisca di 1 e che venga restituito l'elemento rimosso.
     * 
     * @description 1. Rimuove un elemento da una posizione valida e verifica.<br />
     *              2. Verifica che la dimensione sia diminuita di 1.<br />
     *              3. Verifica che venga restituito l'elemento corretto.<br />
     *              4. Verifica eccezioni per indici non validi.
     * 
     * @precondition La lista è stata popolata con 4 elementi.
     * 
     * @postcondition La lista ha un elemento in meno.
     * 
     * @result {@code remove()} deve rimuovere l'elemento alla posizione specificata e restituirlo.
     */
    @Test
    public void test_Lista_Remove_Index() {
        int sizeOriginale = listaTest.size();
        
        // Rimuove un elemento e verifica
        Object rimosso = listaTest.remove(1);
        assertEquals("due", rimosso);
        assertEquals(sizeOriginale - 1, listaTest.size());
        assertFalse(listaTest.contains("due"));
        
        // Verifica eccezioni per indici non validi
        try {
            listaTest.remove(-1);
            fail("Dovrebbe lanciare IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {}
    }

    //------- TEST DEL METODO indexOf() ----------

    /**
     * Test del metodo {@link myAdapter.ListAdapter#indexOf(Object) indexOf}.
     * 
     * @summary Verifica che il metodo {@code indexOf()} restituisca correttamente l'indice della prima occorrenza di un elemento.
     * 
     * @design Verifica che venga restituito l'indice corretto o -1 se l'elemento non è presente.
     * 
     * @description 1. Verifica che venga trovato l'indice di un elemento presente.<br />
     *              2. Verifica che venga restituito -1 per un elemento non presente.<br />
     *              3. Verifica il comportamento con elementi duplicati.
     * 
     * @precondition La lista è stata popolata con 4 elementi.
     * 
     * @postcondition La lista rimane invariata.
     * 
     * @result {@code indexOf()} deve restituire l'indice della prima occorrenza dell'elemento o -1 se non presente.
     */
    @Test
    public void test_Lista_IndexOf() {
        // Verifica elemento presente
        assertEquals(1, listaTest.indexOf("due"));
        
        // Verifica elemento non presente
        assertEquals(-1, listaTest.indexOf("cinque"));
        
        // Verifica con elementi duplicati
        listaTest.add("due");
        assertEquals(1, listaTest.indexOf("due"));
    }

    //------- TEST DEL METODO iterator() ----------

    /**
     * Test del metodo {@link myAdapter.ListAdapter#iterator() iterator}.
     * 
     * @summary Verifica che il metodo {@code iterator()} restituisca un iteratore funzionante.
     * 
     * @design Verifica che l'iteratore possa attraversare correttamente la lista.
     * 
     * @description 1. Verifica che l'iteratore restituisca tutti gli elementi nell'ordine corretto.<br />
     *              2. Verifica che l'iteratore supporti la rimozione di elementi.
     * 
     * @precondition La lista è stata popolata con 4 elementi.
     * 
     * @postcondition La lista potrebbe avere elementi rimossi.
     * 
     * @result {@code iterator()} deve restituire un iteratore che permetta di attraversare la lista.
     */
    @Test
    public void test_Lista_Iterator() {
        HIterator iteratore = listaTest.iterator();
        
        // Verifica che l'iteratore restituisca gli elementi nell'ordine corretto
        assertTrue(iteratore.hasNext());
        assertEquals("uno", iteratore.next());
        assertEquals("due", iteratore.next());
        
        // Verifica la rimozione tramite iteratore
        iteratore.remove();
        assertFalse(listaTest.contains("due"));
        assertEquals(3, listaTest.size());
    }

    //------- TEST DEL METODO listIterator() ----------

    /**
     * Test del metodo {@link myAdapter.ListAdapter#listIterator() listIterator}.
     * 
     * @summary Verifica che il metodo {@code listIterator()} restituisca un listIterator funzionante.
     * 
     * @design Verifica che il listIterator possa attraversare correttamente la lista in entrambe le direzioni.
     * 
     * @description 1. Verifica che il listIterator restituisca tutti gli elementi nell'ordine corretto.<br />
     *              2. Verifica che supporti l'aggiunta, la modifica e la rimozione di elementi.
     * 
     * @precondition La lista è stata popolata con 4 elementi.
     * 
     * @postcondition La lista potrebbe essere modificata.
     * 
     * @result {@code listIterator()} deve restituire un listIterator che permetta di attraversare e modificare la lista.
     */
    @Test
    public void test_Lista_ListIterator() {
        HListIterator listIteratore = listaTest.listIterator();
        
        // Verifica attraversamento in avanti
        assertTrue(listIteratore.hasNext());
        assertEquals("uno", listIteratore.next());
        
        // Verifica modifica elemento
        listIteratore.set("uno-modificato");
        assertEquals("uno-modificato", listaTest.get(0));
        
        // Verifica aggiunta elemento
        listIteratore.add("uno-e-mezzo");
        assertEquals(5, listaTest.size());
        assertEquals("uno-e-mezzo", listaTest.get(1));
        
        // Verifica attraversamento all'indietro
        assertTrue(listIteratore.hasPrevious());
        assertEquals("uno-e-mezzo", listIteratore.previous());
    }
    }

