package com.phoenix.logistics.core.product.api.controller.dto.request;

import org.springframework.boot.context.properties.bind.DefaultValue;

public record SearchProductsRequest(

        @DefaultValue(value = "") String searchQuery,

        @DefaultValue(value = "createdAt") String sortBy,

        @DefaultValue(value = "desc") String direction,

        @DefaultValue(value = "0") Integer page,

        @DefaultValue(value = "10") Integer pageSize

) {
}
