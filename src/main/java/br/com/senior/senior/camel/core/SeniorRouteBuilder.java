package br.com.senior.senior.camel.core;

import java.util.UUID;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.jboss.logging.MDC;

public abstract class SeniorRouteBuilder extends RouteBuilder {

    @Override
    public final void configure() throws Exception {
        getContext().setTracing(true);

        interceptFrom().process(this::configureMDC);

        configureRoute();
    }

    public abstract void configureRoute();

    private void configureMDC(Exchange exchange) {
        Message message = exchange.getMessage();

        Object tenant = message.getHeader("X-Integration-Tenant");
        Object primitive = message.getHeader("X-Integration-Id");
        Object contextId = message.getHeader("X-Integration-Context-Id");
        if (contextId == null) {
            contextId = UUID.randomUUID();
            message.setHeader("X-Integration-Context-Id", contextId);
        }
        UUID requestId = UUID.randomUUID();

        MDC.put("tenant", tenant);
        MDC.put("primitive", primitive);
        MDC.put("contextId", contextId);
        MDC.put("requestId", requestId);
    }

}
