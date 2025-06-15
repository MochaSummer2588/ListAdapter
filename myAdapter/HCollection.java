package myAdapter;

/**
 * La radice della gerarchia delle collezioni. Una collezione rappresenta un gruppo di oggetti,
 * noti come i suoi elementi. Alcune collezioni consentono elementi duplicati e altre no.
 * Alcune sono ordinate e altre no. Il JDK non fornisce implementazioni dirette di questa
 * interfaccia: fornisce implementazioni di sotto-interfacce più specifiche come Set e List.
 * Questa interfaccia viene tipicamente utilizzata per passare collezioni e manipolarle
 * dove è desiderabile la massima generalità.
 */
public interface HCollection 
{

    /**
     * Assicura che questa collezione contenga l'elemento specificato (operazione opzionale).
     * Restituisce true se questa collezione è cambiata come risultato della chiamata.
     * Le collezioni che supportano questa operazione possono porre limitazioni su quali
     * elementi possono essere aggiunti a questa collezione.
     *
     * @param o elemento di cui deve essere assicurata la presenza in questa collezione
     * @return true se questa collezione è cambiata come risultato della chiamata
     * @throws myExceptions.UnsupportedOperationException se l'operazione add non è supportata da questa collezione
     * @throws ClassCastException se la classe dell'elemento specificato impedisce che sia aggiunto a questa collezione
     * @throws NullPointerException se l'elemento specificato è null e questa collezione non permette elementi null
     * @throws IllegalArgumentException se qualche proprietà dell'elemento impedisce che sia aggiunto a questa collezione
     */
    boolean add(Object o);

    /**
     * Aggiunge tutti gli elementi nella collezione specificata a questa collezione (operazione opzionale).
     * Il comportamento di questa operazione è indefinito se la collezione specificata viene modificata
     * mentre l'operazione è in corso.
     *
     * @param c collezione contenente elementi da aggiungere a questa collezione
     * @return true se questa collezione è cambiata come risultato della chiamata
     * @throws myExceptions.UnsupportedOperationException se l'operazione addAll non è supportata da questa collezione
     * @throws ClassCastException se la classe di un elemento della collezione specificata impedisce che sia aggiunto a questa collezione
     * @throws NullPointerException se la collezione specificata contiene uno o più elementi null e questa collezione non permette elementi null
     * @throws IllegalArgumentException se qualche proprietà di un elemento della collezione specificata impedisce che sia aggiunto a questa collezione
     */
    boolean addAll(HCollection c);

    /**
     * Rimuove tutti gli elementi da questa collezione (operazione opzionale).
     * La collezione sarà vuota dopo che questo metodo ritorna.
     *
     * @throws myExceptions.UnsupportedOperationException se l'operazione clear non è supportata da questa collezione
     */
    void clear();

    /**
     * Restituisce true se questa collezione contiene l'elemento specificato.
     * Più formalmente, restituisce true se e solo se questa collezione contiene
     * almeno un elemento e tale che (o==null ? e==null : o.equals(e)).
     *
     * @param o elemento di cui verificare la presenza in questa collezione
     * @return true se questa collezione contiene l'elemento specificato
     * @throws ClassCastException se il tipo dell'elemento specificato è incompatibile con questa collezione
     * @throws NullPointerException se l'elemento specificato è null e questa collezione non permette elementi null
     */
    boolean contains(Object o);

    /**
     * Restituisce true se questa collezione contiene tutti gli elementi della collezione specificata.
     *
     * @param c collezione da verificare per il contenimento in questa collezione
     * @return true se questa collezione contiene tutti gli elementi della collezione specificata
     * @throws ClassCastException se i tipi di uno o più elementi nella collezione specificata sono incompatibili con questa collezione
     * @throws NullPointerException se la collezione specificata contiene uno o più elementi null e questa collezione non permette elementi null
     */
    boolean containsAll(HCollection c);

    /**
     * Confronta l'oggetto specificato con questa collezione per l'uguaglianza.
     * Mentre l'interfaccia Collection non aggiunge stipulazioni al contratto generale
     * per Object.equals, i programmatori che implementano l'interfaccia Collection
     * "direttamente" devono fare attenzione se scelgono di sovrascrivere Object.equals.
     *
     * @param o oggetto da confrontare per l'uguaglianza con questa collezione
     * @return true se l'oggetto specificato è uguale a questa collezione
     */
    boolean equals(Object o);

