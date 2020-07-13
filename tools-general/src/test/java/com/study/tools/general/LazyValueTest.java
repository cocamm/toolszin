package com.study.tools.general;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

class LazyValueTest {

    private List<String> list = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h");

    @Test
    void shouldCreateLazyFromSupplierStaticConstructor() {
        var lazy = LazyValue.of(() -> lazyValue());

        assertThat(lazy.get(), is(true));
    }

    @Test
    void shouldCreateLazyFromValueStaticConstructor() {
        var lazy = LazyValue.of(lazyValue());

        assertThat(lazy.get(), is(true));
    }

    @Test
    void shouldGetOptionalFromLazyValue() {
        var lazy = LazyValue.of(() -> lazyValue());

        assertThat(lazy.getOptional(), is(Optional.of(true)));
    }

    @Test
    void shouldGetOptionalNullFromLazyValue() {
        var lazy = LazyValue.of(() -> null);

        assertThat(lazy.getOptional(), is(Optional.empty()));
    }

    @Test
    void shouldReturnElseValue() {
        var lazy = LazyValue.of(() -> null);

        assertThat(lazy.orElse(true), is(true));
        assertThat(lazy.or(true).get(), is(true));
        assertThat(lazy.or(() -> true).get(), is(true));
    }

    private boolean lazyValue() {
        return list.stream().anyMatch(l -> l.equals("h"));
    }
}