package com.makeitvsolo.hostels.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public final class TenantDto {

    private final Long id;
    private final String name;
}
