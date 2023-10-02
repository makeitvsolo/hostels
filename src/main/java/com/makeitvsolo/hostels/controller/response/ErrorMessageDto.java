package com.makeitvsolo.hostels.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public final class ErrorMessageDto {

    private final String message;
}
