package com.eugeniojava.camelmicroservicea.route.a;

import java.time.LocalDateTime;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyFirstTimerRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // timer
        // transformation
        // log
        // Exchange[ExchangePattern: InOnly, BodyType: String, Body: My constant message]
        from("timer:first-timer") // null
//                .transform().constant("My constant message")
                .transform().constant("Time now is " + LocalDateTime.now())
                .to("log:first-timer");
    }
}
