package com.yapp.ios1.dto.notification;

/**
 * created by jg 2021/05/21
 */
public enum AlarmLogStatus {
    ACTIVITY(1),
    NO_ACTIVITY(2);

    private final int alarmStatus;

    AlarmLogStatus(int alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

    public int getAlarmStatus() {
        return alarmStatus;
    }
}
