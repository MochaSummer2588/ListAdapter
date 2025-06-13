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
    
    public static void main(String[] args) 
    {
        System.out.println("=== ESECUZIONE TEST SUITE ===");
        System.out.println("Avvio dei test...\n");
        
        long startTime = System.currentTimeMillis();
        
        // Esegui tutti i test
        JUnitCore junit = new JUnitCore();
        Result result = junit.run(
            TestListAdapterCore.class
            // Aggiungi qui altre classi di test quando le crei
            // TestListIterator.class,
            // TestListAdapterAdvanced.class
        );
        
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
