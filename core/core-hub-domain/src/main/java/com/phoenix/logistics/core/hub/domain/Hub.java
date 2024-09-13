package com.phoenix.logistics.core.hub.domain;

public record Hub(int sequence, String name, String city, String fullAddress, double latitude, double longitude) {
}
