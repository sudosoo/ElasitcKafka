package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.member.CreateMemberRequestDto;
import com.sudosoo.takeiteasy.entity.Member;

public interface MemberService {
    Member createdMember(CreateMemberRequestDto createMemberRequestDto);
    Member getMemberByMemberId(Long memberId);
    Member getMemberByMemberName(String targetMemberName);
}
