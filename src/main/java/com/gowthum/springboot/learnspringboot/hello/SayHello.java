package com.gowthum.springboot.learnspringboot.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SayHello {
	
	@RequestMapping("hello")
	public String SayHelloByUserName(){
		return "sayHello";
	}
	
}
