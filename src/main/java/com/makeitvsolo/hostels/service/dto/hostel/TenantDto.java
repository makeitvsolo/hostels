package com.makeitvsolo.hostels.service.dto.hostel;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public final class TenantDto {

    private final Long id;
    private final String name;
}
