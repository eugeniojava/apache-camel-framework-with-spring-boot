package com.eugeniojava.camelmicroservicea.route.pattern;

import com.eugeniojava.camelmicroservicea.CurrencyExchange;
import java.util.ArrayList;
import java.util.List;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class EipPatternRouter extends RouteBuilder {

    private final SplitterComponent splitterComponent;

    public EipPatternRouter(SplitterComponent splitterComponent) {
        this.splitterComponent = splitterComponent;
    }

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

//        from("file:files/csv")
//                .unmarshal().csv()
//                .split(body())
//                .to("activemq:split-queue");

        // message,message2,message3
//        from("file:files/csv")
//                .convertBodyTo(String.class)
//                .split(method(splitterComponent))
//                .to("activemq:split-queue");

        // aggregate
        // messages -> aggregate -> endpoint
        // to, 3
        from("file:files/aggregate-json")
                .unmarshal().json(JsonLibrary.Jackson, CurrencyExchange.class)
                .aggregate(simple("${body.getTo}"), new ArrayListAggregationStrategy())
                .completionSize(3)
//                .completionTimeout(HIGHEST)
                .to("log:aggregate-json");

    }

    private static class ArrayListAggregationStrategy implements AggregationStrategy {
        // 1,2,3
        // null, 1 -> [1]
        // [1], 2 -> [1, 2]
        // [1, 2], 3 -> [1, 2, 3]

        @Override
        public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
            Object newBody = newExchange.getIn().getBody();
            ArrayList<Object> list;

            if (oldExchange == null) {
                list = new ArrayList<>();

                list.add(newBody);
                newExchange.getIn().setBody(list);

                return newExchange;
            } else {
                list = oldExchange.getIn().getBody(ArrayList.class);
                list.add(newBody);

                return oldExchange;
            }
        }
    }
}

@Component
class SplitterComponent {

    public List<String> splitInput(String body) {
        return List.of("ABC", "DEF", "GHI");
    }
}
