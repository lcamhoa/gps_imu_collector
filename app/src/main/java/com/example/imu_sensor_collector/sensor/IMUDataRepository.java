package com.example.imu_sensor_collector.sensor;

import com.example.imu_sensor_collector.ui.dashboard.DashboardViewModel;
import com.example.imu_sensor_collector.util.Helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Deque;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class IMUDataRepository {

    private Deque<IMUData> dataRepo;
    private Integer hz;
    private DashboardViewModel model;

    public IMUDataRepository(Integer hz, DashboardViewModel model) {
        this.hz = hz;
        this.dataRepo = new ConcurrentLinkedDeque<>();
        this.model = model;
    }

    public Queue<IMUData> getDataRepo() {
        return dataRepo;
    }

    public Integer getHz() {
        return hz;
    }

    public void setHz(Integer hz) {
        this.hz = hz;
    }

    public void persit() {
        IMUData last = dataRepo.peekLast();
        if(Objects.isNull(last)) {
            IMUData ins = model.toIMUData();
            dataRepo.offerLast(ins);
            model.increaseCount();
        } else if( model.getInStart().getValue() ) {
            Date d = new Date(System.currentTimeMillis() + Helper.calMiliscond(hz));
            boolean isNew = last.trackTime.before(d);
            if(isNew) {
                IMUData ins = model.toIMUData();
                dataRepo.offerLast(ins);
                model.increaseCount();
            }
        }
    }

    public void clear() {
        dataRepo.clear();
        model.resetCount();
    }

    public void writeToFile(File file) throws IOException {
        FileOutputStream fOut = new FileOutputStream(file, false);
        String header = "DATE (YYYY-MO-DD HH-MI-SS_SSS), ACCELEROMETER X (m/s�) , ACCELEROMETER Y (m/s�), " +
                "ACCELEROMETER Z (m/s�), GYROSCOPE Yaw (rad/s), GYROSCOPE Pitch (rad/s), GYROSCOPE Roll (rad/s), " +
                "MAGNETIC FIELD X (μT), MAGNETIC FIELD Y (μT), MAGNETIC FIELD Z (μT)\n";
        fOut.write(header.getBytes());
        String rowFormat = "%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n";
        for (IMUData itm:  this.dataRepo) {
            String row = String.format(rowFormat, Helper.format(itm.trackTime) ,
                    itm.accX, itm.accY, itm.accZ, itm.gyroX, itm.gyroY,
                    itm.gyroZ, itm.magX, itm.magY, itm.magZ );
            fOut.write(row.getBytes());
        }
        fOut.flush();
        fOut.close();
    }


}
