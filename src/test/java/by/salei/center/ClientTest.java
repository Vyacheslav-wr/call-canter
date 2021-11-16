package by.salei.center;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import static java.lang.Thread.sleep;

public class ClientTest {

    @Test
    public void runTest() throws Exception{
        CallCenter callCenter = Mockito.mock(CallCenter.class);
        Client client = new Client(1, callCenter);
        Mockito.when(callCenter.makeACall(client)).thenReturn(null);
        client.start();
        sleep(6000);
        Assertions.assertEquals(0, client.getPatience());
    }
}
