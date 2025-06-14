package myTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore; // Importa l'annotazione Ignore se necessario
import myAdapter.*;
import myExceptions.IllegalStateException; // Assicurati di avere questa classe, se Iterator.remove() la lancia

import java.util.Vector; // Necessario per testare l'interazione con Vector se usi i toArray con array CLDC

/**
 * Suite di test per la classe {@link myAdapter.ListAdapter}.
 * <p>
 * Riassunto: Verifica il corretto funzionamento dei metodi della classe {@code ListAdapter} che implementa l'interfaccia {@link myAdapter.HList}.
 * <p>
 * Design: Utilizza JUnit 4.13.2<br />
 * La suite include test per metodi di accesso, modifica e interrogazione di una {@link myAdapter.HList}<br />
 * attraverso la classe {@link myAdapter.ListAdapter}, con un'istanza ripopolata prima di ogni test case.<br />
 * Si suppone che la classe {@link myAdapter.ListIterator} funzioni correttamente e sarà testata nella sua propria suite.
 */

public class TestListAdapterPopulated
{

    private HList list; // Utilizza HList per mantenere l'astrazione

    /**
     * Configura l'ambiente di test popolando la lista che verrà manipolata.
     */
    @Before
    public void setUp()
    {
        // Inizializza la lista con 4 elementi
        list = new ListAdapter();
        list.add("uno");    // Index 0
        list.add("due");    // Index 1
        list.add("tre");    // Index 2
        list.add("quattro"); // Index 3
    }
    
    //------- TEST DEL METODO size() ----------

    /**
     * Test del metodo {@link HList#size()}.
     * <p>
     * Summary: Verifica che la dimensione di una lista popolata sia corretta.
     * <p>
     * Test Case Design: Questo test verifica il valore iniziale di {@code size()} per una lista pre-popolata.
     * <p>
     * Test Description: 1. Verifica la dimensione della lista dopo la sua inizializzazione nel setup.
     * <p>
     * Preconditions: La lista è stata inizializzata con 4 elementi.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code size()} deve restituire 4.
     */
    @Test
    public void testSizePopulatedList()
    {
        assertEquals(4, list.size());
    }

    //------- TEST DEL METODO isEmpty() ----------

    /**
     * Test del metodo {@link HList#isEmpty()}.
     * <p>
     * Summary: Verifica che {@code isEmpty()} restituisca false per una lista popolata.
     * <p>
     * Test Case Design: Questo test verifica che una lista con elementi non sia identificata come vuota.
     * <p>
     * Test Description: 1. Verifica lo stato di vuoto della lista dopo la sua inizializzazione nel setup.
     * <p>
     * Preconditions: La lista è stata inizializzata con 4 elementi.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code isEmpty()} deve restituire false.
     */
    @Test
    public void testIsEmptyPopulatedList()
    {
        assertFalse(list.isEmpty());
    }

    //------- TEST DEL METODO contains(Object) ----------

    /**
     * Test del metodo {@link HList#contains(Object)}.
     * <p>
     * Summary: Verifica che {@code contains()} restituisca true per un elemento presente nella lista.
     * <p>
     * Test Case Design: Assicurarsi che gli elementi effettivamente presenti siano riconosciuti.
     * <p>
     * Test Description: 1. Cerca un elemento presente nella lista ("due").<br />
     * 2. Verifica che il risultato sia true.
     * <p>
     * Preconditions: Lista popolata con 4 elementi.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code contains("due")} deve restituire true.
     */
    @Test
    public void testContainsExistingElement()
    {
        assertTrue(list.contains("due"));
    }

    /**
     * Test del metodo {@link HList#contains(Object)}.
     * <p>
     * Summary: Verifica che {@code contains()} restituisca false per un elemento non presente nella lista.
     * <p>
     * Test Case Design: Assicurarsi che gli elementi non presenti siano correttamente identificati come tali.
     * <p>
     * Test Description: 1. Cerca un elemento non presente nella lista ("cinque").<br />
     * 2. Verifica che il risultato sia false.
     * <p>
     * Preconditions: Lista popolata con 4 elementi.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code contains("cinque")} deve restituire false.
     */
    @Test
    public void testContainsNonExistingElement()
    {
        assertFalse(list.contains("cinque"));
    }

    /**
     * Test del metodo {@link HList#contains(Object)}.
     * <p>
     * Summary: Verifica che {@code contains(null)} restituisca false se null non è presente.
     * <p>
     * Test Case Design: Assicurarsi che la ricerca di un elemento null funzioni correttamente quando non è presente.
     * <p>
     * Test Description: 1. Verifica che la lista non contenga null.<br />
     * 2. Cerca null nella lista.<br />
     * 3. Verifica che il risultato sia false.
     * <p>
     * Preconditions: Lista popolata con 4 elementi non null.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code contains(null)} deve restituire false.
     */
    @Test
    public void testContainsNullNotPresent()
    {
        assertFalse(list.contains(null));
    }

    /**
     * Test del metodo {@link HList#contains(Object)}.
     * <p>
     * Summary: Verifica che {@code contains(null)} restituisca true se null è presente.
     * <p>
     * Test Case Design: Assicurarsi che la ricerca di un elemento null funzioni correttamente quando è presente.
     * <p>
     * Test Description: 1. Aggiunge null alla lista.<br />
     * 2. Cerca null nella lista.<br />
     * 3. Verifica che il risultato sia true.<br />
     * 4. Verifica la dimensione della lista sia corretta.
     * <p>
     * Preconditions: Lista popolata con 4 elementi non null.
     * <p>
     * Postconditions: La lista contiene un elemento null in più.
     * <p>
     * @expected {@code contains(null)} deve restituire true.
     */
    @Test
    public void testContainsNullWhenPresent()
    {
        list.add(null);
        assertTrue(list.contains(null));
        assertEquals(5, list.size());
    }

    //------- TEST DEL METODO iterator() ----------

    /**
     * Test del metodo {@link HList#iterator()}.
     * <p>
     * Summary: Verifica l'iterazione base su una lista popolata.
     * <p>
     * Test Case Design: Assicurarsi che l'iteratore possa percorrere tutti gli elementi.
     * <p>
     * Test Description: 1. Ottiene un iteratore.<br />
     * 2. Itera attraverso gli elementi verificandone la presenza.<br />
     * 3. Verifica che tutti gli elementi siano stati visitati e {@code hasNext()} sia false alla fine.
     * <p>
     * Preconditions: Lista popolata con 4 elementi.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected L'iteratore deve restituire tutti gli elementi nell'ordine corretto.
     */
    @Test
    public void testIteratorBasicIteration()
    {
        HIterator it = list.iterator();
        assertTrue(it.hasNext());
        assertEquals("uno", it.next());
        assertTrue(it.hasNext());
        assertEquals("due", it.next());
        assertTrue(it.hasNext());
        assertEquals("tre", it.next());
        assertTrue(it.hasNext());
        assertEquals("quattro", it.next());
        assertFalse(it.hasNext());
    }

    //------- TEST DEL METODO toArray() ----------

    /**
     * Test del metodo {@link HList#toArray()}.
     * <p>
     * Summary: Verifica che {@code toArray()} su una lista popolata restituisca un array corretto.
     * <p>
     * Test Case Design: Assicurarsi che la conversione in array includa tutti gli elementi nell'ordine corretto.
     * <p>
     * Test Description: 1. Chiama {@code toArray()} su una lista popolata.<br />
     * 2. Verifica che l'array risultante non sia null.<br />
     * 3. Verifica che la sua lunghezza sia uguale alla dimensione della lista.<br />
     * 4. Verifica il contenuto dell'array.
     * <p>
     * Preconditions: Lista popolata con 4 elementi.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected L'array deve contenere gli stessi elementi della lista nell'ordine.
     */
    @Test
    public void testToArrayPopulatedList()
    {
        Object[] arr = list.toArray();
        assertNotNull(arr);
        assertEquals(4, arr.length);
        assertEquals("uno", arr[0]);
        assertEquals("due", arr[1]);
        assertEquals("tre", arr[2]);
        assertEquals("quattro", arr[3]);
    }

    //------- TEST DEL METODO toArray(Object[]) ----------

    /**
     * Test del metodo {@link HList#toArray(Object[])}.
     * <p>
     * Summary: Verifica che {@code toArray(T[] a)} riutilizzi un array fornito di dimensione sufficiente.
     * <p>
     * Test Case Design: Assicurarsi che l'array fornito, se grande abbastanza, venga riempito e l'elemento dopo l'ultimo sia null.
     * <p>
     * Test Description: 1. Crea un array di stringhe di dimensione maggiore della lista.<br />
     * 2. Chiama {@code toArray(arr)}.<br />
     * 3. Verifica che l'array restituito sia lo stesso array passato.<br />
     * 4. Verifica il contenuto e la nullificazione dell'elemento extra.
     * <p>
     * Preconditions: Lista popolata con 4 elementi. Array fornito di dimensione 5.
     * <p>
     * Postconditions: La lista rimane invariata. L'array fornito è riempito.
     * <p>
     * @expected L'array deve contenere gli elementi della lista e null all'indice 4.
     */
    @Test
    public void testToArrayWithSufficientlyLargeArray()
    {
        String[] arr = new String[5];
        arr[4] = "extra"; // Aggiungi un elemento per verificare che venga nullificato
        Object[] result = list.toArray(arr);
        assertSame(arr, result); // Deve essere la stessa istanza
        assertEquals(5, result.length); // La lunghezza rimane quella dell'array passato

        assertEquals("uno", result[0]);
        assertEquals("due", result[1]);
        assertEquals("tre", result[2]);
        assertEquals("quattro", result[3]);
        assertNull(result[4]); // L'elemento extra dovrebbe essere nullificato
    }

    /**
     * Test del metodo {@link HList#toArray(Object[])}.
     * <p>
     * Summary: Verifica che {@code toArray(T[] a)} crei un nuovo array se quello fornito è troppo piccolo.
     * <p>
     * Test Case Design: Assicurarsi che un nuovo array venga allocato quando quello fornito è insufficiente.
     * <p>
     * Test Description: 1. Crea un array di stringhe di dimensione minore della lista.<br />
     * 2. Chiama {@code toArray(arr)}.<br />
     * 3. Verifica che l'array restituito non sia lo stesso array passato.<br />
     * 4. Verifica che il nuovo array abbia la dimensione corretta e il contenuto corretto.
     * <p>
     * Preconditions: Lista popolata con 4 elementi. Array fornito di dimensione 2.
     * <p>
     * Postconditions: La lista rimane invariata. Un nuovo array è creato.
     * <p>
     * @expected Un nuovo array di dimensione 4 con gli elementi della lista.
     */
    @Test
    public void testToArrayWithTooSmallArray()
    {
        String[] arr = new String[2];
        Object[] result = list.toArray(arr);
        assertNotSame(arr, result); // Deve essere una nuova istanza
        assertEquals(4, result.length); // La lunghezza deve essere quella della lista

        assertEquals("uno", result[0]);
        assertEquals("due", result[1]);
        assertEquals("tre", result[2]);
        assertEquals("quattro", result[3]);
    }

