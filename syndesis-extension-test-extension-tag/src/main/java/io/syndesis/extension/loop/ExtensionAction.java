package io.syndesis.extension.loop;

import java.util.Map;
import java.util.Optional;

import io.syndesis.extension.api.Step;
import io.syndesis.extension.api.annotations.Action;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.model.ProcessorDefinition;

@Action(id = "test-extension", name = "TestExtension", description = "Used to test import libraries extension", tags = { "test", "extension"})
public class ExtensionAction implements Step {


    @Override
    public Optional<ProcessorDefinition> configure(CamelContext context, ProcessorDefinition definition, Map<String, Object> parameters) {
        return Optional.of(definition.process(this::process));
    }

    void process(Exchange exchange) throws Exception {
        String textComingFromLibrary = Class.forName("io.syndesis.extensions.test.A").newInstance().toString();
        System.out.println("Runtime library processed: " + textComingFromLibrary);
        exchange.getIn().setBody(textComingFromLibrary, String.class);
    }
}