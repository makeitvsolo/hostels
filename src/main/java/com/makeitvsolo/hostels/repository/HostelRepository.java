package com.makeitvsolo.hostels.repository;

import com.makeitvsolo.hostels.model.Hostel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HostelRepository extends JpaRepository<Hostel, Long> {

    Optional<Hostel> findByIdAndOwner(Long id, Long ownerId);

    Optional<Hostel> findByName(String name);
    boolean existsByName(String name);

    List<Hostel> findAllByOwner(Long ownerId);
}