    /**
     * Test del metodo {@link HList#toArray(Object[])}.
     * <p>
     * Summary: Verifica che {@code toArray(T[] a)} lanci {@code NullPointerException} se l'array fornito è null.
     * <p>
     * Test Case Design: Assicurarsi che il metodo gestisca correttamente l'input null per l'array.
     * <p>
     * Test Description: 1. Chiama {@code toArray(null)}.<br />
     * 2. Verifica che venga lanciata {@code NullPointerException}.
     * <p>
     * Preconditions: Lista popolata.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code NullPointerException}.
     */
    @Test(expected = NullPointerException.class)
    public void testToArrayNullArray()
    {
        list.toArray(null);
    }

    //------- TEST DEL METODO add(Object) ----------

    /**
     * Test del metodo {@link HList#add(Object)}.
     * <p>
     * Summary: Verifica l'aggiunta di un elemento alla fine della lista popolata.
     * <p>
     * Test Case Design: Assicurarsi che l'elemento sia aggiunto in coda, la dimensione sia aggiornata
     * e il metodo restituisca true.
     * <p>
     * Test Description: 1. Aggiunge un nuovo elemento.<br />
     * 2. Verifica che la dimensione sia 5.<br />
     * 3. Verifica che l'elemento aggiunto sia l'ultimo della lista.
     * <p>
     * Preconditions: Lista popolata con 4 elementi.
     * <p>
     * Postconditions: La lista contiene un elemento in più alla fine, dimensione 5.
     * <p>
     * @expected {@code add(Object)} deve restituire true, {@code size()} deve essere 5,
     * e {@code get(4)} deve restituire l'elemento aggiunto.
     */
    @Test
    public void testAddObjectToPopulatedList()
    {
        assertTrue(list.add("cinque"));
        assertEquals(5, list.size());
        assertEquals("cinque", list.get(4));
    }

    /**
     * Test del metodo {@link HList#add(Object)} con elemento null.
     * <p>
     * Summary: Verifica che l'aggiunta di un elemento null alla lista popolata funzioni.
     * <p>
     * Test Case Design: Assicurarsi che il metodo possa gestire l'aggiunta di elementi nulli.
     * <p>
     * Test Description: 1. Aggiunge un elemento null.<br />
     * 2. Verifica che la dimensione sia 5.<br />
     * 3. Verifica che l'elemento null sia presente all'ultimo indice.
     * <p>
     * Preconditions: Lista popolata con 4 elementi.
     * <p>
     * Postconditions: La lista contiene un elemento null in più alla fine, dimensione 5.
     * <p>
     * @expected {@code add(Object)} deve restituire true, {@code size()} deve essere 5,
     * e {@code get(4)} deve restituire null.
     */
    @Test
    public void testAddNullObjectToPopulatedList()
    {
        assertTrue(list.add(null));
        assertEquals(5, list.size());
        assertNull(list.get(4));
        assertTrue(list.contains(null));
    }

    /**
     * Test del metodo {@link HList#add(Object)}.
     * <p>
     * Summary: Verifica l'aggiunta di molteplici elementi alla fine di una lista popolata.
     * <p>
     * Test Case Design: Assicurarsi che l'aggiunta ripetuta di elementi in coda funzioni correttamente,
     * che la dimensione sia aggiornata progressivamente e che l'ordine degli elementi sia mantenuto.
     * <p>
     * Test Description: 1. Aggiunge tre nuovi elementi consecutivamente ("cinque", "sei", "sette").<br />
     * 2. Dopo ogni aggiunta, verifica che la dimensione della lista sia incrementata.<br />
     * 3. Verifica che ogni elemento aggiunto si trovi all'indice corretto (l'ultimo della lista in quel momento).
     * <p>
     * Preconditions: Lista popolata con 4 elementi: ["uno", "due", "tre", "quattro"].
     * <p>
     * Postconditions: La lista contiene 7 elementi in totale, aggiunti in coda nell'ordine corretto.
     * <p>
     * @expected {@code add(Object)} deve sempre restituire true.
     * La lista finale deve essere `["uno", "due", "tre", "quattro", "cinque", "sei", "sette"]`.
     * La dimensione finale deve essere 7.
     */
    @Test
    public void testAddMultipleObjectsToPopulatedList() {
        // Lista iniziale: ["uno", "due", "tre", "quattro"]
        assertEquals(4, list.size());

        assertTrue(list.add("cinque"));
        assertEquals(5, list.size());
        assertEquals("cinque", list.get(4));

        assertTrue(list.add("sei"));
        assertEquals(6, list.size());
        assertEquals("sei", list.get(5));

        assertTrue(list.add("sette"));
        assertEquals(7, list.size());
        assertEquals("sette", list.get(6));

        // Verifica finale di tutti gli elementi
        assertEquals("uno", list.get(0));
        assertEquals("due", list.get(1));
        assertEquals("tre", list.get(2));
        assertEquals("quattro", list.get(3));
    }

    /**
     * Test del metodo {@link HList#add(Object)}.
     * <p>
     * Summary: Verifica il comportamento di `add(Object)` quando la lista viene riempita fino alla sua capacità massima (se limitata) o cresce dinamicamente.
     * <p>
     * Test Case Design: Assicurarsi che la lista possa espandersi dinamicamente o gestire la capacità se non predefinita.
     * Per `ArrayList` (come probabilmente `ListAdapter` si basa), questo testa l'espansione implicita dell'array sottostante.
     * <p>
     * Test Description: 1. Aggiunge un numero elevato di elementi alla lista (es. 100 elementi).<br />
     * 2. Verifica che la dimensione della lista sia corretta dopo tutte le aggiunte.<br />
     * 3. Verifica il contenuto dell'ultimo elemento aggiunto.
     * <p>
     * Preconditions: Lista inizialmente popolata con 4 elementi.
     * <p>
     * Postconditions: La lista contiene gli elementi iniziali più tutti i nuovi elementi aggiunti.
     * <p>
     * @expected Tutte le chiamate a `add(Object)` devono restituire true e la lista deve contenere tutti gli elementi aggiunti.
     * La dimensione finale deve essere 4 + il numero di elementi aggiunti.
     */
    @Test
    public void testAddObjectStressTest() {
        // Lista iniziale: ["uno", "due", "tre", "quattro"]
        int initialSize = list.size(); // 4
        int elementsToAdd = 1000; // Numero elevato di elementi da aggiungere

        for (int i = 0; i < elementsToAdd; i++) {
            assertTrue(list.add("element_" + i));
        }

        assertEquals(initialSize + elementsToAdd, list.size());
        assertEquals("element_" + (elementsToAdd - 1), list.get(list.size() - 1)); // Verifica l'ultimo elemento
        assertEquals("uno", list.get(0)); // Verifica che i primi elementi non siano stati alterati
    }

    //------- TEST DEL METODO add(int, Object) ----------

    /**
     * Test del metodo {@link HList#add(int, Object)}.
     * <p>
     * Summary: Verifica l'aggiunta di un elemento a un indice specifico all'interno della lista.
     * <p>
     * Test Case Design: Assicurarsi che l'elemento sia inserito correttamente all'indice desiderato,
     * che gli elementi successivi vengano spostati, e che la dimensione della lista sia aggiornata.
     * <p>
     * Test Description: 1. Aggiunge un nuovo elemento all'indice 1.<br />
     * 2. Verifica che la dimensione della lista sia 5.<br />
     * 3. Verifica che l'elemento aggiunto ("nuovo") sia all'indice 1.<br />
     * 4. Verifica che l'elemento precedentemente all'indice 1 ("due") sia ora all'indice 2.
     * <p>
     * Preconditions: Lista popolata con 4 elementi: ["uno", "due", "tre", "quattro"].
     * <p>
     * Postconditions: La lista contiene un elemento in più all'indice specificato, con gli elementi successivi spostati.
     * La dimensione della lista è 5.
     * <p>
     * @expected La lista deve essere `["uno", "nuovo", "due", "tre", "quattro"]`.
     * {@code size()} deve essere 5, {@code get(1)} deve restituire "nuovo",
     * e {@code get(2)} deve restituire "due".
     */
    @Test
    public void testAddObjectAtIndexPopulatedList() {
        list.add(1, "nuovo"); // Aggiungi "nuovo" all'indice 1
        assertEquals(5, list.size());
        assertEquals("uno", list.get(0));
        assertEquals("nuovo", list.get(1));
        assertEquals("due", list.get(2)); // Verifica che l'elemento "due" sia stato spostato
        assertEquals("tre", list.get(3));
        assertEquals("quattro", list.get(4));
    }

    /**
     * Test del metodo {@link HList#add(int, Object)}.
     * <p>
     * Summary: Verifica che {@code add(int, Object)} lanci {@code IndexOutOfBoundsException} per un indice fuori limite superiore.
     * <p>
     * Test Case Design: Assicurarsi che il metodo rispetti il contratto Javadoc, che specifica il lancio di
     * {@code IndexOutOfBoundsException} se l'indice è maggiore della dimensione della lista.
     * <p>
     * Test Description: 1. Tenta di aggiungere un elemento a un indice maggiore della dimensione corrente della lista (size + 1).<br />
     * 2. Si aspetta che venga lanciata una {@code IndexOutOfBoundsException}.
     * <p>
     * Preconditions: Lista popolata con 4 elementi.
     * <p>
     * Postconditions: Nessuna modifica alla lista; viene lanciata {@code IndexOutOfBoundsException}.
     * <p>
     * @expected {@code IndexOutOfBoundsException} per {@code add(list.size() + 1, "elemento")}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAtIndexOutOfBounds() {
        // La lista ha 4 elementi (indici 0, 1, 2, 3).
        // Gli indici validi per add(index, element) sono da 0 a 4 (list.size()).
        list.add(list.size() + 1, "elemento"); // Tentativo di aggiungere a un indice fuori limite
    }

    /**
     * Test del metodo {@link HList#add(int, Object)}.
     * <p>
     * Summary: Verifica l'aggiunta di un elemento all'inizio (indice 0) di una lista popolata.
     * <p>
     * Test Case Design: Assicurarsi che l'elemento sia correttamente inserito all'inizio e che tutti gli altri elementi vengano spostati.
     * <p>
     * Test Description: 1. Aggiunge un nuovo elemento "zero" all'indice 0.<br />
     * 2. Verifica che la dimensione sia 5.<br />
     * 3. Verifica che l'elemento aggiunto sia all'indice 0.<br />
     * 4. Verifica che l'elemento precedentemente all'indice 0 ("uno") sia ora all'indice 1.
     * <p>
     * Preconditions: Lista popolata con 4 elementi: ["uno", "due", "tre", "quattro"].
     * <p>
     * Postconditions: La lista contiene un elemento in più all'inizio.
     * <p>
     * @expected La lista deve essere `["zero", "uno", "due", "tre", "quattro"]`.
     */
    @Test
    public void testAddAtIndex0PopulatedList() {
        list.add(0, "zero");
        assertEquals(5, list.size());
        assertEquals("zero", list.get(0));
        assertEquals("uno", list.get(1)); // L'elemento originale "uno" dovrebbe essere stato spostato
    }

    /**
     * Test del metodo {@link HList#add(int, Object)}.
     * <p>
     * Summary: Verifica l'aggiunta di un elemento all'ultimo indice valido (list.size()).
     * <p>
     * Test Case Design: Assicurarsi che l'aggiunta all'indice `size()` funzioni come `add(Object)`.
     * <p>
     * Test Description: 1. Aggiunge un nuovo elemento "cinque" all'indice `list.size()` (che è 4).<br />
     * 2. Verifica che la dimensione sia 5.<br />
     * 3. Verifica che l'elemento aggiunto sia l'ultimo della lista.
     * <p>
     * Preconditions: Lista popolata con 4 elementi.
     * <p>
     * Postconditions: La lista contiene un elemento in più alla fine.
     * <p>
     * @expected La lista deve essere `["uno", "due", "tre", "quattro", "cinque"]`.
     */
    @Test
    public void testAddAtIndexLastValidPopulatedList() {
        list.add(list.size(), "cinque");
        assertEquals(5, list.size());
        assertEquals("cinque", list.get(4));
    }

