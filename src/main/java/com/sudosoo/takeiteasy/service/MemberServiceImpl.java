package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.member.CreateMemberRequestDto;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    @Override
    public Member createdMember(CreateMemberRequestDto createMemberRequestDto){
        Member member = Member.of(createMemberRequestDto);
        return memberRepository.save(member);
    }

    @Override
    public Member getMemberByMemberId(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Could not found member id :" + memberId));
    }

    @Override
    public Member getMemberByMemberName(String memberName) {
        return memberRepository.findMemberByMemberName(memberName)
                .orElseThrow(() -> new IllegalArgumentException("Could not found member name :" + memberName));
    }

    @Override
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }


}
