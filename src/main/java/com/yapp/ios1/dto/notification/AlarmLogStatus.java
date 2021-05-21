package com.yapp.ios1.dto.notification;

/**
 * created by jg 2021/05/21
 */
public enum AlarmLogStatus {
    ACTIVITY(1),     // 알람 활성화
    NO_ACTIVITY(2);  // 알람 삭제됨

    private final int alarmStatus;

    AlarmLogStatus(int alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

    public int getAlarmStatus() {
        return alarmStatus;
    }
}
