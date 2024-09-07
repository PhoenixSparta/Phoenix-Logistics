package com.phoenix.logistics.storage.db.core.user;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.phoenix.logistics.core.enums.RoleType;
import com.phoenix.logistics.storage.db.core.user.entity.User;
import com.phoenix.logistics.storage.db.core.user.repository.UserRepository;
import java.lang.reflect.Constructor;
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
        User user = new User("testuser", // username
                "Test1234!", // password
                RoleType.MASTER_ADMIN // role
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
        User user = new User("testuser2", "Password123!", RoleType.HUB_MANAGER);
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
    public void testProtectedConstructor() throws NoSuchMethodException {
        // User 클래스의 기본 생성자를 가져옴
        Constructor<User> constructor = User.class.getDeclaredConstructor();

        // 기본 생성자가 protected인지 확인
        // 리플렉션을 통해 기본 생성자를 호출하려고 시도
        assertThrows(IllegalAccessException.class, constructor::newInstance);
    }

}