    /**
     * Test del metodo {@link HList#add(int, Object)} con elemento null.
     * <p>
     * Summary: Verifica l'aggiunta di un elemento null a un indice specifico.
     * <p>
     * Test Case Design: Assicurarsi che `null` possa essere inserito correttamente a un dato indice.
     * <p>
     * Test Description: 1. Aggiunge null all'indice 2.<br />
     * 2. Verifica la dimensione.<br />
     * 3. Verifica che null sia all'indice 2.<br />
     * 4. Verifica che gli elementi successivi siano spostati.
     * <p>
     * Preconditions: Lista popolata con 4 elementi.
     * <p>
     * Postconditions: La lista contiene un elemento null all'indice specificato.
     * <p>
     * @expected La lista deve essere `["uno", "due", null, "tre", "quattro"]`.
     */
    @Test
    public void testAddNullAtIndexPopulatedList() {
        list.add(2, null);
        assertEquals(5, list.size());
        assertEquals("uno", list.get(0));
        assertEquals("due", list.get(1));
        assertNull(list.get(2)); // null dovrebbe essere qui
        assertEquals("tre", list.get(3)); // "tre" spostato
        assertEquals("quattro", list.get(4)); // "quattro" spostato
    }

    /**
     * Test del metodo {@link HList#add(int, Object)}.
     * <p>
     * Summary: Verifica che {@code add(int, Object)} lanci {@code IndexOutOfBoundsException} per indice negativo.
     * <p>
     * Test Case Design: Assicurarsi che il metodo rispetti il contratto Javadoc per indici negativi.
     * <p>
     * Test Description: 1. Tenta di aggiungere un elemento a un indice negativo (-1).<br />
     * 2. Si aspetta che venga lanciata una {@code IndexOutOfBoundsException}.
     * <p>
     * Preconditions: Lista popolata.
     * <p>
     * Postconditions: Nessuna modifica alla lista; viene lanciata {@code IndexOutOfBoundsException}.
     * <p>
     * @expected {@code IndexOutOfBoundsException} per {@code add(-1, "elemento")}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAtIndexNegativePopulatedList() {
        list.add(-1, "elemento");
    }

    //------- TEST DEL METODO remove(Object) ----------

    /**
     * Test del metodo {@link HList#remove(Object)}.
     * <p>
     * Summary: Verifica la rimozione di un elemento intermedio dalla lista popolata.
     * <p>
     * Test Case Design: Assicurarsi che l'elemento corretto venga rimosso e che gli elementi successivi si spostino.
     * <p>
     * Test Description: 1. Rimuove l'elemento "due".<br />
     * 2. Verifica che il risultato sia true.<br />
     * 3. Verifica che la dimensione sia 3.<br />
     * 4. Verifica che "due" non sia più presente.<br />
     * 5. Verifica che gli elementi rimanenti siano nell'ordine corretto.
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"].
     * <p>
     * Postconditions: Lista: ["uno", "tre", "quattro"].
     * <p>
     * @expected {@code remove("due")} deve restituire true.
     */
    @Test
    public void testRemoveObjectFromPopulatedList()
    {
        assertTrue(list.remove("due"));
        assertEquals(3, list.size());
        assertFalse(list.contains("due"));
        assertEquals("uno", list.get(0));
        assertEquals("tre", list.get(1));
        assertEquals("quattro", list.get(2));
    }

    /**
     * Test del metodo {@link HList#remove(Object)}.
     * <p>
     * Summary: Verifica la rimozione del primo elemento dalla lista popolata.
     * <p>
     * Test Case Design: Assicurarsi che la rimozione del primo elemento sposti correttamente gli altri.
     * <p>
     * Test Description: 1. Rimuove l'elemento "uno".<br />
     * 2. Verifica che il risultato sia true.<br />
     * 3. Verifica che la dimensione sia 3.<br />
     * 4. Verifica che "uno" non sia più presente.<br />
     * 5. Verifica che "due" sia ora il primo elemento.
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"].
     * <p>
     * Postconditions: Lista: ["due", "tre", "quattro"].
     * <p>
     * @expected {@code remove("uno")} deve restituire true.
     */
    @Test
    public void testRemoveFirstObjectFromPopulatedList() {
        assertTrue(list.remove("uno"));
        assertEquals(3, list.size());
        assertFalse(list.contains("uno"));
        assertEquals("due", list.get(0));
        assertEquals("tre", list.get(1));
        assertEquals("quattro", list.get(2));
    }

    /**
     * Test del metodo {@link HList#remove(Object)}.
     * <p>
     * Summary: Verifica la rimozione dell'ultimo elemento dalla lista popolata.
     * <p>
     * Test Case Design: Assicurarsi che la rimozione dell'ultimo elemento sia gestita correttamente.
     * <p>
     * Test Description: 1. Rimuove l'elemento "quattro".<br />
     * 2. Verifica che il risultato sia true.<br />
     * 3. Verifica che la dimensione sia 3.<br />
     * 4. Verifica che "quattro" non sia più presente.
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"].
     * <p>
     * Postconditions: Lista: ["uno", "due", "tre"].
     * <p>
     * @expected {@code remove("quattro")} deve restituire true.
     */
    @Test
    public void testRemoveLastObjectFromPopulatedList() {
        assertTrue(list.remove("quattro"));
        assertEquals(3, list.size());
        assertFalse(list.contains("quattro"));
        assertEquals("tre", list.get(2)); // Verifica che il nuovo ultimo elemento sia "tre"
    }

    /**
     * Test del metodo {@link HList#remove(Object)}.
     * <p>
     * Summary: Verifica che {@code remove(Object)} restituisca false per un elemento non presente.
     * <p>
     * Test Case Design: Assicurarsi che la rimozione di un elemento inesistente non modifichi la lista.
     * <p>
     * Test Description: 1. Tenta di rimuovere un elemento "cinque" non presente.<br />
     * 2. Verifica che il risultato sia false.<br />
     * 3. Verifica che la dimensione della lista non sia cambiata.
     * <p>
     * Preconditions: Lista popolata con 4 elementi.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code remove("cinque")} deve restituire false.
     */
    @Test
    public void testRemoveNonExistingObject()
    {
        assertFalse(list.remove("cinque"));
        assertEquals(4, list.size()); // Dimensione invariata
    }

    /**
     * Test del metodo {@link HList#remove(Object)}.
     * <p>
     * Summary: Verifica la rimozione di un elemento null dalla lista popolata.
     * <p>
     * Test Case Design: Assicurarsi che la rimozione di null funzioni correttamente.
     * <p>
     * Test Description: 1. Aggiunge null alla lista.<br />
     * 2. Rimuove null dalla lista.<br />
     * 3. Verifica che il risultato sia true.<br />
     * 4. Verifica che la dimensione sia tornata a 4.
     * <p>
     * Preconditions: Lista popolata con 4 elementi non null, poi un null aggiunto.
     * <p>
     * Postconditions: La lista non contiene più l'elemento null.
     * <p>
     * @expected {@code remove(null)} deve restituire true dopo l'aggiunta.
     */
    @Test
    public void testRemoveNullObject()
    {
        list.add(null); // List: ["uno", "due", "tre", "quattro", null]
        assertEquals(5, list.size());
        assertTrue(list.remove(null));
        assertEquals(4, list.size()); // Dimensione tornata a 4
        assertFalse(list.contains(null)); // Null non più presente
    }

    /**
     * Test del metodo {@link HList#remove(Object)}.
     * <p>
     * Summary: Verifica che {@code remove(null)} restituisca false se null non è presente.
     * <p>
     * Test Case Design: Assicurarsi che tentare di rimuovere null da una lista senza null non alteri la lista.
     * <p>
     * Test Description: 1. Tenta di rimuovere null da una lista senza null.<br />
     * 2. Verifica che il risultato sia false.<br />
     * 3. Verifica che la dimensione non sia cambiata.
     * <p>
     * Preconditions: Lista popolata con 4 elementi non null.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code remove(null)} deve restituire false.
     */
    @Test
    public void testRemoveNullObjectNotPresent() {
        assertFalse(list.remove(null));
        assertEquals(4, list.size());
    }

    /**
     * Test del metodo {@link HList#remove(Object)}.
     * <p>
     * Summary: Verifica che {@code remove(Object)} rimuova solo la prima occorrenza di un elemento duplicato.
     * <p>
     * Test Case Design: Assicurarsi che la semantica di `remove(Object)` sia di rimozione della prima occorrenza.
     * <p>
     * Test Description: 1. Aggiunge un duplicato ("due") alla lista.<br />
     * 2. Rimuove "due".<br />
     * 3. Verifica che la dimensione sia 4 e che l'altro "due" sia ancora presente.
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"], viene aggiunto "due" -> ["uno", "due", "tre", "quattro", "due"].
     * <p>
     * Postconditions: Lista: ["uno", "tre", "quattro", "due"].
     * <p>
     * @expected La dimensione deve essere 4 e {@code contains("due")} deve essere true.
     */
    @Test
    public void testRemoveObjectFirstOccurrence() {
        list.add("due"); // List: ["uno", "due", "tre", "quattro", "due"]
        assertEquals(5, list.size());

        assertTrue(list.remove("due")); // Rimuove il primo "due"
        assertEquals(4, list.size());
        assertTrue(list.contains("due")); // Il secondo "due" dovrebbe essere ancora lì
        assertEquals("uno", list.get(0));
        assertEquals("tre", list.get(1));
        assertEquals("quattro", list.get(2));
        assertEquals("due", list.get(3)); // Il secondo "due" è ora all'indice 3
    }


    //------- TEST DEL METODO remove(int) ----------

    /**
     * Test del metodo {@link HList#remove(int)}.
     * <p>
     * Summary: Verifica la rimozione dell'elemento all'indice 0 dalla lista popolata.
     * <p>
     * Test Case Design: Assicurarsi che la rimozione del primo elemento avvenga correttamente.
     * <p>
     * Test Description: 1. Rimuove l'elemento all'indice 0.<br />
     * 2. Verifica che l'elemento restituito sia "uno".<br />
     * 3. Verifica che la dimensione sia 3.<br />
     * 4. Verifica che "uno" non sia più presente.<br />
     * 5. Verifica che "due" sia ora il primo elemento.
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"].
     * <p>
     * Postconditions: Lista: ["due", "tre", "quattro"].
     * <p>
     * @expected {@code remove(0)} deve restituire "uno".
     */
    @Test
    public void testRemoveAtIndex0PopulatedList() {
        Object removedElement = list.remove(0);
        assertEquals("uno", removedElement);
        assertEquals(3, list.size());
        assertFalse(list.contains("uno"));
        assertEquals("due", list.get(0));
    }

