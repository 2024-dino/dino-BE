package khu.dino.member.business;



import khu.dino.common.auth.PrincipalDetails;
import khu.dino.member.implement.MemberQueryAdapter;
import khu.dino.member.persistence.Member;
import khu.dino.member.presentation.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {

    private final MemberQueryAdapter memberQueryAdapter;
    private final MemberMapper memberMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberQueryAdapter.loadUserByUsername(username);
    }


    public MemberResponseDto.MemberResponse validateJwt(PrincipalDetails principalDetails) {
        Member member = memberQueryAdapter.validateJwt(principalDetails);
        return memberMapper.toMemberResponse(member);
    }
}
