/**
 * Implementazione dell'adapter per l'interfaccia List utilizzando Vector di CLDC 1.1.
 * Questa classe adatta un Vector per implementare le interfacce HList e HCollection.
 */
package myAdapter;

import java.util.Vector;

public class ListAdapter implements HList
{
   // Adaptee - il Vector di CLDC 1.1
    private Vector vector;
    
    /**
     * Costruttore di default che crea un ListAdapter vuoto.
     */
    public ListAdapter() {
        // Implementazione da fornire
    }
    
    /**
     * Costruttore che crea un ListAdapter contenente gli elementi
     * della collezione specificata.
     * 
     * @param c la collezione i cui elementi devono essere inseriti in questa lista
     */
    public ListAdapter(HCollection c) {
        // Implementazione da fornire
    }
    
    // ===== Metodi da HCollection =====
    
    /**
     * Assicura che questa collezione contenga l'elemento specificato.
     */
    public boolean add(Object o) {
        // Implementazione da fornire
        return false;
    }
    
    /**
     * Aggiunge tutti gli elementi della collezione specificata a questa collezione.
     */
    public boolean addAll(HCollection c) {
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
     * Restituisce il valore hash code per questa collezione.
     */
    public int hashCode() {
        // Implementazione da fornire
        return 0;
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
     * Restituisce il numero di elementi in questa collezione.
     */
    public int size() {
        // Implementazione da fornire
        return 0;
    }
    
    /**
     * Restituisce un array contenente tutti gli elementi di questa collezione.
     */
    public Object[] toArray() {
        // Implementazione da fornire
        return null;
    }
    
    /**
     * Restituisce un array contenente tutti gli elementi di questa collezione;
     * il tipo runtime dell'array restituito è quello dell'array specificato.
     */
    public Object[] toArray(Object[] a) {
        // Implementazione da fornire
        return null;
    }
    
    // ===== Metodi da HList =====
    
    /**
     * Inserisce l'elemento specificato nella posizione specificata in questa lista.
     */
    public void add(int index, Object element) {
        // Implementazione da fornire
    }
    
    /**
     * Inserisce tutti gli elementi della collezione specificata in questa lista,
     * iniziando dalla posizione specificata.
     */
    public boolean addAll(int index, HCollection c) {
        // Implementazione da fornire
        return false;
    }
    
    /**
     * Restituisce l'elemento nella posizione specificata in questa lista.
     */
    public Object get(int index) {
        // Implementazione da fornire
        return null;
    }
    
    /**
     * Restituisce l'indice della prima occorrenza dell'elemento specificato
     * in questa lista, o -1 se questa lista non contiene l'elemento.
     */
    public int indexOf(Object o) {
        // Implementazione da fornire
        return -1;
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
     * Sostituisce l'elemento nella posizione specificata in questa lista
     * con l'elemento specificato.
     */
    public Object set(int index, Object element) {
        // Implementazione da fornire
        return null;
    }
    
    /**
     * Restituisce una vista della porzione di questa lista tra gli indici
     * specificati fromIndex, inclusive, e toIndex, esclusivo.
     */
    public HList subList(int fromIndex, int toIndex) {
        // Implementazione da fornire
        return null;
    }
}
