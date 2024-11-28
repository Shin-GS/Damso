package com.demo.admin.storage.repository.member;

import com.demo.admin.storage.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    boolean existsByEmail(String email);
}
