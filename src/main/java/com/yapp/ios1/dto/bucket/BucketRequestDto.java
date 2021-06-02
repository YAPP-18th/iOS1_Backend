package com.yapp.ios1.dto.bucket;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

import static com.yapp.ios1.common.ResponseMessage.*;

/**
 * created by ayoung 2021/05/08
 */
@Getter
@Builder
public class BucketRequestDto {

    @JsonIgnore
    private Long id;
    @JsonIgnore
    private Long userId;
    @NotBlank(message = NOT_VALID_BUCKET_NAME)
    private String bucketName;
    @Min(value = 2, message = NOT_VALID_STATE)
    @Max(value = 4, message = NOT_VALID_STATE)
    private int state;
    @Min(value = 2, message = NOT_VALID_CATEGORY_ID)
    @Max(value = 10, message = NOT_VALID_CATEGORY_ID)
    private int categoryId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String content;
    private List<String> imageList;
    private List<String> tagList;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setId(Long id) { this.id = id; }
}