    /**
     * Test del metodo {@link HList#remove(int)}.
     * <p>
     * Summary: Verifica la rimozione dell'elemento all'ultimo indice valido (size - 1) dalla lista popolata.
     * <p>
     * Test Case Design: Assicurarsi che la rimozione dell'ultimo elemento avvenga correttamente.
     * <p>
     * Test Description: 1. Rimuove l'elemento all'indice `list.size() - 1` (che è 3).<br />
     * 2. Verifica che l'elemento restituito sia "quattro".<br />
     * 3. Verifica che la dimensione sia 3.<br />
     * 4. Verifica che "quattro" non sia più presente.
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"].
     * <p>
     * Postconditions: Lista: ["uno", "due", "tre"].
     * <p>
     * @expected {@code remove(3)} deve restituire "quattro".
     */
    @Test
    public void testRemoveAtIndexLastPopulatedList() {
        Object removedElement = list.remove(list.size() - 1);
        assertEquals("quattro", removedElement);
        assertEquals(3, list.size());
        assertFalse(list.contains("quattro"));
        assertEquals("tre", list.get(list.size() - 1)); // Nuovo ultimo elemento
    }

    /**
     * Test del metodo {@link HList#remove(int)}.
     * <p>
     * Summary: Verifica la rimozione di un elemento a un indice intermedio dalla lista popolata.
     * <p>
     * Test Case Design: Assicurarsi che l'elemento corretto venga rimosso e che gli elementi successivi si spostino.
     * <p>
     * Test Description: 1. Rimuove l'elemento all'indice 1 ("due").<br />
     * 2. Verifica che l'elemento restituito sia "due".<br />
     * 3. Verifica che la dimensione sia 3.<br />
     * 4. Verifica che "due" non sia più presente.<br />
     * 5. Verifica che "tre" sia ora all'indice 1.
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"].
     * <p>
     * Postconditions: Lista: ["uno", "tre", "quattro"].
     * <p>
     * @expected {@code remove(1)} deve restituire "due".
     */
    @Test
    public void testRemoveAtIndexIntermediatePopulatedList() {
        Object removedElement = list.remove(1);
        assertEquals("due", removedElement);
        assertEquals(3, list.size());
        assertFalse(list.contains("due"));
        assertEquals("uno", list.get(0));
        assertEquals("tre", list.get(1));
        assertEquals("quattro", list.get(2));
    }

    /**
     * Test del metodo {@link HList#remove(int)}.
     * <p>
     * Summary: Verifica che {@code remove(int)} lanci {@code IndexOutOfBoundsException} per indice negativo.
     * <p>
     * Test Case Design: Assicurarsi che il metodo rispetti il contratto Javadoc per indici negativi.
     * <p>
     * Test Description: 1. Tenta di rimuovere un elemento da un indice negativo (-1).<br />
     * 2. Si aspetta che venga lanciata una {@code IndexOutOfBoundsException}.
     * <p>
     * Preconditions: Lista popolata.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code IndexOutOfBoundsException}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveAtIndexNegativePopulatedList() {
        list.remove(-1);
    }

    /**
     * Test del metodo {@link HList#remove(int)}.
     * <p>
     * Summary: Verifica che {@code remove(int)} lanci {@code IndexOutOfBoundsException} per indice uguale alla dimensione.
     * <p>
     * Test Case Design: Assicurarsi che il metodo rispetti il contratto Javadoc per indici fuori limite superiore.
     * <p>
     * Test Description: 1. Tenta di rimuovere un elemento dall'indice {@code list.size()}.<br />
     * 2. Si aspetta che venga lanciata una {@code IndexOutOfBoundsException}.
     * <p>
     * Preconditions: Lista popolata.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code IndexOutOfBoundsException}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveAtIndexSizePopulatedList() {
        list.remove(list.size());
    }

    //------- TEST DEL METODO addAll(HCollection) ----------

    /**
     * Test del metodo {@link HList#addAll(HCollection)}.
     * <p>
     * Summary: Verifica che {@code addAll(HCollection)} aggiunga tutti gli elementi di una collezione.
     * <p>
     * Test Case Design: Assicurarsi che tutti gli elementi della collezione vengano aggiunti in coda e che la dimensione sia corretta.
     * <p>
     * Test Description: 1. Crea una collezione con elementi ("cinque", "sei").<br />
     * 2. Chiama {@code addAll()} sulla lista.<br />
     * 3. Verifica che il risultato sia true.<br />
     * 4. Verifica che la dimensione sia 6.<br />
     * 5. Verifica che i nuovi elementi siano in coda.
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"]. Collezione: ["cinque", "sei"].
     * <p>
     * Postconditions: Lista: ["uno", "due", "tre", "quattro", "cinque", "sei"].
     * <p>
     * @expected {@code addAll()} deve restituire true.
     */
    @Test
    public void testAddAllCollectionToPopulatedList() {
        ListAdapter collectionToAdd = new ListAdapter();
        collectionToAdd.add("cinque");
        collectionToAdd.add("sei");

        assertTrue(list.addAll(collectionToAdd));
        assertEquals(6, list.size());
        assertEquals("cinque", list.get(4));
        assertEquals("sei", list.get(5));
    }

    /**
     * Test del metodo {@link HList#addAll(HCollection)}.
     * <p>
     * Summary: Verifica che {@code addAll(HCollection)} con una collezione vuota non modifichi la lista.
     * <p>
     * Test Case Design: Assicurarsi che l'aggiunta di una collezione vuota non alteri lo stato di una lista.
     * <p>
     * Test Description: 1. Crea una collezione vuota.<br />
     * 2. Chiama {@code addAll()} sulla lista popolata.<br />
     * 3. Verifica che il risultato sia false.<br />
     * 4. Verifica che la dimensione e il contenuto della lista non siano cambiati.
     * <p>
     * Preconditions: Lista popolata. Collezione vuota.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code addAll()} deve restituire false.
     */
    @Test
    public void testAddAllEmptyCollectionToPopulatedList() {
        HCollection emptyCollection = new ListAdapter();
        assertFalse(list.addAll(emptyCollection));
        assertEquals(4, list.size());
        assertEquals("uno", list.get(0)); // Verifica che i contenuti non siano cambiati
    }

    /**
     * Test del metodo {@link HList#addAll(HCollection)}.
     * <p>
     * Summary: Verifica che {@code addAll(HCollection)} lanci {@code NullPointerException} se la collezione è null.
     * <p>
     * Test Case Design: Assicurarsi che il metodo gestisca correttamente l'input null per la collezione.
     * <p>
     * Test Description: 1. Chiama {@code addAll(null)}.<br />
     * 2. Verifica che venga lanciata {@code NullPointerException}.
     * <p>
     * Preconditions: Lista popolata. Collezione null.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code NullPointerException}.
     */
    @Test(expected = NullPointerException.class)
    public void testAddAllNullCollectionToPopulatedList() {
        list.addAll(null);
    }

    /**
     * Test del metodo {@link HList#addAll(HCollection)}.
     * <p>
     * Summary: Verifica che {@code addAll(HCollection)} aggiunga elementi contenenti null.
     * <p>
     * Test Case Design: Assicurarsi che la lista possa gestire l'aggiunta di collezioni con elementi null.
     * <p>
     * Test Description: 1. Crea una collezione con elementi ("cinque", null, "sei").<br />
     * 2. Chiama {@code addAll()} sulla lista.<br />
     * 3. Verifica che il risultato sia true.<br />
     * 4. Verifica che la dimensione sia 7.<br />
     * 5. Verifica che i nuovi elementi, incluso null, siano in coda.
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"]. Collezione: ["cinque", null, "sei"].
     * <p>
     * Postconditions: Lista: ["uno", "due", "tre", "quattro", "cinque", null, "sei"].
     * <p>
     * @expected {@code addAll()} deve restituire true.
     */
    @Test
    public void testAddAllCollectionWithNullToPopulatedList() {
        ListAdapter collectionToAdd = new ListAdapter();
        collectionToAdd.add("cinque");
        collectionToAdd.add(null);
        collectionToAdd.add("sei");

        assertTrue(list.addAll(collectionToAdd));
        assertEquals(7, list.size());
        assertEquals("cinque", list.get(4));
        assertNull(list.get(5));
        assertEquals("sei", list.get(6));
        assertTrue(list.contains(null));
    }

    //------- TEST DEL METODO addAll(int, HCollection) ----------

    /**
     * Test del metodo {@link HList#addAll(int, HCollection)} con collezione vuota.
     * <p>
     * Summary: Verifica che {@code addAll(int, HCollection)} con collezione vuota non modifichi la lista.
     * <p>
     * Test Case Design: Assicurarsi che l'aggiunta di una collezione vuota non alteri lo stato di una lista.
     * <p>
     * Test Description: 1. Crea una collezione vuota.<br />
     * 2. Aggiunge la collezione vuota a un indice valido.<br />
     * 3. Verifica che il risultato sia false.<br />
     * 4. Verifica che la dimensione e il contenuto della lista non siano cambiati.
     * <p>
     * Preconditions: Lista popolata. Collezione vuota.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code addAll(int, HCollection)} deve restituire false.
     */
    @Test
    public void testAddAllAtIndexEmptyCollection()
    {
        HCollection emptyCollection = new ListAdapter();
        assertFalse(list.addAll(2, emptyCollection));
        assertEquals(4, list.size());
        assertEquals("uno", list.get(0)); // Verify contents unchanged
    }

    /**
     * Test del metodo {@link HList#addAll(int, HCollection)} con collezione null.
     * <p>
     * Summary: Verifica che {@code addAll(int, HCollection)} lanci {@code NullPointerException} se la collezione è null.
     * <p>
     * Test Case Design: Assicurarsi che il metodo gestisca correttamente l'input null per la collezione.
     * <p>
     * Test Description: 1. Chiama {@code addAll(2, null)}.<br />
     * 2. Verifica che venga lanciata {@code NullPointerException}.
     * <p>
     * Preconditions: Lista popolata.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code NullPointerException}.
     */
    @Test(expected = NullPointerException.class)
    public void testAddAllAtIndexNullCollection()
    {
        list.addAll(2, null);
    }

    /**
     * Test del metodo {@link HList#addAll(int, HCollection)}.
     * <p>
     * Summary: Verifica l'aggiunta di una collezione all'inizio (indice 0) di una lista popolata.
     * <p>
     * Test Case Design: Assicurarsi che tutti gli elementi della collezione vengano inseriti all'inizio e che gli altri si spostino.
     * <p>
     * Test Description: 1. Crea una collezione con elementi ("nuovoZero", "nuovoUno").<br />
     * 2. Chiama {@code addAll(0, collectionToAdd)}.<br />
     * 3. Verifica che il risultato sia true.<br />
     * 4. Verifica la nuova dimensione (6).<br />
     * 5. Verifica che i nuovi elementi siano agli indici 0 e 1, e gli originali siano spostati.
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"]. Collezione: ["nuovoZero", "nuovoUno"].
     * <p>
     * Postconditions: Lista: ["nuovoZero", "nuovoUno", "uno", "due", "tre", "quattro"].
     * <p>
     * @expected {@code addAll()} deve restituire true.
     */
    @Test
    public void testAddAllAtIndex0PopulatedList() {
        ListAdapter collectionToAdd = new ListAdapter();
        collectionToAdd.add("nuovoZero");
        collectionToAdd.add("nuovoUno");

        assertTrue(list.addAll(0, collectionToAdd));
        assertEquals(6, list.size());
        assertEquals("nuovoZero", list.get(0));
        assertEquals("nuovoUno", list.get(1));
        assertEquals("uno", list.get(2)); // Originale "uno" spostato
        assertEquals("due", list.get(3));
    }

