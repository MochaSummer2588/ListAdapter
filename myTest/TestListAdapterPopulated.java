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

public class TestListAdapterPopulated 
{

    private HList list;

    @Before
    public void setUp() 
    {
        // Inizializza la lista con 4 elementi
        list = new ListAdapter();
        list.add("uno");
        list.add("due");
        list.add("tre");
        list.add("quattro");
    }

    /*
     *
     * Test del metodo {@link myAdapter.ListAdapter#get(int) get}.
     * 
     * @summary Verifica che il metodo {@code get()} restituisca l'elemento corretto.
     * 
     * @design Verifica che l'elemento alla posizione specificata sia quello atteso.
     * 
     * @description 1. Verifica che gli elementi siano corretti per gli indici 0, 1, 2 e 3.<br />
     *              2. Verifica che l'eccezione venga lanciata per un indice fuori dai limiti.
     * 
     * @precondition La lista è stata popolata con 4 elementi.
     * 
     * @postcondition La lista non cambia.
     * 
     * @result {@code get()} deve restituire l'elemento corretto o lanciare un'eccezione per indici non validi.
     */
    /** @Test
        public void testListGet() 
        {
            // Verifica gli elementi per indici validi
            assertEquals("uno", list.get(0));
            assertEquals("due", list.get(1));
            assertEquals("tre", list.get(2));
            assertEquals("quattro", list.get(3));
            
            // Verifica l'eccezione per indice fuori dai limiti
            try 
            {
                list.get(4);
                fail("Dovrebbe lanciare IndexOutOfBoundsException");
            } 
            catch (IndexOutOfBoundsException e) 
            {
                // Eccezione attesa
            }
        }
     */

    //------- TEST DEL METODO add(Object) ----------

    /**
     * Test del metodo {@link myAdapter.ListAdapter#add(Object) add}.
     * 
     * @summary Verifica che il metodo {@code add()} aggiunga correttamente un elemento alla lista.
     * 
     * @design Verifica che l'elemento venga aggiunto in fondo alla lista e che la dimensione aumenti di 1.
     * 
     * @description 1. Aggiunge un elemento e verifica che sia presente.<br />
     *              2. Verifica che la dimensione sia aumentata di 1.<br />
     *              3. Verifica che l'elemento sia stato aggiunto in fondo.
     * 
     * @precondition La lista è stata popolata con 4 elementi.
     * 
     * @postcondition La lista ha un elemento in più.
     * 
     * @result {@code add()} deve aggiungere l'elemento in fondo alla lista e restituire true.
     */
    @Test
    public void testListAdd() 
    {
        int sizeOriginale = list.size();
        
        // Aggiunge un elemento e verifica
        assertTrue(list.add("cinque"));
        assertEquals(sizeOriginale + 1, list.size());
        assertEquals("cinque", list.get(list.size() - 1));
    }

    //------- TEST DEL METODO add(int, Object) ----------

    /**
     * Test del metodo {@link HList#add(int, Object)}.
     *
     * @summary Verifica l’inserimento di un elemento all'inizio della lista.
     *
     *  @description 1. Aggiunge un elemento all'inizio della lista.<br />
     *               2. Verifica che la dimensione sia aumentata di 1.<br />
     *               3. Verifica che l'elemento sia stato aggiunto in fondo.
     * @pre La lista è stata popolata con 4 elementi.
     * @post La lista ha un elemento in più all'inizio.
     * @expected {@code add(int, Object)} deve inserire l'elemento "A" all'inizio della lista e aumentare la dimensione di 1.
     */
    @Test
    public void testAddAtBeginning() 
    {   
        int sizeOriginale = list.size();

        Object elemento = "A";
        // Aggiunge "A" all'inizio della lista
        list.add(0, elemento);
        // Verifica che l'elemento sia stato aggiunto correttamente
        assertEquals(sizeOriginale + 5, list.size());
        assertEquals(elemento, list.get(0));
    }

    /**
     * Test del metodo {@link HList#add(int, Object)}.
     * @summary Verifica l’inserimento di un elemento nel mezzo della lista.
     * @description 1. Aggiunge un elemento tra due elementi esistenti.<br />
     *              2. Verifica che la dimensione sia aumentata di 1.<br />
     *              3. Verifica che l'elemento sia stato inserito correttamente.
     * 
     * @pre La lista è stata popolata con 4 elementi.
     * @post La lista ha un elemento in più nel mezzo.
     * @expected {@code add(int, Object)} deve inserire l'elemento "A" tra "due" e "tre" e aumentare la dimensione di 1.
     */
    @Test
    public void testAddAtMiddle() 
    {
        int sizeOriginale = list.size();
        Object elemento = "A";
        // Aggiunge "A" all'indice 2 (tra "due" e "tre")
        list.add(2, elemento);

        // Verifica che la dimensione sia aumentata di 1
        assertEquals(sizeOriginale + 1, list.size());

        // Verifica che l'elemento "A" sia stato inserito correttamente
        assertEquals("uno", list.get(0));
        assertEquals(elemento, list.get(2));

        // Verifica che gli altri elementi siano stati spostati correttamente
        assertEquals("tre", list.get(3));
        assertEquals("quattro", list.get(4));
    }

    /**
     * Test del metodo {@link HList#add(int, Object)}.
     * @summary Verifica l’inserimento di un elemento alla fine della lista.
     * @description 1. Aggiunge un elemento alla fine della lista.<br />
     *              2. Verifica che la dimensione sia aumentata di 1.<br />
     *              3. Verifica che l'elemento sia stato aggiunto in fondo.
     * 
     * @pre La lista è stata popolata con 4 elementi.
     * @post La lista ha un elemento in più alla fine.
     * @expected {@code add(int, Object)} deve inserire l'elemento "A" alla fine della lista e aumentare la dimensione di 1.
     */
    @Test
    public void testAddAtEnd() 
    {
        int sizeOriginale = list.size();
        Object elemento = "A";
        // Aggiunge "A" alla fine della lista
        list.add(sizeOriginale, elemento);

        // Verifica che la dimensione sia aumentata di 1
        assertEquals(sizeOriginale + 1, list.size());

        // Verifica che l'elemento sia stato aggiunto in fondo
        assertEquals(elemento, list.get(list.size() - 1));
    }

    /**
     * Test del metodo {@link HList#add(int, Object)}.
     * @summary Verifica l'inserimento di un elemento in una posizione non valida.
     * @description 1. Prova ad aggiungere un elemento in una posizione negativa.<br />
     *              2. Prova ad aggiungere un elemento in una posizione oltre la dimensione della lista.
     * 
     * @pre La lista è stata popolata con 4 elementi.
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
        list.add(list.size() + 1, "A");
    }

}
