package com.makeitvsolo.hostels.service.dto.hostel;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public final class HostelItemDto {

    private final Long id;
    private final String name;
    private final Long ownerId;
}
