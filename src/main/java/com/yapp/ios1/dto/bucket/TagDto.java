package com.yapp.ios1.dto.bucket;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * created by ayoung 2021/05/08
 */
@NoArgsConstructor
@Getter
public class TagDto {

    private Long id;
    private String tagName;

    public void setId(Long id) {
        this.id = id;
    }

    public TagDto(String tagName) {
        this.tagName = tagName;
    }
}
