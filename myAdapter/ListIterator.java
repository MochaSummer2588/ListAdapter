package myAdapter;
/**
 * Implementazione dell'adapter per ListIterator utilizzando Vector di CLDC 1.1.
 * Questa classe implementa sia HIterator che HListIterator.
 */

import myExceptions.IllegalStateException;

public class ListIterator implements HListIterator
{

    //===== VARIABILI DI ISTANZA =====
    
    private ListAdapter list;       // Riferimento alla lista sottostante
    private int cursor;             // Indice del cursore corrente (posizione dell'elemento successivo)
    private int lastReturned;       // Indice dell'ultimo elemento restituito (-1 se nessuno)
    
    /**
     * Costruttore che crea un ListIteratorAdapter per la lista specificata.
     * 
     * @param list la lista da iterare
     */
    public ListIterator(ListAdapter list) 
    {
        this.list = list;
        this.cursor = 0;            // Inizia all'inizio della lista
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
        this.cursor = index;        // Inizia alla posizione specificata
        this.lastReturned = -1;     // Nessun elemento restituito ancora
    }

    // ===== METODI ListIterator =====

    /**
     * Restituisce true se l'iterazione ha più elementi.
     */
    public boolean hasNext() 
    {
        return cursor < list.size();
    }

    /**
     * Restituisce l'elemento successivo nell'iterazione.
     */
    public Object next() 
    {
        if (!hasNext()) 
        {
            throw new java.util.NoSuchElementException("Nessun elemento successivo disponibile.");
        }
        lastReturned = cursor;
        Object nextElement = list.get(cursor);
        cursor++;
        return nextElement;
    }

    /**
     * Restituisce true se questo iteratore di lista ha più elementi quando
     * attraversa la lista in direzione inversa.
     */
    public boolean hasPrevious() 
    {
        return cursor > 0;      // Controlla se il cursore è oltre l'inizio della lista
    }

    /**
     * Restituisce l'elemento precedente nella lista.
     */
    public Object previous() 
    {
        if (!hasPrevious()) 
        {
            throw new java.util.NoSuchElementException("Nessun elemento precedente disponibile.");
        }
        cursor--;                   // Sposta il cursore indietro
        lastReturned = cursor;      // Aggiorna l'ultimo elemento restituito
        return list.get(cursor);
    }

    /**
     * Restituisce l'indice dell'elemento che verrebbe restituito da una
     * successiva chiamata a next().
     */
    public int nextIndex() 
    {
        if (cursor >= list.size()) 
        {
            return list.size();         // Restituisce la dimensione della lista se alla fine
        }
        return cursor;                  // Restituisce l'indice corrente del cursore
    }

    /**
     * Restituisce l'indice dell'elemento che verrebbe restituito da una
     * successiva chiamata a previous().
     */
    public int previousIndex() 
    {
        if (cursor <= 0) 
        {
            return -1;              // Restituisce -1 se l'iteratore è all'inizio della lista
        }
        return cursor - 1;          // Restituisce l'indice dell'elemento precedente
    }
    
    /**
     * Rimuove dalla collezione sottostante l'ultimo elemento restituito
     * dall'iteratore.
     */
    public void remove() 
    {
        if (lastReturned == -1) 
        {
            throw new IllegalStateException("next() non è stato chiamato o remove() è già stato chiamato dopo l'ultima chiamata a next().");
        }
        list.remove(lastReturned);
        // Aggiorna il cursore e l'ultimo elemento restituito
        if (lastReturned < cursor) 
        {
            cursor--;           // Sposta il cursore indietro se necessario
        }
        lastReturned = -1;      // Resetta l'ultimo elemento restituito
    }

    /**
     * Sostituisce l'ultimo elemento restituito da next() o previous() con
     * l'elemento specificato.
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
     */
    public void add(Object o) 
    {
        list.add(cursor, o);        // Aggiunge l'elemento alla posizione corrente del cursore
        lastReturned = -1;          // Resetta l'ultimo elemento restituito
        cursor++;                   // Sposta il cursore avanti dopo l'inserimento
    }
}
