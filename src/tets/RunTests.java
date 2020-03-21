package tets;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestMessage.class, TestProperties.class, TestTimeStamp.class, TestTopic.class })
public class RunTests {
}
