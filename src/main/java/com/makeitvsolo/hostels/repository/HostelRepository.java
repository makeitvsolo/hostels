package com.makeitvsolo.hostels.repository;

import com.makeitvsolo.hostels.model.Hostel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HostelRepository extends JpaRepository<Hostel, Long> {

    @Query(nativeQuery = true, value = """
            SELECT h.id, h.name, h.owner_id
            FROM hostels h
            WHERE h.id = :id AND h.owner_id = :ownerId
            """)
    Optional<Hostel> findByIdAndOwnerId(Long id, Long ownerId);

    Optional<Hostel> findByName(String name);
    boolean existsByName(String name);

    @Query(nativeQuery = true, value = """
            SELECT h.id, h.name, h.owner_id
            FROM hostels h
            WHERE h.owner_id = :ownerId
            """)
    List<Hostel> findAllByOwnerId(Long ownerId);
}
