package com.phoenix.logistics.core.delivery.domain;

public record Cursor(String query, String searchBy, String cursor, int limit, String sortKey, SortDirection sort) {
}
