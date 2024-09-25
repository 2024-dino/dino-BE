package khu.dino.member.persistence;

import jakarta.persistence.*;
import khu.dino.common.base.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Builder
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Member member;

    private String token;

    public FcmToken(final String token, final Member member) {
        this.token = token;
        this.member = member;
    }

    public void update(final String token) {
        this.token = token;
    }
}
