package com.poly.moneylover.models;

public class EventbusModel {
    String timeStampStart;
    String timeStampEnd;

    public EventbusModel(String timeStampStart, String timeStampEnd) {
        this.timeStampStart = timeStampStart;
        this.timeStampEnd = timeStampEnd;
    }

    public String getTimeStampStart() {
        return timeStampStart;
    }

    public void setTimeStampStart(String timeStampStart) {
        this.timeStampStart = timeStampStart;
    }

    public String getTimeStampEnd() {
        return timeStampEnd;
    }

    public void setTimeStampEnd(String timeStampEnd) {
        this.timeStampEnd = timeStampEnd;
    }
}
