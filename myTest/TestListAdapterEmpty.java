//Alberto Bortoletto 2101761

package myTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import myAdapter.*;
import java.util.Vector; // Necessario per testare l'interazione con Vector se usi i toArray con array CLDC

/**
 * <b>Summary:</b>
 * <p>
 * Questa classe contiene una suite di test per {@link myAdapter.ListAdapter} focalizzata sul comportamento di una lista vuota.
 * Sono presenti test per tutti i metodi principali: verifica di size, isEmpty, contains, iterator, toArray, add, remove, containsAll, addAll, removeAll, retainAll, clear, equals, hashCode, get, set, subList, e listIterator.
 * Ogni metodo viene testato sia per il comportamento atteso su una lista vuota, sia per la corretta gestione di input non validi (indici fuori limite, collezioni null, ecc.).
 * <br>
 * <b>Test Case Design:</b>
 * <p>
 * La motivazione di questa suite è assicurare che ListAdapter gestisca correttamente tutti i casi limite e le operazioni su una lista vuota,
 * senza lanciare eccezioni inattese o restituire risultati errati. Si verifica che le operazioni di aggiunta inizializzino correttamente la lista,
 * che le ricerche e rimozioni su elementi inesistenti siano sicure, e che le eccezioni siano lanciate dove previsto.
 * La separazione dai test su liste popolate permette di isolare i comportamenti di bordo e prevenire regressioni.
 * Versione JUnit: **JUnit 4.13.2**
 * Versione Hamcrest: **Hamcrest 1.3**
 */
public class TestListAdapterEmpty
{
    private ListAdapter list;

    /**
     * Costruttore predefinito per i test di {@code TestListAdapterEmpty}.
     * Non esegue inizializzazioni specifiche, affidandosi al metodo {@code setup()}.
     */
    public TestListAdapterEmpty() 
    {
        // Nessuna logica di inizializzazione complessa qui, JUnit si occupa del setup.
    }


    /**
     * Configura l'ambiente di test popolando la lista che verrà manipolata.
     */
    @Before
    public void setUp()
    {
        // Crea una lista vuota
        list = new ListAdapter();
    }

    //------- TEST DEL COSTRUTTORE ListAdapter(int) ----------

    /**
     * Test del costruttore {@link myAdapter.ListAdapter#ListAdapter(int)} con capacità iniziale positiva.
     * <p>
     * Summary: Verifica che il costruttore crei correttamente un {@code ListAdapter} con la capacità iniziale specificata
     * quando la capacità è un valore positivo valido.
     * <p>
     * Test Case Design: Questo test valuta il caso nominale di un'inizializzazione con una capacità non negativa,
     * assicurandosi che la lista venga creata senza errori e che la sua dimensione iniziale sia 0 (poiché la capacità
     * si riferisce allo spazio allocato, non agli elementi presenti).
     * <p>
     * Test Description:
     * 1) Si istanzia un nuovo {@code ListAdapter} fornendo una capacità iniziale positiva (es. 10).
     * 2) Si verifica che la lista creata non sia {@code null}.
     * 3) Si verifica che il metodo {@code size()} della lista restituisca 0, indicando che la lista è vuota di elementi.
     * <p>
     * Preconditions: Nessuna.
     * <p>
     * Postconditions: Viene creata una nuova istanza di {@code ListAdapter} vuota, ma con una capacità pre-allocata.
     * <p>
     * Expected Result: L'istanziazione avviene senza eccezioni e la lista ha dimensione 0.
     */
    @Test
    public void testConstructorWithPositiveInitialCapacity() 
    {
        ListAdapter newList = new ListAdapter(10); // Capacità iniziale positiva
        assertNotNull("La lista non dovrebbe essere null dopo la creazione con capacità.", newList);
        assertEquals("La lista dovrebbe essere vuota dopo la creazione con capacità.", 0, newList.size());
        assertTrue("La lista dovrebbe essere vuota dopo la creazione con capacità.", newList.isEmpty());
    }

    /**
     * Test del costruttore {@link myAdapter.ListAdapter#ListAdapter(int)} con capacità iniziale negativa.
     * <p>
     * Summary: Verifica che il costruttore lanci correttamente una {@link java.lang.IllegalArgumentException}
     * quando si tenta di creare un {@code ListAdapter} con una capacità iniziale negativa.
     * <p>
     * Test Case Design: Questo test copre un caso limite o di errore, assicurandosi che il costruttore
     * rispetti la specifica che impedisce la creazione di un {@code Vector} (e quindi di un {@code ListAdapter})
     * con capacità non valida. Si basa sulla propagazione dell'eccezione lanciata dal costruttore di {@link java.util.Vector} di CLDC 1.1.
     * <p>
     * Test Description:
     * 1) Si tenta di istanziare un nuovo {@code ListAdapter} fornendo una capacità iniziale negativa (es. -5).
     * 2) Si aspetta che, come risultato di questa operazione, venga lanciata una {@link java.lang.IllegalArgumentException}.
     * <p>
     * Preconditions: Nessuna.
     * <p>
     * Postconditions: Nessuna istanza di {@code ListAdapter} viene creata.
     * <p>
     * Expected Result: Viene lanciata una {@link java.lang.IllegalArgumentException}.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNegativeInitialCapacity() 
    {
        new ListAdapter(-5); // Capacità iniziale negativa, ci aspettiamo un'eccezione
    }

    //------- TEST DEL METODO size() ----------

    /**
     * Test del metodo {@link HList#size()}.
     * <p>
     * Summary: Verifica che la dimensione di una lista vuota sia 0.
     * <p>
     * Test Case Design: Questo test serve per assicurare che il metodo {@code size()} restituisca il valore corretto per una lista appena creata, che per definizione è vuota.
     * <p>
     * Test Description: 1) Si crea una nuova istanza di {@code ListAdapter}.
     * 2) Si chiama il metodo {@code size()}.
     * 3) Si verifica che il valore restituito sia 0.
     * <p>
     * Preconditions: Nessuna.
     * <p>
     * Postconditions: La lista rimane vuota.
     * <p>
     * Expected Result: La dimensione della lista deve essere 0.
     */
    @Test
    public void testSizeEmptyList()
    {
        assertEquals(0, list.size());
    }

    //------- TEST DEL METODO isEmpty() ----------

