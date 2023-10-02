package com.makeitvsolo.hostels.service;

import com.makeitvsolo.hostels.service.dto.hostel.HostelDto;
import com.makeitvsolo.hostels.service.dto.hostel.TenantDto;
import com.makeitvsolo.hostels.service.exception.hostel.HostelNotFoundException;
import com.makeitvsolo.hostels.service.exception.member.MemberNotFoundException;
import com.makeitvsolo.hostels.service.exception.hostel.TenantAlreadyExistsException;
import com.makeitvsolo.hostels.service.exception.hostel.TenantNotFoundException;
import com.makeitvsolo.hostels.model.Hostel;
import com.makeitvsolo.hostels.model.Member;
import com.makeitvsolo.hostels.repository.HostelRepository;
import com.makeitvsolo.hostels.repository.MemberRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.*;
import static org.mockito.Mockito.*;

@DisplayName("TenantService")
public class TenantServiceTests {

    private TenantService service;

    @Mock
    private HostelRepository hostelRepository;

    @Mock
    private MemberRepository memberRepository;

    private AutoCloseable closeable;

    @BeforeEach
    public void beforeEach() {
        closeable = openMocks(this);

        service = new TenantService(hostelRepository, memberRepository);
    }

    @AfterEach
    public void afterEach() throws Exception {
        closeable.close();
    }

    @Nested
    @DisplayName("adds tenant")
    public class AddsTenant {

        @Mock
        private Hostel hostel;

        @Mock
        private Member hostelOwner;

        @Mock
        private Member existingMember;

        private AutoCloseable closeable;

        @BeforeEach
        public void beforeEach() {
            closeable = openMocks(this);
        }

        @AfterEach
        public void afterEach() throws Exception {
            closeable.close();
        }

        @Test
        @DisplayName("saves changes when hostel and member exists and member isn't in tenants")
        public void savesChangesWhenHostelAndTenantExistsAndMemberIsNotInTenants() {
            var ownerId = 0L;
            var hostelId = 0L;
            var memberName = "member name";

            when(hostelRepository.findByIdAndOwner(hostelId, ownerId))
                    .thenReturn(Optional.of(hostel));
            when(memberRepository.findByName(memberName))
                    .thenReturn(Optional.of(existingMember));
            when(hostel.addTenant(existingMember))
                    .thenReturn(true);

            service.addTenant(ownerId, hostelId, memberName);

            verify(hostelRepository).save(hostel);
        }

        @Test
        @DisplayName("or throws when hostel doesn't exists")
        public void orThrowsWhenHostelDoesNotExists() {
            var ownerId = 0L;
            var hostelId = 0L;
            var memberName = "member name";

            when(hostelRepository.findByIdAndOwner(hostelId, ownerId))
                    .thenReturn(Optional.empty());

            assertThrows(HostelNotFoundException.class, () -> service.addTenant(ownerId, hostelId, memberName));
        }

        @Test
        @DisplayName("or throws when member doesn't exists")
        public void orThrowsWhenMemberDoesNotExists() {
            var ownerId = 0L;
            var hostelId = 0L;
            var memberName = "member name";

            when(hostelRepository.findByIdAndOwner(hostelId, ownerId))
                    .thenReturn(Optional.of(hostel));
            when(memberRepository.findByName(memberName))
                    .thenReturn(Optional.empty());

            assertThrows(MemberNotFoundException.class, () -> service.addTenant(ownerId, hostelId, memberName));
        }

        @Test
        @DisplayName("or throws when member already in tenants")
        public void orThrowsWhenMemberAlreadyInTenants() {
            var ownerId = 0L;
            var hostelId = 0L;
            var memberName = "member name";

            when(hostelRepository.findByIdAndOwner(hostelId, ownerId))
                    .thenReturn(Optional.of(hostel));
            when(memberRepository.findByName(memberName))
                    .thenReturn(Optional.of(existingMember));
            when(hostel.addTenant(existingMember))
                    .thenReturn(false);

            assertThrows(TenantAlreadyExistsException.class, () -> service.addTenant(ownerId, hostelId, memberName));
        }
    }

