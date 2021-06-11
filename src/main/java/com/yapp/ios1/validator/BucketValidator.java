package com.yapp.ios1.validator;

import com.yapp.ios1.error.exception.bucket.BucketNotFoundException;
import com.yapp.ios1.error.exception.bucket.bucketStateIdInvalidException;
import com.yapp.ios1.mapper.BucketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.yapp.ios1.enums.BucketStatus.BUCKET_FAIL;
import static com.yapp.ios1.enums.BucketStatus.BUCKET_WHOLE;

/**
 * created by jg 2021/06/10
 */
@RequiredArgsConstructor
@Component
public class BucketValidator {

    private final BucketMapper bucketMapper;

    // TODO Validate 책임
    public void checkValidBucket(Long bucketId, Long userId) {
        bucketMapper.findByBucketIdAndUserId(bucketId, userId)
                .orElseThrow(BucketNotFoundException::new);
    }

    // TODO Validate 책임
    public void checkValidBucketStateId(int bucketStateId) {
        if (bucketStateId <= BUCKET_WHOLE.get() || bucketStateId > BUCKET_FAIL.get()) {
            throw new bucketStateIdInvalidException();
        }
    }
}
