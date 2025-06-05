/**
 * Implementazione dell'adapter per l'interfaccia List utilizzando Vector di CLDC 1.1.
 * Questa classe adatta un Vector per implementare le interfacce HList e HCollection.
 */
package myAdapter;

//===== IMPORTAZIONI =====
import java.util.Vector;

//NB: LA MIA LISTA NON ACCETTA ELEMENTI NULL

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
        // Controlla se l'elemento è null
        if(element == null) 
        {
            throw new NullPointerException("Element cannot be null");
        }

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
        // Controlla se l'elemento è null
        if(o == null) 
        {
            throw new NullPointerException("Element cannot be null");
        }

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
            // Controlla se l'elemento è null
            if (arrayObject[i] == null) 
            {
                throw new NullPointerException("Element in collection cannot be null");
            }
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
        // Implementazione da fornire
        return false;
    }
    
    /**
     * Rimuove tutti gli elementi da questa collezione.
     */
    public void clear() {
        // Implementazione da fornire
    }
    
    /**
     * Restituisce true se questa collezione contiene l'elemento specificato.
     */
    public boolean contains(Object o) {
        // Implementazione da fornire
        return false;
    }
    
    /**
     * Restituisce true se questa collezione contiene tutti gli elementi
     * della collezione specificata.
     */
    public boolean containsAll(HCollection c) {
        // Implementazione da fornire
        return false;
    }
    
    /**
     * Confronta l'oggetto specificato con questa collezione per l'uguaglianza.
     */
    public boolean equals(Object o) {
        // Implementazione da fornire
        return false;
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
    public int hashCode() {
        // Implementazione da fornire
        return 0;
    }

    /**
     * Restituisce l'indice della prima occorrenza dell'elemento specificato
     * in questa lista, o -1 se questa lista non contiene l'elemento.
     */
    public int indexOf(Object o)                    //TESTATO IN MAINPROVA  
    {
        // Controlla se l'elemento è null
        if (o == null) 
        {
            throw new NullPointerException("Element cannot be null");
        }
        return vector.indexOf(o); // Restituisce l'indice della prima occorrenza dell'elemento
    }
    
    /**
     * Restituisce true se questa collezione non contiene elementi.
     */
    public boolean isEmpty() {
        // Implementazione da fornire
        return false;
    }
    
    /**
     * Restituisce un iteratore sugli elementi di questa collezione.
     */
    public HIterator iterator() {
        // Implementazione da fornire
        return null;
    }

    /**
     * Restituisce l'indice dell'ultima occorrenza dell'elemento specificato
     * in questa lista, o -1 se questa lista non contiene l'elemento.
     */
    public int lastIndexOf(Object o) {
        // Implementazione da fornire
        return -1;
    }

    /**
     * Restituisce un iteratore di lista sugli elementi di questa lista
     * (in sequenza appropriata).
     */
    public HListIterator listIterator() {
        // Implementazione da fornire
        return null;
    }
    
    /**
     * Restituisce un iteratore di lista sugli elementi di questa lista
     * (in sequenza appropriata), iniziando dalla posizione specificata nella lista.
     */
    public HListIterator listIterator(int index) {
        // Implementazione da fornire
        return null;
    }

    /**
     * Rimuove l'elemento nella posizione specificata in questa lista.
     */
    public Object remove(int index) {
        // Implementazione da fornire
        return null;
    }
    
    /**
     * Rimuove una singola istanza dell'elemento specificato da questa collezione,
     * se è presente.
     */
    public boolean remove(Object o) {
        // Implementazione da fornire
        return false;
    }
    
    /**
     * Rimuove da questa collezione tutti gli elementi che sono contenuti
     * nella collezione specificata.
     */
    public boolean removeAll(HCollection c) {
        // Implementazione da fornire
        return false;
    }
    
    /**
     * Mantiene solo gli elementi di questa collezione che sono contenuti
     * nella collezione specificata.
     */
    public boolean retainAll(HCollection c) {
        // Implementazione da fornire
        return false;
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
