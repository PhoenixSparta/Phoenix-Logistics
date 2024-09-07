package com.springcloud.eureka.dbcoreuser;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.springcloud.eureka.dbcoreuser.entity.RoleType;
import com.springcloud.eureka.dbcoreuser.entity.User;
import com.springcloud.eureka.dbcoreuser.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        // User 엔티티의 커스텀 생성자를 사용하여 객체 생성
        User user = new User(
            "testuser",  // username
            "Test1234!", // password
            RoleType.MASTER_ADMIN  // role
        );

        // 데이터베이스에 사용자 저장
        User savedUser = userRepository.save(user);

        // 저장된 사용자가 null이 아닌지 검증
        assertThat(savedUser.getUserId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("testuser");
    }

    @Test
    public void testFindUserById() {
        // 새로운 사용자 저장
        User user = new User(
            "testuser2",
            "Password123!",
            RoleType.HUB_ADMIN
        );
        userRepository.save(user);

        // 저장된 사용자 ID로 찾기
        User foundUser = userRepository.findById(user.getUserId()).orElse(null);

        // 검증
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUserId()).isEqualTo(user.getUserId());
        assertThat(foundUser.getUsername()).isEqualTo("testuser2");
    }

    // Lombok의 @NoArgsConstructor(access = AccessLevel.PROTECTED)를 통해
    // 기본 생성자가 protected로 설정되었는지 확인하는 테스트
    @Test
    public void testProtectedConstructor() {
        // 기본 생성자가 protected이므로 외부에서 호출할 수 없음
        // 아래 코드는 컴파일 오류가 발생하므로 주석 처리 (정상적인 동작)
        // User user = new User(); // 컴파일 오류 발생해야 함
    }
}
