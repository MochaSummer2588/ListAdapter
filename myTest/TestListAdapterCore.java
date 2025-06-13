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

    // =============== public boolean add(Object o) ==============

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
     * <p><b>Summary:</b>Il test testa il metodo add(), size() e get() quando viene aggiunto un elemento null.<p>
     * <p><b>Test Case Design:<b>La motivazione del test e' verificare che quando viene aggiunto un elemento null, esso si trova nella lista in posizione 0.<p>
     * <p><b>Test Description:<b>Inizialmente si guarda se l'elemento null viene aggiunto correttamente alla lista, 
     * quindi si verifica che la dimensione della lista sia 1 e che l'elemento null sia presente all'indice 0 della lista.<p>
     * <p><b>Preconditions:<b> La lista è vuota.<p>
     * <p><b>Postconditions:<b> La lista contiene un solo elemento null all'indice 0 e la dimensione della lista e' 1.<p>
     * <p><b>Expected Result:<b> L'elemento null dovrebbe essere aggiunto alla lista e diventare il primo elemento, quindi posizionato nell'indice 0 della lista.<p>
     */
    @Test
    public void testAddNullElement() 
    {
        boolean result = list.add(null);
        assertTrue(result);
        assertEquals(1, list.size());
        assertNull(list.get(0));
    }

    // --- testAddDuplicateElement() ---
    /*
     * <p><b>Summary:</b> Il test testa il metodo add(), size() e get() quando viene aggiunto un elemento duplicato.</p>
     * <p><b>Test Case Design:</b> La motivazione del test è verificare che la lista possa contenere elementi duplicati e che la dimensione della lista
     * sia corretta dopo l'aggiunta di un duplicato.<p>
     * <p><b>Test Description:</b> Aggiunge l'elemento "A" due volte alla lista, quindi verifica che la dimensione della lista sia 2 e che entrambi gli elementi siano
     * lo stesso oggetto e che siano presenti negli indici 0 e 1.<p>
     * <p><b>Preconditions:</b> La lista è vuota.</p>
     * <p><b>Postconditions:</b> La lista contiene due elementi "A" in questo ordine.</p>
     * <p><b>Expected Result:</b> Gli elementi "A" dovrebbero essere aggiunti alla lista e posizionati rispettivamente negli indici 0 e 1.
     * La dimensione della lista dovrebbe essere 2.<p>
     */
    @Test 
    public void testAddDuplicateElement() 
    {
        Object o = "A";
        list.add(o);
        list.add(o);
        assertEquals(2, list.size());
        assertSame(list.get(0), list.get(1));
        assertEquals(o, list.get(0));
        assertEquals(o, list.get(1));
    }

    // --- testAddObjectElements() ---
    /*
     * <p><b>Summary:</b> Il test testa il metodo add() e get() quando vengono aggiunti diversi elementi discendenti da Object.</p>
     * <p><b>Test Case Design:</b> La motivazione del test è verificare che la lista possa contenere oggetti che discendono da Object (ma sono di tipi diversi)
     * e che la dimensione della lista sia corretta dopo l'aggiunta di questi oggetti.</p>
     * <p><b>Test Description:</b> Aggiunge una String e un Integer alla lista, quindi verifica che la dimensione della lista sia 2.</p>
     * <p><b>Preconditions:</b> La lista è vuota.</p>
     * <p><b>Postconditions:</b> La lista contiene due elementi: una String e un Integer.</p>
     * <p><b>Expected Result:</b> La lista dovrebbe contenere due elementi di tipi diversi (String alla posizione 0 e Integer alla posizione 1) e la dimensione della lista dovrebbe essere 2.</p>
     */
    @Test
    public void testAddObjectElements() 
    {
        list.add("A");      // String
        list.add(42);       // Integer

        assertEquals(2, list.size());
        assertTrue(list.get(0) instanceof String);
        assertTrue(list.get(1) instanceof Integer);
    }

    // =============== public int add(int index, Object element) ==============

    // --- testAddAtBeginning() ---
    /*
     * <p><b>Summary:</b> Il test testa il metodo add(int index, Object element) quando viene aggiunto un elemento all'inizio della lista.</p>
     * <p><b>Test Case Design:</b> La motivazione del test è verificare che l'elemento venga inserito correttamente all'inizio della lista e che la dimensione sia aggiornata.</p>
     * <p><b>Test Description:</b> Aggiunge l'elemento "A" all'indice 0, poi aggiunge "B" all'indice 1, e infine aggiunge "C" all'indice 0 (che sposta "A" a destra).
     * Quindi verifica che la dimensione della lista sia 3 e che gli elementi siano presenti negli indici corretti.</p>
     * <p><b>Preconditions:</b> La lista è inizialmente vuota (creata nel metodo @Before).</p>
     * <p><b>Postconditions:</b> La lista contiene tre elementi: "C" all'indice 0, "A" all'indice 1 e "B" all'indice 2.</p>
     * <p><b>Expected Result:</b> L'elemento "C" dovrebbe essere aggiunto alla lista all'indice 0, spostando "A" e "B" a destra, e la dimensione della lista dovrebbe essere 3.</p>
     */
    @Test
    public void testAddAtBeginning() 
    {
        list.add("A");      // Aggiunge "A" all'indice 0
        list.add("B");      // Aggiunge "B" all'indice 1
        list.add(0, "C");   // Aggiunge "C" all'indice 0
        assertEquals(3, list.size());
        assertEquals("C", list.get(0));
        assertEquals("A", list.get(1));
        assertEquals("B", list.get(2));
    }

    // --- testAddAtMiddle() ---
    /*
     * <p><b>Summary:</b> Il test testa il metodo add(int index, Object element) quando viene aggiunto un elemento nel mezzo alla lista (non all'inizioe e nemmeno alla fine).</p>
     * <p><b>Test Case Design:</b> La motivazione del test è verificare che l'elemento venga inserito correttamente nel mezzo alla lista e che gli altri elementi siano spostati.</p>
     * <p><b>Test Description:</b> Aggiunge l'elemento "A" all'indice 0, poi aggiunge "B" all'indice 1, e infine aggiunge "C" all'indice 1 (che sposta "B" a destra).
     * Quindi verifica che la dimensione della lista sia 3 e che gli elementi siano presenti negli indici corretti.</p>
     * <p><b>Preconditions:</b> La lista  è inizialmente vuota (creata nel metodo @Before), poi viene aggiunto un elemento "A" all'indice 0 e un elemento "B" all'indice 1.</p>
     * <p><b>Postconditions:</b> La lista contiene tre elementi: "A" all'indice 0, "C" all'indice 1 e "B" all'indice 2.</p>
     * <p><b>Expected Result:</b> L'elemento "C" dovrebbe essere aggiunto alla lista all'indice 1, spostando "B" a destra, e la dimensione della lista dovrebbe essere 3.</p>
     */
    @Test
    public void testAddAtMiddle() 
    {
        list.add("A");      // Aggiunge "A" all'indice 0
        list.add("B");   // Aggiunge "B" all'indice 1
        list.add(1, "C");   // Aggiunge "B" all'indice 1
        assertEquals(3, list.size());
        assertEquals("A", list.get(0));
        assertEquals("C", list.get(1));
        assertEquals("B", list.get(2));
    }

    // --- testAddAtEnd() ---
    /*
     * <p><b>Summary:</b> Il test testa il metodo add(int index, Object element) quando viene aggiunto un elemento alla fine della lista.</p>
     * <p><b>Test Case Design:</b> La motivazione del test è verificare che l'elemento venga inserito correttamente alla fine della lista e che la dimensione sia aggiornata.</p>
     * <p><b>Test Description:</b> Aggiunge l'elemento "A" all'indice 0, poi aggiunge "B" all'indice 1, e infine aggiunge "C" all'indice 2 (che è la fine della lista).
     * Quindi verifica che la dimensione della lista sia 3 e che gli elementi siano presenti negli indici corretti.</p>
     * <p><b>Preconditions:</b> La lista è inizialmente vuota (creata nel metodo @Before), poi viene aggiunto un elemento "A" all'indice 0 e un elemento "B" all'indice 1.</p>
     * <p><b>Postconditions:</b> La lista contiene tre elementi: "A" all'indice 0, "B" all'indice 1 e "C" all'indice 2.</p>
     * <p><b>Expected Result:</b> L'elemento "C" dovrebbe essere aggiunto alla lista alla fine, e la dimensione della lista dovrebbe essere 3.</p>
     */
    @Test 
    public void testAddAtEnd() 
    {
        list.add("A");                          // Aggiunge "A" all'indice 0
        list.add("B");                          // Aggiunge "B" all'indice 1
        list.add(list.size(), "C");     // Aggiunge "C" all'indice 2 (fine della lista)
        assertEquals(3, list.size());
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("C", list.get(2));
    }

    // --- testAddAtInvalidIndex() ---
    /*
     * <p><b>Summary:</b> Il test testa il metodo add(int index, Object element) quando viene passato un indice non valido.</p>
     * <p><b>Test Case Design:</b> La motivazione del test è verificare che venga lanciata un'eccezione IndexOutOfBoundsException quando si prova ad aggiungere un elemento a un indice non valido.</p>
     * <p><b>Test Description:</b> Prova ad aggiungere un elemento "A" all'indice -1 e verifica che venga lanciata l'eccezione IndexOutOfBoundsException.</p>
     * <p><b>Preconditions:</b> La lista è inizialmente vuota (creata nel metodo @Before).</p>
     * <p><b>Postconditions:</b> Nessun cambiamento nella lista, poiché l'operazione di add fallisce.</p>
     * <p><b>Expected Result:</b> Dovrebbe essere lanciata un'eccezione IndexOutOfBoundsException.</p>
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAtInvalidIndex() 
    {
        list.add(-1, "A");  // Prova ad aggiungere "A" all'indice -1
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
