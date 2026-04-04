package com.huzzi.capstone.AuthService.service;

import com.huzzi.capstone.AuthService.dto.LoginResponseDto;
import com.huzzi.capstone.AuthService.errorhandler.AuthException;
import com.huzzi.capstone.AuthService.modal.Auth;
import com.huzzi.capstone.AuthService.modal.Token;
import com.huzzi.capstone.AuthService.repository.AuthRepository;
import com.huzzi.capstone.UserService.model.Role;
import com.huzzi.capstone.UserService.model.User;
import com.huzzi.capstone.UserService.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service("dbAuthService")
public class DbAuthService implements AuthService {
    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public DbAuthService(UserRepository userRepository, AuthRepository authRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginResponseDto auth(Auth userAuth) {
        Optional<User> user = userRepository.findByUsernameAndIsDeletedFalse(userAuth.getUsername());
        if (user.isEmpty() || !passwordEncoder.matches(userAuth.getPassword(), user.get().getPassword())) {
            throw new AuthException("Invalid username or password", 401);
        }

        Token token = new Token();
        token.setValue(UUID.randomUUID().toString());
        token.setUser(user.get());
        token.setExpiryDate(LocalDateTime.now().plusDays(7));
        authRepository.save(token);

        LoginResponseDto responseDto = new LoginResponseDto();
        responseDto.setAccessToken(token.getValue());
        responseDto.setUser(sanitizeUser(user.get()));
        return responseDto;
    }

    @Override
    public User signUp(String name, String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setUsername(name);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.USER);

        return userRepository.save(user);
    }

    @Override
    public User validateToken(String tokenValue) {
        Optional<Token> token = authRepository.findByValueAndExpiryDateGreaterThan(tokenValue, LocalDateTime.now());
        return token.map(Token::getUser).orElse(null);
    }

    @Override
    public void logout(String tokenValue) {
        authRepository.findByValueAndExpiryDateGreaterThan(tokenValue, LocalDateTime.now())
                .ifPresent(authRepository::delete);
    }

    private User sanitizeUser(User user) {
        User sanitizedUser = new User();
        sanitizedUser.setId(user.getId());
        sanitizedUser.setEmail(user.getEmail());
        sanitizedUser.setUsername(user.getUsername());
        sanitizedUser.setRole(user.getRole());
        sanitizedUser.setCreated_at(user.getCreated_at());
        sanitizedUser.setUpdated_at(user.getUpdated_at());
        sanitizedUser.setIsDeleted(user.getIsDeleted());
        return sanitizedUser;
    }
}
