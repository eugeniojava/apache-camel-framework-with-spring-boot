package com.eugeniojava.camelmicroservicea.route.b;

import java.util.Map;
import org.apache.camel.Body;
import org.apache.camel.ExchangeProperties;
import org.apache.camel.Headers;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MyFileRouter extends RouteBuilder {

    private final DeciderBean deciderBean;

    public MyFileRouter(DeciderBean deciderBean) {
        this.deciderBean = deciderBean;
    }

    @Override
    public void configure() throws Exception {
        from("file:files/input")
                .routeId("Files-Input-Route")
                .transform().body(String.class)
                .choice()
                .when(simple("${file:ext} == 'xml'")).log("XML FILE")
//                .when(simple("${body} contains 'USD'"))
                .when(method(deciderBean))
                .log("NOT A XML FILE BUT CONTAINS USD")
                .otherwise().log("NOT A XML FILE")
                .end()
                .to("file:files/output");

        from("direct:log-file-values")
                .log("${messageHistory} ${file:absolute.path}")
                .log("${file:name} ${file:name.ext} ${file:name.noext} ${file:onlyname}")
                .log("${file:onlyname.noext} ${file:parent} ${file:path} ${file:absolute}")
                .log("${file:size} ${file:modified}")
                .log("${routeId} ${camelId} ${body}");
    }
}

@Component
class DeciderBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeciderBean.class);

    public boolean isThisConditionMet(@Body String body,
                                      @Headers Map<String, String> headers,
                                      @ExchangeProperties Map<String, String> exchangeProperties) {
        LOGGER.info("DeciderBean: body{}\nheaders{}\nexchangeProperties{}", body, headers, exchangeProperties);
        return true;
    }
}
