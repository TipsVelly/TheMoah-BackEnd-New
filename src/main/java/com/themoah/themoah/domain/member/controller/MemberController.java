package com.themoah.themoah.domain.member.controller;

import com.themoah.themoah.common.config.annotations.RestJSONController;
import com.themoah.themoah.common.dto.TokenDto;
import com.themoah.themoah.common.security.TokenProvider;
import com.themoah.themoah.common.util.niceid.NiceIdVerification;
import com.themoah.themoah.common.util.niceid.dto.NiceIdEncData;
import com.themoah.themoah.domain.member.dto.LoginDto;
import com.themoah.themoah.domain.member.dto.RequestMemberDto;
import com.themoah.themoah.domain.member.dto.RequestTokenDto;
import com.themoah.themoah.domain.member.dto.ResponseMemberDto;
import com.themoah.themoah.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

@RestJSONController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final MemberService memberService;

    @PostMapping("/login")
    public Map<String, Object> Login(@RequestBody LoginDto user) {

        String userId = (String)user.getUserId();
        String password = (String)user.getPassword();
        TokenDto tokenObject = null;
        Map<String, Object> map = new HashMap<>();

        /*	로그인 정보(id, pwd)를 읽기 */
        // 2. 로그인 아이디와 비밀번호로 해당 유저의 정보를 조회하고 토큰을 가져옴. 입력한 로그인 정보로 토큰을 생성
        //    --> 인증되지 않은 Authentication객체

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userId, password);

        // 3. 로그인 정보를 db와 비교하여 인증처리함. --> 위의 토큰을 이용하여, 토큰 내부의 정보와 db의 정보를 비교해서 인증여부 체크
        //	  인증되지 않은 Authentication객체 --> (인증 비교) --> 인증된 Authentication객체 반환
        // authenticate 메소드가 실행이 될 때 CustomUserDetailsService class의 loadUserByUsername 메소드가 실행

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 4. 모든 인증 처리를 완료했으면, 데이터 저장으로 넘어간다.
        // 해당 객체를 SecurityContextHolder에 저장하고 --> session 역할
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // authentication 객체를 createToken 메소드를 통해서 JWT Token을 생성 --> 인증한 authentication를 token으로 반환
        tokenObject = tokenProvider.generateToken(authentication);

        map.put("token", tokenObject.getAccessToken());

        return map;
    }

    /**
     * 비밀번호 초기화
     * @param user
     * @return
     */
    @PostMapping("/initPassword")
    public Map<String, Object> initPassword(@RequestBody LoginDto user) {
        String userId = user.getUserId();
        boolean rst = memberService.initPassword(userId);
        Map<String, Object> map = new HashMap<>();
        map.put("result", rst);

        return map;
    }

    /**
     * 토큰을 확인 후 사용자 정보로 내려주는 컨트롤러
     * @param req
     * @return
     */
    @PostMapping("/verify")
    public ResponseMemberDto verify(@RequestBody RequestTokenDto req) {
        String token = req.getToken();

        return memberService.verify(token);
    }
    
    /**
     * 멤버 회원가입(추가) 컨트롤러
     * @param member
     * @return
     */

    @PostMapping("/signup")
    public Map<String, Boolean> signup(@RequestBody RequestMemberDto member) {
        boolean rst = memberService.addMember(member);
        return Map.of("ifSignup", rst);
    }

    @GetMapping("/getNiceIdEncData")
    public NiceIdEncData getNiceIdEncData(@RequestParam(name = "returnURL", required = false) String returnURL) {
        String clientId = NiceIdVerification.CLIENT_ID;
        String clientSecret = NiceIdVerification.CLIENT_SECRET;
        String productId = NiceIdVerification.PRODUCT_ID;

        return memberService.getNiceIdEncData(clientId, clientSecret, productId, returnURL);
    }

    @RequestMapping(value = "/acceptNiceIdResult", method = {RequestMethod.POST, RequestMethod.GET})
    public RedirectView acceptNiceIdResult(@RequestParam("token_version_id") String tokenVersionId, @RequestParam("enc_data") String encData, @RequestParam("integrity_value") String integrityValue) {


        boolean status = false;
        return new RedirectView("http://localhost:3031/view/outer/niceIdResult.html");
    }

    /**
     * 멤버 컨트롤러에서 생기는 권한/인가 에러에 대한 예외 핸들러
     * @param e
     * @return
     */

    @ExceptionHandler({AuthenticationException.class})
    public Map<String, Object> LoginExcetionHandler(AuthenticationException e) {
        return Map.of("error", e.getMessage());
    }
}