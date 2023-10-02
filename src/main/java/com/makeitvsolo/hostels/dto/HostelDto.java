package com.makeitvsolo.hostels.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public final class HostelDto {

    private final Long id;
    private final String name;
    private final List<TenantDto> tenants;
}
