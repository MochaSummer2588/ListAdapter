package myAdapter;

//===== IMPORTAZIONI =====
import java.util.Vector;
/**
 * <b>Summary:</b>
 * <p>
 * La classe {@code ListAdapter} fornisce un'implementazione dell'interfaccia {@link myAdapter.HList}
 * adattando le funzionalità della classe {@link java.util.Vector} di CLDC 1.1.
 * Questa classe permette di utilizzare un {@code java.util.Vector} come una {@code HList},
 * fornendo un'interfaccia familiare e conforme al Collections Framework semplificato.
 * </p>
 * <p>
 * <b>Descrizione Dettagliata:</b>
 * <p>
 * {@code ListAdapter} funge da "adattatore" per {@link java.util.Vector}, che è una classe
 * che offre funzionalità di lista dinamica ma appartiene a un framework Java più vecchio
 * (CLDC 1.1). Attraverso {@code ListAdapter}, le operazioni di base della lista (aggiunta,
 * rimozione, accesso per indice, iterazione) vengono mappate ai metodi equivalenti di {@code Vector}.
 * </p>
 * <p>
 * Questa implementazione si concentra sulla corretta traduzione delle chiamate ai metodi
 * dell'interfaccia {@code HList} in operazioni su un'istanza interna di {@code Vector}.
 * Vengono gestite le eccezioni e le condizioni al contorno (come indici fuori limite)
 * per garantire un comportamento conforme alla specifica {@code HList}.
 * </p>
 * <p>
 * I costruttori permettono l'inizializzazione di un {@code ListAdapter} vuoto o con
 * una capacità iniziale specificata.
 * </p>
 * <p>
 * La classe interna {@code SubList} estende le funzionalità
 * base di {@code ListAdapter} per fornire viste su porzioni
 * della lista, rispettivamente, mantenendo il "backing" con la {@code Vector} sottostante.
 * </p>
 *
 * @see myAdapter.HList
 * @see myAdapter.HCollection
 * @see myAdapter.HIterator
 * @see myAdapter.HListIterator
 * @see java.util.Vector
 */
public class ListAdapter implements HList
{
    //===== VARIABILI DI ISTANZA =====
    // Adaptee - il Vector di CLDC 1.1 

    /**
    * L'istanza di {@link java.util.Vector} che viene adattata per implementare {@link myAdapter.HList}.
    * Questo è l'adaptee su cui vengono eseguite tutte le operazioni della lista.
    */
    private Vector vector;

    //===== COSTRUTTORI DISCENDENTI DA QUELLI DI VECTOR =====

    /**
     * Costruttore di default che crea un {@code ListAdapter} vuoto.
     * La lista sottostante {@link java.util.Vector} viene inizializzata con la sua capacità predefinita.
     */
    public ListAdapter() 
    {
        this.vector = new Vector();
    }

    /**
     * Costruttore che crea un {@code ListAdapter} con una capacità iniziale specificata.
     * La {@link java.util.Vector} sottostante viene inizializzata con la {@code initialCapacity} fornita.
     *
     * @param initialCapacity la capacità iniziale della lista
     * @throws IllegalArgumentException se la capacità iniziale specificata è negativa.
     */
    public ListAdapter(int initialCapacity) 
    {
        this.vector = new Vector(initialCapacity);
    }
    
    // =============== METODI LISTADAPTER ===============
    
