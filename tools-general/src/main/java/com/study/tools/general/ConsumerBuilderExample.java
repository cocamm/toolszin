package com.study.tools.general;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

public class ConsumerBuilderExample {

    @Getter
    private Request request;

    public void createRequest(Request request) {
        this.request = request;
    }

    public void createRequest(Consumer<Request.RequestBuilder> request) {
        this.request = Request.builder().applyBuilder(request).build();
    }
}

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class Request {

    private final String name;
    private final Integer age;
    private final String address;

    public static RequestBuilder builder() {
        return new RequestBuilder();
    }

    public static class RequestBuilder implements Builder<RequestBuilder> {

        private String name;
        private Integer age;
        private String address;

        public RequestBuilder name(String name) {
            this.name = name;
            return this;
        }

        public RequestBuilder age(Integer age) {
            this.age = age;
            return this;
        }

        public RequestBuilder address(String address) {
            this.address = address;
            return this;
        }

        protected Request build() {
            return new Request(name, age, address);
        }
    }
}

interface Builder<T> {

    @SuppressWarnings("unchecked")
    default T applyBuilder(Consumer<T> builder) {
        builder.accept((T) this);
        return (T) this;
    }
}