    /**
     * Test del metodo {@link HList#addAll(int, HCollection)}.
     * <p>
     * Summary: Verifica l'aggiunta di una collezione a un indice intermedio di una lista popolata.
     * <p>
     * Test Case Design: Assicurarsi che gli elementi della collezione vengano inseriti correttamente, spostando gli elementi esistenti.
     * <p>
     * Test Description: 1. Crea una collezione con elementi ("insert1", "insert2").<br />
     * 2. Chiama {@code addAll(2, collectionToAdd)}.<br />
     * 3. Verifica che il risultato sia true.<br />
     * 4. Verifica la nuova dimensione (6).<br />
     * 5. Verifica il contenuto della lista.
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"]. Collezione: ["insert1", "insert2"].
     * <p>
     * Postconditions: Lista: ["uno", "due", "insert1", "insert2", "tre", "quattro"].
     * <p>
     * @expected {@code addAll()} deve restituire true.
     */
    @Test
    public void testAddAllAtIndexIntermediatePopulatedList() {
        ListAdapter collectionToAdd = new ListAdapter();
        collectionToAdd.add("insert1");
        collectionToAdd.add("insert2");

        assertTrue(list.addAll(2, collectionToAdd));
        assertEquals(6, list.size());
        assertEquals("uno", list.get(0));
        assertEquals("due", list.get(1));
        assertEquals("insert1", list.get(2));
        assertEquals("insert2", list.get(3));
        assertEquals("tre", list.get(4));
        assertEquals("quattro", list.get(5));
    }

    /**
     * Test del metodo {@link HList#addAll(int, HCollection)}.
     * <p>
     * Summary: Verifica l'aggiunta di una collezione alla fine (indice size()) di una lista popolata.
     * <p>
     * Test Case Design: Assicurarsi che l'aggiunta alla fine tramite indice sia equivalente a `addAll(HCollection)`.
     * <p>
     * Test Description: 1. Crea una collezione con elementi ("cinque", "sei").<br />
     * 2. Chiama {@code addAll(list.size(), collectionToAdd)}.<br />
     * 3. Verifica che il risultato sia true.<br />
     * 4. Verifica la nuova dimensione (6).<br />
     * 5. Verifica che i nuovi elementi siano in coda.
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"]. Collezione: ["cinque", "sei"].
     * <p>
     * Postconditions: Lista: ["uno", "due", "tre", "quattro", "cinque", "sei"].
     * <p>
     * @expected {@code addAll()} deve restituire true.
     */
    @Test
    public void testAddAllAtIndexEndPopulatedList() {
        ListAdapter collectionToAdd = new ListAdapter();
        collectionToAdd.add("cinque");
        collectionToAdd.add("sei");

        assertTrue(list.addAll(list.size(), collectionToAdd));
        assertEquals(6, list.size());
        assertEquals("cinque", list.get(4));
        assertEquals("sei", list.get(5));
    }

    /**
     * Test del metodo {@link HList#addAll(int, HCollection)}.
     * <p>
     * Summary: Verifica che {@code addAll(int, HCollection)} lanci {@code IndexOutOfBoundsException} per indice negativo.
     * <p>
     * Test Case Design: Assicurarsi che il metodo rispetti il contratto Javadoc per indici negativi.
     * <p>
     * Test Description: 1. Tenta di aggiungere una collezione a un indice negativo (-1).<br />
     * 2. Si aspetta che venga lanciata una {@code IndexOutOfBoundsException}.
     * <p>
     * Preconditions: Lista popolata.
     * <p>
     * Postconditions: Nessuna modifica alla lista.
     * <p>
     * @expected {@code IndexOutOfBoundsException}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAllAtIndexNegativePopulatedList() {
        ListAdapter collectionToAdd = new ListAdapter();
        collectionToAdd.add("a");
        list.addAll(-1, collectionToAdd);
    }

    /**
     * Test del metodo {@link HList#addAll(int, HCollection)}.
     * <p>
     * Summary: Verifica che {@code addAll(int, HCollection)} lanci {@code IndexOutOfBoundsException} per indice maggiore di size().
     * <p>
     * Test Case Design: Assicurarsi che il metodo rispetti il contratto Javadoc per indici fuori limite superiore.
     * <p>
     * Test Description: 1. Tenta di aggiungere una collezione a un indice maggiore di `list.size()`.<br />
     * 2. Si aspetta che venga lanciata una {@code IndexOutOfBoundsException}.
     * <p>
     * Preconditions: Lista popolata.
     * <p>
     * Postconditions: Nessuna modifica alla lista.
     * <p>
     * @expected {@code IndexOutOfBoundsException}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAllAtIndexGreaterThanSizePopulatedList() {
        ListAdapter collectionToAdd = new ListAdapter();
        collectionToAdd.add("a");
        list.addAll(list.size() + 1, collectionToAdd);
    }

    //------- TEST DEL METODO clear() ----------

    /**
     * Test del metodo {@link HList#clear()}.
     * <p>
     * Summary: Verifica che {@code clear()} svuoti una lista popolata.
     * <p>
     * Test Case Design: Assicurarsi che la lista diventi vuota dopo la chiamata a `clear()`.
     * <p>
     * Test Description: 1. Chiama {@code clear()} sulla lista popolata.<br />
     * 2. Verifica che la dimensione sia 0.<br />
     * 3. Verifica che {@code isEmpty()} sia true.
     * <p>
     * Preconditions: Lista popolata con 4 elementi.
     * <p>
     * Postconditions: La lista è vuota.
     * <p>
     * @expected La lista deve essere vuota.
     */
    @Test
    public void testClearPopulatedList() {
        list.clear();
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
        // Tentativo di accedere agli elementi dovrebbe lanciare IndexOutOfBoundsException
        try {
            list.get(0);
            fail("Expected IndexOutOfBoundsException after clear()");
        } catch (IndexOutOfBoundsException e) {
            // Successo atteso
        }
    }

    //------- TEST DEL METODO get(int) ----------

    /**
     * Test del metodo {@link HList#get(int)}.
     * <p>
     * Summary: Verifica l'accesso al primo elemento (indice 0) di una lista popolata.
     * <p>
     * Test Case Design: Assicurarsi che l'accesso al primo elemento sia corretto.
     * <p>
     * Test Description: 1. Accede all'elemento all'indice 0.<br />
     * 2. Verifica che l'elemento restituito sia "uno".
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"].
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code get(0)} deve restituire "uno".
     */
    @Test
    public void testGetAtIndex0PopulatedList() {
        assertEquals("uno", list.get(0));
    }

    /**
     * Test del metodo {@link HList#get(int)}.
     * <p>
     * Summary: Verifica l'accesso all'ultimo elemento (indice size - 1) di una lista popolata.
     * <p>
     * Test Case Design: Assicurarsi che l'accesso all'ultimo elemento sia corretto.
     * <p>
     * Test Description: 1. Accede all'elemento all'indice `list.size() - 1` (che è 3).<br />
     * 2. Verifica che l'elemento restituito sia "quattro".
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"].
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code get(list.size() - 1)} deve restituire "quattro".
     */
    @Test
    public void testGetAtIndexLastPopulatedList() {
        assertEquals("quattro", list.get(list.size() - 1));
    }

    /**
     * Test del metodo {@link HList#get(int)}.
     * <p>
     * Summary: Verifica l'accesso a un elemento intermedio di una lista popolata.
     * <p>
     * Test Case Design: Assicurarsi che l'accesso a un elemento intermedio sia corretto.
     * <p>
     * Test Description: 1. Accede all'elemento all'indice 1.<br />
     * 2. Verifica che l'elemento restituito sia "due".
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"].
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code get(1)} deve restituire "due".
     */
    @Test
    public void testGetAtIndexIntermediatePopulatedList() {
        assertEquals("due", list.get(1));
    }

    /**
     * Test del metodo {@link HList#get(int)}.
     * <p>
     * Summary: Verifica che {@code get(int)} lanci {@code IndexOutOfBoundsException} per indice negativo.
     * <p>
     * Test Case Design: Assicurarsi che il metodo rispetti il contratto Javadoc per indici negativi.
     * <p>
     * Test Description: 1. Tenta di accedere a un elemento all'indice -1.<br />
     * 2. Si aspetta che venga lanciata una {@code IndexOutOfBoundsException}.
     * <p>
     * Preconditions: Lista popolata.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code IndexOutOfBoundsException}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetAtIndexNegativePopulatedList() {
        list.get(-1);
    }

    /**
     * Test del metodo {@link HList#get(int)}.
     * <p>
     * Summary: Verifica che {@code get(int)} lanci {@code IndexOutOfBoundsException} per indice uguale o maggiore di size().
     * <p>
     * Test Case Design: Assicurarsi che il metodo rispetti il contratto Javadoc per indici fuori limite superiore.
     * <p>
     * Test Description: 1. Tenta di accedere a un elemento all'indice `list.size()` (che è 4).<br />
     * 2. Si aspetta che venga lanciata una {@code IndexOutOfBoundsException}.
     * <p>
     * Preconditions: Lista popolata.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code IndexOutOfBoundsException}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetAtIndexSizePopulatedList() {
        list.get(list.size());
    }

    //------- TEST DEL METODO set(int, Object) ----------

    /**
     * Test del metodo {@link HList#set(int, Object)}.
     * <p>
     * Summary: Verifica la modifica dell'elemento all'indice 0 di una lista popolata.
     * <p>
     * Test Case Design: Assicurarsi che l'elemento venga sostituito correttamente e l'elemento precedente sia restituito.
     * <p>
     * Test Description: 1. Imposta un nuovo elemento "zero" all'indice 0.<br />
     * 2. Verifica che l'elemento precedentemente all'indice 0 ("uno") sia restituito.<br />
     * 3. Verifica che l'elemento all'indice 0 sia ora "zero".<br />
     * 4. Verifica che la dimensione non sia cambiata.
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"].
     * <p>
     * Postconditions: Lista: ["zero", "due", "tre", "quattro"].
     * <p>
     * @expected {@code set(0, "zero")} deve restituire "uno".
     */
    @Test
    public void testSetAtIndex0PopulatedList() {
        Object oldElement = list.set(0, "zero");
        assertEquals("uno", oldElement);
        assertEquals("zero", list.get(0));
        assertEquals(4, list.size());
    }

    /**
     * Test del metodo {@link HList#set(int, Object)}.
     * <p>
     * Summary: Verifica la modifica dell'elemento all'ultimo indice (size - 1) di una lista popolata.
     * <p>
     * Test Case Design: Assicurarsi che l'elemento venga sostituito correttamente e l'elemento precedente sia restituito.
     * <p>
     * Test Description: 1. Imposta un nuovo elemento "cinque" all'ultimo indice.<br />
     * 2. Verifica che l'elemento precedentemente all'ultimo indice ("quattro") sia restituito.<br />
     * 3. Verifica che l'elemento all'ultimo indice sia ora "cinque".
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"].
     * <p>
     * Postconditions: Lista: ["uno", "due", "tre", "cinque"].
     * <p>
     * @expected {@code set(3, "cinque")} deve restituire "quattro".
     */
    @Test
    public void testSetAtIndexLastPopulatedList() {
        Object oldElement = list.set(list.size() - 1, "cinque");
        assertEquals("quattro", oldElement);
        assertEquals("cinque", list.get(list.size() - 1));
        assertEquals(4, list.size());
    }