    /**
     * {@inheritDoc}
     *
     * Inserisce l'elemento specificato alla posizione specificata in questa lista (operazione opzionale).
     * Sposta l'elemento attualmente a quella posizione (se presente) e qualsiasi elemento
     * successivo a destra (aggiunge uno ai loro indici).
     *
     * @param index indice al quale l'elemento specificato deve essere inserito
     * @param element elemento da inserire
     * @throws IndexOutOfBoundsException se l'indice è fuori intervallo (index &lt; 0 || index &gt; size())
     */
    public void add(int index, Object element)                  
    {
        // Prova a inserire l'elemento nella posizione specificata
        try 
        {
            vector.insertElementAt(element, index);
        } 
        catch (ArrayIndexOutOfBoundsException e) 
        {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }

    /**
     * {@inheritDoc}
     * Assicura che questa collezione contenga l'elemento specificato (operazione opzionale).
     * Restituisce true se questa collezione è cambiata come risultato della chiamata.
     * Le collezioni che supportano questa operazione possono porre limitazioni su quali
     * elementi possono essere aggiunti a questa collezione.
     *
     * @param o elemento di cui deve essere assicurata la presenza in questa collezione
     * @return true se questa collezione è cambiata come risultato della chiamata
     */
    public boolean add(Object o)                            
    {
        // Aggiunge l'elemento alla fine della lista
        vector.addElement(o);
        return true;
    }
    
    /**
     * {@inheritDoc}
     * Aggiunge tutti gli elementi nella collezione specificata a questa collezione (operazione opzionale).
     * Il comportamento di questa operazione è indefinito se la collezione specificata viene modificata
     * mentre l'operazione è in corso.
     *
     * @param c collezione contenente elementi da aggiungere a questa collezione
     * @return true se questa collezione è cambiata come risultato della chiamata
     * @throws NullPointerException se la collezione specificata e' null
     */
    public boolean addAll(HCollection c)                            
    {
        if(c == null) 
        {
            throw new NullPointerException("Collection cannot be null");
        }

        if(c.isEmpty()) 
        {
            return false; // Se la collezione è vuota, non aggiunge nulla
        }

        Object[] arrayObject = c.toArray();

        for (int i = 0; i < arrayObject.length; i++) 
        {
            this.add(arrayObject[i]);
        }

        return true;
    }

    /**
     * Inserisce tutti gli elementi nella collezione specificata in questa lista alla
     * posizione specificata (operazione opzionale). Sposta l'elemento attualmente a
     * quella posizione (se presente) e qualsiasi elemento successivo a destra (aumenta i loro indici).
     *
     * @param index indice al quale inserire il primo elemento dalla collezione specificata
     * @param c collezione contenente elementi da aggiungere a questa lista
     * @return true se questa lista è cambiata come risultato della chiamata
     * @throws NullPointerException se la collezione specificata e' null
     * @throws IndexOutOfBoundsException se l'indice è fuori intervallo (index &lt; 0 || index &gt; size())
     */
    public boolean addAll(int index, HCollection c) 
    {
        if (c == null) 
        {
            throw new NullPointerException("Collection cannot be null");
        }

        if (index < 0 || index > vector.size()) 
        {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }

        Object[] arrayObject = c.toArray();

        if (arrayObject.length == 0) 
        {
            return false; // Se la collezione è vuota, non aggiunge nulla
        }

        for (int i = 0; i < arrayObject.length; i++) 
        {
            this.add(index + i, arrayObject[i]);
        }

        return true;
    }
    
    /**
     * {@inheritDoc}
     * Rimuove tutti gli elementi da questa collezione (operazione opzionale).
     * La collezione sarà vuota dopo che questo metodo ritorna.
     */
    public void clear() 
    {
        vector.removeAllElements();             // Rimuove tutti gli elementi dal Vector    
    }
    
    /**
     * {@inheritDoc}
     * Restituisce true se questa collezione contiene l'elemento specificato.
     * Più formalmente, restituisce true se e solo se questa collezione contiene
     * almeno un elemento e tale che (o==null ? e==null : o.equals(e)).
     *
     * @param o elemento di cui verificare la presenza in questa collezione
     * @return true se questa collezione contiene l'elemento specificato
     */
    public boolean contains(Object o) 
    {
        return vector.contains(o);              // Restituisce true se l'elemento è presente nel Vector
    }
    
    /**
     * {@inheritDoc}
     * Restituisce true se questa collezione contiene tutti gli elementi della collezione specificata.
     *
     * @param c collezione da verificare per il contenimento in questa collezione
     * @return true se questa collezione contiene tutti gli elementi della collezione specificata
     * @throws NullPointerException se la collezione specificata e' null
     */
    public boolean containsAll(HCollection c) 
    {
        if (c == null) 
        {
            throw new NullPointerException("Collection cannot be null");
        }

        Object[] arrayObject = c.toArray();
        
        for (int i = 0; i < arrayObject.length; i++) 
        {
            if (!this.contains(arrayObject[i])) 
            {
                return false; // Se un elemento non è presente, restituisce false
            }
        }
        
        return true; // Tutti gli elementi sono presenti
    }
    
    /**
     * {@inheritDoc}
     * Confronta l'oggetto specificato con questa collezione per l'uguaglianza.
     * Mentre l'interfaccia Collection non aggiunge stipulazioni al contratto generale
     * per Object.equals, i programmatori che implementano l'interfaccia Collection
     * "direttamente" devono fare attenzione se scelgono di sovrascrivere Object.equals.
     *
     * @param o oggetto da confrontare per l'uguaglianza con questa collezione
     * @return true se l'oggetto specificato è uguale a questa collezione
     */
    public boolean equals(Object o) 
    {
        if (this == o) 
        {
            return true;
        }
        
        if (!(o instanceof HList)) 
        {
            return false;
        }
        
        HList other = (HList) o;
        
        if (this.size() != other.size()) 
        {
            return false;
        }
        
        // CONFRONTO POSIZIONE PER POSIZIONE
        for (int i = 0; i < this.size(); i++) 
        {
            Object thisElement = this.get(i);        // Elemento alla posizione i della mia lista
            Object otherElement = other.get(i);      // Elemento alla posizione i della collezione da confrontare
            
            // Confronto usando la regola standard
            if (thisElement == null) 
            {
                if (otherElement != null) 
                {
                    return false;
                }
            } 
            else 
            {
                if (!thisElement.equals(otherElement)) 
                {
                    return false;
                }
            }
        }
        
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * Restituisce l'elemento alla posizione specificata in questa lista.
     *
     * @param index indice dell'elemento da restituire
     * @return l'elemento alla posizione specificata in questa lista
     * @throws IndexOutOfBoundsException se l'indice è fuori intervallo (index &lt; 0 || index &gt;= size())
     */
    public Object get(int index)                                    
    {
        // Controlla se l'indice è valido
        if (index < 0 || index >= vector.size()) 
        {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        
        // Restituisce l'elemento alla posizione specificata
        return vector.elementAt(index);
    }
    
    /**
     * {@inheritDoc}
     * Restituisce il valore del codice hash per questa collezione. Mentre l'interfaccia
     * Collection non aggiunge stipulazioni al contratto generale per Object.hashCode,
     * i programmatori devono notare che qualsiasi classe che sovrascrive il metodo
     * Object.equals deve anche sovrascrivere il metodo Object.hashCode per soddisfare
     * il contratto generale del metodo Object.hashCode.
     *
     * @return il valore del codice hash per questa collezione
     */
    public int hashCode() 
    {
        int hashCode = 1;
        HIterator i = this.iterator();
        while (i.hasNext()) 
        {
            Object obj = i.next();
            hashCode = 31*hashCode + (obj==null ? 0 : obj.hashCode());
        }
        return hashCode; // Calcola l'hash code basato sugli elementi della lista
    }
    

    /**
     * {@inheritDoc}
     * Restituisce l'indice della prima occorrenza dell'elemento specificato in questa lista,
     * o -1 se questa lista non contiene l'elemento. Più formalmente, restituisce l'indice
     * più basso i tale che (o==null ? get(i)==null : o.equals(get(i))), o -1 se non esiste tale indice.
     *
     * @param o elemento da cercare
     * @return l'indice della prima occorrenza dell'elemento specificato in questa lista,
     *         o -1 se questa lista non contiene l'elemento
     */
    public int indexOf(Object o)                      
    {
        return vector.indexOf(o); // Restituisce l'indice della prima occorrenza dell'elemento
    }
    
    /**
     * {@inheritDoc}
     * Restituisce true se questa collezione non contiene elementi.
     *
     * @return true se questa collezione non contiene elementi
     */
    public boolean isEmpty() 
    {
        return vector.isEmpty(); // Restituisce true se il Vector è vuoto
    }
    
    /**
     * {@inheritDoc}
     * Restituisce un iteratore sugli elementi in questa collezione. Non ci sono
     * garanzie riguardo all'ordine in cui gli elementi sono restituiti (a meno che
     * questa collezione sia un'istanza di qualche classe che fornisce una garanzia).
     *
     * @return un HIterator sugli elementi in questa collezione
     */
    public HIterator iterator() 
    {
        return new ListIterator(this);
    }

    /**
     *{@inheritDoc}
     * Restituisce l'indice dell'ultima occorrenza dell'elemento specificato in questa lista,
     * o -1 se questa lista non contiene l'elemento. Più formalmente, restituisce l'indice
     * più alto i tale che (o==null ? get(i)==null : o.equals(get(i))), o -1 se non esiste tale indice.
     *
     * @param o elemento da cercare
     * @return l'indice dell'ultima occorrenza dell'elemento specificato in questa lista,
     *         o -1 se questa lista non contiene l'elemento
     */
    public int lastIndexOf(Object o) 
    {
        return vector.lastIndexOf(o);                           // Restituisce l'indice dell'ultima occorrenza dell'elemento
    }

    /**
     * Restituisce un iteratore di lista sugli elementi in questa lista (in sequenza corretta).
     *
     * @return un iteratore di lista sugli elementi in questa lista (in sequenza corretta)
     */
    public HListIterator listIterator() 
    {
        return new ListIterator(this);                          // Restituisce un nuovo ListIterator per questa lista
    }
    
    /**
     * {@inheritDoc}
     * Restituisce un iteratore di lista sugli elementi in questa lista (in sequenza corretta),
     * iniziando alla posizione specificata nella lista. L'indice specificato indica il
     * primo elemento che sarebbe restituito da una chiamata iniziale a next.
     *
     * @param index indice del primo elemento da restituire dall'iteratore di lista
     * @return un iteratore di lista sugli elementi in questa lista (in sequenza corretta),
     *         iniziando alla posizione specificata nella lista
     * @throws IndexOutOfBoundsException se l'indice è fuori intervallo (index &lt; 0 || index &gt; size())
     */
    public HListIterator listIterator(int index) 
    {
        if (index < 0 || index > vector.size()) 
        {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        return new ListIterator(this, index);                   // Restituisce un nuovo ListIterator per questa lista a partire dall'indice specificato
    }

    /**
     * Rimuove l'elemento alla posizione specificata in questa lista (operazione opzionale).
     * Sposta qualsiasi elemento successivo a sinistra (sottrae uno dai loro indici).
     * Restituisce l'elemento che è stato rimosso dalla lista.
     *
     * @param index l'indice dell'elemento da rimuovere
     * @return l'elemento che è stato rimosso dalla lista
     * @throws IndexOutOfBoundsException se l'indice è fuori intervallo (index &lt; 0 || index &gt;= size())
     */
    public Object remove(int index) 
    {
        // Controlla se l'indice è valido
        if (index < 0 || index >= vector.size()) 
        {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        
        // Rimuove l'elemento alla posizione specificata e lo restituisce
        Object removedElement = vector.elementAt(index);
        vector.removeElementAt(index);
        return removedElement;
    }
    
    /**
     * {@inheritDoc}
     * Rimuove una singola istanza dell'elemento specificato da questa collezione,
     * se è presente (operazione opzionale). Più formalmente, rimuove un elemento e
     * tale che (o==null ? e==null : o.equals(e)), se questa collezione contiene
     * uno o più di tali elementi.
     *
     * @param o elemento da rimuovere da questa collezione, se presente
     * @return true se un elemento è stato rimosso come risultato di questa chiamata
     */
    public boolean remove(Object o) 
    {
        boolean removed = vector.removeElement(o);                  // Rimuove l'elemento specificato dalla collezione
        return removed;                                             // Restituisce true se l'elemento è stato rimosso, false altrimenti
    }
    
    /**
     * {@inheritDoc}
     * Rimuove tutti gli elementi di questa collezione che sono anche contenuti
     * nella collezione specificata (operazione opzionale). Dopo questa chiamata
     * ritorna, questa collezione non conterrà elementi in comune con la collezione specificata.
     *
     * @param c collezione contenente elementi da rimuovere da questa collezione
     * @return true se questa collezione è cambiata come risultato della chiamata
     * @throws NullPointerException se la collezione passata come parametro e' "null"
     */
    public boolean removeAll(HCollection c) 
    {
        if (c == null) 
        {
            throw new NullPointerException("Collection cannot be null");
        }

        boolean modified = false;
        Object[] arrayObject = c.toArray();

        for (int i = 0; i < arrayObject.length; i++) 
        {
            // Continua a rimuovere finché l'elemento è presente
            while (this.remove(arrayObject[i])) 
            {
                modified = true;
            }
        }

        return modified;
    }
    
    /**
     * {@inheritDoc}
     * Mantiene solo gli elementi in questa collezione che sono contenuti nella
     * collezione specificata (operazione opzionale). In altre parole, rimuove da
     * questa collezione tutti i suoi elementi che non sono contenuti nella collezione specificata.
     *
     * @param c collezione contenente elementi da mantenere in questa collezione
     * @return true se questa collezione è cambiata come risultato della chiamata
     * @throws NullPointerException se la collezione passata come parametro e' "null"
     */
    public boolean retainAll(HCollection c) 
    {
        if (c == null) 
        {
            throw new NullPointerException("Collection cannot be null");
        }

        boolean modified = false;
        
        for (int i = vector.size() - 1; i >= 0; i--) 
        {
            Object element = vector.elementAt(i);
            
            // Se l'elemento NON è contenuto nella collezione c, lo rimuove
            if (!c.contains(element)) 
            {
                this.remove(i);
                modified = true;
            }
        }
        
        return modified;
    }

    /**
     * {@inheritDoc}
     * Sostituisce l'elemento alla posizione specificata in questa lista con l'elemento
     * specificato (operazione opzionale).
     *
     * @param index indice dell'elemento da sostituire
     * @param element elemento da memorizzare alla posizione specificata
     * @return l'elemento precedentemente alla posizione specificata
     * @throws IndexOutOfBoundsException se l'indice è fuori intervallo (index &lt; 0 || index &gt;= size())
     */
    public Object set(int index, Object element) 
    {
        // Controlla se l'indice è valido
        if (index < 0 || index >= vector.size()) 
        {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        // Sostituisce l'elemento alla posizione specificata e restituisce il vecchio elemento
        Object oldElement = this.get(index);
        vector.setElementAt(element, index);
        return oldElement;
    }
    
    /**
     * {@inheritDoc}
     * Restituisce il numero di elementi in questa collezione. Se questa collezione
     * contiene più di Integer.MAX_VALUE elementi, restituisce Integer.MAX_VALUE.
     *
     * @return il numero di elementi in questa collezione
     */
    public int size() 
    {
        return vector.size();
    }

    /**
     * {@inheritDoc}
     * Restituisce una vista della porzione di questa lista tra il fromIndex specificato,
     * incluso, e toIndex, escluso. (Se fromIndex e toIndex sono uguali, la lista restituita
     * è vuota.) La lista restituita è supportata da questa lista, quindi i cambiamenti
     * non strutturali nella lista restituita si riflettono in questa lista, e viceversa.
     *
     * @param fromIndex endpoint basso (incluso) della subList
     * @param toIndex endpoint alto (escluso) della subList
     * @return una vista dell'intervallo specificato all'interno di questa lista
     * @throws IndexOutOfBoundsException per un valore di indice endpoint illegale
     *         (fromIndex &lt; 0 || toIndex &gt; size || fromIndex &gt; toIndex)
     */
    public HList subList(int fromIndex, int toIndex) 
    {
        if (fromIndex < 0 || toIndex > size() || fromIndex > toIndex) 
        {
            throw new IndexOutOfBoundsException("fromIndex: " + fromIndex + ", toIndex: " + toIndex + ", size: " + size());
        }
        return new SubList(this, fromIndex, toIndex);
    }
    
    /**
     * {@inheritDoc}
     * Restituisce un array contenente tutti gli elementi in questa collezione.
     * Se questa collezione fornisce garanzie sull'ordine in cui i suoi elementi
     * sono restituiti dal suo iteratore, questo metodo deve restituire gli elementi
     * nello stesso ordine. L'array restituito sarà "sicuro" in quanto nessun riferimento
     * ad esso è mantenuto da questa collezione.
     *
     * @return un array contenente tutti gli elementi in questa collezione
     */
    public Object[] toArray()                           
    {
        Object[] array = new Object[vector.size()];
        for (int i = 0; i < vector.size(); i++) 
        {
            array[i] = vector.elementAt(i);
        }
        return array;
    }
    
    /**
     * {@inheritDoc}
     * Restituisce un array contenente tutti gli elementi in questa collezione;
     * il tipo runtime dell'array restituito è quello dell'array specificato.
     * Se la collezione si adatta nell'array specificato, vi viene restituita.
     * Altrimenti, viene allocato un nuovo array con il tipo runtime dell'array
     * specificato e la dimensione di questa collezione.
     *
     * @param a l'array in cui gli elementi della collezione devono essere memorizzati, se è abbastanza grande
     * @return un array contenente tutti gli elementi in questa collezione
     * @throws NullPointerException se l'array specificato è null
     */
    public Object[] toArray(Object[] a) 
    {
        if (a == null) 
        {
            throw new NullPointerException("Input array is null");
        }

        int size = vector.size();
        Object[] result = a;

        if (a.length < size) 
        {
            result = new Object[size];
        }

        for (int i = 0; i < size; i++) 
        {
            result[i] = vector.elementAt(i);
        }

        if (result.length > size) 
        {
            result[size] = null;
        }

        return result;
    }

    /**
     * Implementazione della vista per una sottolista di un `ListAdapter`.
     * Questa classe è "backed" dalla lista `ListAdapter` padre, il che significa
     * che le modifiche a questa sottolista si riflettono nella lista padre e viceversa.
     * Le operazioni sono delegate alla lista padre con un offset appropriato.
     */
    private static class SubList extends ListAdapter 
    {
        /**
         * Riferimento alla lista ListAdapter madre da cui questa sottolista è una vista.
         */
        private final ListAdapter parent;
        
        /**
         * L'offset di inizio di questa sottolista rispetto all'inizio della lista madre.
         * Tutti gli indici forniti ai metodi di questa sottolista verranno sommati a questo offset
         * prima di essere passati ai metodi corrispondenti della lista padre.
         */
        private final int offset;

        /**
         * La dimensione attuale di questa sottolista.
         * Viene aggiornata ad ogni modifica strutturale (aggiunta/rimozione) che avviene
         * tramite questa sottolista.
         */
        private int size;

        /**
         * Costruisce una nuova istanza di `SubList` come vista di una porzione della lista padre.
         *
         * @param parent la lista `ListAdapter` che funge da backing per questa sottolista.
         * @param fromIndex l'indice di inizio, inclusivo, della porzione nella lista padre.
         * @param toIndex l'indice di fine, esclusivo, della porzione nella lista padre.
         */
        SubList(ListAdapter parent, int fromIndex, int toIndex) 
        {
            this.parent = parent;
            this.offset = fromIndex;
            this.size = toIndex - fromIndex;
        }

        // =============== METODI PRIVATI DI SUPPORTO ===============

        /**
         * Verifica che l'indice specificato sia all'interno dei limiti validi di questa sottolista.
         * Valido per operazioni come `get`, `set`, `remove(int)`.
         *
         * @param index l'indice da controllare.
         * @throws IndexOutOfBoundsException se l'indice è minore di 0 o maggiore o uguale alla dimensione della sottolista.
         */
        private void checkRange(int index) 
        {
            if (index < 0 || index >= size) 
            {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
            }
        }

        /**
         * Verifica che l'indice specificato sia all'interno dei limiti validi per operazioni di aggiunta.
         * Per le aggiunte, l'indice può essere uguale alla dimensione (aggiunta in coda).
         *
         * @param index l'indice da controllare.
         * @throws IndexOutOfBoundsException se l'indice è minore di 0 o maggiore della dimensione della sottolista.
         */
        private void checkRangeForAdd(int index) 
        {
            if (index < 0 || index > size) 
            {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
            }
        }

        // =============== METODI OVERRIDE ===============

        /**
         * {@inheritDoc}
         * Restituisce l'elemento nella posizione specificata all'interno di questa sottolista.
         * L'operazione è delegata alla lista padre con l'applicazione dell'offset.
         * @param index l'indice dell'elemento da restituire.
         * @return l'elemento nella posizione specificata.
         * @throws IndexOutOfBoundsException se l'indice è fuori dai limiti della sottolista.
         */
        public Object get(int index) 
        {
            checkRange(index);
            return parent.get(index + offset);
        }

        /**
         * {@inheritDoc}
         * Restituisce il numero di elementi in questa sottolista.
         * @return il numero di elementi in questa sottolista.
         */
        public int size() 
        {
            return size;
        }

        /**
         * {@inheritDoc}
         * Sostituisce l'elemento nella posizione specificata in questa sottolista con l'elemento specificato.
         * L'operazione è delegata alla lista padre con l'applicazione dell'offset.
         * @param index l'indice dell'elemento da sostituire.
         * @param element l'elemento da memorizzare nella posizione specificata.
         * @return l'elemento precedentemente presente nella posizione specificata.
         * @throws IndexOutOfBoundsException se l'indice è fuori dai limiti della sottolista.
         */
        public Object set(int index, Object element) 
        {
            checkRange(index);
            return parent.set(index + offset, element);
        }

        /**
         * {@inheritDoc}
         * Inserisce l'elemento specificato nella posizione specificata in questa sottolista.
         * Sposta l'elemento attualmente in quella posizione (se presente) e qualsiasi elemento
         * successivo verso destra (aumenta i loro indici di uno).
         * L'operazione è delegata alla lista padre con l'applicazione dell'offset e la dimensione
         * della sottolista viene aggiornata.
         * @param index l'indice in cui inserire l'elemento specificato.
         * @param element l'elemento da inserire.
         * @throws IndexOutOfBoundsException se l'indice è fuori dai limiti per l'aggiunta.
         */
        public void add(int index, Object element) 
        {
            checkRangeForAdd(index);
            parent.add(index + offset, element);
            size++;
        }

        /**
         * {@inheritDoc}
         * Rimuove l'elemento nella posizione specificata in questa sottolista.
         * Sposta qualsiasi elemento successivo verso sinistra (decrementa i loro indici di uno).
         * L'operazione è delegata alla lista padre con l'applicazione dell'offset e la dimensione
         * della sottolista viene aggiornata.
         * @param index l'indice dell'elemento da rimuovere.
         * @return l'elemento precedentemente nella posizione specificata.
         * @throws IndexOutOfBoundsException se l'indice è fuori dai limiti della sottolista.
         */
        public Object remove(int index) 
        {
            checkRange(index);
            Object result = parent.remove(index + offset);
            size--;
            return result;
        }

        /**
         * {@inheritDoc}
         * Aggiunge l'elemento specificato alla fine di questa sottolista.
         * L'operazione è delegata alla lista padre e la dimensione della sottolista viene aggiornata.
         * @param o l'elemento da aggiungere a questa sottolista.
         * @return `true` (come specificato da `HCollection.add`).
         */
        public boolean add(Object o) 
        {
            parent.add(offset + size, o);
            size++;
            return true;
        }

        /**
         * {@inheritDoc}
         * Aggiunge tutti gli elementi nella collezione specificata alla fine di questa sottolista,
         * nell'ordine in cui sono restituiti dall'iteratore della collezione specificata.
         * L'operazione è delegata alla lista padre e la dimensione della sottolista viene aggiornata.
         * @param c la collezione contenente gli elementi da aggiungere a questa sottolista.
         * @return `true` se questa sottolista è stata modificata a seguito della chiamata.
         */
        public boolean addAll(HCollection c) 
        {
            return addAll(size, c);
        }

        /**
         * {@inheritDoc}
         * Inserisce tutti gli elementi nella collezione specificata in questa sottolista,
         * a partire dalla posizione specificata.
         * L'operazione è delegata alla lista padre e la dimensione della sottolista viene aggiornata.
         * @param index l'indice in cui inserire il primo elemento dalla collezione specificata.
         * @param c la collezione contenente gli elementi da aggiungere a questa sottolista.
         * @return `true` se questa sottolista è stata modificata a seguito della chiamata.
         * @throws IndexOutOfBoundsException se l'indice è fuori dai limiti per l'aggiunta.
         */
        public boolean addAll(int index, HCollection c) 
        {
            checkRangeForAdd(index);
            int cSize = c.size();
            if (cSize == 0)
            {
                return false; // Se la collezione è vuota, non aggiunge nulla
            }
            
            parent.addAll(offset + index, c);
            size += cSize;
            return true;
        }

        /**
         * {@inheritDoc}
         * Rimuove tutti gli elementi da questa sottolista. La sottolista sarà vuota dopo questa chiamata.
         * Gli elementi corrispondenti vengono rimossi anche dalla lista padre.
         */
        public void clear() {
            for (int i = size - 1; i >= 0; i--) 
            {
                parent.remove(offset + i);
            }
            size = 0;
        }

        /**
         * {@inheritDoc}
         * Restituisce `true` se questa sottolista contiene l'elemento specificato.
         * @param o l'elemento la cui presenza in questa sottolista deve essere verificata.
         * @return `true` se questa sottolista contiene l'elemento specificato.
         */
        public boolean contains(Object o) 
        {
            return indexOf(o) != -1;
        }

        /**
         * {@inheritDoc}
         * Restituisce `true` se questa sottolista contiene tutti gli elementi della collezione specificata.
         * @param c la collezione da controllare per la contiguità in questa sottolista.
         * @return `true` se questa sottolista contiene tutti gli elementi della collezione specificata.
         */
        public boolean containsAll(HCollection c) 
        {
            HIterator it = c.iterator();
            while (it.hasNext()) 
            {
                if (!contains(it.next()))
                {
                    return false; // Se un elemento non è presente, restituisce false
                }
            }
            return true;
        }

         /**
         * {@inheritDoc}
         * Restituisce l'indice della prima occorrenza dell'elemento specificato in questa sottolista,
         * o -1 se questa sottolista non contiene l'elemento.
         * @param o l'elemento da cercare.
         * @return l'indice della prima occorrenza dell'elemento specificato in questa sottolista,
         * o -1 se questa sottolista non contiene l'elemento.
         */
        public int indexOf(Object o) 
        {
            for (int i = 0; i < size; i++) 
            {
                if (o == null ? get(i) == null : o.equals(get(i)))
                {
                    return i;
                }
            }
            return -1;
        }

        /**
         * {@inheritDoc}
         * Restituisce `true` se questa sottolista non contiene elementi.
         * @return `true` se questa sottolista non contiene elementi.
         */
        public boolean isEmpty() 
        {
            return size == 0;
        }

        /**
         * {@inheritDoc}
         * Restituisce l'indice dell'ultima occorrenza dell'elemento specificato in questa sottolista,
         * o -1 se questa sottolista non contiene l'elemento.
         * @param o l'elemento da cercare.
         * @return l'indice dell'ultima occorrenza dell'elemento specificato in questa sottolista,
         * o -1 se questa sottolista non contiene l'elemento.
         */
        public int lastIndexOf(Object o) 
        {
            for (int i = size - 1; i >= 0; i--) 
            {
                if (o == null ? get(i) == null : o.equals(get(i)))
                {
                    return i;
                }
            }
            return -1;
        }

        /**
         * {@inheritDoc}
         * Restituisce un iteratore sugli elementi in questa sottolista nell'ordine corretto.
         * @return un iteratore sugli elementi in questa sottolista nell'ordine corretto.
         */
        public HIterator iterator() 
        {
            return listIterator();
        }

        /**
         * {@inheritDoc}
         * Restituisce un list iterator sugli elementi in questa sottolista (nell'ordine corretto).
         * @return un list iterator sugli elementi in questa sottolista (nell'ordine corretto).
         */
        public HListIterator listIterator() 
        {
            return listIterator(0);
        }

        /**
         * {@inheritDoc}
         * Restituisce un list iterator sugli elementi in questa sottolista (nell'ordine corretto),
         * a partire dalla posizione specificata nella sottolista.
         * @param index l'indice del primo elemento che sarà restituito da `next()`.
         * @return un list iterator sugli elementi in questa sottolista (nell'ordine corretto).
         * @throws IndexOutOfBoundsException se l'indice è fuori dai limiti per l'aggiunta.
         */
        public HListIterator listIterator(int index) 
        {
            checkRangeForAdd(index);
            return new ListIterator(this, index);
        }

        /**
         * {@inheritDoc}
         * Rimuove la prima occorrenza dell'elemento specificato da questa sottolista, se presente.
         * @param o l'elemento da rimuovere da questa sottolista, se presente.
         * @return `true` se questa sottolista conteneva l'elemento specificato.
         */
        public boolean remove(Object o) 
        {
            int index = indexOf(o);
            if (index == -1)
            {
                return false; // Se l'elemento non è presente, non rimuove nulla
            }
            remove(index);
            return true;
        }

        /**
         * {@inheritDoc}
         * Rimuove da questa sottolista tutti i suoi elementi che sono contenuti anche nella collezione specificata.
         * @param c la collezione contenente gli elementi da rimuovere da questa sottolista.
         * @return `true` se questa sottolista è stata modificata a seguito della chiamata.
         */
        public boolean removeAll(HCollection c) 
        {
            boolean modified = false;
            HIterator it = iterator();
            while (it.hasNext()) 
            {
                if (c.contains(it.next())) 
                {
                    it.remove();
                    modified = true;
                }
            }
            return modified;
        }

        /**
         * {@inheritDoc}
         * Mantiene in questa sottolista solo gli elementi che sono contenuti nella collezione specificata.
         * In altre parole, rimuove da questa sottolista tutti i suoi elementi che non sono contenuti
         * nella collezione specificata.
         * @param c la collezione contenente gli elementi da mantenere in questa sottolista.
         * @return `true` se questa sottolista è stata modificata a seguito della chiamata.
         */
        public boolean retainAll(HCollection c) 
        {
            boolean modified = false;
            HIterator it = iterator();
            while (it.hasNext()) {
                if (!c.contains(it.next())) 
                {
                    it.remove();
                    modified = true;
                }
            }
            return modified;
        }

         /**
         * {@inheritDoc}
         * Restituisce una vista della porzione specificata di questa sottolista.
         * Questo crea una sottolista di una sottolista. La nuova sottolista sarà ancora
         * "backed" dalla lista `ListAdapter` originale, con un offset cumulativo.
         *
         * @param fromIndex l'indice di inizio, inclusivo, della sottolista (relativo a questa `SubList`).
         * @param toIndex l'indice di fine, esclusivo, della sottolista (relativo a questa `SubList`).
         * @return una vista della porzione specificata di questa sottolista.
         * @throws IndexOutOfBoundsException se un valore dell'indice è fuori dall'intervallo
         * (fromIndex < 0 || toIndex > size || fromIndex > toIndex).
         */
        public HList subList(int fromIndex, int toIndex) 
        {
            if (fromIndex < 0 || toIndex > size || fromIndex > toIndex)
            {
                throw new IndexOutOfBoundsException();
            }
            return new SubList(parent, offset + fromIndex, offset + toIndex);
        }

        /**
         * {@inheritDoc}
         * Restituisce un array contenente tutti gli elementi di questa sottolista nell'ordine corretto.
         * @return un array contenente tutti gli elementi di questa sottolista nell'ordine corretto.
         */
        public Object[] toArray() 
        {
            Object[] result = new Object[size];
            for (int i = 0; i < size; i++)
            {
                result[i] = get(i); // Copia gli elementi della sottolista nell'array
            }
            return result;
        }

        /**
         * {@inheritDoc}
         * Restituisce un array contenente tutti gli elementi di questa sottolista nell'ordine corretto;
         * il tipo di runtime dell'array restituito è quello dell'array specificato.
         * Se l'array specificato è sufficientemente grande, viene utilizzato; altrimenti,
         * viene allocato un nuovo array dello stesso tipo di runtime e della dimensione di questa sottolista.
         * Se l'array specificato è più grande di questa sottolista, l'elemento successivo
         * alla fine della sottolista nell'array viene impostato su `null`.
         * @param a l'array in cui memorizzare gli elementi della sottolista, se è abbastanza grande;
         * altrimenti, un nuovo array dello stesso tipo di runtime viene allocato per questo scopo.
         * @return un array contenente gli elementi di questa sottolista.
         * @throws NullPointerException se l'array specificato è nullo. (Nota: non gestita esplicitamente qui,
         * ma parte della specifica J2SE).
         * @throws ArrayStoreException se il tipo di runtime dell'array specificato non è un supertipo
         * del tipo di runtime di ogni elemento in questa sottolista.
         */
        public Object[] toArray(Object[] a) 
        {
            if (a.length < size)
                a = new Object[size];
            
            for (int i = 0; i < size; i++)
                a[i] = get(i);
            
            if (a.length > size)
                a[size] = null;
            
            return a;
        }
        
    }
}
