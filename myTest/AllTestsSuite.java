package myTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    TestListAdapterEmpty.class,
    TestListAdapterPopulated.class,
    TestSubListAdapter.class,
    TestListIterator.class,
})
public class AllTestsSuite 
{
    // Questa classe non contiene codice, serve solo a raggruppare i test
}