    /**
     * Test del metodo {@link HList#set(int, Object)}.
     * <p>
     * Summary: Verifica la modifica di un elemento a un indice intermedio di una lista popolata.
     * <p>
     * Test Case Design: Assicurarsi che l'elemento venga sostituito correttamente e l'elemento precedente sia restituito.
     * <p>
     * Test Description: 1. Imposta un nuovo elemento "nuovo elemento" all'indice 1.<br />
     * 2. Verifica che l'elemento precedentemente all'indice 1 ("due") sia restituito.<br />
     * 3. Verifica che l'elemento all'indice 1 sia ora "nuovo elemento".
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"].
     * <p>
     * Postconditions: Lista: ["uno", "nuovo elemento", "tre", "quattro"].
     * <p>
     * @expected {@code set(1, "nuovo elemento")} deve restituire "due".
     */
    @Test
    public void testSetAtIndexIntermediatePopulatedList() {
        Object oldElement = list.set(1, "nuovo elemento");
        assertEquals("due", oldElement);
        assertEquals("nuovo elemento", list.get(1));
        assertEquals(4, list.size());
    }

    /**
     * Test del metodo {@link HList#set(int, Object)} con elemento null.
     * <p>
     * Summary: Verifica la modifica di un elemento con null a un indice specifico.
     * <p>
     * Test Case Design: Assicurarsi che `null` possa essere impostato correttamente in una data posizione.
     * <p>
     * Test Description: 1. Imposta null all'indice 2.<br />
     * 2. Verifica che l'elemento precedente ("tre") sia restituito.<br />
     * 3. Verifica che null sia all'indice 2.<br />
     * 4. Verifica che la dimensione non sia cambiata e che la lista contenga null.
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"].
     * <p>
     * Postconditions: Lista: ["uno", "due", null, "quattro"].
     * <p>
     * @expected {@code set(2, null)} deve restituire "tre".
     */
    @Test
    public void testSetNullAtIndexPopulatedList() {
        Object oldElement = list.set(2, null);
        assertEquals("tre", oldElement);
        assertNull(list.get(2));
        assertEquals(4, list.size());
        assertTrue(list.contains(null));
    }

    /**
     * Test del metodo {@link HList#set(int, Object)}.
     * <p>
     * Summary: Verifica che {@code set(int, Object)} lanci {@code IndexOutOfBoundsException} per indice negativo.
     * <p>
     * Test Case Design: Assicurarsi che il metodo rispetti il contratto Javadoc per indici negativi.
     * <p>
     * Test Description: 1. Tenta di impostare un elemento a un indice negativo (-1).<br />
     * 2. Si aspetta che venga lanciata una {@code IndexOutOfBoundsException}.
     * <p>
     * Preconditions: Lista popolata.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code IndexOutOfBoundsException}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSetAtIndexNegativePopulatedList() {
        list.set(-1, "elemento");
    }

    /**
     * Test del metodo {@link HList#set(int, Object)}.
     * <p>
     * Summary: Verifica che {@code set(int, Object)} lanci {@code IndexOutOfBoundsException} per indice uguale o maggiore di size().
     * <p>
     * Test Case Design: Assicurarsi che il metodo rispetti il contratto Javadoc per indici fuori limite superiore.
     * <p>
     * Test Description: 1. Tenta di impostare un elemento all'indice `list.size()` (che è 4).<br />
     * 2. Si aspetta che venga lanciata una {@code IndexOutOfBoundsException}.
     * <p>
     * Preconditions: Lista popolata.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code IndexOutOfBoundsException}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSetAtIndexSizePopulatedList() {
        list.set(list.size(), "elemento");
    }

    //------- TEST DEL METODO indexOf(Object) ----------

    /**
     * Test del metodo {@link HList#indexOf(Object)}.
     * <p>
     * Summary: Verifica che {@code indexOf()} restituisca l'indice della prima occorrenza di un elemento presente.
     * <p>
     * Test Case Design: Assicurarsi che la ricerca dell'indice di un elemento presente sia corretta.
     * <p>
     * Test Description: 1. Cerca l'indice di "due".<br />
     * 2. Verifica che il risultato sia 1.
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"].
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code indexOf("due")} deve restituire 1.
     */
    @Test
    public void testIndexOfExistingElement() {
        assertEquals(1, list.indexOf("due"));
    }

    /**
     * Test del metodo {@link HList#indexOf(Object)}.
     * <p>
     * Summary: Verifica che {@code indexOf()} restituisca -1 per un elemento non presente.
     * <p>
     * Test Case Design: Assicurarsi che la ricerca di un elemento inesistente restituisca -1.
     * <p>
     * Test Description: 1. Cerca l'indice di "cinque".<br />
     * 2. Verifica che il risultato sia -1.
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"].
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code indexOf("cinque")} deve restituire -1.
     */
    @Test
    public void testIndexOfNonExistingElement() {
        assertEquals(-1, list.indexOf("cinque"));
    }

    /**
     * Test del metodo {@link HList#indexOf(Object)}.
     * <p>
     * Summary: Verifica che {@code indexOf(null)} restituisca l'indice corretto se null è presente.
     * <p>
     * Test Case Design: Assicurarsi che la ricerca di null funzioni correttamente.
     * <p>
     * Test Description: 1. Aggiunge null all'indice 2.<br />
     * 2. Cerca l'indice di null.<br />
     * 3. Verifica che il risultato sia 2.
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"], poi null aggiunto.
     * <p>
     * Postconditions: La lista contiene null.
     * <p>
     * @expected {@code indexOf(null)} deve restituire 2.
     */
    @Test
    public void testIndexOfNullPresent() {
        list.add(2, null); // List: ["uno", "due", null, "tre", "quattro"]
        assertEquals(2, list.indexOf(null));
    }

    /**
     * Test del metodo {@link HList#indexOf(Object)}.
     * <p>
     * Summary: Verifica che {@code indexOf(null)} restituisca -1 se null non è presente.
     * <p>
     * Test Case Design: Assicurarsi che la ricerca di null in una lista senza null restituisca -1.
     * <p>
     * Test Description: 1. Cerca l'indice di null.<br />
     * 2. Verifica che il risultato sia -1.
     * <p>
     * Preconditions: Lista popolata senza null.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code indexOf(null)} deve restituire -1.
     */
    @Test
    public void testIndexOfNullNotPresent() {
        assertEquals(-1, list.indexOf(null));
    }

    /**
     * Test del metodo {@link HList#indexOf(Object)}.
     * <p>
     * Summary: Verifica che {@code indexOf()} restituisca la prima occorrenza per elementi duplicati.
     * <p>
     * Test Case Design: Assicurarsi che `indexOf` si comporti come definito (prima occorrenza).
     * <p>
     * Test Description: 1. Aggiunge un duplicato ("due") alla lista.<br />
     * 2. Cerca l'indice di "due".<br />
     * 3. Verifica che il risultato sia l'indice della prima occorrenza (1).
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"], poi "due" aggiunto -> ["uno", "due", "tre", "quattro", "due"].
     * <p>
     * Postconditions: La lista contiene i duplicati.
     * <p>
     * @expected {@code indexOf("due")} deve restituire 1.
     */
    @Test
    public void testIndexOfDuplicates() {
        list.add("due"); // List: ["uno", "due", "tre", "quattro", "due"]
        assertEquals(1, list.indexOf("due"));
    }

    //------- TEST DEL METODO lastIndexOf(Object) ----------

    /**
     * Test del metodo {@link HList#lastIndexOf(Object)}.
     * <p>
     * Summary: Verifica che {@code lastIndexOf()} restituisca l'indice dell'ultima occorrenza di un elemento presente.
     * <p>
     * Test Case Design: Assicurarsi che la ricerca dell'ultimo indice di un elemento presente sia corretta.
     * <p>
     * Test Description: 1. Cerca l'ultimo indice di "due".<br />
     * 2. Verifica che il risultato sia l'indice dell'ultima occorrenza.
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"], poi "due" aggiunto -> ["uno", "due", "tre", "quattro", "due"].
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code lastIndexOf("due")} deve restituire 4.
     */
    @Test
    public void testLastIndexOfExistingElement() {
        list.add("due"); // List: ["uno", "due", "tre", "quattro", "due"]
        assertEquals(4, list.lastIndexOf("due"));
    }

    /**
     * Test del metodo {@link HList#lastIndexOf(Object)}.
     * <p>
     * Summary: Verifica che {@code lastIndexOf()} restituisca -1 per un elemento non presente.
     * <p>
     * Test Case Design: Assicurarsi che la ricerca dell'ultimo indice di un elemento inesistente restituisca -1.
     * <p>
     * Test Description: 1. Cerca l'ultimo indice di "cinque".<br />
     * 2. Verifica che il risultato sia -1.
     * <p>
     * Preconditions: Lista popolata con 4 elementi.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code lastIndexOf("cinque")} deve restituire -1.
     */
    @Test
    public void testLastIndexOfNonExistingElement() {
        assertEquals(-1, list.lastIndexOf("cinque"));
    }

    /**
     * Test del metodo {@link HList#lastIndexOf(Object)}.
     * <p>
     * Summary: Verifica che {@code lastIndexOf(null)} restituisca l'indice corretto se null è presente.
     * <p>
     * Test Case Design: Assicurarsi che la ricerca dell'ultima occorrenza di null funzioni correttamente.
     * <p>
     * Test Description: 1. Aggiunge null a due posizioni diverse.<br />
     * 2. Cerca l'ultimo indice di null.<br />
     * 3. Verifica che il risultato sia l'indice dell'ultima occorrenza di null.
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"], poi null a indice 1 e 4 -> ["uno", null, "due", "tre", "quattro", null].
     * <p>
     * Postconditions: La lista contiene null in più posizioni.
     * <p>
     * @expected {@code lastIndexOf(null)} deve restituire 5.
     */
    @Test
    public void testLastIndexOfNullPresent() {
        list.add(1, null); // ["uno", null, "due", "tre", "quattro"]
        list.add(null);    // ["uno", null, "due", "tre", "quattro", null]
        assertEquals(5, list.lastIndexOf(null));
    }

    /**
     * Test del metodo {@link HList#lastIndexOf(Object)}.
     * <p>
     * Summary: Verifica che {@code lastIndexOf(null)} restituisca -1 se null non è presente.
     * <p>
     * Test Case Design: Assicurarsi che la ricerca dell'ultima occorrenza di null in una lista senza null restituisca -1.
     * <p>
     * Test Description: 1. Cerca l'ultimo indice di null.<br />
     * 2. Verifica che il risultato sia -1.
     * <p>
     * Preconditions: Lista popolata senza null.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code lastIndexOf(null)} deve restituire -1.
     */
    @Test
    public void testLastIndexOfNullNotPresent() {
        assertEquals(-1, list.lastIndexOf(null));
    }

    //------- TEST DEL METODO retainAll(HCollection) ----------

    /**
     * Test del metodo {@link HList#retainAll(HCollection)}.
     * <p>
     * Summary: Verifica che {@code retainAll(HCollection)} mantenga solo gli elementi specificati.
     * <p>
     * Test Case Design: Assicurarsi che gli elementi non presenti nella collezione fornita vengano rimossi.
     * <p>
     * Test Description: 1. Crea una collezione con elementi ("due", "quattro").<br />
     * 2. Chiama {@code retainAll()} sulla lista.<br />
     * 3. Verifica che il risultato sia true.<br />
     * 4. Verifica che la dimensione sia 2.<br />
     * 5. Verifica che solo "due" e "quattro" siano rimasti nella lista.
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"]. Collezione: ["due", "quattro"].
     * <p>
     * Postconditions: Lista: ["due", "quattro"].
     * <p>
     * @expected {@code retainAll()} deve restituire true.
     */
    @Test
    public void testRetainAllCollection()
    {
        ListAdapter collectionToRetain = new ListAdapter();
        collectionToRetain.add("due");
        collectionToRetain.add("quattro");

        assertTrue(list.retainAll(collectionToRetain));
        assertEquals(2, list.size());
        assertTrue(list.contains("due"));
        assertTrue(list.contains("quattro"));
        assertFalse(list.contains("uno"));
        assertFalse(list.contains("tre"));
    }

