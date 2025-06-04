package myAdapter;

/**
 * Una collezione ordinata (anche conosciuta come sequenza). L'utente di questa
 * interfaccia ha controllo preciso su dove nella lista ogni elemento è inserito.
 * L'utente può accedere agli elementi tramite il loro indice intero (posizione nella lista),
 * e cercare elementi nella lista. A differenza dei set, le liste tipicamente permettono
 * elementi duplicati. Più formalmente, le liste tipicamente permettono coppie di elementi
 * e1 ed e2 tali che e1.equals(e2), e tipicamente permettono elementi null multipli se
 * permettono elementi null del tutto.
 */
public interface HList extends HCollection 
{

    /**
     * Inserisce l'elemento specificato alla posizione specificata in questa lista (operazione opzionale).
     * Sposta l'elemento attualmente a quella posizione (se presente) e qualsiasi elemento
     * successivo a destra (aggiunge uno ai loro indici).
     *
     * @param index indice al quale l'elemento specificato deve essere inserito
     * @param element elemento da inserire
     * @throws UnsupportedOperationException se l'operazione add non è supportata da questa lista
     * @throws ClassCastException se la classe dell'elemento specificato impedisce che sia aggiunto a questa lista
     * @throws NullPointerException se l'elemento specificato è null e questa lista non permette elementi null
     * @throws IllegalArgumentException se qualche proprietà dell'elemento impedisce che sia aggiunto a questa lista
     * @throws IndexOutOfBoundsException se l'indice è fuori intervallo (index < 0 || index > size())
     */
    void add(int index, Object element);

    /**
     * Inserisce tutti gli elementi nella collezione specificata in questa lista alla
     * posizione specificata (operazione opzionale). Sposta l'elemento attualmente a
     * quella posizione (se presente) e qualsiasi elemento successivo a destra (aumenta i loro indici).
     *
     * @param index indice al quale inserire il primo elemento dalla collezione specificata
     * @param c collezione contenente elementi da aggiungere a questa lista
     * @return true se questa lista è cambiata come risultato della chiamata
     * @throws UnsupportedOperationException se l'operazione addAll non è supportata da questa lista
     * @throws ClassCastException se la classe di un elemento della collezione specificata impedisce che sia aggiunto a questa lista
     * @throws NullPointerException se la collezione specificata contiene uno o più elementi null e questa lista non permette elementi null
     * @throws IllegalArgumentException se qualche proprietà di un elemento della collezione specificata impedisce che sia aggiunto a questa lista
     * @throws IndexOutOfBoundsException se l'indice è fuori intervallo (index < 0 || index > size())
     */
    boolean addAll(int index, HCollection c);

    /**
     * Restituisce l'elemento alla posizione specificata in questa lista.
     *
     * @param index indice dell'elemento da restituire
     * @return l'elemento alla posizione specificata in questa lista
     * @throws IndexOutOfBoundsException se l'indice è fuori intervallo (index < 0 || index >= size())
     */
    Object get(int index);

    /**
     * Restituisce l'indice della prima occorrenza dell'elemento specificato in questa lista,
     * o -1 se questa lista non contiene l'elemento. Più formalmente, restituisce l'indice
     * più basso i tale che (o==null ? get(i)==null : o.equals(get(i))), o -1 se non esiste tale indice.
     *
     * @param o elemento da cercare
     * @return l'indice della prima occorrenza dell'elemento specificato in questa lista,
     *         o -1 se questa lista non contiene l'elemento
     * @throws ClassCastException se il tipo dell'elemento specificato è incompatibile con questa lista
     * @throws NullPointerException se l'elemento specificato è null e questa lista non permette elementi null
     */
    int indexOf(Object o);

    /**
     * Restituisce l'indice dell'ultima occorrenza dell'elemento specificato in questa lista,
     * o -1 se questa lista non contiene l'elemento. Più formalmente, restituisce l'indice
     * più alto i tale che (o==null ? get(i)==null : o.equals(get(i))), o -1 se non esiste tale indice.
     *
     * @param o elemento da cercare
     * @return l'indice dell'ultima occorrenza dell'elemento specificato in questa lista,
     *         o -1 se questa lista non contiene l'elemento
     * @throws ClassCastException se il tipo dell'elemento specificato è incompatibile con questa lista
     * @throws NullPointerException se l'elemento specificato è null e questa lista non permette elementi null
     */
    int lastIndexOf(Object o);

    /**
     * Restituisce un iteratore di lista sugli elementi in questa lista (in sequenza corretta).
     *
     * @return un iteratore di lista sugli elementi in questa lista (in sequenza corretta)
     */
    HListIterator listIterator();

    /**
     * Restituisce un iteratore di lista sugli elementi in questa lista (in sequenza corretta),
     * iniziando alla posizione specificata nella lista. L'indice specificato indica il
     * primo elemento che sarebbe restituito da una chiamata iniziale a next.
     *
     * @param index indice del primo elemento da restituire dall'iteratore di lista
     * @return un iteratore di lista sugli elementi in questa lista (in sequenza corretta),
     *         iniziando alla posizione specificata nella lista
     * @throws IndexOutOfBoundsException se l'indice è fuori intervallo (index < 0 || index > size())
     */
    HListIterator listIterator(int index);

    /**
     * Rimuove l'elemento alla posizione specificata in questa lista (operazione opzionale).
     * Sposta qualsiasi elemento successivo a sinistra (sottrae uno dai loro indici).
     * Restituisce l'elemento che è stato rimosso dalla lista.
     *
     * @param index l'indice dell'elemento da rimuovere
     * @return l'elemento che è stato rimosso dalla lista
     * @throws UnsupportedOperationException se l'operazione remove non è supportata da questa lista
     * @throws IndexOutOfBoundsException se l'indice è fuori intervallo (index < 0 || index >= size())
     */
    Object remove(int index);

    /**
     * Sostituisce l'elemento alla posizione specificata in questa lista con l'elemento
     * specificato (operazione opzionale).
     *
     * @param index indice dell'elemento da sostituire
     * @param element elemento da memorizzare alla posizione specificata
     * @return l'elemento precedentemente alla posizione specificata
     * @throws UnsupportedOperationException se l'operazione set non è supportata da questa lista
     * @throws ClassCastException se la classe dell'elemento specificato impedisce che sia aggiunto a questa lista
     * @throws NullPointerException se l'elemento specificato è null e questa lista non permette elementi null
     * @throws IllegalArgumentException se qualche proprietà dell'elemento impedisce che sia aggiunto a questa lista
     * @throws IndexOutOfBoundsException se l'indice è fuori intervallo (index < 0 || index >= size())
     */
    Object set(int index, Object element);

    /**
     * Restituisce una vista della porzione di questa lista tra il fromIndex specificato,
     * incluso, e toIndex, escluso. (Se fromIndex e toIndex sono uguali, la lista restituita
     * è vuota.) La lista restituita è supportata da questa lista, quindi i cambiamenti
     * non strutturali nella lista restituita si riflettono in questa lista, e viceversa.
     *
     * @param fromIndex endpoint basso (incluso) della subList
     * @param toIndex endpoint alto (escluso) della subList
     * @return una vista dell'intervallo specificato all'interno di questa lista
     * @throws IndexOutOfBoundsException per un valore di indice endpoint illegale
     *         (fromIndex < 0 || toIndex > size || fromIndex > toIndex)
     */
    HList subList(int fromIndex, int toIndex);
}