package com.challenge.constant;

public enum SituacaoPago {
    SIM(true),
    NAO(false);

    private boolean value;

    private SituacaoPago (boolean value) {
        this.value = value;
    }

    public boolean getValue () {
        return value;
    }
}