    /**
     * Test del metodo {@link HList#retainAll(HCollection)}.
     * <p>
     * Summary: Verifica che {@code retainAll(HCollection)} non modifichi la lista se tutti gli elementi sono da mantenere.
     * <p>
     * Test Case Design: Assicurarsi che il metodo restituisca false (nessuna modifica) se tutti gli elementi sono già presenti.
     * <p>
     * Test Description: 1. Crea una collezione con tutti gli elementi della lista.<br />
     * 2. Chiama {@code retainAll()} sulla lista.<br />
     * 3. Verifica che il risultato sia false.<br />
     * 4. Verifica che la dimensione e il contenuto della lista non siano cambiati.
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"]. Collezione: ["uno", "due", "tre", "quattro"].
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code retainAll()} deve restituire false.
     */
    @Test
    public void testRetainAllNoModification()
    {
        ListAdapter collectionToRetain = new ListAdapter();
        collectionToRetain.add("uno");
        collectionToRetain.add("due");
        collectionToRetain.add("tre");
        collectionToRetain.add("quattro");

        assertFalse(list.retainAll(collectionToRetain));
        assertEquals(4, list.size());
        assertTrue(list.contains("uno"));
        assertTrue(list.contains("due"));
        assertTrue(list.contains("tre"));
        assertTrue(list.contains("quattro"));
    }

