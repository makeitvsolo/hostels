package com.makeitvsolo.hostels.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public final class HostelDto {

    private final Long id;
    private final String name;
    private final Long ownerId;
}
