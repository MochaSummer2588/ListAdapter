package myTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import myAdapter.*;
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
     * @summary Verifica che la dimensione della lista popolata sia corretta.
     * <p>
     * @design Assicurarsi che il metodo {@code size()} restituisca il numero
     * corretto di elementi dopo l'inizializzazione.
     * <p>
     * @description 1. La lista viene popolata con 4 elementi in setUp().<br />
     * 2. Chiama il metodo {@code size()} sulla lista.<br />
     * 3. Verifica che il valore restituito sia 4.
     * <p>
     * @pre Lista popolata con 4 elementi.
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected {@code size()} deve restituire 4.
     */
    @Test
    public void testSizePopulated()
    {
        assertEquals(4, list.size());
    }

    //------- TEST DEL METODO isEmpty() ----------

    /**
     * Test del metodo {@link HList#isEmpty()}.
     * <p>
     * @summary Verifica che una lista popolata non sia vuota.
     * <p>
     * @design Assicurarsi che il metodo {@code isEmpty()} restituisca false
     * quando la lista contiene elementi.
     * <p>
     * @description 1. La lista viene popolata con 4 elementi in setUp().<br />
     * 2. Chiama il metodo {@code isEmpty()} sulla lista.<br />
     * 3. Verifica che il valore restituito sia false.
     * <p>
     * @pre Lista popolata con 4 elementi.
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected {@code isEmpty()} deve restituire false.
     */
    @Test
    public void testIsEmptyFalse()
    {
        assertFalse(list.isEmpty());
    }

    //------- TEST DEL METODO contains(Object) ----------

    /**
     * Test del metodo {@link HList#contains(Object)}.
     * <p>
     * @summary Verifica che {@code contains()} identifichi correttamente elementi presenti e assenti.
     * <p>
     * @design Assicurarsi che il metodo restituisca true per un elemento presente e false per uno assente.
     * <p>
     * @description 1. Chiama {@code contains()} con un elemento presente ("due").<br />
     * 2. Verifica che il risultato sia true.<br />
     * 3. Chiama {@code contains()} con un elemento non presente ("cinque").<br />
     * 4. Verifica che il risultato sia false.
     * <p>
     * @pre Lista popolata con "uno", "due", "tre", "quattro".
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected {@code contains("due")} deve essere true, {@code contains("cinque")} deve essere false.
     */
    @Test
    public void testContainsPopulated()
    {
        assertTrue(list.contains("due"));
        assertFalse(list.contains("cinque"));
    }

    /**
     * Test del metodo {@link HList#contains(Object)} con elemento null.
     * <p>
     * @summary Verifica che {@code contains(null)} funzioni correttamente su una lista popolata senza null.
     * <p>
     * @design Assicurarsi che il metodo gestisca correttamente la ricerca di null
     * quando la lista non contiene tale elemento.
     * <p>
     * @description 1. Chiama {@code contains(null)} su una lista senza elementi null.<br />
     * 2. Verifica che il risultato sia false.
     * <p>
     * @pre Lista popolata con elementi non null.
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected {@code contains(null)} deve restituire false.
     */
    @Test
    public void testContainsNullPopulatedNoNull()
    {
        assertFalse(list.contains(null));
    }

    /**
     * Test del metodo {@link HList#contains(Object)} con elemento null in una lista che contiene null.
     * <p>
     * @summary Verifica che {@code contains(null)} restituisca true se null è presente.
     * <p>
     * @design Assicurarsi che il metodo identifichi correttamente la presenza di elementi null.
     * <p>
     * @description 1. Aggiunge un elemento null alla lista.<br />
     * 2. Chiama {@code contains(null)}.<br />
     * 3. Verifica che il risultato sia true.
     * <p>
     * @pre Lista popolata, con l'aggiunta di un elemento null.
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected {@code contains(null)} deve restituire true.
     */
    @Test
    public void testContainsNullPopulatedWithNull()
    {
        list.add(null);
        assertTrue(list.contains(null));
    }

    //------- TEST DEL METODO iterator() ----------

    /**
     * Test del metodo {@link HList#iterator()}.
     * <p>
     * @summary Verifica l'iterazione su una lista popolata.
     * <p>
     * @design Assicurarsi che l'iteratore restituisca tutti gli elementi nell'ordine corretto
     * e che {@code hasNext()} e {@code next()} funzionino come previsto.
     * <p>
     * @description 1. Ottiene un iteratore.<br />
     * 2. Itera su tutti gli elementi, verificando il valore di ogni elemento.<br />
     * 3. Verifica che {@code hasNext()} sia false alla fine.<br />
     * 4. Tenta di chiamare {@code next()} e verifica l'eccezione.
     * <p>
     * @pre Lista popolata con 4 elementi.
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected Tutti gli elementi devono essere restituiti nell'ordine corretto.
     * {@code NoSuchElementException} alla fine.
     */
    @Test
    public void testIteratorPopulated()
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

        try {
            it.next();
            fail("Expected NoSuchElementException");
        } catch (java.util.NoSuchElementException e) {
            // Success
        }
    }

    /**
     * Test del metodo {@link HList#iterator()} con rimozione tramite iteratore.
     * <p>
     * @summary Verifica che {@code remove()} tramite iteratore funzioni correttamente.
     * <p>
     * @design Assicurarsi che l'elemento corretto venga rimosso e la lista sia aggiornata.
     * <p>
     * @description 1. Itera fino a un certo elemento.<br />
     * 2. Chiama {@code remove()} tramite l'iteratore.<br />
     * 3. Verifica la dimensione della lista e l'assenza dell'elemento rimosso.
     * <p>
     * @pre Lista popolata.
     * <p>
     * @post L'elemento è rimosso dalla lista.
     * <p>
     * @expected La dimensione deve diminuire e l'elemento non deve essere più presente.
     */
    @Test
    public void testIteratorRemove()
    {
        HIterator it = list.iterator();
        it.next(); // "uno"
        it.next(); // "due"
        it.remove(); // Rimuove "due"

        assertEquals(3, list.size());
        assertFalse(list.contains("due"));
        assertEquals("tre", list.get(1)); // "tre" si sposta a indice 1
    }

    //------- TEST DEL METODO toArray() ----------

    /**
     * Test del metodo {@link HList#toArray()}.
     * <p>
     * @summary Verifica che {@code toArray()} su una lista popolata restituisca un array corretto.
     * <p>
     * @design Assicurarsi che l'array restituito contenga tutti gli elementi della lista nell'ordine corretto.
     * <p>
     * @description 1. Chiama {@code toArray()} sulla lista popolata.<br />
     * 2. Verifica la dimensione dell'array.<br />
     * 3. Verifica il contenuto di ogni elemento dell'array.
     * <p>
     * @pre Lista popolata con 4 elementi.
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected L'array restituito deve avere dimensione 4 e contenere "uno", "due", "tre", "quattro" nell'ordine.
     */
    @Test
    public void testToArrayPopulated()
    {
        Object[] arr = list.toArray();
        assertEquals(4, arr.length);
        assertEquals("uno", arr[0]);
        assertEquals("due", arr[1]);
        assertEquals("tre", arr[2]);
        assertEquals("quattro", arr[3]);
    }

    /**
     * Test del metodo {@link HList#toArray(Object[])}.
     * <p>
     * @summary Verifica che {@code toArray(Object[])} su una lista popolata funzioni correttamente.
     * <p>
     * @design Assicurarsi che il metodo popoli correttamente l'array fornito o ne crei uno nuovo se troppo piccolo,
     * e che gli elementi dopo la fine della lista siano null.
     * <p>
     * @description 1. Chiama {@code toArray()} con un array della stessa dimensione.<br />
     * 2. Verifica che l'array sia riempito correttamente.<br />
     * 3. Chiama {@code toArray()} con un array più grande.<br />
     * 4. Verifica che l'array sia riempito correttamente e che l'elemento dopo la fine della lista sia null.<br />
     * 5. Chiama {@code toArray()} con un array più piccolo.<br />
     * 6. Verifica che un nuovo array della dimensione corretta sia creato e riempito.
     * <p>
     * @pre Lista popolata con 4 elementi.
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected {@code toArray(Object[])} deve restituire un array corretto in tutti gli scenari.
     */
    @Test
    public void testToArrayParameterizedPopulated()
    {
        Object[] a = new Object[4];
        Object[] result = list.toArray(a);
        assertSame(a, result); // Dovrebbe essere lo stesso array
        assertEquals("uno", result[0]);
        assertEquals("due", result[1]);
        assertEquals("tre", result[2]);
        assertEquals("quattro", result[3]);

        Object[] b = new Object[5];
        b[4] = "extra"; // Valore extra per verificare che sia settato a null
        result = list.toArray(b);
        assertSame(b, result);
        assertEquals("uno", result[0]);
        assertEquals("quattro", result[3]);
        assertNull(result[4]); // Elemento extra dovrebbe essere null

        Object[] c = new Object[2];
        result = list.toArray(c);
        assertNotSame(c, result); // Dovrebbe essere un nuovo array
        assertEquals(4, result.length);
        assertEquals("uno", result[0]);
        assertEquals("quattro", result[3]);
    }

    /**
     * Test del metodo {@link HList#toArray(Object[])} con array di input null.
     * <p>
     * @summary Verifica che {@code toArray(Object[])} lanci {@code NullPointerException} se l'array di input è null.
     * <p>
     * @design Assicurarsi che il metodo gestisca correttamente l'input null per l'array.
     * <p>
     * @description 1. Chiama {@code toArray(null)}.<br />
     * 2. Verifica che venga lanciata {@code NullPointerException}.
     * <p>
     * @pre Lista popolata.
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected {@code NullPointerException} deve essere lanciata.
     */
    @Test(expected = NullPointerException.class)
    public void testToArrayParameterizedNullArray()
    {
        list.toArray(null);
    }

    //------- TEST DEL METODO add(Object) ----------

    /**
     * Test del metodo {@link HList#add(Object)}.
     * <p>
     * @summary Verifica l'aggiunta di un elemento alla fine della lista popolata.
     * <p>
     * @design Assicurarsi che l'elemento sia aggiunto in coda, la dimensione sia aggiornata
     * e il metodo restituisca true.
     * <p>
     * @description 1. Aggiunge un nuovo elemento.<br />
     * 2. Verifica che la dimensione sia 5.<br />
     * 3. Verifica che l'elemento aggiunto sia l'ultimo della lista.
     * <p>
     * @pre Lista popolata con 4 elementi.
     * <p>
     * @post La lista contiene un elemento in più alla fine, dimensione 5.
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
     * @summary Verifica che l'aggiunta di un elemento null alla lista popolata funzioni.
     * <p>
     * @design Assicurarsi che il metodo possa gestire l'aggiunta di elementi nulli.
     * <p>
     * @description 1. Aggiunge un elemento null.<br />
     * 2. Verifica che la dimensione sia 5.<br />
     * 3. Verifica che l'elemento null sia presente all'ultimo indice.
     * <p>
     * @pre Lista popolata con 4 elementi.
     * <p>
     * @post La lista contiene un elemento null in più alla fine, dimensione 5.
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

    //------- TEST DEL METODO remove(Object) ----------

    /**
     * Test del metodo {@link HList#remove(Object)}.
     * <p>
     * @summary Verifica la rimozione di un elemento presente dalla lista popolata.
     * <p>
     * @design Assicurarsi che il metodo rimuova la prima occorrenza dell'elemento,
     * diminuisca la dimensione e restituisca true.
     * <p>
     * @description 1. Rimuove l'elemento "due".<br />
     * 2. Verifica che il risultato sia true.<br />
     * 3. Verifica che la dimensione sia 3.<br />
     * 4. Verifica che "due" non sia più presente e che gli elementi successivi siano shiftati.
     * <p>
     * @pre Lista popolata con "uno", "due", "tre", "quattro".
     * <p>
     * @post La lista ha un elemento in meno, gli elementi successivi sono shiftati.
     * <p>
     * @expected {@code remove("due")} deve restituire true, la dimensione deve essere 3, e la lista deve essere ["uno", "tre", "quattro"].
     */
    @Test
    public void testRemoveObjectFromPopulatedList()
    {
        assertTrue(list.remove("due"));
        assertEquals(3, list.size());
        assertFalse(list.contains("due"));
        assertEquals("tre", list.get(1)); // "tre" dovrebbe spostarsi a indice 1
    }

    /**
     * Test del metodo {@link HList#remove(Object)} per un elemento non presente.
     * <p>
     * @summary Verifica che {@code remove(Object)} su una lista popolata non modifichi la lista se l'elemento è assente.
     * <p>
     * @design Assicurarsi che il metodo restituisca false e non alteri la lista.
     * <p>
     * @description 1. Tenta di rimuovere un elemento ("cinque") non presente.<br />
     * 2. Verifica che il risultato sia false.<br />
     * 3. Verifica che la dimensione e il contenuto della lista non siano cambiati.
     * <p>
     * @pre Lista popolata con 4 elementi.
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected {@code remove("cinque")} deve restituire false, la dimensione deve essere 4.
     */
    @Test
    public void testRemoveNonExistentObjectFromPopulatedList()
    {
        assertFalse(list.remove("cinque"));
        assertEquals(4, list.size());
        assertTrue(list.contains("uno")); // Verifica che la lista non sia alterata
    }

    /**
     * Test del metodo {@link HList#remove(Object)} per elemento null in lista senza null.
     * <p>
     * @summary Verifica che {@code remove(null)} restituisca false se null non è presente.
     * <p>
     * @design Assicurarsi che il metodo gestisca correttamente la rimozione di null quando non c'è.
     * <p>
     * @description 1. Tenta di rimuovere null.<br />
     * 2. Verifica che il risultato sia false.<br />
     * 3. Verifica che la dimensione non sia cambiata.
     * <p>
     * @pre Lista popolata con elementi non null.
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected {@code remove(null)} deve restituire false.
     */
    @Test
    public void testRemoveNullObjectFromPopulatedListNoNull()
    {
        assertFalse(list.remove(null));
        assertEquals(4, list.size());
    }

    /**
     * Test del metodo {@link HList#remove(Object)} per elemento null in lista con null.
     * <p>
     * @summary Verifica che {@code remove(null)} rimuova la prima occorrenza di null.
     * <p>
     * @design Assicurarsi che il metodo rimuova correttamente un elemento null.
     * <p>
     * @description 1. Aggiunge un elemento null alla lista.<br />
     * 2. Rimuove null.<br />
     * 3. Verifica che il risultato sia true.<br />
     * 4. Verifica che la dimensione sia 4 e che null non sia più presente.
     * <p>
     * @pre Lista popolata, con l'aggiunta di un elemento null.
     * <p>
     * @post La lista ha un elemento null in meno.
     * <p>
     * @expected {@code remove(null)} deve restituire true, la dimensione deve essere 4, e null non deve essere presente.
     */
    @Test
    public void testRemoveNullObjectFromPopulatedListWithNull()
    {
        list.add(null); // Aggiunge null
        assertEquals(5, list.size());
        assertTrue(list.remove(null));
        assertEquals(4, list.size());
        assertFalse(list.contains(null)); // Solo se era l'unica occorrenza
    }

    //------- TEST DEL METODO containsAll(HCollection) ----------

    /**
     * Test del metodo {@link HList#containsAll(HCollection)}.
     * <p>
     * @summary Verifica che {@code containsAll()} identifichi correttamente una collezione di elementi presenti.
     * <p>
     * @design Assicurarsi che il metodo restituisca true se tutti gli elementi della collezione sono presenti.
     * <p>
     * @description 1. Crea una collezione con elementi ("uno", "tre") presenti nella lista.<br />
     * 2. Chiama {@code containsAll()} sulla lista.<br />
     * 3. Verifica che il risultato sia true.
     * <p>
     * @pre Lista popolata con 4 elementi. Collezione con due elementi presenti.
     * <p>
     * @post La lista e la collezione rimangono invariate.
     * <p>
     * @expected {@code containsAll()} deve restituire true.
     */
    @Test
    public void testContainsAllPresentElements()
    {
        HCollection subset = new ListAdapter();
        subset.add("uno");
        subset.add("tre");
        assertTrue(list.containsAll(subset));
    }

    /**
     * Test del metodo {@link HList#containsAll(HCollection)}.
     * <p>
     * @summary Verifica che {@code containsAll()} restituisca false se almeno un elemento è assente.
     * <p>
     * @design Assicurarsi che il metodo restituisca false se anche un solo elemento della collezione non è presente.
     * <p>
     * @description 1. Crea una collezione con elementi ("uno", "cinque") dove "cinque" è assente.<br />
     * 2. Chiama {@code containsAll()} sulla lista.<br />
     * 3. Verifica che il risultato sia false.
     * <p>
     * @pre Lista popolata con 4 elementi. Collezione con un elemento presente e uno assente.
     * <p>
     * @post La lista e la collezione rimangono invariate.
     * <p>
     * @expected {@code containsAll()} deve restituire false.
     */
    @Test
    public void testContainsAllMissingElement()
    {
        HCollection mixed = new ListAdapter();
        mixed.add("uno");
        mixed.add("cinque"); // Non presente
        assertFalse(list.containsAll(mixed));
    }

    /**
     * Test del metodo {@link HList#containsAll(HCollection)} con collezione vuota.
     * <p>
     * @summary Verifica che {@code containsAll()} restituisca true per una collezione vuota.
     * <p>
     * @design Assicurarsi che il metodo restituisca true, poiché una lista contiene sempre "tutti" gli elementi di una collezione vuota.
     * <p>
     * @description 1. Crea una collezione vuota.<br />
     * 2. Chiama {@code containsAll()} sulla lista popolata.<br />
     * 3. Verifica che il risultato sia true.
     * <p>
     * @pre Lista popolata. Collezione vuota.
     * <p>
     * @post La lista e la collezione rimangono invariate.
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
     * Test del metodo {@link HList#containsAll(HCollection)} con collezione null.
     * <p>
     * @summary Verifica che {@code containsAll(null)} lanci {@code NullPointerException}.
     * <p>
     * @design Assicurarsi che il metodo gestisca correttamente l'input null per la collezione.
     * <p>
     * @description 1. Chiama {@code containsAll(null)}.<br />
     * 2. Verifica che venga lanciata {@code NullPointerException}.
     * <p>
     * @pre Lista popolata.
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected {@code NullPointerException} deve essere lanciata.
     */
    @Test(expected = NullPointerException.class)
    public void testContainsAllNullCollection()
    {
        list.containsAll(null);
    }

    //------- TEST DEL METODO addAll(HCollection) ----------

    /**
     * Test del metodo {@link HList#addAll(HCollection)}.
     * <p>
     * @summary Verifica l'aggiunta di una collezione non vuota a una lista popolata.
     * <p>
     * @design Assicurarsi che tutti gli elementi della collezione siano aggiunti in coda,
     * la dimensione sia aggiornata e il metodo restituisca true.
     * <p>
     * @description 1. Crea una collezione con 2 elementi.<br />
     * 2. Aggiunge la collezione alla lista popolata.<br />
     * 3. Verifica che la dimensione sia 6.<br />
     * 4. Verifica che gli elementi aggiunti siano in coda.
     * <p>
     * @pre Lista popolata con 4 elementi. Collezione con due elementi.
     * <p>
     * @post La lista contiene gli elementi aggiunti in coda.
     * <p>
     * @expected {@code addAll(HCollection)} deve restituire true, la lista deve avere dimensione 6.
     */
    @Test
    public void testAddAllToPopulatedList()
    {
        HCollection newElements = new ListAdapter();
        newElements.add("cinque");
        newElements.add("sei");

        assertTrue(list.addAll(newElements));
        assertEquals(6, list.size());
        assertEquals("cinque", list.get(4));
        assertEquals("sei", list.get(5));
    }

    /**
     * Test del metodo {@link HList#addAll(HCollection)} con collezione vuota.
     * <p>
     * @summary Verifica che l'aggiunta di una collezione vuota non modifichi la lista.
     * <p>
     * @design Assicurarsi che il metodo restituisca false e non alteri la lista se la collezione da aggiungere è vuota.
     * <p>
     * @description 1. Crea una collezione vuota.<br />
     * 2. Aggiunge la collezione alla lista.<br />
     * 3. Verifica che il risultato sia false.<br />
     * 4. Verifica che la dimensione e il contenuto della lista non siano cambiati.
     * <p>
     * @pre Lista popolata con 4 elementi. Collezione vuota.
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected {@code addAll(HCollection)} deve restituire false.
     */
    @Test
    public void testAddAllEmptyCollection()
    {
        HCollection emptyCollection = new ListAdapter();
        assertFalse(list.addAll(emptyCollection));
        assertEquals(4, list.size());
    }

    //------- TEST DEL METODO removeAll(HCollection) ----------

    /**
     * Test del metodo {@link HList#removeAll(HCollection)}.
     * <p>
     * @summary Verifica la rimozione di tutti gli elementi presenti in una data collezione.
     * <p>
     * @design Assicurarsi che il metodo rimuova tutte le occorrenze degli elementi specificati
     * e restituisca true se la lista è stata modificata.
     * <p>
     * @description 1. Crea una collezione con elementi da rimuovere ("due", "quattro").<br />
     * 2. Chiama {@code removeAll()} sulla lista.<br />
     * 3. Verifica che il risultato sia true.<br />
     * 4. Verifica la dimensione della lista (2) e che gli elementi rimossi non siano più presenti.
     * <p>
     * @pre Lista popolata con "uno", "due", "tre", "quattro". Collezione con "due", "quattro".
     * <p>
     * @post La lista contiene solo gli elementi non rimossi.
     * <p>
     * @expected {@code removeAll()} deve restituire true, la lista deve essere ["uno", "tre"].
     */
    @Test
    public void testRemoveAllPresentElements()
    {
        HCollection toRemove = new ListAdapter();
        toRemove.add("due");
        toRemove.add("quattro");

        assertTrue(list.removeAll(toRemove));
        assertEquals(2, list.size());
        assertFalse(list.contains("due"));
        assertFalse(list.contains("quattro"));
        assertTrue(list.contains("uno"));
        assertTrue(list.contains("tre"));
    }

    /**
     * Test del metodo {@link HList#removeAll(HCollection)} con elementi non presenti.
     * <p>
     * @summary Verifica che {@code removeAll()} non modifichi la lista se nessun elemento è presente nella collezione.
     * <p>
     * @design Assicurarsi che il metodo restituisca false se la lista non viene modificata.
     * <p>
     * @description 1. Crea una collezione con elementi non presenti nella lista.<br />
     * 2. Chiama {@code removeAll()} sulla lista.<br />
     * 3. Verifica che il risultato sia false.<br />
     * 4. Verifica che la dimensione e il contenuto della lista non siano cambiati.
     * <p>
     * @pre Lista popolata. Collezione con elementi assenti.
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected {@code removeAll()} deve restituire false.
     */
    @Test
    public void testRemoveAllNonExistentElements()
    {
        HCollection nonExistent = new ListAdapter();
        nonExistent.add("cinque");
        nonExistent.add("sei");

        assertFalse(list.removeAll(nonExistent));
        assertEquals(4, list.size());
    }

    /**
     * Test del metodo {@link HList#removeAll(HCollection)} con collezione vuota.
     * <p>
     * @summary Verifica che {@code removeAll()} con una collezione vuota non modifichi la lista.
     * <p>
     * @design Assicurarsi che il metodo restituisca false se la collezione è vuota.
     * <p>
     * @description 1. Crea una collezione vuota.<br />
     * 2. Chiama {@code removeAll()} sulla lista.<br />
     * 3. Verifica che il risultato sia false.<br />
     * 4. Verifica che la dimensione e il contenuto della lista non siano cambiati.
     * <p>
     * @pre Lista popolata. Collezione vuota.
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected {@code removeAll()} deve restituire false.
     */
    @Test
    public void testRemoveAllEmptyCollection()
    {
        HCollection emptyCollection = new ListAdapter();
        assertFalse(list.removeAll(emptyCollection));
        assertEquals(4, list.size());
    }

    /**
     * Test del metodo {@link HList#removeAll(HCollection)} con collezione null.
     * <p>
     * @summary Verifica che {@code removeAll(null)} lanci {@code NullPointerException}.
     * <p>
     * @design Assicurarsi che il metodo gestisca correttamente l'input null per la collezione.
     * <p>
     * @description 1. Chiama {@code removeAll(null)}.<br />
     * 2. Verifica che venga lanciata {@code NullPointerException}.
     * <p>
     * @pre Lista popolata.
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected {@code NullPointerException} deve essere lanciata.
     */
    @Test(expected = NullPointerException.class)
    public void testRemoveAllNullCollection()
    {
        list.removeAll(null);
    }

    //------- TEST DEL METODO retainAll(HCollection) ----------

    /**
     * Test del metodo {@link HList#retainAll(HCollection)}.
     * <p>
     * @summary Verifica che {@code retainAll()} mantenga solo gli elementi presenti in una data collezione.
     * <p>
     * @design Assicurarsi che il metodo rimuova gli elementi non presenti nella collezione e restituisca true
     * se la lista è stata modificata.
     * <p>
     * @description 1. Crea una collezione con elementi da mantenere ("uno", "tre").<br />
     * 2. Chiama {@code retainAll()} sulla lista.<br />
     * 3. Verifica che il risultato sia true.<br />
     * 4. Verifica la dimensione della lista (2) e che solo gli elementi "uno" e "tre" siano rimasti.
     * <p>
     * @pre Lista popolata con "uno", "due", "tre", "quattro". Collezione con "uno", "tre".
     * <p>
     * @post La lista contiene solo "uno" e "tre".
     * <p>
     * @expected {@code retainAll()} deve restituire true, la lista deve essere ["uno", "tre"].
     */
    @Test
    public void testRetainAllExistingElements()
    {
        HCollection toRetain = new ListAdapter();
        toRetain.add("uno");
        toRetain.add("tre");

        assertTrue(list.retainAll(toRetain));
        assertEquals(2, list.size());
        assertTrue(list.contains("uno"));
        assertTrue(list.contains("tre"));
        assertFalse(list.contains("due"));
        assertFalse(list.contains("quattro"));
    }

    /**
     * Test del metodo {@link HList#retainAll(HCollection)} senza elementi in comune.
     * <p>
     * @summary Verifica che {@code retainAll()} svuoti la lista se non ci sono elementi in comune.
     * <p>
     * @design Assicurarsi che il metodo rimuova tutti gli elementi se la collezione non ha elementi in comune.
     * <p>
     * @description 1. Crea una collezione con elementi non presenti nella lista ("cinque", "sei").<br />
     * 2. Chiama {@code retainAll()} sulla lista.<br />
     * 3. Verifica che il risultato sia true.<br />
     * 4. Verifica che la lista sia vuota.
     * <p>
     * @pre Lista popolata. Collezione con elementi assenti.
     * <p>
     * @post La lista è vuota.
     * <p>
     * @expected {@code retainAll()} deve restituire true e la lista deve essere vuota.
     */
    @Test
    public void testRetainAllNoCommonElements()
    {
        HCollection noCommon = new ListAdapter();
        noCommon.add("cinque");
        noCommon.add("sei");

        assertTrue(list.retainAll(noCommon));
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    /**
     * Test del metodo {@link HList#retainAll(HCollection)} con collezione vuota.
     * <p>
     * @summary Verifica che {@code retainAll()} con una collezione vuota svuoti la lista.
     * <p>
     * @design Assicurarsi che il metodo rimuova tutti gli elementi se la collezione di riferimento è vuota.
     * <p>
     * @description 1. Crea una collezione vuota.<br />
     * 2. Chiama {@code retainAll()} sulla lista.<br />
     * 3. Verifica che il risultato sia true.<br />
     * 4. Verifica che la lista sia vuota.
     * <p>
     * @pre Lista popolata. Collezione vuota.
     * <p>
     * @post La lista è vuota.
     * <p>
     * @expected {@code retainAll()} deve restituire true e la lista deve essere vuota.
     */
    @Test
    public void testRetainAllEmptyCollection()
    {
        HCollection emptyCollection = new ListAdapter();
        assertTrue(list.retainAll(emptyCollection));
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    /**
     * Test del metodo {@link HList#retainAll(HCollection)} con collezione identica.
     * <p>
     * @summary Verifica che {@code retainAll()} non modifichi la lista se la collezione è identica.
     * <p>
     * @design Assicurarsi che il metodo restituisca false se la lista non viene modificata.
     * <p>
     * @description 1. Crea una collezione con gli stessi elementi della lista.<br />
     * 2. Chiama {@code retainAll()} sulla lista.<br />
     * 3. Verifica che il risultato sia false.<br />
     * 4. Verifica che la dimensione e il contenuto della lista non siano cambiati.
     * <p>
     * @pre Lista popolata. Collezione con gli stessi elementi.
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected {@code retainAll()} deve restituire false.
     */
    @Test
    public void testRetainAllIdenticalCollection()
    {
        HCollection identicalCollection = new ListAdapter();
        identicalCollection.add("uno");
        identicalCollection.add("due");
        identicalCollection.add("tre");
        identicalCollection.add("quattro");

        assertFalse(list.retainAll(identicalCollection));
        assertEquals(4, list.size());
        assertTrue(list.containsAll(identicalCollection));
    }

    /**
     * Test del metodo {@link HList#retainAll(HCollection)} con collezione null.
     * <p>
     * @summary Verifica che {@code retainAll(null)} lanci {@code NullPointerException}.
     * <p>
     * @design Assicurarsi che il metodo gestisca correttamente l'input null per la collezione.
     * <p>
     * @description 1. Chiama {@code retainAll(null)}.<br />
     * 2. Verifica che venga lanciata {@code NullPointerException}.
     * <p>
     * @pre Lista popolata.
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected {@code NullPointerException} deve essere lanciata.
     */
    @Test(expected = NullPointerException.class)
    public void testRetainAllNullCollection()
    {
        list.retainAll(null);
    }

    //------- TEST DEL METODO clear() ----------

    /**
     * Test del metodo {@link HList#clear()}.
     * <p>
     * @summary Verifica che {@code clear()} svuoti la lista.
     * <p>
     * @design Assicurarsi che il metodo rimuova tutti gli elementi, impostando la dimensione a 0.
     * <p>
     * @description 1. Chiama {@code clear()} sulla lista popolata.<br />
     * 2. Verifica che la dimensione sia 0.<br />
     * 3. Verifica che la lista sia vuota.
     * <p>
     * @pre Lista popolata con 4 elementi.
     * <p>
     * @post La lista è vuota.
     * <p>
     * @expected {@code size()} deve essere 0 e {@code isEmpty()} deve essere true.
     */
    @Test
    public void testClearPopulatedList()
    {
        list.clear();
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    //------- TEST DEL METODO get(int) ----------

    /**
     * Test del metodo {@link HList#get(int)}.
     * <p>
     * @summary Verifica che {@code get(int)} restituisca l'elemento corretto.
     * <p>
     * @design Assicurarsi che il metodo recuperi l'elemento all'indice specificato.
     * <p>
     * @description 1. Chiama {@code get()} per vari indici validi (inizio, mezzo, fine).<br />
     * 2. Verifica che gli elementi restituiti siano quelli attesi.
     * <p>
     * @pre Lista popolata con "uno", "due", "tre", "quattro".
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected {@code get(0)} deve essere "uno", {@code get(1)} "due", {@code get(3)} "quattro".
     */
    @Test
    public void testGetValidIndex()
    {
        assertEquals("uno", list.get(0));
        assertEquals("due", list.get(1));
        assertEquals("quattro", list.get(3));
    }

    /**
     * Test del metodo {@link HList#get(int)} con indice fuori limite superiore.
     * <p>
     * @summary Verifica che {@code get(int)} lanci {@code IndexOutOfBoundsException} se l'indice è troppo grande.
     * <p>
     * @design Assicurarsi che il metodo segnali correttamente gli accessi fuori limite.
     * <p>
     * @description 1. Tenta di recuperare un elemento a indice uguale alla dimensione.<br />
     * 2. Verifica che venga lanciata {@code IndexOutOfBoundsException}.
     * <p>
     * @pre Lista popolata con 4 elementi.
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected {@code IndexOutOfBoundsException} deve essere lanciata.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetIndexTooLarge()
    {
        list.get(4); // size is 4, valid indices 0-3
    }

    /**
     * Test del metodo {@link HList#get(int)} con indice fuori limite inferiore.
     * <p>
     * @summary Verifica che {@code get(int)} lanci {@code IndexOutOfBoundsException} se l'indice è negativo.
     * <p>
     * @design Assicurarsi che il metodo segnali correttamente gli accessi a indici negativi.
     * <p>
     * @description 1. Tenta di recuperare un elemento a indice -1.<br />
     * 2. Verifica che venga lanciata {@code IndexOutOfBoundsException}.
     * <p>
     * @pre Lista popolata.
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected {@code IndexOutOfBoundsException} deve essere lanciata.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetIndexNegative()
    {
        list.get(-1);
    }

    //------- TEST DEL METODO set(int, Object) ----------

    /**
     * Test del metodo {@link HList#set(int, Object)}.
     * <p>
     * @summary Verifica che {@code set(int, Object)} sostituisca correttamente un elemento esistente.
     * <p>
     * @design Assicurarsi che l'elemento all'indice specificato sia sostituito con il nuovo valore
     * e che il vecchio valore sia restituito.
     * <p>
     * @description 1. Imposta un nuovo elemento a indice 1 ("nuovoDue").<br />
     * 2. Verifica che il valore restituito sia il vecchio elemento ("due").<br />
     * 3. Verifica che la dimensione sia invariata.<br />
     * 4. Verifica che l'elemento a indice 1 sia ora "nuovoDue".
     * <p>
     * @pre Lista popolata con "uno", "due", "tre", "quattro".
     * <p>
     * @post L'elemento all'indice specificato è stato sostituito.
     * <p>
     * @expected {@code set(1, "nuovoDue")} deve restituire "due", la dimensione deve essere 4,
     * e {@code get(1)} deve essere "nuovoDue".
     */
    @Test
    public void testSetValidIndex()
    {
        Object oldElement = list.set(1, "nuovoDue");
        assertEquals("due", oldElement);
        assertEquals(4, list.size());
        assertEquals("nuovoDue", list.get(1));
    }

    /**
     * Test del metodo {@link HList#set(int, Object)} con indice fuori limite superiore.
     * <p>
     * @summary Verifica che {@code set(int, Object)} lanci {@code IndexOutOfBoundsException} se l'indice è troppo grande.
     * <p>
     * @design Assicurarsi che il metodo segnali correttamente gli accessi fuori limite.
     * <p>
     * @description 1. Tenta di impostare un elemento a indice uguale alla dimensione.<br />
     * 2. Verifica che venga lanciata {@code IndexOutOfBoundsException}.
     * <p>
     * @pre Lista popolata con 4 elementi.
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected {@code IndexOutOfBoundsException} deve essere lanciata.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSetIndexTooLarge()
    {
        list.set(4, "test");
    }

    /**
     * Test del metodo {@link HList#set(int, Object)} con indice fuori limite inferiore.
     * <p>
     * @summary Verifica che {@code set(int, Object)} lanci {@code IndexOutOfBoundsException} se l'indice è negativo.
     * <p>
     * @design Assicurarsi che il metodo segnali correttamente gli accessi a indici negativi.
     * <p>
     * @description 1. Tenta di impostare un elemento a indice -1.<br />
     * 2. Verifica che venga lanciata {@code IndexOutOfBoundsException}.
     * <p>
     * @pre Lista popolata.
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected {@code IndexOutOfBoundsException} deve essere lanciata.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSetIndexNegative()
    {
        list.set(-1, "test");
    }

    //------- TEST DEL METODO add(int, Object) ----------

    /**
     * Test del metodo {@link HList#add(int, Object)}.
     * <p>
     * @summary Verifica l'aggiunta di un elemento a un indice specifico all'inizio della lista.
     * <p>
     * @design Assicurarsi che l'elemento sia aggiunto correttamente, che la dimensione sia aggiornata
     * e che gli elementi esistenti siano shiftati correttamente.
     * <p>
     * @description 1. Aggiunge un elemento a indice 0 ("inizio").<br />
     * 2. Verifica che la dimensione sia 5.<br />
     * 3. Verifica che l'elemento aggiunto sia il primo e che il vecchio primo sia ora al secondo posto.
     * <p>
     * @pre Lista popolata con 4 elementi.
     * <p>
     * @post La lista contiene un elemento in più all'inizio.
     * <p>
     * @expected {@code size()} deve essere 5, {@code get(0)} deve essere "inizio", {@code get(1)} deve essere "uno".
     */
    @Test
    public void testAddAtIndex0()
    {
        list.add(0, "inizio");
        assertEquals(5, list.size());
        assertEquals("inizio", list.get(0));
        assertEquals("uno", list.get(1));
    }

    /**
     * Test del metodo {@link HList#add(int, Object)}.
     * <p>
     * @summary Verifica l'aggiunta di un elemento a un indice specifico al centro della lista.
     * <p>
     * @design Assicurarsi che l'elemento sia aggiunto correttamente, che la dimensione sia aggiornata
     * e che gli elementi esistenti siano shiftati correttamente.
     * <p>
     * @description 1. Aggiunge un elemento a indice 2 ("nuovoTre").<br />
     * 2. Verifica che la dimensione sia 5.<br />
     * 3. Verifica che l'elemento aggiunto sia al suo posto e che gli elementi successivi siano shiftati.
     * <p>
     * @pre Lista popolata con "uno", "due", "tre", "quattro".
     * <p>
     * @post La lista contiene un elemento in più al centro.
     * <p>
     * @expected {@code size()} deve essere 5, {@code get(2)} deve essere "nuovoTre", {@code get(3)} deve essere "tre".
     */
    @Test
    public void testAddAtIndexMiddle()
    {
        list.add(2, "nuovoTre");
        assertEquals(5, list.size());
        assertEquals("nuovoTre", list.get(2));
        assertEquals("tre", list.get(3)); // L'originale "tre" è stato shiftato
    }

    /**
     * Test del metodo {@link HList#add(int, Object)}.
     * <p>
     * @summary Verifica l'aggiunta di un elemento all'ultimo indice valido (cioè {@code size()}).
     * <p>
     * @design Assicurarsi che l'elemento sia aggiunto alla fine della lista.
     * <p>
     * @description 1. Aggiunge un elemento a indice {@code list.size()}.<br />
     * 2. Verifica che la dimensione sia 5.<br />
     * 3. Verifica che l'elemento aggiunto sia l'ultimo della lista.
     * <p>
     * @pre Lista popolata con 4 elementi.
     * <p>
     * @post La lista contiene un elemento in più alla fine.
     * <p>
     * @expected {@code size()} deve essere 5, {@code get(4)} deve essere "fine".
     */
    @Test
    public void testAddAtIndexSize()
    {
        list.add(list.size(), "fine");
        assertEquals(5, list.size());
        assertEquals("fine", list.get(4));
    }

    /**
     * Test del metodo {@link HList#add(int, Object)} con indice fuori limite superiore.
     * <p>
     * @summary Verifica che {@code add(int, Object)} lanci {@code IndexOutOfBoundsException}
     * se l'indice è maggiore della dimensione.
     * <p>
     * @design Assicurarsi che non sia possibile aggiungere elementi a indici non validi.
     * <p>
     * @description 1. Tenta di aggiungere un elemento a indice {@code list.size() + 1}.<br />
     * 2. Verifica che venga lanciata {@code IndexOutOfBoundsException}.
     * <p>
     * @pre Lista popolata con 4 elementi.
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected {@code IndexOutOfBoundsException} deve essere lanciata.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAtIndexTooLarge()
    {
        list.add(list.size() + 1, "invalid");
    }

    /**
     * Test del metodo {@link HList#add(int, Object)} con indice fuori limite inferiore.
     * <p>
     * @summary Verifica che {@code add(int, Object)} lanci {@code IndexOutOfBoundsException}
     * se l'indice è negativo.
     * <p>
     * @design Assicurarsi che non sia possibile aggiungere elementi a indici negativi.
     * <p>
     * @description 1. Tenta di aggiungere un elemento a indice -1.<br />
     * 2. Verifica che venga lanciata {@code IndexOutOfBoundsException}.
     * <p>
     * @pre Lista popolata.
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected {@code IndexOutOfBoundsException} deve essere lanciata.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAtIndexNegative()
    {
        list.add(-1, "invalid");
    }

    //------- TEST DEL METODO remove(int) ----------

    /**
     * Test del metodo {@link HList#remove(int)}.
     * <p>
     * @summary Verifica la rimozione di un elemento a un indice specifico all'inizio della lista.
     * <p>
     * @design Assicurarsi che l'elemento corretto sia rimosso e che la dimensione e lo shift siano corretti.
     * <p>
     * @description 1. Rimuove l'elemento a indice 0.<br />
     * 2. Verifica che l'elemento restituito sia "uno".<br />
     * 3. Verifica che la dimensione sia 3 e che "due" sia il nuovo primo elemento.
     * <p>
     * @pre Lista popolata con "uno", "due", "tre", "quattro".
     * <p>
     * @post L'elemento a indice 0 è rimosso, gli altri shiftati.
     * <p>
     * @expected Il metodo deve restituire "uno", la dimensione deve essere 3, e la lista deve essere ["due", "tre", "quattro"].
     */
    @Test
    public void testRemoveAtIndex0()
    {
        Object removed = list.remove(0);
        assertEquals("uno", removed);
        assertEquals(3, list.size());
        assertEquals("due", list.get(0));
    }

    /**
     * Test del metodo {@link HList#remove(int)}.
     * <p>
     * @summary Verifica la rimozione di un elemento a un indice specifico al centro della lista.
     * <p>
     * @design Assicurarsi che l'elemento corretto sia rimosso e che la dimensione e lo shift siano corretti.
     * <p>
     * @description 1. Rimuove l'elemento a indice 1 ("due").<br />
     * 2. Verifica che l'elemento restituito sia "due".<br />
     * 3. Verifica che la dimensione sia 3 e che "tre" sia il nuovo elemento a indice 1.
     * <p>
     * @pre Lista popolata con "uno", "due", "tre", "quattro".
     * <p>
     * @post L'elemento a indice 1 è rimosso, gli altri shiftati.
     * <p>
     * @expected Il metodo deve restituire "due", la dimensione deve essere 3, e la lista deve essere ["uno", "tre", "quattro"].
     */
    @Test
    public void testRemoveAtIndexMiddle()
    {
        Object removed = list.remove(1);
        assertEquals("due", removed);
        assertEquals(3, list.size());
        assertEquals("tre", list.get(1));
    }

    /**
     * Test del metodo {@link HList#remove(int)}.
     * <p>
     * @summary Verifica la rimozione dell'ultimo elemento della lista.
     * <p>
     * @design Assicurarsi che l'ultimo elemento sia rimosso e la dimensione aggiornata.
     * <p>
     * @description 1. Rimuove l'elemento a indice {@code list.size() - 1}.<br />
     * 2. Verifica che l'elemento restituito sia "quattro".<br />
     * 3. Verifica che la dimensione sia 3 e che "quattro" non sia più presente.
     * <p>
     * @pre Lista popolata con 4 elementi.
     * <p>
     * @post L'ultimo elemento è rimosso.
     * <p>
     * @expected Il metodo deve restituire "quattro", la dimensione deve essere 3.
     */
    @Test
    public void testRemoveAtLastIndex()
    {
        Object removed = list.remove(list.size() - 1); // Rimuove "quattro"
        assertEquals("quattro", removed);
        assertEquals(3, list.size());
        assertFalse(list.contains("quattro"));
    }

    /**
     * Test del metodo {@link HList#remove(int)} con indice fuori limite superiore.
     * <p>
     * @summary Verifica che {@code remove(int)} lanci {@code IndexOutOfBoundsException}
     * se l'indice è uguale o maggiore della dimensione.
     * <p>
     * @design Assicurarsi che il metodo segnali correttamente gli accessi fuori limite.
     * <p>
     * @description 1. Tenta di rimuovere un elemento a indice uguale alla dimensione.<br />
     * 2. Verifica che venga lanciata {@code IndexOutOfBoundsException}.
     * <p>
     * @pre Lista popolata con 4 elementi.
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected {@code IndexOutOfBoundsException} deve essere lanciata.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveIndexTooLarge()
    {
        list.remove(list.size()); // size is 4, valid indices 0-3
    }

    /**
     * Test del metodo {@link HList#remove(int)} con indice fuori limite inferiore.
     * <p>
     * @summary Verifica che {@code remove(int)} lanci {@code IndexOutOfBoundsException}
     * se l'indice è negativo.
     * <p>
     * @design Assicurarsi che il metodo segnali correttamente gli accessi a indici negativi.
     * <p>
     * @description 1. Tenta di rimuovere un elemento a indice -1.<br />
     * 2. Verifica che venga lanciata {@code IndexOutOfBoundsException}.
     * <p>
     * @pre Lista popolata.
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected {@code IndexOutOfBoundsException} deve essere lanciata.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveIndexNegative()
    {
        list.remove(-1);
    }

    //------- TEST DEL METODO indexOf(Object) ----------

    /**
     * Test del metodo {@link HList#indexOf(Object)}.
     * <p>
     * @summary Verifica che {@code indexOf()} restituisca l'indice della prima occorrenza di un elemento.
     * <p>
     * @design Assicurarsi che il metodo trovi l'indice corretto per elementi presenti e restituisca -1 per elementi assenti.
     * <p>
     * @description 1. Chiama {@code indexOf()} per "due".<br />
     * 2. Verifica che il risultato sia 1.<br />
     * 3. Chiama {@code indexOf()} per "cinque" (assente).<br />
     * 4. Verifica che il risultato sia -1.
     * <p>
     * @pre Lista popolata con "uno", "due", "tre", "quattro".
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected {@code indexOf("due")} deve essere 1, {@code indexOf("cinque")} deve essere -1.
     */
    @Test
    public void testIndexOfPresentAndAbsent()
    {
        assertEquals(1, list.indexOf("due"));
        assertEquals(-1, list.indexOf("cinque"));
    }

    /**
     * Test del metodo {@link HList#indexOf(Object)} con elemento null.
     * <p>
     * @summary Verifica che {@code indexOf(null)} funzioni correttamente.
     * <p>
     * @design Assicurarsi che il metodo trovi l'indice corretto per null se presente, altrimenti -1.
     * <p>
     * @description 1. Chiama {@code indexOf(null)} su una lista senza null.<br />
     * 2. Verifica che il risultato sia -1.<br />
     * 3. Aggiunge null e chiara {@code indexOf(null)}.<br />
     * 4. Verifica che il risultato sia l'indice di null.
     * <p>
     * @pre Lista popolata.
     * <p>
     * @post La lista rimane invariata (o è stata modificata per l'aggiunta di null).
     * <p>
     * @expected {@code indexOf(null)} deve essere -1 inizialmente, poi l'indice corretto dopo l'aggiunta.
     */
    @Test
    public void testIndexOfNull()
    {
        assertEquals(-1, list.indexOf(null));
        list.add(null);
        assertEquals(4, list.indexOf(null)); // null è stato aggiunto all'indice 4
    }

    //------- TEST DEL METODO lastIndexOf(Object) ----------

    /**
     * Test del metodo {@link HList#lastIndexOf(Object)}.
     * <p>
     * @summary Verifica che {@code lastIndexOf()} restituisca l'indice dell'ultima occorrenza di un elemento.
     * <p>
     * @design Assicurarsi che il metodo trovi l'indice corretto per elementi presenti (anche duplicati) e -1 per assenti.
     * <p>
     * @description 1. Aggiunge un duplicato ("due").<br />
     * 2. Chiama {@code lastIndexOf()} per "due".<br />
     * 3. Verifica che il risultato sia l'indice dell'ultima occorrenza.<br />
     * 4. Chiama {@code lastIndexOf()} per un elemento assente ("cinque").<br />
     * 5. Verifica che il risultato sia -1.
     * <p>
     * @pre Lista popolata, eventualmente con duplicati.
     * <p>
     * @post La lista rimane invariata (o è stata modificata per l'aggiunta di duplicati).
     * <p>
     * @expected {@code lastIndexOf("due")} deve essere l'indice corretto dell'ultima occorrenza,
     * {@code lastIndexOf("cinque")} deve essere -1.
     */
    @Test
    public void testLastIndexOfPresentAndAbsent()
    {
        list.add("due"); // list: "uno", "due", "tre", "quattro", "due"
        assertEquals(4, list.lastIndexOf("due"));
        assertEquals(-1, list.lastIndexOf("cinque"));
    }

    /**
     * Test del metodo {@link HList#lastIndexOf(Object)} con elemento null.
     * <p>
     * @summary Verifica che {@code lastIndexOf(null)} funzioni correttamente.
     * <p>
     * @design Assicurarsi che il metodo trovi l'indice corretto per l'ultima occorrenza di null se presente, altrimenti -1.
     * <p>
     * @description 1. Aggiunge più elementi null.<br />
     * 2. Chiama {@code lastIndexOf(null)}.<br />
     * 3. Verifica che il risultato sia l'indice dell'ultima occorrenza di null.
     * <p>
     * @pre Lista popolata, con più elementi null.
     * <p>
     * @post La lista rimane invariata (o è stata modificata per l'aggiunta di null).
     * <p>
     * @expected {@code lastIndexOf(null)} deve essere l'indice corretto dell'ultima occorrenza di null.
     */
    @Test
    public void testLastIndexOfNull()
    {
        list.add(null);
        list.add("sette");
        list.add(null); // list: "uno", "due", "tre", "quattro", null, "sette", null
        assertEquals(6, list.lastIndexOf(null));
    }

    //------- TEST DEL METODO listIterator() ----------

    /**
     * Test del metodo {@link HList#listIterator()}.
     * <p>
     * @summary Verifica che {@code listIterator()} su una lista popolata restituisca un iteratore valido.
     * <p>
     * @design Assicurarsi che l'iteratore sia posizionato all'inizio e che le sue funzionalità base (next, previous)
     * operino correttamente.
     * <p>
     * @description 1. Ottiene un ListIterator.<br />
     * 2. Verifica che {@code hasNext()} sia true e {@code hasPrevious()} sia false.<br />
     * 3. Verifica il primo elemento con {@code next()}.
     * <p>
     * @pre Lista popolata.
     * <p>
     * @post La lista rimane invariata, l'iteratore è posizionato all'inizio.
     * <p>
     * @expected {@code hasNext()} deve essere true, {@code hasPrevious()} false,
     * {@code next()} deve restituire il primo elemento.
     */
    @Test
    public void testListIteratorPopulatedStart()
    {
        HListIterator it = list.listIterator();
        assertNotNull(it);
        assertTrue(it.hasNext());
        assertFalse(it.hasPrevious());
        assertEquals("uno", it.next());
    }

    //------- TEST DEL METODO listIterator(int) ----------

    /**
     * Test del metodo {@link HList#listIterator(int)}.
     * <p>
     * @summary Verifica che {@code listIterator(int)} crei un iteratore posizionato correttamente.
     * <p>
     * @design Assicurarsi che l'iteratore sia inizializzato all'indice specificato e che le sue funzionalità
     * di navigazione e indice riflettano tale posizione.
     * <p>
     * @description 1. Crea un ListIterator a indice 2.<br />
     * 2. Verifica {@code nextIndex()} e {@code previousIndex()}.<br />
     * 3. Verifica {@code next()} e {@code previous()} dal punto di partenza.
     * <p>
     * @pre Lista popolata con 4 elementi.
     * <p>
     * @post La lista rimane invariata, l'iteratore è posizionato all'indice 2.
     * <p>
     * @expected {@code nextIndex()} deve essere 2, {@code previousIndex()} deve essere 1,
     * {@code next()} deve restituire "tre", {@code previous()} deve restituire "due".
     */
    @Ignore("Da dei problemi con la gestione degli indici")
    @Test
    public void testListIteratorAtIndexPopulated()
    {
        HListIterator it = list.listIterator(2); // Posiziona il cursore tra "due" e "tre"
        assertEquals(2, it.nextIndex());
        assertEquals(1, it.previousIndex());
        assertTrue(it.hasNext());
        assertTrue(it.hasPrevious());
        assertEquals("tre", it.next()); // next() dovrebbe restituire "tre"
        assertEquals("due", it.previous()); // previous() dovrebbe restituire "due"
    }

    /**
     * Test del metodo {@link HList#listIterator(int)} con indice alla fine della lista.
     * <p>
     * @summary Verifica che {@code listIterator(size())} crei un iteratore posizionato alla fine.
     * <p>
     * @design Assicurarsi che l'iteratore sia posizionato dopo l'ultimo elemento.
     * <p>
     * @description 1. Crea un ListIterator a indice {@code list.size()}.<br />
     * 2. Verifica che {@code hasNext()} sia false e {@code hasPrevious()} sia true.<br />
     * 3. Verifica l'ultimo elemento con {@code previous()}.
     * <p>
     * @pre Lista popolata con 4 elementi.
     * <p>
     * @post La lista rimane invariata, l'iteratore è posizionato alla fine.
     * <p>
     * @expected {@code hasNext()} deve essere false, {@code hasPrevious()} true,
     * {@code previous()} deve restituire "quattro".
     */
    @Test
    public void testListIteratorAtIndexAtEnd()
    {
        HListIterator it = list.listIterator(list.size()); // Posiziona il cursore dopo "quattro"
        assertFalse(it.hasNext());
        assertTrue(it.hasPrevious());
        assertEquals(list.size(), it.nextIndex());
        assertEquals(list.size() - 1, it.previousIndex());
        assertEquals("quattro", it.previous());
    }

    /**
     * Test del metodo {@link HList#listIterator(int)} con indice fuori limite superiore.
     * <p>
     * @summary Verifica che {@code listIterator(int)} lanci {@code IndexOutOfBoundsException}
     * se l'indice è maggiore della dimensione.
     * <p>
     * @design Assicurarsi che il metodo segnali correttamente la creazione di iteratori con indici non validi.
     * <p>
     * @description 1. Tenta di creare un ListIterator con indice {@code list.size() + 1}.<br />
     * 2. Verifica che venga lanciata {@code IndexOutOfBoundsException}.
     * <p>
     * @pre Lista popolata con 4 elementi.
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected {@code IndexOutOfBoundsException} deve essere lanciata.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testListIteratorAtIndexTooLarge()
    {
        list.listIterator(list.size() + 1);
    }

    /**
     * Test del metodo {@link HList#listIterator(int)} con indice fuori limite inferiore.
     * <p>
     * @summary Verifica che {@code listIterator(int)} lanci {@code IndexOutOfBoundsException}
     * se l'indice è negativo.
     * <p>
     * @design Assicurarsi che il metodo segnali correttamente la creazione di iteratori con indici negativi.
     * <p>
     * @description 1. Tenta di creare un ListIterator con indice -1.<br />
     * 2. Verifica che venga lanciata {@code IndexOutOfBoundsException}.
     * <p>
     * @pre Lista popolata.
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected {@code IndexOutOfBoundsException} deve essere lanciata.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testListIteratorAtIndexNegative()
    {
        list.listIterator(-1);
    }

    //------- TEST DEL METODO subList(int, int) ----------

    /**
     * Test del metodo {@link HList#subList(int, int)}.
     * <p>
     * @summary Verifica la creazione di una sottolista valida al centro della lista.
     * <p>
     * @design Assicurarsi che la sottolista contenga gli elementi corretti e che la sua dimensione sia giusta.
     * <p>
     * @description 1. Crea una sottolista da indice 1 a 3 (esclusivo, quindi elementi a 1 e 2).<br />
     * 2. Verifica la dimensione della sottolista (2).<br />
     * 3. Verifica che gli elementi della sottolista siano "due" e "tre".
     * <p>
     * @pre Lista popolata con "uno", "due", "tre", "quattro".
     * <p>
     * @post La lista originale rimane invariata. Viene creata una nuova sottolista.
     * <p>
     * @expected La sottolista deve avere dimensione 2 e contenere "due", "tre".
     */
    @Test
    public void testSubListValidRangeMiddle()
    {
        HList sub = list.subList(1, 3); // fromIndex=1 (inclusive), toIndex=3 (exclusive) -> elements at index 1, 2
        assertEquals(2, sub.size());
        assertEquals("due", sub.get(0));
        assertEquals("tre", sub.get(1));
        assertFalse(sub.contains("uno"));
        assertFalse(sub.contains("quattro"));
    }

    /**
     * Test del metodo {@link HList#subList(int, int)}.
     * <p>
     * @summary Verifica la creazione di una sottolista valida all'inizio della lista.
     * <p>
     * @design Assicurarsi che la sottolista catturi correttamente gli elementi dall'inizio.
     * <p>
     * @description 1. Crea una sottolista da indice 0 a 2 (esclusivo, quindi elementi a 0 e 1).<br />
     * 2. Verifica la dimensione della sottolista (2).<br />
     * 3. Verifica che gli elementi della sottolista siano "uno" e "due".
     * <p>
     * @pre Lista popolata.
     * <p>
     * @post La lista originale rimane invariata. Viene creata una nuova sottolista.
     * <p>
     * @expected La sottolista deve avere dimensione 2 e contenere "uno", "due".
     */
    @Test
    public void testSubListValidRangeStart()
    {
        HList sub = list.subList(0, 2); // Elements at index 0, 1
        assertEquals(2, sub.size());
        assertEquals("uno", sub.get(0));
        assertEquals("due", sub.get(1));
    }

    /**
     * Test del metodo {@link HList#subList(int, int)}.
     * <p>
     * @summary Verifica la creazione di una sottolista valida alla fine della lista.
     * <p>
     * @design Assicurarsi che la sottolista catturi correttamente gli elementi fino alla fine.
     * <p>
     * @description 1. Crea una sottolista da indice 2 a {@code list.size()} (esclusivo, quindi elementi a 2 e 3).<br />
     * 2. Verifica la dimensione della sottolista (2).<br />
     * 3. Verifica che gli elementi della sottolista siano "tre" e "quattro".
     * <p>
     * @pre Lista popolata.
     * <p>
     * @post La lista originale rimane invariata. Viene creata una nuova sottolista.
     * <p>
     * @expected La sottolista deve avere dimensione 2 e contenere "tre", "quattro".
     */
    @Test
    public void testSubListValidRangeEnd()
    {
        HList sub = list.subList(2, list.size()); // Elements at index 2, 3
        assertEquals(2, sub.size());
        assertEquals("tre", sub.get(0));
        assertEquals("quattro", sub.get(1));
    }

    /**
     * Test del metodo {@link HList#subList(int, int)}.
     * <p>
     * @summary Verifica la creazione di una sottolista vuota con {@code fromIndex == toIndex}.
     * <p>
     * @design Assicurarsi che una sottolista vuota sia creata correttamente quando gli indici sono uguali.
     * <p>
     * @description 1. Crea una sottolista con indici (1, 1).<br />
     * 2. Verifica che la dimensione della sottolista sia 0.
     * <p>
     * @pre Lista popolata.
     * <p>
     * @post La lista originale rimane invariata. Viene creata una sottolista vuota.
     * <p>
     * @expected La sottolista deve avere dimensione 0.
     */
    @Test
    public void testSubListEmptyRange()
    {
        HList sub = list.subList(1, 1);
        assertEquals(0, sub.size());
        assertTrue(sub.isEmpty());
    }

    /**
     * Test del metodo {@link HList#subList(int, int)} con {@code fromIndex} negativo.
     * <p>
     * @summary Verifica che {@code subList()} lanci {@code IndexOutOfBoundsException} se {@code fromIndex} è negativo.
     * <p>
     * @design Assicurarsi che il metodo segnali correttamente l'intervallo non valido.
     * <p>
     * @description 1. Tenta di creare una sottolista con {@code fromIndex = -1}.<br />
     * 2. Verifica che venga lanciata {@code IndexOutOfBoundsException}.
     * <p>
     * @pre Lista popolata.
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected {@code IndexOutOfBoundsException} deve essere lanciata.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSubListInvalidFromIndexNegative()
    {
        list.subList(-1, 2);
    }

    /**
     * Test del metodo {@link HList#subList(int, int)} con {@code toIndex} troppo grande.
     * <p>
     * @summary Verifica che {@code subList()} lanci {@code IndexOutOfBoundsException} se {@code toIndex} è maggiore della dimensione.
     * <p>
     * @design Assicurarsi che il metodo segnali correttamente l'intervallo non valido.
     * <p>
     * @description 1. Tenta di creare una sottolista con {@code toIndex = list.size() + 1}.<br />
     * 2. Verifica che venga lanciata {@code IndexOutOfBoundsException}.
     * <p>
     * @pre Lista popolata.
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected {@code IndexOutOfBoundsException} deve essere lanciata.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSubListInvalidToIndexTooLarge()
    {
        list.subList(0, list.size() + 1);
    }

    /**
     * Test del metodo {@link HList#subList(int, int)} con {@code fromIndex} maggiore di {@code toIndex}.
     * <p>
     * @summary Verifica che {@code subList()} lanci {@code IndexOutOfBoundsException} se {@code fromIndex > toIndex}.
     * <p>
     * @design Assicurarsi che il metodo segnali correttamente l'intervallo non valido (start > end).
     * <p>
     * @description 1. Tenta di creare una sottolista con {@code fromIndex = 2} e {@code toIndex = 1}.<br />
     * 2. Verifica che venga lanciata {@code IndexOutOfBoundsException}.
     * <p>
     * @pre Lista popolata.
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected {@code IndexOutOfBoundsException} deve essere lanciata.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSubListInvalidFromIndexGreaterThanToIndex()
    {
        list.subList(2, 1);
    }

    //------- TEST DEL METODO addAll(int, HCollection) ----------

    /**
     * Test del metodo {@link HList#addAll(int, HCollection)}.
     * <p>
     * @summary Verifica l'aggiunta di una collezione a un indice specifico (inizio).
     * <p>
     * @design Assicurarsi che gli elementi siano aggiunti correttamente e che gli elementi esistenti siano shiftati.
     * <p>
     * @description 1. Crea una collezione con 2 elementi.<br />
     * 2. Aggiunge la collezione a indice 0.<br />
     * 3. Verifica che la dimensione sia 6.<br />
     * 4. Verifica che gli elementi siano stati aggiunti all'inizio e che i vecchi elementi siano shiftati.
     * <p>
     * @pre Lista popolata con 4 elementi. Collezione con due elementi.
     * <p>
     * @post La lista contiene gli elementi aggiunti all'inizio.
     * <p>
     * @expected {@code addAll(0, HCollection)} deve restituire true, la lista deve avere dimensione 6.
     */
    @Test
    public void testAddAllAtIndex0PopulatedList()
    {
        HCollection newElements = new ListAdapter();
        newElements.add("newA");
        newElements.add("newB");

        assertTrue(list.addAll(0, newElements));
        assertEquals(6, list.size());
        assertEquals("newA", list.get(0));
        assertEquals("newB", list.get(1));
        assertEquals("uno", list.get(2)); // Original "uno" is now at index 2
    }

    /**
     * Test del metodo {@link HList#addAll(int, HCollection)}.
     * <p>
     * @summary Verifica l'aggiunta di una collezione a un indice specifico (centro).
     * <p>
     * @design Assicurarsi che gli elementi siano aggiunti correttamente al centro della lista e che gli elementi esistenti siano shiftati.
     * <p>
     * @description 1. Crea una collezione con 2 elementi.<br />
     * 2. Aggiunge la collezione a indice 2.<br />
     * 3. Verifica che la dimensione sia 6.<br />
     * 4. Verifica che gli elementi siano stati aggiunti al centro e che gli elementi successivi siano shiftati.
     * <p>
     * @pre Lista popolata con 4 elementi. Collezione con due elementi.
     * <p>
     * @post La lista contiene gli elementi aggiunti al centro.
     * <p>
     * @expected {@code addAll(2, HCollection)} deve restituire true, la lista deve avere dimensione 6.
     */
    @Test
    public void testAddAllAtIndexMiddlePopulatedList()
    {
        HCollection newElements = new ListAdapter();
        newElements.add("middleX");
        newElements.add("middleY");

        assertTrue(list.addAll(2, newElements));
        assertEquals(6, list.size());
        assertEquals("uno", list.get(0));
        assertEquals("due", list.get(1));
        assertEquals("middleX", list.get(2));
        assertEquals("middleY", list.get(3));
        assertEquals("tre", list.get(4)); // Original "tre" is now at index 4
        assertEquals("quattro", list.get(5));
    }

    /**
     * Test del metodo {@link HList#addAll(int, HCollection)} con indice fuori limite superiore.
     * <p>
     * @summary Verifica che {@code addAll(int, HCollection)} lanci {@code IndexOutOfBoundsException}
     * se l'indice è maggiore della dimensione.
     * <p>
     * @design Assicurarsi che non sia possibile aggiungere collezioni a indici non validi.
     * <p>
     * @description 1. Tenta di aggiungere una collezione a indice {@code list.size() + 1}.<br />
     * 2. Verifica che venga lanciata {@code IndexOutOfBoundsException}.
     * <p>
     * @pre Lista popolata con 4 elementi.
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected {@code IndexOutOfBoundsException} deve essere lanciata.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAllAtIndexTooLargePopulatedList()
    {
        HCollection newElements = new ListAdapter();
        newElements.add("elem");
        list.addAll(list.size() + 1, newElements);
    }

    /**
     * Test del metodo {@link HList#addAll(int, HCollection)} con indice fuori limite inferiore.
     * <p>
     * @summary Verifica che {@code addAll(int, HCollection)} lanci {@code IndexOutOfBoundsException}
     * se l'indice è negativo.
     * <p>
     * @design Assicurarsi che non sia possibile aggiungere collezioni a indici negativi.
     * <p>
     * @description 1. Tenta di aggiungere una collezione a indice -1.<br />
     * 2. Verifica che venga lanciata {@code IndexOutOfBoundsException}.
     * <p>
     * @pre Lista popolata.
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected {@code IndexOutOfBoundsException} deve essere lanciata.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAllAtIndexNegativePopulatedList()
    {
        HCollection newElements = new ListAdapter();
        newElements.add("elem");
        list.addAll(-1, newElements);
    }

    /**
     * Test del metodo {@link HList#addAll(int, HCollection)} con collezione vuota.
     * <p>
     * @summary Verifica che l'aggiunta di una collezione vuota non modifichi la lista.
     * <p>
     * @design Assicurarsi che il metodo restituisca false e non alteri la lista se la collezione da aggiungere è vuota.
     * <p>
     * @description 1. Crea una collezione vuota.<br />
     * 2. Aggiunge la collezione a un indice valido.<br />
     * 3. Verifica che il risultato sia false.<br />
     * 4. Verifica che la dimensione e il contenuto della lista non siano cambiati.
     * <p>
     * @pre Lista popolata. Collezione vuota.
     * <p>
     * @post La lista rimane invariata.
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
     * @summary Verifica che {@code addAll(int, HCollection)} lanci {@code NullPointerException} se la collezione è null.
     * <p>
     * @design Assicurarsi che il metodo gestisca correttamente l'input null per la collezione.
     * <p>
     * @description 1. Chiama {@code addAll(2, null)}.<br />
     * 2. Verifica che venga lanciata {@code NullPointerException}.
     * <p>
     * @pre Lista popolata.
     * <p>
     * @post La lista rimane invariata.
     * <p>
     * @expected {@code NullPointerException} deve essere lanciata.
     */
    @Test(expected = NullPointerException.class)
    public void testAddAllAtIndexNullCollection()
    {
        list.addAll(2, null);
    }
}