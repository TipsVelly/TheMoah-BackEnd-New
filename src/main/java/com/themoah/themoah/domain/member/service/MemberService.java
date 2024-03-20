package com.themoah.themoah.domain.member.service;

import com.themoah.themoah.common.security.TokenProvider;
import com.themoah.themoah.common.util.niceid.NiceIdVerification;
import com.themoah.themoah.common.util.niceid.dto.NiceIdEncData;
import com.themoah.themoah.common.util.niceid.dto.NiceIdResultData;
import com.themoah.themoah.domain.member.dto.RequestMemberDto;
import com.themoah.themoah.domain.member.dto.ResponseMemberDto;
import com.themoah.themoah.domain.member.entity.Member;
import com.themoah.themoah.domain.member.repository.MemberRepository;
import com.themoah.themoah.domain.verification.niceId.entity.NiceIdKey;
import com.themoah.themoah.domain.verification.niceId.repository.NiceIdKeyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository  memberRepository;
    private final PasswordEncoder   passwordEncoder;
    private final TokenProvider     tokenProvider;
    private final NiceIdKeyRepository niceIdKeyRepository;
    
    @Transactional
    public boolean initPassword(String id) {
        Optional<Member> member = memberRepository.findById(id);
        member.ifPresent(
                m -> m.setPwd(passwordEncoder.encode("1234"))
        );

        return true;
    }

    @Transactional
    public ResponseMemberDto verify(String token) {

        Authentication authentication = tokenProvider.getAuthentication(token);
        Authentication verifiedAuthentication = tokenProvider.authenticate(authentication);

        String userId = tokenProvider.extractUserId(verifiedAuthentication);
        return memberRepository.findById(userId).map(ResponseMemberDto::generate).get();

    }

    /**
     * 암호화된 휴대폰 인증 데이터를 복호화 하는 메서드
     */

    @Transactional
    public NiceIdEncData getNiceIdEncData(String clientId, String clientSecret, String productId, String returnURL) {
        returnURL = StringUtils.hasText(returnURL) ? returnURL : "http://localhost:8024/member/acceptNiceIdResult";
        return NiceIdVerification.getNiceIdEncData(clientId, clientSecret, productId, returnURL, niceIdKeyRepository);
    }

    public String executeDecryptedEncData(String tokenVersionId, String encData) {
        // tokenVersionId로 해당하는 Key값 찾는다.
        Optional<NiceIdKey> niceIdKey = niceIdKeyRepository.findById(tokenVersionId);

        // key를 이용해 복호화 시도 후, 복호화된 데이터를 java vo로 변환한다.
        NiceIdResultData decryptedEncData = NiceIdVerification.getDecryptedEncData(niceIdKey.get().getKey(), encData);

        // 해당 정보를 jwt 토큰으로 만들어 반환한다.
        return NiceIdVerification.generateToken(decryptedEncData);
    }


    @Transactional
    public boolean signupMember(RequestMemberDto pMember) {
        boolean rst = true;
        String authToken = pMember.getAuthToken();
        NiceIdResultData niceIdResultData = NiceIdVerification.decodeToken(authToken);

        if(niceIdResultData == null) {
            throw new RuntimeException("본인인증 정보가 잘못되었습니다.");
        }

        if(memberRepository.existsById(pMember.getMemberId())) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }

        memberRepository.save(Member.builder()
                    .memberId(pMember.getMemberId())
                    .pwd(passwordEncoder.encode(pMember.getPassword()))
                    .memberName(niceIdResultData.getName())
                    .phoneNumber(niceIdResultData.getMobileNo())
                    .authType(niceIdResultData.getAuthType())
                    .address(pMember.getAddress() + "//" + pMember.getAddressDetail())
                .build());
        return rst;
    }

    public Optional<Member> findByMemberId(String memberId) {
        return memberRepository.findById(memberId);
    }
}