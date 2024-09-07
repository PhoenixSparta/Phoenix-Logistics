package com.phoenix.logistics.storage.db.core.user;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("local")  // 로컬 환경에서 H2 DB 테스트
class LocalProfileTest {
    @Autowired
    private DataSource dataSource;

    @Test
    void testLocalDataSource() {
        assertNotNull(dataSource);
        // H2 설정이 제대로 되었는지 확인
    }
}
