package com.taotao.redis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taotao.jedis.JedisClient;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class TestJedis {
	
	/**
	 * 此种不支持集群
	 */
	//@Test
	public void testJedis(){
		//指定redis的ip地址和端口号
		Jedis jedis = new Jedis("192.168.1.106", 7002);
		//使用jedis往redis中写入数据
		jedis.set("key3", "4567889");
		//从redis中取数据
		System.out.println(jedis.get("key1"));
		System.out.println(jedis.get("key3"));
		//关闭redis的连接
		jedis.close();
	}
	
	public static void main(String[] args) {
		//指定redis的ip地址和端口号
				Jedis jedis = new Jedis("192.168.1.106", 6379);
				//使用jedis往redis中写入数据
				jedis.set("key3", "4567889");
				//从redis中取数据
				System.out.println(jedis.get("key1"));
				System.out.println(jedis.get("key3"));
				//关闭redis的连接
				jedis.close();
	}
	
	//使用jedis连接池去获得jedis连接，不支持集群
	//@Test
	public void testJedisPool(){
		//创建一个连接池（单例的），指定ip地址和端口号
		JedisPool jedisPool = new JedisPool("192.168.1.106", 6379);
		//从连接池中获取jedis实例
		Jedis jedis = jedisPool.getResource();
		//获取数据
		System.out.println(jedis.get("key3"));
		System.out.println(jedis.ping());
		//使用完关闭jedis连接
		jedis.close();
		//在系统关闭前关闭连接池
		jedisPool.close();
	}
	
	//@Test
	public void testJedisCluster1(){
		//指定集群中redis服务器
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.1.106", 7001));
		nodes.add(new HostAndPort("192.168.1.106", 7002));
		nodes.add(new HostAndPort("192.168.1.106", 7003));
		nodes.add(new HostAndPort("192.168.1.106", 7004));
		nodes.add(new HostAndPort("192.168.1.106", 7005));
		nodes.add(new HostAndPort("192.168.1.106", 7006));
		//创建JedisCluster对象
		JedisCluster cluster = new JedisCluster(nodes);
		//设置内容
		//cluster.set("key1", "value1");
		//获取内容
		System.out.println(cluster.get("key1"));
		//关闭连接
		cluster.close();
	}
	
	//@Test
	public void testJedisCluster2(){
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:resources/spring/applicationContext-redis.xml");
	
		JedisClient client = applicationContext.getBean(JedisClient.class);
		
		client.get("key3");
		
		
	}

	
}
