package com.damso.repository.db.entity.member;

import com.damso.core.constant.MemberRoleType;
import com.damso.core.constant.MemberStatusType;
import com.damso.repository.db.converter.EmailConverter;
import com.damso.repository.db.entity.CommonTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "Member")
@Getter
@Setter
@NoArgsConstructor
public class Member extends CommonTime {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_email", unique = true, nullable = false)
    @Convert(converter = EmailConverter.class)
    private String email;

    @Column(name = "member_name", nullable = false)
    private String name;

    @Column(name = "member_password", nullable = false)
    private String password;

    @Column(name = "member_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRoleType role;

    @Column(name = "member_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberStatusType status;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MemberNotificationSetting> notificationSettings = new LinkedHashSet<>();

    public Member(String email,
                  String name,
                  MemberRoleType role) {
        this.email = email;
        this.name = name;
        this.role = role;
        this.status = MemberStatusType.ACTIVE;
    }

    public void update(String email,
                       String name,
                       MemberRoleType role,
                       MemberStatusType status) {
        this.email = email;
        this.name = name;
        this.role = role;
        this.status = status;
    }
}
