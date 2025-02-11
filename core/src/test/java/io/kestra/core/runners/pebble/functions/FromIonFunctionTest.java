package io.kestra.core.runners.pebble.functions;

import io.kestra.core.exceptions.IllegalVariableEvaluationException;
import io.kestra.core.junit.annotations.KestraTest;
import io.kestra.core.runners.VariableRenderer;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@KestraTest
class FromIonFunctionTest {
    @Inject
    VariableRenderer variableRenderer;

    @Test
    void ionDecodeFunction() throws IllegalVariableEvaluationException {
        String render = variableRenderer.render("{{ fromIon('{date:2024-04-21T23:00:00.000Z, title:\"Main_Page\",views:109787}').title }}", Map.of());
        assertThat(render, is("Main_Page"));

        render = variableRenderer.render("{{ fromIon(null) }}", Map.of());
        assertThat(render, emptyString());
    }

    @Test
    void exception() {
        assertThrows(IllegalVariableEvaluationException.class, () -> variableRenderer.render("{{ fromIon() }}", Map.of()));

        assertThrows(IllegalVariableEvaluationException.class, () -> variableRenderer.render("{{ fromIon('{not: ion') }}", Map.of()));
    }
}