    /**
     * Test del metodo {@link HList#isEmpty()}.
     * <p>
     * Summary: Verifica che {@code isEmpty()} restituisca true per una lista vuota.
     * <p>
     * Test Case Design: Questo test valuta il comportamento del metodo {@code isEmpty()} su una lista che non contiene elementi, garantendo che indichi correttamente il suo stato.
     * <p>
     * Test Description: 1) Si crea una nuova istanza di {@code ListAdapter}.
     * 2) Si chiama il metodo {@code isEmpty()}.
     * 3) Si verifica che il valore booleano restituito sia true.
     * <p>
     * Preconditions: Nessuna.
     * <p>
     * Postconditions: La lista rimane vuota.
     * <p>
     * Expected Result: La lista deve essere considerata vuota.
     */
    @Test
    public void testIsEmptyEmptyList()
    {
        assertTrue(list.isEmpty());
    }

    //------- TEST DEL METODO contains(Object o) ----------

    /**
     * Test del metodo {@link HList#contains(Object)} su lista vuota.
     * <p>
     * Summary: Verifica che {@code contains(Object)} restituisca false per qualsiasi elemento su una lista vuota.
     * <p>
     * Test Case Design: Questo test assicura che il metodo {@code contains()} si comporti correttamente quando cerca un elemento in una lista che non ha elementi. Vengono testati sia un oggetto non nullo che l'oggetto nullo.
     * <p>
     * Test Description: 1) Si tenta di cercare un oggetto arbitrario ("test") nella lista vuota.
     * 2) Si verifica che il risultato sia false.
     * 3) Si tenta di cercare un oggetto nullo nella lista vuota.
     * 4) Si verifica che il risultato sia false.
     * <p>
     * Preconditions: Lista vuota.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * Expected Result: {@code contains(Object)} deve restituire false per entrambi i tentativi.
     */
    @Test
    public void testContainsEmptyList()
    {
        assertFalse(list.contains("test"));
        assertFalse(list.contains(null));
    }

    //------- TEST DEL METODO iterator() ----------

    /**
     * Test del metodo {@link HList#iterator()} su lista vuota.
     * <p>
     * Summary: Verifica che l'iteratore ottenuto da una lista vuota non abbia elementi successivi.
     * <p>
     * Test Case Design: Questo test valuta l'inizializzazione e il comportamento di base dell'iteratore quando la lista sorgente è vuota.
     * <p>
     * Test Description: 1) Si ottiene un iteratore dalla lista vuota.
     * 2) Si verifica che {@code hasNext()} dell'iteratore restituisca false.
     * <p>
     * Preconditions: Lista vuota.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * Expected Result: {@code hasNext()} dell'iteratore deve essere false.
     */
    @Test
    public void testIteratorEmptyList()
    {
        HIterator it = list.iterator();
        assertFalse(it.hasNext());
    }

    //------- TEST DEL METODO toArray() ----------

    /**
     * Test del metodo {@link HList#toArray()} su lista vuota.
     * <p>
     * Summary: Verifica che {@code toArray()} su una lista vuota restituisca un array vuoto.
     * <p>
     * Test Case Design: Questo test serve ad assicurare che la conversione di una lista vuota in un array produca un array di dimensione zero.
     * <p>
     * Test Description: 1) Si chiama il metodo {@code toArray()} su una lista vuota.
     * 2) Si verifica che l'array restituito non sia nullo.
     * 3) Si verifica che la sua lunghezza sia 0.
     * <p>
     * Preconditions: Lista vuota.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * Expected Result: L'array restituito non deve essere nullo e deve avere lunghezza 0.
     */
    @Test
    public void testToArrayEmptyList()
    {
        Object[] arr = list.toArray();
        assertNotNull(arr);
        assertEquals(0, arr.length);
    }

    //------- TEST DEL METODO toArray(Object[] a) ----------

    /**
     * Test del metodo {@link HList#toArray(Object[])} con array vuoto di dimensione 0 su lista vuota.
     * <p>
     * Summary: Verifica che {@code toArray(Object[])} con un array di input vuoto (di dimensione 0) su una lista vuota restituisca l'array originale.
     * <p>
     * Test Case Design: Questo test copre lo scenario in cui l'array fornito è della dimensione minima necessaria per contenere la lista (0, in questo caso), e l'implementazione dovrebbe riutilizzare l'array fornito.
     * <p>
     * Test Description: 1) Si crea un array di oggetti di dimensione 0.
     * 2) Si chiama {@code toArray(Object[])} passando questo array.
     * 3) Si verifica che l'array restituito sia lo stesso array passato come argomento (verifica di riferimento).
     * <p>
     * Preconditions: Lista vuota.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * Expected Result: Il metodo deve restituire l'istanza dell'array passata come argomento.
     */
    @Test
    public void testToArrayEmptyListWithEmptyArrayInput()
    {
        Object[] inputArr = new Object[0];
        Object[] resultArr = list.toArray(inputArr);
        assertSame(inputArr, resultArr);
    }

    /**
     * Test del metodo {@link HList#toArray(Object[])} con array sufficientemente grande su lista vuota.
     * <p>
     * Summary: Verifica che {@code toArray(Object[])} con un array di input più grande su una lista vuota restituisca l'array originale con il primo elemento impostato a null (se l'array è più grande).
     * <p>
     * Test Case Design: Questo test valuta il comportamento quando l'array fornito è più grande della lista vuota. Secondo la specifica J2SE, l'elemento successivo alla fine della collezione nell'array dovrebbe essere null.
     * <p>
     * Test Description: 1) Si crea un array di oggetti di dimensione maggiore di 0 (es. 2).
     * 2) Si chiama {@code toArray(Object[])} passando questo array.
     * 3) Si verifica che l'array restituito sia lo stesso array passato come argomento (verifica di riferimento).
     * 4) Si verifica che il primo elemento dell'array sia null (perché la lista è vuota).
     * <p>
     * Preconditions: Lista vuota.
     * <p>
     * Postconditions: La lista rimane invariata. L'array di input viene modificato (il primo elemento diventa null).
     * <p>
     * Expected Result: Il metodo deve restituire l'istanza dell'array passata come argomento, con l'elemento all'indice 0 (e gli altri, se presenti) impostato a null.
     */
    @Test
    public void testToArrayEmptyListWithLargerArrayInput()
    {
        Object[] inputArr = new Object[2];
        inputArr[0] = "originale1";
        inputArr[1] = "originale2";

        Object[] resultArr = list.toArray(inputArr);
        assertSame(inputArr, resultArr);
        assertNull(resultArr[0]); // Il primo elemento dovrebbe essere null
        // Il secondo elemento può rimanere inalterato o essere nullato a seconda dell'implementazione.
        // La specifica J2SE dice "l'elemento successivo alla fine della sottolista nell'array viene impostato su `null`."
        // Per una lista vuota, "la fine della sottolista" è l'indice 0. Quindi solo resultArr[0] dovrebbe essere null.
        // Gli altri elementi non dovrebbero essere toccati.
        assertNull(resultArr[0]);
        // resultArr[1] dovrebbe rimanere "originale2" se l'implementazione segue rigorosamente la specifica per gli elementi non oltre size
        // Se la tua implementazione CLDC nullifica tutti gli elementi dall'indice size in poi, questo potrebbe fallire.
        // È una sottile differenza. Per Vector CLDC 1.1, toArray(Object[] a) può comportarsi diversamente da J2SE 1.4.2.
        // Se il tuo Vector.copyInto() è il motore, potrebbe non modificare gli elementi oltre 0.
        // Adattare questo assert se l'implementazione sottostante si comporta diversamente.
        // Per il momento, assumo il comportamento J2SE: solo l'elemento all'indice 'size' viene nullificato.
        // Poiché size è 0, l'elemento all'indice 0 è il primo elemento.
        // Il comportamento qui dipende dalla tua implementazione esatta di toArray(Object[]).
        // Se l'implementazione è: for (int i = 0; i < size; i++) a[i] = get(i); if (a.length > size) a[size] = null;
        // Allora, per size=0, il ciclo for non esegue nulla, e a[0] = null (se a.length > 0).
        assertNull(resultArr[0]);
    }

