package com.jzhx.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {
	//连接单机版的redis
	@Test
	 public void testJedis(){
		try {
			//创建一个redis 对象 参数Host prot
			 Jedis jedis = new Jedis("101.132.78.7", 7001);
			 //直接使用Jedis 操作redis
			 jedis.set("test123","my first jedis test!");
			 String string = jedis.get("test123");
			 System.out.println(string);
			 //关闭连接
			 jedis.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		 
	 }
	@Test
	//连接单机版的redis连接池
	public void testJedisPool() throws Exception{
		//创建一个redis 对象 参数Host prot
		JedisPool jedis = new JedisPool("101.132.78.7", 7001);
		//从连接池获得一个连接，就是一个Jedis对象
		Jedis resource = jedis.getResource();
		 //直接使用Jedis 操作redis
		String string = resource.get("test123");
		System.out.println(string);
		//关闭连接
		resource.close();
		//关闭连接池
		jedis.close();
		
	}
	
	@Test
	//连接集群
	public void testJedisCluster() throws Exception{
		//创建一个JedisCluster 对象 有一个参数nodes是一个set型   set中包含若干个hostandport对象
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("101.132.78.7", 7001));
		nodes.add(new HostAndPort("101.132.78.7", 7002));
		nodes.add(new HostAndPort("101.132.78.7", 7003));
		nodes.add(new HostAndPort("101.132.78.7", 7004));
		nodes.add(new HostAndPort("101.132.78.7", 7005));
		nodes.add(new HostAndPort("101.132.78.7", 7006));
		JedisCluster jedisCluster = new JedisCluster(nodes);
		jedisCluster.set("test", "123");
		String string = jedisCluster.get("test");
		System.out.println(string);
		jedisCluster.close();
	}

}
