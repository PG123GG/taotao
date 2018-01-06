package com.taotao.activemq;

import java.util.Queue;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class TestSpringActiveMQ {

	/*
	 * 测试Spring管理的JMSTemplate
	 */
	@Test
	public void testSpringMQ(){
		//加载配置文件
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
		//获取JMSTemplate
		JmsTemplate jmsTemplate = (JmsTemplate) context.getBean(JmsTemplate.class);
		//发送模式在xml文件中就配置好,取相应的bean就行
		Destination destination = (Destination) context.getBean("test-queue");
		
		//使用jmsTemplate发送消息
		jmsTemplate.send(destination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage("wo men jiu shi yi jia ren65!");
				return textMessage;
			}
		});
		
	}
	

	
}
