package com.eugeniojava.camelmicroservicea.route.pattern;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class EipPatternRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // pipeline
        // content based routing -> choice()
        // multicast
//        from("timer:multicast-timer?period=10000")
//                .multicast()
//                .to("log:something1", "log:something2", "log:something3");

//        from("file:files/csv")
//                .multicast()
//                .to("file:files/output1", "file:files/output2", "file:files/output3");

        from("file:files/csv")
                .unmarshal().csv()
                .split(body())
                .to("activemq:split-queue");
    }
}
