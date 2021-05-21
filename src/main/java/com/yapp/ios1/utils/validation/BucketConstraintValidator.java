package com.yapp.ios1.utils.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

/**
 * created by ayoung 2021/05/10
 */
public class BucketConstraintValidator implements ConstraintValidator<BucketConstraint, String> {
    public static final List<String> bucketStateList = Arrays.asList("EXPECT", "ONGOING", "COMPLETE");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && bucketStateList.contains(value.toUpperCase());
    }
}
