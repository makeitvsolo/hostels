package com.makeitvsolo.hostels.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Member")
public class MemberTests {

    private Member member;

    @Test
    @DisplayName("has no hostels and residences after creation")
    public void hasNoHostelsAndResidencesAfterCreation() {
        member = new Member("name", "passwd");

        assertTrue(member.getHostels().isEmpty());
        assertTrue(member.getResidences().isEmpty());
    }
}
