package com.phoenix.logistics.storage.db.core;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ExampleEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String exampleColumn;

    public ExampleEntity() {
    }

    public ExampleEntity(String exampleColumn) {
        this.exampleColumn = exampleColumn;
    }

    public Long getId() {
        return id;
    }

    public String getExampleColumn() {
        return exampleColumn;
    }

}
