package com.example.imu_sensor_collector.sensor;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;
import java.util.Date;

public class IMUData {

    public IMUData() {
    }

    public float accX;
    public float accY;
    public float accZ;

    public float gyroX;
    public float gyroY;
    public float gyroZ;

    public float magX;
    public float magY;
    public float magZ;

    public double gpsLat;
    public double gpsLon;
    public double gpsAtt;
    public float gpsSpeed;
    public float gpsAccuracy;

    public Date trackTime;

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        IMUData newData = new IMUData();
        newData.accX = this.accX;
        newData.accY = this.accY;
        newData.accZ = this.accZ;
        newData.gyroX = this.gyroX;
        newData.gyroY = this.gyroY;
        newData.gyroZ = this.gyroZ;
        newData.magX = this.magX;
        newData.magY = this.magY;
        newData.magZ = this.magZ;
        newData.trackTime = this.trackTime;
        newData.gpsAccuracy = this.gpsAccuracy;
        return newData;

    }
}
