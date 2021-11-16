package by.salei.center;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static java.lang.Thread.sleep;

public class OperatorTest {

    @Test
    public void runTest() throws Exception{
        Operator operator = new Operator();
        operator.start();
        sleep(200);
        Assertions.assertFalse(operator.isAlive());
    }
}
