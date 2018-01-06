package com.taotao.activemq;

import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 监听接收Queue的消息
 * @author Administrator
 *
 */
public class MyMessageListener implements MessageListener {

	@Override
	public void onMessage(javax.jms.Message message) {
		// TODO Auto-generated method stub
		if (message instanceof TextMessage) {
			TextMessage message2 = (TextMessage)message;
			try {
				System.out.println(message2.getText());
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
