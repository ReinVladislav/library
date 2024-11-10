package ru.rein.library.Models.Enums;

import lombok.Getter;

@Getter
public enum TypeOperation {

    TAKE("TAKE"),
    RETURN("RETURN");

    private final String typeOperation;

    TypeOperation(String typeOperation) {
            this.typeOperation = typeOperation;
        }

}
