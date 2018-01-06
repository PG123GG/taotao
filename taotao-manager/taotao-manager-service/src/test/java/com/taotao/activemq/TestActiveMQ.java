package com.taotao.activemq;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;

public class TestActiveMQ {

	//创建一个Queue队列
	@Test
	public void testcreateQueue() throws JMSException{
		//创建ConnectionFactory
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.1.106:61616");
		//创建一个Connection对象
		Connection connection = connectionFactory.createConnection();
		//开启连接
		connection.start();
		//使用Connection创建一个Session
		//第一个参数是否开启分布式事务(一般不开启,只为保证数据最终一致就行了),为true时会自动忽略第二个参数
		//第二个参数表示Session的应答模式，自动应答或手动应答
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//创建Destination对象,两种形式为Queue、Topic
		//Destination destination = session.createTopic("topic名称")
		Destination destination = session.createQueue("test-queue");
		//创建生成者Producer对象
		MessageProducer producer = session.createProducer(destination);
		//消息对象
		TextMessage textmessage = new ActiveMQTextMessage();
		//textmessage.setText("Hello World1!");
		 
		TextMessage textmessage1 = session.createTextMessage("Hello Workjlld2!");
		//发送消息
		producer.send(textmessage1);
		//关闭资源
		producer.close();
		session.close();
		connection.close();
		
	}
	
	//消费一个Queue队列
	@Test
	public void consumerMsg() throws JMSException, InterruptedException, IOException{
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.1.106:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue("test-queue");
		//创建一个消费者
		MessageConsumer consumer = session.createConsumer(destination);
		//异步消费
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message msg) {
				//获取消息内容
				if (msg instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) msg;
					try {
						System.out.println(textMessage.getText());
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		});
		System.in.read();
		consumer.close();
		session.close();
		connection.close();
//
//		//循环等待
//		while(true){
//			Thread.sleep(100);
//		}
		
	}
	
	//当客户端默认没有开启时,会造成消息丢失,因为topic不像queue那样会在服务端进行持久化处理
	@Test
	public void testTopicProducer() throws Exception{
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.1.106:61616");
		
		Connection connection =	connectionFactory.createConnection();
		
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	
		Topic topic = session.createTopic("test-topic");

		MessageProducer producer = session.createProducer(topic);
	
		TextMessage textMessage = session.createTextMessage("topic msg265!");
		
		producer.send(textMessage);
		
		producer.close();
		session.close();
		connection.close();
	
	}
	
	@Test
	public void testTopicConsmer() throws Exception{
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.1.106:61616");
		
		Connection connection = connectionFactory.createConnection();

		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Topic topic = session.createTopic("test-topic");
		
		MessageConsumer consumer = session.createConsumer(topic);
		
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
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
		});
		
		System.out.println("消费者2...");
		System.in.read();
		
		consumer.close();
		session.close();
		connection.close();
	}
	
	
	
	
}
