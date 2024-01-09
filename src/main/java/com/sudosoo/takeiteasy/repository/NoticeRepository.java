package com.sudosoo.takeiteasy.repository;

import com.sudosoo.takeiteasy.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice,Long> {
}
