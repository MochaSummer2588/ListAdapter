package myAdapter;
import myExceptions.IllegalStateException;
/**
 * Implementazione di un iteratore bidirezionale (ListIterator) per la classe {@link ListAdapter}.
 * Questa classe consente di attraversare la lista in entrambe le direzioni,
 * modificare la lista durante l'iterazione e ottenere la posizione corrente dell'iteratore.
 * Si adatta all'interfaccia {@link HListIterator}, fornendo le funzionalità standard
 * di un ListIterator come definito nelle collezioni Java (J2SE).
 *
 * Le operazioni {@code remove()}, {@code set(Object)} e {@code add(Object)}
 * modificano la lista sottostante e aggiornano lo stato dell'iteratore di conseguenza.
 *
 * @see HListIterator
 * @see ListAdapter
 */
public class ListIterator implements HListIterator
{
    private ListAdapter list;       // Riferimento alla lista sottostante
    private int ptr;                // Indice del cursore corrente (posizione dell'elemento successivo)
    private int lastReturned;       // Indice dell'ultimo elemento restituito (-1 se nessuno)
    
    /**
     * Costruttore che crea un ListIteratorAdapter per la lista specificata.
     * 
     * @param list la lista da iterare
     */
    public ListIterator(ListAdapter list) 
    {
        this.list = list;
        this.ptr = 0;            // Inizia all'inizio della lista
        this.lastReturned = -1;     // Nessun elemento restituito ancora
    }
    
    /**
     * Costruttore che crea un ListIteratorAdapter per la lista specificata,
     * iniziando dalla posizione specificata.
     * 
     * @param list la lista da iterare
     * @param index la posizione iniziale del cursore
     * @throws IndexOutOfBoundsException se l'indice è fuori dai limiti
     */
    public ListIterator(ListAdapter list, int index) 
    {
        if(index < 0 || index > list.size()) 
        {
            throw new IndexOutOfBoundsException("Indice fuori dai limiti: " + index);
        }
        this.list = list;
        this.ptr = index;        // Inizia alla posizione specificata
        this.lastReturned = -1;     // Nessun elemento restituito ancora
    }

    /**
     * Restituisce true se questo iteratore di lista ha più elementi quando
     * attraversa la lista in direzione avanti. (In altre parole, restituisce
     * true se next() restituirebbe un elemento piuttosto che lanciare un'eccezione.)
     * 
     * @return true se l'iteratore di lista ha più elementi quando attraversa
     *         la lista in direzione avanti.
     */
    public boolean hasNext() 
    {
        return ptr < list.size();
    }

    /**
     * Restituisce l'elemento successivo nella lista. Questo metodo può essere
     * chiamato ripetutamente per iterare attraverso la lista, o intermezzato
     * con chiamate a previous() per andare avanti e indietro. (Nota che chiamate
     * alternate a next e previous restituiranno lo stesso elemento ripetutamente.)
     * 
     * @return l'elemento successivo nella lista.
     * @throws java.util.NoSuchElementException se l'iterazione non ha un elemento successivo.
     */
    public Object next() 
    {
        if (!hasNext()) 
        {
            throw new java.util.NoSuchElementException("Nessun elemento successivo disponibile.");
        }
        lastReturned = ptr;
        Object nextElement = list.get(ptr);
        ptr++;
        return nextElement;
    }

    /**
     * Restituisce true se questo iteratore di lista ha più elementi quando
     * attraversa la lista in direzione inversa. (In altre parole, restituisce
     * true se previous() restituirebbe un elemento piuttosto che lanciare un'eccezione.)
     * 
     * @return true se l'iteratore di lista ha più elementi quando attraversa
     *         la lista in direzione inversa.
     */
    public boolean hasPrevious() 
    {
        return ptr > 0;      
    }

    /**
     * Restituisce l'elemento precedente nella lista. Questo metodo può essere
     * chiamato ripetutamente per iterare attraverso la lista all'indietro, o
     * intermezzato con chiamate a next() per andare avanti e indietro. (Nota che
     * chiamate alternate a next e previous restituiranno lo stesso elemento ripetutamente.)
     * 
     * @return l'elemento precedente nella lista.
     * @throws java.util.NoSuchElementException se l'iterazione non ha un elemento precedente.
     */
    public Object previous() 
    {
        if (!hasPrevious()) 
        {
            throw new java.util.NoSuchElementException("Nessun elemento precedente disponibile.");
        }
        ptr--;                   // Sposta il cursore indietro
        lastReturned = ptr;      // Aggiorna l'ultimo elemento restituito
        return list.get(ptr);
    }

    /**
     * Restituisce l'indice dell'elemento che verrebbe restituito da una
     * successiva chiamata a next().
     */
    public int nextIndex() 
    {
        if (ptr >= list.size()) 
        {
            return list.size();         // Restituisce la dimensione della lista se alla fine
        }
        return ptr;                  // Restituisce l'indice corrente del cursore
    }

    /**
     * Restituisce l'indice dell'elemento che verrebbe restituito da una
     * successiva chiamata a previous().
     */
    public int previousIndex() 
    {
        if (ptr <= 0) 
        {
            return -1;              // Restituisce -1 se l'iteratore è all'inizio della lista
        }
        return ptr - 1;          // Restituisce l'indice dell'elemento precedente
    }
    
    /**
     * Rimuove dalla collezione sottostante l'ultimo elemento restituito
     * dall'iteratore.
     * @throws IllegalStateException se né next né previous sono stati chiamati,
     *         oppure remove o add sono stati chiamati dopo l'ultima chiamata
     *         a next o previous.
     */
    public void remove() 
    {
        if (lastReturned == -1) 
        {
            throw new IllegalStateException("next() non è stato chiamato o remove() è già stato chiamato dopo l'ultima chiamata a next().");
        }
        list.remove(lastReturned);
        // Aggiorna il cursore e l'ultimo elemento restituito
        if (lastReturned < ptr) 
        {
            ptr--;           // Sposta il cursore indietro se necessario
        }
        lastReturned = -1;      // Resetta l'ultimo elemento restituito
    }

    /**
     * Sostituisce l'ultimo elemento restituito da next() o previous() con
     * l'elemento specificato.
     * @param o l'elemento con cui sostituire l'ultimo elemento restituito da
     *          next o previous.
     * @throws myExceptions.IllegalStateException se né next né previous sono stati chiamati,
     *         oppure remove o add sono stati chiamati dopo l'ultima chiamata
     *         a next o previous.
     */
    public void set(Object o) 
    {
        if (lastReturned == -1) 
        {
            throw new IllegalStateException("next() o previous() non sono stati chiamati, oppure remove() o add() sono stati chiamati dopo l'ultima chiamata a next() o previous().");
        }
        list.set(lastReturned, o);       // Sostituisce l'elemento alla posizione dell'ultimo elemento restituito
    }
    
    /**
     * Inserisce l'elemento specificato nella lista.
     * @param o l'elemento da inserire.
     */
    public void add(Object o) 
    {
        list.add(ptr, o);        // Aggiunge l'elemento alla posizione corrente del cursore
        lastReturned = -1;          // Resetta l'ultimo elemento restituito
        ptr++;                   // Sposta il cursore avanti dopo l'inserimento
    }
}
