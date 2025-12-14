package com.ism.restaurant.model;

public enum TypeComplement {
    
    FRITE (1),
    BOISSON (2);

    private final int id;

    TypeComplement(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
