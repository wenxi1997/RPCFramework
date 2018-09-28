import client.RpcProxy;
import org.junit.Assert;
import org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.ILookupService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class RPCTest extends BaseTest{

    @Autowired
    RpcProxy proxy;

    @Test
    public void main() {
        ILookupService service = proxy.getService(ILookupService.class);
        Object A = service.lookup("A");
        Object B = service.lookup("B");
        Object C = service.lookup("C");
        Object D = service.lookup("D");
        Assert.assertEquals(A, "Hello Stranger");
        Assert.assertEquals(B, "This is some time");
        Assert.assertEquals(C, "And a better life");
        Assert.assertEquals(D, "Just some test data");
    }
}
