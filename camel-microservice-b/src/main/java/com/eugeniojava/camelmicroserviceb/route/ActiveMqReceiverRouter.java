package com.eugeniojava.camelmicroserviceb.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ActiveMqReceiverRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("activemq:my-queue")
                .to("log:received-message-from-activemq");
    }
}
