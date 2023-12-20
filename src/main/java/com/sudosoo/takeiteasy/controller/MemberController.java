package com.sudosoo.takeiteasy.controller;

import com.sudosoo.takeiteasy.dto.member.CreateMemberRequestDto;
import com.sudosoo.takeiteasy.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @PostMapping(value = "/member" , name = "createdMember")
    public ResponseEntity<Void> createdMember(@Valid @RequestBody CreateMemberRequestDto requestDto) {
        memberService.createdMember(requestDto);
        return ResponseEntity.ok().build();
    }
}