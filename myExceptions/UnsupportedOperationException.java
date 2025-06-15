//Alberto Bortoletto 2101761

package myExceptions;

/**
 * Eccezione unchecked lanciata per indicare che l'operazione richiesta non è supportata
 * dall'implementazione corrente di un metodo o di una classe.
 * Questa è un'implementazione personalizzata per ambienti CLDC 1.1,
 * dato che l'equivalente {@code java.lang.UnsupportedOperationException} non è disponibile.
 * Estende {@code java.lang.RuntimeException} rendendola un'eccezione unchecked.
 */
public class UnsupportedOperationException extends RuntimeException 
{

    /**
     * Costruisce una {@code UnsupportedOperationException} senza un messaggio di dettaglio.
     */
    public UnsupportedOperationException() 
    {
        super();
    }

    /**
     * Costruisce una {@code UnsupportedOperationException} con il messaggio di dettaglio specificato.
     *
     * @param message il messaggio di dettaglio.
     */
    public UnsupportedOperationException(String message) 
    {
        super(message);
    }

    /**
     * Costruisce una {@code UnsupportedOperationException} con la causa specificata.
     *
     * @param cause la causa dell'eccezione.
     */
    public UnsupportedOperationException(Throwable cause) 
    {
        super(); // In CLDC 1.1, RuntimeException non ha il costruttore con Throwable. Dovrai gestire la causa manualmente nel messaggio se vuoi.
        // O: super(cause != null ? cause.toString() : null); se vuoi che il messaggio contenga la causa
    }

    /**
     * Costruisce una {@code UnsupportedOperationException} con il messaggio di dettaglio
     * e la causa specificati.
     *
     * @param message il messaggio di dettaglio.
     * @param cause   la causa dell'eccezione.
     */
    public UnsupportedOperationException(String message, Throwable cause) 
    {
        super(message); // In CLDC 1.1, RuntimeException non ha il costruttore con (String, Throwable).
        // Puoi opzionalmente impostare la causa internamente se necessario, ma non è standard per RuntimeException in CLDC.
    }
}