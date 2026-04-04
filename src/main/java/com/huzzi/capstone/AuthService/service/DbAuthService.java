package com.huzzi.capstone.AuthService.service;

import com.huzzi.capstone.AuthService.dto.AuthResponseDto;
import com.huzzi.capstone.AuthService.dto.SignoutResponseDto;
import com.huzzi.capstone.AuthService.errorhandler.AuthException;
import com.huzzi.capstone.AuthService.modal.Auth;
import com.huzzi.capstone.UserService.model.User;
import com.huzzi.capstone.UserService.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service("dbAuthService")
public class DbAuthService implements AuthService {
    private final UserRepository userRepository;
    private final ConcurrentMap<String, Long> activeTokens = new ConcurrentHashMap<>();

    public DbAuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AuthResponseDto login(Auth userAuth) {
        Optional<User> user = userRepository.findByUsernameAndIsDeletedFalse(userAuth.getUsername());
        if (user.isEmpty() || user.get().getPassword() == null || !user.get().getPassword().equals(userAuth.getPassword())) {
            throw new AuthException("Invalid username or password", 401);
        }

        String accessToken = UUID.randomUUID().toString();
        activeTokens.put(accessToken, user.get().getId());

        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setAccessToken(accessToken);
        authResponseDto.setUser(sanitizeUser(user.get()));
        return authResponseDto;
    }

    @Override
    public SignoutResponseDto signout(String accessToken) {
        if (accessToken == null || accessToken.isBlank()) {
            throw new AuthException("Access token is required", 400);
        }

        Long removedUserId = activeTokens.remove(accessToken);
        if (removedUserId == null) {
            throw new AuthException("Invalid access token", 401);
        }

        SignoutResponseDto responseDto = new SignoutResponseDto();
        responseDto.setMessage("Signed out successfully");
        return responseDto;
    }

    private User sanitizeUser(User user) {
        User sanitizedUser = new User();
        sanitizedUser.setId(user.getId());
        sanitizedUser.setName(user.getName());
        sanitizedUser.setEmail(user.getEmail());
        sanitizedUser.setUsername(user.getUsername());
        sanitizedUser.setAddress(user.getAddress());
        sanitizedUser.setPhone(user.getPhone());
        sanitizedUser.setCreated_at(user.getCreated_at());
        sanitizedUser.setUpdated_at(user.getUpdated_at());
        sanitizedUser.setIsDeleted(user.getIsDeleted());
        return sanitizedUser;
    }
}
