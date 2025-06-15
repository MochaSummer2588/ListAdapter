//Alberto Bortoletto 2101761

package myExceptions;

/**
 * Eccezione unchecked (runtime exception) lanciata per indicare che un metodo è stato invocato
 * in un momento inappropriato o che l'oggetto si trova in uno stato non valido
 * per l'operazione richiesta.
 * <p>
 * Questa è un'implementazione personalizzata per ambienti CLDC 1.1,
 * dato che l'equivalente {@code java.lang.IllegalStateException} non è disponibile.
 * Estende {@code java.lang.RuntimeException}, rendendola un'eccezione che non
 * deve essere dichiarata nella clausola {@code throws} dei metodi.
 * </p>
 */
public class IllegalStateException extends RuntimeException 
{
    
    /**
     * Costruisce una nuova IllegalStateException senza messaggio.
     */
    public IllegalStateException() 
    {
        super();
    }
    
    /**
     * Costruisce una nuova IllegalStateException con il messaggio specificato.
     * 
     * @param message il messaggio che descrive l'errore
     */
    public IllegalStateException(String message) 
    {
        super(message);
    }
}
