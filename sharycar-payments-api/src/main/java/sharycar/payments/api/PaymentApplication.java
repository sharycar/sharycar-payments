package sharycar.payments.api; /**
 * Author Jaka Krajnc
 */

import com.kumuluz.ee.discovery.annotations.RegisterService;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


@ApplicationPath("/")
@RegisterService
public class PaymentApplication extends Application{
}
