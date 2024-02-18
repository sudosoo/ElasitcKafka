package com.sudosoo.takeiteasy.controller;

import com.sudosoo.takeiteasy.dto.member.CreateMemberRequestDto;
import com.sudosoo.takeiteasy.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @PostMapping(value = "/create" , name = "createdMember")
    public ResponseEntity<Void> createdMember(@Valid @RequestBody CreateMemberRequestDto requestDto) {

        memberService.createMember(requestDto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/disable" , name = "deletedMember")
    public ResponseEntity<Void> disableMember(@RequestParam(name = "id") Long id) {

        memberService.disableByMemberId(id);

        return ResponseEntity.ok().build();
    }
}