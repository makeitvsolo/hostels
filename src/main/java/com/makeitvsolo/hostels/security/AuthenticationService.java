package com.makeitvsolo.hostels.security;

import com.makeitvsolo.hostels.dto.AccessTokenDto;
import com.makeitvsolo.hostels.dto.MemberDto;
import com.makeitvsolo.hostels.exception.MemberAlreadyExistsException;
import com.makeitvsolo.hostels.exception.MemberNotFoundException;
import com.makeitvsolo.hostels.exception.PasswordsAreNotMatchesException;
import com.makeitvsolo.hostels.model.Member;
import com.makeitvsolo.hostels.repository.MemberRepository;
import com.makeitvsolo.hostels.security.jwt.JwtProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public final class AuthenticationService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthenticationService(
            MemberRepository memberRepository,
            PasswordEncoder passwordEncoder,
            JwtProvider jwtProvider
    ) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    public void register(MemberDto payload) {
        if (memberRepository.existsByName(payload.getName())) {
            throw new MemberAlreadyExistsException(payload.getName());
        }

        var member = new Member(
                payload.getName(),
                passwordEncoder.encode(payload.getPassword())
        );

        memberRepository.save(member);
    }

    public AccessTokenDto authenticate(MemberDto payload) {
        var member = memberRepository.findByName(payload.getName())
                             .orElseThrow(() -> new MemberNotFoundException(payload.getName()));

        if (passwordEncoder.matches(payload.getPassword(), member.getPassword())) {
            throw new PasswordsAreNotMatchesException();
        }

        return new AccessTokenDto(
                jwtProvider.encode(member.getName())
        );
    }
}
