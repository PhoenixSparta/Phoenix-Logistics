package com.phoenix.logistics.core.delivery.domain;

public enum SortDirection {

    ASC, DESC;

    public boolean isAscending() {
        return this.equals(ASC);
    }

    public boolean isDescending() {
        return this.equals(DESC);
    }

}
