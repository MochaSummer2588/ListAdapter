package myAdapter;
public interface HCollection 
{
    void add(int index, Object element);
    void add(Object o);
    void addAll(HCollection c);
    void addAll(int index, HCollection c);

    void clear();
    boolean contains(Object o);
    boolean containsAll(HCollection c);
    boolean equals();

    Object get(int index);
    int hashCode();
    int indexOf(Object o);
    HIterator iterator();


}
