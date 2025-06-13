package myTest;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import myAdapter.*;
public class TestListAdapterCore 
{
    private HList list;

    @Before
    public void setUp() 
    {
        list = new ListAdapter();
    }

    //===== TESTS CORE =====

    // ===== public boolean add(Object o) ====

    // --- testAddOneElement() ---
    /*
     * Summary: Il test testa il metodo add() e get() a partire da una lista vuota.
     * Test Case Design: La motivazione del test è verificare che l'elemento venga aggiunto correttamente alla lista.
     * Test Description: Aggiunge un elemento "X" a una lista vuota e verifica che venga restituito correttamente.
     * Preconditions: La lista è vuota.
     * Postconditions: La lista contiene un solo elemento "X".
     * Expected Result: L'elemento "X" dovrebbe essere aggiunto alla lista e diventare il primo elemento, quindi posizionato nell'indice 0 della lista.
     */
    @Test
    public void testAddOneElement() 
    {
        assertTrue(list.add("X"));
        assertEquals("X", list.get(0));
    }

    // --- testAddMultipleElements() ---
    /*
     * Summary: Il test testa il metodo add() e get()m a partire da unma lista vuota.
     * Test Case Design: La motivazione del test è verificare che aggiungendo piu' elementi alla lista, questi vengano inseriti correttamente in ordine.
     * Test Description: Aggiunge tre elementi "A", "B" e "C" alla lista e verifica che vengano inseriti negli indici corretti.
     * Preconditions: La lista è vuota.
     * Postconditions: La lista contiene tre elementi "A", "B" e "C" in ordine.
     * Expected Result: Gli elementi "A", "B" e "C" dovrebbero essere aggiunti alla lista e posizionati rispettivamente negli indici 0, 1 e 2.
     */
    @Test
    public void testAddMultipleElements() 
    {
        list.add("A");
        list.add("B");
        list.add("C");
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("C", list.get(2));
    }

    @Test
    public void testSizeAfterAdds() 
    {
        list.add("A");
        list.add("B");
        assertEquals(2, list.size());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetInvalidIndex() 
    {
        list.get(1);
    }
}
