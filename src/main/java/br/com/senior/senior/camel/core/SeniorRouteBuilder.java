package br.com.senior.senior.camel.core;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.jboss.logging.MDC;

public abstract class SeniorRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        getContext().setTracing(true);

        interceptFrom().process(this::configureMDC);
    }

    private void configureMDC(Exchange exchange) {
        Message message = exchange.getMessage();
        MDC.put("tenant", message.getHeader("X-Integration-Tenant"));
        MDC.put("primitive", message.getHeader("X-Integration-Id"));
        MDC.put("contextId", message.getHeader("X-Integration-Context-Id"));
        MDC.put("requestId", message.getHeader("X-Integration-Request-Id"));
    }

}
