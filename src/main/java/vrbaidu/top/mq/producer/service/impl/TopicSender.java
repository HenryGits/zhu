package vrbaidu.top.mq.producer.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * 
 * @author liang
 * @description   Topic生产者发送消息到Topic
 * 
 */

@Service
public class TopicSender {
	
	@Qualifier
	private JmsTemplate jmsTemplate;
	
	/**
	 * 发送一条消息到指定的队列（目标）
	 * @param topicName 队列名称
	 * @param message 消息内容
	 */
	public void send(String topicName,final String message){
		jmsTemplate.send(topicName, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message);
			}
		});
	}

}
