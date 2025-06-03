public interface HListIterator extends HIterator 
{
    /**
     * Returns the index of the element that would be returned by a subsequent call to next().
     *
     * @return the index of the next element
     */
    int nextIndex();

    /**
     * Returns the index of the element that would be returned by a subsequent call to previous().
     *
     * @return the index of the previous element
     */
    int previousIndex();

    /**
     * Returns the previous element in the list.
     *
     * @return the previous element
     */
    Object previous();

    
    
}
