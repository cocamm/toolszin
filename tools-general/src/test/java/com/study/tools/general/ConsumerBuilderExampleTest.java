package com.study.tools.general;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ConsumerBuilderExampleTest {

    @Test
    void shouldCreateRequestWithConsumerBuilder() {
        var client = new ConsumerBuilderExample();
        client.createRequest(builder -> builder.name("name")
                .age(20)
                .address("address"));

        final var request = client.getRequest();

        assertAll(() -> assertThat(request.getName(), is("name")),
                  () -> assertThat(request.getAge(), is(20)),
                  () -> assertThat(request.getAddress(), is("address"))
        );
    }

    @Test
    void shouldCreateRequestWithDefaultBuilder() {
        var client = new ConsumerBuilderExample();
        client.createRequest(Request.builder()
                                     .name("name")
                                     .age(20)
                                     .address("address")
                                     .build());

        final var request = client.getRequest();

        assertAll(() -> assertThat(request.getName(), is("name")),
                  () -> assertThat(request.getAge(), is(20)),
                  () -> assertThat(request.getAddress(), is("address"))
        );
    }
}