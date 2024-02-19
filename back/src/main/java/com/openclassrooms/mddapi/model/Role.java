package com.openclassrooms.mddapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public enum Role {
    @JsonIgnore
    USER,
    ADMIN
}
