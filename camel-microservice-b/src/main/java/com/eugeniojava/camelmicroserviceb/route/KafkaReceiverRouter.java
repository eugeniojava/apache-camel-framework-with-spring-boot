package com.eugeniojava.camelmicroserviceb.route;

import org.apache.camel.builder.RouteBuilder;

//@Component
public class KafkaReceiverRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("kafka:myKafkaTopic")
                .to("log:received-message-from-kafka");
    }
}
