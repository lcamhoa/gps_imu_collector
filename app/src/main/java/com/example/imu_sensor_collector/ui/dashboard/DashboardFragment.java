package com.example.imu_sensor_collector.ui.dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.imu_sensor_collector.R;
import com.example.imu_sensor_collector.databinding.FragmentDashboardBinding;
import com.example.imu_sensor_collector.sensor.IMUDataRepository;
import com.example.imu_sensor_collector.util.Helper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class DashboardFragment extends Fragment implements SensorEventListener {

    private FragmentDashboardBinding binding;
    private DashboardViewModel dashboardViewModel;
    private IMUDataRepository imuDataRepository;

    private FusedLocationProviderClient fusedLocationClient;

    private SensorManager sensorManagers;
    private Sensor senAccelerometer;
    private Sensor senGyroscope;
    private Sensor senMagnetic;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        View root = binding.getRoot();

        // Initialize sensor
        // Init sensor

        final int HERTZ = 5; // 5 frame per second
        imuDataRepository = new IMUDataRepository(HERTZ, dashboardViewModel);
        sensorManagers = (SensorManager) this.getActivity().getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = sensorManagers.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senGyroscope = sensorManagers.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        senMagnetic = sensorManagers.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        // Binding UI

        final Button btn = binding.btnTrigger;
        dashboardViewModel.getInStart().observe(getViewLifecycleOwner(), v -> {
            String t = !v.booleanValue() ? getResources().getString(R.string.start) : getResources().getString(R.string.stop);
            btn.setText(t);
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dashboardViewModel.toogleInStart();
                if (dashboardViewModel.getInStart().getValue()) {
                    startTracking();
                } else {
                    stopTracking();
                }
            }
        });
        final Button btnSave = binding.btnSave2;
        final EditText txtFileNamePrefix = binding.txtFileNamePrefix;
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/IMU_Data");
                if (!path.exists()) {
                    path.mkdirs();
                }
                final String fileName = txtFileNamePrefix.getText().toString() + "_" + Helper.formatToFileName(LocalDateTime.now()) + ".csv";
                final File file = new File(path, fileName);
                try {
                    file.createNewFile();
                    imuDataRepository.writeToFile(file);
                    imuDataRepository.clear();
                    Toast.makeText(getContext(), "Success save to file " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                } catch (IOException ex) {
                    Toast.makeText(getContext(), "Error save to file " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        final TextView gpsLat = binding.gpsLat;
        dashboardViewModel.getGPSLat().observe(getViewLifecycleOwner(), (v) -> {
            gpsLat.setText(v.toString());
        });

        final TextView gpsLon = binding.gpsLon;
        dashboardViewModel.getGPSLon().observe(getViewLifecycleOwner(), (v) -> {
            gpsLon.setText(v.toString());
        });

        final TextView gpsAtt = binding.gpsAtt;
        dashboardViewModel.getGPSAtt().observe(getViewLifecycleOwner(), (v) -> {
            gpsAtt.setText(v.toString());
        });

        final TextView gpsSpeed = binding.gpsSpeed;
        dashboardViewModel.getGPSSpeed().observe(getViewLifecycleOwner(), (v) -> {
            gpsSpeed.setText(v.toString());
        });

        final TextView accX = binding.accX;
        dashboardViewModel.getAccX().observe(getViewLifecycleOwner(), (v) -> {
            accX.setText(v.toString());
        });
        final TextView accY = binding.accY;
        dashboardViewModel.getAccY().observe(getViewLifecycleOwner(), (v) -> {
            accY.setText(v.toString());
        });
        final TextView accZ = binding.accZ;
        dashboardViewModel.getAccZ().observe(getViewLifecycleOwner(), (v) -> {
            accZ.setText(v.toString());
        });

        final TextView gyroX = binding.gyroX;
        dashboardViewModel.getGyroX().observe(getViewLifecycleOwner(), (v) -> {
            gyroX.setText(v.toString());
        });
        final TextView gyroY = binding.gyroY;
        dashboardViewModel.getGyroY().observe(getViewLifecycleOwner(), (v) -> {
            gyroY.setText(v.toString());
        });
        final TextView gyroZ = binding.gyroZ;
        dashboardViewModel.getGyroZ().observe(getViewLifecycleOwner(), (v) -> {
            gyroZ.setText(v.toString());
        });

        final TextView magX = binding.magX;
        dashboardViewModel.getMagX().observe(getViewLifecycleOwner(), (v) -> {
            magX.setText(v.toString());
        });
        final TextView magY = binding.magY;
        dashboardViewModel.getMagY().observe(getViewLifecycleOwner(), (v) -> {
            magY.setText(v.toString());
        });
        final TextView magZ = binding.magZ;
        dashboardViewModel.getMagZ().observe(getViewLifecycleOwner(), (v) -> {
            magZ.setText(v.toString());
        });
        final TextView count = binding.count;
        dashboardViewModel.getCount().observe(getViewLifecycleOwner(), (v) -> {
            count.setText(v.toString());
        });
        final TextView startAt = binding.start;
        dashboardViewModel.getStartTime().observe(getViewLifecycleOwner(), (v) -> {
            startAt.setText(v.toString());
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    protected void stopTracking() {
        sensorManagers.unregisterListener(this);
    }

    protected void startTracking() {
        dashboardViewModel.setStartTime(LocalDateTime.now(ZoneOffset.UTC));
        sensorManagers.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManagers.registerListener(this, senGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManagers.registerListener(this, senMagnetic, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private static final String TAG = "IMU";

    @SuppressLint("MissingPermission")
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];
        int type = sensorEvent.sensor.getType();
        Log.i(TAG, String.format("Type: %s timestamp: %s  x: %.4f y: %.4f z: %.4f", type, sensorEvent.timestamp, x, y, z));
        switch (type) {
            case Sensor.TYPE_ACCELEROMETER:
                dashboardViewModel.setAccX(x);
                dashboardViewModel.setAccY(y);
                dashboardViewModel.setAccZ(z);
                break;
            case Sensor.TYPE_GYROSCOPE:
                dashboardViewModel.setGyroX(x);
                dashboardViewModel.setGyroY(y);
                dashboardViewModel.setGyroZ(z);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                dashboardViewModel.setMagX(x);
                dashboardViewModel.setMagY(y);
                dashboardViewModel.setMagZ(z);
                break;
            default:
                break;
        }
        fusedLocationClient.flushLocations();
        fusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), location -> {
            if (location != null) {
                dashboardViewModel.setGPSLat(location.getLatitude());
                dashboardViewModel.setGPSLon(location.getLongitude());
                dashboardViewModel.setGPSAtt(location.getAltitude());
                dashboardViewModel.setGPSSpeed(location.getSpeed());
            }
        });
        imuDataRepository.persit();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

}