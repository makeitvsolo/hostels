package com.makeitvsolo.hostels.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Hostel")
public class HostelTests {

    private Hostel hostel;
    private Member owner;

    @BeforeEach
    public void beforeEach() {
        owner = new Member();

        owner.setId(0L);
        owner.setName("name");
    }

    @Test
    @DisplayName("has no tenants after creation")
    public void hasNoTenantsAfterCreation() {
        hostel = new Hostel("hostel name", owner);

        assertTrue(hostel.getTenants().isEmpty());
    }

    @Nested
    @DisplayName("when has no tenants")
    public class WhenHasNoTenants {

        @BeforeEach
        public void beforeEach() {
            hostel = new Hostel("hostel name", owner);
        }

        @Test
        @DisplayName("tenant can be added")
        public void tenantCanBeAdded() {
            var tenant = new Member();
            tenant.setId(1L);
            tenant.setName("tenant name");

            assertTrue(hostel.addTenant(tenant));
        }

        @Test
        @DisplayName("no tenant can be removed")
        public void noTenantCanBeRemoved() {
            var tenantId = 1L;

            assertFalse(hostel.removeTenant(tenantId));
        }

        @Nested
        @DisplayName("after adding a tenant")
        public class AfterAddingTenant {

            private Member existingTenant;

            @BeforeEach
            public void beforeEach() {
                existingTenant = new Member();
                existingTenant.setId(1L);
                existingTenant.setName("existing tenant");

                hostel.addTenant(existingTenant);
            }

            @Test
            @DisplayName("tenant can be removed")
            public void tenantCanBeRemoved() {
                assertTrue(hostel.removeTenant(existingTenant.getId()));
            }

            @Test
            @DisplayName("tenant cannot be added again")
            public void tenantCannotBeAddedAgain() {
                assertFalse(hostel.addTenant(existingTenant));
            }
        }
    }
}
