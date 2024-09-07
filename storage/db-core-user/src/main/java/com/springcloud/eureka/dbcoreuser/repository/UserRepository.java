package com.springcloud.eureka.dbcoreuser.repository;

import com.springcloud.eureka.dbcoreuser.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
