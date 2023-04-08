package com.challenge.studytime.domain.refreshToken.service;

import com.challenge.studytime.domain.member.dto.response.MemberLoginResponseDto;
import com.challenge.studytime.domain.member.entity.Member;
import com.challenge.studytime.domain.member.repositry.MemberRepositry;
import com.challenge.studytime.domain.refreshToken.dto.request.RefreshTokenDto;
import com.challenge.studytime.domain.refreshToken.entity.RefreshToken;
import com.challenge.studytime.domain.refreshToken.repository.RefreshTokenRepositry;
import com.challenge.studytime.global.exception.member.NotFoundMemberEmail;
import com.challenge.studytime.global.exception.member.NotFoundMemberid;
import com.challenge.studytime.global.jwt.util.JwtTokenizer;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepositry refreshTokenRepository;
    private final JwtTokenizer jwtTokenizer;
    private final MemberRepositry memberRepository;

    @Transactional
    public RefreshToken addRefreshToken(String refreshToken, Member member) {

        RefreshToken token = RefreshToken.builder()
                .value(refreshToken)
                .memberId(member.getId())
                .build();

        return refreshTokenRepository.save(token);
    }

    @Transactional
    public void deleteRefreshToken(String refreshToken) {
        refreshTokenRepository.findByValue(refreshToken)
                .ifPresent(refreshTokenRepository::delete);
    }

    @Transactional(readOnly = true)
    public MemberLoginResponseDto findRefreshToken(RefreshTokenDto refreshTokenDto) {

        RefreshToken refreshToken = refreshTokenRepository.findByValue(refreshTokenDto.getRefreshToken())
                .orElseThrow(() -> new NotFoundMemberEmail(refreshTokenDto.getRefreshToken()));

        Claims claims = jwtTokenizer.parseRefreshToken(refreshToken.getValue());
        Long userId = Long.valueOf((Integer) claims.get("userId"));

        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new NotFoundMemberid(userId));

        List roles = (List) claims.get("roles");
        String email = claims.getSubject();
        String accessToken = jwtTokenizer.createAccessToken(userId, email, roles);

        return MemberLoginResponseDto.toDto(member, accessToken, refreshTokenDto.getRefreshToken());
    }
}