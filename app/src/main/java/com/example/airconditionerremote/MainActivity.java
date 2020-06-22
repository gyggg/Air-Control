package com.example.airconditionerremote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private ToggleButton tgbtn_switch;
    private Button btn_cooling;
    private Button btn_heating;
    private Button btn_higher;
    private Button btn_lower;
    private EditText edt_ipaddr;

    private int ctlNum;
    private int stat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tgbtn_switch = findViewById(R.id.tgbtn_switch);
        btn_higher = findViewById(R.id.tgbtn_higher);
        btn_lower = findViewById(R.id.tgbtn_lower);
        btn_cooling = findViewById(R.id.tgbtn_cooling);
        btn_heating = findViewById(R.id.tgbtn_heating);
        edt_ipaddr = findViewById(R.id.edt_ipaddr);

        tgbtn_switch.setOnCheckedChangeListener(this);
        btn_cooling.setOnClickListener(this);
        btn_heating.setOnClickListener(this);
        btn_higher.setOnClickListener(this);
        btn_lower.setOnClickListener(this);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!buttonView.isPressed()) {
            return;
        }
        switch (buttonView.getId()) {
            case R.id.tgbtn_switch:
                ctlNum=1;
                break;
            default:break;
        }

        if (isChecked) {
            stat = 1;
        } else {
            stat = 0;
        }

        String IPAdress ;
        if (edt_ipaddr.getText() != null && !edt_ipaddr.getText().toString().isEmpty()) {
            IPAdress = edt_ipaddr.getText().toString();
        } else {
            IPAdress= "192.168.1.13";
        }

        HttpGetTask task = new HttpGetTask(this,IPAdress);
        task.execute(ctlNum, stat);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tgbtn_cooling:
                ctlNum=2;
                stat=0;
                break;
            case R.id.tgbtn_heating:
                ctlNum=2;
                stat=1;
                break;
            case R.id.tgbtn_higher:
                ctlNum=3;
                stat=1;
                break;
            case R.id.tgbtn_lower:
                ctlNum=3;
                stat=0;
                break;
            default:break;
        }

        String IPAdress ;
        if (edt_ipaddr.getText() != null && !edt_ipaddr.getText().toString().isEmpty()) {
            IPAdress = edt_ipaddr.getText().toString();
        } else {
            IPAdress= "192.168.1.13";
        }

        HttpGetTask task = new HttpGetTask(this,IPAdress);
        task.execute(ctlNum, stat);
    }
}
