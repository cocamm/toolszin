package com.study.tools.general;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.function.Supplier;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@EqualsAndHashCode
@RequiredArgsConstructor
public class LazyValue<T> implements Supplier<T> {

    private final Supplier<? extends T> supplier;

    @Nullable
    private T value = null;
    private boolean resolved = false;

    public static <T> LazyValue<T> of(Supplier<? extends T> supplier) {
        return new LazyValue<>(supplier);
    }

    public static <T> LazyValue<T> of(T value) {
        return new LazyValue<>(() -> value);
    }

    public LazyValue<T> or(T value) {
        Assert.notNull(value, () -> "Value cannot be null.");

        return new LazyValue<>(() -> orElse(value));
    }

    public LazyValue<T> or(Supplier<? extends T> supplier) {
        Assert.notNull(supplier, "Supplier must not be null!");

        return LazyValue.of(() -> orElseGet(supplier));
    }

    @Override
    public T get() {
        T value = getNullable();

        Assert.state(nonNull(value), () -> "Lazy value returned null.");

        return value;
    }

    public Optional<T> getOptional() {
        return Optional.ofNullable(getNullable());
    }

    public T orElse(T value) {
        T nullable = getNullable();

        return isNull(nullable) ? value : nullable;
    }

    @Nullable
    private T orElseGet(Supplier<? extends T> supplier) {
        Assert.notNull(supplier, () -> "Supplier cannot be null.");

        T value = getNullable();

        return isNull(value) ? supplier.get() : value;
    }

    @Nullable
    private T getNullable() {
        T value = this.value;

        if (this.resolved) {
            return value;
        }

        value = supplier.get();
        this.resolved = true;

        return value;
    }
}
