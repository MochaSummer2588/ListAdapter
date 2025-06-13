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
    @Test
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

    //------- FINE TEST DEL METODO add(Object) ----------

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
    //------- FINE TEST DEL METODO add(index, Object) ----------

    //------- TEST DEL METODO addAll(int, HCollection) ----------
    /**
     * Test del metodo {@link HList#addAll(int, HCollection)}.
     * @summary Verifica l'inserimento di una collezione vuota in una lista vuota.
     * @description 1) Aggiunge una collezione vuota alla lista.<br />
     *              2) Verifica che la dimensione della lista non cambi.
     * 
     * @pre La lista è vuota, la collezione e' vuota.
     * @post La lista rimane vuota.
     * @expected {@code addAll(int, HCollection)} non deve modificare la lista e deve restituire false.
     */
    @Test
    public void testAddAllEmptyCollection() 
    {
        HCollection emptyCollection = new ListAdapter(); // Collezione vuota
        boolean result = list.addAll(0, emptyCollection);
        
        // Verifica che la lista non sia cambiata
        assertFalse(result);
        assertEquals(0, list.size());
    }

    /**
     * Test del metodo {@link HList#addAll(int, HCollection)}.
     * @summary Verifica l'inserimento di una collezione non vuota in una lista vuota.
     * @description 1) Aggiunge una collezione con un elemento alla lista.<br />
     *              2) Verifica che la dimensione della lista sia aumentata di 1.
     * 
     * @pre La lista è vuota, la collezione ha 1 elemento.
     * @post La lista contiene un elemento.
     * @expected {@code addAll(int, HCollection)} deve aggiungere l'elemento e restituire true.
     */
    @Test
    public void testAddAllNonEmptyCollection() 
    {
        int sizeOriginale = list.size();

        HCollection collection = new ListAdapter();
        collection.add("A"); // Collezione con un elemento
        boolean result = list.addAll(0, collection);
        
        // Verifica che la lista sia cambiata
        assertTrue(result);
        assertEquals(sizeOriginale + 1, list.size());
        assertEquals("A", list.get(0));
    }

    /**
     * Test del metodo {@link HList#addAll(int, HCollection)}.
     * @summary Verifica l'inserimento di una collezione con più elementi in una lista vuota.
     * @description 1) Aggiunge una collezione con tre elementi alla lista.<br />
     *              2) Verifica che la dimensione della lista sia aumentata di 3.
     * 
     * @pre La lista è vuota, la collezione ha 3 elementi.
     * @post La lista contiene tre elementi.
     * @expected {@code addAll(int, HCollection)} deve aggiungere gli elementi e restituire true.
     */
    @Test
    public void testAddAllMultipleElements() 
    {
        int sizeOriginale = list.size();

        HCollection collection = new ListAdapter();
        collection.add("A");
        collection.add("B");
        collection.add("C"); // Collezione con tre elementi
        boolean result = list.addAll(0, collection);
        
        // Verifica che la lista sia cambiata
        assertTrue(result);
        assertEquals(sizeOriginale + 3, list.size());
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("C", list.get(2));
    }

    /**
     * Test del metodo {@link HList#addAll(int, HCollection)}.
     * @summary Verifica l'inserimento di una collezione con elementi null in una lista vuota.
     * @description 1) Aggiunge una collezione con un elemento null alla lista.<br />
     *              2) Verifica che la dimensione della lista sia aumentata di 1.
     * 
     * @pre La lista è vuota, la collezione ha un elemento null.
     * @post La lista contiene un elemento null.
     * @expected {@code addAll(int, HCollection)} deve aggiungere l'elemento null e restituire true.
     */
    @Test
    public void testAddAllNullElement() 
    {
        int sizeOriginale = list.size();

        HCollection collection = new ListAdapter();
        collection.add(null); // Collezione con un elemento null
        boolean result = list.addAll(0, collection);
        
        // Verifica che la lista sia cambiata
        assertTrue(result);
        assertEquals(sizeOriginale + 1, list.size());
        assertNull(list.get(0));
    }

    /**
     * Test del metodo {@link HList#addAll(HCollection)}.
     * @summary Verifica l'inserimento di una collezione null in una lista vuota.
     * @description 1) Prova ad aggiungere una collezione null alla lista.<br />
     *              2) Verifica che venga lanciata NullPointerException.
     * @pre La lista è vuota, la collezione è null.
     * @post La lista rimane vuota.
     * @expected {@code addAll(int, HCollection)} deve lanciare NullPointerException se la collezione è null.
     */
    @Test(expected = NullPointerException.class)
    public void testAddAllNullCollection() 
    {
        // Prova ad aggiungere una collezione null alla lista
        list.addAll(null);
    }

    //------- FINE TEST DEL METODO addAll(int, HCollection) ----------

}