    /**
     * Test del metodo {@link HList#retainAll(HCollection)}.
     * <p>
     * Summary: Verifica che {@code retainAll(HCollection)} svuoti la lista se nessun elemento deve essere mantenuto.
     * <p>
     * Test Case Design: Assicurarsi che la lista diventi vuota se la collezione fornita non ha elementi in comune.
     * <p>
     * Test Description: 1. Crea una collezione con elementi non presenti nella lista.<br />
     * 2. Chiama {@code retainAll()} sulla lista.<br />
     * 3. Verifica che il risultato sia true.<br />
     * 4. Verifica che la lista sia vuota.
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"]. Collezione: ["cinque", "sei"].
     * <p>
     * Postconditions: La lista è vuota.
     * <p>
     * @expected {@code retainAll()} deve restituire true.
     */
    @Test
    public void testRetainAllClearList()
    {
        ListAdapter collectionToRetain = new ListAdapter();
        collectionToRetain.add("cinque");
        collectionToRetain.add("sei");

        assertTrue(list.retainAll(collectionToRetain));
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    /**
     * Test del metodo {@link HList#retainAll(HCollection)}.
     * <p>
     * Summary: Verifica che {@code retainAll(HCollection)} lanci {@code NullPointerException} se la collezione è null.
     * <p>
     * Test Case Design: Assicurarsi che il metodo gestisca correttamente l'input null per la collezione.
     * <p>
     * Test Description: 1. Chiama {@code retainAll(null)}.<br />
     * 2. Verifica che venga lanciata {@code NullPointerException}.
     * <p>
     * Preconditions: Lista popolata. Collezione null.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code NullPointerException}.
     */
    @Test(expected = NullPointerException.class)
    public void testRetainAllNullCollection()
    {
        list.retainAll(null);
    }

    /**
     * Test del metodo {@link HList#retainAll(HCollection)}.
     * <p>
     * Summary: Verifica che {@code retainAll(HCollection)} con una collezione vuota svuoti la lista.
     * <p>
     * Test Case Design: Assicurarsi che il comportamento di `retainAll` con una collezione vuota sia di svuotare la lista.
     * <p>
     * Test Description: 1. Crea una collezione vuota.<br />
     * 2. Chiama {@code retainAll()} sulla lista popolata.<br />
     * 3. Verifica che il risultato sia true.<br />
     * 4. Verifica che la lista sia vuota.
     * <p>
     * Preconditions: Lista popolata. Collezione vuota.
     * <p>
     * Postconditions: La lista è vuota.
     * <p>
     * @expected {@code retainAll()} deve restituire true.
     */
    @Test
    public void testRetainAllEmptyCollection()
    {
        HCollection emptyCollection = new ListAdapter();
        assertTrue(list.retainAll(emptyCollection));
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    //------- TEST DEL METODO removeAll(HCollection) ----------

    /**
     * Test del metodo {@link HList#removeAll(HCollection)}.
     * <p>
     * Summary: Verifica la rimozione di un sottoinsieme di elementi dalla lista.
     * <p>
     * Test Case Design: Assicurarsi che tutti gli elementi della collezione fornita siano rimossi dalla lista.
     * <p>
     * Test Description: 1. Crea una collezione con elementi ("due", "quattro").<br />
     * 2. Chiama {@code removeAll()} sulla lista.<br />
     * 3. Verifica che il risultato sia true.<br />
     * 4. Verifica che la dimensione sia 2.<br />
     * 5. Verifica che solo "uno" e "tre" siano rimasti nella lista.
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"]. Collezione: ["due", "quattro"].
     * <p>
     * Postconditions: Lista: ["uno", "tre"].
     * <p>
     * @expected {@code removeAll()} deve restituire true.
     */
    @Test
    public void testRemoveAllCollection()
    {
        ListAdapter collectionToRemove = new ListAdapter();
        collectionToRemove.add("due");
        collectionToRemove.add("quattro");

        assertTrue(list.removeAll(collectionToRemove));
        assertEquals(2, list.size());
        assertTrue(list.contains("uno"));
        assertTrue(list.contains("tre"));
        assertFalse(list.contains("due"));
        assertFalse(list.contains("quattro"));
    }

    /**
     * Test del metodo {@link HList#removeAll(HCollection)}.
     * <p>
     * Summary: Verifica che {@code removeAll(HCollection)} non modifichi la lista se nessun elemento è da rimuovere.
     * <p>
     * Test Case Design: Assicurarsi che il metodo restituisca false se la lista non contiene nessuno degli elementi della collezione.
     * <p>
     * Test Description: 1. Crea una collezione con elementi non presenti nella lista.<br />
     * 2. Chiama {@code removeAll()} sulla lista.<br />
     * 3. Verifica che il risultato sia false.<br />
     * 4. Verifica che la dimensione e il contenuto della lista non siano cambiati.
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"]. Collezione: ["cinque", "sei"].
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code removeAll()} deve restituire false.
     */
    @Test
    public void testRemoveAllNoMatchingElements()
    {
        ListAdapter collectionToRemove = new ListAdapter();
        collectionToRemove.add("cinque");
        collectionToRemove.add("sei");

        assertFalse(list.removeAll(collectionToRemove));
        assertEquals(4, list.size());
        assertTrue(list.contains("uno")); // Verifica che gli elementi originali siano ancora lì
    }

    /**
     * Test del metodo {@link HList#removeAll(HCollection)}.
     * <p>
     * Summary: Verifica che {@code removeAll(HCollection)} lanci {@code NullPointerException} se la collezione è null.
     * <p>
     * Test Case Design: Assicurarsi che il metodo gestisca correttamente l'input null per la collezione.
     * <p>
     * Test Description: 1. Chiama {@code removeAll(null)}.<br />
     * 2. Verifica che venga lanciata {@code NullPointerException}.
     * <p>
     * Preconditions: Lista popolata. Collezione null.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code NullPointerException}.
     */
    @Test(expected = NullPointerException.class)
    public void testRemoveAllNullCollection()
    {
        list.removeAll(null);
    }

    /**
     * Test del metodo {@link HList#removeAll(HCollection)}.
     * <p>
     * Summary: Verifica la rimozione di tutti gli elementi dalla lista.
     * <p>
     * Test Case Design: Assicurarsi che `removeAll` con una collezione contenente tutti gli elementi della lista svuoti la lista.
     * <p>
     * Test Description: 1. Crea una collezione con tutti gli elementi della lista.<br />
     * 2. Chiama {@code removeAll()} sulla lista.<br />
     * 3. Verifica che il risultato sia true.<br />
     * 4. Verifica che la lista sia vuota.
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"]. Collezione: ["uno", "due", "tre", "quattro"].
     * <p>
     * Postconditions: La lista è vuota.
     * <p>
     * @expected {@code removeAll()} deve restituire true.
     */
    @Test
    public void testRemoveAllClearList() {
        ListAdapter collectionToRemove = new ListAdapter();
        collectionToRemove.add("uno");
        collectionToRemove.add("due");
        collectionToRemove.add("tre");
        collectionToRemove.add("quattro");

        assertTrue(list.removeAll(collectionToRemove));
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    /**
     * Test del metodo {@link HList#removeAll(HCollection)}.
     * <p>
     * Summary: Verifica che {@code removeAll(HCollection)} con una collezione vuota non modifichi la lista.
     * <p>
     * Test Case Design: Assicurarsi che rimuovere una collezione vuota non alteri la lista.
     * <p>
     * Test Description: 1. Crea una collezione vuota.<br />
     * 2. Chiama {@code removeAll()} sulla lista popolata.<br />
     * 3. Verifica che il risultato sia false.<br />
     * 4. Verifica che la dimensione e il contenuto della lista non siano cambiati.
     * <p>
     * Preconditions: Lista popolata. Collezione vuota.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code removeAll()} deve restituire false.
     */
    @Test
    public void testRemoveAllEmptyCollection()
    {
        HCollection emptyCollection = new ListAdapter();
        assertFalse(list.removeAll(emptyCollection));
        assertEquals(4, list.size());
        assertEquals("uno", list.get(0)); // Verify contents unchanged
    }

    //------- TEST DEL METODO containsAll(HCollection) ----------

    /**
     * Test del metodo {@link HList#containsAll(HCollection)}.
     * <p>
     * Summary: Verifica che {@code containsAll(HCollection)} restituisca true se tutti gli elementi sono presenti.
     * <p>
     * Test Case Design: Assicurarsi che il metodo identifichi correttamente quando una lista contiene tutti gli elementi di una collezione.
     * <p>
     * Test Description: 1. Crea una collezione con un sottoinsieme di elementi della lista.<br />
     * 2. Chiama {@code containsAll()} e verifica che sia true.
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"]. Collezione: ["due", "quattro"].
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code containsAll()} deve restituire true.
     */
    @Test
    public void testContainsAllSubset()
    {
        ListAdapter subset = new ListAdapter();
        subset.add("due");
        subset.add("quattro");
        assertTrue(list.containsAll(subset));
    }

    /**
     * Test del metodo {@link HList#containsAll(HCollection)}.
     * <p>
     * Summary: Verifica che {@code containsAll(HCollection)} restituisca false se alcuni elementi sono assenti.
     * <p>
     * Test Case Design: Assicurarsi che il metodo identifichi correttamente quando alcuni elementi della collezione non sono nella lista.
     * <p>
     * Test Description: 1. Crea una collezione con alcuni elementi presenti e altri assenti ("due", "cinque").<br />
     * 2. Chiama {@code containsAll()} e verifica che sia false.
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"]. Collezione: ["due", "cinque"].
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code containsAll()} deve restituire false.
     */
    @Test
    public void testContainsAllPartialSubset()
    {
        ListAdapter partialSubset = new ListAdapter();
        partialSubset.add("due");
        partialSubset.add("cinque"); // non presente
        assertFalse(list.containsAll(partialSubset));
    }

    /**
     * Test del metodo {@link HList#containsAll(HCollection)}.
     * <p>
     * Summary: Verifica che {@code containsAll(HCollection)} restituisca false se nessun elemento è presente.
     * <p>
     * Test Case Design: Assicurarsi che il metodo identifichi correttamente quando nessun elemento della collezione è nella lista.
     * <p>
     * Test Description: 1. Crea una collezione con elementi non presenti nella lista ("cinque", "sei").<br />
     * 2. Chiama {@code containsAll()} e verifica che sia false.
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"]. Collezione: ["cinque", "sei"].
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code containsAll()} deve restituire false.
     */
    @Test
    public void testContainsAllNoMatch()
    {
        ListAdapter noMatch = new ListAdapter();
        noMatch.add("cinque");
        noMatch.add("sei");
        assertFalse(list.containsAll(noMatch));
    }

    /**
     * Test del metodo {@link HList#containsAll(HCollection)}.
     * <p>
     * Summary: Verifica che {@code containsAll(HCollection)} restituisca true per una collezione vuota.
     * <p>
     * Test Case Design: Assicurarsi che il contratto di `containsAll` sia rispettato per le collezioni vuote (ogni lista contiene una collezione vuota).
     * <p>
     * Test Description: 1. Crea una collezione vuota.<br />
     * 2. Chiama {@code containsAll()} e verifica che sia true.
     * <p>
     * Preconditions: Lista popolata. Collezione vuota.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code containsAll()} deve restituire true.
     */
    @Test
    public void testContainsAllEmptyCollection()
    {
        HCollection emptyCollection = new ListAdapter();
        assertTrue(list.containsAll(emptyCollection));
    }

    /**
     * Test del metodo {@link HList#containsAll(HCollection)}.
     * <p>
     * Summary: Verifica che {@code containsAll(HCollection)} lanci {@code NullPointerException} se la collezione è null.
     * <p>
     * Test Case Design: Assicurarsi che il metodo gestisca correttamente l'input null per la collezione.
     * <p>
     * Test Description: 1. Chiama {@code containsAll(null)}.<br />
     * 2. Verifica che venga lanciata {@code NullPointerException}.
     * <p>
     * Preconditions: Lista popolata. Collezione null.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code NullPointerException}.
     */
    @Test(expected = NullPointerException.class)
    public void testContainsAllNullCollection()
    {
        list.containsAll(null);
    }

    //------- TEST DEL METODO hashCode() ----------

    /**
     * Test del metodo {@link HList#hashCode()}.
     * <p>
     * Summary: Verifica che l'hashCode di due liste popolata identiche sia lo stesso.
     * <p>
     * Test Case Design: Assicurarsi che l'hashCode sia calcolato in modo coerente per liste con gli stessi elementi e ordine.
     * <p>
     * Test Description: 1. Crea una seconda lista identica alla prima.<br />
     * 2. Calcola e confronta gli hashCode di entrambe le liste.
     * <p>
     * Preconditions: Due liste popolate identicamente.
     * <p>
     * Postconditions: Le liste rimangono invariate.
     * <p>
     * @expected Gli hashCode devono essere uguali.
     */
    @Test
    public void testHashCodePopulatedListConsistent()
    {
        HList otherList = new ListAdapter();
        otherList.add("uno");
        otherList.add("due");
        otherList.add("tre");
        otherList.add("quattro");
        assertEquals(list.hashCode(), otherList.hashCode());
    }

    /**
     * Test del metodo {@link HList#hashCode()}.
     * <p>
     * Summary: Verifica che l'hashCode di due liste popolata diverse sia diverso.
     * <p>
     * Test Case Design: Assicurarsi che l'hashCode cambi per liste con elementi o ordine diversi.
     * <p>
     * Test Description: 1. Crea una seconda lista diversa dalla prima.<br />
     * 2. Calcola e confronta gli hashCode di entrambe le liste.
     * <p>
     * Preconditions: Due liste popolate in modo diverso.
     * <p>
     * Postconditions: Le liste rimangono invariate.
     * <p>
     * @expected Gli hashCode devono essere diversi.
     */
    @Test
    public void testHashCodePopulatedListDifferent() {
        HList differentList = new ListAdapter();
        differentList.add("uno");
        differentList.add("cinque"); // Elemento diverso
        differentList.add("tre");
        differentList.add("quattro");
        assertNotEquals(list.hashCode(), differentList.hashCode());
    }

    //------- TEST DEL METODO equals(Object) ----------

    /**
     * Test del metodo {@link HList#equals(Object)}.
     * <p>
     * Summary: Verifica che una lista popolata sia uguale a se stessa.
     * <p>
     * Test Case Design: Assicurarsi che la riflessività sia rispettata.
     * <p>
     * Test Description: 1. Confronta la lista con se stessa.
     * <p>
     * Preconditions: Lista popolata.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code equals(list)} deve restituire true.
     */
    @Test
    public void testEqualsPopulatedListSelf() {
        assertTrue(list.equals(list));
    }

    /**
     * Test del metodo {@link HList#equals(Object)}.
     * <p>
     * Summary: Verifica che una lista popolata sia uguale a un'altra lista popolata identicamente.
     * <p>
     * Test Case Design: Assicurarsi che la simmetria e la transitività siano rispettate per liste identiche.
     * <p>
     * Test Description: 1. Crea un'altra lista con gli stessi elementi e nello stesso ordine.<br />
     * 2. Confronta la lista originale con la nuova lista.
     * <p>
     * Preconditions: Due liste popolate identicamente.
     * <p>
     * Postconditions: Le liste rimangono invariate.
     * <p>
     * @expected {@code equals(otherList)} deve restituire true.
     */
    @Test
    public void testEqualsPopulatedListIdentical() {
        HList otherList = new ListAdapter();
        otherList.add("uno");
        otherList.add("due");
        otherList.add("tre");
        otherList.add("quattro");
        assertTrue(list.equals(otherList));
    }

    /**
     * Test del metodo {@link HList#equals(Object)}.
     * <p>
     * Summary: Verifica che una lista popolata non sia uguale a null.
     * <p>
     * Test Case Design: Assicurarsi che il confronto con null restituisca false.
     * <p>
     * Test Description: 1. Confronta la lista con null.
     * <p>
     * Preconditions: Lista popolata.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code equals(null)} deve restituire false.
     */
    @Test
    public void testEqualsPopulatedListNull() {
        assertFalse(list.equals(null));
    }

    /**
     * Test del metodo {@link HList#equals(Object)}.
     * <p>
     * Summary: Verifica che una lista popolata non sia uguale a un oggetto di tipo diverso.
     * <p>
     * Test Case Design: Assicurarsi che il confronto con tipi incompatibili restituisca false.
     * <p>
     * Test Description: 1. Confronta la lista con un'istanza di `Object`.
     * <p>
     * Preconditions: Lista popolata.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * @expected {@code equals(new Object())} deve restituire false.
     */
    @Test
    public void testEqualsPopulatedListDifferentType() {
        assertFalse(list.equals(new Object()));
    }

    /**
     * Test del metodo {@link HList#equals(Object)}.
     * <p>
     * Summary: Verifica che due liste con stessi elementi ma ordine diverso non siano uguali.
     * <p>
     * Test Case Design: Assicurarsi che l'ordine degli elementi sia rilevante per l'uguaglianza.
     * <p>
     * Test Description: 1. Crea una lista con gli stessi elementi ma in ordine diverso.<br />
     * 2. Confronta la lista originale con la nuova lista.
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"]. Altra lista: ["quattro", "tre", "due", "uno"].
     * <p>
     * Postconditions: Le liste rimangono invariate.
     * <p>
     * @expected {@code equals(otherList)} deve restituire false.
     */
    @Test
    public void testEqualsPopulatedListDifferentOrder() {
        HList otherList = new ListAdapter();
        otherList.add("quattro");
        otherList.add("tre");
        otherList.add("due");
        otherList.add("uno");
        assertFalse(list.equals(otherList));
    }

    /**
     * Test del metodo {@link HList#equals(Object)}.
     * <p>
     * Summary: Verifica che due liste con elementi diversi non siano uguali.
     * <p>
     * Test Case Design: Assicurarsi che il confronto rilevi differenze negli elementi.
     * <p>
     * Test Description: 1. Crea una lista con alcuni elementi diversi.<br />
     * 2. Confronta la lista originale con la nuova lista.
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"]. Altra lista: ["uno", "cinque", "tre", "sei"].
     * <p>
     * Postconditions: Le liste rimangono invariate.
     * <p>
     * @expected {@code equals(otherList)} deve restituire false.
     */
    @Test
    public void testEqualsPopulatedListDifferentElements() {
        HList otherList = new ListAdapter();
        otherList.add("uno");
        otherList.add("cinque"); // Elemento diverso
        otherList.add("tre");
        otherList.add("sei");    // Elemento diverso
        assertFalse(list.equals(otherList));
    }

    /**
     * Test del metodo {@link HList#equals(Object)}.
     * <p>
     * Summary: Verifica che una lista popolata non sia uguale a una lista di dimensione diversa.
     * <p>
     * Test Case Design: Assicurarsi che il confronto rilevi differenze di dimensione.
     * <p>
     * Test Description: 1. Crea una lista con dimensione maggiore.<br />
     * 2. Confronta la lista originale con la nuova lista.
     * <p>
     * Preconditions: Lista: ["uno", "due", "tre", "quattro"]. Altra lista: ["uno", "due", "tre", "quattro", "cinque"].
     * <p>
     * Postconditions: Le liste rimangono invariate.
     * <p>
     * @expected {@code equals(otherList)} deve restituire false.
     */
    @Test
    public void testEqualsPopulatedListDifferentSize() {
        HList otherList = new ListAdapter();
        otherList.add("uno");
        otherList.add("due");
        otherList.add("tre");
        otherList.add("quattro");
        otherList.add("cinque"); // Dimensione diversa
        assertFalse(list.equals(otherList));
    }

    /**
     * Test del metodo {@link HList#equals(Object)}.
     * <p>
     * Summary: Verifica che una lista con elementi null sia uguale a un'altra lista con null identicamente posizionati.
     * <p>
     * Test Case Design: Assicurarsi che il confronto gestisca correttamente gli elementi null.
     * <p>
     * Test Description: 1. Aggiunge null a entrambe le liste in posizioni corrispondenti.<br />
     * 2. Confronta le due liste.
     * <p>
     * Preconditions: Entrambe le liste: ["uno", null, "due", "tre", "quattro"].
     * <p>
     * Postconditions: Le liste rimangono invariate.
     * <p>
     * @expected {@code equals(otherList)} deve restituire true.
     */
    @Test
    public void testEqualsWithNullElements() {
        list.add(1, null); // list: ["uno", null, "due", "tre", "quattro"]
        HList otherList = new ListAdapter();
        otherList.add("uno");
        otherList.add(null); // Corrispondente null
        otherList.add("due");
        otherList.add("tre");
        otherList.add("quattro");
        assertTrue(list.equals(otherList));
    }

    // Nota: I test per listIterator(int) sono stati inclusi nel file iniziale e sono mantenuti.
    // Gli altri test specifici per ListIterator sono in TestListIteratorPopulated.java
    // e quelli per SubList in TestSubListAdapter.java, come da tua organizzazione.

}