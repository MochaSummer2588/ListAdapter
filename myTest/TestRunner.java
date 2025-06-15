//Alberto Bortoletto 2101761

package myTest;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * TestRunner per eseguire tutti i test JUnit da linea di comando.
 * Fornisce informazioni su: numero test eseguiti, falliti e tempo di esecuzione.
 */
public class TestRunner 
{
    /**
     * Costruttore predefinito per la classe {@code TestRunner}.
     * Questa classe è un'utility e non richiede un'inizializzazione di stato complessa.
     */
    public TestRunner() 
    {
        // Il costruttore predefinito non richiede implementazione specifica.
    }
    
    /**
     * Punto di ingresso principale per l'esecuzione dei test JUnit.
     * Questo metodo può eseguire tutti i test inclusi nella {@link AllTestsSuite}
     * o una classe di test specifica, se il suo nome completo (incluso il package)
     * viene fornito come argomento della linea di comando.
     *
     * @param args Array di stringhe contenente gli argomenti della linea di comando.
     * Se vuoto, esegue tutti i test definiti in {@link AllTestsSuite}.
     * Se contiene un nome di classe (es. "myTest.TestListAdapterEmpty"),
     * tenta di eseguire solo quella classe di test.
     */
    public static void main(String[] args) 
    {
        System.out.println("=== ESECUZIONE TEST SUITE ===");
        System.out.println("Avvio dei test...");
        
        long startTime = System.currentTimeMillis();
        
        Result result = JUnitCore.runClasses(AllTestsSuite.class);
        
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        
        // Stampa risultati
        System.out.println("\n=== RISULTATI TEST ===");
        System.out.println("Test eseguiti: " + result.getRunCount());
        System.out.println("Test falliti: " + result.getFailureCount());
        System.out.println("Test ignorati: " + result.getIgnoreCount());
        System.out.println("Tempo di esecuzione: " + executionTime + " ms");
        System.out.println("Successo: " + (result.wasSuccessful() ? "SI" : "NO"));
        
        // Stampa dettagli dei fallimenti
        if (result.getFailureCount() > 0) 
        {
            System.out.println("\n=== DETTAGLI FALLIMENTI ===");
            for (Failure failure : result.getFailures()) 
            {
                System.out.println("FALLIMENTO: " + failure.getTestHeader());
                System.out.println("MESSAGGIO: " + failure.getMessage());
                System.out.println("TRACE: " + failure.getTrace());
                System.out.println("---");
            }
        }
        
        System.out.println("\n=== FINE ESECUZIONE TEST ===");
        
        // Exit code: 0 se tutti i test passano, 1 se ci sono fallimenti
        System.exit(result.wasSuccessful() ? 0 : 1);
    }
}