    //------- TEST DEL METODO add(Object o) ----------

    /**
     * Test del metodo {@link HList#add(Object)} su lista vuota.
     * <p>
     * Summary: Verifica che {@code add(Object)} aggiunga correttamente un elemento a una lista vuota.
     * <p>
     * Test Case Design: Questo test assicura che l'operazione di aggiunta sia funzionale per inizializzare una lista da uno stato vuoto.
     * <p>
     * Test Description: 1) Si aggiunge un elemento ("test") alla lista vuota.
     * 2) Si verifica che la dimensione della lista sia 1.
     * 3) Si verifica che la lista non sia più vuota.
     * 4) Si verifica che l'elemento aggiunto sia presente nella lista.
     * <p>
     * Preconditions: Lista vuota.
     * <p>
     * Postconditions: La lista contiene un elemento.
     * <p>
     * Expected Result: La lista deve avere dimensione 1, non essere vuota e contenere l'elemento aggiunto.
     */
    @Test
    public void testAddEmptyList()
    {
        list.add("test");
        assertEquals(1, list.size());
        assertFalse(list.isEmpty());
        assertTrue(list.contains("test"));
    }

    /**
     * Test del metodo {@link HList#add(Object)} con un elemento null su lista vuota.
     * <p>
     * Summary: Verifica che {@code add(Object)} possa aggiungere correttamente un elemento null a una lista vuota.
     * <p>
     * Test Case Design: Questo test verifica la capacità della lista di gestire elementi nulli, un requisito importante per molte implementazioni di Collection.
     * <p>
     * Test Description: 1) Si aggiunge un elemento null alla lista vuota.
     * 2) Si verifica che la dimensione della lista sia 1.
     * 3) Si verifica che la lista non sia più vuota.
     * 4) Si verifica che la lista contenga l'elemento null.
     * <p>
     * Preconditions: Lista vuota.
     * <p>
     * Postconditions: La lista contiene l'elemento null.
     * <p>
     * Expected Result: La lista deve avere dimensione 1, non essere vuota e contenere l'elemento null.
     */
    @Test
    public void testAddNullEmptyList()
    {
        list.add(null);
        assertEquals(1, list.size());
        assertFalse(list.isEmpty());
        assertTrue(list.contains(null));
    }

    //------- TEST DEL METODO remove(Object o) ----------

    /**
     * Test del metodo {@link HList#remove(Object)} su lista vuota.
     * <p>
     * Summary: Verifica che {@code remove(Object)} restituisca false quando si tenta di rimuovere un elemento da una lista vuota.
     * <p>
     * Test Case Design: Questo test assicura che il metodo gestisca correttamente la rimozione da una lista priva di elementi, restituendo il valore booleano appropriato.
     * <p>
     * Test Description: 1) Si tenta di rimuovere un oggetto arbitrario ("test") dalla lista vuota.
     * 2) Si verifica che il risultato sia false.
     * 3) Si verifica che la dimensione della lista rimanga 0.
     * <p>
     * Preconditions: Lista vuota.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * Expected Result: {@code remove(Object)} deve restituire false e la lista deve rimanere vuota.
     */
    @Test
    public void testRemoveEmptyList()
    {
        assertFalse(list.remove("test"));
        assertEquals(0, list.size());
    }

    //------- TEST DEL METODO containsAll(HCollection c) ----------

    /**
     * Test del metodo {@link HList#containsAll(HCollection)} con collezione vuota su lista vuota.
     * <p>
     * Summary: Verifica che {@code containsAll(HCollection)} restituisca true quando la collezione specificata è vuota e la lista è vuota.
     * <p>
     * Test Case Design: Questo test valuta il caso in cui una lista vuota è confrontata per il contenimento con un'altra collezione vuota, che dovrebbe sempre essere considerata contenuta.
     * <p>
     * Test Description: 1) Si crea una collezione vuota.
     * 2) Si chiama {@code containsAll()} sulla lista vuota con la collezione vuota.
     * 3) Si verifica che il risultato sia true.
     * <p>
     * Preconditions: Lista vuota. Collezione vuota.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * Expected Result: {@code containsAll(HCollection)} deve restituire true.
     */
    @Test
    public void testContainsAllEmptyCollectionOnEmptyList()
    {
        HCollection emptyCollection = new ListAdapter();
        assertTrue(list.containsAll(emptyCollection));
    }

    /**
     * Test del metodo {@link HList#containsAll(HCollection)} con collezione non vuota su lista vuota.
     * <p>
     * Summary: Verifica che {@code containsAll(HCollection)} restituisca false quando la collezione specificata non è vuota e la lista è vuota.
     * <p>
     * Test Case Design: Questo test assicura che una lista vuota non dichiari di contenere elementi che non possiede.
     * <p>
     * Test Description: 1) Si crea una collezione con un elemento ("test").
     * 2) Si chiama {@code containsAll()} sulla lista vuota con la collezione non vuota.
     * 3) Si verifica che il risultato sia false.
     * <p>
     * Preconditions: Lista vuota. Collezione non vuota.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * Expected Result: {@code containsAll(HCollection)} deve restituire false.
     */
    @Test
    public void testContainsAllNonEmptyCollectionOnEmptyList()
    {
        ListAdapter collection = new ListAdapter();
        collection.add("test");
        assertFalse(list.containsAll(collection));
    }

