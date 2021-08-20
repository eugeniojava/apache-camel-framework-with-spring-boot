package com.eugeniojava.camelmicroservicea.route.c;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ActiveMqSenderRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // timer
//        from("timer:activemq-timer?period=10000")
//                .transform().constant("My message for ActiveMQ")
//                .log("${body}")
//                .to("activemq:my-queue");
        // queue
        from("file:files/json")
                .log("${body}")
                .to("activemq:my-queue");
    }
}
