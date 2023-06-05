package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.CreateMemberRequestDto;
import com.sudosoo.takeiteasy.entity.Member;

public interface MemberService {
    void createMember(CreateMemberRequestDto createMemberRequestDto);
    Member getMemberByMemberId(Long memberId);
}
