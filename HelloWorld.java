package com.jike.spring.chapter01;

public class HelloWorld implements IHelloMessage{
	
	//继承和实现了IHelloMessage接口中的sayHello方法，
	//向大家输出了Hello China的信息
	@Override
	public String sayHello() {
		return "Hello World!";
	}

}
