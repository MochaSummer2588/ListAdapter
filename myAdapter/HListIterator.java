package myAdapter;
/**
 * Un iteratore per liste che permette al programmatore di attraversare la lista
 * in entrambe le direzioni, modificare la lista durante l'iterazione e ottenere
 * la posizione corrente dell'iteratore nella lista. Un ListIterator non ha
 * elemento corrente; la sua posizione del cursore si trova sempre tra l'elemento
 * che verrebbe restituito da una chiamata a previous() e l'elemento che verrebbe
 * restituito da una chiamata a next(). In una lista di lunghezza n, ci sono n+1
 * posizioni valide per l'indice, da 0 a n, inclusive.
 * 
 * Questa interfaccia è compatibile con Java 2 Collections Framework versione 1.4.2
 * ed è progettata per funzionare in ambiente CLDC 1.1.
 */
public interface HListIterator extends HIterator 
{
    
    /**
     * Restituisce true se questo iteratore di lista ha più elementi quando
     * attraversa la lista in direzione avanti. (In altre parole, restituisce
     * true se next() restituirebbe un elemento piuttosto che lanciare un'eccezione.)
     * 
     * @return true se l'iteratore di lista ha più elementi quando attraversa
     *         la lista in direzione avanti.
     */
    boolean hasNext();
    
    /**
     * Restituisce l'elemento successivo nella lista. Questo metodo può essere
     * chiamato ripetutamente per iterare attraverso la lista, o intermezzato
     * con chiamate a previous() per andare avanti e indietro. (Nota che chiamate
     * alternate a next e previous restituiranno lo stesso elemento ripetutamente.)
     * 
     * @return l'elemento successivo nella lista.
     * @throws java.util.NoSuchElementException se l'iterazione non ha un elemento successivo.
     */
    Object next();
    
    /**
     * Restituisce true se questo iteratore di lista ha più elementi quando
     * attraversa la lista in direzione inversa. (In altre parole, restituisce
     * true se previous() restituirebbe un elemento piuttosto che lanciare un'eccezione.)
     * 
     * @return true se l'iteratore di lista ha più elementi quando attraversa
     *         la lista in direzione inversa.
     */
    boolean hasPrevious();
    
    /**
     * Restituisce l'elemento precedente nella lista. Questo metodo può essere
     * chiamato ripetutamente per iterare attraverso la lista all'indietro, o
     * intermezzato con chiamate a next() per andare avanti e indietro. (Nota che
     * chiamate alternate a next e previous restituiranno lo stesso elemento ripetutamente.)
     * 
     * @return l'elemento precedente nella lista.
     * @throws java.util.NoSuchElementException se l'iterazione non ha un elemento precedente.
     */
    Object previous();
    
    /**
     * Restituisce l'indice dell'elemento che verrebbe restituito da una
     * successiva chiamata a next(). (Restituisce la dimensione della lista se
     * l'iteratore di lista è alla fine della lista.)
     * 
     * @return l'indice dell'elemento che verrebbe restituito da una successiva
     *         chiamata a next, o la dimensione della lista se l'iteratore è
     *         alla fine della lista.
     */
    int nextIndex();
    
    /**
     * Restituisce l'indice dell'elemento che verrebbe restituito da una
     * successiva chiamata a previous(). (Restituisce -1 se l'iteratore di lista
     * è all'inizio della lista.)
     * 
     * @return l'indice dell'elemento che verrebbe restituito da una successiva
     *         chiamata a previous, o -1 se l'iteratore è all'inizio della lista.
     */
    int previousIndex();
    
    /**
     * Rimuove dalla lista l'ultimo elemento che è stato restituito da next()
     * o previous() (operazione opzionale). Questa chiamata può essere fatta
     * solo una volta per chiamata a next o previous. Può essere fatta solo se
     * add(Object) non è stato chiamato dopo l'ultima chiamata a next o previous.
     * 
     * @throws UnsupportedOperationException se l'operazione remove non è
     *         supportata da questo iteratore di lista.
     * @throws myExceptions.IllegalStateException se né next né previous sono stati chiamati,
     *         oppure remove o add sono stati chiamati dopo l'ultima chiamata
     *         a next o previous.
     */
    void remove();
    
    /**
     * Sostituisce l'ultimo elemento restituito da next() o previous() con
     * l'elemento specificato (operazione opzionale). Questa chiamata può essere
     * fatta solo se né remove() né add(Object) sono stati chiamati dopo
     * l'ultima chiamata a next o previous.
     * 
     * @param o l'elemento con cui sostituire l'ultimo elemento restituito da
     *          next o previous.
     * @throws UnsupportedOperationException se l'operazione set non è supportata
     *         da questo iteratore di lista.
     * @throws ClassCastException se la classe dell'elemento specificato impedisce
     *         che sia aggiunto a questa lista.
     * @throws IllegalArgumentException se qualche proprietà di questo elemento
     *         impedisce che sia aggiunto a questa lista.
     * @throws myExceptions.IllegalStateException se né next né previous sono stati chiamati,
     *         oppure remove o add sono stati chiamati dopo l'ultima chiamata
     *         a next o previous.
     */
    void set(Object o);
    
    /**
     * Inserisce l'elemento specificato nella lista (operazione opzionale).
     * L'elemento è inserito immediatamente prima dell'elemento che verrebbe
     * restituito da next(), se ce n'è uno, e dopo l'elemento che verrebbe
     * restituito da previous(), se ce n'è uno. (Se la lista è vuota, il nuovo
     * elemento diventa l'unico elemento della lista.) Il nuovo elemento è
     * inserito prima del cursore implicito: una successiva chiamata a next()
     * non sarà influenzata, e una successiva chiamata a previous() restituirà
     * il nuovo elemento. (Questa chiamata aumenta di uno il valore che verrebbe
     * restituito da una chiamata a nextIndex o previousIndex.)
     * 
     * @param o l'elemento da inserire.
     * @throws UnsupportedOperationException se l'operazione add non è supportata
     *         da questo iteratore di lista.
     * @throws ClassCastException se la classe dell'elemento specificato impedisce
     *         che sia aggiunto a questa lista.
     * @throws IllegalArgumentException se qualche proprietà di questo elemento
     *         impedisce che sia aggiunto a questa lista.
     */
    void add(Object o);
}