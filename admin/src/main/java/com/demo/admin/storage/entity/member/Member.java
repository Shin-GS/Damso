package com.demo.admin.storage.entity.member;

import com.demo.admin.core.constant.MemberRoleType;
import com.demo.admin.storage.entity.CommonTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member extends CommonTime {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_email", unique = true)
    private String email;

    @Column(name = "member_name")
    private String name;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private MemberRole role;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MemberNotificationSetting> notificationSettings = new LinkedHashSet<>();

    public Member(String email,
                  String name,
                  MemberRoleType memberRole) {
        this.email = email;
        this.name = name;
        this.role = new MemberRole(this, memberRole);
    }
}
