package myAdapter;


public interface HCollection 
{
    /**
     * Ensures that this collection contains the specified element (optional operation).
     *
     * @param o the element to be added
     */
    boolean add(Object o);

    /**
     * Adds all of the elements in the specified collection to this collection (optional operation).
     * @param c collection containing elements to be added to this collection
     * @return true if this collection changed as a result of the call
     */
    boolean addAll(HCollection c);

    /**
     * Removes all of the elements from this collection (optional operation).
     */
    void clear();

    /**
     * Checks if this collection contains the specified element.
     *
     * @param o the element to be checked
     * @return true if this collection contains the specified element
     */
    boolean contains(Object o);

    /**
     * Checks if this collection contains all of the elements in the specified collection.
     *
     * @param c the collection to be checked
     * @return true if this collection contains all of the elements in the specified collection
     */
    boolean containsAll(HCollection c);

    /**
     * Compares the specified object with this collection for equality.
     *
     * @param o the object to be compared for equality with this collection
     * @return true if the specified object is equal to this collection
     */
    boolean equals(Object o);

    /**
     * Returns the hash code value for this collection.
     *
     * @return the hash code value for this collection
     */
    int hashCode();

    /**
     * Returns true if this collection contains no elements.
     * @return true if this collection contains no elements
     */
    boolean isEmpty();

    /**
     * Returns an iterator over the elements in this collection.
     *
     * @return an iterator over the elements in this collection
     */
    HIterator iterator();

    /**
     * Removes a single instance of the specified element from this collection, if it is present (optional operation).
     *
     * @param o the element to be removed
     * @return true if this collection changed as a result of the call
     */
    boolean remove(Object o);

    /**
     * Removes all this collection's elements that are also contained in the specified collection (optional operation).
     *
     * @param c the collection containing elements to be removed
     * @return true if this collection changed as a result of the call
     */
    boolean removeAll(HCollection c);

    /**
     * Retains only the elements in this collection that are contained in the specified collection (optional operation).
     *
     * @param c the collection containing elements to be retained
     * @return true if this collection changed as a result of the call
     */
    boolean retainAll(HCollection c);

    /**
     * Returns the number of elements in this collection.
     *
     * @return the number of elements in this collection
     */
    int size();

    /**
     * Returns an array containing all of the elements in this collection.
     *
     * @return an array containing all of the elements in this collection
     */
    Object[] toArray();

    /**
     * Returns an array containing all of the elements in this collection; the runtime type of the returned array is that of the specified array.
     *
     * @param a the array into which the elements of this collection are to be stored, if it is large enough
     * @return an array containing all of the elements in this collection
     */
    Object[] toArray(Object[] a);

}
