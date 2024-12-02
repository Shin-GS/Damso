package com.damso.admin.storage.entity.member;

import com.damso.admin.core.constant.MemberRoleType;
import com.damso.admin.storage.entity.CommonTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MemberRole extends CommonTime {
    @Id
    @Column(name = "member_role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "member_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRoleType type;

    public MemberRole(Member member,
                      MemberRoleType type) {
        this.member = member;
        this.type = type;
    }
}
