package khu.dino.domain;



import jakarta.persistence.*;
import khu.dino.domain.base.BaseEntity;
import lombok.*;

@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member")
@Builder
public class Member extends BaseEntity  {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username; //OAuth2 고유 식별자

    @Column(nullable = false)
    private String nickname;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OAuth2Provider oAuth2Provider;


    @Column(nullable = false)
    @Builder.Default
    private String role = "ROLE_USER";




}
