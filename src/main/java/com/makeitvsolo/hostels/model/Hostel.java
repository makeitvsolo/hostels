package com.makeitvsolo.hostels.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(
        name = "hostels",
        uniqueConstraints = @UniqueConstraint(name = "only_one_owner", columnNames = {"id", "owner_id"}),
        indexes = @Index(columnList = "name", unique = true))
@NoArgsConstructor
@Setter
public final class Hostel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column(nullable = false, unique = true)
    @Getter
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "owner_id", nullable = false, referencedColumnName = "id")
    @Getter
    private Member owner;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "hostels_tenants",
            joinColumns = @JoinColumn(name = "hostel_id", nullable = false, referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tenant_id", nullable = false, referencedColumnName = "id")
    )
    private Set<Member> tenants;

    public Hostel(String name, Member owner) {
        this.name = name;
        this.owner = owner;

        this.tenants = new HashSet<>();
    }

    public List<Member> getTenants() {
        return tenants.stream()
                       .toList();
    }

    public boolean addTenant(Member tenant) {
        return tenants.add(tenant);
    }

    public boolean removeTenant(Long tenantId) {
        return tenants.removeIf(tenant -> tenantId.equals(tenant.getId()));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Hostel other)) {
            return false;
        }

        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
