public class ListIteratorAdapter 
{
    public ListIteratorAdapter(java.util.ListIterator<?> listIterator) {
        this.listIterator = listIterator;
    }

    public boolean hasNext() {
        return listIterator.hasNext();
    }

    public Object next() {
        return listIterator.next();
    }

    public boolean hasPrevious() {
        return listIterator.hasPrevious();
    }

    public Object previous() {
        return listIterator.previous();
    }

    public int nextIndex() {
        return listIterator.nextIndex();
    }

    public int previousIndex() {
        return listIterator.previousIndex();
    }

    public void remove() {
        listIterator.remove();
    }

    public void set(Object e) {
        listIterator.set(e);
    }

    public void add(Object e) {
        listIterator.add(e);
    }
}
