package com.phoenix.logistics.core.api.controller.v1.request;

import com.phoenix.logistics.core.domain.ExampleData;

public record ExampleRequest(String data) {
    public ExampleData toExampleData() {
        return new ExampleData(data, data);
    }
}
