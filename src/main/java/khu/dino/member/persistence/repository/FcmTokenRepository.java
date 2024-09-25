package khu.dino.member.persistence.repository;

import khu.dino.member.persistence.FcmToken;
import khu.dino.member.persistence.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {
    Optional<FcmToken> findByMember(Member member);

    void deleteByMemberAndToken(Member member, String token);

    void deleteAllByMember(Member member);

    void deleteByMember(Member member);

    boolean existsByMember(Member member);

    boolean existsByMemberAndToken(Member member, String token);

//    List<FcmToken> findAllByUser(User user);
}
