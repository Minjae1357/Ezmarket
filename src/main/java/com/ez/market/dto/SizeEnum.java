package com.ez.market.dto;


public enum SizeEnum {
    S(0),
    M(1),
    L(2),
    XL(3),
    XXL(4);

    private final int value;

    private SizeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static SizeEnum fromString(String text) {
        for (SizeEnum size : SizeEnum.values()) {
            if (size.name().equalsIgnoreCase(text)) {
                return size;
            }
        }
        throw new IllegalArgumentException("Unknown size: " + text);
    }
}