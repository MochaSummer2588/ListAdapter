package myTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import myAdapter.*;
import myExceptions.IllegalStateException; 
import org.junit.Assume;

import java.util.Vector;
import java.util.Random;

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
     * La motivazione è assicurare che il contatore interno della dimensione sia gestito correttamente
     * dopo una serie di operazioni di aggiunta che popolano la lista.
     * <p>
     * Test Description: 1) Si fa affidamento sul metodo {@code setUp()} che inizializza la lista con 4 elementi.
     *                   2) Si chiama il metodo {@code size()} sulla lista.
     *                   3) Si verifica che il valore restituito sia uguale al numero di elementi aggiunti durante il setup.
     * <p>
     * Preconditions: La lista è stata inizializzata con 4 elementi tramite il metodo {@code setUp()}.
     * <p>
     * Postconditions: La lista rimane invariata, con i suoi 4 elementi.
     * <p>
     * Expected Result: {@code size()} deve restituire 4.
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
     * La motivazione è garantire che il metodo {@code isEmpty()} rifletta correttamente lo stato di una lista che contiene elementi,
     * differenziandola da una lista vuota.
     * <p>
     * Test Description: 1) Si fa affidamento sul metodo {@code setUp()} che inizializza la lista con 4 elementi.
     *                   2) Si chiama il metodo {@code isEmpty()} sulla lista popolata.
     *                   3) Si verifica che il valore booleano restituito sia false, indicando che la lista non è vuota.
     * <p>
     * Preconditions: La lista è stata inizializzata con 4 elementi tramite il metodo {@code setUp()}.
     * <p>
     * Postconditions: La lista rimane invariata, con i suoi 4 elementi.
     * <p>
     * Expected Result: {@code isEmpty()} deve restituire false.
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
     * Summary: Verifica che il metodo {@code contains(Object)} identifichi correttamente la presenza di un elemento che
     * è già stato inserito nella lista popolata, restituendo {@code true}. Questo test assicura che la funzionalità di ricerca
     * di base operi come previsto per gli elementi esistenti.
     * <p>
     * Test Case Design: Questo test assicura che il metodo {@code contains()} identifichi correttamente
     * la presenza di un elemento che è stato effettivamente aggiunto alla lista.
     * La motivazione è convalidare la funzionalità di ricerca di base.
     * <p>
     * Test Description: 1) Si fa affidamento sul metodo {@code setUp()} che inizializza la lista con 4 elementi.
     *                   2) Si chiama il metodo {@code contains()} cercando un elemento che si sa essere presente ("due").
     *                   3) Si verifica che il valore booleano restituito sia true.
     * <p>
     * Preconditions: La lista è stata inizializzata con 4 elementi, inclusi "uno", "due", "tre", "quattro".
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * Expected Result: {@code contains("due")} deve restituire true.
     */
    @Test
    public void testContainsExistingElement()
    {
        assertTrue(list.contains("due"));
    }

    /**
     * Test del metodo {@link HList#contains(Object)}.
     * <p>
     * Summary: Verifica che il metodo {@code contains(Object)} restituisca correttamente {@code false}
     * quando si tenta di cercare un elemento che non è presente all'interno della lista popolata.
     * Questo test assicura che la funzionalità di ricerca non produca falsi positivi.
     * <p>
     * Test Case Design: Questo test assicura che il metodo {@code contains()} identifichi correttamente
     * l'assenza di un elemento. La motivazione è convalidare la funzionalità di ricerca di base
     * anche per gli elementi non esistenti.
     * <p>
     * Test Description: 1) Si fa affidamento sul metodo {@code setUp()} che inizializza la lista con 4 elementi.
     *                   2) Si chiama il metodo {@code contains()} cercando un elemento che non si trova nella lista ("cinque").
     *                   3) Si verifica che il valore booleano restituito sia false.
     * <p>
     * Preconditions: La lista è stata inizializzata con 4 elementi ("uno", "due", "tre", "quattro").
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * Expected Result: {@code contains("cinque")} deve restituire false.
     */
    @Test
    public void testContainsNonExistingElement()
    {
        assertFalse(list.contains("cinque"));
    }

    /**
     * Test del metodo {@link HList#contains(Object)}.
     * <p>
     * Summary: Verifica che il metodo {@code contains(Object)} restituisca correttamente {@code false}
     * quando si tenta di cercare un riferimento {@code null} in una lista che non contiene elementi null.
     * Questo test è cruciale per assicurare che la gestione degli elementi null sia precisa
     * e che la ricerca di un valore non presente (anche se {@code null}) funzioni come atteso.
     * <p>
     * Test Case Design: Questo test assicura che la ricerca di un elemento {@code null}
     * sia gestita correttamente quando {@code null} non è presente nella lista. La motivazione
     * è convalidare che il metodo non dia un falso positivo o lanci un'eccezione inaspettata
     * quando cerca un valore {@code null} assente.
     * <p>
     * Test Description: 1) Si fa affidamento sul metodo {@code setUp()} che inizializza la lista con 4 elementi non null.
     *                   2) Si chiama il metodo {@code contains()} passando {@code null} come argomento.
     *                   3) Si verifica che il valore booleano restituito sia {@code false}.
     * <p>
     * Preconditions: La lista è stata inizializzata con 4 elementi non null ("uno", "due", "tre", "quattro").
     * <p>
     * Postconditions: La lista rimane invariata.
     * <p>
     * Expected Result: {@code contains(null)} deve restituire false.
     */
    @Test
    public void testContainsNullNotPresent()
    {
        assertFalse(list.contains(null));
    }

    /**
     * Test del metodo {@link HList#contains(Object)}.
     * <p>
     * Summary: Verifica che il metodo {@code contains(Object)} restituisca correttamente {@code true}
     * quando si cerca un riferimento {@code null} e questo è effettivamente presente nella lista.
     * Questo test è fondamentale per assicurare la corretta gestione e rintracciabilità degli elementi {@code null}
     * all'interno della lista.
     * <p>
     * Test Case Design: Questo test assicura che la ricerca di un elemento {@code null} funzioni correttamente
     * quando {@code null} è stato esplicitamente aggiunto alla lista. La motivazione è convalidare
     * la capacità del metodo di trovare e riconoscere gli elementi {@code null} come validi contenuti della lista.
     * <p>
     * Test Description: 1) Si aggiunge un elemento {@code null} alla lista già popolata dal {@code setUp()}.
     *                   2) Si chiama il metodo {@code contains()} passando {@code null} come argomento.
     *                   3) Si verifica che il valore booleano restituito sia {@code true}.
     *                   4) Si verifica che la dimensione della lista sia aumentata di uno, confermando l'aggiunta.
     * <p>
     * Preconditions: La lista è stata inizializzata con 5 elementi ("uno", "due", "tre", "quattro", null) 
     * <p>
     * Postconditions: La lista contiene un elemento {@code null} in più rispetto allo stato iniziale.
     * <p>
     * Expected Result: {@code contains(null)} deve restituire true. La dimensione della lista deve essere 5.
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
     * Summary: Il test verifica la capacità di un {@link myAdapter.HIterator} ottenuto da una lista popolata
     * di percorrere correttamente tutti gli elementi in sequenza, dal primo all'ultimo,
     * assicurando che il metodo {@code hasNext()} e {@code next()} funzionino come previsto
     * e che l'iteratore esaurisca correttamente gli elementi.
     * <p>
     * Test Case Design: Questo test mira ad assicurare che l'iteratore possa percorrere tutti gli elementi
     * di una lista popolata nell'ordine di inserimento. La motivazione è convalidare la funzionalità
     * fondamentale di iterazione.
     * <p>
     * Test Description: 1) Si ottiene un'istanza di {@link myAdapter.HIterator} chiamando {@code list.iterator()}.
     *                   2) Si verifica ripetutamente che {@code hasNext()} restituisca {@code true} prima di ogni chiamata a {@code next()}.
     *                   3) Si chiama {@code next()} per ogni elemento della lista, verificando che restituisca l'elemento atteso
     *                      nell'ordine corretto ("uno", "due", "tre", "quattro").
     *                   4) Dopo aver attraversato l'ultimo elemento, si verifica che {@code hasNext()} restituisca {@code false},
     *                      indicando che l'iterazione è terminata.
     * <p>
     * Preconditions: La lista è stata inizializzata e popolata con 4 elementi: ["uno", "due", "tre", "quattro"].
     * <p>
     * Postconditions: La lista rimane invariata nel suo contenuto e nella sua dimensione. Lo stato dell'iteratore
     * si trova alla fine della lista.
     * <p>
     * Expected Result: L'iteratore deve restituire tutti gli elementi nell'ordine corretto ("uno", "due", "tre", "quattro").
     * Dopo l'ultima chiamata a {@code next()}, {@code hasNext()} deve restituire {@code false}.
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
     * Summary: Il test verifica che il metodo {@code toArray()} su una lista popolata
     * restituisca un array di {@code Object} contenente tutti gli elementi della lista
     * nell'ordine corretto e con la dimensione appropriata. Questo assicura che la
     * conversione da lista ad array avvenga in modo accurato e senza perdita di dati.
     * <p>
     * Test Case Design: Questo test è progettato per assicurare che la conversione
     * della lista in un array includa tutti gli elementi presenti, mantenendo il loro
     * ordine. La motivazione è convalidare la corretta rappresentazione dei dati
     * della lista in una struttura array.
     * <p>
     * Test Description: 1) Si chiama il metodo {@code toArray()} sulla lista già popolata dal {@code setUp()}.
     *                   2) Si verifica che l'array risultante non sia {@code null}, garantendo che un array sia effettivamente creato.
     *                   3) Si verifica che la lunghezza dell'array restituito sia esattamente uguale alla dimensione della lista,
     *                      confermando che tutti gli elementi sono stati copiati.
     *                   4) Si verifica il contenuto di ogni posizione dell'array, confrontandolo con gli elementi attesi
     *                      della lista nell'ordine sequenziale.
     * <p>
     * Preconditions: La lista è stata inizializzata e popolata con 4 elementi: ["uno", "due", "tre", "quattro"].
     * <p>
     * Postconditions: La lista originale rimane invariata nel suo contenuto e nella sua dimensione.
     * Viene creato un nuovo array contenente una copia degli elementi della lista.
     * <p>
     * Expected Result: L'array restituito non deve essere {@code null}. La sua lunghezza deve essere 4.
     * Gli elementi dell'array devono essere ["uno", "due", "tre", "quattro"] nell'ordine specificato.
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
     * Summary: Il test verifica che {@code toArray(T[] a)} riutilizzi correttamente un array di destinazione
     * fornito se la sua dimensione è sufficiente a contenere tutti gli elementi della lista.
     * Inoltre, assicura che qualsiasi elemento nell'array fornito, posizionato oltre la dimensione della lista,
     * venga impostato a {@code null} per conformità con le specifiche.
     * <p>
     * Test Case Design: Questo test è progettato per assicurare che il metodo {@code toArray(T[] a)}
     * gestisca correttamente il caso in cui l'array fornito sia di dimensione uguale o maggiore della lista.
     * La motivazione è convalidare che l'implementazione aderisca alla specifica J2SE per la riutilizzazione
     * dell'array fornito e la nullificazione degli elementi residui.
     * <p>
     * Test Description: 1) Si crea un array di {@code String} (`arr`) con una dimensione maggiore rispetto alla lista popolata
     *                      (ad esempio, 5 elementi per una lista di 4).
     *                   2) Si aggiunge un valore arbitrario ("extra") all'ultima posizione dell'array fornito per verificare
     *                      che venga correttamente sovrascritto a {@code null}.
     *                   3) Si chiama il metodo {@code toArray()} della lista, passando l'array `arr` come argomento.
     *                   4) Si verifica, tramite {@code assertSame()}, che l'array restituito sia esattamente la stessa istanza dell'array passato.
     *                   5) Si verifica che la lunghezza dell'array risultante non sia stata modificata (rimanga 5).
     *                   6) Si controlla il contenuto delle prime posizioni dell'array per assicurare che gli elementi della lista siano stati copiati correttamente.
     *                   7) Si verifica che l'elemento alla posizione successiva all'ultimo elemento della lista (l'indice 4 in questo caso)
     *                      sia stato impostato a {@code null}.
     * <p>
     * Preconditions: La lista è stata inizializzata e popolata con 4 elementi: ["uno", "due", "tre", "quattro"].
     * Viene fornito un array di {@code Object} con una dimensione di 5 elementi.
     * <p>
     * Postconditions: La lista originale rimane invariata nel suo contenuto e nella sua dimensione.
     * L'array fornito in input viene modificato: i suoi primi 4 elementi contengono gli elementi della lista,
     * e il quinto elemento è impostato a {@code null}.
     * <p>
     * Expected Result: L'array restituito deve essere la stessa istanza dell'array passato (`arr`).
     * La sua lunghezza deve rimanere 5.
     * I suoi elementi devono essere ["uno", "due", "tre", "quattro", null] nell'ordine specificato.
     */
    @Test
    public void testToArrayWithSufficientlyLargeArray()
    {
        String[] arr = new String[5];
        arr[4] = "extra"; // Aggiungo un elemento per verificare che venga nullificato
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
     * Summary: Il test verifica che il metodo {@code toArray(T[] a)} sia in grado di creare
     * e restituire un *nuovo* array di tipo e dimensione appropriati quando l'array di input
     * fornito è troppo piccolo per contenere tutti gli elementi della lista. Questo assicura
     * che l'implementazione rispetti la specifica.
     * <p>
     * Test Case Design: Questo test è progettato per assicurare che il metodo {@code toArray(T[] a)}
     * allochi un nuovo array quando quello fornito è insufficiente a contenere tutti gli elementi
     * della lista. La motivazione è convalidare la corretta gestione delle dimensioni dell'array
     * di destinazione e la creazione di un nuovo array quando necessario, preservando la tipizzazione.
     * <p>
     * Test Description: 1) Si crea un array di {@code String} (`arr`) con una dimensione inferiore
     *                      rispetto alla lista popolata (ad esempio, 2 elementi per una lista di 4).
     *                   2) Si chiama il metodo {@code toArray()} della lista, passando l'array `arr` come argomento.
     *                   3) Si verifica, tramite {@code assertNotSame()}, che l'array restituito non sia la stessa istanza
     *                      dell'array passato, confermando l'allocazione di un nuovo array.
     *                   4) Si verifica che la lunghezza del nuovo array risultante sia esattamente uguale alla dimensione della lista (4),
     *                      confermando che tutti gli elementi sono stati copiati nel nuovo array.
     *                   5) Si controlla il contenuto di ogni posizione del nuovo array per assicurare che gli elementi della lista
     *                      siano stati copiati correttamente e nell'ordine sequenziale.
     * <p>
     * Preconditions: La lista è stata inizializzata e popolata con 4 elementi: ["uno", "due", "tre", "quattro"].
     * Viene fornito un array di {@code String} con una dimensione di 2 elementi.
     * <p>
     * Postconditions: La lista originale rimane invariata nel suo contenuto e nella sua dimensione.
     * Viene allocato e restituito un nuovo array che contiene una copia degli elementi della lista.
     * L'array originale passato come parametro non viene modificato.
     * <p>
     * Expected Result: L'array restituito deve essere una nuova istanza (non lo stesso di `arr`).
     * La sua lunghezza deve essere 4. Gli elementi dell'array devono essere ["uno", "due", "tre", "quattro"]
     * nell'ordine specificato.
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
        assertEquals("quattro", result[3]); // Assicurati di includere anche l'ultima asserzione
    }

    /**
     * Test del metodo {@link HList#toArray(Object[])}.
     * <p>
     * Summary: Il test verifica che il metodo {@code toArray(T[] a)} lanci correttamente
     * una {@link java.lang.NullPointerException} quando l'array di destinazione fornito
     * come argomento è {@code null}. Questo assicura che l'implementazione aderisca alla
     * specifica che prevede tale comportamento per la gestione
     * di input non validi.
     * <p>
     * Test Case Design: Questo test è progettato per assicurare che il metodo {@code toArray(T[] a)}
     * gestisca in modo robusto il caso limite in cui viene fornito un array di input {@code null}.
     * La motivazione è convalidare che il metodo non proceda con operazioni su un riferimento nullo,
     * prevenendo potenziali {@code NullPointerException} non gestite e rispettando il contratto
     * dell'interfaccia.
     * <p>
     * Test Description: 1) Si chiama il metodo {@code toArray()} sulla lista popolata,
     *                      passando intenzionalmente {@code null} come argomento per l'array di destinazione.
     *                   2) Si usa l'annotazione JUnit `@Test(expected = NullPointerException.class)` per
     *                      dichiarare che ci si aspetta il lancio di una {@code NullPointerException} durante l'esecuzione
     *                      di questo test. Se l'eccezione non viene lanciata o viene lanciata un'eccezione diversa,
     *                      il test fallirà.
     * <p>
     * Preconditions: La lista è stata inizializzata e popolata con 4 elementi.
     * <p>
     * Postconditions: La lista originale rimane invariata nel suo contenuto e nella sua dimensione.
     * Nessuna modifica viene apportata, poiché l'esecuzione del metodo viene interrotta dal lancio dell'eccezione.
     * <p>
     * Expected Result: Il test deve completarsi con successo solo se viene lanciata una
     * {@link java.lang.NullPointerException} al momento della chiamata a {@code list.toArray(null)}.
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
     * Summary: Il test verifica che il metodo {@code add(Object element)} possa aggiungere correttamente
     * un nuovo elemento alla fine di una lista già popolata. Si assicura che l'elemento venga
     * inserito nella posizione corretta, che la dimensione della lista sia aggiornata di conseguenza,
     * e che il metodo restituisca {@code true} come specificato dal {@link myAdapter.HCollection#add(Object) HCollection}
     * e {@link myAdapter.HList#add(Object) HList} per un'operazione di successo.
     * <p>
     * Test Case Design: Questo test è progettato per verificare la funzionalità di base del metodo {@code add(Object)}
     * quando la lista non è vuota. La motivazione è assicurare che l'aggiunta di un elemento mantenga
     * la consistenza della lista, posizionando il nuovo elemento in coda e aggiornando la dimensione.
     * <p>
     * Test Description: 1) Si chiama il metodo {@code add()} sulla lista già popolata dal {@code setUp()},
     *                      passando una nuova stringa ("cinque") come elemento da aggiungere.
     *                   2) Si verifica, tramite {@code assertTrue()}, che il valore booleano restituito da {@code add()} sia {@code true},
     *                      confermando che l'operazione di aggiunta ha avuto successo.
     *                   3) Si verifica che la dimensione della lista sia aumentata correttamente da 4 a 5, utilizzando {@code assertEquals(5, list.size())}.
     *                   4) Si accede all'ultimo elemento della lista (all'indice 4, poiché gli indici vanno da 0 a 4) tramite {@code list.get(4)}
     *                      e si verifica che corrisponda all'elemento appena aggiunto ("cinque").
     * <p>
     * Preconditions: La lista è stata inizializzata e popolata con 4 elementi: ["uno", "due", "tre", "quattro"].
     * <p>
     * Postconditions: La lista contiene un elemento in più rispetto allo stato iniziale.
     * L'elemento aggiunto ("cinque") si trova all'ultima posizione (indice 4).
     * La dimensione della lista è 5.
     * <p>
     * Expected Result: La chiamata a {@code add("cinque")} deve restituire {@code true}.
     * La dimensione della lista dopo l'aggiunta deve essere 5.
     * L'elemento all'indice 4 della lista deve essere "cinque".
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
     * Summary: Il test verifica che il metodo {@code add(Object element)} gestisca correttamente
     * l'aggiunta di un riferimento {@code null} alla fine di una lista già popolata. Si assicura
     * che l'elemento {@code null} venga inserito correttamente, che la dimensione della lista
     * sia aggiornata, e che il metodo {@code contains(null)} restituisca {@code true} dopo l'aggiunta,
     * confermando che gli elementi {@code null} sono supportati e gestiti come elementi validi.
     * <p>
     * Test Case Design: Questo test è progettato per assicurare che il metodo {@code add(Object)}
     * possa gestire e memorizzare correttamente riferimenti {@code null}, trattandoli come qualsiasi
     * altro elemento valido della lista. La motivazione è convalidare la capacità della lista
     * di contenere elementi {@code null}, come specificato dalle interfacce del J2SE Collections Framework.
     * <p>
     * Test Description: 1) Si chiama il metodo {@code add()} sulla lista già popolata dal {@code setUp()},
     *                      passando {@code null} come elemento da aggiungere.
     *                   2) Si verifica, tramite {@code assertTrue()}, che il valore booleano restituito da {@code add()} sia {@code true},
     *                      confermando che l'operazione di aggiunta ha avuto successo.
     *                   3) Si verifica che la dimensione della lista sia aumentata correttamente da 4 a 5, utilizzando {@code assertEquals(5, list.size())}.
     *                   4) Si accede all'ultimo elemento della lista (all'indice 4) tramite {@code list.get(4)}
     *                      e si verifica che sia effettivamente {@code null}, confermando la posizione dell'elemento aggiunto.
     *                   5) Si utilizza {@code list.contains(null)} per verificare che la lista ora riconosca la presenza di {@code null}.
     * <p>
     * Preconditions: La lista è stata inizializzata e popolata con 4 elementi non null: ["uno", "due", "tre", "quattro"].
     * <p>
     * Postconditions: La lista contiene un elemento {@code null} in più alla fine (indice 4).
     * La dimensione della lista è 5.
     * <p>
     * Expected Result: La chiamata a {@code add(null)} deve restituire {@code true}.
     * La dimensione della lista dopo l'aggiunta deve essere 5.
     * L'elemento all'indice 4 della lista deve essere {@code null}.
     * La chiamata a {@code list.contains(null)} deve restituire {@code true}.
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
     * Summary: Il test verifica l'aggiunta sequenziale e corretta di molteplici elementi
     * alla fine di una lista già popolata. Si assicura che ogni nuovo elemento venga
     * inserito in coda, che la dimensione della lista si aggiorni progressivamente
     * dopo ogni aggiunta, e che l'ordine degli elementi esistenti e dei nuovi elementi
     * sia mantenuto correttamente, confermando il comportamento di accodamento.
     * <p>
     * Test Case Design: Questo test è progettato per simulare scenari di utilizzo comune
     * in cui più elementi vengono aggiunti in successione alla fine di una lista.
     * La motivazione è garantire la stabilità e la correttezza del metodo {@code add(Object)}
     * sotto carichi di lavoro iterativi, assicurando che la lista si espanda correttamente
     * e mantenga l'integrità dei dati.
     * <p>
     * Test Description: 1) Si registra la dimensione iniziale della lista (4 elementi).
     *                   2) Si aggiunge il primo nuovo elemento ("cinque") e si verifica che la dimensione
     *                      sia incrementata a 5 e che "cinque" sia all'indice 4.
     *                   3) Si aggiunge il secondo nuovo elemento ("sei") e si verifica che la dimensione
     *                      sia incrementata a 6 e che "sei" sia all'indice 5.
     *                   4) Si aggiunge il terzo nuovo elemento ("sette") e si verifica che la dimensione
     *                      sia incrementata a 7 e che "sette" sia all'indice 6.
     *                   5) Infine, si verifica l'intera sequenza di elementi nella lista finale (dal primo
     *                      elemento originale all'ultimo elemento appena aggiunto) per confermare l'ordine e il contenuto.
     * <p>
     * Preconditions: La lista è stata inizializzata e popolata con 4 elementi: ["uno", "due", "tre", "quattro"].
     * <p>
     * Postconditions: La lista contiene 7 elementi in totale. I tre nuovi elementi ("cinque", "sei", "sette")
     * sono stati aggiunti in coda, mantenendo l'ordine originale e il nuovo ordine di inserimento.
     * <p>
     * Expected Result: Ogni chiamata a {@code add(Object)} deve restituire {@code true}.
     * La dimensione della lista deve essere 5 dopo la prima aggiunta, 6 dopo la seconda, e 7 dopo la terza.
     * La lista finale deve contenere gli elementi nell'ordine: ["uno", "due", "tre", "quattro", "cinque", "sei", "sette"].
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
     * Summary: Il test verifica il comportamento del metodo {@code add(Object)} sotto stress,
     * aggiungendo un numero elevato di elementi alla lista. Questo test è fondamentale
     * per accertarsi che l'implementazione sottostante
     * gestisca correttamente l'espansione dinamica della sua capacità senza perdita di dati,
     * errori di integrità o prestazioni degradate in modo significativo.
     * <p>
     * Test Case Design: Questo test è progettato per simulare uno scenario in cui la lista
     * viene riempita ben oltre la sua capacità iniziale implicita o esplicita. La motivazione
     * è assicurare che il meccanismo di espansione della capacità della lista funzioni
     * correttamente, permettendo l'aggiunta di un gran numero di elementi in successione,
     * e che la lista mantenga la coerenza interna (dimensione e contenuto). Per implementazioni
     * basate su {@code Vector} (come {@code ListAdapter}), questo verifica il corretto
     * funzionamento degli incrementi di capacità.
     * <p>
     * Test Description: 1) Si registra la dimensione iniziale della lista (tipicamente 4 elementi dal {@code setUp()}).
     *                   2) Si esegue un ciclo per aggiungere un numero elevato di nuovi elementi (es. 1000) alla lista,
     *                      ciascuno con un identificatore unico ("element_X"). Ogni chiamata a {@code add()} è verificata
     *                      per assicurare che restituisca {@code true}.
     *                   3) Dopo il completamento del ciclo, si verifica che la dimensione finale della lista
     *                      sia esattamente la somma della dimensione iniziale e del numero di elementi aggiunti.
     *                   4) Si verifica il contenuto dell'ultimo elemento aggiunto per confermare che l'inserimento
     *                      sia avvenuto correttamente in coda.
     *                   5) Si verifica anche il contenuto del primo elemento originale della lista, per assicurare
     *                      che gli elementi preesistenti non siano stati corrotti o spostati in modo imprevisto.
     * <p>
     * Preconditions: La lista è stata inizializzata e popolata con 4 elementi: ["uno", "due", "tre", "quattro"].
     * <p>
     * Postconditions: La lista contiene gli elementi iniziali più tutti i nuovi elementi aggiunti.
     * La sua dimensione è uguale alla dimensione iniziale più il numero di elementi aggiunti.
     * Tutti gli elementi sono presenti nell'ordine di inserimento.
     * <p>
     * Expected Result: Tutte le chiamate a {@code add(Object)} devono restituire {@code true}.
     * La dimensione finale della lista deve essere {@code initialSize + elementsToAdd}.
     * L'ultimo elemento della lista deve corrispondere all'ultimo elemento aggiunto nel ciclo.
     * Gli elementi iniziali devono rimanere inalterati nelle loro posizioni.
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
     * Summary: Verifica l'aggiunta di un elemento a un indice casuale *intermedio* all'interno di una lista popolata.
     * Questo test si concentra sugli inserimenti che non avvengono né all'inizio (indice 0) né alla fine
     * (l'ultimo elemento o {@code list.size()}), ma in posizioni centrali. Assicura che l'inserimento non alteri
     * gli elementi prima dell'indice, sposti correttamente gli elementi successivi, e aggiorni la dimensione della lista.
     * <p>
     * Test Case Design: Questo test è progettato per verificare la funzionalità di inserimento di un elemento
     * in una posizione arbitraria ma valida della lista, escludendo i casi limite di inserimento all'inizio (indice 0)
     * e alla fine (indice {@code list.size()}). La motivazione è garantire la robustezza del metodo
     * {@code add(int, Object)} per gli inserimenti "in mezzo" alla lista. Richiede che la lista abbia
     * almeno 3 elementi per poter avere indici intermedi validi.
     * <p>
     * Test Description: 1) Si determina la dimensione iniziale della lista.
     *                   2) Si genera un indice casuale valido, compreso tra 1 (incluso) e {@code list.size() - 2} (incluso).
     *                   3) Si crea una copia dello stato iniziale della lista per confrontare il contenuto dopo l'inserimento.
     *                   4) Si aggiunge un nuovo elemento ("elementoCasuale") all'indice generato.
     *                   5) Si verifica che la dimensione della lista sia incrementata di 1.
     *                   6) Si verifica che l'elemento aggiunto sia presente all'indice specificato.
     *                   7) Si itera sugli elementi prima dell'indice per assicurarsi che non siano stati modificati.
     *                   8) Si itera sugli elementi dopo l'indice per assicurarsi che siano stati correttamente spostati di una posizione.
     * 
     * NB: Si usa {@link org.junit.Assume#assumeTrue(boolean)} per saltare il test se la lista non ha un numero sufficiente di elementi
     * per generare un indice intermedio valido (almeno 3 elementi).
     * <p>
     * Preconditions: La lista è stata inizializzata e popolata con 4 elementi: ["uno", "due", "tre", "quattro"].
     * Per l'esecuzione di questo test specifico, la dimensione della lista deve essere almeno 3.
     * <p>
     * Postconditions: La lista contiene un elemento in più all'indice specificato, con gli elementi successivi spostati.
     * La dimensione della lista è 5.
     * <p>
     * @expected L'elemento aggiunto deve trovarsi all'indice casuale generato (tra 1 e {@code initialSize - 2}).
     * La dimensione finale deve essere la dimensione iniziale + 1.
     * Tutti gli elementi originali prima dell'indice devono rimanere invariati.
     * Tutti gli elementi originali a partire dall'indice devono essere stati spostati di una posizione a destra.
     */
    @Test
    public void testAddObjectAtRandomValidIndexPopulatedList() 
    {
        int initialSize = list.size();

        Assume.assumeTrue("Test saltato: la lista iniziale non ha abbastanza elementi per un indice intermedio casuale (minimo 3).", initialSize >= 3);

        Random rand = new Random();
        int randomIndex = rand.nextInt(initialSize - 2) + 1;  //Genero un numero tra 1 e (list.size() - 1)

        String newElement = "elementoCasuale";

        // Cattura lo stato della lista prima della modifica per verifiche successive
        Object[] initialContent = new Object[initialSize];
        for (int i = 0; i < initialSize; i++) 
        {
            initialContent[i] = list.get(i);
        }

        list.add(randomIndex, newElement);

        // --- Verifiche ---
        assertEquals("La dimensione della lista non è corretta dopo l'aggiunta.", initialSize + 1, list.size());

        assertEquals("L'elemento aggiunto non si trova all'indice corretto.", newElement, list.get(randomIndex));

        for (int i = 0; i < randomIndex; i++) 
        {
            assertEquals("Elemento all'indice " + i + " è stato modificato inaspettatamente.", initialContent[i], list.get(i));
        }

        for (int i = randomIndex; i < initialSize; i++) 
        {
            assertEquals("Elemento all'indice " + i + " non è stato spostato correttamente (atteso all'indice " + (i + 1) + ").", initialContent[i], list.get(i + 1));
        }
    }

    /**
     * Test del metodo {@link HList#add(int, Object)}.
     * <p>
     * Summary: Il test verifica che il metodo {@code add(int index, Object element)}
     * lanci correttamente una {@link java.lang.IndexOutOfBoundsException}
     * quando si tenta di aggiungere un elemento a un indice che è strettamente maggiore
     * della dimensione corrente della lista (ovvero, {@code index > size()}).
     * Questo assicura che il metodo rispetti le specifiche per la gestione
     * degli indici non validi.
     * <p>
     * Test Case Design: Questo test è progettato per verificare la gestione dei limiti superiori
     * dell'indice per il metodo {@code add(int, Object)}. La motivazione è assicurare
     * che il metodo non consenta inserimenti in posizioni logiche non valide della lista
     * e che segnali tale violazione tramite l'eccezione appropriata, come da specifica J2SE
     * per le interfacce {@code List}.
     * <p>
     * Test Description: 1) Si tenta di chiamare il metodo {@code add()} sulla lista,
     *                      fornendo un indice calcolato come {@code list.size() + 1} (ad esempio, 5 per una lista di dimensione 4)
     *                      e un elemento qualsiasi ("elemento").
     *                   2) Si utilizza l'annotazione JUnit `@Test(expected = IndexOutOfBoundsException.class)`
     *                      per dichiarare che ci si aspetta il lancio di una {@link java.lang.IndexOutOfBoundsException}
     *                      durante l'esecuzione di questa operazione. Se l'eccezione non viene lanciata o
     *                      viene lanciata un'eccezione diversa, il test fallirà.
     * <p>
     * Preconditions: La lista è stata inizializzata e popolata con 4 elementi.
     * <p>
     * Postconditions: La lista rimane invariata nel suo contenuto e nella sua dimensione,
     * poiché l'operazione di aggiunta viene interrotta dal lancio dell'eccezione.
     * <p>
     * Expected Result: Il test deve completarsi con successo solo se viene lanciata una
     * {@link java.lang.IndexOutOfBoundsException} quando si tenta di aggiungere un elemento
     * all'indice {@code list.size() + 1}.
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
     * Summary: Il test verifica che il metodo {@code add(int index, Object element)}
     * possa inserire correttamente un nuovo elemento all'inizio della lista (all'indice 0),
     * spostando tutti gli elementi preesistenti di una posizione a destra e aggiornando
     * correttamente la dimensione della lista. Questo assicura la corretta gestione
     * degli inserimenti in testa alla lista.
     * <p>
     * Test Case Design: Questo test è progettato per verificare il caso specifico
     * di inserimento di un elemento all'indice 0 di una lista già popolata. La motivazione
     * è assicurare che l'operazione di aggiunta all'inizio della lista funzioni correttamente,
     * spostando gli elementi esistenti e mantenendo l'integrità dell'ordine.
     * <p>
     * Test Description: 1) Si chiama il metodo {@code add()} sulla lista già popolata dal {@code setUp()},
     *                      specificando l'indice 0 e l'elemento "zero".
     *                   2) Si verifica che la dimensione della lista sia aumentata correttamente da 4 a 5,
     *                      utilizzando {@code assertEquals(5, list.size())}.
     *                   3) Si verifica che l'elemento all'indice 0 sia ora "zero" (l'elemento appena inserito).
     *                   4) Si verifica che l'elemento precedentemente all'indice 0 ("uno") sia stato
     *                      correttamente spostato all'indice 1.
     *                   5) (Implicitamente dalle altre assert in altri test simili) si presume che anche
     *                       gli altri elementi siano stati spostati correttamente.
     * <p>
     * Preconditions: La lista è stata inizializzata e popolata con 4 elementi: ["uno", "due", "tre", "quattro"].
     * <p>
     * Postconditions: La lista contiene un elemento in più ("zero") all'inizio (indice 0).
     * Gli elementi che erano originariamente agli indici >= 0 sono stati spostati di una posizione a destra.
     * La dimensione della lista è 5.
     * <p>
     * Expected Result: La lista finale deve essere `["zero", "uno", "due", "tre", "quattro"]`.
     * {@code size()} deve restituire 5.
     * {@code get(0)} deve restituire "zero".
     * {@code get(1)} deve restituire "uno".
     */
    @Test
    public void testAddAtIndex0PopulatedList() 
    {
        list.add(0, "zero");
        assertEquals(5, list.size());
        assertEquals("zero", list.get(0));
        assertEquals("uno", list.get(1)); // L'elemento originale "uno" dovrebbe essere stato spostato
    }

    /**
     * Test del metodo {@link HList#add(int, Object)}.
     * <p>
     * Summary: Il test verifica che il metodo {@code add(int index, Object element)}
     * gestisca correttamente l'aggiunta di un nuovo elemento quando l'indice specificato
     * è esattamente uguale alla dimensione corrente della lista (ovvero, {@code index == size()}).
     * In questo scenario, il metodo dovrebbe comportarsi in modo analogo a {@link HList#add(Object)},
     * aggiungendo l'elemento in coda alla lista. Questo assicura che l'intervallo valido
     * per l'inserimento sia inclusivo dell'indice pari alla dimensione.
     * <p>
     * Test Case Design: Questo test mira a convalidare il comportamento del metodo {@code add(int, Object)}
     * quando l'elemento viene aggiunto all'ultima posizione valida (fine della lista).
     * La motivazione è verificare che questo caso limite sia gestito correttamente e che
     * non produca {@code IndexOutOfBoundsException} o comportamenti inattesi, agendo
     * come un'aggiunta in coda.
     * <p>
     * Test Description: 1) Si chiama il metodo {@code add()} sulla lista già popolata dal {@code setUp()},
     *                      specificando l'indice {@code list.size()} (che è 4 per una lista di 4 elementi) e l'elemento "cinque".
     *                   2) Si verifica che la dimensione della lista sia aumentata correttamente da 4 a 5,
     *                      utilizzando {@code assertEquals(5, list.size())}.
     *                   3) Si accede all'ultimo elemento della lista (all'indice 4) tramite {@code list.get(4)}
     *                      e si verifica che corrisponda all'elemento appena aggiunto ("cinque").
     * <p>
     * Preconditions: La lista è stata inizializzata e popolata con 4 elementi: ["uno", "due", "tre", "quattro"].
     * <p>
     * Postconditions: La lista contiene un elemento in più ("cinque") alla fine.
     * La dimensione della lista è 5.
     * <p>
     * Expected Result: La lista finale deve essere ["uno", "due", "tre", "quattro", "cinque"].
     * {@code size()} deve restituire 5.
     * {@code get(4)} deve restituire "cinque".
     */
    @Test
    public void testAddAtIndexLastValidPopulatedList() 
    {
        list.add(list.size(), "cinque");
        assertEquals(5, list.size());
        assertEquals("cinque", list.get(4));
    }

    /**
     * Test del metodo {@link HList#add(int, Object)} con elemento null.
     * <p>
     * Summary: Il test verifica che il metodo {@code add(int index, Object element)}
     * sia in grado di inserire correttamente un riferimento {@code null} in una posizione
     * *casuale ma intermedia* all'interno di una lista già popolata. Si assicura che gli elementi
     * preesistenti vengano spostati per fare spazio al {@code null} e che la dimensione
     * della lista sia aggiornata di conseguenza, confermando che i valori null sono
     * trattati come elementi validi per l'inserimento posizionale.
     * <p>
     * Test Case Design: Questo test è progettato per assicurare che il metodo
     * {@code add(int, Object)} gestisca correttamente l'inserimento di un valore {@code null}
     * in un indice arbitrario non di bordo (escludendo l'inizio e la fine). La motivazione
     * è convalidare che la lista possa contenere e gestire elementi {@code null} in posizioni
     * intermedie, mantenendo l'integrità strutturale e di contenuto. Richiede che la lista
     * iniziale abbia almeno 3 elementi per poter generare un indice intermedio valido.
     * <p>
     * Test Description: 1) Si determina la dimensione iniziale della lista.
     *                   2) Si genera un indice casuale, {@code randomIndex}, compreso tra 1 (incluso) e {@code list.size() - 2} (incluso),
     *                      per garantire un inserimento intermedio.
     *                   3) Si prepara l'oggetto {@code null} da inserire.
     *                   4) Si crea una copia dello stato iniziale della lista ({@code initialContent}) per confrontare
     *                      il contenuto dopo l'inserimento.
     *                   5) Si chiama il metodo {@code add()} sulla lista, specificando {@code randomIndex} e {@code nullObject}.
     *                   6) Si verifica che la dimensione della lista sia aumentata correttamente di 1.
     *                   7) Si verifica che l'elemento aggiunto ({@code null}) sia presente all'indice {@code randomIndex}.
     *                   8) Si itera sugli elementi prima di {@code randomIndex} per assicurarsi che non siano stati modificati.
     *                   9) Si itera sugli elementi da {@code randomIndex} in poi della lista originale per assicurarsi
     *                      che siano stati correttamente spostati di una posizione a destra.
     * 
     * NB: Si usa {@link org.junit.Assume#assumeTrue(boolean)} per saltare il test se la lista non ha un numero sufficiente di elementi
     * per generare un indice intermedio valido (almeno 3 elementi).
     * <p>
     * Preconditions: La lista è stata inizializzata e popolata con 4 elementi: ["uno", "due", "tre", "quattro"].
     * Per l'esecuzione di questo test specifico, la dimensione della lista deve essere almeno 3.
     * <p>
     * Postconditions: La lista contiene un elemento {@code null} in più all'indice specificato.
     * Gli elementi che erano originariamente agli indici >= {@code randomIndex} sono stati spostati di una posizione a destra.
     * La dimensione della lista è {@code initialSize + 1}.
     * <p>
     * @expected L'elemento {@code null} deve trovarsi all'indice casuale generato (tra 1 e {@code initialSize - 2}).
     * La dimensione finale deve essere {@code initialSize + 1}.
     * Tutti gli elementi originali prima dell'indice devono rimanere invariati.
     * Tutti gli elementi originali a partire dall'indice devono essere stati spostati di una posizione a destra.
     */
    @Test
    public void testAddNullAtIndexPopulatedList() {
        int initialSize = list.size();

        Assume.assumeTrue("Test saltato: la lista iniziale non ha abbastanza elementi per un indice intermedio casuale (minimo 3).", initialSize >= 3);

        Random rand = new Random();

        int randomIndex = rand.nextInt(initialSize - 2) + 1; // Genero un numero tra 1 e (list.size() - 2)

        Object nullObject = null;

        // Cattura lo stato della lista prima della modifica per verifiche successive
        Object[] initialContent = new Object[initialSize];
        for (int i = 0; i < initialSize; i++) {
            initialContent[i] = list.get(i);
        }

        list.add(randomIndex, nullObject);

        // --- Verifiche ---
        assertEquals("La dimensione della lista non è corretta dopo l'aggiunta.", initialSize + 1, list.size());

        assertNull("L'elemento aggiunto non si trova all'indice corretto o non è null.", list.get(randomIndex));

        for (int i = 0; i < randomIndex; i++) {
            assertEquals("Elemento all'indice " + i + " è stato modificato inaspettatamente.", initialContent[i], list.get(i));
        }

        for (int i = randomIndex; i < initialSize; i++) {
            assertEquals("Elemento all'indice " + i + " non è stato spostato correttamente (atteso all'indice " + (i + 1) + ").", initialContent[i], list.get(i + 1));
        }
    }

    /**
     * Test del metodo {@link HList#add(int, Object)}.
     * <p>
     * Summary: Il test verifica che il metodo {@code add(int index, Object element)}
     * lanci correttamente una {@link java.lang.IndexOutOfBoundsException}
     * quando si tenta di aggiungere un elemento a un indice negativo.
     * Questo assicura che il metodo rispetti il contratto specificato dal J2SE
     * Collections Framework, che definisce gli indici validi come non negativi
     * e minori o uguali alla dimensione della lista.
     * <p>
     * Test Case Design: Questo test è progettato per verificare la gestione degli indici negativi
     * per il metodo {@code add(int, Object)}. La motivazione è assicurare che il metodo
     * non consenta inserimenti in posizioni logiche non valide della lista (prima dell'inizio)
     * e che segnali tale violazione tramite l'eccezione appropriata.
     * <p>
     * Test Description: 1) Si tenta di chiamare il metodo {@code add()} sulla lista popolata dal {@code setUp()},
     *                      fornendo un indice intenzionalmente negativo (ad esempio, -1) e un elemento qualsiasi ("elemento").
     * <p>
     * Preconditions: La lista è stata inizializzata e popolata (ad esempio, con 4 elementi).
     * <p>
     * Postconditions: La lista rimane invariata nel suo contenuto e nella sua dimensione,
     * poiché l'operazione di aggiunta viene interrotta dal lancio dell'eccezione.
     * <p>
     * Expected Result: Il test deve completarsi con successo solo se viene lanciata una
     * {@link java.lang.IndexOutOfBoundsException} quando si tenta di aggiungere un elemento
     * a un indice negativo.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAtIndexNegativePopulatedList() {
        list.add(-1, "elemento");
    }

    //------- TEST DEL METODO remove(Object) ----------

    /**
     * Test del metodo {@link HList#remove(Object)}.
     * <p>
     * Summary: Il test verifica che il metodo {@code remove(Object o)} rimuova correttamente
     * la prima occorrenza specificata di un elemento da una lista popolata. Si assicura
     * che la dimensione della lista venga aggiornata, che gli elementi successivi a quello
     * rimosso si spostino correttamente per riempire lo spazio, e che il metodo restituisca
     * {@code true} se l'elemento è stato trovato e rimosso. Questo test conferma il corretto
     * comportamento della rimozione per valore.
     * <p>
     * Test Case Design: Questo test è progettato per verificare la rimozione di un elemento
     * che si trova in una posizione intermedia della lista. La motivazione è assicurare
     * che il metodo {@code remove(Object)} non solo identifichi e rimuova l'elemento corretto,
     * ma anche che gestisca lo spostamento degli elementi rimanenti, mantenendo l'integrità
     * strutturale e l'ordine della lista.
     * <p>
     * Test Description: 1) Si chiama il metodo {@code remove()} sulla lista già popolata dal {@code setUp()},
     *                      passando l'oggetto "due" come elemento da rimuovere.
     *                   2) Si verifica, tramite {@code assertTrue()}, che il valore booleano restituito da {@code remove()}
     *                      sia {@code true}, confermando che l'operazione di rimozione ha avuto successo (l'elemento era presente).
     *                   3) Si verifica che la dimensione della lista sia diminuita correttamente da 4 a 3,
     *                      utilizzando {@code assertEquals(3, list.size())}.
     *                   4) Si verifica, tramite {@code assertFalse()}, che l'elemento "due" non sia più presente nella lista,
     *                      utilizzando {@code list.contains("due")}.
     *                   5) Si verifica il contenuto della lista elemento per elemento, per assicurarsi che gli elementi
     *                      rimanenti ("uno", "tre", "quattro") siano presenti nell'ordine corretto e che "tre" abbia
     *                      scalato all'indice precedentemente occupato da "due".
     * <p>
     * Preconditions: La lista è stata inizializzata e popolata con 4 elementi: ["uno", "due", "tre", "quattro"].
     * L'elemento "due" è presente nella lista.
     * <p>
     * Postconditions: L'elemento "due" è stato rimosso dalla lista.
     * La lista ora contiene 3 elementi: ["uno", "tre", "quattro"].
     * La dimensione della lista è 3.
     * <p>
     * Expected Result: La chiamata a {@code remove("due")} deve restituire {@code true}.
     * La dimensione della lista dopo la rimozione deve essere 3.
     * La lista non deve più contenere l'elemento "due".
     * La lista finale deve essere `["uno", "tre", "quattro"]`.
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
     * Summary: Verifica che il metodo {@code remove(int index)} rimuova correttamente
     * l'elemento all'indice specificato casualmente da una lista popolata. Questo test
     * assicura che gli elementi successivi a quello rimosso si spostino a sinistra per
     * riempire lo spazio, che la dimensione della lista venga aggiornata, e che il metodo
     * restituisca l'elemento correttamente rimosso.
     * <p>
     * Test Case Design: Questo test è progettato per verificare la funzionalità di rimozione
     * per indice su una posizione arbitraria e valida della lista (inizio, mezzo o fine).
     * La motivazione è garantire la robustezza del metodo {@code remove(int)} su un intervallo
     * completo di indici validi per la rimozione.
     * <p>
     * Test Description: 1) Si determina la dimensione iniziale della lista.
     * 2) Si genera un indice casuale valido, {@code randomIndex}, compreso tra 0 (incluso) e
     * {@code list.size() - 1} (incluso).
     * 3) Si crea una copia dello stato iniziale della lista ({@code initialContent}) per
     * confrontare il contenuto dopo la rimozione e identificare l'elemento atteso.
     * 4) Si esegue l'operazione di rimozione, memorizzando l'elemento restituito.
     * 5) Si verifica che la dimensione della lista sia diminuita di 1.
     * 6) Si verifica che l'elemento restituito dal metodo sia quello che era originariamente
     * all'indice {@code randomIndex}.
     * 7) Si itera sugli elementi prima di {@code randomIndex} per assicurarsi che non siano stati modificati.
     * 8) Si itera sugli elementi *dopo* {@code randomIndex} nella lista originale e si verifica
     * che gli stessi elementi siano ora presenti un indice indietro nella lista modificata.
     * 
     * NB: Si usa {@link org.junit.Assume#assumeTrue(boolean)} per saltare il test se la lista è vuota,
     * dato che non si può rimuovere da una lista vuota.
     * <p>
     * Preconditions: La lista è stata inizializzata e popolata con 4 elementi: ["uno", "due", "tre", "quattro"].
     * La lista deve avere almeno un elemento (ovvero non essere vuota) affinché il test sia valido.
     * <p>
     * Postconditions: L'elemento all'indice {@code randomIndex} è stato rimosso.
     * Gli elementi che erano originariamente agli indici > {@code randomIndex} sono stati spostati di una posizione a sinistra.
     * La dimensione della lista è {@code initialSize - 1}.
     * <p>
     * @expected Il metodo {@code remove(int)} deve restituire l'elemento che era all'indice {@code randomIndex}.
     * La dimensione finale deve essere la dimensione iniziale - 1.
     * Tutti gli elementi originali prima dell'indice rimosso devono rimanere invariati.
     * Tutti gli elementi originali dopo l'indice rimosso devono essere stati spostati di una posizione a sinistra.
     */
    @Test
    public void testRemoveAtIndexRandomPopulatedList() 
    {
        int initialSize = list.size(); 

        Assume.assumeTrue("Test saltato: la lista è vuota, impossibile rimuovere elementi per indice.", initialSize > 0);

        Random rand = new Random();
        int randomIndex = rand.nextInt(initialSize);

        Object[] initialContent = new Object[initialSize];
        for (int i = 0; i < initialSize; i++) 
        {
            initialContent[i] = list.get(i);
        }
        Object expectedRemovedElement = initialContent[randomIndex];

        Object actualRemovedElement = list.remove(randomIndex);

        assertEquals("La dimensione della lista non è corretta dopo la rimozione.", initialSize - 1, list.size());

        assertEquals("L'elemento restituito non corrisponde a quello rimosso.", expectedRemovedElement, actualRemovedElement);

        for (int i = 0; i < randomIndex; i++) 
        {
            assertEquals("Elemento all'indice " + i + " è stato modificato inaspettatamente.", initialContent[i], list.get(i));
        }

        for (int i = randomIndex; i < initialSize - 1; i++) 
        { // La lista ora è più piccola di 1
            assertEquals("Elemento all'indice " + (i + 1) + " non è stato spostato correttamente (atteso all'indice " + i + ").", initialContent[i + 1], list.get(i));
        }
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