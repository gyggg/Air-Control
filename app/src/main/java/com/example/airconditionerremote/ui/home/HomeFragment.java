package com.example.airconditionerremote.ui.home;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.airconditionerremote.HttpTask;
import com.example.airconditionerremote.R;
import com.example.airconditionerremote.data.Record;
import com.example.airconditionerremote.data.Records;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;
    private Button btnTempUp;
    private Button btnTempDown;
    private Button btnRunPause;
    private Button btnMode;
    private Button btnPower;
    private TextView tvTemp;
    private TextView tvRunning;
    private TextView tvMode;
    private TextView tvPower;
    private FloatingActionButton fabAddRecord;
    private FloatingActionButton fabResend;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        Record.loadRecord(this.getContext());
        Records.loadRecords(this.getContext());
        btnTempUp = root.findViewById(R.id.btn_temp_up);
        btnTempDown = root.findViewById(R.id.btn_temp_down);
        btnRunPause = root.findViewById(R.id.btn_run_pause);
        btnMode = root.findViewById(R.id.btn_mode);
        btnPower = root.findViewById(R.id.btn_power);
        fabAddRecord = root.findViewById(R.id.fab_add_record);
        fabResend = root.findViewById(R.id.fab_resend);

        tvTemp = root.findViewById(R.id.tv_temp);
        tvRunning = root.findViewById(R.id.tv_running);
        tvMode = root.findViewById(R.id.tv_mode);
        tvPower = root.findViewById(R.id.tv_power);

        btnTempUp.setOnClickListener(this);
        btnTempDown.setOnClickListener(this);
        btnRunPause.setOnClickListener(this);
        btnMode.setOnClickListener(this);
        btnPower.setOnClickListener(this);
        fabAddRecord.setOnClickListener(this);
        fabResend.setOnClickListener(this);

        homeViewModel.getRecord().observe(getViewLifecycleOwner(), new Observer<Record>() {
            @Override
            public void onChanged(Record r) {
                tvTemp.setText(r.getTemputerString());
                tvMode.setText(r.getModeString());
                tvPower.setText(r.getPowerString());
                if(r.isRunning())
                    tvRunning.setVisibility(View.VISIBLE);
                else
                    tvRunning.setVisibility(View.GONE);
            }
        });

        homeViewModel.getRecord().postValue(Record.getMainRecord());
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        Record.saveRecord(getContext());
        Records.saveRecords(getContext());
        if(Record.getMainRecord().isResetFlag()) {
            Record.getMainRecord().setResetFlag(false);
            sendCtl();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_temp_up:
                Record.getMainRecord().temperatureUp();break;
            case R.id.btn_temp_down:
                Record.getMainRecord().temperatureDown();break;
            case R.id.btn_run_pause:
                Record.getMainRecord().setRunning(!Record.getMainRecord().isRunning());break;
            case R.id.btn_mode:
                Record.getMainRecord().modeChange();break;
            case R.id.btn_power:
                Record.getMainRecord().powerChange();break;
            case R.id.fab_resend:break;
            case R.id.fab_add_record:addRecord();return;
        }
        Record.saveRecord(this.getContext());
        sendCtl();
    }

    public void sendCtl() {
        homeViewModel.getRecord().postValue(Record.getMainRecord());
        try {
            HttpTask.sendGet(Record.getMainRecord().generateUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addRecord() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("はい", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Records.addRecord(Record.getMainRecord());
                Records.saveRecords(getContext());
            }
        });
        builder.setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setMessage("今の設定を記録したいですか？");
        dialog.show();
    }
}
