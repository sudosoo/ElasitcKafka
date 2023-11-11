package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.CreateMemberRequestDto;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    public void createMember(CreateMemberRequestDto createMemberRequestDto){
        Member member = new Member(createMemberRequestDto.getMemberName());
        memberRepository.save(member);
        log.info("New Member created : memberName{}", member.getUserName());
    }

    public Member getMemberByMemberId(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Could not found member id :"+memberId));
    }
}