    @Nested
    @DisplayName("removes tenant")
    public class RemovesTenant {

        @Mock
        private Hostel hostel;

        @Mock
        private Member hostelOwner;

        @Mock
        private Member existingMember;

        private AutoCloseable closeable;

        @BeforeEach
        public void beforeEach() {
            closeable = openMocks(this);
        }

        @AfterEach
        public void afterEach() throws Exception {
            closeable.close();
        }

        @Test
        @DisplayName("saves changes when hostel exists and member is in tenants")
        public void savesChangesWhenHostelExistsAndMemberIsInTenants() {
            var ownerId = 0L;
            var hostelId = 0L;
            var tenantId = 1L;

            when(hostelRepository.findByIdAndOwner(hostelId, ownerId))
                    .thenReturn(Optional.of(hostel));
            when(hostel.removeTenant(tenantId))
                    .thenReturn(true);

            service.removeTenant(ownerId, hostelId, tenantId);

            verify(hostelRepository).save(hostel);
        }

        @Test
        @DisplayName("or throws when hostel doesn't exists")
        public void orThrowsWhenHostelDoesNotExists() {
            var ownerId = 0L;
            var hostelId = 0L;
            var tenantId = 1L;

            when(hostelRepository.findByIdAndOwner(hostelId, ownerId))
                    .thenReturn(Optional.empty());

            assertThrows(HostelNotFoundException.class, () -> service.removeTenant(ownerId, hostelId, tenantId));
        }

        @Test
        @DisplayName("or throws when member isn't in tenants")
        public void orThrowsWhenMemberIsNotInTenants() {
            var ownerId = 0L;
            var hostelId = 0L;
            var tenantId = 1L;

            when(hostelRepository.findByIdAndOwner(hostelId, ownerId))
                    .thenReturn(Optional.of(hostel));
            when(hostel.removeTenant(tenantId))
                    .thenReturn(false);

            assertThrows(TenantNotFoundException.class, () -> service.removeTenant(ownerId, hostelId, tenantId));
        }
    }

    @Nested
    @DisplayName("all tenants")
    public class AllTenants {

        private Hostel hostel;

        private Member hostelOwner;

        private Member existingTenant;

        @BeforeEach
        public void beforeEach() {
            hostelOwner = new Member();
            hostelOwner.setId(0L);
            hostelOwner.setName("hostel owner");

            existingTenant = new Member();
            existingTenant.setId(1L);
            existingTenant.setName("name");

            hostel = new Hostel();
            hostel.setId(0L);
            hostel.setName("hostel name");
            hostel.setOwner(hostelOwner);
            hostel.setTenants(Set.of(existingTenant));
        }

        @Test
        @DisplayName("gets all tenants when hostel exists")
        public void getsAllTenantsWhenHostelExists() {
            var ownerId = 0L;
            var hostelId = 0L;

            var expected = new HostelDto(
                    hostel.getId(),
                    hostel.getName(),
                    List.of(new TenantDto(existingTenant.getId(), existingTenant.getName()))
            );

            when(hostelRepository.findByIdAndOwner(hostelId, ownerId))
                    .thenReturn(Optional.of(hostel));

            assertEquals(expected, service.getAllTenants(ownerId, hostelId));
        }

        @Test
        @DisplayName("or throws when hostel doesn't exists")
        public void orThrowsWhenHostelDoesNotExists() {
            var ownerId = 0L;
            var hostelId = 0L;

            when(hostelRepository.findByIdAndOwner(hostelId, ownerId))
                    .thenReturn(Optional.empty());

            assertThrows(HostelNotFoundException.class, () -> service.getAllTenants(ownerId, hostelId));
        }
    }
}
