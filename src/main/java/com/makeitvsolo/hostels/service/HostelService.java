package com.makeitvsolo.hostels.service;

import com.makeitvsolo.hostels.dto.HostelItemDto;
import com.makeitvsolo.hostels.exception.HostelAlreadyExistsException;
import com.makeitvsolo.hostels.exception.MemberAlreadyExistsException;
import com.makeitvsolo.hostels.exception.MemberNotFoundException;
import com.makeitvsolo.hostels.model.Hostel;
import com.makeitvsolo.hostels.repository.HostelRepository;
import com.makeitvsolo.hostels.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class HostelService {

    private final HostelRepository hostelRepository;
    private final MemberRepository memberRepository;

    public HostelService(HostelRepository hostelRepository, MemberRepository memberRepository) {
        this.hostelRepository = hostelRepository;
        this.memberRepository = memberRepository;
    }

    public void create(Long ownerId, String name) {
        var owner = memberRepository.findById(ownerId)
                             .orElseThrow(MemberNotFoundException::new);

        if (hostelRepository.existsByName(name)) {
            throw new HostelAlreadyExistsException(name);
        }

        var hostel = new Hostel(name, owner);

        hostelRepository.save(hostel);
    }

    public List<HostelItemDto> getAll(Long ownerId) {
        if (!memberRepository.existsById(ownerId)) {
            throw new MemberNotFoundException();
        }

        return hostelRepository.findAllByOwner(ownerId)
                       .stream()
                       .map(hostel -> new HostelItemDto(
                               hostel.getId(),
                               hostel.getName(),
                               hostel.getOwner().getId()
                       ))
                       .toList();
    }
}
