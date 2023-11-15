package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.CreateMemberRequestDto;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MemberServiceImplTest {
    MemberRepository memberRepository = mock(MemberRepository.class);
    MemberServiceImpl memberService = new MemberServiceImpl(memberRepository);
    CreateMemberRequestDto createPostRequestDto = new CreateMemberRequestDto("TestMember");


    @Test
    @DisplayName("createMember")
    void createMember() {
        //Given
        when(memberRepository.save(any(Member.class))).thenReturn(Member.getInstance(createPostRequestDto));
        // When
        Member member = memberService.createMember(createPostRequestDto);

        // Then
        String expectedMemberName = createPostRequestDto.getMemberName();
        String actualMemberName = member.getMemberName();

        assertNotNull(member, "The actual member should not be null");
        assertEquals(expectedMemberName, actualMemberName, "Expected Member Name: " + expectedMemberName + ", Actual Member Name: " + actualMemberName);
    }

    @Test
    @DisplayName("getMemberByMemberId")
    void getMemberByMemberId() {
        // Given
        when(memberRepository.findById(any())).thenReturn(Optional.of(Member.getInstance(createPostRequestDto)));

        // When
        Member member = memberService.getMemberByMemberId(1L);

        // Then
        String expectedMemberName = createPostRequestDto.getMemberName();
        String actualMemberName = member.getMemberName();

        assertNotNull(member, "The actual member should not be null");
        assertEquals(expectedMemberName, actualMemberName, "Expected Member Name: " + expectedMemberName + ", Actual Member Name: " + actualMemberName);
    }
}