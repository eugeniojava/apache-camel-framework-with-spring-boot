package com.eugeniojava.camelmicroservicea.route.c;

import org.apache.camel.builder.RouteBuilder;

//@Component
public class RestApiConsumerRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        restConfiguration().host("localhost").port(8081);

        from("timer:rest-api-consumer?period=10000")
                .setHeader("from", () -> "EUR")
                .setHeader("to", () -> "INR")
                .log("${body}")
                .to("rest:get:/currencyExchange/from/{from}/to/{to}")
                .log("${body}");
    }
}
