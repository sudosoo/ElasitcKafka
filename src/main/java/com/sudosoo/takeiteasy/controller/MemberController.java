package com.sudosoo.takeiteasy.controller;

import com.sudosoo.takeiteasy.dto.CreateCategoryRequestDto;
import com.sudosoo.takeiteasy.dto.CreateMemberRequestDto;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.service.CategoryService;
import com.sudosoo.takeiteasy.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;
    @PostMapping( "/member")
    public ResponseEntity<Void> createdMember(@Valid @RequestBody CreateMemberRequestDto requestDto) {
        Member member = memberService.createdMember(requestDto);
        log.info("New Member created : memberName{}", member.getMemberName());
        return ResponseEntity.ok().build();
    }


}