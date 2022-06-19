package com.example.imu_sensor_collector.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class Helper {

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss_SSS", Locale.getDefault());

    public static String format(Date input) {
        return dateFormat.format(input);
    }

    public static String formatToFileName(Date input) {
        SimpleDateFormat fileDateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS", Locale.getDefault());
        return fileDateFormat.format(input);
    }

    public static int calMiliscond(int hertz) {
        return 1000/hertz;
    }
}
