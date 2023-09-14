package com.gema.connectpulse.typeEnum;

public enum SpecialityEnum {
    CARDIOLOGIA("Cardiologia"),
    DERMATOLOGIA("Dermatologia"),
    GINECOLOGIA("Ginecologia"),
    PEDIATRIA("Pediatria"),
    PSIQUIATRIA("Psiquiatria"),
    ORTOPEDIA("Ortopedia"),
    ONCOLOGIA("Oncologia"),
    UROLOGIA("Urologia"),
    NEUROLOGIA("Neurologia"),
    OFTALMOLOGIA("Oftalmologia"),
    OTORRINOLARINGOLOGIA("Otorrinolaringologia"),
    GASTROENTEROLOGIA("Gastroenterologia"),
    NEFROLOGIA("Nefrologia"),
    RADIOLOGIA("Radiologia"),
    ANESTESIOLOGIA("Anestesiologia"),
    CIRURGIA_GERAL("Cirurgia Geral");

    private final String descricao;

    SpecialityEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
