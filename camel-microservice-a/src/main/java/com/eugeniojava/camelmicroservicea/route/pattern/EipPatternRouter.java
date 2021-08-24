package com.eugeniojava.camelmicroservicea.route.pattern;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class EipPatternRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer:multicast-timer?period=10000")
                .multicast()
                .to("log:something1", "log:something2", "log:something3");
    }
}
