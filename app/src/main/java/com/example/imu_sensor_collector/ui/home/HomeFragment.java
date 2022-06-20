package com.example.imu_sensor_collector.ui.home;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.imu_sensor_collector.databinding.FragmentHomeBinding;
import com.example.imu_sensor_collector.ui.dashboard.DashboardFragment;

import androidx.lifecycle.Observer;

import java.io.File;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ListView listFiles;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        listFiles =  binding.listFiles;

        Button btnRefresh = binding.btnRefresh;
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
            }
        });
        return root;
    }

    protected  void refresh() {
        File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), DashboardFragment.DATA_FOLDER);
        if (!path.exists()) {
            return;
        }
        final ArrayList<String> list = new ArrayList<String>();
        for (File f:  path.listFiles()) {
            list.add(f.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, list);
        listFiles.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}