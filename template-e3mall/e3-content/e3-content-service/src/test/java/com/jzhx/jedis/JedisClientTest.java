package com.jzhx.jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.config.ApplicationConfig;

import redis.clients.jedis.JedisCluster;

public class JedisClientTest {
	@Test
	public void testJedisClient() throws Exception{
		//初始化spring容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
		
		//从容器中获得JedisClient对象
		JedisCluster jedisCluster = applicationContext.getBean(JedisCluster.class);
		jedisCluster.set("mytest", "jedisCluster");
		String string = jedisCluster.get("mytest");
		System.out.println(string);
	}

}
