package com.makeitvsolo.hostels.service;

import com.makeitvsolo.hostels.dto.HostelItemDto;
import com.makeitvsolo.hostels.service.exception.hostel.HostelAlreadyExistsException;
import com.makeitvsolo.hostels.service.exception.member.MemberNotFoundException;
import com.makeitvsolo.hostels.model.Hostel;
import com.makeitvsolo.hostels.model.Member;
import com.makeitvsolo.hostels.repository.HostelRepository;
import com.makeitvsolo.hostels.repository.MemberRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.*;
import static org.mockito.Mockito.*;

@DisplayName("HostelService")
public class HostelServiceTests {

    private HostelService service;

    @Mock
    private HostelRepository hostelRepository;

    @Mock
    private MemberRepository memberRepository;

    private AutoCloseable closeable;

    @BeforeEach
    public void beforeEach() {
        closeable = openMocks(this);

        service = new HostelService(hostelRepository, memberRepository);
    }

    @AfterEach
    public void afterEach() throws Exception {
        closeable.close();
    }

    @Nested
    @DisplayName("creates hostel")
    public class CreatesHostel {

        private Member owner;
        private Hostel existingHostel;

        @BeforeEach
        public void beforeEach() {
            owner = new Member();
            owner.setId(0L);
            owner.setName("name");

            existingHostel = new Hostel();
            existingHostel.setId(0L);
            existingHostel.setName("name");
            existingHostel.setOwner(owner);
        }

        @Test
        @DisplayName("saves created hostel when owner exists and hostel name is unique")
        public void savesCreatedHostelWhenOwnerExistsAndHostelNameIsUnique() {
            var hostelName = "name";
            var ownerId = 0L;

            when(hostelRepository.existsByName(hostelName))
                    .thenReturn(false);
            when(memberRepository.findById(ownerId))
                    .thenReturn(Optional.of(owner));

            service.create(ownerId, hostelName);

            verify(hostelRepository).save(any(Hostel.class));
        }

        @Test
        @DisplayName("or throws when owner doesn't exits")
        public void orThrowsWhenOwnerDoesNotExists() {
            var hostelName = "name";
            var ownerId = 0L;

            when(hostelRepository.existsByName(hostelName))
                    .thenReturn(false);
            when(memberRepository.findById(ownerId))
                    .thenReturn(Optional.empty());

            assertThrows(MemberNotFoundException.class, () -> service.create(ownerId, hostelName));
        }

        @Test
        @DisplayName("or throws when name isn't unique")
        public void orThrowsWhenNameIsNotUnique() {
            var hostelName = "name";
            var ownerId = 0L;

            when(hostelRepository.existsByName(hostelName))
                    .thenReturn(true);
            when(memberRepository.findById(ownerId))
                    .thenReturn(Optional.of(owner));

            assertThrows(HostelAlreadyExistsException.class, () -> service.create(ownerId, hostelName));
        }
    }

    @Nested
    @DisplayName("all hostels")
    public class AllHostels {

        private Member owner;
        private Hostel existingHostel;

        @BeforeEach
        public void beforeEach() {
            owner = new Member();
            owner.setId(0L);
            owner.setName("name");

            existingHostel = new Hostel();
            existingHostel.setId(0L);
            existingHostel.setName("name");
            existingHostel.setOwner(owner);
        }

        @Test
        @DisplayName("gets all hostels when owner exists")
        public void getsAllHostelsWhenOwnerExists() {
            var ownerId = 0L;

            var expected = List.of(new HostelItemDto(
                    existingHostel.getId(),
                    existingHostel.getName(),
                    existingHostel.getOwner().getId()
            ));

            when(memberRepository.existsById(ownerId))
                    .thenReturn(true);
            when(hostelRepository.findAllByOwner(ownerId))
                    .thenReturn(List.of(existingHostel));

            assertEquals(expected, service.getAll(ownerId));
        }

        @Test
        @DisplayName("or throws when owner doesn't exists")
        public void orThrowsWhenOwnerDoesNotExists() {
            var ownerId = 0L;

            when(memberRepository.existsById(ownerId))
                    .thenReturn(false);

            assertThrows(MemberNotFoundException.class, () -> service.getAll(ownerId));
        }
    }
}
