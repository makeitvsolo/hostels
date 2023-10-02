package com.makeitvsolo.hostels.controller;

import com.makeitvsolo.hostels.controller.response.ErrorMessageDto;
import com.makeitvsolo.hostels.security.MemberPrincipal;
import com.makeitvsolo.hostels.service.HostelService;
import com.makeitvsolo.hostels.service.TenantService;
import com.makeitvsolo.hostels.service.exception.hostel.HostelAlreadyExistsException;
import com.makeitvsolo.hostels.service.exception.hostel.HostelNotFoundException;
import com.makeitvsolo.hostels.service.exception.hostel.TenantAlreadyExistsException;
import com.makeitvsolo.hostels.service.exception.hostel.TenantNotFoundException;
import com.makeitvsolo.hostels.service.exception.member.MemberNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/hostels")
public class HostelController {

    private final HostelService hostelService;
    private final TenantService tenantService;

    public HostelController(HostelService hostelService, TenantService tenantService) {
        this.hostelService = hostelService;
        this.tenantService = tenantService;
    }

    @GetMapping
    public ResponseEntity<?> getAll(@AuthenticationPrincipal MemberPrincipal principal) {
        try {
            var hostels = hostelService.getAll(principal.getId());

            log.info("gets all hostels");
            return ResponseEntity.status(HttpStatus.OK)
                           .body(hostels);
        } catch (MemberNotFoundException ex) {
            log.info(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(new ErrorMessageDto(ex.getMessage()));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .build();
        }
    }

    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody String name,
            @AuthenticationPrincipal MemberPrincipal principal
    ) {
        try {
            hostelService.create(principal.getId(), name);

            log.info("hostel created");
            return ResponseEntity.status(HttpStatus.CREATED)
                           .build();
        } catch (MemberNotFoundException ex) {
            log.info(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(new ErrorMessageDto(ex.getMessage()));
        } catch (HostelAlreadyExistsException ex) {
            log.info(ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                           .body(new ErrorMessageDto(ex.getMessage()));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .build();
        }
    }

    @GetMapping("/{hostelId}/tenants")
    public ResponseEntity<?> getAllTenants(
            @PathVariable("hostelId") Long hostelId,
            @AuthenticationPrincipal MemberPrincipal principal
    ) {
        try {
            var hostel = tenantService.getAllTenants(principal.getId(), hostelId);

            log.info("gets all tenants");
            return ResponseEntity.status(HttpStatus.OK)
                           .body(hostel);
        } catch (HostelNotFoundException ex) {
            log.info(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(new ErrorMessageDto(ex.getMessage()));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .build();
        }
    }

    @PostMapping("/{hostelId}/tenants")
    public ResponseEntity<?> addTenant(
            @RequestBody String memberName,
            @PathVariable("hostelId") Long hostelId,
            @AuthenticationPrincipal MemberPrincipal principal
    ) {
        try {
            tenantService.addTenant(principal.getId(), hostelId, memberName);

            log.info("adds tenant");
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                           .build();
        } catch (HostelNotFoundException | MemberNotFoundException ex) {
            log.info(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(new ErrorMessageDto(ex.getMessage()));
        } catch (TenantAlreadyExistsException ex) {
            log.info(ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                           .body(new ErrorMessageDto(ex.getMessage()));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .build();
        }
    }

    @DeleteMapping("/{hostelId}/tenants")
    public ResponseEntity<?> removeTenant(
            @RequestBody Long tenantId,
            @PathVariable("hostelId") Long hostelId,
            @AuthenticationPrincipal MemberPrincipal principal
    ) {
        try {
            tenantService.removeTenant(principal.getId(), hostelId, tenantId);

            log.info("removes tenant");
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                           .build();
        } catch (HostelNotFoundException | TenantNotFoundException ex) {
            log.info(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(new ErrorMessageDto(ex.getMessage()));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .build();
        }
    }
}
