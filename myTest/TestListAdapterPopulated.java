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
     * @result {@code add(int, Object)} deve inserire l'elemento all'inizio della lista.
     * @expected {@code size()} restituisce 1 e {@code get(0)} è "A".
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


}
