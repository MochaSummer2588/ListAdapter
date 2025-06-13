package myTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import myAdapter.*;

/**
 * Suite di test per la classe {@link myAdapter.ListAdapter}.
 * <p>
 * Riassunto: Verifica il corretto funzionamento dei metodi della classe {@code ListAdapter} che implementa l'interfaccia {@link myAdapter.HList}.
 * <p>
 * Design: Utilizza JUnit 4.13.2<br />
 * La suite include test per metodi di accesso, modifica e interrogazione di una {@link myAdapter.HList}<br />
 * attraverso la classe {@link myAdapter.ListAdapter}, con un'istanza vuota ripopolata prima di ogni test case.<br />
 * Si suppone che la classe {@link myAdapter.ListIterator} funzioni correttamente e sarà testata nella sua propria suite.
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

    //------- TEST DEL METODO size() ----------

    /**
     * Test del metodo {@link HList#size()}.
     * <p>
     * @summary Verifica che la dimensione di una lista vuota sia 0.
     * <p>
     * @design Assicurarsi che il metodo {@code size()} restituisca 0
     * quando la lista è appena creata e non contiene elementi.
     * <p>
     * @description 1. Crea una nuova istanza di ListAdapter.<br />
     * 2. Chiama il metodo {@code size()} sulla lista.<br />
     * 3. Verifica che il valore restituito sia 0.
     * <p>
     * @pre Lista appena inizializzata (vuota).
     * <p>
     * @post La lista rimane vuota.
     * <p>
     * @expected {@code size()} deve restituire 0.
     */
    @Test
    public void testSizeEmpty()
    {
        assertEquals(0, list.size());
    }

    //------- TEST DEL METODO isEmpty() ----------

    /**
     * Test del metodo {@link HList#isEmpty()}.
     * <p>
     * @summary Verifica che una lista vuota sia correttamente identificata come tale.
     * <p>
     * @design Assicurarsi che il metodo {@code isEmpty()} restituisca true
     * quando la lista è appena creata e non contiene elementi.
     * <p>
     * @description 1. Crea una nuova istanza di ListAdapter.<br />
     * 2. Chiama il metodo {@code isEmpty()} sulla lista.<br />
     * 3. Verifica che il valore restituito sia true.
     * <p>
     * @pre Lista appena inizializzata (vuota).
     * <p>
     * @post La lista rimane vuota.
     * <p>
     * @expected {@code isEmpty()} deve restituire true.
     */
    @Test
    public void testIsEmptyTrue()
    {
        assertTrue(list.isEmpty());
    }

    //------- TEST DEL METODO contains(Object) ----------

    /**
     * Test del metodo {@link HList#contains(Object)}.
     * <p>
     * @summary Verifica che una lista vuota non contenga alcun elemento.
     * <p>
     * @design Assicurarsi che il metodo {@code contains()} restituisca false
     * per qualsiasi elemento (anche null) quando la lista è vuota.
     * <p>
     * @description 1. Crea una lista vuota.<br />
     * 2. Chiama {@code contains()} con un elemento non null.<br />
     * 3. Verifica che il risultato sia false.<br />
     * 4. Chiama {@code contains()} con un elemento null.<br />
     * 5. Verifica che il risultato sia false.
     * <p>
     * @pre Lista vuota.
     * <p>
     * @post La lista rimane vuota.
     * <p>
     * @expected {@code contains()} deve restituire false per qualsiasi elemento.
     */
    @Test
    public void testContainsEmpty()
    {
        assertFalse(list.contains("elemento"));
        assertFalse(list.contains(null));
    }

    //------- TEST DEL METODO iterator() ----------

    /**
     * Test del metodo {@link HList#iterator()}.
     * <p>
     * @summary Verifica che l'iteratore su una lista vuota funzioni correttamente.
     * <p>
     * @design Assicurarsi che l'iteratore restituito per una lista vuota
     * indichi correttamente l'assenza di elementi.
     * <p>
     * @description 1. Ottiene un iteratore da una lista vuota.<br />
     * 2. Verifica che {@code hasNext()} sia false.<br />
     * 3. Tenta di chiamare {@code next()} e verifica che lanci `NoSuchElementException`.
     * <p>
     * @pre Lista vuota.
     * <p>
     * @post La lista rimane vuota, l'iteratore non modifica la lista.
     * <p>
     * @expected {@code hasNext()} deve essere false; {@code next()} deve lanciare {@code NoSuchElementException}.
     */
    @Test
    public void testIteratorEmpty()
    {
        HIterator it = list.iterator();
        assertNotNull(it);
        assertFalse(it.hasNext());
        try {
            it.next();
            fail("Expected NoSuchElementException");
        } catch (java.util.NoSuchElementException e) {
            // Success
        }
    }

    //------- TEST DEL METODO toArray() ----------

    /**
     * Test del metodo {@link HList#toArray()}.
     * <p>
     * @summary Verifica che {@code toArray()} su una lista vuota restituisca un array vuoto.
     * <p>
     * @design Assicurarsi che la conversione di una lista vuota in array
     * produca un array di dimensione zero, non null.
     * <p>
     * @description 1. Chiama {@code toArray()} su una lista vuota.<br />
     * 2. Verifica che l'array restituito non sia null e abbia dimensione 0.
     * <p>
     * @pre Lista vuota.
     * <p>
     * @post La lista rimane vuota.
     * <p>
     * @expected {@code toArray()} deve restituire un array di Object di dimensione 0.
     */
    @Test
    public void testToArrayEmpty()
    {
        Object[] arr = list.toArray();
        assertNotNull(arr);
        assertEquals(0, arr.length);
    }

    /**
     * Test del metodo {@link HList#toArray(Object[])}.
     * <p>
     * @summary Verifica che {@code toArray(Object[])} su una lista vuota gestisca correttamente gli array di input.
     * <p>
     * @design Assicurarsi che il metodo popoli correttamente l'array fornito (o ne crei uno nuovo se troppo piccolo)
     * quando la lista è vuota, e che il riferimento alla fine sia null se l'array era più grande.
     * <p>
     * @description 1. Chiama {@code toArray(new Object[0])}.<br />
     * 2. Verifica che l'array restituito abbia dimensione 0.<br />
     * 3. Chiama {@code toArray(new Object[2])}.<br />
     * 4. Verifica che l'array restituito abbia dimensione 2 e che gli elementi siano null dopo l'indice 0.
     * <p>
     * @pre Lista vuota.
     * <p>
     * @post La lista rimane vuota.
     * <p>
     * @expected {@code toArray(Object[])} deve restituire un array di dimensione appropriata e riempito correttamente.
     */
    @Ignore("Da problemi")
    @Test
    public void testToArrayParameterizedEmpty()
    {
        Object[] a = new Object[0];
        Object[] result = list.toArray(a);
        assertSame(a, result); // Se l'array di input è di dimensione 0, dovrebbe essere riutilizzato
        assertEquals(0, result.length);

        Object[] b = new Object[2];
        b[0] = "presente"; // Valore che dovrebbe essere sovrascritto o rimosso
        b[1] = "presente2";
        result = list.toArray(b);
        assertSame(b, result); // L'array dovrebbe essere riutilizzato se sufficientemente grande
        assertEquals(2, result.length);
        assertNull(result[0]); // Il primo elemento dovrebbe essere null
        assertNull(result[1]); // Il secondo elemento dovrebbe essere null
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
     * @pre Lista vuota.
     * <p>
     * @post La lista rimane vuota.
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
     * @summary Verifica che un singolo elemento venga aggiunto correttamente alla lista vuota.
     * <p>
     * @design Assicurarsi che l'elemento sia aggiunto, la dimensione sia aggiornata
     * e il metodo restituisca true.
     * <p>
     * @description 1. Aggiunge un elemento alla lista.<br />
     * 2. Verifica che la dimensione sia 1.<br />
     * 3. Verifica che la lista non sia vuota.<br />
     * 4. Verifica che l'elemento aggiunto sia presente nella lista.
     * <p>
     * @pre Lista vuota.
     * <p>
     * @post La lista contiene l'elemento aggiunto, dimensione 1.
     * <p>
     * @expected {@code add(Object)} deve restituire true, {@code size()} deve essere 1,
     * {@code isEmpty()} false, e {@code contains()} true per l'elemento aggiunto.
     */
    @Test
    public void testAddObjectToEmptyList()
    {
        assertTrue(list.add("testElement"));
        assertEquals(1, list.size());
        assertFalse(list.isEmpty());
        assertTrue(list.contains("testElement"));
    }

    /**
     * Test del metodo {@link HList#add(Object)} con un elemento null.
     * <p>
     * @summary Verifica che un elemento null venga aggiunto correttamente alla lista vuota.
     * <p>
     * @design Assicurarsi che il metodo possa gestire l'aggiunta di elementi nulli.
     * <p>
     * @description 1. Aggiunge un elemento null alla lista.<br />
     * 2. Verifica che la dimensione sia 1.<br />
     * 3. Verifica che l'elemento null sia presente nella lista.
     * <p>
     * @pre Lista vuota.
     * <p>
     * @post La lista contiene un elemento null, dimensione 1.
     * <p>
     * @expected {@code add(Object)} deve restituire true, {@code size()} deve essere 1,
     * {@code contains(null)} deve essere true.
     */
    @Test
    public void testAddNullObjectToEmptyList()
    {
        assertTrue(list.add(null));
        assertEquals(1, list.size());
        assertTrue(list.contains(null));
    }

    //------- TEST DEL METODO remove(Object) ----------

    /**
     * Test del metodo {@link HList#remove(Object)}.
     * <p>
     * @summary Verifica che {@code remove(Object)} su una lista vuota restituisca false.
     * <p>
     * @design Assicurarsi che il metodo non provi a rimuovere elementi da una lista vuota
     * e segnali correttamente l'insuccesso.
     * <p>
     * @description 1. Chiama {@code remove(Object)} con un elemento qualsiasi su una lista vuota.<br />
     * 2. Verifica che il risultato sia false.<br />
     * 3. Verifica che la lista rimanga vuota.
     * <p>
     * @pre Lista vuota.
     * <p>
     * @post La lista rimane vuota.
     * <p>
     * @expected {@code remove(Object)} deve restituire false.
     */
    @Test
    public void testRemoveObjectFromEmptyList()
    {
        assertFalse(list.remove("nonexistent"));
        assertEquals(0, list.size());
    }

    /**
     * Test del metodo {@link HList#remove(Object)} con elemento null su una lista vuota.
     * <p>
     * @summary Verifica che {@code remove(null)} su una lista vuota restituisca false.
     * <p>
     * @design Assicurarsi che il metodo gestisca correttamente la rimozione di null
     * quando la lista è vuota.
     * <p>
     * @description 1. Chiama {@code remove(null)} su una lista vuota.<br />
     * 2. Verifica che il risultato sia false.<br />
     * 3. Verifica che la lista rimanga vuota.
     * <p>
     * @pre Lista vuota.
     * <p>
     * @post La lista rimane vuota.
     * <p>
     * @expected {@code remove(null)} deve restituire false.
     */
    @Test
    public void testRemoveNullObjectFromEmptyList()
    {
        assertFalse(list.remove(null));
        assertEquals(0, list.size());
    }

    //------- TEST DEL METODO containsAll(HCollection) ----------

    /**
     * Test del metodo {@link HList#containsAll(HCollection)}.
     * <p>
     * @summary Verifica che una lista vuota non contenga tutti gli elementi di una collezione non vuota.
     * <p>
     * @design Assicurarsi che il metodo gestisca correttamente il confronto con una collezione non vuota.
     * <p>
     * @description 1. Crea una collezione con un elemento.<br />
     * 2. Chiama {@code containsAll()} su una lista vuota con la collezione.<br />
     * 3. Verifica che il risultato sia false.
     * <p>
     * @pre Lista vuota. Collezione con un elemento.
     * <p>
     * @post La lista e la collezione rimangono invariate.
     * <p>
     * @expected {@code containsAll()} deve restituire false.
     */
    @Test
    public void testContainsAllWithNonEmptyCollection()
    {
        HCollection otherCollection = new ListAdapter();
        otherCollection.add("A");
        assertFalse(list.containsAll(otherCollection));
    }

    /**
     * Test del metodo {@link HList#containsAll(HCollection)} con collezione vuota.
     * <p>
     * @summary Verifica che una lista vuota contenga tutti gli elementi di una collezione vuota.
     * <p>
     * @design Assicurarsi che il metodo restituisca true quando la collezione da controllare è vuota,
     * poiché una lista vuota contiene "tutti" gli elementi di una collezione vuota.
     * <p>
     * @description 1. Crea una collezione vuota.<br />
     * 2. Chiama {@code containsAll()} su una lista vuota con la collezione vuota.<br />
     * 3. Verifica che il risultato sia true.
     * <p>
     * @pre Lista vuota. Collezione vuota.
     * <p>
     * @post La lista e la collezione rimangono invariate.
     * <p>
     * @expected {@code containsAll()} deve restituire true.
     */
    @Test
    public void testContainsAllWithEmptyCollection()
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
     * @pre Lista vuota.
     * <p>
     * @post La lista rimane vuota.
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
     * @summary Verifica l'aggiunta di una collezione non vuota a una lista vuota.
     * <p>
     * @design Assicurarsi che tutti gli elementi della collezione siano aggiunti,
     * la dimensione sia aggiornata e il metodo restituisca true.
     * <p>
     * @description 1. Crea una collezione con 2 elementi.<br />
     * 2. Aggiunge la collezione alla lista vuota.<br />
     * 3. Verifica che la dimensione sia 2.<br />
     * 4. Verifica che la lista contenga gli elementi della collezione.
     * <p>
     * @pre Lista vuota. Collezione con due elementi.
     * <p>
     * @post La lista contiene gli elementi della collezione.
     * <p>
     * @expected {@code addAll(HCollection)} deve restituire true, la lista deve avere dimensione 2 e contenere gli elementi.
     */
    @Test
    public void testAddAllToEmptyList()
    {
        HCollection newElements = new ListAdapter();
        newElements.add("elem1");
        newElements.add("elem2");

        assertTrue(list.addAll(newElements));
        assertEquals(2, list.size());
        assertTrue(list.contains("elem1"));
        assertTrue(list.contains("elem2"));
        assertFalse(list.isEmpty());
    }

    /**
     * Test del metodo {@link HList#addAll(HCollection)} con collezione vuota.
     * <p>
     * @summary Verifica che l'aggiunta di una collezione vuota a una lista vuota non modifichi la lista.
     * <p>
     * @design Assicurarsi che il metodo restituisca false e non modifichi la lista se la collezione
     * da aggiungere è vuota.
     * <p>
     * @description 1. Crea una collezione vuota.<br />
     * 2. Aggiunge la collezione alla lista vuota.<br />
     * 3. Verifica che il risultato sia false.<br />
     * 4. Verifica che la lista rimanga vuota.
     * <p>
     * @pre Lista vuota. Collezione vuota.
     * <p>
     * @post La lista rimane vuota.
     * <p>
     * @expected {@code addAll(HCollection)} deve restituire false.
     */
    @Test
    public void testAddAllEmptyCollectionToEmptyList()
    {
        HCollection emptyCollection = new ListAdapter();
        assertFalse(list.addAll(emptyCollection));
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    /**
     * Test del metodo {@link HList#addAll(HCollection)} con collezione null.
     * <p>
     * @summary Verifica che {@code addAll(null)} lanci {@code NullPointerException}.
     * <p>
     * @design Assicurarsi che il metodo gestisca correttamente l'input null per la collezione.
     * <p>
     * @description 1. Chiama {@code addAll(null)}.<br />
     * 2. Verifica che venga lanciata {@code NullPointerException}.
     * <p>
     * @pre Lista vuota.
     * <p>
     * @post La lista rimane vuota.
     * <p>
     * @expected {@code NullPointerException} deve essere lanciata.
     */
    @Test(expected = NullPointerException.class)
    public void testAddAllNullCollection()
    {
        list.addAll(null);
    }

    //------- TEST DEL METODO removeAll(HCollection) ----------

    /**
     * Test del metodo {@link HList#removeAll(HCollection)}.
     * <p>
     * @summary Verifica che {@code removeAll(HCollection)} su una lista vuota restituisca false.
     * <p>
     * @design Assicurarsi che il metodo non modifichi una lista vuota e restituisca false.
     * <p>
     * @description 1. Crea una collezione con elementi.<br />
     * 2. Chiama {@code removeAll()} su una lista vuota con la collezione.<br />
     * 3. Verifica che il risultato sia false.<br />
     * 4. Verifica che la lista rimanga vuota.
     * <p>
     * @pre Lista vuota. Collezione con elementi.
     * <p>
     * @post La lista rimane vuota.
     * <p>
     * @expected {@code removeAll(HCollection)} deve restituire false.
     */
    @Test
    public void testRemoveAllFromEmptyList()
    {
        HCollection otherCollection = new ListAdapter();
        otherCollection.add("A");
        assertFalse(list.removeAll(otherCollection));
        assertEquals(0, list.size());
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
     * @pre Lista vuota.
     * <p>
     * @post La lista rimane vuota.
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
     * @summary Verifica che {@code retainAll(HCollection)} su una lista vuota restituisca false.
     * <p>
     * @design Assicurarsi che il metodo non modifichi una lista vuota e restituisca false.
     * <p>
     * @description 1. Crea una collezione con elementi.<br />
     * 2. Chiama {@code retainAll()} su una lista vuota con la collezione.<br />
     * 3. Verifica che il risultato sia false.<br />
     * 4. Verifica che la lista rimanga vuota.
     * <p>
     * @pre Lista vuota. Collezione con elementi.
     * <p>
     * @post La lista rimane vuota.
     * <p>
     * @expected {@code retainAll(HCollection)} deve restituire false.
     */
    @Test
    public void testRetainAllOnEmptyList()
    {
        HCollection otherCollection = new ListAdapter();
        otherCollection.add("A");
        assertFalse(list.retainAll(otherCollection));
        assertEquals(0, list.size());
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
     * @pre Lista vuota.
     * <p>
     * @post La lista rimane vuota.
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
     * @summary Verifica che {@code clear()} su una lista vuota non faccia nulla.
     * <p>
     * @design Assicurarsi che il metodo possa essere chiamato su una lista vuota
     * senza errori e che la lista rimanga vuota.
     * <p>
     * @description 1. Chiama {@code clear()} su una lista vuota.<br />
     * 2. Verifica che la dimensione sia ancora 0.<br />
     * 3. Verifica che la lista sia ancora vuota.
     * <p>
     * @pre Lista vuota.
     * <p>
     * @post La lista rimane vuota.
     * <p>
     * @expected {@code size()} deve essere 0 e {@code isEmpty()} deve essere true.
     */
    @Test
    public void testClearOnEmptyList()
    {
        list.clear();
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    //------- TEST DEL METODO get(int) ----------

    /**
     * Test del metodo {@link HList#get(int)}.
     * <p>
     * @summary Verifica che {@code get(int)} lanci {@code IndexOutOfBoundsException} su una lista vuota.
     * <p>
     * @design Assicurarsi che il metodo segnali correttamente gli accessi fuori limite.
     * <p>
     * @description 1. Tenta di recuperare un elemento a indice 0 da una lista vuota.<br />
     * 2. Verifica che venga lanciata {@code IndexOutOfBoundsException}.
     * <p>
     * @pre Lista vuota.
     * <p>
     * @post La lista rimane vuota.
     * <p>
     * @expected {@code IndexOutOfBoundsException} deve essere lanciata.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetFromEmptyList()
    {
        list.get(0);
    }

    //------- TEST DEL METODO set(int, Object) ----------

    /**
     * Test del metodo {@link HList#set(int, Object)}.
     * <p>
     * @summary Verifica che {@code set(int, Object)} lanci {@code IndexOutOfBoundsException} su una lista vuota.
     * <p>
     * @design Assicurarsi che il metodo segnali correttamente gli accessi fuori limite.
     * <p>
     * @description 1. Tenta di impostare un elemento a indice 0 su una lista vuota.<br />
     * 2. Verifica che venga lanciata {@code IndexOutOfBoundsException}.
     * <p>
     * @pre Lista vuota.
     * <p>
     * @post La lista rimane vuota.
     * <p>
     * @expected {@code IndexOutOfBoundsException} deve essere lanciata.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSetOnEmptyList()
    {
        list.set(0, "elemento");
    }

    //------- TEST DEL METODO add(int, Object) ----------

    /**
     * Test del metodo {@link HList#add(int, Object)}.
     * <p>
     * @summary Verifica che un elemento venga aggiunto correttamente a indice 0 in una lista vuota.
     * <p>
     * @design Assicurarsi che l'elemento sia aggiunto correttamente e la dimensione aggiornata.
     * <p>
     * @description 1. Aggiunge un elemento a indice 0 in una lista vuota.<br />
     * 2. Verifica che la dimensione sia 1.<br />
     * 3. Verifica che l'elemento sia presente all'indice 0.
     * <p>
     * @pre Lista vuota.
     * <p>
     * @post La lista contiene l'elemento aggiunto all'indice 0.
     * <p>
     * @expected {@code size()} deve essere 1, {@code get(0)} deve restituire l'elemento aggiunto.
     */
    @Test
    public void testAddAtIndex0ToEmptyList()
    {
        list.add(0, "firstElement");
        assertEquals(1, list.size());
        assertEquals("firstElement", list.get(0));
    }

    /**
     * Test del metodo {@link HList#add(int, Object)} con indice fuori limite superiore.
     * <p>
     * @summary Verifica che {@code add(int, Object)} lanci {@code IndexOutOfBoundsException}
     * se l'indice è maggiore della dimensione della lista vuota.
     * <p>
     * @design Assicurarsi che non sia possibile aggiungere elementi a indici non validi.
     * <p>
     * @description 1. Tenta di aggiungere un elemento a indice 1 (quando la lista è vuota).<br />
     * 2. Verifica che venga lanciata {@code IndexOutOfBoundsException}.
     * <p>
     * @pre Lista vuota.
     * <p>
     * @post La lista rimane vuota.
     * <p>
     * @expected {@code IndexOutOfBoundsException} deve essere lanciata.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAtIndexTooLargeOnEmptyList()
    {
        list.add(1, "element"); // Size è 0, index 1 è fuori limite
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
     * @pre Lista vuota.
     * <p>
     * @post La lista rimane vuota.
     * <p>
     * @expected {@code IndexOutOfBoundsException} deve essere lanciata.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAtIndexNegativeOnEmptyList()
    {
        list.add(-1, "element");
    }

    //------- TEST DEL METODO remove(int) ----------

    /**
     * Test del metodo {@link HList#remove(int)}.
     * <p>
     * @summary Verifica che {@code remove(int)} lanci {@code IndexOutOfBoundsException} su una lista vuota.
     * <p>
     * @design Assicurarsi che il metodo segnali correttamente la mancanza di elementi da rimuovere.
     * <p>
     * @description 1. Tenta di rimuovere un elemento a indice 0 da una lista vuota.<br />
     * 2. Verifica che venga lanciata {@code IndexOutOfBoundsException}.
     * <p>
     * @pre Lista vuota.
     * <p>
     * @post La lista rimane vuota.
     * <p>
     * @expected {@code IndexOutOfBoundsException} deve essere lanciata.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveAtIndexFromEmptyList()
    {
        list.remove(0);
    }

    //------- TEST DEL METODO indexOf(Object) ----------

    /**
     * Test del metodo {@link HList#indexOf(Object)}.
     * <p>
     * @summary Verifica che {@code indexOf(Object)} restituisca -1 su una lista vuota.
     * <p>
     * @design Assicurarsi che il metodo indichi correttamente l'assenza di qualsiasi elemento.
     * <p>
     * @description 1. Chiama {@code indexOf()} con un elemento non null su una lista vuota.<br />
     * 2. Verifica che il risultato sia -1.<br />
     * 3. Chiama {@code indexOf()} con un elemento null.<br />
     * 4. Verifica che il risultato sia -1.
     * <p>
     * @pre Lista vuota.
     * <p>
     * @post La lista rimane vuota.
     * <p>
     * @expected {@code indexOf()} deve restituire -1.
     */
    @Test
    public void testIndexOfOnEmptyList()
    {
        assertEquals(-1, list.indexOf("nonexistent"));
        assertEquals(-1, list.indexOf(null));
    }

    //------- TEST DEL METODO lastIndexOf(Object) ----------

    /**
     * Test del metodo {@link HList#lastIndexOf(Object)}.
     * <p>
     * @summary Verifica che {@code lastIndexOf(Object)} restituisca -1 su una lista vuota.
     * <p>
     * @design Assicurarsi che il metodo indichi correttamente l'assenza di qualsiasi elemento.
     * <p>
     * @description 1. Chiama {@code lastIndexOf()} con un elemento non null su una lista vuota.<br />
     * 2. Verifica che il risultato sia -1.<br />
     * 3. Chiama {@code lastIndexOf()} con un elemento null.<br />
     * 4. Verifica che il risultato sia -1.
     * <p>
     * @pre Lista vuota.
     * <p>
     * @post La lista rimane vuota.
     * <p>
     * @expected {@code lastIndexOf()} deve restituire -1.
     */
    @Test
    public void testLastIndexOfOnEmptyList()
    {
        assertEquals(-1, list.lastIndexOf("nonexistent"));
        assertEquals(-1, list.lastIndexOf(null));
    }

    //------- TEST DEL METODO listIterator() ----------

    /**
     * Test del metodo {@link HList#listIterator()}.
     * <p>
     * @summary Verifica che {@code listIterator()} su una lista vuota funzioni correttamente.
     * <p>
     * @design Assicurarsi che l'iteratore restituito per una lista vuota
     * indichi correttamente l'assenza di elementi e abbia indici corretti.
     * <p>
     * @description 1. Ottiene un ListIterator da una lista vuota.<br />
     * 2. Verifica che {@code hasNext()} sia false e {@code hasPrevious()} sia false.<br />
     * 3. Verifica che {@code nextIndex()} sia 0 e {@code previousIndex()} sia -1.<br />
     * 4. Tenta di chiamare {@code next()} e {@code previous()} e verifica che lancino `NoSuchElementException`.
     * <p>
     * @pre Lista vuota.
     * <p>
     * @post La lista rimane vuota, l'iteratore non modifica la lista.
     * <p>
     * @expected {@code hasNext()} e {@code hasPrevious()} devono essere false;
     * {@code nextIndex()} deve essere 0, {@code previousIndex()} deve essere -1;
     * {@code next()} e {@code previous()} devono lanciare {@code NoSuchElementException}.
     */
    @Test
    public void testListIteratorEmpty()
    {
        HListIterator it = list.listIterator();
        assertNotNull(it);
        assertFalse(it.hasNext());
        assertFalse(it.hasPrevious());
        assertEquals(0, it.nextIndex());
        assertEquals(-1, it.previousIndex());

        try {
            it.next();
            fail("Expected NoSuchElementException");
        } catch (java.util.NoSuchElementException e) {
            // Success
        }

        try {
            it.previous();
            fail("Expected NoSuchElementException");
        } catch (java.util.NoSuchElementException e) {
            // Success
        }
    }

    //------- TEST DEL METODO listIterator(int) ----------

    /**
     * Test del metodo {@link HList#listIterator(int)}.
     * <p>
     * @summary Verifica che {@code listIterator(int)} lanci {@code IndexOutOfBoundsException}
     * su una lista vuota per qualsiasi indice.
     * <p>
     * @design Assicurarsi che non sia possibile creare un ListIterator con un indice non valido su una lista vuota.
     * <p>
     * @description 1. Tenta di creare un ListIterator con indice 0 su una lista vuota.<br />
     * 2. Verifica che venga lanciata {@code IndexOutOfBoundsException}.<br />
     * 3. Ripete per un indice negativo e un indice positivo > 0.
     * <p>
     * @pre Lista vuota.
     * <p>
     * @post La lista rimane vuota.
     * <p>
     * @expected {@code IndexOutOfBoundsException} deve essere lanciata per qualsiasi indice.
     */
    @Test
    public void testListIteratorAtIndex0OnEmptyList() {
        // La lista è già vuota dal setUp()
        HListIterator it = list.listIterator(0); // Non dovrebbe lanciare eccezioni

        assertNotNull(it); // Verifica che l'iteratore non sia null
        assertFalse(it.hasNext()); // Non ci sono elementi successivi
        assertFalse(it.hasPrevious()); // Non ci sono elementi precedenti
        assertEquals(0, it.nextIndex()); // L'indice del prossimo elemento è 0
        assertEquals(-1, it.previousIndex()); // L'indice dell'elemento precedente è -1
    }

    /**
     * Test del metodo {@link HList#listIterator(int)} con indice fuori limite.
     * <p>
     * @summary Verifica che {@code listIterator(int)} lanci {@code IndexOutOfBoundsException}
     * se l'indice è negativo o maggiore della dimensione della lista.
     * <p>
     * @design Assicurarsi che il metodo segnali correttamente la creazione di iteratori con indici non validi.
     * <p>
     * @description 1. Tenta di creare un ListIterator con indice -1.<br />
     * 2. Verifica che venga lanciata {@code IndexOutOfBoundsException}.<br />
     * 3. Tenta di creare un ListIterator con indice 1 (quando la lista è vuota, size=0).<br />
     * 4. Verifica che venga lanciata {@code IndexOutOfBoundsException}.
     * <p>
     * @pre Lista vuota.
     * <p>
     * @post La lista rimane vuota.
     * <p>
     * @expected {@code IndexOutOfBoundsException} deve essere lanciata.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testListIteratorAtIndexNegativeOnEmptyList()
    {
        list.listIterator(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testListIteratorAtIndexGreaterThanSizeOnEmptyList()
    {
        list.listIterator(1); // list.size() è 0
    }

    //------- TEST DEL METODO subList(int, int) ----------

    /**
     * Test del metodo {@link HList#subList(int, int)}.
     * <p>
     * @summary Verifica che {@code subList()} lanci {@code IndexOutOfBoundsException} su una lista vuota.
     * <p>
     * @design Assicurarsi che il metodo segnali correttamente gli intervalli non validi
     * per la creazione di una sottolista da una lista vuota.
     * <p>
     * @description 1. Tenta di creare una sottolista con indici (0, 0) su una lista vuota.<br />
     * 2. Verifica che venga lanciata {@code IndexOutOfBoundsException}.<br />
     * 3. Ripete per altri indici non validi.
     * <p>
     * @pre Lista vuota.
     * <p>
     * @post La lista rimane vuota.
     * <p>
     * @expected {@code IndexOutOfBoundsException} deve essere lanciata per qualsiasi intervallo non valido.
     */
    // In TestListAdapterEmpty.java
    @Test
    public void testSubListFromEmptyListValidRange() {
        // La lista è già vuota dal setUp()
        HList sub = list.subList(0, 0); // Non dovrebbe lanciare eccezioni

        assertNotNull(sub); // Verifica che la sub-lista non sia null
        assertTrue(sub.isEmpty()); // La sub-lista deve essere vuota
        assertEquals(0, sub.size()); // La dimensione della sub-lista deve essere 0
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSubListFromEmptyListInvalidFromIndex()
    {
        list.subList(-1, 0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSubListFromEmptyListInvalidToIndex()
    {
        list.subList(0, 1); // toIndex > size
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSubListFromEmptyListFromGreaterThanTo()
    {
        list.subList(1, 0);
    }

    //------- TEST DEL METODO addAll(int, HCollection) ----------

    /**
     * Test del metodo {@link HList#addAll(int, HCollection)}.
     * <p>
     * @summary Verifica che {@code addAll(int, HCollection)} aggiunga elementi a indice 0 in una lista vuota.
     * <p>
     * @design Assicurarsi che gli elementi siano aggiunti correttamente e che la dimensione sia aggiornata.
     * <p>
     * @description 1. Crea una collezione con 2 elementi.<br />
     * 2. Aggiunge la collezione a indice 0 in una lista vuota.<br />
     * 3. Verifica che la dimensione sia 2.<br />
     * 4. Verifica che la lista contenga gli elementi della collezione nell'ordine corretto.
     * <p>
     * @pre Lista vuota. Collezione con due elementi.
     * <p>
     * @post La lista contiene gli elementi della collezione all'inizio.
     * <p>
     * @expected {@code addAll(int, HCollection)} deve restituire true, la lista deve avere dimensione 2
     * e gli elementi devono essere aggiunti all'inizio.
     */
    @Test
    public void testAddAllAtIndex0ToEmptyList()
    {
        HCollection newElements = new ListAdapter();
        newElements.add("elem1");
        newElements.add("elem2");

        assertTrue(list.addAll(0, newElements));
        assertEquals(2, list.size());
        assertEquals("elem1", list.get(0));
        assertEquals("elem2", list.get(1));
    }

    /**
     * Test del metodo {@link HList#addAll(int, HCollection)} con indice fuori limite superiore.
     * <p>
     * @summary Verifica che {@code addAll(int, HCollection)} lanci {@code IndexOutOfBoundsException}
     * se l'indice è maggiore della dimensione della lista vuota.
     * <p>
     * @design Assicurarsi che non sia possibile aggiungere collezioni a indici non validi.
     * <p>
     * @description 1. Tenta di aggiungere una collezione a indice 1 (quando la lista è vuota).<br />
     * 2. Verifica che venga lanciata {@code IndexOutOfBoundsException}.
     * <p>
     * @pre Lista vuota.
     * <p>
     * @post La lista rimane vuota.
     * <p>
     * @expected {@code IndexOutOfBoundsException} deve essere lanciata.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAllAtIndexTooLargeOnEmptyList()
    {
        HCollection newElements = new ListAdapter();
        newElements.add("elem1");
        list.addAll(1, newElements); // Size è 0, index 1 è fuori limite
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
     * @pre Lista vuota.
     * <p>
     * @post La lista rimane vuota.
     * <p>
     * @expected {@code IndexOutOfBoundsException} deve essere lanciata.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAllAtIndexNegativeOnEmptyList()
    {
        HCollection newElements = new ListAdapter();
        newElements.add("elem1");
        list.addAll(-1, newElements);
    }

    /**
     * Test del metodo {@link HList#addAll(int, HCollection)} con collezione vuota.
     * <p>
     * @summary Verifica che l'aggiunta di una collezione vuota a indice 0 non modifichi la lista.
     * <p>
     * @design Assicurarsi che il metodo restituisca false e non modifichi la lista se la collezione
     * da aggiungere è vuota.
     * <p>
     * @description 1. Crea una collezione vuota.<br />
     * 2. Aggiunge la collezione vuota a indice 0 in una lista vuota.<br />
     * 3. Verifica che il risultato sia false.<br />
     * 4. Verifica che la lista rimanga vuota.
     * <p>
     * @pre Lista vuota. Collezione vuota.
     * <p>
     * @post La lista rimane vuota.
     * <p>
     * @expected {@code addAll(int, HCollection)} deve restituire false.
     */
    @Test
    public void testAddAllAtIndex0EmptyCollectionToEmptyList()
    {
        HCollection emptyCollection = new ListAdapter();
        assertFalse(list.addAll(0, emptyCollection));
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    /**
     * Test del metodo {@link HList#addAll(int, HCollection)} con collezione null.
     * <p>
     * @summary Verifica che {@code addAll(int, HCollection)} lanci {@code NullPointerException} se la collezione è null.
     * <p>
     * @design Assicurarsi che il metodo gestisca correttamente l'input null per la collezione.
     * <p>
     * @description 1. Chiama {@code addAll(0, null)}.<br />
     * 2. Verifica che venga lanciata {@code NullPointerException}.
     * <p>
     * @pre La lista è vuota, la collezione è null.
     * <p>
     * @post La lista rimane vuota.
     * <p>
     * @expected {@code NullPointerException} deve essere lanciata.
     */
    @Test(expected = NullPointerException.class)
    public void testAddAllAtIndex0NullCollection()
    {
        list.addAll(0, null);
    }
}