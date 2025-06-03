package myAdapter;
public interface HList extends HCollection
{
    /**
     * Inserts the specified element at the specified position in this list (optional operation).
     *
     * @param index the index at which the specified element is to be inserted
     * @param element the element to be inserted
     */
    void add(int index, Object element);

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index the index of the element to return
     * @return the element at the specified position in this list
     */
    Object get(int index);

    /**
     * Removes the element at the specified position in this list (optional operation).
     *
     * @param index the index of the element to be removed
     * @return the element that was removed from the list
     */
    Object remove(int index);

    /**
     * Replaces the element at the specified position in this list with the specified element (optional operation).
     *
     * @param index the index of the element to replace
     * @param element the element to be stored at the specified position
     */
    void set(int index, Object element);
}