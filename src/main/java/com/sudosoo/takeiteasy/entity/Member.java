package com.sudosoo.takeiteasy.entity;

import com.sudosoo.takeiteasy.common.BaseEntity;
import com.sudosoo.takeiteasy.dto.member.CreateMemberRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member  extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String memberName;

    private Member(CreateMemberRequestDto createMemberRequestDto) {
        this.memberName = createMemberRequestDto.getMemberName();
    }
    public static Member of(CreateMemberRequestDto createMemberRequestDto){
        return new Member(createMemberRequestDto);
    }

}
