package vrbaidu.top.zookeeper;


import org.I0Itec.zkclient.ZkClient;

/**
 * Created by Administrator on 2017/3/26.
 */
public class ConnZk {
    private static final String host = "127.0.0.1:2181";
    private static int timeOut = 3000;

    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient(host,timeOut ,timeOut, new MyZkSerializer());
    }
}
