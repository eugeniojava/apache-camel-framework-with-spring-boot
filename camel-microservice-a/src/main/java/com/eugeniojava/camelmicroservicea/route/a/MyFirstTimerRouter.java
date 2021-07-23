package com.eugeniojava.camelmicroservicea.route.a;

import java.time.LocalDateTime;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MyFirstTimerRouter extends RouteBuilder {

    private final GetCurrentTimeBean getCurrentTimeBean;
    private final SimpleLoggingProcessingComponent simpleLoggingProcessingComponent;

    public MyFirstTimerRouter(GetCurrentTimeBean getCurrentTimeBean,
                              SimpleLoggingProcessingComponent simpleLoggingProcessingComponent) {
        this.getCurrentTimeBean = getCurrentTimeBean;
        this.simpleLoggingProcessingComponent = simpleLoggingProcessingComponent;
    }

    @Override
    public void configure() throws Exception {
        // timer
        // transformation
        // log
        // Exchange[ExchangePattern: InOnly, BodyType: String, Body: My constant message]
        from("timer:first-timer") // null
                .log("${body}") // null
                .transform().constant("My constant message")
                .log("${body}") // My constant message
//                .transform().constant("Time now is " + LocalDateTime.now())
//                .bean("getCurrentTimeBean")
                // processing
                // transformation
                .bean(getCurrentTimeBean)
                .log("${body}") // Time now is 2021-07-22T00:02:43.702836
                .bean(simpleLoggingProcessingComponent)
                .log("${body}")
                .process(new SimpleLoggingProcessor())
                .to("log:first-timer"); // database
    }
}

@Component
class GetCurrentTimeBean {

    public String getCurrentTime() {
        return "Time now is " + LocalDateTime.now();
    }
}

@Component
class SimpleLoggingProcessingComponent {

    private final Logger LOGGER = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);

    public void process(String message) {
        LOGGER.info("SimpleLoggingProcessingComponent {}", message);
    }
}

class SimpleLoggingProcessor implements Processor {

    private final Logger LOGGER = LoggerFactory.getLogger(SimpleLoggingProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        LOGGER.info("SimpleLoggingProcessor {}", exchange.getMessage().getBody());
    }
}
