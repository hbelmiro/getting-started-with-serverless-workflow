package com.acme;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


@QuarkusTest
class InternationalGreetingResourceTest {

    @Inject
    InternationalGreetingResource resource;

    @ParameterizedTest
    @MethodSource("getGreetingSource")
    void getGreeting(String language, String name, String expectedMessage) {
        var data = new Data(language, name);
        assertThat(resource.getGreeting(data).getGreeting()).isEqualTo(expectedMessage);
    }

    public static Stream<Arguments> getGreetingSource() {
        return Stream.of(
                Arguments.of("Portuguese", "Helber", "Saudações do Serverless Workflow, Helber!"),
                Arguments.of("Spanish", "Helber", "Saludos desde Serverless Workflow, Helber!"),
                Arguments.of("English", "Helber", "Greetings from Serverless Workflow, Helber!")
        );
    }
}