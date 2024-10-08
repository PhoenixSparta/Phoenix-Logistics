package com.phoenix.logistics.storage.db.core.user.repository;

import com.phoenix.logistics.storage.db.core.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    Optional<User> findByUserIdAndIsDeleteFalse(Long userId);

    List<User> findAllByIsDeleteFalse();

    List<User> findByUsernameContainingAndIsDeleteFalse(String username);

}
