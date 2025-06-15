//Alberto Bortoletto 2101761

package myTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * <b>Summary:</b>
 * <p>
 * Questa classe {@code AllTestsSuite} è una suite di test JUnit che aggrega tutti i test relativi alle implementazioni
 * delle interfacce `HList` e `HListIterator` presenti nel progetto.
 * La sua funzione principale è quella di fornire un unico punto di ingresso per eseguire tutti i test definiti nelle
 * classi specificate, facilitando l'esecuzione complessiva della suite di test.
 * <p>
 * <b>Test Case Design:</b>
 * <p>
 * La motivazione di questa suite è centralizzare l'esecuzione dei test JUnit. Utilizzando l'annotazione {@code @RunWith(Suite.class)},
 * si indica a JUnit di eseguire tutti i test contenuti nelle classi elencate nell'annotazione {@code @Suite.SuiteClasses}.
 * Questo approccio garantisce che tutte le funzionalità critiche delle classi {@code ListAdapter}, {@code ListIterator}
 * e {@code SubList} siano verificate in modo coerente e completo.
 * <p>
 * Le classi incluse sono:
 * <ul>
 * <li>{@link myTest.TestListAdapterEmpty}: Test per {@link myAdapter.ListAdapter} su una lista vuota.</li>
 * <li>{@link myTest.TestListAdapterPopulated}: Test per {@link myAdapter.ListAdapter} su una lista popolata.</li>
 * <li>{@link myTest.TestListIteratorPopulated}: Test per {@link myAdapter.ListIterator} su una lista popolata.</li>
 * <li>{@link myTest.TestListIteratorEmpty}: Test per {@link myAdapter.ListIterator} su una lista vuota.</li>
 * <li>{@link myTest.TestSubListAdapter}: Test per la classe interna {@code myAdapter.ListAdapter.SubList}.</li>
 * </ul>
 * <p>
 * Non contiene logica di test propria, ma funge da contenitore per l'organizzazione e l'esecuzione collettiva
 * delle singole suite di test.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    TestListAdapterEmpty.class,     //46 Tests
    TestListAdapterPopulated.class,   //90 Tests
    TestListIteratorPopulated.class,    //28 Tests
    TestListIteratorEmpty.class,          //15 Tests
    TestSubListAdapter.class,               //60 Tests
})
public class AllTestsSuite 
{
    /**
     * Costruttore predefinito per la suite di test {@code AllTestsSuite}.
     * Questa classe è un contenitore per l'esecuzione di test JUnit e non richiede
     * inizializzazione di stato specifica.
     */
    public AllTestsSuite() 
    {
        // Il costruttore predefinito non richiede implementazione
    }
}