    /**
     * Restituisce il valore del codice hash per questa collezione. Mentre l'interfaccia
     * Collection non aggiunge stipulazioni al contratto generale per Object.hashCode,
     * i programmatori devono notare che qualsiasi classe che sovrascrive il metodo
     * Object.equals deve anche sovrascrivere il metodo Object.hashCode per soddisfare
     * il contratto generale del metodo Object.hashCode.
     *
     * @return il valore del codice hash per questa collezione
     */
    int hashCode();

    /**
     * Restituisce true se questa collezione non contiene elementi.
     *
     * @return true se questa collezione non contiene elementi
     */
    boolean isEmpty();

    /**
     * Restituisce un iteratore sugli elementi in questa collezione. Non ci sono
     * garanzie riguardo all'ordine in cui gli elementi sono restituiti (a meno che
     * questa collezione sia un'istanza di qualche classe che fornisce una garanzia).
     *
     * @return un HIterator sugli elementi in questa collezione
     */
    HIterator iterator();

    /**
     * Rimuove una singola istanza dell'elemento specificato da questa collezione,
     * se è presente (operazione opzionale). Più formalmente, rimuove un elemento e
     * tale che (o==null ? e==null : o.equals(e)), se questa collezione contiene
     * uno o più di tali elementi.
     *
     * @param o elemento da rimuovere da questa collezione, se presente
     * @return true se un elemento è stato rimosso come risultato di questa chiamata
     * @throws ClassCastException se il tipo dell'elemento specificato è incompatibile con questa collezione
     * @throws NullPointerException se l'elemento specificato è null e questa collezione non permette elementi null
     * @throws myExceptions.UnsupportedOperationException se l'operazione remove non è supportata da questa collezione
     */
    boolean remove(Object o);

    /**
     * Rimuove tutti gli elementi di questa collezione che sono anche contenuti
     * nella collezione specificata (operazione opzionale). Dopo questa chiamata
     * ritorna, questa collezione non conterrà elementi in comune con la collezione specificata.
     *
     * @param c collezione contenente elementi da rimuovere da questa collezione
     * @return true se questa collezione è cambiata come risultato della chiamata
     * @throws myExceptions.UnsupportedOperationException se l'operazione removeAll non è supportata da questa collezione
     * @throws ClassCastException se i tipi di uno o più elementi in questa collezione sono incompatibili con la collezione specificata
     * @throws NullPointerException se questa collezione contiene uno o più elementi null e la collezione specificata non supporta elementi null
     */
    boolean removeAll(HCollection c);

    /**
     * Mantiene solo gli elementi in questa collezione che sono contenuti nella
     * collezione specificata (operazione opzionale). In altre parole, rimuove da
     * questa collezione tutti i suoi elementi che non sono contenuti nella collezione specificata.
     *
     * @param c collezione contenente elementi da mantenere in questa collezione
     * @return true se questa collezione è cambiata come risultato della chiamata
     * @throws myExceptions.UnsupportedOperationException se l'operazione retainAll non è supportata da questa collezione
     * @throws ClassCastException se i tipi di uno o più elementi in questa collezione sono incompatibili con la collezione specificata
     * @throws NullPointerException se questa collezione contiene uno o più elementi null e la collezione specificata non supporta elementi null
     */
    boolean retainAll(HCollection c);

    /**
     * Restituisce il numero di elementi in questa collezione. Se questa collezione
     * contiene più di Integer.MAX_VALUE elementi, restituisce Integer.MAX_VALUE.
     *
     * @return il numero di elementi in questa collezione
     */
    int size();

    /**
     * Restituisce un array contenente tutti gli elementi in questa collezione.
     * Se questa collezione fornisce garanzie sull'ordine in cui i suoi elementi
     * sono restituiti dal suo iteratore, questo metodo deve restituire gli elementi
     * nello stesso ordine. L'array restituito sarà "sicuro" in quanto nessun riferimento
     * ad esso è mantenuto da questa collezione.
     *
     * @return un array contenente tutti gli elementi in questa collezione
     */
    Object[] toArray();

    /**
     * Restituisce un array contenente tutti gli elementi in questa collezione;
     * il tipo runtime dell'array restituito è quello dell'array specificato.
     * Se la collezione si adatta nell'array specificato, vi viene restituita.
     * Altrimenti, viene allocato un nuovo array con il tipo runtime dell'array
     * specificato e la dimensione di questa collezione.
     *
     * @param a l'array in cui gli elementi della collezione devono essere memorizzati, se è abbastanza grande
     * @return un array contenente tutti gli elementi in questa collezione
     * @throws ArrayStoreException se il tipo runtime dell'array specificato non è un supertipo del tipo runtime di ogni elemento in questa collezione
     * @throws NullPointerException se l'array specificato è null
     */
    Object[] toArray(Object[] a);
}