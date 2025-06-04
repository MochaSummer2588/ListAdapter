package myExceptions;

/**
 * Eccezione lanciata quando un metodo viene invocato in un momento inappropriato
 * o quando l'oggetto si trova in uno stato non valido per l'operazione richiesta.
 * 
 * Questa è una runtime exception (unchecked) che estende RuntimeException.
 * Versione compatibile con CLDC 1.1.
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
