package com.makeitvsolo.hostels.service;

import com.makeitvsolo.hostels.dto.HostelDto;
import com.makeitvsolo.hostels.dto.TenantDto;
import com.makeitvsolo.hostels.service.exception.hostel.HostelNotFoundException;
import com.makeitvsolo.hostels.service.exception.member.MemberNotFoundException;
import com.makeitvsolo.hostels.service.exception.hostel.TenantAlreadyExistsException;
import com.makeitvsolo.hostels.service.exception.hostel.TenantNotFoundException;
import com.makeitvsolo.hostels.repository.HostelRepository;
import com.makeitvsolo.hostels.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public final class TenantService {

    private final HostelRepository hostelRepository;
    private final MemberRepository memberRepository;

    public TenantService(HostelRepository hostelRepository, MemberRepository memberRepository) {
        this.hostelRepository = hostelRepository;
        this.memberRepository = memberRepository;
    }

    public void addTenant(Long ownerId, Long hostelId, String memberName) {
        hostelRepository.findByIdAndOwner(hostelId, ownerId)
                .ifPresentOrElse(hostel -> {
                    var tenant = memberRepository.findByName(memberName)
                                         .orElseThrow(() -> new MemberNotFoundException(memberName));

                    var success = hostel.addTenant(tenant);
                    if (!success) {
                        throw new TenantAlreadyExistsException(memberName);
                    }

                    hostelRepository.save(hostel);
                }, () -> {
                    throw new HostelNotFoundException();
                });
    }

    public void removeTenant(Long ownerId, Long hostelId, Long tenantId) {
        hostelRepository.findByIdAndOwner(hostelId, ownerId)
                .ifPresentOrElse(hostel -> {
                    var success = hostel.removeTenant(tenantId);
                    if (!success) {
                        throw new TenantNotFoundException();
                    }

                    hostelRepository.save(hostel);
                }, () -> {
                    throw new HostelNotFoundException();
                });
    }

    public HostelDto getAllTenants(Long ownerId, Long hostelId) {
        return hostelRepository.findByIdAndOwner(hostelId, ownerId)
                       .map(hostel -> new HostelDto(
                               hostel.getId(),
                               hostel.getName(),
                               hostel.getTenants()
                                       .stream()
                                       .map(tenant -> new TenantDto(
                                               tenant.getId(),
                                               tenant.getName()
                                       ))
                                       .toList()
                       ))
                       .orElseThrow(HostelNotFoundException::new);
    }
}
