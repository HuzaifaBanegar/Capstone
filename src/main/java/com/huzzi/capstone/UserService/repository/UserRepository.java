package com.huzzi.capstone.UserService.repository;

import com.huzzi.capstone.UserService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);
    Optional<User> findByIdAndIsDeletedFalse(Long id);
    List<User> findAllByIsDeletedFalse();
}
