package com.phoenix.logistics.db.core.redis;

import com.phoenix.logistics.db.core.redis.service.RedisServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class RedisServiceImplTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @InjectMocks
    private RedisServiceImpl redisService;

    @BeforeEach
    void setUp() {
        // Mockito 초기화
        MockitoAnnotations.openMocks(this);

        // RedisTemplate이 valueOperations를 반환하도록 설정
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void testSetValue() {
        String key = "testKey";
        String value = "testValue";

        // 메서드 실행
        redisService.setValue(key, value);

        // set 메서드가 한 번 호출되었는지 확인
        verify(valueOperations, times(1)).set(key, value);
    }

    @Test
    void testGetValue() {
        String key = "testKey";
        String expectedValue = "testValue";

        // Redis에서 해당 키로 값을 가져올 때 expectedValue가 반환되도록 설정
        when(valueOperations.get(key)).thenReturn(expectedValue);

        // 메서드 실행
        Object result = redisService.getValue(key);

        // 결과가 expectedValue와 같은지 확인
        assertEquals(expectedValue, result);

        // get 메서드가 한 번 호출되었는지 확인
        verify(valueOperations, times(1)).get(key);
    }

    @Test
    void testDeleteValue() {
        String key = "testKey";

        // 메서드 실행
        redisService.deleteValue(key);

        // delete 메서드가 한 번 호출되었는지 확인
        verify(redisTemplate, times(1)).delete(key);
    }

    @Test
    void testHasKey() {
        String key = "testKey";
        Boolean expectedValue = true;

        // Redis에 해당 키가 있는지 확인할 때 true 반환되도록 설정
        when(redisTemplate.hasKey(key)).thenReturn(expectedValue);

        // 메서드 실행
        Boolean result = redisService.hasKey(key);

        // 결과가 true인지 확인
        assertEquals(expectedValue, result);

        // hasKey 메서드가 한 번 호출되었는지 확인
        verify(redisTemplate, times(1)).hasKey(key);
    }

}
