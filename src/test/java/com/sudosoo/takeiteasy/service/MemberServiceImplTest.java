package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.member.CreateMemberRequestDto;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MemberServiceImplTest {
    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private MemberServiceImpl memberService;
    private CreateMemberRequestDto createMemberRequestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        createMemberRequestDto = new CreateMemberRequestDto("TestMember");
     }


    @Test
    @DisplayName("createdMember")
    void createdMember() {
        //given
        Member member = Member.of(createMemberRequestDto);
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        //when
        Member ActualMember = memberService.createdMember(createMemberRequestDto);

        //then
        String expectedMemberName = createMemberRequestDto.getMemberName();
        String actualMemberName = ActualMember.getMemberName();

        assertNotNull(member, "The actual member should not be null");
        assertEquals(expectedMemberName, actualMemberName, "Expected Member Name: " + expectedMemberName + ", Actual Member Name: " + actualMemberName);
    }

    @Test
    @DisplayName("getMemberByMemberId")
    void getMemberByMemberId() {
        // given
        when(memberRepository.findById(any())).thenReturn(Optional.of(Member.of(createMemberRequestDto)));

        // when
        Member member = memberService.getMemberByMemberId(1L);

        // then
        String expectedMemberName = createMemberRequestDto.getMemberName();
        String actualMemberName = member.getMemberName();

        assertNotNull(member, "The actual member should not be null");
        assertEquals(expectedMemberName, actualMemberName, "Expected Member Name: " + expectedMemberName + ", Actual Member Name: " + actualMemberName);
    }
}