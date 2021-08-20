package com.eugeniojava.camelmicroserviceb.route;

import com.eugeniojava.camelmicroserviceb.CurrencyExchange;
import java.math.BigDecimal;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ActiveMqReceiverRouter extends RouteBuilder {

    private final MyCurrencyExchangeProcessor myCurrencyExchangeProcessor;
    private final MyCurrencyExchangeTransformer myCurrencyExchangeTransformer;

    public ActiveMqReceiverRouter(MyCurrencyExchangeProcessor myCurrencyExchangeProcessor,
                                  MyCurrencyExchangeTransformer myCurrencyExchangeTransformer) {
        this.myCurrencyExchangeProcessor = myCurrencyExchangeProcessor;
        this.myCurrencyExchangeTransformer = myCurrencyExchangeTransformer;
    }

    @Override
    public void configure() throws Exception {
//        from("activemq:my-queue")
//                .unmarshal()
//                .json(JsonLibrary.Jackson, CurrencyExchange.class)
//                .bean(myCurrencyExchangeProcessor)
//                .bean(myCurrencyExchangeTransformer)
//                .to("log:received-message-from-activemq");
        from("activemq:my-xml-queue")
                .unmarshal()
                .jacksonxml(CurrencyExchange.class)
                .to("log:received-message-from-activemq");
    }
}

@Component
class MyCurrencyExchangeProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyCurrencyExchangeProcessor.class);

    public void processMessage(CurrencyExchange currencyExchange) {
        LOGGER.info("Do some processing with currencyExchange.getConversionMultiple() value which is {}",
                currencyExchange.getConversionMultiple());
    }
}

@Component
class MyCurrencyExchangeTransformer {

    public CurrencyExchange processMessage(CurrencyExchange currencyExchange) {
        currencyExchange.setConversionMultiple(currencyExchange.getConversionMultiple().multiply(BigDecimal.TEN));

        return currencyExchange;
    }
}
