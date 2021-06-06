package com.yapp.ios1.model.tag;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * created by ayoung 2021/05/08
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Getter
public class Tag {

    private Long id;
    private String tagName;

    public void setId(Long id) {
        this.id = id;
    }

    public Tag(String tagName) {
        this.tagName = tagName;
    }
}
