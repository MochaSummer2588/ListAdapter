//Alberto Bortoletto 2101761

package myAdapter;
/**
 * <b>Summary:</b>
 * <p>
 * L'interfaccia {@code HList} rappresenta una collezione ordinata di elementi, nota anche come sequenza.
 * L'utente di questa interfaccia ha un controllo preciso su dove in questa lista viene inserito ogni elemento.
 * Gli elementi possono essere acceduti tramite il loro indice intero (posizione nella lista)
 * e possono essere cercati all'interno della lista.
 * </p>
 * <p>
 * <b>Descrizione Dettagliata:</b>
 * <p>
 * A differenza dei set,
 * le liste tipicamente permettono elementi duplicati. Più formalmente,
 * le liste generalmente consentono coppie di elementi {@code e1} ed {@code e2}
 * tali che {@code e1.equals(e2)}, e tipicamente permettono elementi {@code null} multipli
 * se essi consentono elementi {@code null} del tutto. Non è inconcepibile che qualcuno
 * possa desiderare di implementare una lista che proibisce i duplicati, lanciando
 * eccezioni a runtime quando l'utente tenta di inserirli, ma ci si aspetta che
 * questo utilizzo sia raro.
 * </p>
 * <p>
 * L'interfaccia {@code HList} impone clausole aggiuntive, oltre a quelle specificate
 * nell'interfaccia {@link myAdapter.HCollection}, sui contratti dei metodi {@code iterator()},
 * {@code add()}, {@code remove()}, {@code equals()}, e {@code hashCode()}.
 * Le dichiarazioni per altri metodi ereditati sono incluse qui per comodità.
 * </p>
 * <p>
 * L'interfaccia {@code HList} fornisce quattro metodi per l'accesso posizionale (indicizzato)
 * agli elementi della lista. Le liste (come gli array Java) sono basate su indice zero.
 * Si noti che queste operazioni possono essere eseguite in tempo proporzionale al valore
 * dell'indice per alcune implementazioni (ad esempio, per una {@link myAdapter.ListAdapter}
 * che emulasse una lista collegata). Pertanto, iterare sugli elementi di una lista è
 * generalmente preferibile all'indicizzazione se il chiamante non conosce l'implementazione sottostante.
 * </p>
 * <p>
 * L'interfaccia {@code HList} fornisce un iteratore speciale, chiamato {@link myAdapter.HListIterator},
 * che consente l'inserimento e la sostituzione di elementi, e l'accesso bidirezionale
 * in aggiunta alle normali operazioni che l'interfaccia {@link myAdapter.HIterator} fornisce.
 * Viene fornito un metodo per ottenere un iteratore di lista che inizia in una posizione
 * specificata nella lista.
 * </p>
 * <p>
 * L'interfaccia {@code HList} fornisce due metodi per cercare un oggetto specificato.
 * Dal punto di vista delle prestazioni, questi metodi dovrebbero essere usati con cautela.
 * In molte implementazioni eseguiranno costose ricerche lineari.
 * </p>
 * <p>
 * L'interfaccia {@code HList} fornisce due metodi per inserire e rimuovere efficientemente
 * più elementi in un punto arbitrario della lista.
 * </p>
 * <p>
 * <b>Nota:</b> Sebbene sia consentito che le liste contengano se stesse come elementi,
 * si consiglia estrema cautela: i metodi {@code equals()} e {@code hashCode()}
 * non sono più ben definiti su una lista di questo tipo.
 * </p>
 * <p>
 * Alcune implementazioni di lista hanno restrizioni sugli elementi che possono contenere.
 * Ad esempio, alcune implementazioni proibiscono elementi {@code null}, e alcune
 * hanno restrizioni sui tipi dei loro elementi. Il tentativo di aggiungere un elemento
 * non idoneo lancia un'eccezione non controllata, tipicamente {@link java.lang.NullPointerException}
 * o {@link java.lang.ClassCastException}. Il tentativo di interrogare la presenza
 * di un elemento non idoneo può lanciare un'eccezione, oppure può semplicemente restituire
 * {@code false}; alcune implementazioni mostreranno il primo comportamento e alcune il secondo.
 * Più in generale, il tentativo di un'operazione su un elemento non idoneo la cui
 * conclusione non comporterebbe l'inserimento di un elemento non idoneo nella lista
 * può lanciare un'eccezione o può avere successo, a discrezione dell'implementazione.
 * Tali eccezioni sono contrassegnate come "opzionali" nella specifica per questa interfaccia.
 * </p>
 *
 * @see myAdapter.HCollection
 * @see myAdapter.HIterator
 * @see myAdapter.HListIterator
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
     * @throws myExceptions.UnsupportedOperationException se l'operazione add non è supportata da questa lista
     * @throws ClassCastException se la classe dell'elemento specificato impedisce che sia aggiunto a questa lista
     * @throws NullPointerException se l'elemento specificato è null e questa lista non permette elementi null
     * @throws IllegalArgumentException se qualche proprietà dell'elemento impedisce che sia aggiunto a questa lista
     * @throws IndexOutOfBoundsException se l'indice è fuori intervallo (index &lt; 0 || index &gt; size())
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
     * @throws myExceptions.UnsupportedOperationException se l'operazione addAll non è supportata da questa lista
     * @throws ClassCastException se la classe di un elemento della collezione specificata impedisce che sia aggiunto a questa lista
     * @throws NullPointerException se la collezione specificata contiene uno o più elementi null e questa lista non permette elementi null
     * @throws IllegalArgumentException se qualche proprietà di un elemento della collezione specificata impedisce che sia aggiunto a questa lista
     * @throws IndexOutOfBoundsException se l'indice è fuori intervallo (index &lt; 0 || index &gt; size())
     */
    boolean addAll(int index, HCollection c);

    /**
     * Restituisce l'elemento alla posizione specificata in questa lista.
     *
     * @param index indice dell'elemento da restituire
     * @return l'elemento alla posizione specificata in questa lista
     * @throws IndexOutOfBoundsException se l'indice è fuori intervallo (index &lt; 0 || index &gt;= size())
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
     * @throws IndexOutOfBoundsException se l'indice è fuori intervallo (index &lt; 0 || index &gt; size())
     */
    HListIterator listIterator(int index);

    /**
     * Rimuove l'elemento alla posizione specificata in questa lista (operazione opzionale).
     * Sposta qualsiasi elemento successivo a sinistra (sottrae uno dai loro indici).
     * Restituisce l'elemento che è stato rimosso dalla lista.
     *
     * @param index l'indice dell'elemento da rimuovere
     * @return l'elemento che è stato rimosso dalla lista
     * @throws myExceptions.UnsupportedOperationException se l'operazione remove non è supportata da questa lista
     * @throws IndexOutOfBoundsException se l'indice è fuori intervallo (index &lt; 0 || index &gt;= size())
     */
    Object remove(int index);

    /**
     * Sostituisce l'elemento alla posizione specificata in questa lista con l'elemento
     * specificato (operazione opzionale).
     *
     * @param index indice dell'elemento da sostituire
     * @param element elemento da memorizzare alla posizione specificata
     * @return l'elemento precedentemente alla posizione specificata
     * @throws myExceptions.UnsupportedOperationException se l'operazione set non è supportata da questa lista
     * @throws ClassCastException se la classe dell'elemento specificato impedisce che sia aggiunto a questa lista
     * @throws NullPointerException se l'elemento specificato è null e questa lista non permette elementi null
     * @throws IllegalArgumentException se qualche proprietà dell'elemento impedisce che sia aggiunto a questa lista
     * @throws IndexOutOfBoundsException se l'indice è fuori intervallo (index &lt; 0 || index &gt;= size())
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
     *         (fromIndex &lt; 0 || toIndex &gt; size || fromIndex &gt; toIndex)
     */
    HList subList(int fromIndex, int toIndex);
}