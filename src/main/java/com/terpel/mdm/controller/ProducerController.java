package com.terpel.mdm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {
	
	@RequestMapping("/sendMessage")
	public String sendMessage() {
		QueueMessageProducer queProducer = new QueueMessageProducer("ssl://broker-amq-tcp-ssl-terpel-dcem-amq-dev.cloudapps.terpel.com:443", "userhqc", "5MiEv4ph");
        queProducer.sendDummyMessages("mdm.queue");
		return "SUCCESS";
		
	}

}
