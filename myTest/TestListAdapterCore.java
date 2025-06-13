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
     * <p><b>Summary:</b> Il test testa il metodo add(), size() e get() quando viene aggiunto un unico elemento a partire da una lista vuota.</p>
     * <p><b>Test Case Design:</b> La motivazione del test è verificare che aggiungendo un singolo elemento alla lista, questo venga inserito 
     * nella posizione corretta e che la lista contenga 1 elemento.<p>
     * <p><b>Test Description:</b> salvo il risultato di list.add("X") che dovrebbe restituire true, quindi verifico che la dimensione della 
     * lista sia 1 e che l'elemento "X" sia presente all'indice 0 della lista.<p>
     * <p><b>Preconditions:</b> La lista è inizialmente vuota.</p>
     * <p><b>Postconditions:</b> La lista contiene un solo elemento "X" all'indice 0 e la dimensione della lista e' 1.<p>
     * <p><b>Expected Result:</b> L'elemento "X" dovrebbe essere aggiunto alla lista e diventare il primo elemento, quindi posizionato nell'indice 0 della lista.<p>
     */
    @Test
    public void testAddOneElement() 
    {
        boolean result = list.add("X");
        assertTrue(result);
        assertEquals(1, list.size());
        assertEquals("X", list.get(0));
    }

    // --- testAddMultipleElements() ---
    /*
     * <p><b>Summary:</b> Il test testa il metodo add(), size() e get() quando viene aggiunto piu' di un elemento a partire da una lista vuota .<p>
     * <p><b>Test Case Design:<b> La motivazione del test è verificare che aggiungendo piu' elementi alla lista, questi vengano inseriti correttamente in ordine
     * di inserimento e che la dimensione della lista sia uguale al numero di elementi inseriti.<p>
     * <p><b>Test Description:<b> Aggiunge tre elementi "A", "B" e "C" alla lista, verifica che la dimensione della lista sia corretta e 
     * che gli elementi "B" e "C" si trovino negli indici 1 e 2 rispettivamente.<p>
     * <p><b>Preconditions:<b> La lista è vuota.<p>
     * <p><b>Postconditions:<b> La lista contiene tre elementi "A", "B" e "C" in questo ordine.<p>
     * <p><b>Expected Result:<b> Gli elementi "A", "B" e "C" dovrebbero essere aggiunti alla lista e posizionati rispettivamente negli indici 0, 1 e 2.
     * La dimensione della lista dovrebbe essere 3.<p>
     */
    @Test
    public void testAddMultipleElements() 
    {
        list.add("A");
        list.add("B");
        list.add("C");
        assertEquals(3, list.size());
        assertEquals("B", list.get(1));
        assertEquals("C", list.get(2));
    }

    // --- testAddNullElement() ---
    /*
     * Summary: Il test verifica il comportamento del metodo add() quando si tenta di aggiungere un elemento null.
     * Test Case Design: La motivazione del test è verificare che l'implementazione gestisca correttamente l'aggiunta di un elemento null dato che sono 
     * ammessi nell'implementazione di ntale lista.
     * Test Description: Tenta di aggiungere un elemento null alla lista e verifica che la lista contenga l'elemento null.
     * Preconditions: La lista è vuota.
     * Postconditions: La lista contiene 1 elemento, che è null.
     * Expected Result: L'elemento null dovrebbe essere aggiunto alla lista senza eccezioni e diventare il primo elemento, quindi posizionato nell'indice 0 della lista.
     */
    @Test
    public void testAddNullElement() 
    {
        boolean result = list.add(null);
        assertTrue(result);
        assertEquals(1, list.size());
        assertNull(list.get(0));
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
