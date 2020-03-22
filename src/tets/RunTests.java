package tets;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Lanceur pour la suite de test, à executer avec JUnit 4
 * 
 * @author Bello Velly
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ TestMessage.class, TestProperties.class, TestTimeStamp.class, TestTopic.class })
public class RunTests {
}