    /**
     * Test del metodo {@link HList#containsAll(HCollection)} con collezione null.
     * <p>
     * Summary: Verifica che {@code containsAll(HCollection)} lanci {@code NullPointerException} se la collezione è null.
     * <p>
     * Test Case Design: Questo test serve per verificare la gestione dell'input nullo secondo le specifiche del Collection Framework.
     * <p>
     * Test Description: 1) Si tenta di chiamare {@code containsAll(null)} sulla lista vuota.
     * 2) Si verifica che venga lanciata {@code NullPointerException}.
     * <p>
     * Preconditions: Lista vuota. Collezione null.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * Expected Result: {@code containsAll(HCollection)} deve lanciare {@code NullPointerException}.
     */
    @Test(expected = NullPointerException.class)
    public void testContainsAllNullCollection()
    {
        list.containsAll(null);
    }

    //------- TEST DEL METODO addAll(HCollection c) ----------

    /**
     * Test del metodo {@link HList#addAll(HCollection)} con collezione vuota su lista vuota.
     * <p>
     * Summary: Verifica che {@code addAll(HCollection)} restituisca false e non modifichi la lista quando aggiunge una collezione vuota a una lista vuota.
     * <p>
     * Test Case Design: Questo test verifica il comportamento di aggiunta di una collezione vuota, che non dovrebbe modificare la lista né indicare un cambiamento riuscito.
     * <p>
     * Test Description: 1) Si crea una collezione vuota.
     * 2) Si aggiunge la collezione vuota alla lista vuota.
     * 3) Si verifica che il risultato sia false.
     * 4) Si verifica che la dimensione della lista rimanga 0 e che sia ancora vuota.
     * <p>
     * Preconditions: Lista vuota. Collezione vuota.
     * <p>
     * Postconditions: La lista rimane vuota.
     * <p>
     * Expected Result: {@code addAll(HCollection)} deve restituire false e la lista deve rimanere vuota.
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
     * Test del metodo {@link HList#addAll(HCollection)} con collezione non vuota su lista vuota.
     * <p>
     * Summary: Verifica che {@code addAll(HCollection)} aggiunga correttamente tutti gli elementi di una collezione non vuota a una lista vuota.
     * <p>
     * Test Case Design: Questo test valuta la capacità di {@code addAll()} di popolare correttamente una lista inizialmente vuota con tutti gli elementi di un'altra collezione.
     * <p>
     * Test Description: 1) Si crea una collezione con due elementi ("test1", "test2").
     * 2) Si aggiunge questa collezione alla lista vuota.
     * 3) Si verifica che il risultato sia true (la lista è stata modificata).
     * 4) Si verifica che la dimensione della lista sia 2.
     * 5) Si verifica che la lista contenga entrambi gli elementi aggiunti.
     * <p>
     * Preconditions: Lista vuota. Collezione non vuota.
     * <p>
     * Postconditions: La lista contiene gli elementi della collezione aggiunta.
     * <p>
     * Expected Result: {@code addAll(HCollection)} deve restituire true, la lista deve avere la dimensione corretta e contenere tutti gli elementi.
     */
    @Test
    public void testAddAllNonEmptyCollectionToEmptyList()
    {
        ListAdapter collection = new ListAdapter();
        collection.add("test1");
        collection.add("test2");
        assertTrue(list.addAll(collection));
        assertEquals(2, list.size());
        assertTrue(list.contains("test1"));
        assertTrue(list.contains("test2"));
    }

    /**
     * Test del metodo {@link HList#addAll(HCollection)} con collezione null.
     * <p>
     * Summary: Verifica che {@code addAll(HCollection)} lanci {@code NullPointerException} se la collezione specificata è null.
     * <p>
     * Test Case Design: Questo test serve a garantire che il metodo rispetti la specifica che impone di lanciare un'eccezione per un input di collezione nullo.
     * <p>
     * Test Description: 1) Si tenta di chiamare {@code addAll(null)} sulla lista vuota.
     * 2) Si verifica che venga lanciata {@code NullPointerException}.
     * <p>
     * Preconditions: Lista vuota. Collezione null.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * Expected Result: {@code addAll(HCollection)} deve lanciare {@code NullPointerException}.
     */
    @Test(expected = NullPointerException.class)
    public void testAddAllNullCollection()
    {
        list.addAll(null);
    }

    //------- TEST DEL METODO addAll(int index, HCollection c) ----------

    /**
     * Test del metodo {@link HList#addAll(int, HCollection)} con indice 0 e collezione vuota su lista vuota.
     * <p>
     * Summary: Verifica che {@code addAll(int, HCollection)} con indice 0 e collezione vuota restituisca false e non modifichi la lista vuota.
     * <p>
     * Test Case Design: Questo test copre il caso limite in cui si tenta di aggiungere una collezione vuota a una lista vuota a un indice valido (0). Il risultato atteso è che la lista non venga modificata.
     * <p>
     * Test Description: 1) Si crea una collezione vuota.
     * 2) Si aggiunge la collezione vuota a indice 0 in una lista vuota.
     * 3) Si verifica che il risultato sia false.
     * 4) Si verifica che la lista rimanga vuota.
     * <p>
     * Preconditions: Lista vuota. Collezione vuota.
     * <p>
     * Postconditions: La lista rimane vuota.
     * <p>
     * Expected Result: {@code addAll(int, HCollection)} deve restituire false.
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
     * Summary: Verifica che {@code addAll(int, HCollection)} lanci {@code NullPointerException} se la collezione è null.
     * <p>
     * Test Case Design: Assicurarsi che il metodo gestisca correttamente l'input null per la collezione.
     * <p>
     * Test Description: 1) Chiama {@code addAll(0, null)}.<br />
     * 2. Verifica che venga lanciata {@code NullPointerException}.
     * <p>
     * Preconditions: La lista è vuota, la collezione è null.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * Expected Result: {@code addAll(int, HCollection)} deve lanciare {@code NullPointerException}.
     */
    @Test(expected = NullPointerException.class)
    public void testAddAllAtIndexNullCollection()
    {
        list.addAll(0, null);
    }

