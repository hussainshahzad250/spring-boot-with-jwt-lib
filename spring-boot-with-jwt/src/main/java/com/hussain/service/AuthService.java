package com.hussain.service;

import com.hussain.constant.Constant;
import com.hussain.entity.Token;
import com.hussain.entity.User;
import com.hussain.enums.Role;
import com.hussain.exception.BadRequestException;
import com.hussain.repository.TokenRepository;
import com.hussain.repository.UserRepository;
import com.hussain.request.LoginRequest;
import com.hussain.request.RegisterRequest;
import com.hussain.response.LoginResponse;
import com.hussain.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements Constant {

    private final JwtService jwtService;
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public LoginResponse register(RegisterRequest request) {
        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        String jwtToken = jwtService.generateToken(user);
        saveUserToken(user, jwtToken);
        return LoginResponse.builder().token(jwtToken).build();
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder().user(user).token(jwtToken).expired(false).revoked(false).build();
        tokenRepository.save(token);
    }

    public Response authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Optional<User> optionalUser = repository.findByEmail(request.getEmail());
        if (optionalUser.isEmpty()) {
            return new Response(NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        User user = optionalUser.get();
        String jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return new Response(SUCCESS, LoginResponse.builder().token(jwtToken).build(), HttpStatus.OK);
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (!validUserTokens.isEmpty()) {
            validUserTokens.forEach(token -> {
                token.setExpired(true);
                token.setRevoked(true);
            });
            tokenRepository.saveAll(validUserTokens);
        }
    }
}
