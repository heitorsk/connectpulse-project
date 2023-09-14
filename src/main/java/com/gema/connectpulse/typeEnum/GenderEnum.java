package com.gema.connectpulse.typeEnum;

public enum GenderEnum {
    MASCULINO("Masculino"),
    FEMININO("Feminino");

    private final String descricao;

    GenderEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
