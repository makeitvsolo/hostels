package com.makeitvsolo.hostels.repository;

import com.makeitvsolo.hostels.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByName(String name);
    boolean existsByName(String name);
}
