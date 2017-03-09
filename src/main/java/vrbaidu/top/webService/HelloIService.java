package vrbaidu.top.webService;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Created by Administrator on 2017/2/21.
 */
@WebService
public interface HelloIService {
    @WebMethod
    public  String sayHello(String name);
}
