package com.sudosoo.takeiteasy.repository;

import com.sudosoo.takeiteasy.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {


}
