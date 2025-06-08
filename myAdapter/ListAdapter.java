/**
 * Implementazione dell'adapter per l'interfaccia List utilizzando Vector di CLDC 1.1.
 * Questa classe adatta un Vector per implementare le interfacce HList e HCollection.
 */
package myAdapter;

//===== IMPORTAZIONI =====
import java.util.Vector;

//NB: LA MIA LISTA ACCETTA ELEMENTI NULL

public class ListAdapter implements HList
{
    //===== VARIABILI DI ISTANZA =====
   // Adaptee - il Vector di CLDC 1.1

    private Vector vector;

    //===== COSTRUTTORI DISCENDENTI DA QUELLI DI VECTOR =====

    /**
     * Costruttore di default che crea un ListAdapter vuoto.
     */
    public ListAdapter() 
    {
        this.vector = new Vector();
    }

    /**
     * Costruttore che crea un ListAdapter con una capacità iniziale specificata.
     * 
     * @param initialCapacity la capacità iniziale della lista
     * @throws IllegalArgumentException se la capacità iniziale è negativa
     */
    public ListAdapter(int initialCapacity) 
    {
        this.vector = new Vector(initialCapacity);
    }

    /**
     * Costruttore che crea un ListAdapter con una capacità iniziale specificata.
     * 
     * @param initialCapacity la capacità iniziale della lista
     * @param capacityIncrement l'incremento di capacità della lista
     * @throws IllegalArgumentException se la capacità iniziale è negativa
     */
    public ListAdapter(int initialCapacity, int capacityIncrement) 
    {
        this.vector = new Vector(initialCapacity, capacityIncrement);
    }

    // =============== METODI LISTADAPTER ===============
    
    /**
     * Inserisce l'elemento specificato nella posizione specificata in questa lista.
     */
    public void add(int index, Object element)                  //TESTATO IN MAINPROVA
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
     * Aggiunge l'elemento specificato alla fine di questa lista.
     */
    public boolean add(Object o)                            //TESTATO IN MAINPROVA
    {
        // Aggiunge l'elemento alla fine della lista
        vector.addElement(o);
        return true;
    }
    
