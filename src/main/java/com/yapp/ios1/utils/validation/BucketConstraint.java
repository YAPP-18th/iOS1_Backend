package com.yapp.ios1.utils.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static com.yapp.ios1.common.ResponseMessage.BAD_BUCKET_STATE;

/**
 * created by ayoung 2021/05/10
 */
@Constraint(validatedBy = BucketConstraintValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BucketConstraint {
    String message() default BAD_BUCKET_STATE;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

