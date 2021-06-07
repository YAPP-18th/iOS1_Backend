package com.yapp.ios1.common;

/**
 * created by jg 2021/06/07
 */
public enum AlarmStatus {
    WHOLE_ALARM(1),      // 전체 알람
    FOLLOW_ALARM(2)      // 친구 알람
    ;
    private final int alarmStatus;

    AlarmStatus(int alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

    public int getAlarmStatus() {
        return alarmStatus;
    }
}
