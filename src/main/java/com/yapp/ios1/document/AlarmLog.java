package com.yapp.ios1.document;

import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * created by jg 2021/05/23
 */
// MongoDB 임시
@Builder
@Document("alarm_log")
@Getter
public class AlarmLog {
    @Id
    private ObjectId id;
    private String title;
    private String message;
    private boolean isFriend;
    private Long userId;
}
