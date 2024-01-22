package com.sudosoo.takeiteasy.controller;

import com.sudosoo.takeiteasy.dto.member.CreateMemberRequestDto;
import com.sudosoo.takeiteasy.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @PostMapping(value = "/create" , name = "createdMember")
    public ResponseEntity<Void> createdMember(@Valid @RequestBody CreateMemberRequestDto requestDto) {

        memberService.createdMember(requestDto);

        return ResponseEntity.ok().build();
    }
}