package com.makeitvsolo.hostels.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "members", indexes = @Index(columnList = "name", unique = true))
@NoArgsConstructor
@Setter
public final class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column(nullable = false, unique = true)
    @Getter
    private String name;

    @Column(nullable = false)
    @Getter
    private String password;

    @OneToMany(mappedBy = "owner")
    private Set<Hostel> hostels;

    @ManyToMany(mappedBy = "tenants")
    private Set<Hostel> residences;

    public Member(String name, String password) {
        this.name = name;
        this.password = password;

        this.hostels = new HashSet<>();
    }

    public List<Hostel> hostels() {
        return hostels.stream()
                       .toList();
    }

    public List<Hostel> residences() {
        return residences.stream()
                       .toList();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Member other)) {
            return false;
        }

        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
