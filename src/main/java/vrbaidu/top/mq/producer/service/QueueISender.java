package vrbaidu.top.mq.producer.service;

/**
 * Created by Administrator on 2017/5/7.
 */
public interface QueueISender {
    public void send(String queueName, final String message);
}