    /**
     * Aggiunge tutti gli elementi della collezione specificata a questa collezione.
     */
    public boolean addAll(HCollection c)                            //TESTATO IN MAINPROVA
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
            vector.addElement(arrayObject[i]);
        }

        return true;
    }

    /**
     * Inserisce tutti gli elementi della collezione specificata in questa lista,
     * iniziando dalla posizione specificata.
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
            vector.insertElementAt(arrayObject[i], index + i);
        }

        return true;
    }
    
    /**
     * Rimuove tutti gli elementi da questa collezione.
     */
    public void clear() 
    {
        vector.removeAllElements();             // Rimuove tutti gli elementi dal Vector    
    }
    
    /**
     * Restituisce true se questa collezione contiene l'elemento specificato.
     */
    public boolean contains(Object o) 
    {
        return vector.contains(o);              // Restituisce true se l'elemento è presente nel Vector
    }
    
    /**
     * Restituisce true se questa collezione contiene tutti gli elementi
     * della collezione specificata.
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
            if (!vector.contains(arrayObject[i])) 
            {
                return false; // Se un elemento non è presente, restituisce false
            }
        }
        
        return true; // Tutti gli elementi sono presenti
    }
    
    /**
     * Confronta l'oggetto specificato con questa collezione per l'uguaglianza.
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
            Object thisElement = this.get(i);        // Elemento alla posizione i
            Object otherElement = other.get(i);      // Elemento alla posizione i
            
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
     * Restituisce l'elemento nella posizione specificata in questa lista.
     */
    public Object get(int index)                                    //TESTATO IN MAINPROVA
    {
        // Controlla se l'indice è valido
        if (index < 0 || index >= vector.size()) 
        {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        
        // Restituisce l'elemento alla posizione specificata
        {
            return vector.elementAt(index);
        }
    }
    
    /**
     * Restituisce il valore hash code per questa collezione.
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
     * Restituisce l'indice della prima occorrenza dell'elemento specificato
     * in questa lista, o -1 se questa lista non contiene l'elemento.
     */
    public int indexOf(Object o)                    //TESTATO IN MAINPROVA  
    {

        return vector.indexOf(o); // Restituisce l'indice della prima occorrenza dell'elemento
    }
    
    /**
     * Restituisce true se questa collezione non contiene elementi.
     */
    public boolean isEmpty() 
    {
        return vector.isEmpty(); // Restituisce true se il Vector è vuoto
    }
    
    /**
     * Restituisce un iteratore sugli elementi di questa collezione.
     */
    public HIterator iterator() 
    {
        return new ListIterator(this);
    }

    /**
     * Restituisce l'indice dell'ultima occorrenza dell'elemento specificato
     * in questa lista, o -1 se questa lista non contiene l'elemento.
     */
    public int lastIndexOf(Object o) 
    {
        return vector.lastIndexOf(o);                           // Restituisce l'indice dell'ultima occorrenza dell'elemento
    }

    /**
     * Restituisce un iteratore di lista sugli elementi di questa lista
     * (in sequenza appropriata).
     */
    public HListIterator listIterator() 
    {
        return new ListIterator(this);                          // Restituisce un nuovo ListIterator per questa lista
    }
    
    /**
     * Restituisce un iteratore di lista sugli elementi di questa lista
     * (in sequenza appropriata), iniziando dalla posizione specificata nella lista.
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
     * Rimuove l'elemento nella posizione specificata in questa lista.
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
     * Rimuove una singola istanza dell'elemento specificato da questa collezione,
     * se è presente.
     */
    public boolean remove(Object o) 
    {
        boolean removed = vector.removeElement(o);                  // Rimuove l'elemento specificato dalla collezione
        return removed;                                             // Restituisce true se l'elemento è stato rimosso, false altrimenti
    }
    
    /**
     * Rimuove da questa collezione tutti gli elementi che sono contenuti
     * nella collezione specificata.
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
            while (vector.removeElement(arrayObject[i])) 
            {
                modified = true;
            }
        }

        return modified;
    }
    
    /**
     * Mantiene solo gli elementi di questa collezione che sono contenuti
     * nella collezione specificata.
     */
    public boolean retainAll(HCollection c) {
        if (c == null) 
        {
            throw new NullPointerException("Collection cannot be null");
        }

        boolean modified = false;
        
        // Scorre dal fondo per evitare problemi con gli indici che cambiano
        for (int i = vector.size() - 1; i >= 0; i--) 
        {
            Object element = vector.elementAt(i);
            
            // Se l'elemento NON è contenuto nella collezione c, lo rimuove
            if (!c.contains(element)) 
            {
                vector.removeElementAt(i);
                modified = true;
            }
        }
        
        return modified;
    }

    /**
     * Sostituisce l'elemento nella posizione specificata in questa lista
     * con l'elemento specificato.
     */
    public Object set(int index, Object element) {
        // Implementazione da fornire
        return null;
    }
    
    /**
     * Restituisce il numero di elementi in questa collezione.
     */
    public int size() 
    {
        return vector.size();
    }

    /**
     * Restituisce una vista della porzione di questa lista tra gli indici
     * specificati fromIndex, inclusive, e toIndex, esclusivo.
     */
    public HList subList(int fromIndex, int toIndex) {
        // Implementazione da fornire
        return null;
    }
    
    /**
     * Restituisce un array contenente tutti gli elementi di questa collezione.
     */
    public Object[] toArray()                           //TESTATO IN MAINPROVA
    {
        Object[] array = new Object[vector.size()];
        for (int i = 0; i < vector.size(); i++) 
        {
            array[i] = vector.elementAt(i);
        }
        return array;
    }
    
    /**
     * Restituisce un array contenente tutti gli elementi di questa collezione;
     * il tipo runtime dell'array restituito è quello dell'array specificato.
     */
    public Object[] toArray(Object[] a) {
        // Implementazione da fornire
        return null;
    }
}
