package com.phoenix.logistics.storage.db.core.user.repository;

import com.phoenix.logistics.storage.db.core.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);
}
