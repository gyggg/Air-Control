package com.example.airconditionerremote.ui.settings;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.airconditionerremote.R;
import com.example.airconditionerremote.data.Record;
import com.example.airconditionerremote.data.Records;

public class SettingsFragment extends Fragment {

    private EditText et_ip;
    private Button btnReset;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        et_ip = root.findViewById(R.id.et_ip);
        btnReset = root.findViewById(R.id.btn_reset);
        et_ip.setText(Record.getMainRecord().getIpAddress());
        et_ip.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                Record.getMainRecord().setIpAddress(s.toString());
                Record.saveRecord(getContext());
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("はい", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Record.resetRecord(getContext());
                Records.resetRecords(getContext());
            }
        });
        builder.setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.setMessage("すべての記録を消したいですか？（記録が消されたら、元の状態に戻れないですよ）");

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        return root;
    }
}
