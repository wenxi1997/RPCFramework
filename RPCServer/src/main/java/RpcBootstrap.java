import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author jony
 */
public class RpcBootstrap {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("spring.xml");
    }

}