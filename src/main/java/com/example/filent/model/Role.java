package com.example.filent.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Role {
    CLIENT,
    FREELANCER,
    ADMIN;

    public static List<String> getRoleNames() {
        return Arrays.stream(values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}