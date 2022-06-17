package com.example.imu_sensor_collector.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.imu_sensor_collector.sensor.IMUData;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<Boolean> inStart;

    private final MutableLiveData<Float> accX;
    private final MutableLiveData<Float> accY;
    private final MutableLiveData<Float> accZ;

    private final MutableLiveData<Float> gyroX;
    private final MutableLiveData<Float> gyroY;
    private final MutableLiveData<Float> gyroZ;

    private final MutableLiveData<Float> magX;
    private final MutableLiveData<Float> magY;
    private final MutableLiveData<Float> magZ;

    private final MutableLiveData<Double> gpsLat;
    private final MutableLiveData<Double> gpsLon;
    private final MutableLiveData<Double> gpsAtt;
    private final MutableLiveData<Float> gpsSpeed;

    private final MutableLiveData<Integer> count;
    private final MutableLiveData<LocalDateTime> startTime;

    public DashboardViewModel() {
        inStart = new MutableLiveData<>(false);
        accX = new MutableLiveData<>();
        accY = new MutableLiveData<>();
        accZ = new MutableLiveData<>();
        gyroX = new MutableLiveData<>();
        gyroY = new MutableLiveData<>();
        gyroZ = new MutableLiveData<>();
        magX = new MutableLiveData<>();
        magY = new MutableLiveData<>();
        magZ = new MutableLiveData<>();
        gpsLat = new MutableLiveData<>();
        gpsLon = new MutableLiveData<>();
        gpsAtt = new MutableLiveData<>();
        gpsSpeed = new MutableLiveData<>();
        count = new MutableLiveData<>(0);
        startTime = new MutableLiveData<>(LocalDateTime.now(ZoneOffset.UTC));
    }

    public LiveData<Double> getGPSLat() {
        return gpsLat;
    }

    public LiveData<Double> getGPSLon() {
        return gpsLon;
    }

    public LiveData<Double> getGPSAtt() {
        return gpsAtt;
    }

    public LiveData<Float> getGPSSpeed() {
        return gpsSpeed;
    }


    public LiveData<Float> getAccX() {
        return accX;
    }

    public LiveData<Float> getAccY() {
        return accY;
    }

    public LiveData<Float> getAccZ() {
        return accZ;
    }

    public LiveData<Float> getGyroX() {
        return gyroX;
    }

    public LiveData<Float> getGyroY() {
        return gyroY;
    }

    public LiveData<Float> getGyroZ() {
        return gyroZ;
    }

    public LiveData<Float> getMagX() {
        return magX;
    }

    public LiveData<Float> getMagY() {
        return magY;
    }

    public LiveData<Float> getMagZ() {
        return magZ;
    }

    public LiveData<Integer> getCount() {
        return count;
    }

    public LiveData<LocalDateTime> getStartTime() {
        return startTime;
    }

    public void setAccX(Float value) {
        accX.setValue(value);
    }

    public void setAccY(Float value) {
        accY.setValue(value);
    }

    public void setAccZ(Float value) {
        accZ.setValue(value);
    }

    public void setGyroX(Float value) {
        gyroX.setValue(value);
    }

    public void setGyroY(Float value) {
        gyroY.setValue(value);
    }

    public void setGyroZ(Float value) {
        gyroZ.setValue(value);
    }

    public void setMagX(Float value) {
        magX.setValue(value);
    }

    public void setMagY(Float value) {
        magY.setValue(value);
    }

    public void setMagZ(Float value) {
        magZ.setValue(value);
    }

    public void setGPSLat(Double value) {
        gpsLat.setValue(value);
    }

    public void setGPSLon(Double value) {
        gpsLon.setValue(value);
    }

    public void setGPSAtt(Double value) {
        gpsAtt.setValue(value);
    }

    public void setGPSSpeed(Float value) {
        gpsSpeed.setValue(value);
    }

    public void setStartTime(LocalDateTime value) {
        startTime.setValue(value);
    }

    public void increaseCount() {
        count.setValue(Optional.ofNullable(count.getValue()).orElse(0) + 1);
    }

    public void resetCount() {
        count.setValue(0);
    }

    public LiveData<Boolean> getInStart() {
        return inStart;
    }

    public void toogleInStart() {
        inStart.setValue(! Optional.ofNullable(inStart.getValue()).orElse(false));
    }

    public IMUData toIMUData() {
        IMUData result = new IMUData();
        result.accX = Optional.ofNullable(this.accX.getValue()).orElse(0f);
        result.accY = Optional.ofNullable(this.accY.getValue()).orElse(0f);
        result.accZ = Optional.ofNullable(this.accZ.getValue()).orElse(0f);
        result.gyroX = Optional.ofNullable(this.gyroX.getValue()).orElse(0f);
        result.gyroY = Optional.ofNullable(this.gyroY.getValue()).orElse(0f);
        result.gyroZ = Optional.ofNullable(this.gyroZ.getValue()).orElse(0f);
        result.magX = Optional.ofNullable(this.magX.getValue()).orElse(0f);
        result.magY = Optional.ofNullable(this.magY.getValue()).orElse(0f);
        result.magZ = Optional.ofNullable(this.magZ.getValue()).orElse(0f);
        result.gpsLat = Optional.ofNullable(this.gpsLat.getValue()).orElse(0.0d);
        result.gpsLon = Optional.ofNullable(this.gpsLon.getValue()).orElse(0.0d);
        result.gpsAtt = Optional.ofNullable(this.gpsAtt.getValue()).orElse(0.0d);
        result.gpsSpeed = Optional.ofNullable(this.gpsSpeed.getValue()).orElse(0f);
        result.trackTime = LocalDateTime.now(ZoneOffset.UTC);
        return result;
    }
}