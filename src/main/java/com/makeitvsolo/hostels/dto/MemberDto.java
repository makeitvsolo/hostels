package com.makeitvsolo.hostels.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public final class MemberDto {

    private final String name;
    private final String password;
}
