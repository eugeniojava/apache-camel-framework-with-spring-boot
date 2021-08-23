package com.eugeniojava.camelmicroservicea.route.b;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyFileRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("file:files/input")
                .routeId("Files-Input-Route")
                .transform().body(String.class)
                .choice()
                .when(simple("${file:ext} == 'xml'")).log("XML FILE")
                .when(simple("${body} contains 'USD'")).log("NOT A XML FILE BUT CONTAINS USD")
                .otherwise().log("NOT A XML FILE")
                .end()
                .log("${messageHistory} ${file:absolute.path}")
                .to("file:files/output");
    }
}
