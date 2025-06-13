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
     * {@inheritDoc}
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
     */
    public boolean add(Object o)                            
    {
        // Aggiunge l'elemento alla fine della lista
        vector.addElement(o);
        return true;
    }
    
    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
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
     */
    public void clear() 
    {
        vector.removeAllElements();             // Rimuove tutti gli elementi dal Vector    
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean contains(Object o) 
    {
        return vector.contains(o);              // Restituisce true se l'elemento è presente nel Vector
    }
    
    /**
     * {@inheritDoc}
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
     */
    public int indexOf(Object o)                      
    {
        return vector.indexOf(o); // Restituisce l'indice della prima occorrenza dell'elemento
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean isEmpty() 
    {
        return vector.isEmpty(); // Restituisce true se il Vector è vuoto
    }
    
    /**
     * {@inheritDoc}
     */
    public HIterator iterator() 
    {
        return new ListIterator(this);
    }

    /**
     *{@inheritDoc}
     */
    public int lastIndexOf(Object o) 
    {
        return vector.lastIndexOf(o);                           // Restituisce l'indice dell'ultima occorrenza dell'elemento
    }

    /**
     * {@inheritDoc}
     */
    public HListIterator listIterator() 
    {
        return new ListIterator(this);                          // Restituisce un nuovo ListIterator per questa lista
    }
    
    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
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
     */
    public boolean remove(Object o) 
    {
        boolean removed = vector.removeElement(o);                  // Rimuove l'elemento specificato dalla collezione
        return removed;                                             // Restituisce true se l'elemento è stato rimosso, false altrimenti
    }
    
    /**
     * {@inheritDoc}
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
     */
    public int size() 
    {
        return vector.size();
    }

    /**
     * {@inheritDoc}
     */
    public HList subList(int fromIndex, int toIndex) 
    {
        // Controlla se gli indici sono validi
        if (fromIndex < 0 || toIndex > this.size() || fromIndex > toIndex) 
        {
            throw new IndexOutOfBoundsException("Invalid subList indices: " + fromIndex + ", " + toIndex);
        }

        // Crea una nuova ListAdapter per la sottolista
        ListAdapter subList = new ListAdapter();
        for (int i = fromIndex; i < toIndex; i++) 
        {
            subList.add(this.get(i)); // Aggiunge gli elementi della sottolista
        }
        return subList;
    }
    
    /**
     * {@inheritDoc}
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
}
