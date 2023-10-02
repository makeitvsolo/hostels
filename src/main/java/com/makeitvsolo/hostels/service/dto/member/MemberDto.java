package com.makeitvsolo.hostels.service.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public final class MemberDto {

    private final String name;
    private final String password;
}