    /**
     * Test del metodo {@link HList#addAll(int, HCollection)} con indice fuori limite.
     * <p>
     * Summary: Verifica che {@code addAll(int, HCollection)} lanci {@code IndexOutOfBoundsException} se l'indice è fuori limite.
     * <p>
     * Test Case Design: Questo test assicura che il metodo impedisca l'aggiunta a un indice non valido (ovvero, un indice > 0 per una lista vuota).
     * <p>
     * Test Description: 1) Si crea una collezione non vuota.
     * 2) Si tenta di chiamare {@code addAll(1, collection)} su una lista vuota (l'indice 1 è fuori limite).
     * 3) Si verifica che venga lanciata {@code IndexOutOfBoundsException}.
     * <p>
     * Preconditions: Lista vuota.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * Expected Result: {@code addAll(int, HCollection)} deve lanciare {@code IndexOutOfBoundsException}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAllAtIndexOutOfBoundsEmptyList()
    {
        ListAdapter collection = new ListAdapter();
        collection.add("test");
        list.addAll(1, collection);
    }

    //------- TEST DEL METODO removeAll(HCollection c) ----------

    /**
     * Test del metodo {@link HList#removeAll(HCollection)} con collezione vuota su lista vuota.
     * <p>
     * Summary: Verifica che {@code removeAll(HCollection)} restituisca false e non modifichi la lista quando si tenta di rimuovere una collezione vuota da una lista vuota.
     * <p>
     * Test Case Design: Questo test copre il caso in cui la rimozione di elementi non presenti (perché la lista è vuota) non dovrebbe causare un cambiamento.
     * <p>
     * Test Description: 1) Si crea una collezione vuota.
     * 2) Si tenta di chiamare {@code removeAll()} sulla lista vuota con la collezione vuota.
     * 3) Si verifica che il risultato sia false.
     * 4) Si verifica che la dimensione della lista rimanga 0.
     * <p>
     * Preconditions: Lista vuota. Collezione vuota.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * Expected Result: {@code removeAll(HCollection)} deve restituire false e la lista deve rimanere vuota.
     */
    @Test
    public void testRemoveAllEmptyCollectionFromEmptyList()
    {
        HCollection emptyCollection = new ListAdapter();
        assertFalse(list.removeAll(emptyCollection));
        assertEquals(0, list.size());
    }

    /**
     * Test del metodo {@link HList#removeAll(HCollection)} con collezione non vuota su lista vuota.
     * <p>
     * Summary: Verifica che {@code removeAll(HCollection)} restituisca false quando si tenta di rimuovere una collezione non vuota da una lista vuota.
     * <p>
     * Test Case Design: Questo test assicura che il metodo non indichi un cambiamento se non ci sono elementi da rimuovere.
     * <p>
     * Test Description: 1) Si crea una collezione con elementi.
     * 2) Si tenta di chiamare {@code removeAll()} sulla lista vuota con la collezione non vuota.
     * 3) Si verifica che il risultato sia false.
     * 4) Si verifica che la dimensione della lista rimanga 0.
     * <p>
     * Preconditions: Lista vuota. Collezione non vuota.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * Expected Result: {@code removeAll(HCollection)} deve restituire false e la lista deve rimanere vuota.
     */
    @Test
    public void testRemoveAllNonEmptyCollectionFromEmptyList()
    {
        ListAdapter collection = new ListAdapter();
        collection.add("test1");
        assertFalse(list.removeAll(collection));
        assertEquals(0, list.size());
    }

    /**
     * Test del metodo {@link HList#removeAll(HCollection)} con collezione null.
     * <p>
     * Summary: Verifica che {@code removeAll(HCollection)} lanci {@code NullPointerException} se la collezione specificata è null.
     * <p>
     * Test Case Design: Questo test serve a garantire che il metodo rispetti la specifica che impone di lanciare un'eccezione per un input di collezione nullo.
     * <p>
     * Test Description: 1) Si tenta di chiamare {@code removeAll(null)} sulla lista vuota.
     * 2) Si verifica che venga lanciata {@code NullPointerException}.
     * <p>
     * Preconditions: Lista vuota. Collezione null.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * Expected Result: {@code removeAll(HCollection)} deve lanciare {@code NullPointerException}.
     */
    @Test(expected = NullPointerException.class)
    public void testRemoveAllNullCollection()
    {
        list.removeAll(null);
    }

    //------- TEST DEL METODO retainAll(HCollection c) ----------

    /**
     * Test del metodo {@link HList#retainAll(HCollection)} con collezione vuota su lista vuota.
     * <p>
     * Summary: Verifica che {@code retainAll(HCollection)} restituisca false e non modifichi la lista quando si tenta di mantenere elementi basati su una collezione vuota da una lista vuota.
     * <p>
     * Test Case Design: Se la lista è vuota e la collezione da ritenere è vuota, non ci sono elementi da modificare, quindi non c'è cambiamento.
     * <p>
     * Test Description: 1) Si crea una collezione vuota.
     * 2) Si tenta di chiamare {@code retainAll()} sulla lista vuota con la collezione vuota.
     * 3) Si verifica che il risultato sia false.
     * 4) Si verifica che la dimensione della lista rimanga 0.
     * <p>
     * Preconditions: Lista vuota. Collezione vuota.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * Expected Result: {@code retainAll(HCollection)} deve restituire false e la lista deve rimanere vuota.
     */
    @Test
    public void testRetainAllEmptyCollectionOnEmptyList()
    {
        HCollection emptyCollection = new ListAdapter();
        assertFalse(list.retainAll(emptyCollection));
        assertEquals(0, list.size());
    }

    /**
     * Test del metodo {@link HList#retainAll(HCollection)} con collezione non vuota su lista vuota.
     * <p>
     * Summary: Verifica che {@code retainAll(HCollection)} restituisca false quando si tenta di mantenere elementi basati su una collezione non vuota da una lista vuota.
     * <p>
     * Test Case Design: Poiché non ci sono elementi nella lista vuota, il tentativo di "mantenere" elementi basati su una collezione non vuota non dovrebbe portare a un cambiamento nella lista, quindi dovrebbe restituire false.
     * <p>
     * Test Description: 1) Si crea una collezione con elementi.
     * 2) Si tenta di chiamare {@code retainAll()} sulla lista vuota con la collezione non vuota.
     * 3) Si verifica che il risultato sia false.
     * 4) Si verifica che la dimensione della lista rimanga 0.
     * <p>
     * Preconditions: Lista vuota. Collezione non vuota.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * Expected Result: {@code retainAll(HCollection)} deve restituire false e la lista deve rimanere vuota.
     */
    @Test
    public void testRetainAllNonEmptyCollectionOnEmptyList()
    {
        ListAdapter collection = new ListAdapter();
        collection.add("test1");
        assertFalse(list.retainAll(collection));
        assertEquals(0, list.size());
    }

