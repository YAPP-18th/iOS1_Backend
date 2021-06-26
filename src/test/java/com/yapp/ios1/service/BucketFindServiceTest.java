package com.yapp.ios1.service;

import com.yapp.ios1.error.exception.bucket.BucketNotFoundException;
import com.yapp.ios1.service.bucket.BucketFindService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * created by jg 2021/06/25
 */
@SpringBootTest
public class BucketFindServiceTest {

    @Autowired
    BucketFindService bucketFindService;

    @Test
    void 존재하지_않는_버킷_에러() {
        assertThrows(BucketNotFoundException.class, () -> {
                bucketFindService.getBucket(0L, 0L);
        });
    }
}
