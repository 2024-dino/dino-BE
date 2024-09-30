package khu.dino.member.persistence;



import jakarta.persistence.*;
import khu.dino.common.base.BaseEntity;
import khu.dino.event.persistence.Event;
import khu.dino.member.persistence.enums.MemberStatus;
import khu.dino.member.persistence.enums.OAuth2Provider;
import khu.dino.member.persistence.enums.UserRole;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member")
@Builder
public  class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String socialId; //OAuth2 고유 식별자

    @Column(nullable = false)
    private String nickname;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OAuth2Provider oAuth2Provider;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserRole userRole = UserRole.ROLE_USER;

    @OneToMany(mappedBy = "creator")
    @ToString.Exclude
    private List<Event> eventList = new ArrayList<>();


    public void ChangeNickname(String nickname){
        this.nickname = nickname;
    }

}
