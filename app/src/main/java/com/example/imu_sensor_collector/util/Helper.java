package com.example.imu_sensor_collector.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Helper {

    //  yyyy-MM-dd HH:mm:ss"
    public static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss_SSS");

    public static String format(LocalDateTime input) {
        return dateFormat.format(input);
    }

    public static String formatToFileName(LocalDateTime input) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss_SSS");
        return dateFormat.format(input);
    }
}
