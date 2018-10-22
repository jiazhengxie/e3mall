package com.jzhx.activemq;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class messageCustomer {
	
	@Test
	public void magCustomer() throws Exception{
		ApplicationContext 	applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
		
		System.in.read();
	}

}