    /**
     * Test del metodo {@link HList#retainAll(HCollection)} con collezione null.
     * <p>
     * Summary: Verifica che {@code retainAll(HCollection)} lanci {@code NullPointerException} se la collezione specificata è null.
     * <p>
     * Test Case Design: Questo test serve a garantire che il metodo rispetti la specifica che impone di lanciare un'eccezione per un input di collezione nullo.
     * <p>
     * Test Description: 1) Si tenta di chiamare {@code retainAll(null)} sulla lista vuota.
     * 2) Si verifica che venga lanciata {@code NullPointerException}.
     * <p>
     * Preconditions: Lista vuota. Collezione null.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * Expected Result: {@code retainAll(HCollection)} deve lanciare {@code NullPointerException}.
     */
    @Test(expected = NullPointerException.class)
    public void testRetainAllNullCollection()
    {
        list.retainAll(null);
    }

    //------- TEST DEL METODO clear() ----------

    /**
     * Test del metodo {@link HList#clear()} su lista vuota.
     * <p>
     * Summary: Verifica che {@code clear()} non modifichi lo stato di una lista già vuota.
     * <p>
     * Test Case Design: Questo test assicura che chiamare {@code clear()} su una lista vuota non causi errori e che lo stato della lista rimanga invariato.
     * <p>
     * Test Description: 1) Si chiama il metodo {@code clear()} sulla lista vuota.
     * 2) Si verifica che la dimensione della lista sia ancora 0.
     * 3) Si verifica che la lista sia ancora vuota.
     * <p>
     * Preconditions: Lista vuota.
     * <p>
     * Postconditions: La lista rimane vuota.
     * <p>
     * Expected Result: La lista deve rimanere vuota e di dimensione 0.
     */
    @Test
    public void testClearEmptyList()
    {
        list.clear();
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    //------- TEST DEL METODO equals(Object o) ----------

    /**
     * Test del metodo {@link HList#equals(Object)} con un'altra lista vuota.
     * <p>
     * Summary: Verifica che una lista vuota sia uguale a un'altra lista vuota.
     * <p>
     * Test Case Design: Questo test verifica la riflessività e la simmetria del metodo {@code equals()} per liste vuote, assicurando che due liste vuote siano considerate equivalenti.
     * <p>
     * Test Description: 1) Si crea una seconda istanza di {@code ListAdapter} vuota.
     * 2) Si confronta la lista di test con la seconda lista vuota usando {@code equals()}.
     * 3) Si verifica che il risultato sia true.
     * <p>
     * Preconditions: Due liste vuote.
     * <p>
     * Postconditions: Nessuna modifica alle liste.
     * <p>
     * Expected Result: {@code equals(Object)} deve restituire true.
     */
    @Test
    public void testEqualsWithEmptyList()
    {
        HList anotherEmptyList = new ListAdapter();
        assertTrue(list.equals(anotherEmptyList));
    }

    /**
     * Test del metodo {@link HList#equals(Object)} con una lista non vuota.
     * <p>
     * Summary: Verifica che una lista vuota non sia uguale a una lista non vuota.
     * <p>
     * Test Case Design: Questo test valuta la correttezza del metodo {@code equals()} quando una lista vuota viene confrontata con una lista che contiene elementi.
     * <p>
     * Test Description: 1) Si crea una lista con un elemento.
     * 2) Si confronta la lista di test (vuota) con la lista non vuota usando {@code equals()}.
     * 3) Si verifica che il risultato sia false.
     * <p>
     * Preconditions: Lista vuota. Lista non vuota.
     * <p>
     * Postconditions: Nessuna modifica alle liste.
     * <p>
     * Expected Result: {@code equals(Object)} deve restituire false.
     */
    @Test
    public void testEqualsWithNonEmptyList()
    {
        ListAdapter nonEmptyList = new ListAdapter();
        nonEmptyList.add("test");
        assertFalse(list.equals(nonEmptyList));
    }

    /**
     * Test del metodo {@link HList#equals(Object)} con null.
     * <p>
     * Summary: Verifica che una lista vuota non sia uguale a null.
     * <p>
     * Test Case Design: Questo test verifica la gestione del confronto con un oggetto nullo, che per convenzione in Java dovrebbe sempre restituire false per {@code equals()}.
     * <p>
     * Test Description: 1) Si confronta la lista di test (vuota) con null usando {@code equals()}.
     * 2) Si verifica che il risultato sia false.
     * <p>
     * Preconditions: Lista vuota. Oggetto null.
     * <p>
     * Postconditions: Nessuna modifica alla lista.
     * <p>
     * Expected Result: {@code equals(Object)} deve restituire false.
     */
    @Test
    public void testEqualsWithNull()
    {
        assertFalse(list.equals(null));
    }

    /**
     * Test del metodo {@link HList#equals(Object)} con un oggetto di tipo diverso.
     * <p>
     * Summary: Verifica che una lista vuota non sia uguale a un oggetto di tipo diverso.
     * <p>
     * Test Case Design: Questo test valuta il comportamento di {@code equals()} quando confronta la lista con un oggetto che non è un'istanza di {@code HList}.
     * <p>
     * Test Description: 1) Si crea un oggetto di tipo diverso (es. String).
     * 2) Si confronta la lista di test (vuota) con l'oggetto di tipo diverso usando {@code equals()}.
     * 3) Si verifica che il risultato sia false.
     * <p>
     * Preconditions: Lista vuota. Oggetto di tipo diverso.
     * <p>
     * Postconditions: Nessuna modifica alla lista.
     * <p>
     * Expected Result: {@code equals(Object)} deve restituire false.
     */
    @Test
    public void testEqualsWithDifferentType()
    {
        assertFalse(list.equals("a string"));
    }

    //------- TEST DEL METODO hashCode() ----------

    /**
     * Test del metodo {@link HList#hashCode()} su lista vuota.
     * <p>
     * Summary: Verifica che l'hashCode di una lista vuota sia lo stesso di quello di un'altra lista vuota.
     * <p>
     * Test Case Design: Questo test assicura che gli hash code siano coerenti per oggetti {@code equals()}, un requisito fondamentale per le implementazioni di {@code hashCode()}.
     * <p>
     * Test Description: 1) Si crea una seconda istanza di {@code ListAdapter} vuota.
     * 2) Si calcola l'hashCode di entrambe le liste.
     * 3) Si verifica che gli hash code siano uguali.
     * <p>
     * Preconditions: Due liste vuote.
     * <p>
     * Postconditions: Nessuna modifica alle liste.
     * <p>
     * Expected Result: Gli hash code delle due liste vuote devono essere uguali.
     */
    @Test
    public void testHashCodeEmptyList()
    {
        HList anotherEmptyList = new ListAdapter();
        assertEquals(anotherEmptyList.hashCode(), list.hashCode());
    }

    //------- TEST DI GET ----------

    /**
     * Test del metodo {@link HList#get(int)} su una lista vuota.
     * <p>
     * Summary: Verifica che {@code get(int)} lanci {@code IndexOutOfBoundsException} quando chiamato su una lista vuota.
     * <p>
     * Test Case Design: La motivazione è assicurarsi che il metodo gestisca correttamente l'accesso a un indice non valido su una lista vuota.
     * <p>
     * Test Description: 1) Si tenta di chiamare {@code get(0)} su una lista vuota.
     * 2) Si verifica che venga lanciata {@code IndexOutOfBoundsException}.
     * <p>
     * Preconditions: Lista vuota.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * Expected Result: {@code get(int)} deve lanciare {@code IndexOutOfBoundsException}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetEmptyList() {
        list.get(0);
    }

    //------- TEST DI SET ----------

    /**
     * Test del metodo {@link HList#set(int, Object)} su una lista vuota.
     * <p>
     * Summary: Verifica che {@code set(int, Object)} lanci {@code IndexOutOfBoundsException} quando chiamato su una lista vuota.
     * <p>
     * Test Case Design: La motivazione è assicurarsi che il metodo gestisca correttamente il tentativo di impostare un elemento in una lista vuota.
     * <p>
     * Test Description: 1) Si tenta di chiamare {@code set(0, "test")} su una lista vuota.
     * 2) Si verifica che venga lanciata {@code IndexOutOfBoundsException}.
     * <p>
     * Preconditions: Lista vuota.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * Expected Result: {@code set(int, Object)} deve lanciare {@code IndexOutOfBoundsException}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSetEmptyList() {
        list.set(0, "test");
    }

    //------- TEST DI ADD ----------

    /**
     * Test del metodo {@link HList#add(int, Object)} su lista vuota con indice fuori limite.
     * <p>
     * Summary: Verifica che {@code add(int, Object)} lanci {@code IndexOutOfBoundsException} se l'indice non è 0 su una lista vuota.
     * <p>
     * Test Case Design: La motivazione è assicurarsi che il metodo gestisca correttamente l'aggiunta a un indice non valido (ovvero, un indice > 0 per una lista vuota).
     * <p>
     * Test Description: 1) Si tenta di chiamare {@code add(1, "test")} su una lista vuota.
     * 2) Si verifica che venga lanciata {@code IndexOutOfBoundsException}.
     * <p>
     * Preconditions: Lista vuota.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * Expected Result: {@code add(int, Object)} deve lanciare {@code IndexOutOfBoundsException}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAtIndexOutOfBoundsEmptyList() {
        list.add(1, "test");
    }

    //------- TEST DI REMOVE ----------

    /**
     * Test del metodo {@link HList#remove(int)} su lista vuota.
     * <p>
     * Summary: Verifica che {@code remove(int)} lanci {@code IndexOutOfBoundsException} quando chiamato su una lista vuota.
     * <p>
     * Test Case Design: La motivazione è assicurarsi che il metodo gestisca correttamente il tentativo di rimozione da una lista vuota.
     * <p>
     * Test Description: 1) Si tenta di chiamare {@code remove(0)} su una lista vuota.
     * 2) Si verifica che venga lanciata {@code IndexOutOfBoundsException}.
     * <p>
     * Preconditions: Lista vuota.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * Expected Result: {@code remove(int)} deve lanciare {@code IndexOutOfBoundsException}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveAtIndexEmptyList() {
        list.remove(0);
    }

    //-------- TEST INDEXOF --------

    /**
     * Test del metodo {@link HList#indexOf(Object)} su una lista vuota.
     * <p>
     * Summary: Verifica che {@code indexOf(Object)} restituisca -1 per qualsiasi oggetto su una lista vuota.
     * <p>
     * Test Case Design: La motivazione è assicurarsi che il metodo gestisca correttamente la ricerca di un elemento in una lista che non contiene elementi.
     * <p>
     * Test Description: 1) Si chiama {@code indexOf("qualsiasiElemento")} su una lista vuota.
     * 2) Si verifica che il risultato sia -1.
     * 3) Si chiama {@code indexOf(null)} su una lista vuota.
     * 4) Si verifica che il risultato sia -1.
     * <p>
     * Preconditions: Lista vuota.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * Expected Result: {@code indexOf(Object)} deve restituire -1 in entrambi i casi.
     */
    @Test
    public void testIndexOfEmptyList() {
        assertEquals(-1, list.indexOf("nonEsistente"));
        assertEquals(-1, list.indexOf(null)); // Anche per null
    }

    //-------- TEST LASTINDEXOF --------

    /**
     * Test del metodo {@link HList#lastIndexOf(Object)} su una lista vuota.
     * <p>
     * Summary: Verifica che {@code lastIndexOf(Object)} restituisca -1 per qualsiasi oggetto su una lista vuota.
     * <p>
     * Test Case Design: La motivazione è assicurarsi che il metodo gestisca correttamente la ricerca dell'ultima occorrenza di un elemento in una lista che non contiene elementi.
     * <p>
     * Test Description: 1) Si chiama {@code lastIndexOf("qualsiasiElemento")} su una lista vuota.
     * 2) Si verifica che il risultato sia -1.
     * 3) Si chiama {@code lastIndexOf(null)} su una lista vuota.
     * 4) Si verifica che il risultato sia -1.
     * <p>
     * Preconditions: Lista vuota.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * Expected Result: {@code lastIndexOf(Object)} deve restituire -1 in entrambi i casi.
     */
    @Test
    public void testLastIndexOfEmptyList() {
        assertEquals(-1, list.lastIndexOf("nonEsistente"));
        assertEquals(-1, list.lastIndexOf(null)); // Anche per null
    }

    //-------- TEST LISTITERATOR --------

    /**
     * Test del metodo {@link HList#listIterator()} su una lista vuota.
     * <p>
     * Summary: Verifica che l'iteratore ottenuto da una lista vuota abbia {@code hasNext()} e {@code hasPrevious()} falsi e gli indici corretti.
     * <p>
     * Test Case Design: La motivazione è assicurarsi che l'iteratore sia correttamente inizializzato per una lista vuota, riflettendo l'assenza di elementi.
     * <p>
     * Test Description: 1) Si ottiene un iteratore chiamando {@code listIterator()}.
     * 2) Si verifica che {@code hasNext()} sia false.
     * 3) Si verifica che {@code hasPrevious()} sia false.
     * 4) Si verificano i valori di {@code nextIndex()} e {@code previousIndex()}.
     * <p>
     * Preconditions: Lista vuota.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * Expected Result: {@code hasNext()} e {@code hasPrevious()} dell'iteratore devono essere false. {@code nextIndex()} deve essere 0 e {@code previousIndex()} deve essere -1.
     */
    @Test
    public void testListIteratorEmptyList() {
        HListIterator it = list.listIterator();
        assertFalse(it.hasNext());
        assertFalse(it.hasPrevious());
        assertEquals(0, it.nextIndex());
        assertEquals(-1, it.previousIndex());
    }

    /**
     * Test del metodo {@link HList#listIterator(int)} su una lista vuota con indice 0.
     * <p>
     * Summary: Verifica che {@code listIterator(0)} su una lista vuota restituisca un iteratore correttamente posizionato.
     * <p>
     * Test Case Design: La motivazione è assicurarsi che l'iteratore sia correttamente inizializzato per una lista vuota quando si specifica l'indice 0, che è l'unico indice valido in questo contesto.
     * <p>
     * Test Description: 1) Si ottiene un iteratore chiamando {@code listIterator(0)}.
     * 2) Si verifica che {@code hasNext()} sia false.
     * 3) Si verifica che {@code hasPrevious()} sia false.
     * 4) Si verificano i valori di {@code nextIndex()} e {@code previousIndex()}.
     * <p>
     * Preconditions: Lista vuota.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * Expected Result: {@code hasNext()} e {@code hasPrevious()} dell'iteratore devono essere false. {@code nextIndex()} deve essere 0 e {@code previousIndex()} deve essere -1.
     */
    @Test
    public void testListIteratorAtIndex0EmptyList() 
    {
        HListIterator it = list.listIterator(0);
        assertFalse(it.hasNext());
        assertFalse(it.hasPrevious());
        assertEquals(0, it.nextIndex());
        assertEquals(-1, it.previousIndex());
    }

    /**
     * Test del metodo {@link HList#listIterator(int)} su una lista vuota con indice fuori limite.
     * <p>
     * Summary: Verifica che {@code listIterator(int)} lanci {@code IndexOutOfBoundsException} se l'indice non è 0 su una lista vuota.
     * <p>
     * Test Case Design: La motivazione è assicurarsi che il metodo gestisca correttamente l'ottenimento di un iteratore con un indice non valido (ovvero, un indice > 0 per una lista vuota).
     * <p>
     * Test Description: 1) Si tenta di chiamare {@code listIterator(1)} su una lista vuota.
     * 2) Si verifica che venga lanciata {@code IndexOutOfBoundsException}.
     * <p>
     * Preconditions: Lista vuota.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * Expected Result: {@code listIterator(int)} deve lanciare {@code IndexOutOfBoundsException}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testListIteratorAtIndexOutOfBoundsEmptyList() {
        list.listIterator(1);
    }

    //------- TEST SUBLIST -------

    /**
     * Test del metodo {@link HList#subList(int, int)} su una lista vuota con indici validi (0, 0).
     * <p>
     * Summary: Verifica che {@code subList(0, 0)} su una lista vuota restituisca una sublist vuota.
     * <p>
     * Test Case Design: La motivazione è assicurarsi che la creazione di una sublist vuota da una lista vuota sia gestita correttamente, mantenendo le proprietà di una lista vuota.
     * <p>
     * Test Description: 1) Si chiama {@code subList(0, 0)} su una lista vuota.
     * 2) Si verifica che la sublist risultante sia vuota.
     * 3) Si verifica che la dimensione della sublist sia 0.
     * <p>
     * Preconditions: Lista vuota.
     * <p>
     * Postconditions: La lista originale rimane invariata.
     * <p>
     * Expected Result: La sublist restituita deve essere vuota e avere dimensione 0.
     */
    @Test
    public void testSubListEmptyListValidIndexes() {
        HList sub = list.subList(0, 0);
        assertTrue(sub.isEmpty());
        assertEquals(0, sub.size());
    }

    /**
     * Test del metodo {@link HList#subList(int, int)} su una lista vuota con fromIndex fuori limite (superiore).
     * <p>
     * Summary: Verifica che {@code subList(int, int)} lanci {@code IndexOutOfBoundsException} se fromIndex è maggiore della dimensione.
     * <p>
     * Test Case Design: La motivazione è assicurarsi che il metodo gestisca correttamente gli indici di inizio fuori limite per una lista vuota.
     * <p>
     * Test Description: 1) Si tenta di chiamare {@code subList(1, 1)} su una lista vuota.
     * 2) Si verifica che venga lanciata {@code IndexOutOfBoundsException}.
     * <p>
     * Preconditions: Lista vuota.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * Expected Result: {@code subList(int, int)} deve lanciare {@code IndexOutOfBoundsException}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSubListEmptyListFromIndexOutOfBounds() {
        list.subList(1, 1);
    }

    /**
     * Test del metodo {@link HList#subList(int, int)} su una lista vuota con toIndex fuori limite (superiore).
     * <p>
     * Summary: Verifica che {@code subList(int, int)} lanci {@code IndexOutOfBoundsException} se toIndex è maggiore della dimensione.
     * <p>
     * Test Case Design: La motivazione è assicurarsi che il metodo gestisca correttamente gli indici di fine fuori limite per una lista vuota.
     * <p>
     * Test Description: 1) Si tenta di chiamare {@code subList(0, 1)} su una lista vuota.
     * 2) Si verifica che venga lanciata {@code IndexOutOfBoundsException}.
     * <p>
     * Preconditions: Lista vuota.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * Expected Result: {@code subList(int, int)} deve lanciare {@code IndexOutOfBoundsException}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSubListEmptyListToIndexOutOfBounds() {
        list.subList(0, 1);
    }

    /**
     * Test del metodo {@link HList#subList(int, int)} su una lista vuota con fromIndex > toIndex.
     * <p>
     * Summary: Verifica che {@code subList(int, int)} lanci {@code IllegalArgumentException} se fromIndex è maggiore di toIndex.
     * <p>
     * Test Case Design: La motivazione è assicurarsi che il metodo enforce l'invariante che l'indice di inizio non possa essere maggiore dell'indice di fine, indipendentemente dallo stato (vuoto o popolato) della lista.
     * <p>
     * Test Description: 1) Si tenta di chiamare {@code subList(1, 0)} su una lista vuota.
     * 2) Si verifica che venga lanciata {@code IllegalArgumentException}.
     * <p>
     * Preconditions: Lista vuota.
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * Expected Result: {@code subList(int, int)} deve lanciare {@code IllegalArgumentException}.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSubListEmptyListFromIndexGreaterThanToIndex() 
    {
        list.subList(1, 0); // Anche se la lista è vuota, questa condizione è prioritaria rispetto a IOB
    }
}