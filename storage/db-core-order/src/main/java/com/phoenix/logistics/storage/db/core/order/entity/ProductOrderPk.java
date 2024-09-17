package com.phoenix.logistics.storage.db.core.order.entity;

import java.io.Serializable;
import java.util.UUID;

public class ProductOrderPk implements Serializable {

    private UUID productUuid;

    private UUID orderUuid;

}
