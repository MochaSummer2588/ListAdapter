//Alberto Bortoletto 2101761

package myAdapter;

/**
 * Un iteratore su una collezione. Iterator prende il posto di Enumeration nel
 * framework delle collezioni Java. Gli iteratori differiscono dalle enumerazioni in due modi:
 * <ul>
 * <li>Gli iteratori permettono al chiamante di rimuovere elementi dalla collezione
 *     sottostante durante l'iterazione con semantica ben definita.
 * <li>I nomi dei metodi sono stati migliorati.
 * </ul>
 * 
 * Questa interfaccia è compatibile con Java 2 Collections Framework versione 1.4.2
 * ed è progettata per funzionare in ambiente CLDC 1.1.
 */
public interface HIterator 
{
    
    /**
     * Restituisce true se l'iterazione ha più elementi. (In altre parole,
     * restituisce true se next restituirebbe un elemento piuttosto che
     * lanciare un'eccezione.)
     * 
     * @return true se l'iteratore ha più elementi.
     */
    boolean hasNext();
    
    /**
     * Restituisce l'elemento successivo nell'iterazione.
     * 
     * @return l'elemento successivo nell'iterazione.
     * @throws java.util.NoSuchElementException l'iterazione non ha più elementi.
     */
    Object next();
    
    /**
     * Rimuove dalla collezione sottostante l'ultimo elemento restituito
     * dall'iteratore (operazione opzionale). Questo metodo può essere chiamato
     * solo una volta per ogni chiamata a next. Il comportamento di un iteratore
     * è non specificato se la collezione sottostante viene modificata durante
     * l'iterazione in qualsiasi modo diverso dalla chiamata di questo metodo.
     * 
     * @throws myExceptions.UnsupportedOperationException se l'operazione remove non è
     *         supportata da questo iteratore.
     * @throws myExceptions.IllegalStateException se il metodo next non è ancora stato
     *         chiamato, oppure il metodo remove è già stato chiamato dopo
     *         l'ultima chiamata al metodo next.
     */
    void remove();
}
